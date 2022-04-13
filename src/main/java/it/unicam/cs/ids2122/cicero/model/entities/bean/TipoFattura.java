package it.unicam.cs.ids2122.cicero.model.entities.bean;

public enum  TipoFattura {

    /**
     * semplice pagamento, stato di default.
     */
    PAGAMENTO(0),

    /**
     *  se la fattura è un rimborso.
     */
    RIMBORSO(1),

    /**
     * se la fattura rappresenta il compenso ad un Cicerone.
     */
    LIQUIDAZIONE(2);

    private int tipo;

    TipoFattura(int i) {
        this.tipo = i;
    }

    public int getTipo() {
        return tipo;
    }
}
