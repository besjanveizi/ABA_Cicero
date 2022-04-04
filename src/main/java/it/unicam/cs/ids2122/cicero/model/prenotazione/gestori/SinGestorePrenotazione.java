package it.unicam.cs.ids2122.cicero.model.prenotazione.gestori;

import it.unicam.cs.ids2122.cicero.model.esperienza.IEsperienza;
import it.unicam.cs.ids2122.cicero.model.prenotazione.bean.*;

import it.unicam.cs.ids2122.cicero.persistence.DBManager;
import it.unicam.cs.ids2122.cicero.ruoli.IUtente;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Set;

public class SinGestorePrenotazione {


    private static SinGestorePrenotazione sinGestorePrenotazione = null;

    private IUtente utente_corrente;


    /**
     * lista delle prenotazioni effettuale riferite all' utente corrente
     */
    private Set<BeanPrenotazione> prenotazioni;



    private SinGestorePrenotazione(IUtente iUtente) {
        utente_corrente = iUtente;
        carica();
    }

    public static SinGestorePrenotazione getInstance(IUtente iUtente )  {
        if(sinGestorePrenotazione==null){
            sinGestorePrenotazione = new  SinGestorePrenotazione(iUtente);
        }return sinGestorePrenotazione;
    }

    /**
     * chiama il db per recuperare le prenotazioni dell' utente
     *
     * @throws SQLException possibile eccezione dal db o dal resultset
     */
    private void carica()  {
        prenotazioni = ServicePrenotazione.getInstance().select(utente_corrente.getUID());
    }

    /**
     * crea una nuova prenotazione e modifica i posti disponibili
     * @param propEsperienza
     * @param posti_prenotati
     */
    public void crea_prenotazione(IEsperienza propEsperienza, int posti_prenotati){
        BeanPrenotazione beanPrenotazione = new BeanPrenotazione(
                propEsperienza.info().getDataInizio(),
                propEsperienza.info().getMaxGiorniRiserva(), posti_prenotati,
                propEsperienza.info().getCostoIndividuale().getValore(),
                propEsperienza.info().getCostoIndividuale().getValuta().toString());
        beanPrenotazione.setID_turista(utente_corrente.getUID());
        beanPrenotazione.setStatoPrenotazione(StatoPrenotazione.RISERVATA);
        ServicePrenotazione.getInstance().insert(beanPrenotazione);
        prenotazioni.add(beanPrenotazione);
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
        beanPrenotazione.setData_prenotazione(LocalDateTime.now());
        beanPrenotazione.setPrezzo_totale(invito_ricevuto.getImporto());
        beanPrenotazione.setValuta(invito_ricevuto.getValuta());
        beanPrenotazione.setID_esperienza(invito_ricevuto.getId_esperienza());
        ServicePrenotazione.getInstance().insert(beanPrenotazione);
        prenotazioni.add(beanPrenotazione);
    }


     /**
     * Modifica stato di una prenotazione
     * @param beanPrenotazione
     * @param nuovo_stato
     */
    public void modifica_stato(BeanPrenotazione beanPrenotazione, StatoPrenotazione nuovo_stato){
        ServicePrenotazione.getInstance().update(beanPrenotazione.getID_prenotazione(), nuovo_stato);
    }


    public Set<BeanPrenotazione> getPrenotazioni() {
        return prenotazioni;
    }
}
