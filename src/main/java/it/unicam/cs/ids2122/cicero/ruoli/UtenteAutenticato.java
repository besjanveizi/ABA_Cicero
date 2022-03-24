package it.unicam.cs.ids2122.cicero.ruoli;

/**
 * Rappresenta generalmente un utente autenticato nel sistema.
 */
public class UtenteAutenticato {
    private final int id;
    private final String username;
    private final String email;
    private final UtenteType uType;

    public UtenteAutenticato(int id, String username, String email, UtenteType uType) {
        // TODO: id should be given by the database primary key of ciceroni table
        this.id = id;
        this.username = username;
        this.email = email;
        this.uType = uType;
    }

    /**
     * Permette di ottenere l'user ID dell'utente.
     * @return valore intero corrispondente all'user id dell'utente.
     */
    public int getID() {
        return id;
    }

    /**
     * Permette di ottenere l'username dell'utente.
     * @return username dell'utente.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Permette di ottenere l'email dell'utente.
     * @return la stringa dell'email dell'utente.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Permette di ottenere il tipo di utente autenticato.
     * @return <code>UtenteType</code> dell'utente autenticato
     */
    public UtenteType getUtenteType() {
        return uType;
    }

    @Override
    public String toString() {
        return "UtenteAutenticato{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", uType=" + uType +
                '}';
    }
}
