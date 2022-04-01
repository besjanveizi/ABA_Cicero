package it.unicam.cs.ids2122.cicero.model.prenotazione_V3;

import java.math.BigDecimal;
import java.time.LocalDateTime;



public interface Prenotazione {

    /**
     * Recupera l' id {@code Prenotazione}
     * @return id prenotazione
     */
    int getID_prenotazione();

    /**
     * id della prenotazione
     * @return id della prenotazione
     */
    int getID_esperienza();

    /**
     * id della prenotazione
     * @return id della prenotazione
     */
    int getID_turista();

    /**
     * recupera nome esperienza prenotata
     * @return nome esperienza prenotata
     */
    String getNomeEsperienza();

    /**
     * restituisce il numero di posti prenotati
     * @return posti prenotati
     */
    int getPosti();

    /**
     * recupera la data di creazione
     * calcolata in base al LocalDateTime.now();
     * @return data creazione
     */
    LocalDateTime getDataPrenotazione();

    /**
     * data scadenza prenotazione
     * @return data scadenza
     */
    LocalDateTime getScadenza();

    /**
     * prezzo totale
     * @return iil costo di un eventuale pagamento
     */
    BigDecimal getPrezzo_totale();

    /**
     * restituisce il tipo di valuta
     * @return la rispettiva valuta
     */
    String getValuta();

    /**
     * restituisce lo stato corrente
     * @return <code>{@link StatoPrenotazione}</code>
     */
    StatoPrenotazione getStatoPrenotazione();


    /**
     * modifica lo stato di una prenotazione (RunTime)
     * @param nuovoStatoPrenotazione si rimanda a <code>{@link StatoPrenotazione}</code>
     */
    void cambiaStatoPrenotazione(StatoPrenotazione nuovoStatoPrenotazione);


    /**
     * Assegna un identificato generato dal DB
     * @param id assegna un id
     */
    void assegna_id(int id);


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
