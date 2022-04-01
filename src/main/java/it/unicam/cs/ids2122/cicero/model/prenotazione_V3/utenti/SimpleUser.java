package it.unicam.cs.ids2122.cicero.model.prenotazione_V3.utenti;


public class SimpleUser implements Utente {

    private int id;
    private String username;
    private String email;
    private UtenteType userType;
    private String hash_pass;


    // recupero dal db // log in
    public SimpleUser(int id, UtenteType userType, String email,String username) {
        this.id = id;
        this.userType = userType;
        this.email = email;
        this.username = username;

    }

    // creazione utente // sign in
    public SimpleUser(UtenteType userType, String username, String email , String hash_pass) {
        this.userType = userType;
        this.email = email;
        this.hash_pass = hash_pass;
        this.username = username;
    }


    @Override
    public int getID() {
        return this.id;
    }

    @Override
    public UtenteType getType() {
        return this.userType;
    }

    @Override
    public String getMail() {
        return this.email;
    }


    @Override
    public String toString() {
        return "SimpleUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", userType=" + userType +
                '}';
    }
}
