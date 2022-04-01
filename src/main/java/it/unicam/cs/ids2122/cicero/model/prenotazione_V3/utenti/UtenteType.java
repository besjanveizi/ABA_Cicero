package it.unicam.cs.ids2122.cicero.model.prenotazione_V3.utenti;

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
