package it.unicam.cs.ids2122.cicero.model.prenotazione_V3.persistenza;



import it.unicam.cs.ids2122.cicero.model.prenotazione_V3.Prenotazione;
import it.unicam.cs.ids2122.cicero.model.prenotazione_V3.SimplePrenotazione;
import it.unicam.cs.ids2122.cicero.model.prenotazione_V3.StatoPrenotazione;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBPrenotazione{


    public DBPrenotazione(){
        Logger.getAnonymousLogger().log(Level.INFO, "db prenotazione start");
    }

    /**
     * istanzia una <code>{@link SimplePrenotazione}</code>
     *
     * @param resultSet il risultato di una query <code>Resulset</code>
     * @return una <code>{@link SimplePrenotazione}</code>
     * @throws SQLException
     */
    public Prenotazione genera(ResultSet resultSet) throws SQLException {
            while(resultSet!=null && resultSet.next()){
                Prenotazione  prenotazione = new SimplePrenotazione(
                        resultSet.getInt("id_prenotazione"),
                        resultSet.getInt("id_esperienza"),
                        resultSet.getInt("uid_turista"),
                        trasformazione(resultSet.getInt("stato_prenotazione")),
                        resultSet.getInt("posti_prenotati"),
                        resultSet.getObject("data_prenotazione", LocalDateTime.class),
                        resultSet.getObject("data_scadenza_riserva", LocalDateTime.class),
                        resultSet.getBigDecimal("costo_totale"),
                        resultSet.getString("valuta")
                );
              return prenotazione;
            }
            return null;
    }

    /**
     * inserisce una serie di <code>{@link SimplePrenotazione}</code>
     *
     * @param resultSet <code>ResultSet</code>
     * @param prenotazioneList una lista
     * @throws SQLException
     */
    public void genera(ResultSet resultSet , List<Prenotazione> prenotazioneList) throws SQLException {
        while(resultSet!=null && resultSet.next()){
            Prenotazione  prenotazione = new SimplePrenotazione(
                    resultSet.getInt("id_prenotazione"),
                    resultSet.getInt("id_esperienza"),
                    resultSet.getInt("uid_turista"),
                    trasformazione(resultSet.getInt("stato_prenotazione")),
                    resultSet.getInt("posti_prenotati"),
                    resultSet.getObject("data_prenotazione", LocalDateTime.class),
                    resultSet.getObject("data_scadenza_riserva", LocalDateTime.class),
                    resultSet.getBigDecimal("costo_totale"),
                    resultSet.getString("valuta")
                    );
            prenotazioneList.add(prenotazione);
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



}
