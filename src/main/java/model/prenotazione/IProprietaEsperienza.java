package model.prenotazione;



import model.esperienza.Esperienza;

import java.time.LocalDateTime;

public interface IProprietaEsperienza extends Esperienza {


    @Override
    int getId();

    @Override
    String getName();

    LocalDateTime getDataInizio();

    LocalDateTime getDataFine();

    int getMaxPartecipanti();

    int getMinPartecipanti();

    String getAutoreID();

    int getMaxGiorniRiserva();

    int getPrezzo();

}
