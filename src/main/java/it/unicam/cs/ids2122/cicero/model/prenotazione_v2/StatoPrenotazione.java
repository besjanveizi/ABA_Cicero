package it.unicam.cs.ids2122.cicero.model.prenotazione_v2;

/**
 *  enumerazione per i diversi stati in cui può trovarsi una
 *  <code>{@link SimplePrenotazione}</code>
 *  RISERVATA -> PAGATA
 *  RISERVATA -> CANCELLATA
 */
public enum StatoPrenotazione {

    PAGATA(0),RISERVATA(1),CANCELLATA(2);

    private int n;
    StatoPrenotazione(int n){
        this.n = n;
    }

    public int getN() {
        return n;
    }
}
