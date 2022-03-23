package it.unicam.cs.ids2122.cicero.model.prenotazione;



import java.time.LocalDateTime;

/**
 * Rappresenta una semplice <code>Prenotazione</code> nella piattaforma.
 */
public class SimplePrenotazione implements Prenotazione {

    private String nome;
    private int ID;
    private int posti;
    private boolean riserva;
    private int tempoDiRiserva;
    private LocalDateTime data_creazione;
    private LocalDateTime ttl;
    private float prezzo_singolo;
    private float costo_totale;
    private boolean invito;

    public SimplePrenotazione(String nome, int ID, int posti, boolean riserva, int tempoDiRiserva, LocalDateTime data_creazione, LocalDateTime ttl,float prezzo_singolo,float costo_totale,boolean invito){
        this.nome = nome;
        this.ID = ID;
        this.posti = posti;
        this.riserva = riserva;
        this.tempoDiRiserva = tempoDiRiserva;
        this.data_creazione = data_creazione;
        this.ttl = ttl;
        this.prezzo_singolo = prezzo_singolo;
        this.costo_totale = costo_totale;
        this.invito = invito;
    }

    public SimplePrenotazione(BuilderPrenotazione b)  {
        nome = b.nomeEsperienza;
        ID = b.ID;
        posti = b.posti;
        riserva = b.riserva;
        data_creazione = b.data_creazione;
        ttl = b.ttl;
        tempoDiRiserva = b.tempoDiRiserva;
        this.prezzo_singolo = b.prezzo_singolo;
        this.costo_totale = b.prezzo_totale;
        invito = b.con_invito;
    }

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public boolean isRiserva(){
        return riserva;
    }

    @Override
    public boolean isInvito() {
        return invito;
    }

    @Override
    public LocalDateTime getData_creazione(){
        return data_creazione;
    }

    @Override
    public LocalDateTime getTtl() {
        return ttl;
    }

    @Override
    public int getPosti() {
        return posti;
    }

    @Override
    public String getName() {
        return nome;
    }

    @Override
    public int getTempoRiserva() {
        return tempoDiRiserva;
    }

    @Override
    public float getPrezzoSingolo() {
        return prezzo_singolo;
    }

    @Override
    public float getCostoTotale() {
        return costo_totale;
    }
}
