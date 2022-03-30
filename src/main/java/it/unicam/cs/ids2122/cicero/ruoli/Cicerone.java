package it.unicam.cs.ids2122.cicero.ruoli;

/**
 * Rappresenta un utente autenticato nel sistema come <code>Cicerone</code>.
 */
public class Cicerone extends UtenteAutenticato implements IUtente{

    /**
     * Crea un utente autenticato come <code>Cicerone</code>.
     * @param username username del <code>Cicerone</code>.
     * @param email email del <code>Cicerone</code>.
     */
    public Cicerone(String username, String email) {
        super(username, email, UtenteType.CICERONE);
    }
}
