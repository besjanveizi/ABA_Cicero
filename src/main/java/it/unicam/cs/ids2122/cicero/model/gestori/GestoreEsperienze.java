package it.unicam.cs.ids2122.cicero.model.gestori;

import it.unicam.cs.ids2122.cicero.model.Bacheca;
import it.unicam.cs.ids2122.cicero.model.IBacheca;
import it.unicam.cs.ids2122.cicero.model.entities.bean.BeanPrenotazione;
import it.unicam.cs.ids2122.cicero.model.entities.bean.StatoPrenotazione;
import it.unicam.cs.ids2122.cicero.model.entities.esperienza.EsperienzaStatus;
import it.unicam.cs.ids2122.cicero.model.entities.esperienza.Esperienza;
import it.unicam.cs.ids2122.cicero.model.entities.esperienza.percorso.Percorso;
import it.unicam.cs.ids2122.cicero.model.entities.tag.Tag;
import it.unicam.cs.ids2122.cicero.model.services.ServiceEsperienza;
import it.unicam.cs.ids2122.cicero.model.services.ServicePrenotazione;
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
    private final Cicerone cicerone;
    private static GestoreEsperienze instance = null;
    private static ServiceEsperienza serviceEsperienza;
    private static IBacheca bacheca;

    /**
     * Crea un gestore delle esperienze per il dato <code>Cicerone</code>.
     * @param cicerone <code>Cicerone</code> a cui il gestore si riferisce.
     */
    private GestoreEsperienze(Cicerone cicerone) {
        this.cicerone = cicerone;
        serviceEsperienza = ServiceEsperienza.getInstance();
        bacheca = Bacheca.getInstance();
        updateEsperienze();
    }

    public static GestoreEsperienze getInstance(Cicerone cicerone) {
        if (instance == null) instance = new GestoreEsperienze(cicerone);
        return instance;
    }

    private void updateEsperienze() {
        esperienze = new HashSet<>();
        Set<Esperienza> temp = bacheca.getEsperienze(e -> e.getCiceroneCreatore().equals(cicerone));
        //Set<Esperienza> temp = serviceEsperienza.download(cicerone.getUID());
        esperienze.addAll(temp);
    }

    /**
     * Restituisce tutte le esperienze create dal <code>Cicerone</code> associato.
     * @return collezione di tutte le esperienze create dal <code>Cicerone</code> associato.
     */
    public Set<Esperienza> getAllEsperienze(Predicate<Esperienza> p) {
        return esperienze.stream().filter(p).collect(Collectors.toSet());
    }

    /**
     * Aggiunge un'{@code Esperienza} tra quelle create dal {@code Cicerone}.
     * @param nomeE nome dell'esperienza.
     * @param cicerone
     * @param descrizioneE
     * @param dI
     * @param dF
     * @param minP
     * @param maxP
     * @param percorso
     * @param costoIndividuale
     * @param maxRiserva
     * @param chosenTags
     */
    public void add(String nomeE, Cicerone cicerone, String descrizioneE, LocalDateTime dI, LocalDateTime dF, int minP,
                    int maxP, Percorso percorso, Money costoIndividuale, int maxRiserva, Set<Tag> chosenTags) {
        Esperienza e =
                serviceEsperienza.upload(nomeE, cicerone, descrizioneE, dI, dF, minP, maxP, percorso,
                costoIndividuale, maxRiserva, chosenTags);
        bacheca.add(e);
        updateEsperienze();
    }

    public Set<BeanPrenotazione> getPrenotazioni(Esperienza e) {
        return ServicePrenotazione.getInstance().getPrenotazioniOfEsperienza(e.getId());
    }

    public void cancellaEsperienza(Esperienza e, Set<BeanPrenotazione> prenotazioni) {
        serviceEsperienza.updateStatus(e.getId(), EsperienzaStatus.CANCELLATA);
        for (BeanPrenotazione p : prenotazioni) {
            if (p.getStatoPrenotazione() == StatoPrenotazione.PAGATA)
                // TODO: rimborsa la prenotazione e annulla i biglietti
            ServicePrenotazione.getInstance().update(p.getID_prenotazione(), StatoPrenotazione.CANCELLATA);
        }
    }
}
