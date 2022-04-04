package it.unicam.cs.ids2122.cicero.model.prenotazione.bean;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public final class BeanInfoPrenotazione {


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



    public int getID_esperienza() {
        return ID_esperienza;
    }

    public int getID_turista() {
        return ID_turista;
    }

    public int getPosti() {
        return posti;
    }

    public LocalDateTime getData_prenotazione() {
        return data_prenotazione;
    }

    public LocalDateTime getScadenza() {
        return scadenza;
    }

    public BigDecimal getPrezzo_totale() {
        return prezzo_totale;
    }

    public String getValuta() {
        return valuta;
    }

    public StatoPrenotazione getStatoPrenotazione() {
        return statoPrenotazione;
    }
}
