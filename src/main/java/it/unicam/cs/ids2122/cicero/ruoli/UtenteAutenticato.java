package it.unicam.cs.ids2122.cicero.ruoli;

import com.google.common.base.Objects;
import it.unicam.cs.ids2122.cicero.model.prenotazione_V3.SystemConstraints;

/**
 * Rappresenta generalmente un utente autenticato nel sistema.
 */
public class UtenteAutenticato implements IUtente{

    private final int uid;
    private final String username;
    private final String email;
    private final String password;
    private final UtenteType uType;
    private String id_client;

    /**
     * Crea un utente autenticato.
     * @param uid
     * @param username username dell'utente autenticato.
     * @param email email dell'utente autenticato.
     * @param uType tipo di utente autenticato.
     */
    public UtenteAutenticato(int uid, String username, String email, String password, UtenteType uType) {
        this.uid = uid;
        this.username = username;
        this.email = email;
        this.password = password;
        this.uType = uType;
        this.id_client = null;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public UtenteType getType() {
        return uType;
    }

    @Override
    public String getID_Client() {
        if (id_client == null)
            id_client = SystemConstraints.id_client_generator(getEmail());
        return id_client;
    }

    @Override
    public int getUID() {
        return uid;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UtenteAutenticato that = (UtenteAutenticato) o;
        return Objects.equal(getUsername(), that.getUsername()) &&
                Objects.equal(getEmail(), that.getEmail()) &&
                uType == that.uType;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getUsername(), getEmail(), uType);
    }

    @Override
    public String toString() {
        return "Utente autenticato {" +
                "username: '" + getUsername() + '\'' +
                ", email: '" + getEmail() + '\'' +
                ", ruolo: " + getType() +
                '}';
    }
}
