package it.unicam.cs.ids2122.cicero.model.entities.esperienza.percorso;

import it.unicam.cs.ids2122.cicero.model.entities.territorio.Area;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Percorso {
    private List<Spostamento> spostamenti;

    public Percorso() {
        spostamenti = new ArrayList<>();
    }

    public void addSpostamento(Tappa partenza, Tappa destinazione, String info) {
        spostamenti.add(new Spostamento(spostamenti.size(), partenza, destinazione, info));
    }

    public void addAllSpostamenti(List<Spostamento> spostamentiList) {
        spostamenti.addAll(spostamentiList);
    }

    public List<Spostamento> getSpostamenti() {
        return spostamenti;
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
        return "Percorso: " +
                spostamenti.stream()
                .map(Spostamento::toString)
                .collect(Collectors.toList()) + '}';
    }

    public void reset() {
        spostamenti.forEach(Spostamento::reset);
    }
}
