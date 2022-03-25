package it.unicam.cs.ids2122.cicero.model.service.ricerca;

import it.unicam.cs.ids2122.cicero.model.esperienza.Esperienza;

import java.util.Set;

/**
 * Rappresenta un raccoglitore di tutte le esperienze pubbliche nella piattaforma Cicero.
 */
public interface IBacheca {

    /**
     * Restituisce tutte le esperienze pubbliche nella piattaforma.
     * @return lista di tutte le esperienze pubblicate.
     */
    Set<Esperienza> getAllEsperienze();
}