package it.unicam.cs.ids2122.cicero.model.prenotazione.gestori;


import it.unicam.cs.ids2122.cicero.model.prenotazione.persistenza.DBManager;
import it.unicam.cs.ids2122.cicero.model.prenotazione.utenti.SimpleUser;
import it.unicam.cs.ids2122.cicero.model.prenotazione.utenti.Utente;
import it.unicam.cs.ids2122.cicero.model.prenotazione.utenti.UtenteType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class GestoreUtente extends AbstractGestore {

    private String insertQuery = "INSERT INTO public.utenti_registrati (username, email, password, user_type)	" +
            "VALUES ( {0}, {1}, {2}, {3} ) ;";


    private String sql_select =  "SELECT * FROM public.utenti_registrati WHERE email= {0} AND password= {1} ;";



    public GestoreUtente(DBManager dbManager) {
        super(dbManager);
    }

    public void sign_in(String username, String email, String password){
        final String pattern = "[^@ \\t\\r\\n]+@[^@ \\t\\r\\n]+\\.[^@ \\t\\r\\n]+";
        if(Pattern.compile(pattern).matcher(email).matches()) {
            Logger.getAnonymousLogger().log(Level.WARNING, "email non valida");
        }else {
            Object[] token = {
                    "'" + username + "'",
                    "'" + email + "'",
                    "'" + Math.abs(password.hashCode()) + "'",
                    UtenteType.TURISTA.getI()
            };
            String format = MessageFormat.format(insertQuery, token);
            if (dbManager.insert_update_delete_query(format) == -1) {
                Logger.getAnonymousLogger().log(Level.WARNING, "username o email non valide");
            }
        }
    }

    public Utente log_in(String mail, String pass) throws  SQLException {
        Object[] token = {"'"+mail+"'", "'"+pass.hashCode()+"'" };
        String format = MessageFormat.format(sql_select, token);
        ResultSet resultSet = dbManager.select_query(format);
        if(resultSet!=null && resultSet.next()) {
            Utente utente = new SimpleUser(resultSet.getInt("uid"),
                    tipoUtente(resultSet.getInt("user_type")),
                    resultSet.getString("email"), resultSet.getString("id_client"));
            return utente;
        }
        return null;
    }

    public void log_out(){
        utente_corrente = null;
    }


    public void upgrade_to_cicero(){
       String sql_update = "UPDATE public.utenti SET tipo_utente= {0} WHERE uid= {2}";
       Object[] token = { 1,super.utente_corrente.getID()+"'"};
       String sql_format = MessageFormat.format(sql_update, token);
       dbManager.insert_update_delete_query(sql_format);
    }

    public void cambia_mail(String vecchia_mail,String nuova_mail,String pass){

    }

    public void cambia_pass(String vecchia, String nuova){

    }

    private UtenteType tipoUtente(int tipo){
        switch (tipo){
            case 1 : return UtenteType.CICERONE;
            case 2: return UtenteType.TURISTA;
            case 0: return UtenteType.ADMIN;
            default: return null;
        }
    }

    @Override
    public void show() {
        Logger.getAnonymousLogger().log(Level.INFO, utente_corrente.toString());
    }


}
