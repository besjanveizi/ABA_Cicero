package model.prenotazione;

import model.esperienza.Esperienza;

/**
 * Rappresenta una semplice <code>Prenotazione</code> nella piattaforma.
 */
public class SimplePrenotazione implements Prenotazione {

    private Esperienza esperienza;
    private int postiPrenotati;

    /**
     * Crea una semplice <code>Prenotazione</code> per l'<code>Esperienza</code> data.
     * @param esperienza <code>Esperienza</code> cui la <code>Prenotazione</code> &egrave associata.
     * @param postiPrenotati numero dei posti prenotati all'<code>Esperienza</code> data.
     */
    public SimplePrenotazione(Esperienza esperienza, int postiPrenotati) {
        this.esperienza = esperienza;
        this.postiPrenotati = postiPrenotati;
    }

    @Override
    public String toString() {
        return "SimplePrenotazione{ " +
                "esperienza = " + esperienza +
                ", postiPrenotati = " + postiPrenotati +
                " }";
    }
}
