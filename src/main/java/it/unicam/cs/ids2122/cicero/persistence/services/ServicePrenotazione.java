package it.unicam.cs.ids2122.cicero.persistence.services;

import it.unicam.cs.ids2122.cicero.model.esperienza.IEsperienza;
import it.unicam.cs.ids2122.cicero.model.prenotazione.bean.BeanPrenotazione;
import it.unicam.cs.ids2122.cicero.model.prenotazione.bean.StatoPrenotazione;
import it.unicam.cs.ids2122.cicero.persistence.PGManager;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public final class ServicePrenotazione extends AbstractService<BeanPrenotazione> {

    private static ServicePrenotazione servicePrenotazione=null;

    private final String sql_insert = "INSERT INTO public.prenotazioni( id_esperienza, uid_turista, " +
            "stato_prenotazione, posti_prenotati, data_prenotazione, data_scadenza_riserva, costo_totale, valuta)" +
            "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?);";

    private final String sql_select = "SELECT * FROM public.prenotazioni";

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
            connection = PGManager.getInstance().connect();
            PreparedStatement preparedStatement = connection.prepareStatement(sql_insert, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, prenotazione.getID_esperienza());
            preparedStatement.setInt(2, prenotazione.getID_turista());
            preparedStatement.setInt(3, prenotazione.getStatoPrenotazione().getN());
            preparedStatement.setInt(4, prenotazione.getPosti());
            preparedStatement.setObject(5, prenotazione.getData_prenotazione());
            preparedStatement.setObject(6, prenotazione.getScadenza());
            preparedStatement.setBigDecimal(7, prenotazione.getPrezzo_totale());
            preparedStatement.setString(8, prenotazione.getValuta());
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            prenotazione.setID_prenotazione(rs.getInt(1));
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

    public Set<BeanPrenotazione> sql_select(int id){
         return parseDataResult( getDataResult(sql_select_base + " WHERE id_prenotazione=" + id +";" ));
    }

    public Set<BeanPrenotazione> select(String sql_select){
        Set<BeanPrenotazione> prenotazioni = new HashSet<>();
        Connection connection = null;
        try {
            connection = PGManager.getInstance().connect();
            PreparedStatement preparedStatement = connection.prepareStatement(sql_select);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                BeanPrenotazione beanPrenotazione = new BeanPrenotazione();
                beanPrenotazione.setID_prenotazione(resultSet.getInt("id_prenotazione"));
                beanPrenotazione.setID_esperienza(resultSet.getInt("id_esperienza"));
                beanPrenotazione.setID_turista(resultSet.getInt("uid_turista"));
                beanPrenotazione.setStatoPrenotazione(trasformazione(resultSet.getInt("stato_prenotazione")));
                beanPrenotazione.setPosti(resultSet.getInt("posti_prenotati"));
                beanPrenotazione.setData_prenotazione(resultSet.getObject("data_prenotazione", LocalDateTime.class));
                beanPrenotazione.setScadenza(resultSet.getObject("data_scadenza_riserva", LocalDateTime.class));
                beanPrenotazione.setPrezzo_totale(resultSet.getBigDecimal("costo_totale"));
                beanPrenotazione.setValuta(resultSet.getString("valuta"));
                prenotazioni.add(beanPrenotazione);
            }
        }catch (SQLException | IllegalStateException e){
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return prenotazioni;
    }

    public Set<BeanPrenotazione> select(int uid_turista){
        String s = sql_select.concat("where uid_turista=" + uid_turista+ " ;");
        return select(s);
    }

    public Set<BeanPrenotazione> select(){
        return select(this.sql_select+";");
    }


    public void update(int id, StatoPrenotazione statoPrenotazione){
        String sql_update =  "SELECT * FROM public.prenotazioni SET stato_prenotazione=" + statoPrenotazione.getN() +
                " where id_prenotazione=" + id +" ;";
        update(sql_update);
    }


    public void update(StatoPrenotazione statoPrenotazione){
        String sql_update =  "SELECT * FROM public.prenotazioni SET stato_prenotazione=" + statoPrenotazione.getN() +" ;";
        update(sql_update);
    }


    public void update(String sql){
        Connection connection = null;
        try{
            connection = PGManager.getInstance().connect();
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
    * trasforma una stringa predefinita nel suo corrispondente <code>{@link StatoPrenotazione}</code>
    *
    * @param n  valore corrispondente allo stato
    * @return <code>{@link StatoPrenotazione}</code>
    */
    private StatoPrenotazione trasformazione(int n){
        switch(n){
            case 0 : return StatoPrenotazione.RISERVATA;
            case 1 : return StatoPrenotazione.PAGATA;
            case 2 : return StatoPrenotazione.CANCELLATA;
            default: return null;
        }
    }

    @Override
    public Set<BeanPrenotazione> parseDataResult(TreeMap<String, HashMap<String, String>> arcano) {
        Set<BeanPrenotazione> resultSet = new HashSet<>();



        for (Map.Entry<String, HashMap<String, String>> firstEntry : arcano.entrySet()) {
            idEsperienza = Integer.parseInt(firstEntry.getKey());
            HashMap<String, String> others = firstEntry.getValue();
            for (Map.Entry<String, String> secondEntry : others.entrySet()) {
                String key = secondEntry.getKey();
                String val = secondEntry.getValue();
                switch (key) {
                    case "nome": nome = val; break;
                    case "uid_cicerone": uidCicerone = Integer.parseInt(val); break;
                    case "descrizione": descrizione = val; break;
                    case "data_pubblicazione": dataPubblicazione =
                            LocalDateTime.parse(val.replace(" ", "T")); break;
                    case "data_inizio": dataInizio =
                            LocalDateTime.parse(val.replace(" ", "T")); break;
                    case "data_conclusione": dataFine =
                            LocalDateTime.parse(val.replace(" ", "T")); break;
                    case "data_termine": dataTermine =
                            LocalDateTime.parse(val.replace(" ", "T")); break;
                    case "stato": stato = Integer.parseInt(val); break;
                    case "max_partecipanti": maxPartecipanti = Integer.parseInt(val); break;
                    case "min_partecipanti": minPartecipanti = Integer.parseInt(val); break;
                    case "costo_individuale": costoIndividuale = new BigDecimal(val); break;
                    case "valuta": valuta = Currency.getInstance(val); break;
                    case "max_riserva": maxRiserva = Integer.parseInt(val); break;
                    case "posti_disponibili": postiDisponibili = Integer.parseInt(val); break;
                    default: break;
                }
            }
            //InfoEsperienza infoE = new InfoEsperienza()
            //resultSet.add();
        }
        return resultSet;
    }
}
