package it.unicam.cs.ids2122.cicero.model.prenotazione_V3.persistenza;


import it.unicam.cs.ids2122.cicero.model.prenotazione_V3.utenti.SimpleUser;
import it.unicam.cs.ids2122.cicero.model.prenotazione_V3.utenti.Utente;
import it.unicam.cs.ids2122.cicero.model.prenotazione_V3.utenti.UtenteType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBUtente {

    public DBUtente(){
        Logger.getAnonymousLogger().log(Level.INFO, "attivazione db utenti");
    }

    public void genera(ResultSet resultSet, List<Utente> lista) throws SQLException {
        while(resultSet!=null && resultSet.next()){
            Utente utente = new SimpleUser(
                    resultSet.getInt("uid"),
                    tipoUtente(resultSet.getInt("user_type")),
                    resultSet.getString("email"),
                    resultSet.getString("username"));
            lista.add(utente);
        }
    }


    public Utente genera(ResultSet resultSet) throws SQLException {
        while(resultSet!=null && resultSet.next()){
            Utente utente = new SimpleUser(
                    resultSet.getInt("uid"),
                    tipoUtente(resultSet.getInt("user_type")),
                    resultSet.getString("email"),
                    resultSet.getString("username"));
            return utente;
        }return null;
    }

    private UtenteType tipoUtente(int tipo){
        switch (tipo){
            case 1 : return UtenteType.CICERONE;
            case 2: return UtenteType.TURISTA;
            case 0: return UtenteType.ADMIN;
            default: return null;
        }
    }


}
