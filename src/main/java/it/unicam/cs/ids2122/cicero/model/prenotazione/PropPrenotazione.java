package it.unicam.cs.ids2122.cicero.model.prenotazione;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * interfaccia che estende <code>{@link Prenotazione}</code>
 * definisce le caratteristiche principali di una prenotazione
 * simile al modello JavaBean
 */
public interface PropPrenotazione extends Prenotazione{

    /**
     * id della prenotazione
     * @return id della prenotazione
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
}
