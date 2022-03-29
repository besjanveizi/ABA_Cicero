package it.unicam.cs.ids2122.cicero.model.prenotazione.sistema;




import it.unicam.cs.ids2122.cicero.model.prenotazione.esperienza.Esperienza;
import it.unicam.cs.ids2122.cicero.model.prenotazione.esperienza.PropEsperienza;
import it.unicam.cs.ids2122.cicero.model.prenotazione.gestori.GestoreDisponibilita;
import it.unicam.cs.ids2122.cicero.model.prenotazione.gestori.GestoreInviti;
import it.unicam.cs.ids2122.cicero.model.prenotazione.invito.Invito;
import it.unicam.cs.ids2122.cicero.model.prenotazione.persistenza.DBManager;
import it.unicam.cs.ids2122.cicero.model.prenotazione.utenti.Utente;
import it.unicam.cs.ids2122.cicero.view.IView;

import java.sql.SQLException;
import java.util.Map;


public class ServizioInvito<I extends Invito> implements Service<I>{

    private IView<String> cliView;
    private DBManager dbManager;
    private Utente utenteCorrente;
    private GestoreInviti gestoreInviti;
    private Map<String,Service> servizi;

    public ServizioInvito(IView iView ,Utente utente, DBManager dbManager,Map<String,Service> servizi) throws SQLException {
        this.utenteCorrente = utente;
        this.dbManager = dbManager;
        cliView = iView;
        this.gestoreInviti = new GestoreInviti(utenteCorrente, this.dbManager);
        gestoreInviti.show();
        this.servizi = servizi;
    }

    public void creaInvito() throws SQLException {
        cliView.message("menù invito");
        cliView.message("seleziona esperienza");
        Esperienza esperienza = (Esperienza) servizi.get("ricerca").menu();
        if(esperienza!=null) {

            String mail_invitato = cliView.ask("inserire mail invitato");
            cliView.message("inserire il numero di posti");
            int posti = cliView.fetchInt();
            cliView.message("verifica");
            GestoreDisponibilita gestoreDisponibilita = new GestoreDisponibilita(dbManager);
            int postiDisponibili = gestoreDisponibilita.getPostiDisponibiliToDB(((PropEsperienza) esperienza).getId());
            if (posti <= postiDisponibili && posti > 0) {

                GestoreInviti inviti = new GestoreInviti(utenteCorrente, dbManager);
                inviti.crea_invito(esperienza, mail_invitato, posti);
                cliView.message("operazione completata, invito inviato");

            } else {
                cliView.message("invio non spedito, controllare");
            }

        }
    }


    @Override
    public I menu() throws SQLException {
        while(true){
            cliView.message("menu inviti");
            cliView.message("visualizza_inviti = vi ");
            cliView.message("seleziona_invito = si ");
            cliView.message("crea_invito = ci ");
            cliView.ask("quit/q");
            switch (cliView.ask("inserire comando")){

                case "vi":
                case "visualizza_inviti": gestoreInviti.show();break;
                case "si":
                case "seleziona_invito":
                    try{
                       return (I) gestoreInviti.select_invito(cliView.fetchInt());
                    }catch (IndexOutOfBoundsException e){
                        continue;
                    }
                case "ci":
                case "crea_invito": creaInvito();break;
                case "q":
                case "quit": return null;
            }


        }
    }
}
