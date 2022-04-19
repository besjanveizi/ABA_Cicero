package it.unicam.cs.ids2122.cicero.model.entities.bean;


/**
 *  enumerazione per i diversi stati in cui può trovarsi una
 *  <code>{@link BeanPrenotazione}</code>
 */
public enum StatoPrenotazione {

    RISERVATA(0),
    CANCELLATA(1),
    PAGATA(2);

    private final int code;

    StatoPrenotazione(int code){
        this.code = code;
    }

    public static StatoPrenotazione fetchStatus(int pCode) {
        switch(pCode){
            case 0 : return StatoPrenotazione.RISERVATA;
            case 1 : return StatoPrenotazione.CANCELLATA;
            case 2 : return StatoPrenotazione.PAGATA;
            default: return null;
        }
    }

    public int getCode() {
        return code;
    }
}
