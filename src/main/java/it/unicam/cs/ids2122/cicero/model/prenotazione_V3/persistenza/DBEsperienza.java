package it.unicam.cs.ids2122.cicero.model.prenotazione_V3.persistenza;


import it.unicam.cs.ids2122.cicero.model.esperienza.Esperienza;
import it.unicam.cs.ids2122.cicero.model.esperienza.EsperienzaStatus;
import it.unicam.cs.ids2122.cicero.model.esperienza.SimpleEsperienza;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBEsperienza {


    public DBEsperienza(){
        Logger.getAnonymousLogger().log(Level.INFO, "DBEsperienza attivato");
    }


    private  String arrayToValues(Set values){
       /* String top = "";
        Object[] trasformata=  values.toArray();
        for (int i = 0; i < trasformata.length; i++) {
            if(trasformata[i] instanceof Toponimo){
                top = top+((Toponimo) trasformata[i]).getName();
            }else {
                top = top+((Tag) trasformata[i]).getName();
            }
            if(i+1<trasformata.length){
                top = top+",";
            }
        }
        return top;*/
        return null;
    }

    private HashSet trasformazione_array(Array array, int colonna) throws SQLException {
      /*  Object[] test = (Object[]) array.getArray();
        if(colonna==3){
            Set<Toponimo> top = new HashSet<>();
            for (Object t:test) {
                top.add(new SimpleToponimo(t.toString()));
            }
            return (HashSet) top;
        }
        if (colonna == 4) {
            Set<Tag> tag = new HashSet<>();
            for (Object t : test) {
              tag.add(new SimpleTag(t.toString()));
            }
            return (HashSet) tag;
        }*/
        return null;
    }

    public Esperienza crea_singola_esperienza(ResultSet resultSet) throws SQLException {
                /*while (resultSet!= null && resultSet.next()) {
                    Esperienza esperienza = new SimpleEsperienza(
                            resultSet.getInt("id_esperienza"),
                            resultSet.getInt("uid_cicerone"),
                            resultSet.getString("nome"),
                            resultSet.getString("descrizione"),
                            resultSet.getObject("data_inizio", LocalDateTime.class),
                            resultSet.getObject("data_conclusione", LocalDateTime.class),
                            resultSet.getInt("max_partecipanti"),
                            resultSet.getInt("min_partecipanti"),
                            resultSet.getBigDecimal("costo_individuale"), resultSet.getString("valuta"),
                            resultSet.getInt("max_riserva"),
                            resultSet.getInt("posti_disponibili"),
                            trasformazione(resultSet.getInt("stato")),
                            resultSet.getObject("data_conclusione", LocalDateTime.class),
                            resultSet.getObject("data_termine", LocalDateTime.class)
                            );
                    resultSet.close();
                    return esperienza;
                }*/
                return null;
    }

    private EsperienzaStatus trasformazione(int code){
        switch (code){
            case 0 : return EsperienzaStatus.IDLE;
        }
        return null;
    }


}