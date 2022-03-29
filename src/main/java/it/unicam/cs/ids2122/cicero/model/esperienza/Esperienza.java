package it.unicam.cs.ids2122.cicero.model.esperienza;

import it.unicam.cs.ids2122.cicero.model.tag.Tag;
import it.unicam.cs.ids2122.cicero.model.territorio.Area;

import java.util.Set;

/**
 * Rappresenta un'esperienza che &egrave stata proposta nella piattaforma da un <code>Cicerone</code>.
 */
public interface Esperienza {
    /**
     * @return lo stato dell'<code>Esperienza</code>.
     */
    EsperienzaStatus getStatus();

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
    Set<Tag> getTags();

    /**
     * @return insieme delle aree associate all'<code>Esperienza</code>.
     */
    Set<Area> getAree();
}
