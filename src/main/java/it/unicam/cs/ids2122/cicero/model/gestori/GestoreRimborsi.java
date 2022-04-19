package it.unicam.cs.ids2122.cicero.model.gestori;


import it.unicam.cs.ids2122.cicero.model.Piattaforma;
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
import java.util.logging.Logger;
import java.util.stream.Collectors;

public final class GestoreRimborsi {

    Logger logger = Logger.getLogger(Piattaforma.class.getName());

    private final IUtente iUtente;

    private Set<RichiestaRimborso> rimborsi;

    public GestoreRimborsi(IUtente iUtente){
        this.iUtente = iUtente;
        rimborsi = new HashSet<>();
        carica();
    }

    private void carica() {
        if(iUtente.getType().equals(UtenteType.ADMIN)){
            logger.info("\tcaricamento delle richieste di rimborso..");
            rimborsi = ServiceRimborso.getInstance().getRichiesteRimborso();
            logger.info("richieste caricate.\n");
        }
    }

    /**
     * Verifica se il rimborso può avvenire automaticamente.
     * @param beanPrenotazione
     * @return
     */
    public boolean isAutoRefundable(BeanPrenotazione beanPrenotazione){
        Esperienza esperienza = ServiceEsperienza.getInstance().getEsperienza(beanPrenotazione.getID_esperienza());
        EsperienzaStatus es = esperienza.getStatus();
        return es.equals(EsperienzaStatus.IDLE) || es.equals(EsperienzaStatus.VALIDA);
    }

    /**
     * Verifica se il rimborso può avvenire tramite richiesta
     * @param beanPrenotazione
     * @return
     */
    public boolean isRequestRefundable(BeanPrenotazione beanPrenotazione){
        Esperienza esperienza = ServiceEsperienza.getInstance().getEsperienza(beanPrenotazione.getID_esperienza());
        return esperienza.getStatus().equals(EsperienzaStatus.CONCLUSA);
    }

    /**
     * Crea un rimborso nel DB
     * @param beanFattura
     * @param motivo
     */
    public void crea_rimborso( BeanFattura beanFattura, String motivo) {
        RichiestaRimborso r = ServiceRimborso.getInstance().insertRichiestaRimborso(beanFattura.getId_fattura(), motivo, RimborsoStatus.PENDING);
        rimborsi.add(r);
    }


    /**
     * Accetta la richiesta di rimborso specificata.
     * @param richiesta richiesta di rimborso da accettare.
     */
    public BeanFattura accettaRichiestaRimborso(RichiestaRimborso richiesta, String motivo){
       changeStatus(richiesta,RimborsoStatus.ACCETTATA, motivo);
       return ServiceFattura.getInstance().sql_select(richiesta.getIdFattura()).iterator().next();
    }

    /**
     * Rifiuta la richiesta di rimborso specificata.
     * @param richiesta richiesta di rimborso da rifiutare.
     */
    public void rifiutaRichiestaRimborso(RichiestaRimborso richiesta, String motivo){
        changeStatus(richiesta,RimborsoStatus.RIFIUTATA, motivo);
    }

    /**
     * Modifica lo stato di un rimborso.
     * @param richiesta
     * @param stato
     */
    private void changeStatus(RichiestaRimborso richiesta, RimborsoStatus stato, String motivo){
        ServiceRimborso serviceRimborso=ServiceRimborso.getInstance();
        serviceRimborso.updateRichiestaRimborsoStatus(richiesta, stato, motivo);
        carica();
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
