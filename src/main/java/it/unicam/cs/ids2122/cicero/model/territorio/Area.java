package it.unicam.cs.ids2122.cicero.model.territorio;

public class Area {
    private String toponimo;
    private String descrizione;

    public Area(String toponimo, String descrizione) {
        this.toponimo = toponimo;
        this.descrizione = descrizione;
    }

    public String getToponimo() {
        return this.toponimo;
    }
}
