package it.unicam.cs.ids2122.cicero.model.prenotazione_V3.rimborso;

import it.unicam.cs.ids2122.cicero.model.esperienza.Esperienza;
import it.unicam.cs.ids2122.cicero.model.prenotazione_V3.pagamento.Fattura;


import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class SimpleRimborso implements Rimborso{

    private LocalDateTime data_creazione;

    public SimpleRimborso(){
        data_creazione = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
    }

    @Override
    public boolean automatico(Fattura fattura, Esperienza esperienza) {
        return fattura.getData_pagamento().isBefore(esperienza.getDataInizio());
    }

    @Override
    public RichiestaRimborso richiede_rimborso(Fattura fattura,Esperienza esperienza) {
       if(this.data_creazione.isAfter(esperienza.getDataFine()) && this.data_creazione.isBefore(esperienza.getDataTermine())){
           return new RichiestaRimborso(fattura);
       }else
           return null;
    }

    @Override
    public LocalDateTime getDataCreazione() {
        return data_creazione;
    }


}
