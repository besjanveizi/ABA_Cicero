package it.unicam.cs.ids2122.cicero.persistence;

import java.sql.*;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.logging.*;

/**
 * Rappresenta il gestore del database.
 */
public class DBManager {

    private final static String uri = SystemConstraints.DB_URI;
    private final static String host = SystemConstraints.DB_HOST;
    private final static String port = SystemConstraints.DB_PORT;
    private final static String dbName = SystemConstraints.DB_NAME;
    private final static String dbUser = SystemConstraints.DB_USER;
    private final static String dbPass = SystemConstraints.DB_PASS;

    private static DBManager instance = null;
    private Logger logger;

    private DBManager() {
        setupLogger();
    }

    public static DBManager getInstance() {
        if (instance == null)
            instance = new DBManager();
        return instance;
    }

    /**
     * Esegue un test della connessione al database e riporta il risultato nella console.
     */
    public void testConnection() {
        Connection conn = null;
        try {
            conn = connect();
            logger.info("Successfully connected to '" + dbName + "'. Ready to go!\n");
        } catch (SQLException e) {
            logger.severe("Connection failed\n");
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void setupLogger() {
        logger = Logger.getLogger(DBManager.class.getName());
        logger.setUseParentHandlers(false);
        ConsoleHandler ch = new ConsoleHandler();
        ch.setLevel(Level.ALL);
        ch.setFormatter(new SimpleFormatter() {
            @Override
            public String format(LogRecord record) {
                return record.getMessage();
            }
        });
        logger.addHandler(ch);
    }

    /**
     * Esegue {@code INSERT}, {@code UPDATE} e {@code DELETE} statements.
     * @param conn connessione al database.
     * @param sql query statement.
     * @return la chiave primaria generata dall'esecuzione della query.
     */
    public int executeGeneralDML(Connection conn, String sql) {
        int genKey = 0;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
            rs = stmt.getGeneratedKeys();
            rs.next();
            genKey = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return genKey;
    }

    /**
     * Esegue il {@code SELECT} statement.
     * @param conn connessione al database.
     * @param sql query statement.
     * @return {@code TreeMap} che contiene ogni chiave primaria associata al {@code HashMap} delle altre
     * colonne associate ai corrispettivi valori.<br>
     * Tutti gli elementi sono memorizzati come stringe, pertanto bisogna fare l'analisi per ottenere
     * un insieme coeso di ciascuna entry selezionata dalla query.
     */
    public TreeMap<String, HashMap<String,String>> select(Connection conn, String sql) {

        TreeMap<String, HashMap<String, String>> selectDataResult = new TreeMap<>();
        String[] columns = sql
                .replace(" ","")
                .replace("SELECT", "")
                .split("FROM")[0]
                .split(",");
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                HashMap<String,String> eachResult = new HashMap<>();
                for (int i = 1; i < columns.length; i++) {
                    eachResult.put(columns[i], rs.getString(i+1));
                }
                selectDataResult.put(rs.getString(1),eachResult);
            }

        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
        }

        return selectDataResult;
    }

    public final Connection connect() throws SQLException {
        return DriverManager.getConnection(uri + "://" + host + ":" + port + "/" + dbName, dbUser, dbPass);
    }
}
