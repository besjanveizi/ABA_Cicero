package it.unicam.cs.ids2122.cicero.model.controllerRuoli;

import it.unicam.cs.ids2122.cicero.model.Bacheca;
import it.unicam.cs.ids2122.cicero.model.entities.esperienza.Esperienza;
import it.unicam.cs.ids2122.cicero.model.entities.bean.BeanFattura;
import it.unicam.cs.ids2122.cicero.model.entities.bean.BeanInvito;
import it.unicam.cs.ids2122.cicero.model.entities.bean.BeanPrenotazione;
import it.unicam.cs.ids2122.cicero.model.entities.bean.StatoPrenotazione;
import it.unicam.cs.ids2122.cicero.model.gestori.*;
import it.unicam.cs.ids2122.cicero.ruoli.Turista;
import it.unicam.cs.ids2122.cicero.view.IView;


import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collector;
import java.util.stream.Collectors;
/**
 * Rappresenta un gestore radice un utente <code>Turista</code> che elabora le sue interazioni con il sistema.
 */
public class Ctrl_Turista extends Ctrl_UtenteAutenticato implements Ctrl_Utente {


    public Ctrl_Turista(Turista turista) {
        super(turista);
        impostaMenu();
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

    private void cancellaPrenotazione() {

        BeanPrenotazione b = seleziona_prenotazione();

        if(b!= null && b.getStatoPrenotazione().equals(StatoPrenotazione.PAGATA)){
            cancellaPrenotazione_pagata(b);
        }
        if(b!=null && b.getStatoPrenotazione().equals(StatoPrenotazione.RISERVATA)){
            cancellaPrenotazione_riservata(b);
        }
        if(b!=null && b.getStatoPrenotazione().equals(StatoPrenotazione.CANCELLATA)){
            view.message("prenotazione già cancellata");
        }
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
        view.message("gestisci rimborsi");
        if(beanPrenotazione== null) {
            beanPrenotazione = seleziona_prenotazione();
        }
        if(beanPrenotazione!=null && beanPrenotazione.getStatoPrenotazione().equals(StatoPrenotazione.PAGATA)) {
            view.message(" elaborazione richiesta di rimborso... ");
            final BeanPrenotazione finalBeanPrenotazione = beanPrenotazione;
            BeanFattura beanFattura = GestorePagamenti.getInstance((Turista) utente).getEffettuati()
                    .stream()
                    .filter(f -> f.getId_prenotazione() == finalBeanPrenotazione.getID_prenotazione()).findFirst().get();
            if (GestoreRimborsi.getInstance((Turista) utente).rimborsa(beanPrenotazione)) {
                view.message("rimborso automatico");
                GestorePrenotazioni.getInstance((Turista) utente).modifica_stato(beanPrenotazione, StatoPrenotazione.CANCELLATA);
                GestorePagamenti.getInstance((Turista) utente).crea_fattura(beanFattura);
            }else{
                if(GestoreRimborsi.getInstance(utente).richiedi_rimborso(beanPrenotazione)){
                    view.message("possibile richiesta rimborso");
                    String motivo = view.ask("inserire motivazione rimborso");
                    GestoreRimborsi.getInstance((Turista) utente).crea_rimborso(beanFattura,motivo);
                }
            }
        }view.message("nessuna prenotazione pagata da rimborsare o pagamento rimborsabile disponibile");
    }

    /**
     * Realizza i casi d' uso accetta/rifiuto invito.
     * In caso di accettazione si genera una nova prenotazione.
     * In caso di rifiuto l' invito viene eliminato.
     */
    private void gestisciInvitiRicevuti() {
        view.message("gestisci inviti ");

        BeanInvito beanInvito = seleziona_invito();

        if(beanInvito!= null){
            view.message("accetta invito? [y/n] ");
            boolean accetta = view.fetchBool();
            if(accetta){
                GestorePrenotazioni.getInstance((Turista) utente).crea_prenotazione(beanInvito);
                view.message("prenotazione effettuata");
            }else {
                view.message("cancellare invito? [y/n] ");
                accetta = view.fetchBool();
                if(accetta) cancellaInvito(beanInvito);
            }
            view.message("prenotazione non effettuata");
        }
    }

    /**
     * Chiama il gestore per l' eliminazione dell' invito.
     * @param beanInvito da eliminare
     */
    private void cancellaInvito(BeanInvito beanInvito) {
            GestoreInviti.getInstance((Turista) utente).rifiuta_invito(beanInvito);
    }

    /**
     * realizza il caso d'uso, invita per un esperienza.
     * L'invito modifica la disponibilità di un esperienza.
     */
    private void invitaEsperienza() {
       view.message("invita");

       Esperienza esperienza = seleziona_esperienza();

       if(esperienza!=null){
           int posti = esperienza.getPostiDisponibili();

           view.message("posti disponibili:  " + posti);
           view.message("Continuare y/n ?");
           boolean continua= view.fetchBool();
           if(continua){
               String mail_invitato = view.ask("inserire mail invitato");
               view.message("inserire il numero di posti");
               int posti_inseriti = view.fetchInt();
               if(posti_inseriti>0 && posti_inseriti<= posti){
                   GestoreInviti.getInstance((Turista) utente).crea_invito(esperienza, mail_invitato, posti_inseriti);
                   view.message("invio spedito");
               }
           }
       }

    }

    /**
     * Realizza il caso d' uso prenota esperienza.
     */
    private void prenotaEsperienza() {
         Esperienza esperienza = seleziona_esperienza();
            if (esperienza != null) {
                int posti = esperienza.getPostiDisponibili();
                view.message("posti attualmente disponibili: " + posti);

                if (posti < 0) {
                    view.message("posti non disponibili ");
                    view.message("scegli nuova esperienza");

                }else {

                    int posti_scelti = 0;
                    while (true) {
                        view.message("inserire posti da riservare");
                        posti_scelti = view.fetchInt();
                        if (posti_scelti > 0 && posti_scelti <= posti) break;
                        else view.message("riprova, valore non valido");
                    }

                    view.message("costo totale: " + esperienza.getCostoIndividuale().op_multi(String.valueOf(posti_scelti)));
                    view.message("confermare prenotazione [y/n]");
                    int id;
                    boolean flag = view.fetchBool();
                    if (flag) {
                        view.message("confermata, creazione prenotazione...");
                        id = GestorePrenotazioni.getInstance((Turista) utente).crea_prenotazione(esperienza, posti_scelti);
                    } else {
                        view.message("prenotazione annullata");
                        view.message("uscita");
                        return;
                    }

                    view.message("pagare la prenotazione? [y/n] ");
                    if(view.fetchBool())
                    {
                        pagaPrenotazione(GestorePrenotazioni.getInstance((Turista) utente).getPrenotazione(bean-> bean.getID_prenotazione()==id));
                        view.message("pagamento riuscito, arrivederci e grazie");
                    }
                }
        }
    }

    /**
     * Permette il pagamento di una prenotazione. (RISERVATA)
     *
     * @param beanPrenotazione può essere null se l'accesso è diretto.
     */
    private void pagaPrenotazione(BeanPrenotazione beanPrenotazione){
        if(beanPrenotazione == null){
             beanPrenotazione = seleziona_prenotazione();
        }
        if (beanPrenotazione!=null && beanPrenotazione.getStatoPrenotazione().equals(StatoPrenotazione.RISERVATA)){
                view.message(beanPrenotazione.toString());
                view.message(" avviare il pagamento? [y/n] ");
                if (view.fetchBool()) {
                    stub_mod_pagamento();
                    GestorePagamenti.getInstance((Turista) utente).crea_fattura(beanPrenotazione);
                    GestorePrenotazioni.getInstance((Turista) utente).modifica_stato(beanPrenotazione, StatoPrenotazione.PAGATA);
                }
        }view.message("nessuna prenotazione presente, errore di selezione");
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
     * Permette di annullare una prenotazione, non pagata.
     */
    private void cancellaPrenotazione_riservata(BeanPrenotazione ref) {
        if(ref==null){
            view.message("nessuna prenotazione disponibile");
        }else {
            view.message(ref.toString());
            view.message("confermare y/n");
            boolean flag = view.fetchBool();
            if (flag) {
                GestorePrenotazioni.getInstance((Turista) utente).modifica_stato(ref, StatoPrenotazione.CANCELLATA);
                view.message("ok!");
            } else {
                view.message("la prenotazione non è stata cancellata");
                view.message("uscita");
            }
        }
    }

    /**
     * Permette di annullare una prenotazione, pagata.
     * Richiama il metodo richiediRimborso()
     */
    private void cancellaPrenotazione_pagata(BeanPrenotazione ref){
        if(ref==null){
            view.message("nessuna prenotazione disponibile");
        }else {
            view.message(ref.toString());
            view.message("confermare y/n");
            boolean flag = view.fetchBool();
            if (flag) {
                richiediRimborso(ref);
                view.message("ok!");
            } else {
                view.message("la prenotazione non è stata cancellata");
                view.message("uscita");
            }
        }
    }

    private void impostaMenu() {
        menuItems.add("4) Prenota Esperienza");
        menuItems.add("5) Paga Prenotazione");
        menuItems.add("6) Invita ad una Esperienza");
        menuItems.add("7) Gestisci Inviti");
        menuItems.add("8) Cancella Prenotazione");
        menuItems.add("9) Richiedi Rimborso");

    }

    /**
     * Enumera e mostra le possibili scelte.
     * @return la scelta
     */
    private BeanPrenotazione seleziona_prenotazione() {
        while(true){
            try {
                view.message("=====PRENOTAZIONI DISPONIBILI=====");
               Set<BeanPrenotazione> view_p = GestorePrenotazioni.getInstance((Turista) utente).getPrenotazioni();
               if(view_p.isEmpty()) return null;
               view.message("=====SELEZIONA INDICE======");
                AtomicInteger contatore = new AtomicInteger(0);
                return view_p
                        .stream()
                        .peek(beanPrenotazione -> view.message(contatore.getAndIncrement()+") "+ beanPrenotazione.toString()))
                        .collect(Collectors.toList()).get(view.fetchInt());
            }catch (IndexOutOfBoundsException e){
                e.printStackTrace();

            }
        }
    }

    /**
     * Enumera e mostra le possibili scelte.
     * @return la scelta
     */
    private Esperienza seleziona_esperienza() {
        while(true){
            try {
                view.message("=====ESPERIENZE DISPONIBILI=====");
                view.message("=====SELEZIONA INDICE=====");
                AtomicInteger contatore = new AtomicInteger(0);
                return Bacheca.getInstance()
                        .getEsperienze(Esperienza::isAvailable)
                        .stream()
                        .peek(esperienza -> view.message(contatore.getAndIncrement()+") " + esperienza.toString()))
                        .collect(Collectors.toList())
                        .get(view.fetchInt());
            }catch (IndexOutOfBoundsException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Enumera e mostra le possibili scelte.
     * @return la scelta
     */
    private BeanInvito seleziona_invito() {
        while(true){
            try {
                view.message("===== INVITI RICEVUTI=====");
                if(GestoreInviti.getInstance((Turista) utente).getRicevuti().isEmpty()) return null;
                view.message("=====SELEZIONA INVITO=====");
                AtomicInteger contatore = new AtomicInteger(0);
                return GestoreInviti.getInstance((Turista) utente)
                        .getRicevuti()
                        .stream()
                        .peek(invito -> view.message(contatore.getAndIncrement()+") " + invito.toString()))
                        .collect(Collectors.toList())
                        .get(view.fetchInt());
            }catch (IndexOutOfBoundsException e){
                e.printStackTrace();
            }
        }
    }



}
