package it.unicam.cs.ids2122.cicero.model;

import it.unicam.cs.ids2122.cicero.model.esperienza.Esperienza;

import java.util.Set;

/**
 * Rappresenta un raccoglitore di tutte le esperienze pubbliche nella piattaforma Cicero.
 */
public interface IBacheca {

    /**
     * Restituisce tutte le esperienze pubbliche nella piattaforma.
     * @return insieme di tutte le esperienze pubblicate nella piattaforma.
     */
    Set<Esperienza> getAllEsperienze();
}