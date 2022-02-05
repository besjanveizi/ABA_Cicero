package model.prenotazione;

import model.ruoli.Turista;

import java.util.List;

/**
 * Rappresenta il gestore delle prenotazioni di un <code>Turista</code>.
 * @param <P> tipo di <code>Prenotazione</code>.
 */
public class GestorePrenotazioni<P> {
    private List<P> prenotazioni;
    private final Turista turistaAssociato;

    /**
     * Permette di creare un gestore delle prenotazioni per il <code>Turista</code> dato.
     * @param turistaAssociato <code>Turista</code> associato al gestore delle prenotazioni.
     */
    public GestorePrenotazioni(Turista turistaAssociato) {
        this.turistaAssociato = turistaAssociato;
    }
}