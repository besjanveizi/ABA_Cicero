package it.unicam.cs.ids2122.cicero.model.tag;

/**
 * Rappresenta un {@code Tag} nella piattaforma.
 */
public interface Tag {

    /**
     * Recupera l'id del {@code Tag}.
     * @return intero dell'id.
     */
    int getId();

    /**
     * Recupera il nome del {@code Tag}.
     * @return la stringa del nome.
     */
    String getName();

    /**
     * Recupera lo stato del {@code Tag}.
     * @return {@link TagStatus} corrispondente.
     */
    TagStatus getState();

    /**
     * Recupera la descrizione del {@code Tag}.
     * @return la stringa della descrizione.
     */
    String getDescrizione();
}
