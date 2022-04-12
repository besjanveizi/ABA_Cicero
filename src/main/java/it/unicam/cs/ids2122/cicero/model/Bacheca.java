package it.unicam.cs.ids2122.cicero.model;


import it.unicam.cs.ids2122.cicero.model.entities.esperienza.Esperienza;
import it.unicam.cs.ids2122.cicero.model.services.ServiceEsperienza;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Classe singleton che rappresenta una bacheca globale nella piattaforma cicero.
 */
public class Bacheca implements IBacheca {

    private static Bacheca instance = null;
    private Set<Esperienza> esperienze;

    private Bacheca() {
        esperienze = new HashSet<>();
        esperienze.addAll(ServiceEsperienza.getInstance().download());
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
        esperienze = esperienze.stream().filter(Esperienza::isAvailable).collect(Collectors.toSet());
        return esperienze;
    }
}