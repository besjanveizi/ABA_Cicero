package it.unicam.cs.ids2122.cicero.ruoli;

/**
 * Rappresenta un utente autenticato nella piattaforma.
 */
public interface IUtente {
    /**
     * Permette di ottenere l'username dell'utente autenticato.
     * @return username dell'utente autenticato.
     */
    String getUsername();

    /**
     * Permette di ottenere l'email dell'utente autenticato.
     * @return la stringa dell'email dell'utente autenticato.
     */
    String getEmail();

    /**
     * Permette di ottenere il tipo di utente autenticato.
     * @return <code>UtenteType</code> dell'utente autenticato.
     */
    UtenteType getType();

    /**
     * Recupera l'identificativo del conto dell'utente autenticato.
     * @return stringa dell identificativo del conto dell'utente autenticato.
     */
    String getID_Client();

    int getID();
}
