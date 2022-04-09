package it.unicam.cs.ids2122.cicero.model.territorio;

import it.unicam.cs.ids2122.cicero.persistence.services.ServiceArea;


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

    public void add(String nome, String descrizione){
        ServiceArea service=ServiceArea.getInstance();
        service.insertArea(nome,descrizione);
        updateAree();
    }
}
