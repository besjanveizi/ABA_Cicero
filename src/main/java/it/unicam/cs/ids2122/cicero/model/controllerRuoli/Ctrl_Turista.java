package it.unicam.cs.ids2122.cicero.model.controllerRuoli;

import it.unicam.cs.ids2122.cicero.model.Bacheca;
import it.unicam.cs.ids2122.cicero.model.IBacheca;
import it.unicam.cs.ids2122.cicero.model.Piattaforma;
import it.unicam.cs.ids2122.cicero.model.entities.esperienza.Esperienza;
import it.unicam.cs.ids2122.cicero.model.entities.bean.BeanFattura;
import it.unicam.cs.ids2122.cicero.model.entities.bean.BeanInvito;
import it.unicam.cs.ids2122.cicero.model.entities.bean.BeanPrenotazione;
import it.unicam.cs.ids2122.cicero.model.entities.bean.StatoPrenotazione;
import it.unicam.cs.ids2122.cicero.model.gestori.*;
import it.unicam.cs.ids2122.cicero.ruoli.Turista;
import it.unicam.cs.ids2122.cicero.util.Money;


import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
import java.util.stream.Collectors;
/**
 * Rappresenta un gestore radice un utente <code>Turista</code> che elabora le sue interazioni con il sistema.
 */
public class Ctrl_Turista extends Ctrl_UtenteAutenticato implements Ctrl_Utente {

    private final GestorePrenotazioni gestorePrenotazioni;
    private final GestoreInviti gestoreInviti;
    private final GestoreFatture gestoreFatture;
    private final GestoreRimborsi gestoreRimborsi;
    private final IBacheca bacheca;

    Logger logger = Logger.getLogger(Piattaforma.class.getName());


    public Ctrl_Turista(Turista turista) {
        super(turista);
        impostaMenu();
        bacheca = Bacheca.getInstance();
        gestoreFatture = new GestoreFatture(utente);
        gestorePrenotazioni = new GestorePrenotazioni(utente);
        gestoreRimborsi = new GestoreRimborsi(utente);
        gestoreInviti = new GestoreInviti(utente);
    }

    @Override
    protected boolean switchMenu(int scelta) {
        boolean loop = true;
        switch (scelta) {
            case 4:
                prenotaEsperienza();
                break;
            case 5:
                pagaPrenotazione(null);
                break;
            case 6:
                invitaEsperienza();
                break;
            case 7:
                gestisciInvitiRicevuti();
                break;
            case 8:
                cancellaPrenotazione();
                break;
            case 9:
                richiediRimborso(null);
                break;
            default:
                loop = super.switchMenu(scelta);
        }
        return loop;
    }

    /**
     * Realizza il caso d' uso prenota esperienza.
     */
    private void prenotaEsperienza() {
        Esperienza esperienza = null;
        while (true) {
            esperienza = selezionaEsperienza(bacheca.getEsperienze(Esperienza::isAvailable));
            if (esperienza == null) {
                logger.info("Non ci sono esperienze disponibili da prenotare\n");
                return;
            }
            view.message(esperienza.longToString());
            int scelta = view.fetchChoice("\n1) Proseguire con la prenotazione dell'esperienza scelta\n" +
                    "2) Scegli un'altra esperienza\n3) Torna al menu principale", 3);
            if (scelta == 3) return;
            if (scelta == 1) break;
        }
        int postiDisponibili = esperienza.getPostiDisponibili();
        int posti_scelti;
        while (true) {
            view.message("Inserisci il numero dei posti da riservare [max= " + postiDisponibili + "]");
            posti_scelti = view.fetchInt();
            if (posti_scelti > 0 && posti_scelti <= postiDisponibili) break;
            else logger.info("Errore di inserimento, riprova.");
        }
        Money costoIndividuale = esperienza.getCostoIndividuale();
        view.message("Costo totale = " + posti_scelti + " * " + costoIndividuale.toString() + " = " +
                costoIndividuale.op_multi(String.valueOf(posti_scelti)) + costoIndividuale.getValuta().getSymbol());
        view.message("Confermare prenotazione [Y/n]");
        int id;
        if (view.fetchBool()) {
            id = gestorePrenotazioni.crea_prenotazione(esperienza, posti_scelti);
            logger.info("Prenotazione creata.\n");
        } else {
            logger.info("Prenotazione annullata.\n");
            return;
        }
        BeanPrenotazione p = gestorePrenotazioni.getPrenotazione(bp -> bp.getID_prenotazione() == id);
        pagaPrenotazione(p);
    }

