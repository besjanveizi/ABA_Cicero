package it.unicam.cs.ids2122.cicero.model.esperienza;

import it.unicam.cs.ids2122.cicero.model.esperienza.percorso.Percorso;
import it.unicam.cs.ids2122.cicero.model.tag.Tag;
import it.unicam.cs.ids2122.cicero.model.territorio.Area;
import it.unicam.cs.ids2122.cicero.ruoli.Cicerone;
import it.unicam.cs.ids2122.cicero.util.Money;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Rappresenta un'{@code Esperienza} che &egrave stata proposta nella piattaforma da un {@code Cicerone}.
 */
public interface Esperienza {

    /**
     * Recupera il nome dell'{@code Esperienza}.
     * @return stringa del nome.
     */
    String getName();

    /**
     * Recupera il {@code Cicerone} che ha creato l'{@code Esperienza}.
     * @return {@link  Cicerone} creatore.
     */
    Cicerone getCiceroneCreatore();

    /**
     * Recupera la data in cui inizia l'{@code Esperienza}.
     * @return {@link LocalDateTime} della data d'inizio.
     */
    LocalDateTime getDataInizio();

    /**
     * Recupera la data in cui si conclude l'{@code Esperienza}.
     * @return {@link LocalDateTime} della data di conclusione.
     */
    LocalDateTime getDataFine();

    /**
     * Recupera il numero massimo dei partecipanti all'{@code Esperienza}.
     * @return il numero massimo dei posti che &egrave possibile prenotare
     * (riservare e pagare) per l'{@code Esperienza}.
     */
    int getMaxPartecipanti();

    /**
     * Recupera il numero minimo dei partecipanti all'{@code Esperienza}.
     * @return il numero minimo dei posti che si devono prenotare (riservare e pagare)
     * all'{@code Esperienza} perch&eacute essa sia {@code VALIDA}.
     */
    int getMinPartecipanti();

    /**
     * Recupera il percorso dell'{@code Esperienza}.
     * @return {@link Percorso} dell'{@code Esperienza}.
     */
    Percorso getPercorso();

    /**
     * Recupera il costo individuale dell'{@code Esperienza}.
     * @return {@link Money} di un solo posto all'{@code Esperienza}.
     */
    Money getCostoIndividuale();

    /**
     * Recupera il numero massimo dei giorni che è permesso riservare dei
     * posti all'{@code Esperienza} senza dover pagare.
     * @return numero massimo dei giorni di riserva.
     */
    int getMaxRiserva();

    /**
     * Recupera tutti i tag associati all'{@code Esperienza}.
     * @return insieme di {@link Tag} associati.
     */
    Set<Tag> getTags();

    /**
     * Recupera tutte le aree del territorio associate all'{@code Esperienza}.
     * @return insieme di {@link Area} associate.
     */
    Set<Area> getAree();

    /**
     * Recupera lo stato dell'{@code Esperienza}.
     * @return {@link EsperienzaStatus} dell'{@code Esperienza}.
     */
    EsperienzaStatus getStatus();

    /**
     * Controlla se un'{@code Esperienza} ha posti disponibili per prenotarsi.
     * @return true se ci sono posti disponibili, false altrimenti.
     */
    boolean isAvailable();

    /**
     * Calcola il numero di posti disponibili per prenotarsi all'{@code Esperienza}.
     * @return numero dei posti disponibili alla prenotazione.
     */
    int getPostiDisponibili();
}
