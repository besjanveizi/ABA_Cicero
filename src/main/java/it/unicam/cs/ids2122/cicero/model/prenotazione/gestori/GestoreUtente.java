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

public  final class GestoreUtente extends AbstractGestore {

    private final String insertQuery = "INSERT INTO public.utentiregistrati (username, email, password, user_type)	" +
            "VALUES ( {0}, {1}, {2}, {3} ) ;";


    private final String sql_select =  "SELECT * FROM public.utentiregistrati WHERE email= {0} AND password= {1} ;";


    public GestoreUtente(final DBManager dbManager) {
        super(dbManager);
    }

    public  void sign_in(final String username,final String email, final String password) throws SQLException {
        Pattern pattern = Pattern.compile("\"^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\\\.[A-Z]{2,6}$\", Pattern.CASE_INSENSITIVE");
        if(pattern.matcher(email).matches()) {
            Logger.getAnonymousLogger().log(Level.WARNING, "email non valida -> "+email);
        }else {
           final Object[] token = {
                    "'" + username + "'",
                    "'" + email + "'",
                    "'" + Math.abs(password.hashCode()) + "'",
                    UtenteType.TURISTA.getI()
            };
            final String format = MessageFormat.format(insertQuery, token);
            if (dbManager.insert_update_delete_query(format) == -1) {
                Logger.getAnonymousLogger().log(Level.WARNING, "username o email non valide");
                throw new SQLException();
            }
        }
    }

    public Utente log_in(String mail, String pass) throws  SQLException {

        final Object[] token = {"'"+mail+"'", "'"+pass.hashCode()+"'" };
        final String format = MessageFormat.format(sql_select, token);
        ResultSet resultSet = dbManager.select_query(format);

        if(resultSet!=null && resultSet.next()) {

            Utente utente = new SimpleUser(resultSet.getInt("uid"),
                    tipoUtente(resultSet.getInt("user_type")),
                    resultSet.getString("email"), resultSet.getString("username"));
            return utente;
        }else {

            throw new SQLException();
        }
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
