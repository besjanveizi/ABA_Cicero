package it.unicam.cs.ids2122.cicero.model.gestori;

import it.unicam.cs.ids2122.cicero.model.Bacheca;
import it.unicam.cs.ids2122.cicero.model.IBacheca;
import it.unicam.cs.ids2122.cicero.model.entities.bean.BeanFattura;
import it.unicam.cs.ids2122.cicero.model.entities.bean.BeanInvito;
import it.unicam.cs.ids2122.cicero.model.entities.bean.BeanPrenotazione;
import it.unicam.cs.ids2122.cicero.model.entities.bean.StatoPrenotazione;
import it.unicam.cs.ids2122.cicero.model.entities.esperienza.EsperienzaStatus;
import it.unicam.cs.ids2122.cicero.model.entities.esperienza.Esperienza;
import it.unicam.cs.ids2122.cicero.model.entities.esperienza.percorso.Percorso;
import it.unicam.cs.ids2122.cicero.model.entities.tag.Tag;
import it.unicam.cs.ids2122.cicero.model.services.*;
import it.unicam.cs.ids2122.cicero.ruoli.Cicerone;
import it.unicam.cs.ids2122.cicero.ruoli.IUtente;
import it.unicam.cs.ids2122.cicero.ruoli.Turista;
import it.unicam.cs.ids2122.cicero.ruoli.UtenteType;
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
    private final IUtente utente;
    private static ServiceEsperienza serviceEsperienza;
    private static IBacheca bacheca;

    /**
     * Crea un gestore delle esperienze per il dato <code>Cicerone</code>.
     * @param utente <code>Cicerone</code> a cui il gestore si riferisce.
     */
    public GestoreEsperienze(IUtente utente) {
        this.utente = utente;
        serviceEsperienza = ServiceEsperienza.getInstance();
        bacheca = Bacheca.getInstance();
        updateEsperienze();
    }

    private void updateEsperienze() {
        esperienze = new HashSet<>();
        if (utente.getType() == UtenteType.CICERONE){
            esperienze.addAll(bacheca.getEsperienze(e -> e.getCiceroneCreatore().equals(utente)));
        }
        else if (utente.getType() == UtenteType.ADMIN) esperienze.addAll(bacheca.getEsperienze(e -> true));
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
     * @param utente
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
    public void add(String nomeE, Cicerone utente, String descrizioneE, LocalDateTime dI, LocalDateTime dF, int minP,
                    int maxP, Percorso percorso, Money costoIndividuale, int maxRiserva, Set<Tag> chosenTags) {
        Esperienza e =
                serviceEsperienza.upload(nomeE, utente, descrizioneE, dI, dF, minP, maxP, percorso,
                costoIndividuale, maxRiserva, chosenTags);
        bacheca.add(e);
        updateEsperienze();
    }

    public Set<BeanPrenotazione> getPrenotazioni(Esperienza e, Predicate<BeanPrenotazione> f) {
        return ServicePrenotazione.getInstance().getPrenotazioniOfEsperienza(e.getId()).stream().filter(f).collect(Collectors.toSet());
    }

    public void cancellaEsperienza(Esperienza e, Set<BeanPrenotazione> prenotazioni, Set<BeanInvito> inviti) {
        serviceEsperienza.updateStatus(e.getId(), EsperienzaStatus.CANCELLATA);
        for (BeanPrenotazione p : prenotazioni) {
            if (p.getStatoPrenotazione() == StatoPrenotazione.CANCELLATA) continue;
            IUtente turista = ServiceUtente.getInstance().getUser(p.getID_turista()).get();
            if (p.getStatoPrenotazione() == StatoPrenotazione.PAGATA) {
                // TODO: rimborsa il pagamento della prenotazione al turista
                BeanFattura fattura =
                        ServiceFattura.getInstance().sql_select(turista.getID_Client())
                                .stream()
                                .filter(f -> f.getId_client_origine().equals(turista.getID_Client()) &&
                                        f.getId_prenotazione() == p.getID_prenotazione())
                                .findFirst().orElseThrow();
                new GestoreFatture(turista).crea_fattura(fattura);
            }
            new GestorePrenotazioni(turista).modifica_stato(p, StatoPrenotazione.CANCELLATA);
        }
        for (BeanInvito i : inviti) {
            ServiceInvito.getInstance().delete(i.getId_invito());
        }
    }

    public Set<BeanInvito> getInviti(Esperienza e) {
        return ServiceInvito.getInstance().download(e);
    }
}
