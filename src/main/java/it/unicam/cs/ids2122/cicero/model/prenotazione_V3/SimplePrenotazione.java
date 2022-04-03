package it.unicam.cs.ids2122.cicero.model.prenotazione_V3;



import it.unicam.cs.ids2122.cicero.util.Money;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class SimplePrenotazione implements Prenotazione {

    /**
     *  identificativo generato dal DB
     *  identificativo esperienza
     */
    private int ID_prenotazione;

    /**
     * identificativo esperienza relativa
     */
    private int ID_esperienza;

    /**
     * identificativo di chi effettua la prenotazione
     */
    private int ID_turista;

    /**
     * nome dell' esperienza prenotata
     */
    private  String nomeEsperienza;

    /**
     * numero di posti prenotati
     */
    private  int posti;

    /**
     * data di creazione
     */
    private LocalDateTime data_prenotazione;

    /**
     * data di scadenza per la validità di questa
     * prenotazione
     * PRENOTATA -> CENCELLATA
     */
    private LocalDateTime scadenza;

    /**
     * costo effettivo di un eventuale pagamento
     * calcolato in base al numero di posti prenotati
     */
    private BigDecimal prezzo_totale;

    /**
     * valuta pagamento
     */
    private String valuta;

    /**
     * identifica lo stato di prenotazione
     * DEFAULT -> RISERVATA
     */
    private StatoPrenotazione statoPrenotazione;



    /**
     * Costruttore per la creazione di una prenotazione runtime
     * @param ID_turista id dell' utente corrente
     * @param ID_esperienza id dell' esperienza da prenotare
     * @param nome_esperienza nome dell' esperienza
     * @param posti posti da riservare
     * @param maxGiorniRiserva calcolo giorni di validità della prenotazione
     * @param prezzo_singolo prezzo dell' esperienza del singolo posto
     * @param valuta dell' importo
     * @throws IllegalStateException se non è più possibile prenotare
     */
    public SimplePrenotazione(int ID_turista, int ID_esperienza, String nome_esperienza, int posti,
                               LocalDateTime data_inizio_esperienza ,int maxGiorniRiserva,BigDecimal prezzo_singolo, String valuta){
        this.ID_turista = ID_turista;
        this.ID_esperienza = ID_esperienza;
        this.nomeEsperienza = nome_esperienza;
        this.scadenza = LocalDateTime.now().plus(maxGiorniRiserva, ChronoUnit.DAYS).truncatedTo(ChronoUnit.HOURS);
        if(scadenza.isAfter(data_inizio_esperienza)){
            this.scadenza = data_inizio_esperienza;
        }
        this.posti = posti;
        this.prezzo_totale = prezzo_singolo.multiply(BigDecimal.valueOf(posti));
        this.valuta = valuta;
        this.statoPrenotazione = StatoPrenotazione.RISERVATA;
    }

    /**
     * Costruttore per la creazione di un esperienza già creata in precedenza
     * @param ID_prenotazione id della prenotazione
     * @param ID_esperienza id dell' esperienza prenotata
     * @param ID_turista id dell' utente che ha effettuato la prenotazione
     * @param nomeEsperienza nome dell' esperienza
     * @param posti numero di posti prenotati
     * @param scadenza data ultima di validità
     * @param prezzo_totale costo di un eventuale pagamento
     * @param valuta dell' importo
     * @param statoPrenotazione stato corrente <code>{@link StatoPrenotazione}</code>
     *
     */
    public SimplePrenotazione(int ID_prenotazione, int ID_esperienza, int ID_turista, String nomeEsperienza, int posti,
                              LocalDateTime scadenza, BigDecimal prezzo_totale, String valuta,
                              StatoPrenotazione statoPrenotazione) {
        this.ID_prenotazione = ID_prenotazione;
        this.ID_esperienza = ID_esperienza;
        this.ID_turista = ID_turista;
        this.nomeEsperienza = nomeEsperienza;
        this.posti = posti;
        this.scadenza = scadenza;
        this.prezzo_totale = prezzo_totale;
        this.statoPrenotazione = statoPrenotazione;
        this.valuta = valuta;
    }

    /**
     * costruttore da utilizzare nel caso di una trasformazione da invito a prenotazione
     * @param id_turista  id del creatore della prenotazione
     * @param ID_esperienza
     * @param posti
     * @param scadenza
     * @param prezzo_singolo
     * @param valuta dell' importo
     */
    public SimplePrenotazione( int id_turista , int ID_esperienza, int posti,
                               LocalDateTime scadenza ,BigDecimal prezzo_singolo, String valuta){
        this.ID_turista = id_turista;
        this.ID_esperienza = ID_esperienza;
        this.scadenza = scadenza;
        this.posti = posti;
        String posti_s = String.valueOf(posti);
        this.data_prenotazione  = LocalDateTime.now();
        this.prezzo_totale = prezzo_singolo.multiply(new BigDecimal(posti_s));
        this.valuta = valuta;
        this.statoPrenotazione = StatoPrenotazione.RISERVATA;
    }

    /**
     * Runtime
     * @param ID_turista
     * @param ID_esperienza
     * @param posti
     * @param data_inizio_esperienza
     * @param maxGiorniRiserva
     * @param prezzo_singolo
     * @param valuta
     */
    public SimplePrenotazione(int ID_turista, int ID_esperienza, int posti,
                              LocalDateTime data_inizio_esperienza ,int maxGiorniRiserva,BigDecimal prezzo_singolo, String valuta){
        this.ID_turista = ID_turista;
        this.ID_esperienza = ID_esperienza;
        this.data_prenotazione = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
        this.scadenza = LocalDateTime.now().plus(maxGiorniRiserva, ChronoUnit.DAYS).truncatedTo(ChronoUnit.HOURS);
        if(scadenza.isAfter(data_inizio_esperienza)){
            this.scadenza = data_inizio_esperienza;
        }
        this.posti = posti;
        this.prezzo_totale = prezzo_singolo.multiply(BigDecimal.valueOf(posti));
        this.valuta = valuta;
        this.statoPrenotazione = StatoPrenotazione.RISERVATA;
    }

    /**
     * DAL DB
     * @param id_prenotazione
     * @param id_esperienza
     * @param uid_turista
     * @param stato_prenotazione
     * @param posti_prenotati
     * @param data_prenotazione
     * @param data_scadenza_riserva
     * @param costo_totale
     * @param valuta
     */
    public SimplePrenotazione(int id_prenotazione, int id_esperienza, int uid_turista, StatoPrenotazione stato_prenotazione,
                              int posti_prenotati, LocalDateTime data_prenotazione, LocalDateTime data_scadenza_riserva,
                              BigDecimal costo_totale, String valuta){

        this.ID_prenotazione = id_prenotazione;
        this.ID_esperienza = id_esperienza;
        this.ID_turista = uid_turista;
        this.posti = posti_prenotati;
        this.data_prenotazione = data_prenotazione;
        this.scadenza = data_scadenza_riserva;
        this.prezzo_totale = costo_totale;
        this.statoPrenotazione = stato_prenotazione;
        this.valuta = valuta;
    }

    @Override
    public int getID_prenotazione() {
        return ID_prenotazione;
    }

    @Override
    public int getID_esperienza() {
        return ID_esperienza;
    }

    @Override
    public int getID_turista() {
        return ID_turista;
    }

    @Override
    public String getNomeEsperienza() {
        return nomeEsperienza;
    }

    @Override
    public int getPosti() {
        return posti;
    }

    @Override
    public LocalDateTime getDataPrenotazione() {
        return data_prenotazione;
    }
    @Override
    public LocalDateTime getScadenza() {
        return scadenza;
    }

    @Override
    public BigDecimal getPrezzo_totale() {
        return prezzo_totale;
    }

    @Override
    public String getValuta() {
        return valuta;
    }

    @Override
    public StatoPrenotazione getStatoPrenotazione() {
        return statoPrenotazione;
    }

    @Override
    public void cambiaStatoPrenotazione(StatoPrenotazione nuovoStatoPrenotazione) {
        this.statoPrenotazione = nuovoStatoPrenotazione;
    }

    @Override
    public void assegna_id(int id) {
        this.ID_prenotazione = id;
    }

    @Override
    public String toString() {
        return "SimplePrenotazione{" +
                "ID_prenotazione=" + ID_prenotazione +
                ", ID_esperienza=" + ID_esperienza +
                ", ID_turista=" + ID_turista +
                ", posti=" + posti +
                ", data_prenotazione=" + data_prenotazione +
                ", scadenza=" + scadenza +
                ", statoPrenotazione=" + statoPrenotazione +
                ", costo_totale=" + new Money(getPrezzo_totale(), getValuta()).toString() +
                '}';
    }
}
