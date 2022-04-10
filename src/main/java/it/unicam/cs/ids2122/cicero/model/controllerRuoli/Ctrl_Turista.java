package it.unicam.cs.ids2122.cicero.model.controllerRuoli;

import it.unicam.cs.ids2122.cicero.model.Bacheca;
import it.unicam.cs.ids2122.cicero.model.entities.esperienza.EsperienzaStatus;
import it.unicam.cs.ids2122.cicero.model.entities.esperienza.IEsperienza;
import it.unicam.cs.ids2122.cicero.model.prenotazione.bean.BeanFattura;
import it.unicam.cs.ids2122.cicero.model.prenotazione.bean.BeanInvito;
import it.unicam.cs.ids2122.cicero.model.prenotazione.bean.BeanPrenotazione;
import it.unicam.cs.ids2122.cicero.model.prenotazione.bean.StatoPrenotazione;
import it.unicam.cs.ids2122.cicero.model.gestori.GestoreInviti;
import it.unicam.cs.ids2122.cicero.model.gestori.GestorePagamenti;
import it.unicam.cs.ids2122.cicero.model.gestori.GestorePrenotazioni;
import it.unicam.cs.ids2122.cicero.ruoli.Turista;
import it.unicam.cs.ids2122.cicero.view.IView;


import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.util.stream.Collectors;

public class Ctrl_Turista extends Ctrl_UtenteAutenticato implements Ctrl_Utente {


