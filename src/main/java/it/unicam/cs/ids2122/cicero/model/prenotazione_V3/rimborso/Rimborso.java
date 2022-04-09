package it.unicam.cs.ids2122.cicero.model.prenotazione_V3.rimborso;

import it.unicam.cs.ids2122.cicero.model.entities.esperienza.Esperienza;
import it.unicam.cs.ids2122.cicero.model.prenotazione_V3.pagamento.Fattura;

import java.time.LocalDateTime;

public interface Rimborso {

    LocalDateTime getDataCreazione();


    /**
     * Determina se la richiesta di rimborso è automatica
     * @param fattura una fattura relativa all' esperienza
     * @param esperienza l' esperienza che definisce il termine temporale.
     * @return true se possibile false altrimenti
     */
    boolean automatico(Fattura fattura, Esperienza esperienza);

    /**
     * Genera una richiesta di rimborso se la data creazione della
     * stessa si trova fra la data di conclusione e la data
     * termine dell' esperienza.
     * @param esperienza l' esperienza pagata
     * @return Una richiesta di rimborso da compilare
     */
    RichiestaRimborso richiede_rimborso(Fattura fattura,Esperienza esperienza);
}
