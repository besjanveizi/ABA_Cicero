package it.unicam.cs.ids2122.cicero.model.prenotazione_v2;



import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class SimplePrenotazione implements PropPrenotazione,FunPrenotazione {

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
     * data inizio dell' esperienza, influenza la
     * validità della prenotazione
     * PRENOTATA -> CANCELLATA
     */
    private LocalDateTime data_inizio_esperienza;

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
     * @param data_inizio_esperienza data inizio dell' esperienza
     * @param maxGiorniRiserva calcolo giorni di validità della prenotazione
     * @param prezzo_singolo prezzo dell' esperienza del singolo posto
     * @param valuta dell' importo
     * @throws IllegalStateException se non è più possibile prenotare
     */
    public SimplePrenotazione(int ID_turista, int ID_esperienza, String nome_esperienza, int posti,
                              LocalDateTime data_inizio_esperienza, int maxGiorniRiserva,BigDecimal prezzo_singolo, String valuta){
        this.ID_turista = ID_turista;
        this.ID_esperienza = ID_esperienza;
        this.nomeEsperienza = nome_esperienza;
        this.scadenza = LocalDateTime.now().plus(maxGiorniRiserva, ChronoUnit.DAYS).truncatedTo(ChronoUnit.HOURS);
        this.data_inizio_esperienza = data_inizio_esperienza;
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
     * @param data_inizio_esperienza data inizio esperienza
     * @param scadenza data ultima di validità
     * @param prezzo_totale costo di un eventuale pagamento
     * @param valuta dell' importo
     * @param statoPrenotazione stato corrente <code>{@link StatoPrenotazione}</code>
     *
     */
    public SimplePrenotazione(int ID_prenotazione, int ID_esperienza, int ID_turista, String nomeEsperienza, int posti,
                              LocalDateTime data_inizio_esperienza, LocalDateTime scadenza,
                              BigDecimal prezzo_totale, String valuta,StatoPrenotazione statoPrenotazione) {
        this.ID_prenotazione = ID_prenotazione;
        this.ID_esperienza = ID_esperienza;
        this.ID_turista = ID_turista;
        this.nomeEsperienza = nomeEsperienza;
        this.posti = posti;
        this.data_inizio_esperienza = data_inizio_esperienza;
        this.scadenza = scadenza;
        this.prezzo_totale = prezzo_totale;
        this.statoPrenotazione = statoPrenotazione;
        this.valuta = valuta;
    }

    /**
     * costruttore da utilizzare nel caso di una trasformazione da invito a prenotazione
     * @param ID_turista
     * @param ID_esperienza
     * @param nome_esperienza
     * @param posti
     * @param data_inizio_esperienza
     * @param scadenza
     * @param prezzo_singolo
     * @param valuta dell' importo
     */
    public SimplePrenotazione(int ID_turista, int ID_esperienza, String nome_esperienza, int posti,
                              LocalDateTime data_inizio_esperienza, LocalDateTime scadenza ,BigDecimal prezzo_singolo, String valuta){
        this.ID_turista = ID_turista;
        this.ID_esperienza = ID_esperienza;
        this.nomeEsperienza = nome_esperienza;
        this.scadenza = scadenza;
        this.data_inizio_esperienza = data_inizio_esperienza;
        this.posti = posti;
        this.prezzo_totale = prezzo_singolo.multiply(BigDecimal.valueOf(posti));
        this.valuta = valuta;
        this.statoPrenotazione = StatoPrenotazione.RISERVATA;
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
    public LocalDateTime getData_inizio_esperienza() {
        return data_inizio_esperienza;
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


}
