package it.unicam.cs.ids2122.cicero.model.prenotazione_V3.pagamento;

import it.unicam.cs.ids2122.cicero.model.prenotazione_V3.rimborso.Rimborso;

/**
 *  enumerazione delle possibili direzioni di pagamento.
 *  Servono per simulare l' intera catena del pagamento
 *  e stabili la possibilità di rimborso.
 *  In sintesi rappresenta il ciclo di vita di una {@link Fattura}
 */
public enum StatoPagamento {

    /**
     * stato transitorio della {@code Fattura}.
     * Assunto solo in fase di creazione
     */
    TURISTA_ADMIN(0),

    /**
     * Stato intermedio della fattura.
     */
    ADMIN_ADMIN(1),

    /**
     * Stato finale, il pagamento
     * è arrivato al turista.
     * viene generato da una {@link Rimborso}
     */
    ADMIN_TURISTA(2),

    /**
     * stato finale, il pagamento ha raggiunto
     * il cicerone.
     */
    ADMIN_CICERONE(3);

    private int code;

    StatoPagamento(int code) {
       this.code = code;
    }

    public int getCode() {
        return code;
    }
}
