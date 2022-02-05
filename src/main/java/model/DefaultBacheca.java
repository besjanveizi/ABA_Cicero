package model;

import model.ruoli.Cicerone;

import java.util.List;

/**
 * Rappresenta una implementazione di default della <code>Bacheca</code> della piattaforma Cicero.
 */
public class DefaultBacheca implements Bacheca {

    private List<Cicerone> allCiceroni;
    private List<Esperienza> allEsperienze;

    /**
     * Permette di costruire una <code>Bacheca</code> di default.
     * @param allCiceroni collezione di tutti i ciceroni nella piattaforma Cicero.
     */
    public DefaultBacheca(List<Cicerone> allCiceroni) {
        this.allCiceroni = allCiceroni;
        allCiceroni.forEach(Cicerone::getAllEsperienze);
    }

    @Override
    public List<Esperienza> showAllEsperienze() {
        return null;
    }

    @Override
    public Esperienza showEsperienza(int id) {
        return null;
    }
}