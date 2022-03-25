package it.unicam.cs.ids2122.cicero.model;


import it.unicam.cs.ids2122.cicero.model.esperienza.Esperienza;
import it.unicam.cs.ids2122.cicero.model.service.ricerca.IBacheca;

import java.util.*;

/**
 * Classe singleton che rappresenta una bacheca globale nella piattaforma cicero.
 */
public class Bacheca implements IBacheca {

    private static Bacheca instance=null;
    private Set<Esperienza> esperienze;

    private Bacheca() {
        //ottengo esperienze
    }

    public static Bacheca getInstance(){
        if(instance == null)
            instance= new Bacheca();
        return instance;
    }

    @Override
    public Set<Esperienza> getAllEsperienze() {
        return esperienze;
    }

    /*
    public Set<Esperienza> gettAllValidEsperienze(){
         return esperienze;
    }
     */
}