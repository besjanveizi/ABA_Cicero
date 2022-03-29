package it.unicam.cs.ids2122.cicero.model.prenotazione.sistema;


import it.unicam.cs.ids2122.cicero.model.prenotazione.Prenotazione;
import it.unicam.cs.ids2122.cicero.model.prenotazione.pagamento.Fattura;

import java.sql.SQLException;

public class ServizioRimborso<F extends Fattura & Prenotazione> implements Service<F> {


    @Override
    public F menu() throws SQLException {
        return null;
    }
}
