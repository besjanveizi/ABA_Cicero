package it.unicam.cs.ids2122.cicero.model.prenotazione_V3.sistema;




import it.unicam.cs.ids2122.cicero.model.entities.esperienza.Esperienza;
import it.unicam.cs.ids2122.cicero.model.prenotazione_V3.Prenotazione;
import it.unicam.cs.ids2122.cicero.model.prenotazione_V3.StatoPrenotazione;
import it.unicam.cs.ids2122.cicero.model.prenotazione_V3.gestori.SinGestoreDisponibilita;
import it.unicam.cs.ids2122.cicero.model.prenotazione_V3.gestori.SinGestorePrenotazione;
import it.unicam.cs.ids2122.cicero.model.prenotazione_V3.invito.Invito;
import it.unicam.cs.ids2122.cicero.ruoli.IUtente;
import it.unicam.cs.ids2122.cicero.view.IView;

import java.sql.SQLException;
import java.util.Map;


public class ServizioPrenotazione<P extends Prenotazione> implements Service<P> {

    private IView<String> cliView;
    private IUtente utenteCorrente;
    private Map<String,Service> servizi;

    /**
     *
     * @param iView console
     * @param utente utente corrente
     * @param service servizio di supporto
     */
    public ServizioPrenotazione(IView iView, IUtente utente, Map<String,Service> service) {
        this.cliView = iView;
        this.servizi =service;
        this.utenteCorrente = utente;
    }


    /**
     * Aziona il servizio di supporto per la scelta dell' esperienza
     * Richiede il numero di posti, dopo aver prelevato i posti disponibili,
     * calcola il totale, lo mostra e chiede conferma.
     * Ottenuta la conferma si cerca di generare la prenotazione.
     * Nel caso la data di creazione non sia corretta lancia un eccezione.
     *
     * @throws SQLException
     */
    private void prenotazione() throws SQLException {

        Esperienza esp_selezionata =  (Esperienza) servizi.get("ricerca").menu();
        if(esp_selezionata==null){
            cliView.message("esperienza non selezionata");
            return;
        }

        cliView.message("posti attualmente disponibili: ");
        int posti = esp_selezionata.getPostiDisponibili();
        String  posti_disponibili = String.valueOf((esp_selezionata.getPostiDisponibili()));
        cliView.message(posti_disponibili);

        if(posti<0){
            cliView.message("posti non disponibili ");
            cliView.message("scegli nuova esperienza");
            return;
        }

        int posti_scelti = 0;
        while(true){
            cliView.message("inserire posti da riservare");
            posti_scelti = cliView.fetchInt();
            if(posti_scelti>0 && posti_scelti<=posti)break;
            else cliView.message("riprova");
        }

        cliView.message("costo totale: "+ esp_selezionata.getCostoIndividuale().op_multi(String.valueOf(posti_scelti)));
        cliView.message("confermare prenotazione [y/n]");
        boolean flag = cliView.fetchBool();
        if(flag){
            cliView.message("confermata, creazione prenotazione...");
            SinGestorePrenotazione.getInstance(utenteCorrente).crea_prenotazione(esp_selezionata, posti_scelti);
            SinGestoreDisponibilita.getInstance().modificaDisponibilita(esp_selezionata, posti);
        }else  cliView.message("prenotazione annullata");
        cliView.message("uscita");
    }



    private void cancella_prenotazione() throws SQLException {
        cliView.message("cancella prenotazione non pagata");
        SinGestorePrenotazione.getInstance(utenteCorrente).carica();
        Prenotazione ref = seleziona_prenotazione();

        if(ref==null){cliView.message("prenotazione non selezionata");return;}
        if(!ref.getStatoPrenotazione().equals(StatoPrenotazione.RISERVATA)){
            cliView.message("prenotazione non valida per la cancellazione");
            return;
        }

            cliView.message(ref.toString());
            cliView.message("confermare y/n");
            boolean flag = cliView.fetchBool();
                if (flag) {
                    SinGestorePrenotazione.getInstance(utenteCorrente).modifica_stato(ref,StatoPrenotazione.CANCELLATA);
                    SinGestoreDisponibilita.getInstance().modificaDisponibilita(ref);
                    cliView.message("ok!");
                } else {
                    cliView.message("la prenotazione non è stata cancellata");
                    cliView.message("uscita");
                }
    }

    private void accetta_invito() throws SQLException {
        Invito invito = (Invito) servizi.get("inviti").menu();
        if(invito!= null){ cliView.message("invito non selezionato");return;}

        cliView.message("info invito");
        cliView.message(invito.toString());
        cliView.message("accetta invito? [y/n] ");
        boolean accetta = cliView.fetchBool();
        if(accetta){
                SinGestorePrenotazione.getInstance(utenteCorrente).crea_prenotazione(invito);
                cliView.message("prenotazione effettuata");
        }
        cliView.message("prenotazione non effettuata");
    }


    private Prenotazione seleziona_prenotazione() throws SQLException {
        while(true){
            cliView.message("menu selezione");
            cliView.message("show/s");
            cliView.message("seleziona indice -> i -> 'indice' ");
            cliView.message("quit/q");
            switch (cliView.ask("inserire comando")){
                case "q":
                case "quit": return null;
                case "s":
                case "show": SinGestorePrenotazione.getInstance(utenteCorrente).show();break;
                case "i": {
                    try{
                        return SinGestorePrenotazione.getInstance(utenteCorrente).select_prenotazione(cliView.fetchInt());
                    }catch (IndexOutOfBoundsException e){
                        continue;
                    }
                }
            }
        }

    }


    @Override
    public P menu() throws SQLException {
        while(true){
            cliView.message("menu prenotazione quit/q per uscire");
            cliView.message("prenota = p");
            cliView.message("cancella_prenotazione = cp");
            cliView.message("seleziona_prenotazione = sp");
            cliView.message("accetta_invito = ac");
            switch (cliView.ask("inserire comando")){
                case "p":
                case "prenota": prenotazione();break;
                case "cp":
                case "cancella_prenotazione": cancella_prenotazione();break;
                case "sp":
                case "seleziona_prenotazione" : return (P) seleziona_prenotazione();
                case "ac":
                case "accetta_invito": accetta_invito();break;
                case "q":
                case "quit": return null;
            }
        }
    }

}
