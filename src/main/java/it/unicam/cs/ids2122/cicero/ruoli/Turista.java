package it.unicam.cs.ids2122.cicero.ruoli;

/**
 * Rappresenta un utente autenticato nel sistema come <code>Turista</code>.
 */
public class Turista extends UtenteAutenticato {

    /**
     * Crea un utente autenticato come <code>Turista</code>.
     * @param username username del <code>Turista</code>.
     * @param email    email del <code>Turista</code>.
     */
    public Turista(String username, String email) {
        super(username, email, UtenteType.TURISTA);
    }
}
