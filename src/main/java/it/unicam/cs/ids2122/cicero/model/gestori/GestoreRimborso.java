package it.unicam.cs.ids2122.cicero.model.gestori;

import it.unicam.cs.ids2122.cicero.model.entities.rimborso.RichiestaRimborso;
import it.unicam.cs.ids2122.cicero.model.entities.rimborso.RimborsoStatus;
import it.unicam.cs.ids2122.cicero.model.services.ServiceRimborso;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Classe che gestisce la collezione di richieste di rimborso presenti nella piattaforma cicero
 */
public class GestoreRimborso {
    private static GestoreRimborso instance=null;
    private Set<RichiestaRimborso> allRichiesteRimborso;

    private GestoreRimborso(){ updateRichiesteRimborso();}

    /**
     * @return l'istanza aggiornata del gestore delle richieste di rimborso.
     */
    public static GestoreRimborso getInstance(){
        if(instance==null) instance=new GestoreRimborso();
        return instance;
    }

    private void updateRichiesteRimborso(){
        ServiceRimborso serviceRimborso = ServiceRimborso.getInstance();
        allRichiesteRimborso=serviceRimborso.getRichiesteRimborso();
    }

    /**
     * Ritorna tutte le richieste di rimborso che rispettano il predicato dato.
     * @param p predicato sulle richieste di rimborso.
     * @return insieme delle richieste di rimborso che rispettano il predicato.
     */
    public Set<RichiestaRimborso> getRichiesteRimborso(Predicate<RichiestaRimborso> p){
        return allRichiesteRimborso.stream().filter(p).collect(Collectors.toSet());
    }

    /**
     * Registra una nuova richiesta di rimborso.
     * @param id_fattura identificativo della fattura da rimborsare.
     * @param motivoRichiesta motivo della richiesta di rimborso.
     */
    public void add(int id_fattura, String motivoRichiesta){
        ServiceRimborso serviceRimborso=ServiceRimborso.getInstance();
        serviceRimborso.insertRichiestaRimborso(id_fattura,motivoRichiesta,RimborsoStatus.PENDING);
    }

    /**
     * Accetta la richiesta di rimborso specificata.
     * @param richiesta richiesta di rimborso da accettare.
     */
    public void accettaRichiestaRimborso(RichiestaRimborso richiesta){
        changeStatus(richiesta,RimborsoStatus.ACCETTATA);
        rimborsaPrenotazione(richiesta);
    }

    /**
     * Rifiuta la richiesta di rimborso specificata.
     * @param richiesta richiesta di rimborso da rifiutare.
     */
    public void rifiutaRichiestaRimborso(RichiestaRimborso richiesta){
        changeStatus(richiesta,RimborsoStatus.RIFIUTATA);
    }

    private void changeStatus(RichiestaRimborso richiesta, RimborsoStatus stato){
        ServiceRimborso serviceRimborso=ServiceRimborso.getInstance();
        serviceRimborso.updateRichiestaRimborsoStatus(richiesta,stato);
        updateRichiesteRimborso();
    }

    private void rimborsaPrenotazione(RichiestaRimborso richiesta){
        //TODO:
        //  effettua la procedura di rimborso
    }
}