    public Ctrl_Turista(IView<String> view, Turista turista) {
        super(view, turista);
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
                cancellaPrenotazione_riservata();
                break;
            case 9:
                cancellaPrenotazione_pagata();
                break;
            case 10:
                richiediRimborso(null);
                break;
            default:
                loop = super.switchMenu(scelta);
        }
        return loop;
    }


    private void richiediRimborso(BeanPrenotazione beanPrenotazione){
        if(beanPrenotazione== null) {
            beanPrenotazione = seleziona_prenotazione(StatoPrenotazione.PAGATA);
        }
        view.message(" elaborazione richiesta di rimborso... ");
            IEsperienza iEsperienza = null;
        final BeanPrenotazione finalBeanPrenotazione = beanPrenotazione;
        BeanFattura beanFattura = GestorePagamenti.getInstance((Turista) utente).getEffettuati()
                    .stream()
                    .filter(f-> f.getId_prenotazione() == finalBeanPrenotazione.getID_prenotazione()).findFirst().get();

           if(iEsperienza.info().getStatus().equals(EsperienzaStatus.IDLE) || iEsperienza.info().getStatus().equals(EsperienzaStatus.VALIDA) ){
               view.message("rimborso accettato");
               GestorePrenotazioni.getInstance((Turista) utente).modifica_stato(beanPrenotazione, StatoPrenotazione.CANCELLATA);
               GestorePagamenti.getInstance((Turista) utente).crea_fattura(beanFattura);
           }else{
               view.message("rimborso automatico rifiutato");
               view.message("richiedere rimborso manuale?");
               //...
           }

    }


    private void gestisciInvitiRicevuti() {
        view.message("accetta invito ");

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

    private void cancellaInvito(BeanInvito beanInvito) {
        if(beanInvito == null){
            beanInvito = seleziona_invito();
        }
        GestoreInviti.getInstance((Turista) utente).rifiuta_invito(beanInvito);
    }

    private void invitaEsperienza() {
       view.message("invita");

       IEsperienza esperienza = seleziona_esperienza();

       if(esperienza!=null){
           int posti = esperienza.info().getPostiDisponibili();

           view.message("posti disponibili:  " + posti);
           view.message("Continuare y/n ?");
           boolean continua= view.fetchBool();
           if(continua){
               String mail_invitato = view.ask("inserire mail invitato");
               view.message("inserire il numero di posti");
               int posti_inseriti = view.fetchInt();
               if(posti_inseriti>0 && posti_inseriti<= posti){
                   GestoreInviti.getInstance((Turista) utente).crea_invito(esperienza, mail_invitato, posti);
                   view.message("invio spedito");
               }
           }
       }

    }


    private void prenotaEsperienza() {
         IEsperienza esperienza = seleziona_esperienza();
            if (esperienza != null) {
                int posti = esperienza.info().getPostiDisponibili();
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
                        else view.message("riprova");
                    }

                    view.message("costo totale: " + esperienza.info().getCostoIndividuale().op_multi(String.valueOf(posti_scelti)));
                    view.message("confermare prenotazione [y/n]");

                    boolean flag = view.fetchBool();
                    if (flag) {
                        view.message("confermata, creazione prenotazione...");
                        GestorePrenotazioni.getInstance((Turista) utente).crea_prenotazione(esperienza, posti);
                    } else {
                        view.message("prenotazione annullata");
                        view.message("uscita");
                    }

                    view.message("pagare la prenotazione? [Y/N] ");
                    if(view.fetchBool())
                    {   BeanPrenotazione b = GestorePrenotazioni.getInstance((Turista) utente).getPrenotazione(esperienza.getId());
                        pagaPrenotazione(b);
                        view.message("pagamento riuscito, arrivederci e grazie");
                    }
                }
        }
    }

    private void pagaPrenotazione(BeanPrenotazione beanPrenotazione){
        if(beanPrenotazione == null){
             beanPrenotazione = seleziona_prenotazione(StatoPrenotazione.RISERVATA);
        }
            if (GestorePrenotazioni.getInstance((Turista) utente).getPrenotazioni().size() > 0){
                view.message(beanPrenotazione.toString());
                view.message(" avviare il pagamento? [y/n] ");
                if (view.fetchBool()) {
                    stub_mod_pagamento();
                    GestorePagamenti.getInstance((Turista) utente).crea_fattura(beanPrenotazione);
                }
            }view.message("nessuna prenotazione presente");
    }


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
        }

    }

    private void cancellaPrenotazione_riservata() {
        BeanPrenotazione ref = seleziona_prenotazione(StatoPrenotazione.RISERVATA);
            view.message(ref.toString());
            view.message("confermare y/n");
            boolean flag = view.fetchBool();
            if (flag) {
                GestorePrenotazioni.getInstance((Turista) utente).modifica_stato(ref,StatoPrenotazione.CANCELLATA);
                view.message("ok!");
            } else {
                view.message("la prenotazione non è stata cancellata");
                view.message("uscita");
            }
    }

    private void cancellaPrenotazione_pagata(){
        BeanPrenotazione ref = seleziona_prenotazione(StatoPrenotazione.PAGATA);
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

    private void impostaMenu() {
        menuItems.add("4) Prenota Esperienza");
        menuItems.add("5) Paga Prenotazione");
        menuItems.add("6) Invita ad una Esperienza");
        menuItems.add("7) Gestisci Inviti");
        menuItems.add("8) Annulla Prenotazione riservata");
        menuItems.add("9) Annulla Prenotazione pagata");

    }

    private BeanPrenotazione seleziona_prenotazione(StatoPrenotazione statoPrenotazione) {
        while(true){
            try {
                view.message("=====PRENOTAZIONI DISPONIBILI===== STATO --> "+ statoPrenotazione);
                GestorePrenotazioni.getInstance((Turista) utente).getPrenotazioni(statoPrenotazione);
                view.message("=====SELEZIONA INDICE======");
                return GestorePrenotazioni.getInstance((Turista) utente).getPrenotazioni().stream().collect(Collectors.toList()).get(view.fetchInt());
            }catch (IndexOutOfBoundsException e){
                e.printStackTrace();
            }
        }
    }


    private IEsperienza seleziona_esperienza() {
        while(true){
            try {
                view.message("=====ESPERIENZE DISPONIBILI=====");
                Bacheca.getInstance().getAllEsperienze().stream().forEach(esperienza -> view.message(esperienza.toString()));
                view.message("=====SELEZIONA INDICE=====");
                return Bacheca.getInstance().getAllIEsperienze().stream().collect(Collectors.toList()).get(view.fetchInt());
            }catch (IndexOutOfBoundsException e){
                e.printStackTrace();
            }
        }
    }

    private BeanInvito seleziona_invito() {
        while(true){
            try {
                view.message("===== INVITI RICEVUTI=====");
                GestoreInviti.getInstance((Turista) utente).getRicevuti().forEach(beanInvito -> view.message(beanInvito.toString()));
                view.message("=====SELEZIONA INVITO=====");
                return GestoreInviti.getInstance((Turista) utente).getRicevuti().stream().collect(Collectors.toList()).get(view.fetchInt());
            }catch (IndexOutOfBoundsException e){
                e.printStackTrace();
            }
        }
    }



}
