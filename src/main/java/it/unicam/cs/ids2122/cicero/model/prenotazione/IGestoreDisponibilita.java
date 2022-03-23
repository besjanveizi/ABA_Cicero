package it.unicam.cs.ids2122.cicero.model.prenotazione;

public interface IGestoreDisponibilita {

    int getPosti();

    boolean ok();

    void aggiorna();

}
