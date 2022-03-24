package it.unicam.cs.ids2122.cicero.model.esperienza;

import it.unicam.cs.ids2122.cicero.model.tag.Tag;

import java.util.Set;

/**
 * Rappresenta un'esperienza proposta nella piattaforma.
 */
public interface Esperienza {
    /**
     * Controlla se un'esperienza è valida per essere eseguita.
     * @return true se è valida, false altrimenti.
     */
    boolean isValid();

    /**
     * Controlla se un'esperienza ha posti disponibili per prenotarsi.
     * @return true se ci sono posti disponibili, false altrimenti.
     */
    boolean isAvailable();

    /**
     * Calcola il numero di posti disponibili per prenotarsi all'esperienza.
     * @return numero intero di posti disponibili per prenotarsi.
     */
    int getPostiDisponibili();

    /**
     * Restituisce l'identificativo dell'<code>Esperienza</code>.
     * @return identificativo dell'<code>Esperienza</code>.
     */
    int getId();

    /**
     * Restituisce il nome dell'<code>Esperienza</code>.
     * @return nome dell'<code>Esperienza</code>.
     */
    String getName();

    /**
     *
     * @return insieme dei tags associati all'esperienza
     */
    Set<Tag> getTagsAssociati();

    /**
     *
     * @return insieme dei toponimi associati all'esperienza
     */
    Set<String> getToponimiAssociati();
}
