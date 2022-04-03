package it.unicam.cs.ids2122.cicero.ruoli;

/**
 * Rappresenta un utente autenticato nel sistema come {@code Amministratore}.
 */
public class Amministratore extends UtenteAutenticato implements IUtente {

    /**
     * Crea un utente autenticato come {@code Amministratore}.
     * @param uid user id dell'{@code Amministratore}.
     * @param username username dell'{@code Amministratore}.
     * @param email email dell'{@code Amministratore}.
     * @param password password dell'{@code Amministratore}.
     */
    public Amministratore(int uid, String username, String email, String password) {
        super(uid, username, email, password, UtenteType.ADMIN);
    }
}
