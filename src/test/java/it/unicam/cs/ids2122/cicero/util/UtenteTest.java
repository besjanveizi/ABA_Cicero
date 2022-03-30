package it.unicam.cs.ids2122.cicero.util;

import it.unicam.cs.ids2122.cicero.model.prenotazione.gestori.GestoreUtente;
import it.unicam.cs.ids2122.cicero.model.prenotazione.persistenza.PostgresDB;
import it.unicam.cs.ids2122.cicero.model.prenotazione.utenti.Utente;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class UtenteTest {


    private static PostgresDB postgresDB;
    private static GestoreUtente gestoreUtente;
    private static Random random;
    private static String username = "";
    private static String email = "";
    private static String pass = "";

    @BeforeAll
    public static void init()  {
        try {
            postgresDB = new PostgresDB("cicero", "postgres", "asdasd12345");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        gestoreUtente = new GestoreUtente(postgresDB);
        random = new Random();
        username = random.ints(48,123)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(10)
                .collect(StringBuilder::new , StringBuilder::appendCodePoint, StringBuilder::append).toString();
        email = random.ints(48,123)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(10)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)+"@test.it";
        pass = "asdasd12345";
    }

    @Test
    public void inserimento(){
       try {
            gestoreUtente.sign_in(username, email,pass);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        // duplicato
        assertThrows(SQLException.class, ()-> gestoreUtente.sign_in(username, email,pass));
    }


    @Test
    public void recupero(){
        String email = "lV8H3YaJqZ@test.it";
        try {
          assertNotNull( gestoreUtente.log_in(email, pass));
          gestoreUtente.show();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
      assertThrows(SQLException.class, ()-> gestoreUtente.log_in("",pass));
    }



}
