package it.unicam.cs.ids2122.cicero.model.prenotazione_V3.pagamento;

import it.unicam.cs.ids2122.cicero.model.prenotazione_V3.Prenotazione;
import it.unicam.cs.ids2122.cicero.model.prenotazione_V3.SystemConstraints;
import it.unicam.cs.ids2122.cicero.model.prenotazione_V3.utenti.Utente;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * raccordo per le proprietà e per le funzioni relative ad un esperienza
 */
public interface Fattura {

    /**
     * Definisce lo stato in cui versa il pagamento.
     * Influisce su origine e destinazione del pagamento
     * @return un possibile {@link StatoPagamento}
     */
    StatoPagamento getStatoPagamento();

    /**
     * Id generato dal DB
     * @return id fattura
     */
    int getId_fattura();

    /**
     * recupero id della {@code Prenotazione}
     * @return id {@link Prenotazione} collegata
     */
    int getId_prenotazione();

    /**
     * Stringa generata
     * @return uid.
     */
    String getId_client_destinatario();

    /**
     * Stringa generata
     * @return uid.
     */
    String getId_client_origine();

    /**
     * Recupera dalla fattura il suo
     * ammontare
     * @return importo della fattura
     */
    BigDecimal getImporto();

    /**
     * es "EUR", "USD"...
     * @return la valuta corrente
     */
    String getValuta();

    /**
     * recupera la data di creazione
     * @return data di creazione del pagamento
     */
    LocalDateTime getData_pagamento();

    /**
     * recupera i posti prenotati e pagati
     * dell' {@code Esperienza}
     * @return posti pagati
     */
    int getPosti_pagati();

    /**
     * Assegna un identificativo generato dal DB
     * @param id id generato
     */
    void assegna_id(int id);

    /**
     * primo stadio del pagamento,
     * inserisce l' origine del pagamento, mentre la destinazione
     * è data da <code>{@link SystemConstraints}</code>
     *  * deve aggiornare lo stato di un pagamento
     *      * @param statoPagamento <code>{@link StatoPagamento}</code>
     * @param conto_origine altrimenti definito come il turista
     */
    void turista_admin(String conto_origine);

    /**
     * seconda stadio del pagamento,
     * dal conto origine del <code>{@link Utente}</code>
     *  * deve aggiornare lo stato di un pagamento
     *      * @param statoPagamento <code>{@link Utente}</code>
     * @param conto_destinatario altrimenti definito come cicerone
     */
    void admin_cicerone(String conto_destinatario);

    /**
     * eccezione del flusso di pagamento in cui invece di transitare verso
     * il <code>{@link Utente}</code>
     * che ha creato l <code>Esperienza</code> dopo la conferma del
     * rimborso il destinatario è chi ha pagato
     *  * deve aggiornare lo stato di un pagamento
     *      * @param statoPagamento <code>{@link StatoPagamento}</code>
     * @param conto_destinatario altrimenti definito turista
     */
    void admin_turista(String conto_destinatario);
}
