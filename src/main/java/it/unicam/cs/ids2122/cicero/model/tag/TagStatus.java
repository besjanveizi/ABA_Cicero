package it.unicam.cs.ids2122.cicero.model.tag;

/**
 * Stati dei <code>Tag</code> della piattaforma.
 */
public enum TagStatus {
    /**
     * Il <code>Tag</code> &egrave stato proposto dal <code>Cicerone</code>.
     * Significa che &egrave in attesa di essere approvato o rifiutato dall'<code>Amministrazione</code>.
     */
    PROPOSTO,
    /**
     * Il <code>Tag</code> &egrave stato approvato dall'<code>Amministrazione</code>.
     * Esso pu&ograve essere usato dai <code>Ciceroni</code> nella creazione dell'<code>Esperienza</code>.
     */
    APPROVATO,
    /**
     * Il <code>Tag</code> &egrave stato rifiutato dall'<code>Amministrazione</code>.
     * Esso non pu&ograve pi&ugrave essere proposto dai <code>Ciceroni</code>.
     */
    RIFIUTATO
}
