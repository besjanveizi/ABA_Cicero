package it.unicam.cs.ids2122.cicero.model.territorio;

import it.unicam.cs.ids2122.cicero.model.esperienza.percorso.Percorso;

import java.util.HashSet;
import java.util.Set;

/**
 * Rappresenta un gestore per le aree
 */
public class GestoreAree {

    private static GestoreAree instance = null;
    private Set<Area> aree;

    private GestoreAree() {
        updateAree();
    }

    public static GestoreAree getInstance() {
        if (instance == null)
            instance = new GestoreAree();
        return instance;
    }

    private void updateAree() {
        // TODO: update aree with database select request from table "aree"
        //  return DBManager.select(DBTable.AREE) -> crea e ritorna un HashSet di oggetti SimpleArea
        aree = new HashSet<>(); // temporary
    }

    /**
     * @return tutte le aree definite nella piattaforma.
     */
    public Set<Area> getAree() {
        return aree;
    }

}
