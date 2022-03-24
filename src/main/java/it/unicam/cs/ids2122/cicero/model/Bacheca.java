package it.unicam.cs.ids2122.cicero.model;

import it.unicam.cs.ids2122.cicero.model.esperienza.Esperienza;
import it.unicam.cs.ids2122.cicero.ruoli_LEGACY.Cicerone;

import java.util.List;

/**
 * Rappresenta un raccoglitore di tutte le esperienze pubbliche nella piattaforma Cicero.
 */
public interface Bacheca {

    /**
     * Restituisce tutte le esperienze pubbliche nella piattaforma.
     * @return lista di tutte le esperienze pubblicate.
     */
    List<Esperienza> getAllEsperienze();

    /**
     * Restituisce l'<code>Esperienza</code> cui identificativo corrisponde a quello dato.
     * @param id identificativo dell'<code>Esperienza</code> che si vuole ottenere.
     * @return <code>Esperienza</code> corrispondente all'identificativo dato.
     */
    Esperienza getEsperienza(int id);
    /**
     * Aggiunge un nuovo cicerone e le relative esperienze alla bacheca
     * @param c {@link Cicerone} che si vuole aggiungere
     */
    void addCicerone(Cicerone c);
}