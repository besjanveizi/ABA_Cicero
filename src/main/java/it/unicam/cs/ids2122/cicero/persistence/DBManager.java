package it.unicam.cs.ids2122.cicero.persistence;

import it.unicam.cs.ids2122.cicero.model.Piattaforma;
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

    /**
     * si connette al database per inserire la query di tipo select
     * @param sql query preparata
     * @return il <code>{@link ResultSet}</code>
     */
    public ResultSet select_query(String sql) {
        try{
            Statement statement = connection.createStatement();
            return statement.executeQuery(sql);
        } catch (SQLException sqlException) {
            process_exception(sqlException);
            return null;
        }
    }


    /**
     * si connette al database per inserire la query di tipo update, delete, insert
     * @param sql query preparata
     * @return una chiave generata se questa viene generata
     */
    public int insert_update_delete_query(String sql)  {
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException throwable) {
            process_exception(throwable);
            return -1;
        }
    }

    /**
     * log delle informazioni sul fallimento della query
     *
     * @param sqlException info su <code>SQLException</code>
     */
    private void process_exception(SQLException sqlException){
        logger.log(Level.SEVERE, sqlException.getMessage());
        logger.log(Level.SEVERE, sqlException.getSQLState());
    }
}
