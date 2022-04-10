package it.unicam.cs.ids2122.cicero.model.entities.esperienza.percorso;

public class Spostamento {
    private int id;
    private Tappa partenza;
    private Tappa destinazione;
    private String info;
    public Spostamento(Tappa partenza, Tappa destinazione, String info) {
        this.partenza = partenza;
        this.destinazione = destinazione;
        this.info = info;
    }

    public Tappa getPartenza() {
        return partenza;
    }

    public Tappa getDestinazione() {
        return destinazione;
    }

    public String getInfoSpostamento() {
        return info;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void reset() {
        partenza.getListAttivita().clear();
        destinazione.getListAttivita().clear();
    }
}
