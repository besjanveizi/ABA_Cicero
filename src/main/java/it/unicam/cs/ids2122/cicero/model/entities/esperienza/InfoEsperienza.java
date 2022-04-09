package it.unicam.cs.ids2122.cicero.model.entities.esperienza;

import com.google.common.base.Objects;
import it.unicam.cs.ids2122.cicero.model.entities.esperienza.percorso.Percorso;
import it.unicam.cs.ids2122.cicero.model.entities.tag.Tag;
import it.unicam.cs.ids2122.cicero.model.entities.territorio.Area;
import it.unicam.cs.ids2122.cicero.ruoli.Cicerone;
import it.unicam.cs.ids2122.cicero.util.Money;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Raccoglitore delle informazioni di un'{@code Esperienza}.
 */
public class InfoEsperienza {

    // set by Cicerone
    private final String nome;
    private final Cicerone ciceroneCreatore;
    private final String descrizione;
    private final LocalDateTime dataInizio;
    private final LocalDateTime dataFine;
    private final int maxPartecipanti;
    private final int minPartecipanti;
    private final Percorso percorso;
    private final Money costoIndividuale;
    private final int maxGiorniRiserva;
    private final Set<Tag> tags;
    private Set<Area> aree;

    // set by system
    private int postiDisponibili;
    private EsperienzaStatus status;
    private final LocalDateTime dataPubblicazione;
    private final LocalDateTime dataTermine;

    /**
     * Crea il raccoglitore delle informazioni di un'{@code Esperienza} che sono state impostate dal {@code Cicerone}.
     * @param nome nome dell'{@code Esperienza}.
     * @param ciceroneCreatore {@link Cicerone} che ha creato l'{@code Esperienza}.
     * @param descrizione descrizione dell'{@code Esperienza}.
     * @param dataInizio data d'inizio dell'{@code Esperienza}.
     * @param dataFine data di conclusione dell'{@code Esperienza}.
     * @param maxPartecipanti numero massimo dei partecipanti all'{@code Esperienza}.
     * @param minPartecipanti numero minimo dei partecipanti all'{@code Esperienza}.
     * @param percorso {@link Percorso} dell'{@code Esperienza}.
     * @param costoIndividuale {@link Money} di un posto all'{@code Esperienza}.
     * @param maxGiorniRiserva numero massimo di giorni cui un posto all'{@code Esperienza} può rimanere riservato.
     * @param tags insieme dei tags associati all'{@code Esperienza}.
     */
    public InfoEsperienza(String nome, Cicerone ciceroneCreatore, String descrizione,
                          LocalDateTime dataInizio, LocalDateTime dataFine,
                          int maxPartecipanti, int minPartecipanti,
                          Percorso percorso, Money costoIndividuale,
                          int maxGiorniRiserva, Set<Tag> tags) {
        this.nome = nome;
        this.ciceroneCreatore = ciceroneCreatore;
        this.descrizione = descrizione;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.maxPartecipanti = maxPartecipanti;
        this.minPartecipanti = minPartecipanti;
        this.percorso = percorso;
        this.costoIndividuale = costoIndividuale;
        this.maxGiorniRiserva = maxGiorniRiserva;
        this.tags = tags;

        // set by the system
        this.postiDisponibili = maxPartecipanti;
        this.status = EsperienzaStatus.IDLE;
        this.dataPubblicazione = LocalDateTime.now();
        this.dataTermine = impostaTermine();
    }

/*
     * Genera il raccoglitore delle informazioni di un'{@code Esperienza}.
     * @param userSettings informazioni dell'{@code Esperienza} impostate dal {@code Cicerone}.
     *                     vedi - {@link InfoEsperienza#InfoEsperienza(String, Cicerone, String, LocalDateTime, LocalDateTime, int, int, Percorso, Money, int, Set)}
     * @param postiDisponibili numero dei posti disponibili all'{@code Esperienza}.
     * @param status {@link EsperienzaStatus} dell'{@code Esperienza}.
     * @param dataPubblicazione data di pubblicazione dell'{@code Esperienza}.
     * @param dataTermine data di termine dell'{@code Esperienza}.
     *//*
    public InfoEsperienza(InfoEsperienza userSettings,
                          int postiDisponibili, EsperienzaStatus status,
                          LocalDateTime dataPubblicazione, LocalDateTime dataTermine) {
        this.nome = userSettings.getNome();
        this.ciceroneCreatore = userSettings.getCiceroneCreatore();
        this.descrizione = userSettings.getDescrizione();
        this.dataInizio = userSettings.getDataInizio();
        this.dataFine = userSettings.getDataFine();
        this.maxPartecipanti = userSettings.getMaxPartecipanti();
        this.minPartecipanti = userSettings.getMinPartecipanti();
        this.percorso = userSettings.getPercorso();
        this.costoIndividuale = userSettings.getCostoIndividuale();
        this.maxGiorniRiserva = userSettings.getMaxGiorniRiserva();
        this.tags = userSettings.getTags();

        this.postiDisponibili = postiDisponibili;
        this.status = status;
        this.dataPubblicazione = dataPubblicazione;
        this.dataTermine = dataTermine;
    }*/

    /**
     * Recupera il nome dell'{@code Esperienza}.
     * @return stringa del nome.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Recupera il {@code Cicerone} che ha creato l'{@code Esperienza}.
     * @return {@link  Cicerone} creatore.
     */
    public Cicerone getCiceroneCreatore() {
        return ciceroneCreatore;
    }

