package it.unicam.cs.ids2122.cicero.model.entities.bean;

public enum  TipoFattura {


    PAGAMENTO(0), RIMBORSO(1), LIQUIDAZIONE(2);

    private int tipo;

    TipoFattura(int i) {
        this.tipo = i;
    }

    public int getTipo() {
        return tipo;
    }
}
