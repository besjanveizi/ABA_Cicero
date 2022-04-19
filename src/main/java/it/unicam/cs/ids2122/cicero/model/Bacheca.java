package it.unicam.cs.ids2122.cicero.model;


import it.unicam.cs.ids2122.cicero.model.entities.esperienza.Esperienza;
import it.unicam.cs.ids2122.cicero.model.services.ServiceEsperienza;

import java.util.*;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Classe singleton che rappresenta una bacheca globale nella piattaforma cicero.
 */
public class Bacheca implements IBacheca {

    private static Bacheca instance = null;
    private Set<Esperienza> esperienze;
    private Logger logger;

    private Bacheca() {
        esperienze = new HashSet<>();
        logger = Logger.getLogger(Piattaforma.class.getName());
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
        logger.info("\taggiornamento delle esperienze in corso.. ");
        esperienze.addAll(ServiceEsperienza.getInstance().download());
        logger.info("esperienze aggiornate.");
    }

    @Override
    public void add(Esperienza e) {
        esperienze.add(e);
    }
}