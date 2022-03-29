package it.unicam.cs.ids2122.cicero.model.prenotazione_v2.sistema;


import it.unicam.cs.ids2122.cicero.model.prenotazione_v2.FunPrenotazione;
import it.unicam.cs.ids2122.cicero.model.prenotazione_v2.Prenotazione;
import it.unicam.cs.ids2122.cicero.model.prenotazione_v2.PropPrenotazione;
import it.unicam.cs.ids2122.cicero.model.prenotazione_v2.StatoPrenotazione;
import it.unicam.cs.ids2122.cicero.model.prenotazione_v2.gestori.GestorePagamento;
import it.unicam.cs.ids2122.cicero.model.prenotazione_v2.gestori.GestorePrenotazione;
import it.unicam.cs.ids2122.cicero.model.prenotazione_v2.persistenza.DBManager;
import it.unicam.cs.ids2122.cicero.model.prenotazione_v2.utenti.Utente;
import it.unicam.cs.ids2122.cicero.view.IView;

import java.sql.SQLException;
import java.util.Map;

public class ServizioPagamento<P extends Prenotazione> implements Service<P> {

    private IView<String> cliView;
    private DBManager dbManager;
    private Utente utenteCorrente;
    private GestorePrenotazione gestorePrenotazione;
    private GestorePagamento gestorePagamento;
    private Map<String,Service> serviceMap;

    public  ServizioPagamento(IView iView, Utente utente, DBManager dbManager, Map<String, Service> mapServizi) throws SQLException {
        cliView = iView;
        this.dbManager = dbManager;
        this.utenteCorrente = utente;
        this.serviceMap = mapServizi;
        gestorePagamento = new GestorePagamento(utenteCorrente, this.dbManager);
        gestorePrenotazione = new GestorePrenotazione(utente, this.dbManager);
    }

    public void pagamento() throws SQLException {
        cliView.message("servizio di pagamento");
        if(gestorePrenotazione.getSize()>0){
              //Prenotazione prenotazione = seleziona_prenotazione();
              Prenotazione prenotazione = (Prenotazione) serviceMap.get("prenotazione").menu();
              if(prenotazione!=null && ((PropPrenotazione) prenotazione).getStatoPrenotazione().equals(StatoPrenotazione.RISERVATA)){
                  cliView.message("continuare e avviare il pagamento? [y/n] ");
                  if(cliView.fetchBool()){
                      gestorePagamento.crea_fattura(prenotazione);
                      ((FunPrenotazione) prenotazione).cambiaStatoPrenotazione(StatoPrenotazione.PAGATA);
                  }
              }cliView.message("errore nella selezione");
        }else
            cliView.message("nessuna prenotazione attiva");
    }


    @Override
    public P menu() throws SQLException {
        while(true) {
            cliView.message("menu pagamento");
            cliView.message("paga_prenotazione = pp ");
            cliView.message("richiesta_rimborso = rr ");
            cliView.message("stato_transazioni = st ");
            cliView.message("quit/q");
            switch (cliView.ask("inserire comando")) {

                case "pp":
                case "paga_prenotazione":
                    pagamento();
                    break;

                case "rr":
                case "richiesta_rimborso":  break;

                case "st":
                case "stato_transazioni":break;

                case "q":
                case "quit":
                    return null;
                default:
                    continue;
            }
        }
    }

    /*private Prenotazione seleziona_prenotazione(){
        while(true){
            cliView.message("menu selezione");
            cliView.message("show/s");
            cliView.message("seleziona indice -> i -> 'indice' ");
            cliView.message("quit/q");
            switch (cliView.ask("inserire comando")){
                case "q":
                case "quit": return null;
                case "s":
                case "show": gestorePrenotazione.show() ;break;
                case "i": {
                    try{
                        cliView.message("seleziona indice");
                        return gestorePrenotazione.select_prenotazione(cliView.fetchInt());
                    }catch (IndexOutOfBoundsException e){
                        cliView.message(e.getMessage());
                        break;
                    }
                } default:continue;
            }
        }
    }*/
}
