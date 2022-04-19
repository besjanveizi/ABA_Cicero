package it.unicam.cs.ids2122.cicero.model.entities.bean;


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Currency;
import java.util.Objects;

public final class BeanPrenotazione implements Serializable {

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


    public BeanPrenotazione(){}

    public BeanPrenotazione(LocalDateTime data_inizio_esperienza, int maxGiorniRiserva,int posti, BigDecimal importo, String valuta){

        this.scadenza = LocalDateTime.now().plus(maxGiorniRiserva, ChronoUnit.DAYS).truncatedTo(ChronoUnit.SECONDS);

        if(scadenza.isAfter(data_inizio_esperienza)){
            this.scadenza = data_inizio_esperienza;
        }

        this.posti = posti;
        this.prezzo_totale = importo.multiply(BigDecimal.valueOf(posti));
        this.valuta = valuta;
        this.data_prenotazione = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    }



    public int getID_prenotazione() {
        return ID_prenotazione;
    }


    public void setID_prenotazione(int ID_prenotazione) {
        this.ID_prenotazione = ID_prenotazione;
    }


    public int getID_esperienza() {
        return ID_esperienza;
    }


    public void setID_esperienza(int ID_esperienza) {
        this.ID_esperienza = ID_esperienza;
    }


    public int getID_turista() {
        return ID_turista;
    }


    public void setID_turista(int ID_turista) {
        this.ID_turista = ID_turista;
    }


    public int getPosti() {
        return posti;
    }


    public void setPosti(int posti) {
        this.posti = posti;
    }


    public LocalDateTime getData_prenotazione() {
        return data_prenotazione;
    }


    public void setData_prenotazione(LocalDateTime data_prenotazione) {
        this.data_prenotazione = data_prenotazione;
    }


    public LocalDateTime getScadenza() {
        return scadenza;
    }


    public void setScadenza(LocalDateTime scadenza) {
        this.scadenza = scadenza;
    }


    public BigDecimal getPrezzo_totale() {
        return prezzo_totale;
    }


    public void setPrezzo_totale(BigDecimal prezzo_totale) {
        this.prezzo_totale = prezzo_totale;
    }


    public String getValuta() {
        return valuta;
    }


    public void setValuta(String valuta) {
        this.valuta = valuta;
    }

    /**
     * Controlla se un'{@code Esperienza} &egrave valida.
     * @return {@code true} se lo stato dell'{@code Esperienza} non &egrave {@link StatoPrenotazione#CANCELLATA},
     * altrimenti restituisce {@code false}.
     */
    public boolean isValida() {
        return statoPrenotazione != StatoPrenotazione.CANCELLATA;
    }

    public StatoPrenotazione getStatoPrenotazione() {
        return statoPrenotazione;
    }


    public void setStatoPrenotazione(StatoPrenotazione statoPrenotazione) {
        this.statoPrenotazione = statoPrenotazione;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BeanPrenotazione that = (BeanPrenotazione) o;
        return getID_prenotazione() == that.getID_prenotazione() &&
                getID_esperienza() == that.getID_esperienza() &&
                getID_turista() == that.getID_turista();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getID_prenotazione(), getID_esperienza(), getID_turista());
    }

    @Override
    public String toString() {
        return "\n---INFORMAZIONI DELLA PRENOTAZIONE---" +
                "\nID prenotazione: " + ID_prenotazione +
                "\nID esperienza: " + ID_esperienza +
                "\nID turista: " + ID_turista +
                "\nPosti prenotati: " + posti +
                "\nData di prenotazione: " + data_prenotazione +
                (statoPrenotazione == StatoPrenotazione.RISERVATA ?
                        "\nData di scadenza della riserva: " + scadenza : "") +
                "\nStato: " + statoPrenotazione +
                "\nCosto totale: " + prezzo_totale + " " + Currency.getInstance(valuta).getSymbol();
    }

    public String shortToString() {
        return "\n\tID esperienza associata: " + ID_esperienza +
                "\n\tN. posti prenotati: " + posti +
                "\n\tData di prenotazione: " + data_prenotazione +
                (statoPrenotazione == StatoPrenotazione.RISERVATA ?
                        "\n\tData di scadenza della riserva: " + scadenza : "") +
                "\n\tStato: " + statoPrenotazione +
                "\n\tCosto totale: " + prezzo_totale + " " + Currency.getInstance(valuta).getSymbol();
    }
}
