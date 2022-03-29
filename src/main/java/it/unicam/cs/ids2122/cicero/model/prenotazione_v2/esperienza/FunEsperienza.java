package it.unicam.cs.ids2122.cicero.model.prenotazione_v2.esperienza;

public interface FunEsperienza extends Esperienza{

    void cambioStato(StatoEsperienza nuovoStato);

    void modificaPostiDisponibili(char simbolo,int nuova_disponibilita);


}
