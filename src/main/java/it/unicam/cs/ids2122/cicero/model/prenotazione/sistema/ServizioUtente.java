package it.unicam.cs.ids2122.cicero.model.prenotazione.sistema;




import it.unicam.cs.ids2122.cicero.model.prenotazione.gestori.GestoreUtente;
import it.unicam.cs.ids2122.cicero.model.prenotazione.persistenza.DBManager;
import it.unicam.cs.ids2122.cicero.view.IView;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServizioUtente<Utente> implements Service<Utente> {

    private IView<String> cliView;
    private DBManager dbManager;


    public ServizioUtente(IView iView, DBManager dbManager) {
        this.cliView = iView;
        this.dbManager = dbManager;
    }

    public void registrazione() {
        GestoreUtente gestoreUtente = new GestoreUtente(dbManager);
        while (true) {
            cliView.message("fase di registrazione, digitare exit per uscire");

            String username = cliView.ask("inserire username");
            if (username.equals("exit")) break;

            String mail = cliView.ask("inserire mail");
            if (mail.equals("exit")) break;

            retry:{
                String pass = cliView.ask("inserire pass");
                String pass2 = cliView.ask("inserire nuovamente");
                if (pass.equals(pass2)) {
                    try{
                        gestoreUtente.sign_in(username, mail, pass);
                    }catch (SQLException e){
                        cliView.message("errore creazione");
                    }
                    cliView.message("utente creato con successo");
                    break ;
                } else {
                    cliView.ask("password non valida");
                    break retry;
                }
            }
            }
    }


    public Utente accedi() {
        GestoreUtente gestoreUtente = new GestoreUtente(dbManager);
        while (true) {
            cliView.message("fase di log in, digitare exit per uscire");
            String mail = cliView.ask("inserire mail");
            if (mail.equals("exit")) return null;
            String pass = cliView.ask("inserire pass");
            try {
                return (Utente) gestoreUtente.log_in(mail, pass);
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
                mail = null;
                pass = null;
            }

        }
    }


    @Override
    public Utente menu() {
        String text = null;
        while(true){
            cliView.message("cambiapassword");
            cliView.message("cambiamail");
            switch (cliView.ask("inserire ora")){
                case "cambiapassword": break;
                case "cambiamail": break;
            }
            break;
        }
        return null;
    }
}


