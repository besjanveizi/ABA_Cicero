package it.unicam.cs.ids2122.cicero.model;


import it.unicam.cs.ids2122.cicero.model.entities.esperienza.Esperienza;
import it.unicam.cs.ids2122.cicero.model.services.ServiceEsperienza;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Classe singleton che rappresenta una bacheca globale nella piattaforma cicero.
 */
public class Bacheca implements IBacheca {

    private static Bacheca instance = null;
    private Set<Esperienza> esperienze;

    private Bacheca() {
        esperienze = new HashSet<>();
        updateEsperienze();
    }

    public static Bacheca getInstance(){
        if(instance == null)
            instance= new Bacheca();
        return instance;
    }

    @Override
    public Set<Esperienza> getEsperienze(Predicate<Esperienza> predicate) {
        return esperienze.stream().filter(predicate).collect(Collectors.toSet());
    }

    @Override
    public void updateEsperienze() {
        esperienze.addAll(ServiceEsperienza.getInstance().download());
    }

    @Override
    public void add(Esperienza e) {
        esperienze.add(e);
    }
}