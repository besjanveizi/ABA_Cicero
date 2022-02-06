package model.esperienza;

import java.util.List;

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
    int calculateAvailability();

    /**
     * Aggiorna i posti disponibili dell'esperienza.
     * @param postiDisponibili nuovo valore dei posti disponibili.
     */
    void updateAvailability(int postiDisponibili);

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
     * @return lista di {@link Tag} associati all'esperienza
     */
    List<Tag> getTagsAssociati();

    /**
     *
     * @return lista di {@link Toponimo} associati all'esperienza
     */
    List<Toponimo> getToponimiAssociati();
}
