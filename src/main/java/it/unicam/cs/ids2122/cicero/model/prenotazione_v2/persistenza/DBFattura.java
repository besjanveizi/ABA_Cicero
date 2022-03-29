package it.unicam.cs.ids2122.cicero.model.prenotazione_v2.persistenza;



import it.unicam.cs.ids2122.cicero.model.prenotazione_v2.pagamento.Fattura;
import it.unicam.cs.ids2122.cicero.model.prenotazione_v2.pagamento.SimpleFattura;
import it.unicam.cs.ids2122.cicero.model.prenotazione_v2.pagamento.StatoPagamento;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBFattura {

    public DBFattura(){
        Logger.getAnonymousLogger().log(Level.INFO,"attivazione del db fattura");
    }

    public void genera(ResultSet resultSet, List<Fattura> list) throws SQLException {
        while(resultSet != null && resultSet.next()){
            Fattura fattura = new SimpleFattura(trasformazione(resultSet.getInt("stato_fattura")),
                    resultSet.getInt("id_fattura"),
                    resultSet.findColumn("id_prenotazione"),
                    resultSet.getString("id_client_destinatario"),
                    resultSet.getString("id_client_origine"),
                    resultSet.getBigDecimal("importo"), resultSet.getString("valuta"),
                    resultSet.getObject("data_pagamento", LocalDateTime.class),
                    resultSet.getInt("posti_pagati"));
            list.add(fattura);
        }
        resultSet.close();
    }

    private StatoPagamento trasformazione(int stato){
        switch (stato){
            case 1: return StatoPagamento.ADMIN_ADMIN;
            case 2: return StatoPagamento.ADMIN_TURISTA;
            case 3: return StatoPagamento.ADMIN_CICERONE;
            default:return null;
        }
    }

    public Fattura genera(ResultSet resultSet) throws SQLException {
        while(resultSet != null && resultSet.next()){
            Fattura fattura = new SimpleFattura(trasformazione(resultSet.getInt("stato_fattura")),
                    resultSet.getInt("id_fattura"),
                    resultSet.findColumn("id_prenotazione"),
                    resultSet.getString("id_client_destinatario"),
                    resultSet.getString("id_client_origine"),
                    resultSet.getBigDecimal("importo"), resultSet.getString("valuta"),
                    resultSet.getObject("data_pagamento", LocalDateTime.class),
                    resultSet.getInt("posti_pagati"));
            resultSet.close();
           return fattura;
        }return null;
    }
}
