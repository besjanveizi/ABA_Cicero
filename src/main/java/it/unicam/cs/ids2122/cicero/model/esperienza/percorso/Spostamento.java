package it.unicam.cs.ids2122.cicero.model.esperienza.percorso;

public class Spostamento {
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

    public void reset() {
        partenza.getListAttivita().clear();
        destinazione.getListAttivita().clear();
    }
}
