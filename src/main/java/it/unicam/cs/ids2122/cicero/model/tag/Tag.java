package it.unicam.cs.ids2122.cicero.model.tag;

/**
 * Rappresenta i tag nella piattaforma.
 */
public interface Tag {

    /**
     * @return il nome del <code>Tag</code>.
     */
    String getName();

    /**
     * @return ritorna lo stato del <code>Tag</code>.
     */
    TagStatus getState();
}
