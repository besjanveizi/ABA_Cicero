package it.unicam.cs.ids2122.cicero.model.prenotazione.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public final class BeanFattura implements Serializable {

    private StatoPagamento statoPagamento;
    private int id_fattura;
    private int id_prenotazione;
    private String id_client_destinatario;
    private String id_client_origine;
    private BigDecimal importo;
    private LocalDateTime data_pagamento;
    private int posti_pagati;
    private String valuta;

    public BeanFattura(){ }

    public StatoPagamento getStatoPagamento() {
        return statoPagamento;
    }

    public void setStatoPagamento(StatoPagamento statoPagamento) {
        this.statoPagamento = statoPagamento;
    }

    public int getId_fattura() {
        return id_fattura;
    }

    public void setId_fattura(int id_fattura) {
        this.id_fattura = id_fattura;
    }

    public int getId_prenotazione() {
        return id_prenotazione;
    }

    public void setId_prenotazione(int id_prenotazione) {
        this.id_prenotazione = id_prenotazione;
    }

    public String getId_client_destinatario() {
        return id_client_destinatario;
    }

    public void setId_client_destinatario(String id_client_destinatario) {
        this.id_client_destinatario = id_client_destinatario;
    }

    public String getId_client_origine() {
        return id_client_origine;
    }

    public void setId_client_origine(String id_client_origine) {
        this.id_client_origine = id_client_origine;
    }

    public BigDecimal getImporto() {
        return importo;
    }

    public void setImporto(BigDecimal importo) {
        this.importo = importo;
    }

    public LocalDateTime getData_pagamento() {
        return data_pagamento;
    }

    public void setData_pagamento(LocalDateTime data_pagamento) {
        this.data_pagamento = data_pagamento;
    }

    public int getPosti_pagati() {
        return posti_pagati;
    }

    public void setPosti_pagati(int posti_pagati) {
        this.posti_pagati = posti_pagati;
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
        BeanFattura that = (BeanFattura) o;
        return getId_fattura() == that.getId_fattura();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId_fattura());
    }


    @Override
    public String toString() {
        return "BeanFattura{" +
                "statoPagamento=" + statoPagamento +
                ", id_fattura=" + id_fattura +
                ", id_prenotazione=" + id_prenotazione +
                ", id_client_destinatario='" + id_client_destinatario + '\'' +
                ", id_client_origine='" + id_client_origine + '\'' +
                ", importo=" + importo +
                ", data_pagamento=" + data_pagamento +
                ", posti_pagati=" + posti_pagati +
                ", valuta='" + valuta + '\'' +
                '}';
    }
}