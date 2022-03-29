package it.unicam.cs.ids2122.cicero.model.prenotazione_v2.sistema;



import it.unicam.cs.ids2122.cicero.model.prenotazione_v2.SystemConstraints;
import it.unicam.cs.ids2122.cicero.model.prenotazione_v2.persistenza.DBManager;
import it.unicam.cs.ids2122.cicero.model.prenotazione_v2.persistenza.PostgresDB;
import it.unicam.cs.ids2122.cicero.model.prenotazione_v2.utenti.Utente;
import it.unicam.cs.ids2122.cicero.model.prenotazione_v2.utenti.UtenteType;
import it.unicam.cs.ids2122.cicero.view.CLIView;
import it.unicam.cs.ids2122.cicero.view.IView;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;


public class ControllerSistema {


    private DBManager dbManager;
    private IView<String> iView;
    private Map<String, Service> mapServizi;
    private Utente utente;


    public ControllerSistema() throws SQLException {
        iView = new CLIView();
        iView.message("init...");
        dbManager = new PostgresDB(SystemConstraints.DBUSER,SystemConstraints.DBPASS);
        mapServizi = new LinkedHashMap<>();
        iView.message("done");
    }

    /**
     * main loop
     */
    public void menu() throws SQLException {
        while(true){
            iView.message("menu di sistema");
            iView.message("free = f");
            iView.message("registrati = r");
            iView.message("accedi = a");
            iView.message("quit/q");
            switch (iView.ask("inserire comando")){
                case "q":
                case "quit" : System.exit(0);
                case "f":
                case "free" : init_servizi_liberi(); menuServizi(); break;
                case "r":
                case "registrati" : new ServizioUtente<>(iView,dbManager).registrazione(); break;
                case "a":
                case "accedi": utente = (Utente) new ServizioUtente<>(iView,dbManager).accedi();
                default: if(utente!=null){
                    if(utente.getType().equals(UtenteType.CICERONE)){
                        init_servizi_turista();
                        init_servizio_cicero();
                        menuServizi();
                    }
                    if(utente.getType().equals(UtenteType.TURISTA)){
                        init_servizi_turista();
                        menuServizi();
                    }
                    if(utente.getType().equals(UtenteType.ADMIN)){

                    }
                }break;
            }
        }
    }

    private void menuServizi() {
        iView.message("digitare il nome del servizio per accedere alle funzionalità");
        iView.message("0 per uscire");
        String nomeServizio;

        while (true) {
            mapServizi.forEach((ss, service) -> System.out.println(ss));
            nomeServizio = iView.ask("digitare nome servizio");
            try{
                mapServizi.get(nomeServizio).menu();
            }catch (ClassCastException | NullPointerException | SQLException asd){
                asd.printStackTrace();
            }
        }
    }


    private void init_servizi_liberi() throws SQLException {
        ServizioRicerca servizioRicerca = new ServizioRicerca(iView,dbManager);
        mapServizi.put("ricerca", servizioRicerca);
    }


    private void init_servizi_turista(){
        ServizioUtente servizioUtente = new ServizioUtente(iView, dbManager);
        mapServizi.put("utente", servizioUtente);
        try {
            ServizioPrenotazione servizioPrenotazione = new ServizioPrenotazione(iView,utente,dbManager,mapServizi);
            mapServizi.put("prenotazione",servizioPrenotazione);
            ServizioPagamento servizioPagamento = new ServizioPagamento(iView, utente, dbManager,mapServizi);
            mapServizi.put("pagamento", servizioPagamento);
            ServizioInvito servizioInvito = new ServizioInvito(iView,utente , dbManager,mapServizi);
            mapServizi.put("inviti", servizioInvito);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void init_servizio_cicero() {

    }

    private void init_servizio_amm(){

    }

}