    /**
     * Permette il pagamento di una prenotazione. (RISERVATA)
     *
     * @param prenotazione può essere null se l'accesso è diretto.
     */
    private void pagaPrenotazione(BeanPrenotazione prenotazione){
        if(prenotazione == null){
            prenotazione = seleziona_prenotazione(
                    gestorePrenotazioni
                            .getPrenotazioni(p -> p.getStatoPrenotazione().equals(StatoPrenotazione.RISERVATA)));
            if (prenotazione == null) {
                logger.info("Non ci sono prenotazioni da pagare\n");
                return;
            }
        }
        view.message(prenotazione.toString());
        view.message("Vuoi pagare la prenotazione? [Y/n] ");
        if (view.fetchBool()) {
            stub_mod_pagamento();
            gestoreFatture.crea_fattura(prenotazione);
            gestorePrenotazioni.modifica_stato(prenotazione, StatoPrenotazione.PAGATA);
            logger.info("La prenotazione è stata pagata");
        }
        else
            view.message("La prenotazione rimarrà riservata fino a " + prenotazione.getScadenza());
    }

    /**
     * realizza il caso d'uso, invita per un esperienza.
     * L'invito modifica la disponibilità di un esperienza.
     */
    private void invitaEsperienza() {
        Esperienza esperienza = selezionaEsperienza(bacheca.getEsperienze(Esperienza::isAvailable));

        if (esperienza == null) {
            logger.info("Non ci sono esperienze disponibili per effettuare un invito\n");
            return;
        }
        view.message(esperienza.longToString());
        String mail_invitato = view.ask("Inserisci l'email dell'invitato");
        int posti_riservati, postiDisponibili = esperienza.getPostiDisponibili();
        while (true) {
            view.message("Inserisci il numero dei posti da riservare [max= " + postiDisponibili + "]");
            posti_riservati = view.fetchInt();
            if (posti_riservati > 0 && posti_riservati <= postiDisponibili) break;
            else logger.info("Errore di inserimento, riprova.");
        }
        view.message("Confermare l'invito? [Y/n]");
        boolean continua = view.fetchBool();
        if (continua) {
            gestoreInviti.crea_invito(esperienza, mail_invitato, posti_riservati);
            logger.info("Invito eseguito\n");
        }
        else logger.info("Invito non eseguito\n");
    }

    /**
     * Realizza i casi d' uso accetta/rifiuto invito.
     * In caso di accettazione si genera una nova prenotazione.
     * In caso di rifiuto l' invito viene eliminato.
     */
    private void gestisciInvitiRicevuti() {
        BeanInvito beanInvito = seleziona_invito(gestoreInviti.getRicevuti());

        if(beanInvito == null)
            logger.info("Non hai inviti ricevuti\n");
        else {
            view.message(beanInvito.toString());
            view.message("Vuoi accettare l'invito scelto? [Y/n]\nNota bene: l'accettazione comporterà la " +
                    "generazione della prenotazione all'esperienza con i parametri impostati dall'invito");
            boolean accetta = view.fetchBool();
            if (accetta) {
                gestorePrenotazioni.crea_prenotazione(beanInvito);
                logger.info("Prenotazione generata\n");
            } else {
                view.message("Vuoi rifiutare l'invito? [Y/n]");
                accetta = view.fetchBool();
                if(accetta) {
                    gestoreInviti.rifiuta_invito(beanInvito);
                    logger.info("L'invito è stato cancellato dalla lista degli inviti ricevuti\n");
                } else logger.info("Nessuna azione eseguita.\n");
            }
        }
    }

    private void cancellaPrenotazione() {
        BeanPrenotazione b = seleziona_prenotazione(gestorePrenotazioni.getPrenotazioni(BeanPrenotazione::isValida));
        if (b == null) {
            logger.info("Non hai prenotazioni riservate o pagate\n");
            return;
        }
        view.message(b.toString());
        view.message("Sei sicuro di voler cancellare la prenotazione [Y/n]");
        if (view.fetchBool()) {
            if (b.getStatoPrenotazione().equals(StatoPrenotazione.PAGATA)) {
                richiediRimborso(b);
            }
            else {
                gestorePrenotazioni.modifica_stato(b, StatoPrenotazione.CANCELLATA);
                logger.info("La prenotazione è stata cancellata.\n");
            }
        }
        else logger.info("La prenotazione non è stata cancellata\n");
    }

