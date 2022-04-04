package it.unicam.cs.ids2122.cicero.model.prenotazione.bean;

import it.unicam.cs.ids2122.cicero.model.esperienza.Esperienza;
import it.unicam.cs.ids2122.cicero.model.esperienza.IEsperienza;

public interface Rimborso {



    /**
     * Determina se la richiesta di rimborso è automatica
     * @param fattura una fattura relativa all' esperienza
     * @param esperienza l' esperienza che definisce il termine temporale.
     * @return true se possibile false altrimenti
     */
    boolean automatico(BeanFattura fattura, IEsperienza esperienza);

    /**
     * Genera una richiesta di rimborso se la data creazione della
     * stessa si trova fra la data di conclusione e la data
     * termine dell' esperienza.
     * @param esperienza l' esperienza pagata
     * @return Una richiesta di rimborso da compilare
     */
    Rimborso richiede_rimborso(BeanFattura fattura, Esperienza esperienza);
}
