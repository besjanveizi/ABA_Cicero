package it.unicam.cs.ids2122.cicero.model.prenotazione_v2.pagamento;






import it.unicam.cs.ids2122.cicero.model.prenotazione_v2.SystemConstraints;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


public class SimpleFattura implements FunFattura,  PropFattura {


    private StatoPagamento statoPagamento;
    private int id_fattura;
    private int id_prenotazione;
    private String id_client_destinatario;
    private String id_client_origine;
    private BigDecimal importo;
    private LocalDateTime data_pagamento;
    private int posti_pagati;
    private String valuta;


    /**
     * Creazione run time
     * @param id_prenotazione
     * @param posti_prenotati
     * @param importo
     * @param valuta
     */
    public SimpleFattura( int id_prenotazione, int posti_prenotati,
                          BigDecimal importo, String valuta){
        this.id_prenotazione = id_prenotazione;
        this.posti_pagati = posti_prenotati;
        this.importo = importo;
        this.valuta = valuta;
        this.data_pagamento = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
        this.statoPagamento = StatoPagamento.TURISTA_ADMIN;
    }


    /**
     * Costruttore persistenza
     * @param statoPagamento
     * @param id_fattura
     * @param id_prenotazione
     * @param id_client_destinatario
     * @param id_client_origine
     * @param importo
     * @param valuta
     * @param data_pagamento
     * @param posti_prenotati
     */
    public SimpleFattura(StatoPagamento statoPagamento, int id_fattura, int id_prenotazione,
                         String id_client_destinatario, String id_client_origine, BigDecimal importo,String valuta,
                         LocalDateTime data_pagamento, int posti_prenotati) {
        this.statoPagamento = statoPagamento;
        this.id_fattura = id_fattura;
        this.id_prenotazione = id_prenotazione;
        this.id_client_destinatario = id_client_destinatario;
        this.id_client_origine = id_client_origine;
        this.importo = importo;
        this.valuta = valuta;
        this.data_pagamento = data_pagamento;
        this.posti_pagati = posti_prenotati;
    }

    @Override
    public void turista_admin(String id_client_origine) {
        this.id_client_origine = id_client_origine;
        this.id_client_destinatario = SystemConstraints.ID_CLIENT;
        this.statoPagamento = StatoPagamento.ADMIN_ADMIN;
    }

    @Override
    public void admin_cicerone(String id_client_destinatario) {
        this.id_client_origine = SystemConstraints.ID_CLIENT;
        this.id_client_destinatario = id_client_destinatario;
        this.statoPagamento = StatoPagamento.ADMIN_CICERONE;
    }

    @Override
    public void admin_turista(String id_client_destinatario) {
        this.id_client_origine = SystemConstraints.ID_CLIENT;
        this.id_client_destinatario = id_client_destinatario;
        this.statoPagamento = StatoPagamento.ADMIN_TURISTA;
    }

    @Override
    public StatoPagamento getStatoPagamento() {
        return statoPagamento;
    }

    @Override
    public int getId_fattura() {
        return id_fattura;
    }

    @Override
    public int getId_prenotazione() {
        return id_prenotazione;
    }

    @Override
    public String getId_client_destinatario() {
        return id_client_destinatario;
    }

    @Override
    public String getId_client_origine() {
        return id_client_origine;
    }

    @Override
    public BigDecimal getImporto() {
        return importo;
    }

    @Override
    public String getValuta() {
        return valuta;
    }

    @Override
    public LocalDateTime getData_pagamento() {
        return data_pagamento;
    }

    @Override
    public int getPosti_pagati() {
        return posti_pagati;
    }
}
