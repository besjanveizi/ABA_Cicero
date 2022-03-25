package it.unicam.cs.ids2122.cicero.model.territorio;

import it.unicam.cs.ids2122.cicero.model.esperienza.percorso.Percorso;

import java.util.HashSet;
import java.util.Set;

public class GestoreAree {
    private Set<Area> aree;
    private Percorso percorso;

    public GestoreAree() {
        aree = updateAree();
        percorso = new Percorso();
    }

    private Set<Area> updateAree() {
        // TODO: update aree with database select request from table "aree"
        //  return DBManager.select(DBTable.AREE) -> crea e ritorna un HashSet di oggetti Area
        return new HashSet<>();  // temporary
    }

    public Set<Area> getAree() {
        return aree;
    }

}
