package it.unicam.cs.ids2122.cicero.model;

import it.unicam.cs.ids2122.cicero.model.entities.esperienza.Esperienza;

import java.util.Set;
import java.util.function.Predicate;

/**
 * Rappresenta un raccoglitore di tutte le esperienze pubbliche nella piattaforma Cicero.
 */
public interface IBacheca {

    /**
     * Restituisce tutte le esperienze che rispettano il predicato dato.
     * @param p {@code Predicate} che le esperienze ritornate devono rispettare
     * @return {@code Set} di tutte le esperienze salvate nella piattaforma che rispettano il predicato dato.
     */
    Set<Esperienza> getEsperienze(Predicate<Esperienza> p);

    /**
     * Aggiorna le esperienze nella piattaforma.
     */
    void updateEsperienze();

    /**
     * Aggiungi un'{@code Esperienza} alla bacheca.
     * @param e {@code Esperienza} da aggiungere.
     */
    void add(Esperienza e);
}