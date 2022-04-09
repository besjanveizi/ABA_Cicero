package it.unicam.cs.ids2122.cicero.model;

import it.unicam.cs.ids2122.cicero.model.entities.esperienza.Esperienza;
import it.unicam.cs.ids2122.cicero.model.entities.esperienza.IEsperienza;

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

    Set<IEsperienza> getAllIEsperienze();
}