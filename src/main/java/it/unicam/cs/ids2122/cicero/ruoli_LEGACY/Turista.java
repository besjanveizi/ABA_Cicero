package it.unicam.cs.ids2122.cicero.ruoli_LEGACY;

import it.unicam.cs.ids2122.cicero.model.esperienza.Esperienza;
import it.unicam.cs.ids2122.cicero.model.prenotazione.Prenotazione;

import java.util.List;


/**
 * Rappresenta un utente che desidera partecipare ad esperienze pubblicate nella piattaforma.
 */
public interface Turista {

    /**
     * Permette di effettuare una <codice>Prenotazione</codice> ad una data <code>Esperienza</code>.
     * @param esperienza <code>Esperienza</code> cui si desidera prenotarsi.
     * @param numeroPosti numero dei posti che si vuole riservare nella <code>Prenotazione</code>.
     */
    void prenota(Esperienza esperienza, int numeroPosti);

    /**
     * Mostra i dettagli della <code>Prenotazione</code> data.
     * @param prenotazione <code>Prenotazione<code> effettuata cui si vuole vedere i dettagli.
     */
    void showPrenotazione(Prenotazione prenotazione);

    /**
     * Mostra tutte le prenotazioni effettuate.
     * @return collezione di tutte le prenotazioni effettuate.
     */
    List<Prenotazione> showAllPrenotazioni();

    /**
     * Mostra i dettagli dell'<code>Esperienza</code> data.
     * @param esperienza <code>Esperienza</code> cui si desidera vedere i dettagli.
     */
    void showEsperienza(Esperienza esperienza);

    /**
     * Mostra tutte le esperienze pubblicate nella piattaforma.
     * @return collezione di tutte le esperienze pubblicate.
     */
    List<Esperienza> showAllEsperienze();
}
