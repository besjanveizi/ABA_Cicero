package it.unicam.cs.ids2122.cicero.util;

import it.unicam.cs.ids2122.cicero.model.prenotazione.gestori.GestoreUtente;
import it.unicam.cs.ids2122.cicero.model.prenotazione.persistenza.PostgresDB;
import it.unicam.cs.ids2122.cicero.model.prenotazione.utenti.Utente;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class UtenteTest {


    private static PostgresDB postgresDB;
    private static GestoreUtente gestoreUtente;
    private static Random random = new Random();
    private final static String username = random.ints(48,123)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(10)
                .collect(StringBuilder::new , StringBuilder::appendCodePoint, StringBuilder::append).toString();

    private final static String email=random.ints(48,123)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(10)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)+"@test.it";

    private static String pass = "asdasd12345";

    @BeforeAll
    public static void init()  {
        try {
            postgresDB = new PostgresDB("cicero", "postgres", "asdasd12345");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        gestoreUtente = new GestoreUtente(postgresDB);

    }

    @Test
    public void inserimento(){
        System.out.println(email);
       try {
            gestoreUtente.sign_in(username, email,pass);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        // duplicato
        assertThrows(SQLException.class, ()-> gestoreUtente.sign_in(username, email,pass));
    }


    @AfterAll
    public static void recupero(){
        System.out.println(email);
        try {
          assertNotNull( gestoreUtente.log_in(email, pass));
          gestoreUtente.show();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        //utente non esistente
      assertThrows(SQLException.class, ()-> gestoreUtente.log_in("",pass));
    }


    @AfterAll
    public static void autenticazione_cicerone(){
        gestoreUtente.upgrade_to_cicero();
        try {
            Utente utente =gestoreUtente.log_in(email, pass);
            assertNotNull(utente);
            assertEquals(1, utente.getType().getCode());
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }


}
