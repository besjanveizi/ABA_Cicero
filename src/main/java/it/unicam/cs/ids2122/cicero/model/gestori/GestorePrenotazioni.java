package it.unicam.cs.ids2122.cicero.model.gestori;

import it.unicam.cs.ids2122.cicero.model.Piattaforma;
import it.unicam.cs.ids2122.cicero.model.entities.esperienza.Esperienza;
import it.unicam.cs.ids2122.cicero.model.entities.bean.BeanInvito;
import it.unicam.cs.ids2122.cicero.model.entities.bean.BeanPrenotazione;
import it.unicam.cs.ids2122.cicero.model.entities.bean.StatoPrenotazione;
import it.unicam.cs.ids2122.cicero.model.services.ServiceDisponibilita;
import it.unicam.cs.ids2122.cicero.model.services.ServiceEsperienza;
import it.unicam.cs.ids2122.cicero.model.services.ServiceInvito;
import it.unicam.cs.ids2122.cicero.model.services.ServicePrenotazione;
import it.unicam.cs.ids2122.cicero.ruoli.IUtente;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public final class GestorePrenotazioni {
    Logger logger = Logger.getLogger(Piattaforma.class.getName());
    private ServicePrenotazione servicePrenotazione;

    private final IUtente utente_corrente;

    /**
     * lista delle prenotazioni effettuale riferite all' utente corrente
     */
    private Set<BeanPrenotazione> prenotazioni;

    public GestorePrenotazioni(IUtente iUtente) {
        utente_corrente = iUtente;
        prenotazioni = new HashSet<>();
        servicePrenotazione = ServicePrenotazione.getInstance();
        carica();
    }

    /**
     * chiama il db per recuperare le prenotazioni dell' utente
     *
     * @throws SQLException possibile eccezione dal db o dal resultset
     */
    private void carica()  {
        logger.info("\tcaricamento delle prenotazioni..");
        prenotazioni = servicePrenotazione.sql_select(utente_corrente.getUID());
        logger.info("prenotazioni caricate.\n");
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

        servicePrenotazione.insert(beanPrenotazione);
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
        beanPrenotazione.setData_prenotazione(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        beanPrenotazione.setPrezzo_totale(invito_ricevuto.getImporto());
        beanPrenotazione.setValuta(invito_ricevuto.getValuta());
        beanPrenotazione.setID_esperienza(invito_ricevuto.getId_esperienza());
        beanPrenotazione.setPosti(invito_ricevuto.getPosti_riservati());
        servicePrenotazione.insert(beanPrenotazione);
        prenotazioni.add(beanPrenotazione);
        ServiceInvito.getInstance().delete(invito_ricevuto.getId_invito());
    }


    public Esperienza getEsperienzaAssociata(BeanPrenotazione b) {
        return ServiceEsperienza.getInstance().getEsperienza(b.getID_esperienza());
    }

     /**
     * Modifica stato di una prenotazione ed elimina se ?? CANCELLATA
     * @param beanPrenotazione
     * @param nuovo_stato
     */
    public void modifica_stato(BeanPrenotazione beanPrenotazione, StatoPrenotazione nuovo_stato){
        servicePrenotazione.update(beanPrenotazione.getID_prenotazione(), nuovo_stato);
        beanPrenotazione.setStatoPrenotazione(nuovo_stato);
        if(nuovo_stato.equals(StatoPrenotazione.CANCELLATA)){
            int posti = ServiceDisponibilita.getInstance().select(beanPrenotazione.getID_esperienza());
            posti = posti + beanPrenotazione.getPosti();
            ServiceDisponibilita.getInstance().update(posti, beanPrenotazione.getID_esperienza());
            // TODO : annulla i biglietti
        }
    }


    public Set<BeanPrenotazione> getPrenotazioni(Predicate<BeanPrenotazione> p) {
        return prenotazioni.stream().filter(p).collect(Collectors.toSet());
    }


    /**
     * Filtra le prenotazioni per un predicato qualsiasi.
     * @param predicate filtro
     * @return
     */
    public BeanPrenotazione getPrenotazione(Predicate<BeanPrenotazione> predicate){
        return prenotazioni.stream().filter(predicate).findFirst().orElseThrow();
    }

}
