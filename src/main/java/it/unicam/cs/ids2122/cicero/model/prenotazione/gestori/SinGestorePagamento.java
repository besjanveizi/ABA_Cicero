package it.unicam.cs.ids2122.cicero.model.prenotazione.gestori;


import it.unicam.cs.ids2122.cicero.model.prenotazione.bean.BeanFattura;
import it.unicam.cs.ids2122.cicero.model.prenotazione.bean.BeanPrenotazione;
import it.unicam.cs.ids2122.cicero.model.prenotazione.bean.ServiceFattura;
import it.unicam.cs.ids2122.cicero.model.prenotazione.bean.StatoPagamento;
import it.unicam.cs.ids2122.cicero.persistence.SystemConstraints;
import it.unicam.cs.ids2122.cicero.ruoli.IUtente;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public class SinGestorePagamento {

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
     ricevuti=tot
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
    }


    public Set<BeanFattura> getRicevuti() {
        return ricevuti;
    }

    public void setRicevuti(Set<BeanFattura> ricevuti) {
        this.ricevuti = ricevuti;
    }

    public Set<BeanFattura> getEffettuati() {
        return effettuati;
    }

    public void setEffettuati(Set<BeanFattura> effettuati) {
        this.effettuati = effettuati;
    }
}
