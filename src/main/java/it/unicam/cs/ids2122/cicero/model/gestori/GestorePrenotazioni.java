package it.unicam.cs.ids2122.cicero.model.gestori;

import it.unicam.cs.ids2122.cicero.model.entities.esperienza.Esperienza;
import it.unicam.cs.ids2122.cicero.model.entities.bean.BeanInvito;
import it.unicam.cs.ids2122.cicero.model.entities.bean.BeanPrenotazione;
import it.unicam.cs.ids2122.cicero.model.entities.bean.StatoPrenotazione;
import it.unicam.cs.ids2122.cicero.model.services.ServiceDisponibilita;
import it.unicam.cs.ids2122.cicero.model.services.ServiceInvito;
import it.unicam.cs.ids2122.cicero.model.services.ServicePrenotazione;
import it.unicam.cs.ids2122.cicero.ruoli.Turista;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class GestorePrenotazioni {


    private static GestorePrenotazioni gestorePrenotazioni = null;

    private Turista utente_corrente;

    /**
     * lista delle prenotazioni effettuale riferite all' utente corrente
     */
    private Set<BeanPrenotazione> prenotazioni;



    private GestorePrenotazioni(Turista iUtente) {
        utente_corrente = iUtente;
        prenotazioni = new HashSet<>();
        carica();
    }

    public static GestorePrenotazioni getInstance(Turista iUtente)  {
        if(gestorePrenotazioni ==null){
            gestorePrenotazioni = new GestorePrenotazioni(iUtente);
        }
        return gestorePrenotazioni;
    }

    /**
     * chiama il db per recuperare le prenotazioni dell' utente
     *
     * @throws SQLException possibile eccezione dal db o dal resultset
     */
    private void carica()  {
        prenotazioni = ServicePrenotazione.getInstance().sql_select(utente_corrente.getUID());
    }

    /**
     * crea una nuova prenotazione e modifica i posti disponibili
     * @param propEsperienza
     * @param posti_prenotati
     */
    public int crea_prenotazione(Esperienza propEsperienza, int posti_prenotati){
        BeanPrenotazione beanPrenotazione = new BeanPrenotazione(
                propEsperienza.getDataInizio(),
                propEsperienza.getMaxRiserva(), posti_prenotati,
                propEsperienza.getCostoIndividuale().getValore(),
                propEsperienza.getCostoIndividuale().getValuta().toString());
        beanPrenotazione.setID_turista(utente_corrente.getUID());
        beanPrenotazione.setStatoPrenotazione(StatoPrenotazione.RISERVATA);
        beanPrenotazione.setID_esperienza(propEsperienza.getId());

        ServicePrenotazione.getInstance().insert(beanPrenotazione);
        prenotazioni.add(beanPrenotazione);

        propEsperienza.cambiaPostiDisponibili('-', posti_prenotati);

        ServiceDisponibilita.getInstance().update(propEsperienza.getPostiDisponibili(), propEsperienza.getId() );
        return beanPrenotazione.getID_prenotazione() ;
    }

    /**
     * Crea una prenotazione dall' invito e dalla sua relativa esperienza,
     * con l' attuale scadenza della prenotazione uguale a quella dell'invito
     * @param invito_ricevuto invito selezionata
     * @throws SQLException dal db o dal resultset
     */
    public void crea_prenotazione(BeanInvito invito_ricevuto)  {
        BeanPrenotazione beanPrenotazione = new BeanPrenotazione();
        beanPrenotazione.setID_turista(utente_corrente.getUID());
        beanPrenotazione.setStatoPrenotazione(StatoPrenotazione.RISERVATA);
        beanPrenotazione.setScadenza(invito_ricevuto.getData_scadenza_riserva());
        beanPrenotazione.setData_prenotazione(LocalDateTime.now().truncatedTo(ChronoUnit.HOURS));
        beanPrenotazione.setPrezzo_totale(invito_ricevuto.getImporto());
        beanPrenotazione.setValuta(invito_ricevuto.getValuta());
        beanPrenotazione.setID_esperienza(invito_ricevuto.getId_esperienza());
        beanPrenotazione.setPosti(invito_ricevuto.getPosti_riservati());
        ServicePrenotazione.getInstance().insert(beanPrenotazione);
        prenotazioni.add(beanPrenotazione);
        ServiceInvito.getInstance().delete(invito_ricevuto.getId_invito());
    }


     /**
     * Modifica stato di una prenotazione ed elimina se è CANCELLATA
     * @param beanPrenotazione
     * @param nuovo_stato
     */
    public void modifica_stato(BeanPrenotazione beanPrenotazione, StatoPrenotazione nuovo_stato){
        ServicePrenotazione.getInstance().update(beanPrenotazione.getID_prenotazione(), nuovo_stato);
        beanPrenotazione.setStatoPrenotazione(nuovo_stato);

        if(nuovo_stato.equals(StatoPrenotazione.CANCELLATA)){
            int posti = ServiceDisponibilita.getInstance().select(beanPrenotazione.getID_esperienza());
            posti = posti + beanPrenotazione.getPosti();
            ServiceDisponibilita.getInstance().update(posti, beanPrenotazione.getID_esperienza());
        }
    }

    public void cancellaPrenotazione(BeanPrenotazione prenotazione) {
        if (prenotazione.getStatoPrenotazione() == StatoPrenotazione.PAGATA) {
            // ottieni la fattura_pagamento di prenotazione
            // effettua pagamento del rimborso usando i dati della fattura_pagamento: ritorna l'oggetto new_pagamento
            // se new_pagamento è stato autorizzato
                // crea fattura_rimborso con i dati di new_pagamento e inseriscila nel database
                // annulla i biglietti della prenotazione
            // notifica amministratore dell'errore sul pagamento del rimborso (return;)
        }
        // modifica lo stato della prenotazione a CANCELLATA
        // modifica la disponibilità dell'esperienza associata alla prenotazione (posti_disponibili += posti_prenotati)
    }

    public Set<BeanPrenotazione> getPrenotazioni() {
        return prenotazioni;
    }

    /**
     * Filtra le prenotazioni run-time.
     * @param filtro lo stato ricercato.
     * @return
     */
    public Set<BeanPrenotazione> getPrenotazioni(StatoPrenotazione filtro){
        return prenotazioni.stream().filter(beanPrenotazione -> beanPrenotazione.getStatoPrenotazione().getN()==filtro.getN()).collect(Collectors.toSet());
    }

    /**
     * Filtra le prenotazioni per un predicato qualsiasi.
     * @param predicate filtro
     * @return
     */
    public BeanPrenotazione getPrenotazione(Predicate<BeanPrenotazione> predicate){
        return prenotazioni.stream().filter(predicate).findFirst().get();
    }




}
