package model.ruoli;

import model.Esperienza;
import model.GestoreEsperienze;

import java.util.List;

/**
 * Rappresenta un utente <code>Cicerone</code> nella piattaforma Cicero.
 */
public class DefaultCicerone implements Cicerone {

    private int id;
    private String name;
    private String email;
    private GestoreEsperienze gestoreEsperienze;

    /**
     * Permette di creare un nuovo utente <code>Cicerone</code> nella piattaforma Cicero.
     * @param name nome del <code>Cicerone</code>.
     * @param email email del <code>Cicerone</code>.
     */
    public DefaultCicerone(String name, String email) {
        this.name = name;
        this.email = email;
        this.gestoreEsperienze = new GestoreEsperienze(this);
    }

    @Override
    public void creaEsperienza() {
        // create new esperienza
        // add it to gestoreEsperienza
    }

    @Override
    public List<Esperienza> getAllEsperienze(){
        return getGestoreEsperienze().getAllEsperienze();
    }

    private GestoreEsperienze getGestoreEsperienze() {
        return this.gestoreEsperienze;
    }

    @Override
    public String toString() {
        return "DefaultCicerone {" +
                " name = '" + name + '\'' +
                "; email = '" + email + '\'' +
                " }";
    }
}
