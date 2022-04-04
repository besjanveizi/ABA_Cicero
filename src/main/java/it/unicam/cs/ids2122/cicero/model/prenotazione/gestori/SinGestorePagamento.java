package it.unicam.cs.ids2122.cicero.model.prenotazione.gestori;


import it.unicam.cs.ids2122.cicero.model.prenotazione.bean.BeanFattura;
import it.unicam.cs.ids2122.cicero.model.prenotazione.bean.BeanPrenotazione;
import it.unicam.cs.ids2122.cicero.model.prenotazione.bean.StatoPrenotazione;
import it.unicam.cs.ids2122.cicero.persistence.services.ServiceFattura;
import it.unicam.cs.ids2122.cicero.model.prenotazione.bean.StatoPagamento;
import it.unicam.cs.ids2122.cicero.persistence.SystemConstraints;
import it.unicam.cs.ids2122.cicero.persistence.services.ServicePrenotazione;
import it.unicam.cs.ids2122.cicero.ruoli.IUtente;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public final class SinGestorePagamento {

    private static SinGestorePagamento sinGestorePagamento= null;

    private IUtente utente_corrente;

    private Set<BeanFattura> ricevuti;
    private Set<BeanFattura> effettuati;

    private SinGestorePagamento(IUtente iUtente) {
        this.utente_corrente = iUtente;
        if(utente_corrente!=null) {
            carica();
        }
    }

    private void carica() {
    Set<BeanFattura> tot = ServiceFattura.getInstance().select(utente_corrente.getID_Client());
     ricevuti = tot
             .stream()
             .filter(beanFattura -> beanFattura.getId_client_destinatario().equals(utente_corrente.getID_Client()))
             .collect(Collectors.toSet());

     effettuati = tot
             .stream()
             .filter(beanFattura -> beanFattura.getId_client_origine().equals(utente_corrente.getID_Client()))
             .collect(Collectors.toSet());
    }

    public static SinGestorePagamento getInstance(IUtente iUtente){
        if(sinGestorePagamento == null) {
            sinGestorePagamento = new  SinGestorePagamento(iUtente);
        }return sinGestorePagamento;
    }

    /**
     * Crea una semplice fattura per il pagamento vero si conto dell' Amministratore.
     * Trasforma la prenotazione in PAGATA
     * @param beanPrenotazione la prenotazione
     */
    public void crea_fattura(BeanPrenotazione beanPrenotazione) {
        BeanFattura beanFattura = new BeanFattura();
        beanFattura.setData_pagamento(LocalDateTime.now());
        beanFattura.setPosti_pagati(beanPrenotazione.getPosti());
        beanFattura.setImporto(beanFattura.getImporto());
        beanFattura.setValuta(beanPrenotazione.getValuta());
        beanFattura.setStatoPagamento(StatoPagamento.ADMIN_ADMIN);
        beanFattura.setId_prenotazione(beanPrenotazione.getID_prenotazione());
        beanFattura.setId_client_origine(utente_corrente.getID_Client());
        beanFattura.setId_client_destinatario(SystemConstraints.ID_SYSTEM);
        ServiceFattura.getInstance().insert(beanFattura);
        effettuati.add(beanFattura);
        ServicePrenotazione.getInstance().update(beanPrenotazione.getID_prenotazione(),StatoPrenotazione.PAGATA);
    }

    /**
     * Rigenera una fattura scambiando il mittente con il destinatario,
     * utile per creare un rimborso automatico.
     * @param beanFattura
     */
    public void crea_fattura(BeanFattura beanFattura){
         BeanFattura newBeanFattura = new BeanFattura();
         newBeanFattura.setData_pagamento(LocalDateTime.now());
         newBeanFattura.setStatoPagamento(StatoPagamento.ADMIN_TURISTA);
         newBeanFattura.setPosti_pagati(beanFattura.getPosti_pagati());
         newBeanFattura.setId_client_destinatario(beanFattura.getId_client_origine());
         newBeanFattura.setId_client_origine(beanFattura.getId_client_destinatario());
         newBeanFattura.setImporto(beanFattura.getImporto());
         newBeanFattura.setValuta(newBeanFattura.getValuta());
         newBeanFattura.setId_prenotazione(beanFattura.getId_prenotazione());
         ServiceFattura.getInstance().insert(newBeanFattura);
         ServicePrenotazione.getInstance().update(beanFattura.getId_prenotazione(), StatoPrenotazione.CANCELLATA);
    }

    public void crea_fattura(BeanFattura beanFattura, IUtente destinatario){

    }

    public Set<BeanFattura> getRicevuti() {
        return ricevuti;
    }


    public Set<BeanFattura> getEffettuati() {
        return effettuati;
    }


}
