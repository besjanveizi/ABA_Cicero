package it.unicam.cs.ids2122.cicero.model.entities.esperienza.percorso;

public class Attivita {

    int id;
    private String nome;
    private String descrizione;
    private int indice_attivita;

    public Attivita(int indice_attivita, String nome, String descrizione) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.indice_attivita = indice_attivita;
    }


    public String getNome() {
        return nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public int getIndice() {
        return indice_attivita;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "\t\t\tAttivita n." + getIndice() +
                "\n\t\t\t\tnome: '" + nome + '\'' +
                "\n\t\t\t\tdescrizione: '" + descrizione + "'\n";
    }
}
