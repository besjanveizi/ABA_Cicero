package it.unicam.cs.ids2122.cicero.model.prenotazione.persistenza;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PostgresDB implements DBManager {

        //"postgres", "asdasd12345"
        private final static String port = "5432";
        private final static String host = "localhost:";
        private final static String uri = "jdbc:postgresql://";
        private String nomeDB = "/ciceroserver";
        private final String username;
        private final String pass;
        private final Logger logger;
        private Connection connection;


        /**
         *
         * @param username
         * @param pass
         * @throws SQLException
         */
        public PostgresDB(String username, String pass) throws SQLException{
                this.pass = pass;
                this.username = username;
                logger = Logger.getAnonymousLogger();
                testConn();
        }
        public PostgresDB(String nomeDB, String username, String pass) throws SQLException{
                this.pass = pass;
                this.username = username;
                logger = Logger.getAnonymousLogger();
                this.nomeDB = "/"+nomeDB;
                testConn();
        }

        public void testConn() throws SQLException {
                connection = DriverManager.getConnection(uri + host + port + nomeDB, username, pass);
                logger.log(Level.INFO,"accept!");
        }


        @Override
        public ResultSet select_query(String sql) {
                try{
                   Statement statement = connection.createStatement();
                   return statement.executeQuery(sql);
                } catch (SQLException sqlException) {
                        process_exception(sqlException);
                        return null;
                }
        }


        @Override
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