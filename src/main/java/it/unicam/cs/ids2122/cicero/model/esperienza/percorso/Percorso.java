package it.unicam.cs.ids2122.cicero.model.esperienza.percorso;

import it.unicam.cs.ids2122.cicero.model.territorio.Area;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Percorso {
    private List<Spostamento> spostamenti;

    public Percorso() {
        spostamenti = new ArrayList<>();
    }

    public void addSpostamento(Tappa partenza, Tappa destinazione, String info) {
        spostamenti.add(new Spostamento(partenza, destinazione, info));
    }

    public List<Tappa> getTappe() {
        List<Tappa> tappe = new ArrayList<>();
        for (Spostamento s : spostamenti) {
            tappe.add(s.getPartenza());
            tappe.add(s.getDestinazione());
        }
        return tappe;
    }

    public Set<Area> getAree() {
        List<Tappa> tappe = getTappe();
        Set<Area> aree = new HashSet<>();
        for (Tappa t : tappe) {
            aree.add(t.getArea());
        }
        return aree;
    }

    @Override
    public String toString() {
        for (Spostamento s : spostamenti) {

        }
        return "Percorso {" +
                "spostamenti=" + spostamenti +
                '}';
    }

    public void reset() {
        spostamenti.forEach(Spostamento::reset);
    }
}
