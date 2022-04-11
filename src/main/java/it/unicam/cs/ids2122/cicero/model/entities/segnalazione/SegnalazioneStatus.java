package it.unicam.cs.ids2122.cicero.model.entities.segnalazione;

/**
 * Stati delle <code>Segnalazioni</code> della piattaforma.
 */
public enum SegnalazioneStatus {
    /**
     * la <code>Segnalazione</code> è stata effettuata dall' <code>Utente</code>.
     * Significa che è in attesa di essere accettata o rifiutata dall'<code>Amministrazione</code>.
     */
    PENDING(0),
    /**
     * La <code>Segnalazione</code> è stata accettata dall'<code>Amministrazione</code>.
     */
    ACCETTATA(1),
    /**
     * La <code>Segnalazione</code> è stata rifiutata dall'<code>Amministrazione</code>.
     */
    RIFIUTATA(2);

    private final int code;

    SegnalazioneStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static SegnalazioneStatus fetchStatus(int tCode) {
        switch (tCode) {
            case 0: return SegnalazioneStatus.PENDING;
            case 1: return SegnalazioneStatus.ACCETTATA;
            case 2: return SegnalazioneStatus.RIFIUTATA;
            default: return null;
        }
    }
}