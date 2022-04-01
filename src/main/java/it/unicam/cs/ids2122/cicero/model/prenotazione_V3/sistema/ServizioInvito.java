package it.unicam.cs.ids2122.cicero.model.prenotazione_V3.sistema;





import it.unicam.cs.ids2122.cicero.model.esperienza.Esperienza;
import it.unicam.cs.ids2122.cicero.model.prenotazione_V3.gestori.SinGestoreDisponibilita;
import it.unicam.cs.ids2122.cicero.model.prenotazione_V3.gestori.SinGestoreInvito;
import it.unicam.cs.ids2122.cicero.model.prenotazione_V3.invito.Invito;
import it.unicam.cs.ids2122.cicero.ruoli.IUtente;
import it.unicam.cs.ids2122.cicero.view.IView;

import java.sql.SQLException;
import java.util.Map;


public class ServizioInvito<I extends Invito> implements Service<I>{

    private IView<String> cliView;
    private IUtente utenteCorrente;
    private Map<String,Service> servizi;

    public ServizioInvito(IView iView , IUtente utente, Map<String,Service> servizi) throws SQLException {
        this.utenteCorrente = utente;
        cliView = iView;
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

            int postiDisponibili = SinGestoreDisponibilita.getInstance().getPostiDisponibiliToDB(esperienza.getId());
            if (posti <= postiDisponibili && posti > 0) {
                SinGestoreInvito.getInstance(utenteCorrente).crea_invito(esperienza, mail_invitato, posti);
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
                case "visualizza_inviti": SinGestoreInvito.getInstance(utenteCorrente).show();break;
                case "si":
                case "seleziona_invito":
                    try{
                       return (I) SinGestoreInvito.getInstance(utenteCorrente).select_invito(cliView.fetchInt());
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
