package it.unicam.cs.ids2122.cicero.model.entities.esperienza.percorso;

import it.unicam.cs.ids2122.cicero.model.entities.territorio.Area;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Tappa {
    private int id;
    private Area area;
    private List<Attivita> listAttivita;
    private String info;

    public Tappa(Area area, String info) {
        this.area = area;
        this.info = info;
        this.listAttivita = new ArrayList<>();
    }

    public Tappa(int id, Area area, String info) {
        this.id = id;
        this.area = area;
        this.info = info;
    }

    public String getToponimo() {
        return getArea().getToponimo();
    }

    public Area getArea() {
        return area;
    }

    public void addAttivita(String nomeAttivita, String descrizioneAttivita) {
        listAttivita.add(new Attivita(nomeAttivita, descrizioneAttivita));
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Tappa: " +
                "\n\ttoponimo: " + getToponimo() +
                "\n\tinfo: " + info +
                "\n\tattività: " +
                String.join("\n\t",
                        listAttivita.stream().map(Attivita::toString).collect(Collectors.toSet()));
    }

    public List<Attivita> getListAttivita() {
        return listAttivita;
    }
}
