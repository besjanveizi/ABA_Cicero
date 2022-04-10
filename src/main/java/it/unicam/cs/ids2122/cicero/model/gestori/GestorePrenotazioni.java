package it.unicam.cs.ids2122.cicero.model.gestori;

import com.google.common.collect.Sets;
import it.unicam.cs.ids2122.cicero.model.entities.esperienza.IEsperienza;
import it.unicam.cs.ids2122.cicero.model.prenotazione.bean.BeanInvito;
import it.unicam.cs.ids2122.cicero.model.prenotazione.bean.BeanPrenotazione;
import it.unicam.cs.ids2122.cicero.model.prenotazione.bean.StatoPrenotazione;
import it.unicam.cs.ids2122.cicero.model.services.ServiceDisponibilita;
import it.unicam.cs.ids2122.cicero.model.services.ServicePrenotazione;
import it.unicam.cs.ids2122.cicero.ruoli.Turista;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Set;

public final class GestorePrenotazioni {


    private static GestorePrenotazioni gestorePrenotazioni = null;

    private Turista utente_corrente;

    /**
     * lista delle prenotazioni effettuale riferite all' utente corrente
     */
    private Set<BeanPrenotazione> prenotazioni;



    private GestorePrenotazioni(Turista iUtente) {
        utente_corrente = iUtente;
        carica();
    }

    public static GestorePrenotazioni getInstance(Turista iUtente)  {
        if(gestorePrenotazioni ==null){
            gestorePrenotazioni = new GestorePrenotazioni(iUtente);
        }return gestorePrenotazioni;
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
    public BeanPrenotazione crea_prenotazione(IEsperienza propEsperienza, int posti_prenotati){
        BeanPrenotazione beanPrenotazione = new BeanPrenotazione(
                propEsperienza.info().getDataInizio(),
                propEsperienza.info().getMaxGiorniRiserva(), posti_prenotati,
                propEsperienza.info().getCostoIndividuale().getValore(),
                propEsperienza.info().getCostoIndividuale().getValuta().toString());
        beanPrenotazione.setID_turista(utente_corrente.getUID());
        beanPrenotazione.setStatoPrenotazione(StatoPrenotazione.RISERVATA);
        ServicePrenotazione.getInstance().insert(beanPrenotazione);
        prenotazioni.add(beanPrenotazione);
        propEsperienza.info().cambiaPostiDisponibili(-posti_prenotati);
        ServiceDisponibilita.getInstance().update(propEsperienza.info().getPostiDisponibili(), propEsperienza.getId() );
        return beanPrenotazione;
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
        // non c' modifica dell'esperienza
    }


     /**
     * Modifica stato di una prenotazione ed elimina se è CANCELLATA
     * @param beanPrenotazione
     * @param nuovo_stato
     */
    public void modifica_stato(BeanPrenotazione beanPrenotazione, StatoPrenotazione nuovo_stato){
        ServicePrenotazione.getInstance().update(beanPrenotazione.getID_prenotazione(), nuovo_stato);
        if(nuovo_stato.equals(StatoPrenotazione.CANCELLATA)){
            int posti = ServiceDisponibilita.getInstance().select(beanPrenotazione.getID_esperienza());
            posti = posti + beanPrenotazione.getPosti();
            ServiceDisponibilita.getInstance().update(posti, beanPrenotazione.getID_esperienza());
        }
    }


    public Set<BeanPrenotazione> getPrenotazioni() {
        return prenotazioni;
    }

    public Set<BeanPrenotazione> getPrenotazioni(StatoPrenotazione filtro){
        return Sets.filter( prenotazioni,beanPrenotazione -> beanPrenotazione.getStatoPrenotazione().equals(filtro) );
    }


    public BeanPrenotazione getPrenotazione(int id){
        return Sets.filter( prenotazioni,beanPrenotazione -> beanPrenotazione.getID_prenotazione() == id ||
                                                             beanPrenotazione.getID_esperienza() == id ||
                                                             beanPrenotazione.getID_turista() == id).iterator().next();
    }

}
