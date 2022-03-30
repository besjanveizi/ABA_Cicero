package it.unicam.cs.ids2122.cicero.ruoli;

/**
 * Rappresenta il tipo dell'utente autenticato al sistema.
 */
public enum UtenteType {
    /**
     * Utente amministratore.
     */
    ADMIN(0),

    /**
     * Utente cicerone.
     */
    CICERONE(1),

    /**
     * Utente turista.
     */
    TURISTA(2);

    private final int code;

    UtenteType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
