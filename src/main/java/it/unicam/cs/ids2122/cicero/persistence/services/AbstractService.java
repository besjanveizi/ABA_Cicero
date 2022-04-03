package it.unicam.cs.ids2122.cicero.persistence.services;

import it.unicam.cs.ids2122.cicero.persistence.PGManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.*;

/**
 * Abstract service class per le operazioni di persistenza.
 * @param <T> tipo dell'entit&agrave coinvolta nelle operazioni di persistenza.
 */
public abstract class AbstractService<T> {

    PGManager dbMng = PGManager.getInstance();
    Logger logger = setupLogger();


    public final int getGeneratedKey(String sql) {
        int key = 0;
        Connection conn = null;
        try {
            conn = dbMng.connect();
            key = dbMng.insert(conn, sql);
        } catch (SQLException e) {
            logger.severe("Connection failed");
            e.printStackTrace();
        } finally {
            try { if (conn != null) conn.close(); }
            catch (SQLException e) { e.printStackTrace(); }
        }
        return key;
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
            logger.severe("Connection failed");
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
     * Esegue l'analisi delle informazioni recuperate e le raggruppa in modo coeso in un insieme.
     * @param dataResult {@code TreeMap} che contiene le informazioni recuperate.
     * @return {@code Set} coeso delle informazioni del tipo dell'entit&agrave cui si riferisce il servizio.
     */
    public abstract Set<T> parseDataResult(TreeMap<String, HashMap<String, String>> dataResult);

    private Logger setupLogger() {
        logger = Logger.getLogger(AbstractService.class.getName());
        logger.setUseParentHandlers(false);
        ConsoleHandler ch = new ConsoleHandler();
        ch.setLevel(Level.WARNING);
        ch.setFormatter(new SimpleFormatter() {
            @Override
            public String format(LogRecord record) {
                return record.getMessage() + "\n";
            }
        });
        logger.addHandler(ch);
        return logger;
    }

}
