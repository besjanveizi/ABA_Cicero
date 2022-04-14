package it.unicam.cs.ids2122.cicero.model.entities.bean;


/**
 *  enumerazione per i diversi stati in cui può trovarsi una
 *  <code>{@link BeanPrenotazione}</code>
 *  RISERVATA -> PAGATA
 *  RISERVATA -> CANCELLATA
 */
public enum StatoPrenotazione {

    RISERVATA(0),
    CANCELLATA(1),
    PAGATA(2);

    private final int code;

    StatoPrenotazione(int code){
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
