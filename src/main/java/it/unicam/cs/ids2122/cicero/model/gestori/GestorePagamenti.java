package it.unicam.cs.ids2122.cicero.model.gestori;


import it.unicam.cs.ids2122.cicero.model.entities.bean.BeanFattura;
import it.unicam.cs.ids2122.cicero.model.entities.bean.BeanPrenotazione;
import it.unicam.cs.ids2122.cicero.model.entities.bean.StatoPrenotazione;
import it.unicam.cs.ids2122.cicero.model.entities.bean.TipoFattura;
import it.unicam.cs.ids2122.cicero.model.services.ServiceFattura;
import it.unicam.cs.ids2122.cicero.model.services.ServicePrenotazione;
import it.unicam.cs.ids2122.cicero.persistence.SystemConstraints;
import it.unicam.cs.ids2122.cicero.ruoli.IUtente;
import it.unicam.cs.ids2122.cicero.ruoli.Turista;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.stream.Collectors;

public final class GestorePagamenti {

    private static GestorePagamenti gestorePagamenti = null;

    private Turista utente_corrente;

    private Set<BeanFattura> ricevuti;
    private Set<BeanFattura> effettuati;

    private GestorePagamenti(Turista iUtente) {
        this.utente_corrente = iUtente;
        if(utente_corrente!=null) {
            carica();
        }
    }

    private void carica() {

    Set<BeanFattura> tot = ServiceFattura.getInstance().sql_select(utente_corrente.getID_Client());
     ricevuti = tot
             .stream()
             .filter(beanFattura -> beanFattura.getId_client_destinatario().equals(utente_corrente.getID_Client()))
             .collect(Collectors.toSet());

     effettuati = tot
             .stream()
             .filter(beanFattura -> beanFattura.getId_client_origine().equals(utente_corrente.getID_Client()))
             .collect(Collectors.toSet());
    }

    public static GestorePagamenti getInstance(Turista iUtente){
        if(gestorePagamenti == null) {
            gestorePagamenti = new GestorePagamenti(iUtente);
        }return gestorePagamenti;
    }

    /**
     * Crea una semplice fattura per il pagamento vero si conto dell' Amministratore.
     * Trasforma la prenotazione in PAGATA
     * @param beanPrenotazione la prenotazione
     */
    public void crea_fattura(BeanPrenotazione beanPrenotazione) {
        BeanFattura beanFattura = new BeanFattura();
        beanFattura.setData_pagamento(LocalDateTime.now().truncatedTo(ChronoUnit.HOURS));
        beanFattura.setImporto(beanPrenotazione.getPrezzo_totale());
        beanFattura.setValuta(beanPrenotazione.getValuta());
        beanFattura.setStatoPagamento(TipoFattura.PAGAMENTO);
        beanFattura.setId_prenotazione(beanPrenotazione.getID_prenotazione());
        beanFattura.setId_client_origine(utente_corrente.getID_Client());
        beanFattura.setId_client_destinatario(SystemConstraints.ID_SYSTEM);
        ServiceFattura.getInstance().insert(beanFattura);
        effettuati.add(beanFattura);
        ServicePrenotazione.getInstance().update(beanPrenotazione.getID_prenotazione(),StatoPrenotazione.PAGATA);
    }

    /**
     * CREA una fattura di tipo rimborso
     * @param beanFattura
     */
    public void crea_fattura(BeanFattura beanFattura){
         BeanFattura newBeanFattura = new BeanFattura();
         newBeanFattura.setData_pagamento(LocalDateTime.now());
         newBeanFattura.setStatoPagamento(TipoFattura.RIMBORSO);
         newBeanFattura.setId_client_destinatario(beanFattura.getId_client_origine());
         newBeanFattura.setId_client_origine(beanFattura.getId_client_destinatario());
         newBeanFattura.setImporto(beanFattura.getImporto());
         newBeanFattura.setValuta(newBeanFattura.getValuta());
         newBeanFattura.setId_prenotazione(beanFattura.getId_prenotazione());
         ServiceFattura.getInstance().insert(newBeanFattura);
    }

    /**
     * CREA una fattura di tipo LIQUIDAZIONE
     * @param beanFattura
     * @param destinatario
     */
    public void crea_fattura(BeanFattura beanFattura, IUtente destinatario){

    }

    public Set<BeanFattura> getRicevuti() {
        return ricevuti;
    }


    public Set<BeanFattura> getEffettuati() {
        return effettuati;
    }


}
