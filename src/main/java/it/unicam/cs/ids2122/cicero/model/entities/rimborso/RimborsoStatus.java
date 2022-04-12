package it.unicam.cs.ids2122.cicero.model.entities.rimborso;


/**
 * Rappresenta lo stato del rimborso
 */
public enum RimborsoStatus {
    /**
     * La richiesta di <code>Rimborso</code> è stata effettuata.
     * La richiesta è in attesa di essere accettata o rifiutata da un amministratore.
     */
    PENDING(0),
    /**
     * La richiesta di rimborso è stata accettata.
     */
    ACCETTATA(1),
    /**
     * La richiesta di rimborso è stata rifiutata.
     */
    RIFIUTATA(2);

    private final int code;

    RimborsoStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static RimborsoStatus fetchStatus(int tCode) {
        switch (tCode) {
            case 0: return RimborsoStatus.PENDING;
            case 1: return RimborsoStatus.ACCETTATA;
            case 2: return RimborsoStatus.RIFIUTATA;
            default: return null;
        }
    }
}
