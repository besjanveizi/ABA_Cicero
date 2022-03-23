package it.unicam.cs.ids2122.cicero.model.esperienza;

import it.unicam.cs.ids2122.cicero.model.tag.Tag;
import it.unicam.cs.ids2122.cicero.model.territorio.Toponimo;
import it.unicam.cs.ids2122.cicero.ruoli.Cicerone;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Rappresenta un builder per una <code>SimpleEsperienza</code>.
 */
public class BuilderSimpleEsperienza implements BuilderEsperienza {

    private final Cicerone cicerone;
    private String nomeEsperienza;
    private LocalDateTime dataInizio;
    private LocalDateTime dataFine;
    private int maxPartecipanti;
    private int minPartecipanti;
    private int maxGiorniRiserva;
    private List<Toponimo> toponimi;
    private List<Tag> tags;

    /**
     * Costruisce un builder per una <code>SimpleEsperienza</code>.
     * @param cicerone <code>Cicerone</code> associato al builder.
     */
    public BuilderSimpleEsperienza(Cicerone cicerone) {
        this.cicerone = cicerone;
    }

    @Override
    public void setNome(String nomeEsperienza) {
        this.nomeEsperienza = nomeEsperienza;
    }

    @Override
    public void setInizio(LocalDateTime inizioEsperienza) {
        this.dataInizio = inizioEsperienza;
    }

    @Override
    public void setFine(LocalDateTime fineEsperienza) {
        this.dataFine = fineEsperienza;
    }

    @Override
    public void setMax(int maxPartecipanti) {
        this.maxPartecipanti = maxPartecipanti;
    }

    @Override
    public void setMin(int minPartecipanti) {
        this.minPartecipanti = minPartecipanti;
    }

    @Override
    public void setGiorniRiserva(int maxGiorni) {
        this.maxGiorniRiserva = maxGiorni;
    }

    @Override
    public void setToponimi(List<Toponimo> toponimi) {
        this.toponimi = new ArrayList<>(toponimi);
    }

    @Override
    public void setTags(List<Tag> tags) {
        this.tags = new ArrayList<>(tags);
    }

    @Override
    public Esperienza getResult() {
        return new SimpleEsperienza(nomeEsperienza, cicerone, dataInizio, dataFine,
                maxPartecipanti, minPartecipanti, maxGiorniRiserva, toponimi, tags);
    }

    @Override
    public void reset() {
        setNome(null);
        setInizio(null);
        setFine(null);
        setMax(0);
        setMin(0);
        setGiorniRiserva(0);
    }
}