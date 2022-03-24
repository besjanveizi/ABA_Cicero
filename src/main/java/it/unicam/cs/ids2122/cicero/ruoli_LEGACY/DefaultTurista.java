package it.unicam.cs.ids2122.cicero.ruoli_LEGACY;

import it.unicam.cs.ids2122.cicero.model.esperienza.Esperienza;
import it.unicam.cs.ids2122.cicero.model.prenotazione.GestorePrenotazioni;
import it.unicam.cs.ids2122.cicero.model.prenotazione.Prenotazione;
import it.unicam.cs.ids2122.cicero.model.Bacheca;

import java.util.List;

/**
 * Rappresenta un utente <code>Turista</code> nella piattaforma Cicero.
 */
public class DefaultTurista implements Turista {
    private Bacheca bacheca;

    private final GestorePrenotazioni gestorePrenotazioni;

    /**
     * Permette di costruire un <code>Turista</code> di default.
     * @param gestorePrenotazioni gestore delle prenotazioni dell'utente <code>Turista</code>.
     */
    public DefaultTurista(GestorePrenotazioni gestorePrenotazioni) {
        this.gestorePrenotazioni = gestorePrenotazioni;
    }

    @Override
    public void prenota(Esperienza esperienza, int numeroPosti) {
        // create new prenotazione(esperienza, numeroPosti)
        // add it to gestoreEsperienza
    }

    @Override
    public void showPrenotazione(Prenotazione prenotazione) {

    }

    @Override
    public List<Prenotazione> showAllPrenotazioni() {
        return null;
    }

    @Override
    public void showEsperienza(Esperienza esperienza) {

    }

    @Override
    public List<Esperienza> showAllEsperienze() {
        return null;
    }
}