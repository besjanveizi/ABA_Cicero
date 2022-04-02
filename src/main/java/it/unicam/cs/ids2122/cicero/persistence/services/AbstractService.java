package it.unicam.cs.ids2122.cicero.persistence.services;

import it.unicam.cs.ids2122.cicero.persistence.PGManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Logger;

/**
 * Abstract service class per le operazioni di persistenza.
 * @param <T> tipo dell'entit&agrave coinvolta nelle operazioni di persistenza.
 */
public abstract class AbstractService<T> {

    PGManager dbMng = PGManager.getInstance();

    /**
     * Esegue la query {@code SQL} data per recuperare le informazioni.
     * @param sql stringa della query {@code SQL}.
     * @return {@code Set} delle informazioni analizzate nel tipo {@code T}.
     */
    public Set<T> execute(String sql) {
        return parseDataResult(getDataResult(sql));
    }

    /**
     * Recupera le informazioni in una {@code TreeMap} eseguendo le operazioni di persistenza.
     * @param sql stringa della query {@code SQL}.
     * @return {@code TreeMap} che contiene le informazioni recuperate.
     */
    public final TreeMap<String, HashMap<String, String>> getDataResult(String sql) {
        TreeMap<String, HashMap<String, String>> dataResult = new TreeMap<>();
        Connection conn = null;
        try {
            conn = dbMng.connect();
            dataResult = dbMng.select(conn, sql);
        } catch (SQLException e) {
            Logger.getLogger(PGManager.class.getName()).severe("Connection failed\n");
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) { conn.close(); }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return dataResult;
    }

    /**
     * Esegue l'analisi delle informazioni recuperate.
     * @param dataResult {@code TreeMap} che contiene le informazioni recuperate.
     * @return {@code Set} delle informazioni del tipo dell'entit&agrave cui si riferisce il servizio.
     */
    public abstract Set<T> parseDataResult(TreeMap<String, HashMap<String, String>> dataResult);

}
