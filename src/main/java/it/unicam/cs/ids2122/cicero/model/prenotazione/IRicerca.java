package it.unicam.cs.ids2122.cicero.model.prenotazione;


import it.unicam.cs.ids2122.cicero.model.esperienza.Esperienza;

import java.util.List;

public interface IRicerca<E extends Esperienza> {

    List<E> risultatoRicerca();
}
