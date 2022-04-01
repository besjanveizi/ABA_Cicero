package it.unicam.cs.ids2122.cicero.model.prenotazione_V3.rimborso;

import it.unicam.cs.ids2122.cicero.model.prenotazione_V3.pagamento.Fattura;

public class RichiestaRimborso {

    private String modulo;
    private boolean approvata;
    private Fattura fattura;

    public RichiestaRimborso(Fattura fattura){
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
