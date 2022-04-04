package it.unicam.cs.ids2122.cicero.model.service.controllerRuoli;

import it.unicam.cs.ids2122.cicero.model.Bacheca;
import it.unicam.cs.ids2122.cicero.model.esperienza.Esperienza;
import it.unicam.cs.ids2122.cicero.model.esperienza.IEsperienza;
import it.unicam.cs.ids2122.cicero.model.prenotazione.bean.BeanInvito;
import it.unicam.cs.ids2122.cicero.model.prenotazione.bean.BeanPrenotazione;
import it.unicam.cs.ids2122.cicero.model.prenotazione.bean.StatoPrenotazione;
import it.unicam.cs.ids2122.cicero.model.prenotazione.gestori.SinGestoreDisponibilita;
import it.unicam.cs.ids2122.cicero.model.prenotazione.gestori.SinGestoreInvito;
import it.unicam.cs.ids2122.cicero.model.prenotazione.gestori.SinGestorePagamento;
import it.unicam.cs.ids2122.cicero.model.prenotazione.gestori.SinGestorePrenotazione;
import it.unicam.cs.ids2122.cicero.model.prenotazione.sistema.*;
import it.unicam.cs.ids2122.cicero.ruoli.Turista;
import it.unicam.cs.ids2122.cicero.view.IView;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Ctrl_Turista extends Ctrl_UtenteAutenticato implements Ctrl_Utente {


    public Ctrl_Turista(IView<String> view, Turista turista) {
        super(view, turista);
        //carico i servizi
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
                pagaPrenotazione();
                break;
            case 6:
                invitaEsperienza();
                break;
            case 7:
                gestisciInvitiRicevuti();
                break;
            default:
                loop = super.switchMenu(scelta);
        }
        return loop;
    }

    private void gestisciInvitiRicevuti() {
        view.message("accetta invito ");

        BeanInvito beanInvito = seleziona_invito();
        if(beanInvito!= null){
            view.message("accetta invito? [y/n] ");
            boolean accetta = view.fetchBool();
            if(accetta){
                SinGestorePrenotazione.getInstance(utente).crea_prenotazione(beanInvito);
                view.message("prenotazione effettuata");
            }
            view.message("prenotazione non effettuata");
        }
    }

    private void invitaEsperienza() {
       view.message("invita");

       Esperienza esperienza = seleziona_esperienza();

       if(esperienza!=null){

           int posti = 0;
         //int posti_disponibili = SinGestoreDisponibilita.getInstance().getPostiDisponibiliToDB(esperienza);

           view.message("posti disponibili:  " + posti);
           view.message("Continuare y/n ?");
           boolean continua= view.fetchBool();
           if(continua){
               String mail_invitato = view.ask("inserire mail invitato");
               view.message("inserire il numero di posti");
               int posti_inseriti = view.fetchInt();
               if(posti_inseriti>0 && posti_inseriti<= posti){
                //   SinGestoreInvito.getInstance(utente).crea_invito(esperienza, mail_invitato, posti);
                //   SinGestoreDisponibilita.getInstance().modificaDisponibilita(esperienza, posti_inseriti);
                   view.message("invio spedito");
               }
           }
       }

    }

    private BeanInvito seleziona_invito() {
        while(true){
            try {
                view.message("===== INVITI RICEVUTI=====");
                SinGestoreInvito.getInstance(utente).getRicevuti().forEach( beanInvito -> view.message(beanInvito.toString()));
                view.message("=====SELEZIONA INVITO=====");
                return SinGestoreInvito.getInstance(utente).getRicevuti().stream().collect(Collectors.toList()).get(view.fetchInt());
            }catch (IndexOutOfBoundsException e){
                e.printStackTrace();
            }
        }
    }

    private void prenotaEsperienza() {

        while(true) {

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
                        else view.message("riprova");
                    }

                    view.message("costo totale: " + esperienza.getCostoIndividuale().op_multi(String.valueOf(posti_scelti)));
                    view.message("confermare prenotazione [y/n]");
                    boolean flag = view.fetchBool();
                    if (flag) {
                        view.message("confermata, creazione prenotazione...");
                        // SinGestorePrenotazione.getInstance().crea_prenotazione(esperienza, posti);
                        //  SinGestoreDisponibilita.getInstance().modificaDisponibilita(esp_selezionata, posti);
                    } else {
                        view.message("prenotazione annullata");
                        view.message("uscita");
                        break;
                    }
                }
            }
            //fine loop
        }
    }

    private Esperienza seleziona_esperienza() {
        while(true){
            try {
               view.message("=====ESPERIENZE DISPONIBILI=====");
               Bacheca.getInstance().getAllEsperienze().stream().forEach(esperienza -> view.message(esperienza.getDescrizione()));
               view.message("=====SELEZIONA INDICE=====");
               return Bacheca.getInstance().getAllEsperienze().stream().collect(Collectors.toList()).get(view.fetchInt());
            }catch (IndexOutOfBoundsException e){
                e.printStackTrace();
            }
        }
    }



    private void pagaPrenotazione() {
        view.message("pagamento prenotazione");
        // ci sono prenotazioni presenti?
        if(SinGestorePrenotazione.getInstance(utente).getPrenotazioni().size()>0) {

            BeanPrenotazione beanPrenotazione = seleziona_prenotazione();
                if (beanPrenotazione!=null && beanPrenotazione.getStatoPrenotazione().equals(StatoPrenotazione.RISERVATA)) {
                    view.message("continuare e avviare il pagamento? [y/n] ");
                    if (view.fetchBool()) {
                        SinGestorePagamento.getInstance(utente).crea_fattura(beanPrenotazione);
                    }
                }
                view.message("errore nella selezione");
            } else
                view.message("nessuna prenotazione attiva");
    }

    private BeanPrenotazione seleziona_prenotazione() {
        while(true){
            try {
                view.message("=====PRENOTAZIONI DISPONIBILI=====");
                SinGestorePrenotazione.getInstance(utente).getPrenotazioni().stream().forEach(beanPrenotazione -> view.message(beanPrenotazione.toString()));
                view.message("=====SELEZIONA INDICE======");
                return SinGestorePrenotazione.getInstance(utente).getPrenotazioni().stream().collect(Collectors.toList()).get(view.fetchInt());
            }catch (IndexOutOfBoundsException e){
                e.printStackTrace();
            }
        }
    }


    private void impostaMenu() {
        menuItems.add("4) Prenota Esperienza");
        menuItems.add("5) Paga Prenotazione");
        menuItems.add("6) Invita ad una Esperienza");
        menuItems.add("7) Gestisci Inviti");
    }



}
