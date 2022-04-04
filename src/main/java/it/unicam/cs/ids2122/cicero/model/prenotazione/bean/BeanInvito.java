package it.unicam.cs.ids2122.cicero.model.prenotazione.bean;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public final class BeanInvito {

    private int id_invito;
    private int id_mittente;
    private int id_esperienza;
    private String email_destinatario;
    private LocalDateTime data_creazione;
    private LocalDateTime data_scadenza_riserva;
    private int posti_riservati;

    private BigDecimal importo;
    private String valuta;

    public BeanInvito (){}

    public BeanInvito(LocalDateTime data_inizio, int maxGiorniRiserva){
        this.data_creazione = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
        this.data_scadenza_riserva = data_creazione.plus(maxGiorniRiserva,ChronoUnit.DAYS).truncatedTo(ChronoUnit.HOURS);
        if(data_scadenza_riserva.isAfter(data_inizio)){
            data_scadenza_riserva = data_inizio;
        }
    }

    public int getId_invito() {
        return id_invito;
    }

    public void setId_invito(int id_invito) {
        this.id_invito = id_invito;
    }

    public int getId_mittente() {
        return id_mittente;
    }

    public void setId_mittente(int id_mittente) {
        this.id_mittente = id_mittente;
    }

    public int getId_esperienza() {
        return id_esperienza;
    }

    public void setId_esperienza(int id_esperienza) {
        this.id_esperienza = id_esperienza;
    }

    public String getEmail_destinatario() {
        return email_destinatario;
    }

    public void setEmail_destinatario(String email_destinatario) {
        this.email_destinatario = email_destinatario;
    }

    public LocalDateTime getData_creazione() {
        return data_creazione;
    }

    public void setData_creazione(LocalDateTime data_creazione) {
        this.data_creazione = data_creazione;
    }

    public LocalDateTime getData_scadenza_riserva() {
        return data_scadenza_riserva;
    }

    public void setData_scadenza_riserva(LocalDateTime data_scadenza_riserva) {
        this.data_scadenza_riserva = data_scadenza_riserva;
    }

    public int getPosti_riservati() {
        return posti_riservati;
    }

    public void setPosti_riservati(int posti_riservati) {
        this.posti_riservati = posti_riservati;
    }

    public BigDecimal getImporto() {
        return importo;
    }

    public void setImporto(BigDecimal importo) {
        this.importo = importo;
    }

    public String getValuta() {
        return valuta;
    }

    public void setValuta(String valuta) {
        this.valuta = valuta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BeanInvito that = (BeanInvito) o;
        return getId_invito() == that.getId_invito();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId_invito());
    }

    @Override
    public String toString() {
        return "BeanInvito{" +
                "id_invito=" + id_invito +
                ", id_mittente=" + id_mittente +
                ", id_esperienza=" + id_esperienza +
                ", email_destinatario='" + email_destinatario + '\'' +
                ", data_creazione=" + data_creazione +
                ", data_scadenza_riserva=" + data_scadenza_riserva +
                ", posti_riservati=" + posti_riservati +
                ", importo=" + importo +
                ", valuta='" + valuta + '\'' +
                '}';
    }
}
