package it.unicam.cs.ids2122.cicero.model.esperienza;

import it.unicam.cs.ids2122.cicero.model.esperienza.percorso.Percorso;
import it.unicam.cs.ids2122.cicero.model.tag.Tag;
import it.unicam.cs.ids2122.cicero.model.territorio.Area;
import it.unicam.cs.ids2122.cicero.ruoli.Cicerone;
import it.unicam.cs.ids2122.cicero.util.Money;

import java.math.BigDecimal;
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

    //-- aggiunte
    private String descrizione = ""; // può essere null
    private int uid_cicerone;
    private LocalDateTime dataPubblicazione;
    private LocalDateTime dataTermine;

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

    /**
     * Costruttore per il DB. Mancano tag aree e percorso
     * @param id_esperienza
     * @param uid_cicerone
     * @param nome
     * @param descrizione
     * @param data_pubblicazione
     * @param data_inizio
     * @param data_conclusione
     * @param data_termine
     * @param stato
     * @param max_partecipanti
     * @param min_partecipanti
     * @param costo_individuale
     * @param valuta
     * @param posti_disponibili
     */
    public SimpleEsperienza(int id_esperienza, int uid_cicerone, String nome, String descrizione, LocalDateTime data_pubblicazione,
                            LocalDateTime data_inizio,  LocalDateTime data_conclusione, LocalDateTime data_termine,
                            EsperienzaStatus stato, int max_partecipanti, int min_partecipanti , BigDecimal costo_individuale,
                            String valuta, int posti_disponibili){
        this.id = id_esperienza;
        this.uid_cicerone = uid_cicerone;
        this.nome = nome;
        this.descrizione = descrizione;
        this.dataPubblicazione = data_pubblicazione;
        this.dataInizio = data_inizio;
        this.dataFine = data_conclusione;
        this.dataTermine = data_termine;
        this.status = stato;
        this.minPartecipanti = min_partecipanti;
        this.maxPartecipanti = max_partecipanti;
        this.costoIndividuale = new Money(costo_individuale,valuta);
        this.postiDisponibili = posti_disponibili;
    }

    public Cicerone getCiceroneCreatore() {
        return ciceroneCreatore;
    }


    public Percorso getPercorso() {
        return percorso;
    }

    @Override
    public Set<Tag> getTags() {
        return tags;
    }

    @Override
    public LocalDateTime getDataInizio() {
        return dataInizio;
    }

    @Override
    public LocalDateTime getDataFine() {
        return dataFine;
    }

    @Override
    public LocalDateTime getDataPubblicazione() {
        return dataPubblicazione;
    }

    @Override
    public LocalDateTime getDataTerminazione() {
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
    public Money getCostoIndividuale() {
        return costoIndividuale;
    }

    @Override
    public int getMaxGiorniRiserva() {
        return maxGiorniRiserva;
    }

    @Override
    public String getDescrizione() {
        return descrizione;
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
    public int getAutoreID() {
        return ciceroneCreatore.getID();
    }

    @Override
    public void cambioStato(EsperienzaStatus nuovoStato) {
        this.status = nuovoStato;
    }

    @Override
    public void modificaPostiDisponibili(char simbolo, int nuova_disponibilita) {
        switch (simbolo) {
            case '-':
                postiDisponibili -= nuova_disponibilita;
            case '+':
                postiDisponibili += nuova_disponibilita;
        }
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
