package it.unicam.cs.ids2122.cicero.model.prenotazione.persistenza;


import it.unicam.cs.ids2122.cicero.model.prenotazione.invito.Invito;
import it.unicam.cs.ids2122.cicero.model.prenotazione.invito.SimpleInvito;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBInvito {

    public DBInvito() {
        Logger.getAnonymousLogger().log(Level.INFO, "db invito attivato");
    }

    public void genera(ResultSet resultSet, List<Invito> invitoList ) throws SQLException {
        while(resultSet!=null && resultSet.next()){
            Invito invito = new SimpleInvito(resultSet.getInt("id_invito"),
                    resultSet.getInt("id_mittente"),
                    resultSet.getInt("id_esperienza"),
                    resultSet.getString("email_destinatario"),
                    resultSet.getObject("data_creazione", LocalDateTime.class),
                    resultSet.getObject("data_scadenza_riserva", LocalDateTime.class),
                    resultSet.getInt("posti_riservati"));
            invitoList.add(invito);
        }
    }
}
