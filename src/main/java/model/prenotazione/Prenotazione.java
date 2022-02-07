package model.prenotazione;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Rappresenta una prenotazione effettuata da un <code>Turista</code> per una determinata <code>Esperienza</code>.
 */
public interface Prenotazione extends Serializable {

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