    /**
     * Realizza il caso d 'uso in collegato alla richiesta rimborso.
     * Se non è automatico, chiede la motivazione ed inserisce/crea una nuova richiesta di rimborso.
     * Se è automatico, quindi idoneo, modifica lo stato della prenotazione e crea una nuova fattura,
     * per simulare l' avvenuto rimborso.
     *
     * @param beanPrenotazione può essere null se l' accesso al metodo è diretto.
     */
    private void richiediRimborso(BeanPrenotazione beanPrenotazione){
        if(beanPrenotazione == null) {
            beanPrenotazione = seleziona_prenotazione(
                    gestorePrenotazioni
                            .getPrenotazioni(p -> p.getStatoPrenotazione().equals(StatoPrenotazione.PAGATA)));
            if(beanPrenotazione == null) {
                logger.info("Non hai prenotazioni pagate\n");
                return;
            }
        }
        logger.info("\telaborazione della pratica di rimborso..\n");
        final BeanPrenotazione finalBeanPrenotazione = beanPrenotazione;
        BeanFattura beanFattura = gestoreFatture.getEffettuati()
                .stream()
                .filter(f -> f.getId_prenotazione() == finalBeanPrenotazione.getID_prenotazione())
                .findFirst().orElseThrow();
        if (gestoreRimborsi.isAutoRefundable(beanPrenotazione)) {
            logger.info("\telaborazione del rimborso automatico..\n");
            gestoreFatture.crea_fattura(beanFattura);
            logger.info("Rimborso automatico avvenuto con successo.\n");
            gestorePrenotazioni.modifica_stato(beanPrenotazione, StatoPrenotazione.CANCELLATA);
            logger.info("La prenotazione è stata cancellata.\n");
        }
        else if(gestoreRimborsi.isRequestRefundable(beanPrenotazione)) {
            logger.info("Non è possibile avere un rimborso automatico per la prenotazione scelta, " +
                    "ma è possibile effettuare una richiesta di rimborso.\n");
            String motivo = view.ask("Inserisci la motivazione del rimborso:");
            gestoreRimborsi.crea_rimborso(beanFattura, motivo);
            logger.info("La richiesta di rimborso è stata inviata all'amministrazione.\n");
            gestorePrenotazioni.modifica_stato(beanPrenotazione, StatoPrenotazione.CANCELLATA);
            logger.info("La prenotazione è stata cancellata.\n");
        }
        else logger.info("Non è più possibile avere un rimborso dalla prenotazione scelta");
    }

    /**
     * Simula il pagamento via paypal e via iban.
     */
    private void stub_mod_pagamento(){
        view.message("scegli modalità di pagamento");
        view.message("1) pay pal");
        view.message("2) IBAN");
        switch (view.fetchInt()){
            case 1: view.message("PAYPAL indirizzamento alla pagina ");
                try {
                    Desktop.getDesktop().browse(URI.create("https://www.paypal.com/it/home"));
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            case 2: view.message("IBAN");
                    view.ask("inserire iban");
                    break;
            default:break;
        }

    }

    /**
     * Enumera e mostra le possibili scelte.
     * @return la scelta
     */
    private BeanPrenotazione seleziona_prenotazione(Set<BeanPrenotazione> prenotazioni) {
        if(prenotazioni.isEmpty()) {
            return null;
        }
        List<String> viewList = new ArrayList<>();
        List<Integer> idList = new ArrayList<>();
        int i = 1;
        for (BeanPrenotazione p : prenotazioni) {
            viewList.add(i++ + ") "+ p.shortToString());
            idList.add(p.getID_prenotazione());
        }
        view.message("Prenotazioni:", viewList);
        int indice = view.fetchChoice("Scegli l'indice della prenotazione", viewList.size());
        int idPrenotazione = idList.get(indice-1);
        return prenotazioni.stream().filter(p -> p.getID_prenotazione() == idPrenotazione).findFirst().orElseThrow();
    }

    /**
     * Enumera e mostra le possibili scelte.
     * @return la scelta
     */
    private BeanInvito seleziona_invito(Set<BeanInvito> inviti) {
        if(inviti.isEmpty()) return null;

        List<String> viewList = new ArrayList<>();
        List<Integer> idList = new ArrayList<>();
        int i = 1;
        for (BeanInvito invito : inviti) {
            viewList.add(i++ + ")" + invito.shortToString());
            idList.add(invito.getId_invito());
        }
        view.message("Inviti ricevuti:", viewList);
        int indice = view.fetchChoice("Scegli l'indice dell'invito", viewList.size());
        int idInvito = idList.get(indice-1);
        return inviti.stream().filter(ir -> ir.getId_invito() == idInvito).findFirst().orElseThrow();
    }

    private void impostaMenu() {
        menuItems.add("4) Prenota Esperienza");
        menuItems.add("5) Paga Prenotazione");
        menuItems.add("6) Invita ad una Esperienza");
        menuItems.add("7) Gestisci Inviti");
        menuItems.add("8) Cancella Prenotazione");
        menuItems.add("9) Richiedi Rimborso");
    }
}
