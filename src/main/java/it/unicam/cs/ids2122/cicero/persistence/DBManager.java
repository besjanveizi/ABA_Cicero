package it.unicam.cs.ids2122.cicero.persistence;

import it.unicam.cs.ids2122.cicero.model.Piattaforma;
import it.unicam.cs.ids2122.cicero.model.system.SystemConstraints;
import it.unicam.cs.ids2122.cicero.ruoli.UtenteType;
import it.unicam.cs.ids2122.cicero.ruoli.UtenteAutenticato;

import java.sql.*;
import java.util.logging.*;

public class DBManager {

    private static DBManager instance;
    private Connection connection;
    private Logger logger;

    private final static String uri = SystemConstraints.DB_URI;
    private final static String host = SystemConstraints.DB_HOST;
    private final static String port = SystemConstraints.DB_PORT;
    private final static String dbName = SystemConstraints.DB_NAME;
    private final static String dbUser = SystemConstraints.DB_USER;
    private final static String dbPass = SystemConstraints.DB_PASS;

    private DBManager() throws SQLException {
         setupLogger();
         connect();
    }

    public static DBManager getInstance() throws SQLException {
         if (instance == null)
             instance = new DBManager();
         return instance;
    }

    private void connect() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            logger.severe("No PostgreSQL JDBC Driver found\n");
            e.printStackTrace();
        }
        try {
            connection = DriverManager
                            .getConnection(uri + "://" + host + ":" + port + "/" + dbName, dbUser, dbPass);
            logger.info("Successfully connected to '" + dbName + "'. Ready to go!\n");
        } catch (SQLException e) {
            logger.severe("Connection failed\n");
            throw e;
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

    public void login(String username, String password) {
        String sql = "SELECT * FROM utenti_registrati WHERE username = '" + username + "' AND password = '" + password + "'";
        Statement statement;
        ResultSet resultSet;
        UtenteAutenticato utenteAutenticato;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            if(resultSet!=null && resultSet.next()) {
                utenteAutenticato = new UtenteAutenticato(resultSet.getString("username"),
                        resultSet.getString("email"),
                        tipoUtente(resultSet.getInt("user_type")));
                Piattaforma.getInstance().setCtrl_utente(utenteAutenticato);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private UtenteType tipoUtente(int tipo){
        switch (tipo){
            case 1 : return UtenteType.CICERONE;
            case 2: return UtenteType.TURISTA;
            case 0: return UtenteType.ADMIN;
            default: return null;
        }
    }
}
