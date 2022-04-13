package it.unicam.cs.ids2122.cicero.model.gestori;


import it.unicam.cs.ids2122.cicero.model.entities.bean.BeanFattura;
import it.unicam.cs.ids2122.cicero.model.entities.bean.BeanPrenotazione;
import it.unicam.cs.ids2122.cicero.model.entities.esperienza.Esperienza;
import it.unicam.cs.ids2122.cicero.model.entities.esperienza.EsperienzaStatus;
import it.unicam.cs.ids2122.cicero.model.entities.rimborso.RichiestaRimborso;
import it.unicam.cs.ids2122.cicero.model.entities.rimborso.RimborsoStatus;

import it.unicam.cs.ids2122.cicero.model.services.ServiceEsperienza;
import it.unicam.cs.ids2122.cicero.model.services.ServiceFattura;
import it.unicam.cs.ids2122.cicero.model.services.ServiceRimborso;
import it.unicam.cs.ids2122.cicero.ruoli.IUtente;

import it.unicam.cs.ids2122.cicero.ruoli.UtenteType;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class GestoreRimborsi {

    private static GestoreRimborsi gestoreRimborsi = null;

    private IUtente iUtente;

    private Set<RichiestaRimborso> rimborsi;

    private GestoreRimborsi(IUtente iUtente){
        this.iUtente = iUtente;
        rimborsi = new HashSet<>();
        carica();
    }

    private void carica() {
        if(iUtente.getType().equals(UtenteType.ADMIN)){
            rimborsi = ServiceRimborso.getInstance().getRichiesteRimborso();
        }
        else {
            rimborsi = ServiceRimborso.getInstance().getRichiesteRimborsoUtente(iUtente.getUID());
        }
    }


    public static GestoreRimborsi getInstance(IUtente turista){
        if(gestoreRimborsi ==null){
            gestoreRimborsi = new GestoreRimborsi(turista);
        }return gestoreRimborsi;
    }


    /**
     * Verifica se il rimborso può avvenire automaticamente.
     * @param beanPrenotazione
     * @return
     */
    public boolean rimborsa(BeanPrenotazione beanPrenotazione){
        Esperienza esperienza = ServiceEsperienza.getInstance().getEsperienza(beanPrenotazione.getID_esperienza());
        if(esperienza.getStatus().equals(EsperienzaStatus.IDLE) || esperienza.getStatus().equals(EsperienzaStatus.VALIDA) ){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Crea un rimborso nel DB
     * @param beanFattura
     * @param motivo
     */
    public void crea_rimborso( BeanFattura beanFattura, String motivo) {
        ServiceRimborso.getInstance().insertRichiestaRimborso(beanFattura.getId_fattura(), motivo, RimborsoStatus.PENDING);
    }


    /**
     * Accetta la richiesta di rimborso specificata.
     * @param richiesta richiesta di rimborso da accettare.
     */
    public BeanFattura accettaRichiestaRimborso(RichiestaRimborso richiesta){
       changeStatus(richiesta,RimborsoStatus.ACCETTATA);
       return ServiceFattura.getInstance().sql_select(richiesta.getId()).iterator().next();
    }

    /**
     * Rifiuta la richiesta di rimborso specificata.
     * @param richiesta richiesta di rimborso da rifiutare.
     */
    public void rifiutaRichiestaRimborso(RichiestaRimborso richiesta){
        changeStatus(richiesta,RimborsoStatus.RIFIUTATA);
    }

    /**
     * Modifica lo stato di un rimborso.
     * @param richiesta
     * @param stato
     */
    private void changeStatus(RichiestaRimborso richiesta, RimborsoStatus stato){
        ServiceRimborso serviceRimborso=ServiceRimborso.getInstance();
        serviceRimborso.updateRichiestaRimborsoStatus(richiesta,stato);
    }

    /**
     * Recupera un specifico insieme, filtrato.
     * @param p
     * @return
     */
    public Set<RichiestaRimborso> getRimborsi(Predicate<RichiestaRimborso> p){
        return  rimborsi.stream().filter(p).collect(Collectors.toSet());
    }

}
