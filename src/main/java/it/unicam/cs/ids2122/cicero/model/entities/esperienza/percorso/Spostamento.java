package it.unicam.cs.ids2122.cicero.model.entities.esperienza.percorso;

public class Spostamento {
    private int id;
    private int indice_spostamento;
    private Tappa partenza;
    private Tappa destinazione;
    private String info;
    public Spostamento(int indice_spostamento, Tappa partenza, Tappa destinazione, String info) {
        this.indice_spostamento = indice_spostamento;
        this.partenza = partenza;
        this.destinazione = destinazione;
        this.info = info;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getIndice() {
        return indice_spostamento;
    }

    public void reset() {
        partenza.getListAttivita().clear();
        destinazione.getListAttivita().clear();
    }

    @Override
    public String toString() {
        return "\nSpostamento n. "+ indice_spostamento +
                "\n\tTappa di partenza:" + partenza.toString() +
                "\n\tTappa di destinazione:" + destinazione.toString() +
                "\n\tinformazioni dello spostamento:'" + info + '\'';
    }
}
