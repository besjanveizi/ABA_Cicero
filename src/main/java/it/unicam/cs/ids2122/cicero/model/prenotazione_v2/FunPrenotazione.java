package it.unicam.cs.ids2122.cicero.model.prenotazione_v2;

import java.time.LocalDateTime;

/**
 *  interfaccia relativa alle funzioni inerenti la prenotazione
 */
public interface FunPrenotazione extends Prenotazione {

    /**
     * modifica lo stato di una prenotazione (RunTime)
     * @param nuovoStatoPrenotazione si rimanda a <code>{@link StatoPrenotazione}</code>
     */
    void cambiaStatoPrenotazione(StatoPrenotazione nuovoStatoPrenotazione);


    /**
     * Verifica se la chiamata attuale avviene prima di una certa data
     * @param dateTime generica data
     * @return true se la chiamata è precedente a tale data
     *          false altrimenti
     */
    default boolean check_is_before(LocalDateTime dateTime){
       return LocalDateTime.now().isBefore(dateTime);
    }

    /**
     * Verifica se la chiamata attuale  avviene dopo una certa data
     * @param dateTime generica data
     * @return true se la chiamata è antecendete a tale data
     *          false altrimenti
     */
    default boolean check_is_after(LocalDateTime dateTime){
        return LocalDateTime.now().isAfter(dateTime);
    }

}
