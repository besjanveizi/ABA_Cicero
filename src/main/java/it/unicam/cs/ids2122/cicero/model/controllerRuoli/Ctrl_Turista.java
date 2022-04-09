package it.unicam.cs.ids2122.cicero.model.controllerRuoli;

import com.google.common.collect.Sets;
import it.unicam.cs.ids2122.cicero.model.Bacheca;
import it.unicam.cs.ids2122.cicero.model.entities.esperienza.IEsperienza;
import it.unicam.cs.ids2122.cicero.model.prenotazione.bean.*;
import it.unicam.cs.ids2122.cicero.model.prenotazione.gestori.SinGestoreDisponibilita;
import it.unicam.cs.ids2122.cicero.model.prenotazione.gestori.SinGestoreInvito;
import it.unicam.cs.ids2122.cicero.model.prenotazione.gestori.SinGestorePagamento;
import it.unicam.cs.ids2122.cicero.model.prenotazione.gestori.SinGestorePrenotazione;
import it.unicam.cs.ids2122.cicero.ruoli.Turista;
import it.unicam.cs.ids2122.cicero.view.IView;

import java.util.stream.Collectors;

public class Ctrl_Turista extends Ctrl_UtenteAutenticato implements Ctrl_Utente {

    Turista utente;

    public Ctrl_Turista(IView<String> view, Turista turista) {
        super(view, turista);
        this.utente = turista;
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
            case 8:
                cancellaPrenotazione();
                break;
            case 9:
                richiediRimborso();
                break;
            default:
                loop = super.switchMenu(scelta);
        }
        return loop;
    }

    private void richiediRimborso() {
        view.message("richiesta di rimborso");

        BeanFattura beanFattura = seleziona_pagamento_effettuato();
        if(beanFattura != null){
            // chiedi se vuole verificare le opzioni

            // prima opzione se è automatico -> poi chiede se lo vuole richiedere-> se si nuova fattura
            // quindi serve, prima recuperare la prenotazione, e posso usare il gestore
            BeanPrenotazione beanPrenotazione = Sets.filter(SinGestorePrenotazione.getInstance(utente).getPrenotazioni(),
                    input -> input.getID_prenotazione()==beanFattura.getId_prenotazione()).stream().collect(Collectors.toList()).get(0);
            //dalla prenotazione all' esperienza relativa
            //dall' esperienza alla data di inizio
            BeanRimborso beanRimborso = new BeanRimborso();
            //  beanRimborso.automatico(beanFattura, );

            //se accetta devi creare una nova fattura, da fattura

            // seconda opzione compila modulo per la richiesta -> fase di compilazione un semplice testo
        }

    }

    private BeanFattura seleziona_pagamento_effettuato() {
        while(true){
            try {
                view.message("===== INVITI RICEVUTI=====");
                SinGestorePagamento.getInstance(utente).getEffettuati().forEach(beanF-> view.message(beanF.toString()));
                view.message("=====SELEZIONA INVITO=====");
                return SinGestorePagamento.getInstance(utente).getEffettuati().stream().collect(Collectors.toList()).get(view.fetchInt());
            }catch (IndexOutOfBoundsException e){
                e.printStackTrace();
            }
        }
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

        IEsperienza esperienza = seleziona_esperienza();

        if(esperienza!=null){
            int posti = SinGestoreDisponibilita.getInstance().getPostiDisponibiliToDB(esperienza);

            view.message("posti disponibili:  " + posti);
            view.message("Continuare y/n ?");
            boolean continua= view.fetchBool();
            if(continua){
                String mail_invitato = view.ask("inserire mail invitato");
                view.message("inserire il numero di posti");
                int posti_inseriti = view.fetchInt();
                if(posti_inseriti>0 && posti_inseriti<= posti){
                    SinGestoreInvito.getInstance(utente).crea_invito(esperienza, mail_invitato, posti);
                    SinGestoreDisponibilita.getInstance().modificaDisponibilita(esperienza, posti_inseriti);
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
                    SinGestorePrenotazione.getInstance(utente).crea_prenotazione(esperienza, posti);
                    SinGestoreDisponibilita.getInstance().modificaDisponibilita(esperienza, posti);
                } else {
                    view.message("prenotazione annullata");
                    view.message("uscita");
                }
            }
        }
    }

    private IEsperienza seleziona_esperienza() {
        while(true){
            try {
                view.message("=====ESPERIENZE DISPONIBILI=====");
                Bacheca.getInstance().getAllEsperienze().stream().forEach(esperienza -> view.message(esperienza.getDescrizione()));
                view.message("=====SELEZIONA INDICE=====");
                return Bacheca.getInstance().getAllIEsperienze().stream().collect(Collectors.toList()).get(view.fetchInt());
            }catch (IndexOutOfBoundsException e){
                e.printStackTrace();
            }
        }
    }


    private void pagaPrenotazione() {
        view.message("pagamento prenotazione");

        if(SinGestorePrenotazione.getInstance(utente).getPrenotazioni().size()>0) {

            BeanPrenotazione beanPrenotazione = seleziona_prenotazione();
            if (beanPrenotazione!=null && beanPrenotazione.getStatoPrenotazione().equals(StatoPrenotazione.RISERVATA)) {
                view.message("continuare e avviare il pagamento? [y/n] ");
                if (view.fetchBool()) {
                    SinGestorePagamento.getInstance(utente).crea_fattura(beanPrenotazione);
                }
            }
            view.message("errore nella selezione, solo esperienze riservate");
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

    private void cancellaPrenotazione() {
        view.message("annulla prenotazione");

        BeanPrenotazione ref = seleziona_prenotazione();

        if(ref.getStatoPrenotazione().equals(StatoPrenotazione.RISERVATA)){
            view.message("confermare y/n");
            boolean flag = view.fetchBool();

            if (flag) {
                SinGestorePrenotazione.getInstance(utente).modifica_stato(ref,StatoPrenotazione.CANCELLATA);
                SinGestoreDisponibilita.getInstance().modificaDisponibilita(ref);
                view.message("ok!");
            } else {
                view.message("la prenotazione non è stata cancellata");
                view.message("uscita");
            }
        }
        view.message("errore nella selezione");

    }


    private void impostaMenu() {
        menuItems.add("4) Prenota Esperienza");
        menuItems.add("5) Paga Prenotazione");
        menuItems.add("6) Invita ad una Esperienza");
        menuItems.add("7) Gestisci Inviti");
        menuItems.add("8) Annulla Prenotazione");
        menuItems.add("9) Richiedi Rimborso");
    }



}