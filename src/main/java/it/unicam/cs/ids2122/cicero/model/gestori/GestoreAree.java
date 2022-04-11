package it.unicam.cs.ids2122.cicero.model.gestori;

import it.unicam.cs.ids2122.cicero.model.entities.territorio.Area;
import it.unicam.cs.ids2122.cicero.model.services.ServiceArea;

import java.util.Set;

/**
 * Rappresenta un gestore per le aree
 */
public class GestoreAree {

    private static GestoreAree instance = null;
    private Set<Area> aree;

    private GestoreAree(){
        updateAree();
    }

    /**
     *
     * @return l'istanza aggiornata del gestore dei toponimi della piattaforma.
     */
    public static GestoreAree getInstance() {
        if (instance == null)
            instance = new GestoreAree();
        return instance;
    }

    private void updateAree() {
        ServiceArea service=ServiceArea.getInstance();
        aree = service.getAree();
    }

    /**
     * @return tutte le aree definite nella piattaforma.
     */
    public Set<Area> getAree() {
        return aree;
    }

    /**
     * aggiunge un nuovo toponimo nella piattaforma
     * @param nome nome area
     * @param descrizione descrizione area
     */
    public void add(String nome, String descrizione){
        ServiceArea service=ServiceArea.getInstance();
        aree.add(service.insertArea(nome,descrizione));
    }
}
