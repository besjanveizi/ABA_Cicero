package it.unicam.cs.ids2122.cicero.model.entities.esperienza.percorso;

public class Attivita {

    int id;
    private String nome;
    private String descrizione;

    public Attivita(String nome, String descrizione) {
        this.nome = nome;
        this.descrizione = descrizione;
    }

    public Attivita(int id, String nome, String descrizione) {
        this.id = id;
        this.nome = nome;
        this.descrizione = descrizione;
    }

    public String getNome() {
        return nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public int getId() {
        return id;
    }
}
