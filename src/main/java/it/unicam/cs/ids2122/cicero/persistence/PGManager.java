package it.unicam.cs.ids2122.cicero.persistence;

import java.sql.*;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.logging.*;

public class PGManager {

    private final static String uri = SystemConstraints.DB_URI;
    private final static String host = SystemConstraints.DB_HOST;
    private final static String port = SystemConstraints.DB_PORT;
    private final static String dbName = SystemConstraints.DB_NAME;
    private final static String dbUser = SystemConstraints.DB_USER;
    private final static String dbPass = SystemConstraints.DB_PASS;

    private static PGManager instance = null;
    private Logger logger;

    private PGManager() {
        setupLogger();
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

    public static PGManager getInstance() {
        if (instance == null)
            instance = new PGManager();
        return instance;
    }

    private void setupLogger() {
        logger = Logger.getLogger(PGManager.class.getName());
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

    public TreeMap<String, HashMap<String,String>> select(Connection conn, String selectQuery) {

        TreeMap<String, HashMap<String, String>> selectDataResult = new TreeMap<>();

        // table columns specified in the query
        String[] columns = selectQuery
                .replace(" ","")
                .replace("SELECT", "")
                .split("FROM")[0]
                .split(",");

        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement(selectQuery);
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
            // close ResultSet
            try {
                if (rs != null) rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            // close PreparedStatement
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return selectDataResult;
    }

    public final Connection connect() throws SQLException {
        return DriverManager.getConnection(uri + "://" + host + ":" + port + "/" + dbName, dbUser, dbPass);
    }
}
