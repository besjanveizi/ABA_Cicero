package it.unicam.cs.ids2122.cicero.ruoli;

/**
 * Rappresenta un utente autenticato nel sistema come <code>Amministratore</code>.
 */
public class Amministratore extends UtenteAutenticato implements IUtente {

    /**
     * Crea un utente autenticato come <code>Amministratore</code>.
     * @param username username del <code>Amministratore</code>.
     * @param email    email del <code>Amministratore</code>.
     */
    public Amministratore(String username, String email) {
        super(username, email, UtenteType.ADMIN);
    }
}
