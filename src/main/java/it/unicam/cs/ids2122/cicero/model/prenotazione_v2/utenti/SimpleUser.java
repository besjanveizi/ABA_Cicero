package it.unicam.cs.ids2122.cicero.model.prenotazione_v2.utenti;


import it.unicam.cs.ids2122.cicero.model.prenotazione_v2.SystemConstraints;

public class SimpleUser implements Utente {

    private int id;
    private String username;
    private String email;
    private UtenteType userType;
    private String hash_pass;
    private String id_client = null;

    // recupero dal db // log in
    public SimpleUser(int id, UtenteType userType, String email, String id_client) {
        this.id = id;
        this.userType = userType;
        this.email = email;
        this.id_client = id_client;
    }

    // creazione utente // sign in
    public SimpleUser(UtenteType userType, String username, String email , String hash_pass) {
        this.userType = userType;
        this.email = email;
        this.hash_pass = hash_pass;
        this.id_client = SystemConstraints.id_client_generator(getMail()) ;
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
    public String getID_Client() {
        return id_client;
    }


    @Override
    public String toString() {
        return "SimpleUser{" +
                "id=" + id +
                ", userType=" + userType +
                ", mail='" + email + '\'' +
                ", hash_pass='" + hash_pass + '\'' +
                '}';
    }


}
