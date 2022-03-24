package it.unicam.cs.ids2122.cicero.ruoli;

/**
 * Rappresenta un utente autenticato nel sistema come cicerone.
 */
public class Cicerone extends UtenteAutenticato {

    /**
     * Crea un cicerone.
     * @param uid user id del cicerone.
     * @param username username del cicerone.
     * @param email email del cicerone.
     */
    public Cicerone(int uid, String username, String email) {
        super(uid, username, email, UtenteType.CICERONE);
    }
}
