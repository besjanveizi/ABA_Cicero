package model.prenotazione;

import java.io.Serializable;
import java.time.LocalDateTime;


public interface IObjPrenotazione extends  Serializable {

    int getId();

    String getName();

    boolean isRiserva();

    boolean isInvito();

    int getTempoRiserva();

    LocalDateTime getData_creazione();

    LocalDateTime getTtl();

    int getPosti();

    float getPrezzoSingolo();

    float getCostoTotale();




}
