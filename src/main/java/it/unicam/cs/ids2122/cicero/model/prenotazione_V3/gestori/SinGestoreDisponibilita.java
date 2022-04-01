package it.unicam.cs.ids2122.cicero.model.prenotazione_V3.gestori;


import it.unicam.cs.ids2122.cicero.model.esperienza.Esperienza;
import it.unicam.cs.ids2122.cicero.model.prenotazione_V3.Prenotazione;
import it.unicam.cs.ids2122.cicero.persistence.DBManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;

public class SinGestoreDisponibilita {

    private static SinGestoreDisponibilita sinGestoreDisponibilita = null;

    private DBManager dbManager = DBManager.getInstance();

    private String sql_update = "UPDATE public.esperienze SET posti_disponibili= {0} WHERE id_esperienza= {1} ;";

    private String sql_select = "SELECT posti_disponibili FROM public.esperienze WHERE id_esperienza=";


    private SinGestoreDisponibilita() throws SQLException {

    }

    public static SinGestoreDisponibilita getInstance() throws SQLException {
        if(sinGestoreDisponibilita==null){
            return new SinGestoreDisponibilita();
        }return sinGestoreDisponibilita;
    }


    /**
     * In caso di annullamento di una prenotazione, restituisce all' esperienza i posti disponibili
     * @param prenotazione la prenotazione da annullare
     * @throws SQLException
     */
    public void modificaDisponibilita(Prenotazione prenotazione) throws SQLException {
        ResultSet resultSet = dbManager.select_query(sql_select+prenotazione.getID_esperienza()+";");
        resultSet.next();
        int posti_disponibili = resultSet.getInt("posti_disponibili") + prenotazione.getPosti();
        Object[] token = {posti_disponibili, prenotazione.getID_esperienza()};
        String format = MessageFormat.format(sql_update, token);
        if(dbManager.insert_update_delete_query(format) == -1){
            resultSet.close();
            throw new SQLException();
        }
        resultSet.close();
    }

    /**
     * utilizzato durante la fase di prenotazione aggiorna l' esperienza runtime e sul db
     * @param esperienza
     * @param num_posti
     */
    public void modificaDisponibilita(Esperienza esperienza, int num_posti){
        esperienza.cambiaPostiDisponibili('-', num_posti);
        Object[] token = {
                esperienza.getPostiDisponibili(),
                esperienza.getId()
        };
        String formato = MessageFormat.format(sql_update, token);
        System.out.println(formato);
        if(dbManager.insert_update_delete_query(formato)==-1){
            esperienza.cambiaPostiDisponibili('+', num_posti);
        }
    }

    /**
     * recupera i posti disponibili di un esperienza
     * @param id_esperienza
     * @return
     * @throws SQLException
     */
    public int getPostiDisponibiliToDB(int id_esperienza) throws SQLException {
        String sql = sql_select+id_esperienza+";";
        ResultSet resultSet = dbManager.select_query(sql);
        resultSet.next();
        return resultSet.getInt("posti_disponibili");
    }




}
