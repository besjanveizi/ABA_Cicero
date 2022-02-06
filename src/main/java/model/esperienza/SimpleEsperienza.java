package model.esperienza;

import model.ruoli.Cicerone;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Rappresenta una semplice <code>Esperienza</code> nella piattaforma Cicero.
 */
public class SimpleEsperienza implements Esperienza {

    private int id;
    private String nome;
    private LocalDateTime dataInizio;
    private LocalDateTime dataFine;
    private int maxPartecipanti;
    private int minPartecipanti;
    private Cicerone ciceroneCreatore;
    private int maxGiorniRiserva;
    private List<Toponimo> toponimi;
    private List<Tag> tags;

    /**
     * Crea un'<code>Esperienza</code> semplice impostando i suoi parametri.
     * @param nome nome dell'<code>Esperienza</code>.
     * @param ciceroneCreatore <code>Cicerone</code> che ha creato l'<code>Esperienza</code>.
     * @param dataInizio data d'inizio dell'<code>Esperienza</code>.
     * @param dataFine data di conclusione dell'<code>Esperienza</code>.
     * @param maxPartecipanti numero massimo dei partecipanti all'<code>Esperienza</code>.
     * @param minPartecipanti numero minimo dei partecipanti all'<code>Esperienza</code>.
     * @param maxGiorniRiserva numero massimo di giorni cui un posto all'<code>Esperienza</code> può rimanere riservato.
     * @param toponimi lista dei toponimi dell'<code>Esperienza</code>.
     * @param tags lista dei tags associati all'<code>Esperienza</code>.
     */
    public SimpleEsperienza(String nome, Cicerone ciceroneCreatore, LocalDateTime dataInizio, LocalDateTime dataFine,
                            int maxPartecipanti, int minPartecipanti, int maxGiorniRiserva, List<Toponimo> toponimi, List<Tag> tags) {
        this.id = this.hashCode();
        this.nome = nome;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.maxPartecipanti = maxPartecipanti;
        this.minPartecipanti = minPartecipanti;
        this.ciceroneCreatore = ciceroneCreatore;
        this.maxGiorniRiserva = maxGiorniRiserva;
        this.tags=tags;
        this.toponimi=toponimi;
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
        return false;
    }

    @Override
    public boolean isAvailable() {
        return false;
    }

    @Override
    public int calculateAvailability() {
        return 0;
    }

    @Override
    public void updateAvailability(int postiDisponibili) {

    }

    @Override
    public List<Tag> getTagsAssociati() {
        return tags;
    }

    @Override
    public List<Toponimo> getToponimiAssociati() {
        return toponimi;
    }

    @Override
    public String toString() {
        return "SimpleEsperienza{" +
                " id = " + id +
                ", nome = '" + nome + '\'' +
                ", dataInizio = " + dataInizio +
                ", dataFine = " + dataFine +
                ", maxPartecipanti = " + maxPartecipanti +
                ", minPartecipanti = " + minPartecipanti +
                ", ciceroneCreatore = " + ciceroneCreatore.toString() +
                ", maxGiorniRiserva = " + maxGiorniRiserva +
                " }";
    }
}
