package controller;

import model.esperienza.Tag;
import model.esperienza.Toponimo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Rappresenta le informazioni riguardanti un <code>Esperienza</code> fornite dall'utente.
 */
public class InfoEsperienza {

    private final String nomeE;
    private final LocalDateTime dI;
    private final LocalDateTime dF;
    private final int minP;
    private final int maxP;
    private final int maxRiserva;
    private final List<Toponimo> listToponimi;
    private final List<Tag> listTags;

    /**
     * Crea un istanza delle informazioni riguardanti l'<code>Esperienza</code> con i parametri dati.
     * @param nomeE nome dell'<code>Esperienza</code>.
     * @param dI data d'inizio dell'<code>Esperienza</code>.
     * @param dF data di fine dell'<code>Esperienza</code>.
     * @param minP minimo numero dei partecipanti all'<code>Esperienza</code>.
     * @param maxP massimo numero dei partecipanti all'<code>Esperienza</code>.
     * @param maxRiserva massimo numero dei giorni di riserva per l'<code>Esperienza</code>.
     */
    public InfoEsperienza(String nomeE, LocalDateTime dI, LocalDateTime dF, int minP, int maxP, int maxRiserva,
                          List<Toponimo> listToponimi, List<Tag> listTags) {
        this.nomeE = nomeE;
        this.dI = dI;
        this.dF = dF;
        this.minP = minP;
        this.maxP = maxP;
        this.maxRiserva = maxRiserva;
        this.listToponimi = new ArrayList<>(listToponimi);
        this.listTags = new ArrayList<>(listTags);
    }

    /**
     * Restituisce il nome dell'<code>Esperienza</code>.
     * @return nome dell'<code>Esperienza</code>.
     */
    public String getNomeE() {
        return this.nomeE;
    }

    /**
     * Restituisce la data d'inizio dell'<code>Esperienza</code>.
     * @return data d'inizio dell'<code>Esperienza</code>.
     */
    public LocalDateTime get_dI() {
        return this.dI;
    }

    /**
     * Restituisce la data di conclusione dell'<code>Esperienza</code>.
     * @return data di conclusione dell'<code>Esperienza</code>.
     */
    public LocalDateTime get_dF() {
        return this.dF;
    }

    /**
     * Restituisce il numero minimo dei partecipanti all'<code>Esperienza</code>.
     * @return numero minimo dei partecipanti all'<code>Esperienza</code>.
     */
    public int getMinP() {
        return this.minP;
    }

    /**
     * Restituisce il numero massimo dei partecipanti all'<code>Esperienza</code>.
     * @return numero massimo dei partecipanti all'<code>Esperienza</code>.
     */
    public int getMaxP() {
        return this.maxP;
    }

    /**
     * Restituisce il numero massimo di giorni permessi alla riserva di un posto all'<code>Esperienza</code>.
     * @return numero massimo di giorni di riserva per l'<code>Esperienza</code>.
     */
    public int getMaxRiserva() {
        return this.maxRiserva;
    }

    public List<Toponimo> getToponimi() {
        return this.listToponimi;
    }

    public List<Tag> getTags() {
        return this.listTags;
    }
}
