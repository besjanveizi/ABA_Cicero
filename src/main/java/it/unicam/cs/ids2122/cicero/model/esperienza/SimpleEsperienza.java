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

    private final int id;
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
    private Set<Area> aree;
    private int postiDisponibili;
    private EsperienzaStatus status;

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
        this.status = EsperienzaStatus.IDLE;
    }

    public String getNome() {
        return nome;
    }

    public Cicerone getCiceroneCreatore() {
        return ciceroneCreatore;
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

    public Percorso getPercorso() {
        return percorso;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public Money getCostoIndividuale() {
        return costoIndividuale;
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
    public Set<Area> getAree() {
        if (aree.isEmpty())
            aree = percorso.getAree();
        return aree;
    }

    @Override
    public String toString() {
        return "Info dell'esperienza {" +
                "nome: '" + getName() + '\'' +
                ", cicerone: '" + getCiceroneCreatore().getUsername() + '\'' +
                ", inizio: " + getDataInizio() +
                ", fine: " + getDataFine() +
                ", num. max partecipanti: " + getMaxPartecipanti() +
                ", num. min partecipanti: " + getMinPartecipanti() +
                ", costo per posto: " + getCostoIndividuale() +
                ", max giorni di riserva: " + getMaxGiorniRiserva() +
                ", tags: " + getTags().stream().map(Tag::getName).collect(Collectors.toSet()) +
                ", toponimi: " + getAree().stream().map(Area::getToponimo).collect(Collectors.toSet()) +
                ", postiDisponibili: " + getPostiDisponibili() +
                '}';
    }
}
