package it.unicam.cs.ids2122.cicero.model.prenotazione_v2.persistenza;



import java.sql.ResultSet;


public interface DBManager{

     /**
      * si connette al database per inserire la query di tipo select
      * @param sql query preparata
      * @return il <code>{@link ResultSet}</code>
      */
     ResultSet select_query(String sql);

     /**
      * si connette al database per inserire la query di tipo update, delete, insert
      * @param sql query preparata
      * @return una chiave generata se questa viene generata
      */
     int insert_update_delete_query(String sql);

}
