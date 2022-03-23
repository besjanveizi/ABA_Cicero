package it.unicam.cs.ids2122.cicero.ruoli;

import it.unicam.cs.ids2122.cicero.model.service.InfoEsperienza;
import it.unicam.cs.ids2122.cicero.model.esperienza.*;

import java.util.List;

/**
 * Rappresenta un utente <code>Cicerone</code> nella piattaforma Cicero.
 */
public class DefaultCicerone implements Cicerone {

    private int id;
    private String name;
    private String email;
    private GestoreEsperienze gestoreEsperienze;
    private BuilderEsperienza builder;
    private DirectorEsperienza director;

    /**
     * Permette di creare un nuovo utente <code>Cicerone</code> nella piattaforma Cicero.
     * @param name nome del <code>Cicerone</code>.
     * @param email email del <code>Cicerone</code>.
     */
    public DefaultCicerone(String name, String email) {
        // TODO: id should be given by the database primary key of ciceroni table
        this.id = this.hashCode();
        this.name = name;
        this.email = email;
        // TODO: update ciceroni table with this new entry
        this.gestoreEsperienze = new GestoreEsperienze(this);
    }

    @Override
    public void creaEsperienza(InfoEsperienza infoE) {
        this.director = new DirectorEsperienza(infoE);

        // TODO: check type,
        //  if type == Visita,
        //          create builderVisita
        //          call director.creaVisita(builderVisita)
        //  elseif type == Escursione,
        //          create builderEscursione
        //          call director.creaEscursione(builderEscursione)
        this.builder = new BuilderSimpleEsperienza(this);

        director.creaSimpleEsperienza(builder);
        Esperienza nuovaEsperienza = builder.getResult();
        getGestoreEsperienze().add(nuovaEsperienza);
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
