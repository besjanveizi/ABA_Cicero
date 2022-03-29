package it.unicam.cs.ids2122.cicero.model;


import it.unicam.cs.ids2122.cicero.model.esperienza.Esperienza;

import java.util.*;

/**
 * Classe singleton che rappresenta una bacheca globale nella piattaforma cicero.
 */
public class Bacheca implements IBacheca {

    private static Bacheca instance = null;
    private Set<Esperienza> esperienze;

    private Bacheca() {
        // TODO: aggiorna il set esperienze
        //  esperienze = select(DBTable.ESPERIENZE)
        //  -> seleziona tutte le esperienze memorizzate nella tabella "esperienze" e restituisce un set degli oggetti
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
}