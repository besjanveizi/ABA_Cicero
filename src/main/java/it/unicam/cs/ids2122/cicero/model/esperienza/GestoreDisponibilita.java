package it.unicam.cs.ids2122.cicero.model.esperienza;




import java.util.List;

/**
 * Rappresenta il gestore della disponibilit&agrave per un'<code>Esperienza</code>.
 */


public class GestoreDisponibilita {
    private List<Prenotazione> listaPrenotazioniPagate;
    private List<Prenotazione> listaRiserve;
    private int postiDisponibili;
    private Esperienza esperienza;

    /**
     * Permette di creare un gestore della disponibilit&agrave dell'<code>Esperienza</code> data.
     * @param esperienza <code>Esperienza</code> cui &egrave associata il gestore di disponibilit&agrave.
     */
    public GestoreDisponibilita(Esperienza esperienza) {
        this.esperienza = esperienza;
    }

    /**
     * Controlla se ci sono posti disponibili per l'esperienza associata.
     * @return vero se ci sono posti disponibili, falso altrimenti.
     */
    public boolean isAvailable() {
        return postiDisponibili > 0;
    }

    /**
     * Aggiungi una nuova prenotazione alla lista di posti riservati.
     * @param nuovaPrenotazione nuova prenotazione da aggiungere nella lista dei posti riservati.
     */
    public void riservaPrenotazione(Prenotazione nuovaPrenotazione) {
        listaRiserve.add(nuovaPrenotazione);
    }


    interface Prenotazione{

    }

}
