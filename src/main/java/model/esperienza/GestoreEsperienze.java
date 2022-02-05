package model.esperienza;

import model.ruoli.Cicerone;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Gestore di tutte le esperienze create dal <code>Cicerone</code> associato.
 */
public class GestoreEsperienze{
    private List<Esperienza> esperienze;
    private Cicerone cicerone;

    /**
     * Crea un gestore delle esperienze per il dato <code>Cicerone</code>.
     * @param cicerone <code>Cicerone</code> a cui il gestore si riferisce.
     */
    public GestoreEsperienze(Cicerone cicerone) {
        this.cicerone = cicerone;
        esperienze = new ArrayList<>();
    }

    /**
     * Restituisce il <code>Cicerone</code> del gestore.
     * @return <code>Cicerone</code> del gestore.
     */
    public Cicerone getCicerone() {
        return cicerone;
    }

    /**
     * Restituisce l'esperienza cui l'identificativo è quello dato.
     * @param id identificativo dell'esperienza che si cerca.
     * @return l'esperienza cercata oppure un elemento null.
     */
    public Optional<Esperienza> getEsperienza(int id){
        return esperienze.stream().filter((e) -> e.getId()==id).findFirst();
    }

    /**
     * Restituisce tutte le esperienze create dal <code>Cicerone</code> associato.
     * @return collezione di tutte le esperienze create dal <code>Cicerone</code> associato.
     */
    public List<Esperienza> getAllEsperienze() {
        return new ArrayList<>(esperienze);
    }

    /**
     * Aggiunge un'<code>Esperienza</code> tra quelle create dal <code>Cicerone</code>
     * @param esperienzaCreata nuova <code>Esperienza</code> da aggiungere al gestore.
     */
    public void add(Esperienza esperienzaCreata) {
        esperienze.add(esperienzaCreata);
    }
}
