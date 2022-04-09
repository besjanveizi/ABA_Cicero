package it.unicam.cs.ids2122.cicero.model.prenotazione_V3.sistema;


import it.unicam.cs.ids2122.cicero.model.entities.esperienza.Esperienza;
import it.unicam.cs.ids2122.cicero.model.prenotazione_V3.Prenotazione;
import it.unicam.cs.ids2122.cicero.model.prenotazione_V3.gestori.*;
import it.unicam.cs.ids2122.cicero.model.prenotazione_V3.pagamento.Fattura;
import it.unicam.cs.ids2122.cicero.model.prenotazione_V3.pagamento.StatoPagamento;
import it.unicam.cs.ids2122.cicero.model.prenotazione_V3.persistenza.DBEsperienza;
import it.unicam.cs.ids2122.cicero.model.prenotazione_V3.rimborso.Rimborso;
import it.unicam.cs.ids2122.cicero.model.prenotazione_V3.rimborso.SimpleRimborso;
import it.unicam.cs.ids2122.cicero.persistence.DBManager;
import it.unicam.cs.ids2122.cicero.ruoli.IUtente;
import it.unicam.cs.ids2122.cicero.view.IView;

import java.sql.SQLException;
import java.util.Map;

public class ServizioRimborso<F extends Fattura> implements Service<F> {

    private IView<String> cliView;
    private IUtente utenteCorrente;
    private Map<String,Service> servizi;

    /**
     *
     * @param iView console
     * @param utente utente corrente
     * @param service servizio di supporto
     */
    public ServizioRimborso(IView iView, IUtente utente, Map<String,Service> service) {
        this.cliView = iView;
        this.utenteCorrente = utente;
        this.servizi =service;
    }

    private void chiedi_rimborso() throws SQLException {
        Fattura fattura = seleziona_fattura();
        if(fattura!=null
                && utenteCorrente.getID_Client().equals(fattura.getId_client_origine())
                && fattura.getStatoPagamento().equals(StatoPagamento.ADMIN_ADMIN)){

            /**
             * creo un rimborso
             */
            Rimborso rimborso = new SimpleRimborso();
            Prenotazione prenotazione = SinGestorePrenotazione.getInstance(utenteCorrente).getPrenotazione_by_Id(fattura.getId_prenotazione());

            String query = "select * from public.esperienze where id_esperienza=" +  prenotazione.getID_esperienza()+";";

            Esperienza esperienza = new DBEsperienza().crea_singola_esperienza(DBManager.getInstance().select_query(query));

            // verifico se è possibile
            if(rimborso.automatico(fattura, esperienza)){
               SinGestorePagamento.getInstance(utenteCorrente).crea_fattura(prenotazione);
            }


        }
    }


    private Fattura seleziona_fattura() throws SQLException {
        while (true) {
            cliView.message("menu selezione");
            cliView.message("show/s");
            cliView.message("seleziona indice pagamenti effettuati -> i -> 'indice' ");
            cliView.message("seleziona indice pagamenti ricevuti -> j -> 'indice' ");
            cliView.message("quit/q");
            switch (cliView.ask("inserire comando")) {
                case "q":
                case "quit":
                    return null;
                case "s":
                case "show":
                    SinGestorePrenotazione.getInstance(utenteCorrente).show();
                    break;
                case "i": {
                    try {
                        return SinGestorePagamento.getInstance(utenteCorrente).get_effettuati(cliView.fetchInt());
                    } catch (IndexOutOfBoundsException e) {
                        continue;
                    }
                }
                case "j": {
                    try {
                        return SinGestorePagamento.getInstance(utenteCorrente).get_ricevuti(cliView.fetchInt());
                    } catch (IndexOutOfBoundsException e) {
                        continue;
                    }
                }
                default:
                    continue;
            }
        }
    }


    @Override
    public F menu() throws SQLException {
        while(true){
            cliView.message("menu rimborso");
            cliView.message("chiedi_rimborso = cr");
            cliView.message("seleziona_fattura = sf");
            cliView.message("pagamenti_inviati = pi");
            cliView.message("show/s");
            cliView.message("quit/q");
            switch (cliView.ask("inserire comando")){
                case "s":
                case "show":SinGestorePagamento.getInstance(utenteCorrente).show(); break;
                case "sf":
                case "seleziona_fattura": seleziona_fattura();break;
                case "cr":
                case "chiedi_rimborso": chiedi_rimborso();break;
                case "q":
                case "quit": return null;
                default:continue;
            }
        }
    }


}
