package it.unicam.cs.ids2122.cicero.model.prenotazione_v2.gestori;




import it.unicam.cs.ids2122.cicero.model.prenotazione_v2.esperienza.Esperienza;
import it.unicam.cs.ids2122.cicero.model.prenotazione_v2.persistenza.DBManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GestoreBacheca extends AbstractGestore {

    private List<Esperienza> allEsperienze;

    private String sql_select = "SELECT * FROM public.esperienze ";

    public GestoreBacheca(DBManager dbManager) throws SQLException {
        super(dbManager);
        allEsperienze = new ArrayList<>();
        carica();
    }

    public void carica() throws SQLException {
        dbManager.select_query(sql_select);
    }

    public void aggiorna() throws SQLException {
        carica();
    }

    public List<Esperienza> getAllEsperienze() {
        return allEsperienze;
    }


    public void show() {

    }
}
