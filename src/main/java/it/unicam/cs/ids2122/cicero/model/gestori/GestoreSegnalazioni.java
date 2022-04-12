package it.unicam.cs.ids2122.cicero.model.gestori;

import it.unicam.cs.ids2122.cicero.model.entities.segnalazione.Segnalazione;
import it.unicam.cs.ids2122.cicero.model.entities.segnalazione.SegnalazioneStatus;
import it.unicam.cs.ids2122.cicero.model.services.ServiceSegnalazione;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Classe che gestisce la collezione di <code>Segnalazioni</code> delle esperienze presenti nella piattaforma cicero
 */
public class GestoreSegnalazioni {
    private static GestoreSegnalazioni instance = null;
    private Set<Segnalazione> allSegnalazioni;

    private GestoreSegnalazioni() {
        updateSegnalazioni();
    }

    /**
     * @return l'istanza aggiornata del gestore delle <code>Segnalazioni</code>.
     */
    public static GestoreSegnalazioni getInstance() {
        if (instance == null) instance = new GestoreSegnalazioni();
        return instance;
    }

    private void updateSegnalazioni() {
        ServiceSegnalazione service = ServiceSegnalazione.getInstance();
        allSegnalazioni = service.getSegnalazioni();
    }

    /**
     * ritorna tutte le segnalazioni che rispettano il predicato dato.
     * @param p predicato sulle segnalazioni.
     * @return insieme delle segnalazioni che rispettano il predicato.
     */
    public Set<Segnalazione> getSegnalazioni(Predicate<Segnalazione> p) {
        return allSegnalazioni.stream().filter(p).collect(Collectors.toSet());
    }

    /**
     * Registra una nuova segnalazione.
     * @param id_esperienza identificativo dell'esperienza da segnalare.
     * @param uid identificativo dell'utente che effettua la segnalazione.
     * @param descrizione descrizione della segnalazione.
     */
    public void add(int id_esperienza, int uid, String descrizione){
        ServiceSegnalazione service = ServiceSegnalazione.getInstance();
        allSegnalazioni.add(service.insertSegnalazione(id_esperienza, uid, descrizione, SegnalazioneStatus.PENDING));
    }

    /**
     * Accetta la segnalazione specificata.
     * @param segnalazione segnalazione da accettare.
     */
    public void accettaSegnalazione(Segnalazione segnalazione){
        changeStatus(segnalazione,SegnalazioneStatus.ACCETTATA);
    }

    /**
     * Rifiuta la segnalazione specificata.
     * @param segnalazione segnalazione da rifiutare.
     */
    public void rifiutaSegnalazione(Segnalazione segnalazione){
        changeStatus(segnalazione,SegnalazioneStatus.RIFIUTATA);
    }

    private void changeStatus(Segnalazione segnalazione, SegnalazioneStatus stato){
        ServiceSegnalazione service = ServiceSegnalazione.getInstance();
        service.updateSegnalazioneStatus(segnalazione,stato);
        updateSegnalazioni();
    }
}


