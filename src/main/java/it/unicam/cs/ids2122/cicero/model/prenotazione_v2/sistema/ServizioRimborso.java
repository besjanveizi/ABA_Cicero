package it.unicam.cs.ids2122.cicero.model.prenotazione_v2.sistema;


import it.unicam.cs.ids2122.cicero.model.prenotazione_v2.Prenotazione;
import it.unicam.cs.ids2122.cicero.model.prenotazione_v2.pagamento.Fattura;

import java.sql.SQLException;

public class ServizioRimborso<F extends Fattura & Prenotazione> implements Service<F> {


    @Override
    public F menu() throws SQLException {
        return null;
    }
}
