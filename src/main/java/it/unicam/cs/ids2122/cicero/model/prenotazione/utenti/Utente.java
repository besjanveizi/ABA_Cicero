package it.unicam.cs.ids2122.cicero.model.prenotazione.utenti;

public interface Utente {

    /**
     * recupera id
     * @return id utente corrente
     */
    int getID();

    /**
     * recupera il tipo per definire le funzionalità
     * @return il tipo dell' utente corrente
     */
    UtenteType getType();

    /**
     * recupera mail
     * @return l'email dell'utente corrente
     */
    String getMail();





}
