package it.unicam.cs.ids2122.cicero.model.prenotazione.pagamento;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface PropFattura extends Fattura{


    StatoPagamento getStatoPagamento();

    int getId_fattura();

    int getId_prenotazione();

    String getId_client_destinatario();

    String getId_client_origine();

    BigDecimal getImporto();

    String getValuta();

    LocalDateTime getData_pagamento();

    int getPosti_pagati();
}
