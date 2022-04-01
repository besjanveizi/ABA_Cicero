package it.unicam.cs.ids2122.cicero.model.prenotazione_V3.sistema;



import it.unicam.cs.ids2122.cicero.model.prenotazione_V3.Prenotazione;
import it.unicam.cs.ids2122.cicero.model.prenotazione_V3.StatoPrenotazione;
import it.unicam.cs.ids2122.cicero.model.prenotazione_V3.gestori.SinGestorePagamento;
import it.unicam.cs.ids2122.cicero.model.prenotazione_V3.gestori.SinGestorePrenotazione;
import it.unicam.cs.ids2122.cicero.ruoli.IUtente;
import it.unicam.cs.ids2122.cicero.view.IView;

import java.sql.SQLException;
import java.util.Map;

public class ServizioPagamento<P extends Prenotazione> implements Service<P> {

    private IView<String> cliView;
    private IUtente utenteCorrente;

    private Map<String,Service> serviceMap;

    public  ServizioPagamento(IView iView, IUtente utente, Map<String, Service> mapServizi) throws SQLException {
        cliView = iView;
        this.utenteCorrente = utente;
        this.serviceMap = mapServizi;
    }

    public void pagamento() throws SQLException {
        cliView.message("servizio di pagamento");

        if(SinGestorePrenotazione.getInstance(utenteCorrente).getSize()>0){

              Prenotazione prenotazione = (Prenotazione) serviceMap.get("prenotazione").menu();

              if(prenotazione!=null &&  prenotazione.getStatoPrenotazione().equals(StatoPrenotazione.RISERVATA)){
                  cliView.message("continuare e avviare il pagamento? [y/n] ");
                  if(cliView.fetchBool()){
                     SinGestorePagamento.getInstance(utenteCorrente).crea_fattura(prenotazione);
                  }
              }cliView.message("errore nella selezione");
        }else
            cliView.message("nessuna prenotazione attiva");
    }

    private void richiesta_rimborso() throws SQLException {
           serviceMap.get("rimborso").menu();
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
                case "richiesta_rimborso":
                    richiesta_rimborso();
                    break;

                case "st":
                case "stato_transazioni":
                    SinGestorePagamento.getInstance(utenteCorrente).show();
                    break;

                case "q":
                case "quit":
                    return null;
                default:
                    continue;
            }
        }
    }

}
