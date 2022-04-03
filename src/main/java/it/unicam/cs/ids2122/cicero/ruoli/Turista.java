package it.unicam.cs.ids2122.cicero.ruoli;

/**
 * Rappresenta un utente autenticato nel sistema come {@code Turista}.
 */
public class Turista extends UtenteAutenticato {

    /**
     * Crea un utente autenticato come {@code Turista}.
     * @param uid user id del {@code Turista}.
     * @param username username del {@code Turista}.
     * @param email email del {@code Turista}.
     * @param password password del {@code Turista}.
     */
    public Turista(int uid, String username, String email, String password) {
        super(uid, username, email, password, UtenteType.TURISTA);
    }
}
