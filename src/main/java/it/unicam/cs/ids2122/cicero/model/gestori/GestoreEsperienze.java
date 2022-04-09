package it.unicam.cs.ids2122.cicero.model.gestori;

import it.unicam.cs.ids2122.cicero.model.entities.esperienza.EsperienzaStatus;
import it.unicam.cs.ids2122.cicero.model.entities.esperienza.IEsperienza;
import it.unicam.cs.ids2122.cicero.model.entities.esperienza.InfoEsperienza;
import it.unicam.cs.ids2122.cicero.model.services.ServiceEsperienza;
import it.unicam.cs.ids2122.cicero.ruoli.Cicerone;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Gestore di tutte le esperienze create dal <code>Cicerone</code> associato.
 */
public class GestoreEsperienze {

    private Set<IEsperienza> esperienze;
    private Cicerone cicerone;
    private static GestoreEsperienze instance = null;
    private static ServiceEsperienza serviceEsperienza;

    /**
     * Crea un gestore delle esperienze per il dato <code>Cicerone</code>.
     * @param cicerone <code>Cicerone</code> a cui il gestore si riferisce.
     */
    private GestoreEsperienze(Cicerone cicerone) {
        this.cicerone = cicerone;
        updateEsperienze();
    }

    public static GestoreEsperienze getInstance(Cicerone cicerone) {
        if (instance == null) instance = new GestoreEsperienze(cicerone);
        return instance;
    }

    private void updateEsperienze() {
        Set<IEsperienza> temp = serviceEsperienza.download(cicerone.getUID());
        // TODO:
        //  -> rimpiazza il set esperienze corrente con uno aggiornato dal database
        //      esperienze.clear();
        //      esperienze.addAll(temp);
    }

    /**
     * Restituisce l'esperienza cui l'identificativo è quello dato.
     * @param id identificativo dell'esperienza che si cerca.
     * @return l'esperienza con l'id dato oppure null se non trovata.
     */
    public IEsperienza getEsperienza(int id){
        return esperienze.stream().filter((e) -> e.getId()==id).findFirst().orElseThrow(NullPointerException::new);
    }

    /**
     * Restituisce tutte le esperienze create dal <code>Cicerone</code> associato.
     * @return collezione di tutte le esperienze create dal <code>Cicerone</code> associato.
     */
    public Set<IEsperienza> getAllEsperienze(Predicate<IEsperienza> p) {
        return esperienze.stream().filter(p).collect(Collectors.toSet());
    }

    /**
     * Aggiunge un'<code>Esperienza</code> tra quelle create dal <code>Cicerone</code>.
     * @param infoEsperienza informazioni dell'<code>Esperienza</code>.
     */
    public void add(InfoEsperienza infoEsperienza) {
        serviceEsperienza.upload(infoEsperienza);
        // TODO:
        //  -> crea una nuova entry nel db e ritorna l'id generato
        //      int id = ServiceEsperienza.upload(infoEsperienza);
        //  -> crea l'oggetto Esperienza passando l'id generato e infoEsperienza
        //      Esperienza e = new SimpleEsperienza(id, infoEsperienza);
        //  -> aggiungi la nuova istanza Esperienza al set del gestore
        //      esperienze.add(e);
    }

    /**
     * Modifica lo stato dell'{@code Esperienza} corrispondente all'identificativo dato, nel nuovo stato dato.
     * @param id identificativo dell'{@code Esperienza}.
     * @param newStatus nuovo stato dell'{@code Esperienza}.
     */
    public void updateStatus(int id, EsperienzaStatus newStatus) {
        // TODO:
        //  -> cambia lo stato al database
        //      ServiceEsperienza.changeState(id, newStatus);
        //      getEsperienza(id).info().setStatus(newStatus);
    }
}
