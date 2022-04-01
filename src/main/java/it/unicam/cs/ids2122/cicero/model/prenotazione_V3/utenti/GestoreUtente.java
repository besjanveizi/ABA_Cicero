package it.unicam.cs.ids2122.cicero.model.prenotazione_V3.utenti;


import it.unicam.cs.ids2122.cicero.model.prenotazione_V3.persistenza.DBManager;
import it.unicam.cs.ids2122.cicero.model.prenotazione_V3.persistenza.DBUtente;
import it.unicam.cs.ids2122.cicero.model.prenotazione_V3.utenti.Utente;
import it.unicam.cs.ids2122.cicero.model.prenotazione_V3.utenti.UtenteType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;



public  final class GestoreUtente {


    Utente utente_corrente;

    private final String insertQuery = "INSERT INTO public.utenti_registrati (username, email, password, user_type)	" +
            "VALUES ( {0}, {1}, {2}, {3} ) ;";


    private final String sql_select =  "SELECT * FROM public.utenti_registrati" +
            " WHERE email= {0} AND password= {1} ";


    public GestoreUtente(final DBManager dbManager) {

    }

    /**
     * verifica le informazioni e se corrette inserisce nel DB
     * @param username
     * @param email
     * @param password
     * @throws SQLException nel caso ci sia un problema nel DB o la mail o l' username sono già presenti
     */
    public  void sign_in(final String username,final String email, final String password) throws SQLException {
        Pattern pattern = Pattern.compile("\"^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\\\.[A-Z]{2,6}$\", Pattern.CASE_INSENSITIVE");
        if(pattern.matcher(email).matches()) {
            Logger.getAnonymousLogger().log(Level.WARNING, "email non valida -> "+email);
        }else {
           final Object[] token = {
                    "'" + username + "'",
                    "'" + email + "'",
                    "'" + Math.abs(password.hashCode()) + "'",
                    UtenteType.TURISTA.getCode()
            };
            final String format = MessageFormat.format(insertQuery, token);
            int id = it.unicam.cs.ids2122.cicero.persistence.DBManager.getInstance().insert_update_delete_query(format);
            if (id == -1) {
                Logger.getAnonymousLogger().log(Level.WARNING, "username o email non valide");
                throw new SQLException();
            }else{
                Logger.getAnonymousLogger().log(Level.INFO, "id generato: "+id);
            }
        }
    }

    /**
     *
     * @param mail
     * @param pass
     * @return
     * @throws SQLException
     */
    public Utente log_in(String mail, String pass) throws NullPointerException, SQLException {
        final String hash_pass = String.valueOf(Math.abs(pass.hashCode()));
        final Object[] token = {"'"+mail+"'" , "'"+hash_pass+"';" };
        final String format = MessageFormat.format(sql_select, token);
        ResultSet resultSet = it.unicam.cs.ids2122.cicero.persistence.DBManager.getInstance().select_query(format);

        if(resultSet!=null) {
            utente_corrente = new DBUtente().genera(resultSet);
        }
        if(utente_corrente == null) {
            throw new NullPointerException();
        }
        else return utente_corrente;
    }

    public void log_out(){
        utente_corrente = null;
    }


    public void upgrade_to_cicero() throws SQLException {
       final String sql_update = "UPDATE public.utenti_registrati SET user_type= {0} WHERE uid= {1} ;";
       final Object[] token = { 1 ,utente_corrente.getID()};
       final String sql_format = MessageFormat.format(sql_update, token);
       it.unicam.cs.ids2122.cicero.persistence.DBManager.getInstance().insert_update_delete_query(sql_format);
    }

    public void cambia_mail(String vecchia_mail,String nuova_mail,String pass){

    }

    public void cambia_pass(String vecchia, String nuova){

    }



    public void show() {
        Logger.getAnonymousLogger().log(Level.INFO, utente_corrente.toString());
    }


}
