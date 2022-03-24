package it.unicam.cs.ids2122.cicero.model.esperienza;

import it.unicam.cs.ids2122.cicero.model.esperienza.percorso.Percorso;
import it.unicam.cs.ids2122.cicero.model.tag.Tag;
import it.unicam.cs.ids2122.cicero.model.territorio.Area;
import it.unicam.cs.ids2122.cicero.ruoli.Cicerone;
import it.unicam.cs.ids2122.cicero.util.Money;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Rappresenta una semplice <code>Esperienza</code> nella piattaforma Cicero.
 */
public class SimpleEsperienza implements Esperienza {

    private int id;
    private String nome;
    private Cicerone ciceroneCreatore;
    private LocalDateTime dataInizio;
    private LocalDateTime dataFine;
    private int maxPartecipanti;
    private int minPartecipanti;
    private Percorso percorso;
    private Money costoIndividuale;
    private int maxGiorniRiserva;
    private Set<Tag> tags;
    private Set<Area> toponimi;
    private int postiDisponibili;

    /**
     * Crea un'<code>Esperienza</code> semplice impostando i suoi parametri.
     * @param nome nome dell'<code>Esperienza</code>.
     * @param ciceroneCreatore <code>Cicerone</code> che ha creato l'<code>Esperienza</code>.
     * @param dataInizio data d'inizio dell'<code>Esperienza</code>.
     * @param dataFine data di conclusione dell'<code>Esperienza</code>.
     * @param maxPartecipanti numero massimo dei partecipanti all'<code>Esperienza</code>.
     * @param minPartecipanti numero minimo dei partecipanti all'<code>Esperienza</code>.
     * @param maxGiorniRiserva numero massimo di giorni cui un posto all'<code>Esperienza</code> può rimanere riservato.
     * @param tags insieme dei tags associati all'<code>Esperienza</code>.
     */
    public SimpleEsperienza(String nome, Cicerone ciceroneCreatore, LocalDateTime dataInizio, LocalDateTime dataFine,
                            int minPartecipanti, int maxPartecipanti, Percorso p, Money costoIndividuale, int maxGiorniRiserva, Set<Tag> tags) {
        this.id = this.hashCode();
        this.nome = nome;
        this.ciceroneCreatore = ciceroneCreatore;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.maxPartecipanti = maxPartecipanti;
        this.postiDisponibili = maxPartecipanti;
        this.minPartecipanti = minPartecipanti;
        this.percorso = p;
        this.costoIndividuale = costoIndividuale;
        this.maxGiorniRiserva = maxGiorniRiserva;
        this.tags=tags;
    }

    public LocalDateTime getDataInizio() {
        return dataInizio;
    }

    public LocalDateTime getDataFine() {
        return dataFine;
    }

    public int getMaxPartecipanti() {
        return maxPartecipanti;
    }

    public int getMinPartecipanti() {
        return minPartecipanti;
    }

    public Cicerone getCiceroneCreatore() {
        return ciceroneCreatore;
    }

    public int getMaxGiorniRiserva() {
        return maxGiorniRiserva;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return this.nome;
    }

    @Override
    public boolean isValid() {
        //  TODO: insert stato esperienza
        return false;
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
    public Set<Tag> getTagsAssociati() {
        return tags;
    }

    @Override
    public Set<String> getToponimiAssociati() {
        return percorso.getAree().stream().map(Area::getToponimo).collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        return "SimpleEsperienza{" +
                "nome='" + nome + '\'' +
                ", ciceroneCreatore=" + ciceroneCreatore +
                ", dataInizio=" + dataInizio +
                ", dataFine=" + dataFine +
                ", maxPartecipanti=" + maxPartecipanti +
                ", minPartecipanti=" + minPartecipanti +
                ", percorso=" + percorso +
                ", costoIndividuale=" + costoIndividuale +
                ", maxGiorniRiserva=" + maxGiorniRiserva +
                ", tags=" + tags +
                ", toponimi=" + toponimi +
                ", postiDisponibili=" + postiDisponibili +
                '}';
    }
}
