package model.prenotazione;


import model.esperienza.Esperienza;

import java.util.List;

public interface IRicerca<E extends Esperienza> {

    List<E> risultatoRicerca();
}
