package it.unicam.cs.ids2122.cicero.model.prenotazione.persistenza;


import it.unicam.cs.ids2122.cicero.model.esperienza.Esperienza;
import it.unicam.cs.ids2122.cicero.model.esperienza.EsperienzaStatus;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBEsperienza {


    public DBEsperienza(){
        Logger.getAnonymousLogger().log(Level.INFO, "DBEsperienza attivato");
    }



    public void inserisci_esperienza(Esperienza esperienza, DBManager dbManager){

    }

    private Object[] token_init(Esperienza propEsperienza) {
        return null;
    }



    /**
     *     IDLE,
     *     VALIDA,
     *     IN_CORSO,
     *     CANCELLATA,
     *     CONCLUSA,
     *     TERMINATA
     */
    private EsperienzaStatus trasformazione(int status){
       /* switch (s) {
            case ("PUBBLICATA"):
                return StatoEsperienza.PUBBLICATA;
            case ("VALIDA"):
                return StatoEsperienza.VALIDA;
            case ("INCORSO"):
                return StatoEsperienza.INCORSO;
            case ("CONCLUSA"):
                return StatoEsperienza.CONCLUSA;
            case ("TERMINATA"):
                return StatoEsperienza.TERMINATA;
            case ("CANCELLATA"):
                return StatoEsperienza.CANCELLATA;
            default:
                return null;
        }*/
        return  null;
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

                return null;
    }

}
