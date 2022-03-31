package it.unicam.cs.ids2122.cicero.model.esperienza;

import com.google.common.base.Objects;
import it.unicam.cs.ids2122.cicero.model.esperienza.percorso.Percorso;
import it.unicam.cs.ids2122.cicero.model.tag.Tag;
import it.unicam.cs.ids2122.cicero.model.territorio.Area;
import it.unicam.cs.ids2122.cicero.ruoli.Cicerone;
import it.unicam.cs.ids2122.cicero.util.Money;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Rappresenta una semplice implementazione di un'{@code Esperienza} nella piattaforma Cicero.
 */
public class SimpleEsperienza implements Esperienza {

    private String nome;
    private final Cicerone ciceroneCreatore;
    private String descrizione;
    private LocalDateTime dataInizio;
    private LocalDateTime dataFine;
    private int maxPartecipanti;
    private int minPartecipanti;
    private Percorso percorso;
    private Money costoIndividuale;
    private int maxGiorniRiserva;
    private Set<Tag> tags;
    private Set<Area> aree;
    private int postiDisponibili;
    private EsperienzaStatus status;
    private final LocalDateTime dataPubblicazione;
    private final LocalDateTime dataTermine;

    /**
     * Crea una {@code SimpleEsperienza} impostando i suoi parametri.
     * @param nome nome dell'{@code Esperienza}.
     * @param ciceroneCreatore {@link Cicerone} che ha creato l'{@code Esperienza}.
     * @param dataInizio data d'inizio dell'{@code Esperienza}.
     * @param dataFine data di conclusione dell'{@code Esperienza}.
     * @param maxPartecipanti numero massimo dei partecipanti all'{@code Esperienza}.
     * @param minPartecipanti numero minimo dei partecipanti all'{@code Esperienza}.
     * @param percorso {@link Percorso} dell'{@code Esperienza}.
     * @param costoIndividuale {@link Money} di un posto all'{@code Esperienza}.
     * @param maxGiorniRiserva numero massimo di giorni cui un posto all'{@code Esperienza} può rimanere riservato.
     * @param tags insieme dei tags associati all'{@code Esperienza}.
     */
    public SimpleEsperienza(String nome, Cicerone ciceroneCreatore, String descrizione, LocalDateTime dataInizio, LocalDateTime dataFine,
                            int minPartecipanti, int maxPartecipanti, Percorso percorso, Money costoIndividuale, int maxGiorniRiserva, Set<Tag> tags) {
        this.nome = nome;
        this.ciceroneCreatore = ciceroneCreatore;
        this.descrizione = descrizione;
        this.dataPubblicazione = LocalDateTime.now();
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.dataTermine = impostaTermine();
        this.minPartecipanti = minPartecipanti;
        this.maxPartecipanti = maxPartecipanti;
        this.postiDisponibili = maxPartecipanti;
        this.percorso = percorso;
        this.costoIndividuale = costoIndividuale;
        this.maxGiorniRiserva = maxGiorniRiserva;
        this.tags=tags;
        this.status = EsperienzaStatus.IDLE;
    }

    @Override
    public String getName() {
        return this.nome;
    }

    @Override
    public Cicerone getCiceroneCreatore() {
        return ciceroneCreatore;
    }

    @Override
    public String getDescrizione() {
        return descrizione;
    }

    @Override
    public LocalDateTime getDataPubblicazione() {
        return dataPubblicazione;
    }

    @Override
    public LocalDateTime getDataInizio() {
        return dataInizio;
    }

    @Override
    public LocalDateTime getDataFine() {
        return dataFine;
    }

    private LocalDateTime impostaTermine() {
        // chiedi l'expertise al Cicerone e diminuisci/aumenta la data del termine dell'esperienza
        return getDataFine().plusDays(2);  // al momento è impostato a 2 giorni dopo la conclusione
    }

    @Override
    public LocalDateTime getDataTermine() {
        return dataTermine;
    }

    @Override
    public int getMaxPartecipanti() {
        return maxPartecipanti;
    }

    @Override
    public int getMinPartecipanti() {
        return minPartecipanti;
    }

    @Override
    public Percorso getPercorso() {
        return percorso;
    }

    @Override
    public Money getCostoIndividuale() {
        return costoIndividuale;
    }

    @Override
    public int getMaxRiserva() {
        return maxGiorniRiserva;
    }

    @Override
    public Set<Tag> getTags() {
        return tags;
    }

    @Override
    public Set<Area> getAree() {
        if (aree.isEmpty())
            aree = percorso.getAree();
        return aree;
    }

    @Override
    public EsperienzaStatus getStatus() {
        return status;
    }

    @Override
    public boolean isAvailable() {
        return postiDisponibili>0;
    }

    @Override
    public int getPostiDisponibili() {
        return postiDisponibili;
    }

    @Override
    public void cambiaStatus(EsperienzaStatus newStatus) {
        this.status = newStatus;
    }

    @Override
    public void cambiaPostiDisponibili(char simbolo, int numeroPosti) {
        switch (simbolo) {
            case '-':
                postiDisponibili -= numeroPosti;
            case '+':
                postiDisponibili += numeroPosti;
        }
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleEsperienza that = (SimpleEsperienza) o;
        return getMaxPartecipanti() == that.getMaxPartecipanti() &&
                getMinPartecipanti() == that.getMinPartecipanti() &&
                getMaxRiserva() == that.getMaxRiserva() &&
                Objects.equal(getName(), that.getName()) &&
                Objects.equal(getCiceroneCreatore(), that.getCiceroneCreatore()) &&
                Objects.equal(getDataInizio(), that.getDataInizio()) &&
                Objects.equal(getDataFine(), that.getDataFine()) &&
                Objects.equal(getCostoIndividuale(), that.getCostoIndividuale());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getName(),
                getCiceroneCreatore(),
                getDataInizio(), getDataFine(),
                getMaxPartecipanti(), getMinPartecipanti(),
                getCostoIndividuale(),
                getMaxRiserva());
    }

    @Override
    public String toString() {
        return "Info dell'esperienza {" +
                "nome: '" + getName() + '\'' +
                ", descrizione: " + getDescrizione() +
                ", cicerone: '" + getCiceroneCreatore().getUsername() + '\'' +
                ", inizio: " + getDataInizio() +
                ", conclusione: " + getDataFine() +
                ", num. max partecipanti: " + getMaxPartecipanti() +
                ", num. min partecipanti: " + getMinPartecipanti() +
                ", costo per posto: " + getCostoIndividuale() +
                ", max giorni di riserva: " + getMaxRiserva() +
                ", tags: " + getTags().stream().map(Tag::getName).collect(Collectors.toSet()) +
                ", toponimi: " + getAree().stream().map(Area::getToponimo).collect(Collectors.toSet()) +
                ", postiDisponibili: " + getPostiDisponibili() +
                '}';
    }
}
