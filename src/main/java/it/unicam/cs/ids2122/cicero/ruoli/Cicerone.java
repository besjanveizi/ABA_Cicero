package it.unicam.cs.ids2122.cicero.ruoli;

/**
 * Rappresenta un utente autenticato nel sistema come {@code Cicerone}.
 */
public class Cicerone extends UtenteAutenticato implements IUtente{

    /**
     * Crea un utente autenticato come {@code Cicerone}.
     * @param uid user id del {@code Cicerone}.
     * @param username username del {@code Cicerone}.
     * @param email email del {@code Cicerone}.
     * @param password password del {@code Cicerone}.
     */
    public Cicerone(int uid, String username, String email, String password) {
        super(uid, username, email, password, UtenteType.CICERONE);
    }
}
