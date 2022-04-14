package it.unicam.cs.ids2122.cicero.model.services;

import it.unicam.cs.ids2122.cicero.model.entities.bean.BeanPrenotazione;
import it.unicam.cs.ids2122.cicero.model.entities.bean.StatoPrenotazione;
import it.unicam.cs.ids2122.cicero.persistence.DBManager;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public final class ServicePrenotazione extends AbstractService<BeanPrenotazione> {

    private static ServicePrenotazione servicePrenotazione=null;

    private final String sql_insert = "INSERT INTO public.prenotazioni(id_esperienza, uid_turista, stato_prenotazione," +
            " posti_prenotati, data_prenotazione, data_scadenza_riserva, costo_totale, valuta)" +
            "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?);";

    private final String delete = "DELETE FROM public.prenotazione";

    private final String colonne =
            "id_prenotazione, id_esperienza, uid_turista, " +
                    "stato_prenotazione, posti_prenotati, data_prenotazione, data_scadenza_riserva, costo_totale, valuta";

    private final String sql_select_base = "select " + colonne + " FROM public.prenotazioni ";


    ServicePrenotazione(){}

    public static ServicePrenotazione getInstance(){
        if(servicePrenotazione==null) {
            servicePrenotazione = new ServicePrenotazione();
        }
        return servicePrenotazione;
    }

    /**
     * Schema
     * "INSERT INTO public.prenotazioni( id_esperienza, uid_turista, " +
     *             "stato_prenotazione, posti_prenotati, data_prenotazione, data_scadenza_riserva, costo_totale, valuta)" +
     *             "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?);";
     * @param prenotazione
     */
    public void insert(BeanPrenotazione prenotazione){
        Connection connection = null;
        try{
            connection = DBManager.getInstance().connect();
            PreparedStatement preparedStatement = connection.prepareStatement(sql_insert, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, prenotazione.getID_esperienza());
            preparedStatement.setInt( 2, prenotazione.getID_turista());
            preparedStatement.setInt(3, prenotazione.getStatoPrenotazione().getCode());
            preparedStatement.setInt(4, prenotazione.getPosti());
            preparedStatement.setObject(5, prenotazione.getData_prenotazione());
            preparedStatement.setObject(6, prenotazione.getScadenza());
            preparedStatement.setBigDecimal(7, prenotazione.getPrezzo_totale());
            preparedStatement.setString(8, prenotazione.getValuta());
            preparedStatement.executeUpdate();
            ResultSet rs =preparedStatement.getGeneratedKeys();
            rs.next();
            prenotazione.setID_prenotazione(rs.getInt("id_prenotazione"));
        }catch (SQLException sqlException){
                    sqlException.printStackTrace();
        }finally {
            try {
               connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Modifica lo stato.
     * @param id prenotazione
     * @param statoPrenotazione il nuovo stato
     */
    public void update(int id, StatoPrenotazione statoPrenotazione){
        String sql_update =  "UPDATE public.prenotazioni SET stato_prenotazione=" + statoPrenotazione.getCode() +
                " WHERE id_prenotazione=" + id +" ;";
        update(sql_update);
    }


    /**
     * Conn al DB per uun update.
     * @param sql
     */
    public void update(String sql){
        Connection connection = null;
        try{
            connection = DBManager.getInstance().connect();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Overload delezione per id.
     * @param id
     */
    public void delete(int id){
        String s = delete.concat("where id_prenotazione=" + id + ";");
        delete(s);
    }

    /**
     * Overload delezione per tutte le esperienze con un determinato stato. (CANCELLATA)
     * @param statoPrenotazione
     */
    public void delete(StatoPrenotazione statoPrenotazione){
        String s = delete.concat("where stato_prenotazione=" + statoPrenotazione.getCode()+";");
        delete(s);
    }

    /**
     * Connessione al DB per l' eliminazione.
     * @param sql
     */
    public void delete(String sql){
        Connection connection = null;
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



    /**
    * trasforma una stringa predefinita nel suo corrispondente <code>{@link StatoPrenotazione}</code>
    *
    * @param n  valore corrispondente allo stato
    * @return <code>{@link StatoPrenotazione}</code>
    */
    private StatoPrenotazione trasformazione(int n){
        switch(n){
            case 1 : return StatoPrenotazione.RISERVATA;
            case 0 : return StatoPrenotazione.PAGATA;
            case 2 : return StatoPrenotazione.CANCELLATA;
            default: return null;
        }
    }

    public Set<BeanPrenotazione> getPrenotazioniOfEsperienza(int idEsperienza) {
        return parseDataResult( getDataResult(sql_select_base + " WHERE id_esperienza=" + idEsperienza +";" ));
    }

    public Set<BeanPrenotazione> sql_select(int id){
        return parseDataResult( getDataResult(sql_select_base + " WHERE uid_turista=" + id +";" ));
    }

    @Override
    public Set<BeanPrenotazione> parseDataResult(TreeMap<String, HashMap<String, String>> arcano) {
        Set<BeanPrenotazione> resultSet = new HashSet<>();

        for (Map.Entry<String, HashMap<String, String>> firstEntry : arcano.entrySet()) {

            BeanPrenotazione beanPrenotazione = new BeanPrenotazione();
            beanPrenotazione.setID_prenotazione(Integer.parseInt(firstEntry.getKey()));

            HashMap<String, String> others = firstEntry.getValue();
            for (Map.Entry<String, String> secondEntry : others.entrySet()) {
                String key = secondEntry.getKey();
                String val = secondEntry.getValue();
                switch (key) {
                    case "id_esperienza": beanPrenotazione.setID_esperienza(Integer.parseInt(val));break;
                    case "uid_turista":beanPrenotazione.setID_turista(Integer.parseInt(val));break;
                    case "stato_prenotazione":beanPrenotazione.setStatoPrenotazione(trasformazione(Integer.parseInt(val)));break;
                    case "posti_prenotati": beanPrenotazione.setPosti(Integer.parseInt(val));break;
                    case "data_prenotazione": beanPrenotazione.setData_prenotazione(LocalDateTime.parse(val.replace(' ', 'T')));break;
                    case "data_scadenza_riserva": beanPrenotazione.setScadenza(LocalDateTime.parse(val.replace(' ', 'T')));break;
                    case "costo_totale" : beanPrenotazione.setPrezzo_totale(new BigDecimal(val));break;
                    case "valuta": beanPrenotazione.setValuta(val); break;
                    default: break;
                }
            }
            resultSet.add(beanPrenotazione);
        }
        return resultSet;
    }
}
