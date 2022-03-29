package it.unicam.cs.ids2122.cicero.model.prenotazione_v2.gestori;


import it.unicam.cs.ids2122.cicero.model.prenotazione_v2.PropPrenotazione;
import it.unicam.cs.ids2122.cicero.model.prenotazione_v2.esperienza.Esperienza;
import it.unicam.cs.ids2122.cicero.model.prenotazione_v2.esperienza.FunEsperienza;
import it.unicam.cs.ids2122.cicero.model.prenotazione_v2.esperienza.PropEsperienza;
import it.unicam.cs.ids2122.cicero.model.prenotazione_v2.persistenza.DBManager;

import javax.naming.OperationNotSupportedException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;


public class GestoreDisponibilita extends AbstractGestore {


    private String sql_update = "UPDATE public.esperienze SET posti_disponibili= {0} WHERE id_esperienza= {1} ;";

    private String sql_select = "SELECT posti_disponibili FROM public.esperienze where id_esperienza=";

    public GestoreDisponibilita(DBManager dbManager) {
        super(dbManager);
    }


    public void modificaDisponibilita(PropPrenotazione prenotazione) throws SQLException {
        ResultSet resultSet = dbManager.select_query(sql_select+prenotazione.getID_esperienza()+";");
        resultSet.next();
        int posti_disponibili = resultSet.getInt("posti_disponibili") + prenotazione.getPosti();
        Object[] token = {posti_disponibili, prenotazione.getID_esperienza()};
        String format = MessageFormat.format(sql_update, token);
        dbManager.insert_update_delete_query(format);
        resultSet.close();
    }

    /**
     * utilizzato durante la fase di prenotazione aggiorna l' esperienza runtime e sul db
     * @param esperienza
     * @param num_posti
     */
    public void modificaDisponibilita(Esperienza esperienza, int num_posti){
        ((FunEsperienza) esperienza).modificaPostiDisponibili('-', num_posti);
        Object[] token = {
            ((PropEsperienza) esperienza).getPostiDisponibili(),
            ((PropEsperienza) esperienza).getId()
        };
        String formato = MessageFormat.format(sql_update, token);
        dbManager.insert_update_delete_query(formato);
    }

    public int getPostiDisponibiliToDB(int id_esperienza) throws SQLException {
       String sql = sql_select+id_esperienza+";";
       ResultSet resultSet = dbManager.select_query(sql);
       return resultSet.getInt("posti_disponibili");
    }


    @Override
    public void show() throws OperationNotSupportedException {
        throw new OperationNotSupportedException("operazione non supportata");
    }


}
