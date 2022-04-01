package it.unicam.cs.ids2122.cicero.model.esperienza;

import it.unicam.cs.ids2122.cicero.model.esperienza.percorso.Percorso;
import it.unicam.cs.ids2122.cicero.model.prenotazione.persistenza.DBManager;
import it.unicam.cs.ids2122.cicero.model.prenotazione.persistenza.PostgresDB;
import it.unicam.cs.ids2122.cicero.model.tag.Tag;
import it.unicam.cs.ids2122.cicero.ruoli.Cicerone;
import it.unicam.cs.ids2122.cicero.util.Money;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Gestore di tutte le esperienze create dal <code>Cicerone</code> associato.
 */
public class GestoreEsperienze {

    private Set<Esperienza> esperienze;
    private Cicerone cicerone;
    private static GestoreEsperienze instance = null;
    DBManager dbManager;

    /**
     * Crea un gestore delle esperienze per il dato <code>Cicerone</code>.
     * @param cicerone <code>Cicerone</code> a cui il gestore si riferisce.
     */
    private GestoreEsperienze(Cicerone cicerone) {
        this.cicerone = cicerone;
        esperienze = updateEsperienze();
    }

    public static GestoreEsperienze getInstance(Cicerone cicerone) {
        if (instance == null) instance = new GestoreEsperienze(cicerone);
        return instance;
    }

    private Set<Esperienza> updateEsperienze() {
        // TODO: update esperienze with database select request from table "esperienze" where id_cicerone = cicerone.getId()
        //  return DBManager.select(DBTable.ESPERIENZE, cicerone) -> crea e ritorna un HashSet di oggetti Esperienza
        return new HashSet<>();  // temporary
    }

    /**
     * Restituisce l'esperienza cui l'identificativo è quello dato.
     * @param id identificativo dell'esperienza che si cerca.
     * @return l'esperienza con l'id dato oppure null se non trovata.
     */
    public Esperienza getEsperienza(int id){
        return esperienze.stream().filter((e) -> e.hashCode()==id).findFirst().orElse(null);
    }

    /**
     * Restituisce tutte le esperienze create dal <code>Cicerone</code> associato.
     * @return collezione di tutte le esperienze create dal <code>Cicerone</code> associato.
     */
    public Set<Esperienza> getAllEsperienze(Predicate<Esperienza> p) {
        return esperienze.stream().filter(p).collect(Collectors.toSet());
    }

    /**
     * Aggiunge un'<code>Esperienza</code> tra quelle create dal <code>Cicerone</code>.
     * @param nome nome dell'<code>Esperienza</code>.
     * @param dI data d'inizio dell'<code>Esperienza</code>.
     * @param dF data di fine dell'<code>Esperienza</code>.
     * @param minP numero minimo dei partecipanti all'<code>Esperienza</code>.
     * @param maxP numero massimo dei partecipanti all'<code>Esperienza</code>.
     * @param p <code>Percorso</code> dell'<code>Esperienza</code>.
     * @param costoIndividuale costo di un posto dell<code>Esperienza</code>.
     * @param maxRiserva numero massimo dei giorni di riserva di posti dell'<code>Esperienza</code>.
     * @param tags tags associati all'<code>Esperienza</code>.
     */
    public void add(String nome, String descrizione, LocalDateTime dI, LocalDateTime dF, int minP, int maxP, Percorso p, Money costoIndividuale, int maxRiserva, Set<Tag> tags) {
        Esperienza e = new SimpleEsperienza(nome, cicerone, descrizione, dI, dF, minP, maxP, p, costoIndividuale, maxRiserva, tags);
        esperienze.add(e);

        //  TODO: insert e in table "esperienze"
        //   DBManager.insert(DBTable.ESPERIENZE, e) -> inserisce la nuova esperienza e nel database
    }
}
