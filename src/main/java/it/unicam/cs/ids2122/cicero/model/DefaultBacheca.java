package it.unicam.cs.ids2122.cicero.model;

import it.unicam.cs.ids2122.cicero.model.esperienza.Esperienza;
import it.unicam.cs.ids2122.cicero.ruoli_LEGACY.Cicerone;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Rappresenta una implementazione di default della <code>Bacheca</code> della piattaforma Cicero.
 */
public class DefaultBacheca implements Bacheca {

    private List<Cicerone> allCiceroni;
    private Map<Integer, Esperienza> allEsperienze;

    /**
     * Permette di costruire una {@link Bacheca} di default.
     * @param allCiceroni collezione di tutti i ciceroni nella piattaforma Cicero.
     */
    public DefaultBacheca(List<Cicerone> allCiceroni) {
        if(allCiceroni==null)throw new IllegalArgumentException("La lista di ciceroni e' null");
        this.allCiceroni = allCiceroni;
        allEsperienze=new HashMap<>();
        for(Cicerone c:allCiceroni){
            for(Esperienza e:c.getAllEsperienze()){
                allEsperienze.put(e.getId(),e);
            }
        }
    }

    @Override
    public List<Esperienza> getAllEsperienze() {
        return new ArrayList<>(allEsperienze.values());
    }

    @Override
    public Esperienza getEsperienza(int id) {
        return allEsperienze.get(id);
    }

    @Override
    public void addCicerone(Cicerone c) {
        if(c==null)throw new IllegalArgumentException("Cicerone da aggiungere alla bacheca e' null");
        allCiceroni.add(c);
        for(Esperienza e:c.getAllEsperienze()){
            allEsperienze.put(e.getId(),e);
        }
    }
}