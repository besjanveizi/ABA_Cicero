package it.unicam.cs.ids2122.cicero.model.prenotazione_v2.pagamento;

/**
 *  enumerazione delle possibili direzioni di pagamento.
 *  Servono per simulare l' intera catena del pagamento
 *  e stabili la possibilità di rimborso.
 */
public enum StatoPagamento {

    TURISTA_ADMIN(0),ADMIN_ADMIN(1),ADMIN_TURISTA(2), ADMIN_CICERONE(3);

    int i;
    StatoPagamento(int i) {
       this.i = i;
    }

    public int getI() {
        return i;
    }
}
