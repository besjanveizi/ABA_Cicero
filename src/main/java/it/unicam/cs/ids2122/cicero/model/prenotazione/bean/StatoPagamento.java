package it.unicam.cs.ids2122.cicero.model.prenotazione.bean;

/**
 *  enumerazione delle possibili direzioni di pagamento.
 *  Servono per simulare l' intera catena del pagamento
 *  e stabili la possibilità di rimborso.
 *  In sintesi rappresenta il ciclo di vita di una {@link BeanFattura}
 */
public enum StatoPagamento {


    /**
     * versamento al conto di sistema
     */
    ADMIN_ADMIN(0),

    /**
     * Stato finale, il pagamento
     * è arrivato al turista.
     * viene generato da una {@link Rimborso}
     */
    ADMIN_TURISTA(1),

    /**
     * stato finale, il pagamento ha raggiunto
     * il cicerone.
     */
    ADMIN_CICERONE(2);

    private int code;

    StatoPagamento(int code) {
       this.code = code;
    }

    public int getCode() {
        return code;
    }
}
