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

    public static UtenteType tipoUtente(int tipo){
        switch (tipo){
            case 1 : return UtenteType.CICERONE;
            case 2: return UtenteType.TURISTA;
            case 0: return UtenteType.ADMIN;
            default: return null;
        }
    }
}