    /**
     * Recupera la descrizione dell'{@code Esperienza}.
     * @return stringa della descrizione.
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Recupera lo stato dell'{@code Esperienza}.
     * @return {@link EsperienzaStatus} dell'{@code Esperienza}.
     */
    public EsperienzaStatus getStatus() {
        return status;
    }

    /**
     * Recupera la data di pubblicazione dell'{@code Esperienza}.
     * @return {@link LocalDateTime} della pubblicazione.
     */
    public LocalDateTime getDataPubblicazione() {
        return dataPubblicazione;
    }

    /**
     * Recupera la data in cui inizia l'{@code Esperienza}.
     * @return {@link LocalDateTime} dell'inizio.
     */
    public LocalDateTime getDataInizio() {
        return dataInizio;
    }

    /**
     * Recupera la data in cui si conclude l'{@code Esperienza}.
     * @return {@link LocalDateTime} della conclusione.
     */
    public LocalDateTime getDataFine() {
        return dataFine;
    }

    private LocalDateTime impostaTermine() {
        // chiedi l'expertise al Cicerone e diminuisci/aumenta la data del termine dell'esperienza
        return getDataFine().plusDays(2);  // al momento è impostato a 2 giorni dopo la conclusione
    }

    /**
     * Recupera la data in cui l'{@code Esperienza} termina.
     * @return {@link LocalDateTime} del termine.
     */
    public LocalDateTime getDataTermine() {
        return dataTermine;
    }

    /**
     * Recupera il numero massimo dei partecipanti all'{@code Esperienza}.
     * @return il numero massimo dei posti che &egrave possibile prenotare
     * (riservare e pagare) per l'{@code Esperienza}.
     */
    public int getMaxPartecipanti() {
        return maxPartecipanti;
    }

    /**
     * Recupera il numero minimo dei partecipanti all'{@code Esperienza}.
     * @return il numero minimo dei posti che si devono prenotare (riservare e pagare)
     * all'{@code Esperienza} perch&eacute essa sia {@code VALIDA}.
     */
    public int getMinPartecipanti() {
        return minPartecipanti;
    }

    /**
     * Recupera il percorso dell'{@code Esperienza}.
     * @return {@link Percorso} dell'{@code Esperienza}.
     */
    public Percorso getPercorso() {
        return percorso;
    }

    /**
     * Recupera il costo individuale dell'{@code Esperienza}.
     * @return {@link Money} di un solo posto all'{@code Esperienza}.
     */
    public Money getCostoIndividuale() {
        return costoIndividuale;
    }

    /**
     * Recupera il numero massimo dei giorni che è permesso riservare dei
     * posti all'{@code Esperienza} senza dover pagare.
     * @return numero massimo dei giorni di riserva.
     */
    public int getMaxGiorniRiserva() {
        return maxGiorniRiserva;
    }

    /**
     * Recupera tutti i tag associati all'{@code Esperienza}.
     * @return insieme di {@link Tag} associati.
     */
    public Set<Tag> getTags() {
        return tags;
    }

    /**
     * Recupera tutte le aree del territorio associate all'{@code Esperienza}.
     * @return insieme di {@link Area} associate.
     */
    public Set<Area> getAree() {
        if (aree.isEmpty())
            aree = getPercorso().getAree();
        return aree;
    }

    /**
     * Calcola il numero di posti disponibili per prenotarsi all'{@code Esperienza}.
     * @return numero dei posti disponibili alla prenotazione.
     */
    public int getPostiDisponibili() {
        return postiDisponibili;
    }

    /**
     * Cambia il valore dei posti disponibili secondo la variazione data.
     * @param variazione valore di cambiamento della disponibilit&agrave: essa pu&ograve essere positiva
     *                   o negativa a seconda di quanto si vuole modificare il numero dei posti disponibili.
     */
    public void cambiaPostiDisponibili(int variazione) {
        if (variazione >= 0) this.postiDisponibili += postiDisponibili;
        else this.postiDisponibili -= postiDisponibili;
    }

    /**
     * Modifica lo stato corrente dell'{@code Esperienza}.
     * @param newStatus nuovo stato.
     */
    public void setStatus(EsperienzaStatus newStatus) {
        this.status = newStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InfoEsperienza that = (InfoEsperienza) o;
        return getMaxPartecipanti() == that.getMaxPartecipanti() &&
                getMinPartecipanti() == that.getMinPartecipanti() &&
                getMaxGiorniRiserva() == that.getMaxGiorniRiserva() &&
                getPostiDisponibili() == that.getPostiDisponibili() &&
                Objects.equal(getNome(), that.getNome()) &&
                Objects.equal(getCiceroneCreatore(), that.getCiceroneCreatore()) &&
                Objects.equal(getDescrizione(), that.getDescrizione()) &&
                Objects.equal(getDataInizio(), that.getDataInizio()) &&
                Objects.equal(getDataFine(), that.getDataFine()) &&
                Objects.equal(getPercorso(), that.getPercorso()) &&
                Objects.equal(getCostoIndividuale(), that.getCostoIndividuale()) &&
                Objects.equal(getTags(), that.getTags()) &&
                getStatus() == that.getStatus() &&
                Objects.equal(getDataPubblicazione(), that.getDataPubblicazione()) &&
                Objects.equal(getDataTermine(), that.getDataTermine());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getNome(), getCiceroneCreatore(), getDescrizione(), getDataInizio(),
                getDataFine(), getMaxPartecipanti(), getMinPartecipanti(), getPercorso(),
                getCostoIndividuale(), getMaxGiorniRiserva(), getTags(), getPostiDisponibili(), getStatus(),
                getDataPubblicazione(), getDataTermine());
    }
}
