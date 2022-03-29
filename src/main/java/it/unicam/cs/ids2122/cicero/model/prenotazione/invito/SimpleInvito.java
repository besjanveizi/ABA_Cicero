package it.unicam.cs.ids2122.cicero.model.prenotazione.invito;



import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class SimpleInvito implements FunInvito,  PropInvito {

    private int id_invito;
    private int id_mittente;
    private int id_esperienza;
    private String email_destinatario;
    private LocalDateTime data_creazione;
    private LocalDateTime data_scadenza_riserva;
    private int posti_riservati;


    /**
     * run time
     * @param id_mittente
     * @param id_esperienza
     * @param email_destinatario
     * @param maxGiorniRiserva
     * @param posti_riservati
     */
    public SimpleInvito(int id_mittente, int id_esperienza, String email_destinatario, LocalDateTime data_inizio,
                        int maxGiorniRiserva, int posti_riservati) {
        this.id_mittente = id_mittente;
        this.id_esperienza = id_esperienza;
        this.email_destinatario = email_destinatario;
        this.data_creazione = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
        this.data_scadenza_riserva = data_creazione.plus(maxGiorniRiserva,ChronoUnit.DAYS).truncatedTo(ChronoUnit.HOURS);
        if(data_scadenza_riserva.isAfter(data_inizio)){
            data_scadenza_riserva = data_inizio;
        }
        this.posti_riservati = posti_riservati;
    }

    /**
     *  dal database
     * @param id_invito
     * @param id_mittente
     * @param id_esperienza
     * @param email_destinatario
     * @param data_creazione
     * @param data_scadenza_riserva
     * @param posti_riservati
     */
    public SimpleInvito(int id_invito, int id_mittente, int id_esperienza, String email_destinatario,
                        LocalDateTime data_creazione, LocalDateTime data_scadenza_riserva, int posti_riservati) {
        this.id_invito = id_invito;
        this.id_mittente = id_mittente;
        this.id_esperienza = id_esperienza;
        this.email_destinatario = email_destinatario;
        this.data_creazione = data_creazione;
        this.data_scadenza_riserva = data_scadenza_riserva;
        this.posti_riservati = posti_riservati;
    }

    @Override
    public int getId_invito() {
        return id_invito;
    }
    @Override
    public int getId_mittente() {
        return id_mittente;
    }

    @Override
    public int getId_esperienza() {
        return id_esperienza;
    }
    @Override
    public String getEmail_destinatario() {
        return email_destinatario;
    }
    @Override
    public LocalDateTime getData_creazione() {
        return data_creazione;
    }
    @Override
    public LocalDateTime getData_scadenza_riserva() {
        return data_scadenza_riserva;
    }
    @Override
    public int getPosti_riservati() {
        return posti_riservati;
    }
}
