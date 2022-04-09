package it.unicam.cs.ids2122.cicero.model.entities.tag;

/**
 * Stati dei <code>Tag</code> della piattaforma.
 */
public enum TagStatus {
    /**
     * Il <code>Tag</code> &egrave stato proposto dal <code>Cicerone</code>.
     * Significa che &egrave in attesa di essere approvato o rifiutato dall'<code>Amministrazione</code>.
     */
    PROPOSTO(0),
    /**
     * Il <code>Tag</code> &egrave stato approvato dall'<code>Amministrazione</code>.
     * Esso pu&ograve essere usato dai <code>Ciceroni</code> nella creazione dell'<code>Esperienza</code>.
     */
    APPROVATO(1),
    /**
     * Il <code>Tag</code> &egrave stato rifiutato dall'<code>Amministrazione</code>.
     * Esso non pu&ograve pi&ugrave essere proposto dai <code>Ciceroni</code>.
     */
    RIFIUTATO(2);

    private final int code;

    TagStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static TagStatus fetchStatus(int tCode) {
        switch (tCode) {
            case 0: return TagStatus.PROPOSTO;
            case 1: return TagStatus.APPROVATO;
            case 2: return TagStatus.RIFIUTATO;
            default: return null;
        }
    }
}
