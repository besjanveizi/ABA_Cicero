package it.unicam.cs.ids2122.cicero.model.esperienza;


import it.unicam.cs.ids2122.cicero.model.tag.Tag;
import it.unicam.cs.ids2122.cicero.model.territorio.Area;
import it.unicam.cs.ids2122.cicero.util.Money;


import java.time.LocalDateTime;
import java.util.Set;

/**
 * Rappresenta un'esperienza che &egrave stata proposta nella piattaforma da un <code>Cicerone</code>.
 */
public interface Esperienza {

    /**
     * @return lo stato dell'<code>Esperienza</code>.
     */
    EsperienzaStatus getStatus();

    /**
     * Controlla se un'esperienza ha posti disponibili per prenotarsi.
     * @return true se ci sono posti disponibili, false altrimenti.
     */
    boolean isAvailable();

    /**
     * Calcola il numero di posti disponibili per prenotarsi all'esperienza.
     * @return numero intero di posti disponibili per prenotarsi.
     */
    int getPostiDisponibili();

    /**
     * Restituisce l'identificativo dell'<code>Esperienza</code>.
     * @return identificativo dell'<code>Esperienza</code>.
     */
    int getId();

    /**
     * Restituisce il nome dell'<code>Esperienza</code>.
     * @return nome dell'<code>Esperienza</code>.
     */
    String getName();

    /**
     *
     * @return insieme dei tags associati all'esperienza
     */
    Set<Tag> getTags();

    /**
     * @return insieme delle aree associate all'<code>Esperienza</code>.
     */
    Set<Area> getAree();

    /**
     *
     * @return restituisce il costo
     */
    Money getCostoIndividuale();

    /**
     * @return id del cicerone
     */
    int getAutoreID();

    /**
     *
     * @return la data di inizio
     */
    LocalDateTime getDataInizio();

    /**
     *
     * @return data inizio esperienza
     */
    LocalDateTime getDataFine();

    /**
     *
     * @return data di pubblicazione
     */
    LocalDateTime getDataPubblicazione();

    /**
     * dalla data di conclusione + il tempo che assegna il sistema
     * per assegnare il pagamento al cicerone
     *
     * @return data di terminazione
     */
    LocalDateTime getDataTerminazione();

    /**
     *
     * @return max partecipanti
     */
    int getMaxPartecipanti();

    /**
     * recupera il numero minimo di partecipanti richiesti
     * @return min partecipanti
     */
    int getMinPartecipanti();

    /**
     * Variabile che rappresenta i giorni
     * @return giorni di riserva
     */
    int getMaxGiorniRiserva();

    /**
     *
     * @return la descrizione (un testo)
     */
    String getDescrizione();

    /**
     * equivale a setStato(StatoEsperienza nuovoStato)
     * @param nuovoStato nuovo stato da assegnare
     */
    void cambioStato(EsperienzaStatus nuovoStato);

    /**
     * modifica lo stato dei posti disponibili
     * @param simbolo + in caso di aumento dei posti disponibili
     *                - in caso di una diminuzione dei posti disponibili
     * @param nuova_disponibilita il numero di posti
     */
    void modificaPostiDisponibili(char simbolo,int nuova_disponibilita);

}
