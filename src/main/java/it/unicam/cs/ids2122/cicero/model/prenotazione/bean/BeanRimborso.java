package it.unicam.cs.ids2122.cicero.model.prenotazione.bean;

import it.unicam.cs.ids2122.cicero.model.esperienza.Esperienza;
import it.unicam.cs.ids2122.cicero.model.esperienza.IEsperienza;


import java.time.LocalDateTime;

public final class BeanRimborso implements Rimborso {

    private LocalDateTime data_creazione;
    private Modulo modulo ;

    public BeanRimborso (){
        data_creazione = LocalDateTime.now();
    }

    public LocalDateTime getData_creazione() {
        return data_creazione;
    }

    public void setData_creazione(LocalDateTime data_creazione) {
        this.data_creazione = data_creazione;
    }


    @Override
    public boolean automatico(BeanFattura fattura, IEsperienza esperienza) {
        return fattura.getData_pagamento().isBefore(esperienza.info().getDataInizio());
    }

    @Override
    public BeanRimborso richiede_rimborso(BeanFattura fattura, Esperienza esperienza) {
        if(this.data_creazione.isAfter(esperienza.getDataFine()) && this.data_creazione.isBefore(esperienza.getDataTermine())){
          BeanRimborso rimborso = new BeanRimborso();
          rimborso.setModulo(new Modulo(fattura));
          return rimborso;
        }else
            return null;
    }


    public Modulo getModulo() {
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }

    private class Modulo {

        private String modulo;
        private boolean approvata;
        private BeanFattura fattura;

        public Modulo(BeanFattura fattura){
            this.modulo = "";
            this.approvata = false;
            this.fattura = fattura;
        }

        public void compila(String modulo){
            this.modulo = modulo;
        }

        public void approva(){
            approvata = true;
        }

        public boolean isApprovata() {
            return approvata;
        }
    }
}
