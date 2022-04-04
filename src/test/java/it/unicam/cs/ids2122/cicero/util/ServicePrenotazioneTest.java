package it.unicam.cs.ids2122.cicero.util;

import it.unicam.cs.ids2122.cicero.persistence.PGManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ServicePrenotazioneTest {

    @BeforeAll
     static void testConnection(){
        PGManager.getInstance().testConnection();
    }

    @Test
    void init(){

    }


}
