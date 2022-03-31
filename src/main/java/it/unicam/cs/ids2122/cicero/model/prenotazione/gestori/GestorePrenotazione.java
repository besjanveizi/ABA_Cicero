package it.unicam.cs.ids2122.cicero.model.prenotazione.gestori;



import it.unicam.cs.ids2122.cicero.model.esperienza.Esperienza;
import it.unicam.cs.ids2122.cicero.model.prenotazione.*;
import it.unicam.cs.ids2122.cicero.model.prenotazione.invito.Invito;
import it.unicam.cs.ids2122.cicero.model.prenotazione.invito.PropInvito;
import it.unicam.cs.ids2122.cicero.model.prenotazione.persistenza.DBEsperienza;
import it.unicam.cs.ids2122.cicero.model.prenotazione.persistenza.DBManager;
import it.unicam.cs.ids2122.cicero.model.prenotazione.persistenza.DBPrenotazione;
import it.unicam.cs.ids2122.cicero.model.prenotazione.utenti.Utente;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * classe intermediaria per l' utilizzo di un <code>{@link DBManager}</code>
 * e il servizio/controller che la utilizzerà.
 */
public class GestorePrenotazione extends AbstractGestore{

    /**
     * lista delle prenotazioni effettuale riferite all' utente corrente
     */
    private List<Prenotazione> prenotazioni;


    /**
     * sostituzione della stringa <code>{@link java.sql.PreparedStatement}</code>
     */
    private String sql_insert = "INSERT INTO public.prenotazioni (id_esperienza, uid_turista, " +
            "nome_esperienza, posti_prenotati, , data_scadenza_riserva, costo_totale, valuta " +
            "stato_prenotazione) VALUES ( {0} , {1} , {2} , {3}, {4} , {5} , {6} , {7} );";

    /**
     * recupera le esperienze dell' utente corrente, tramite query
     */
    private String sql_select = "SELECT * FROM public.prenotazioni where uid_utente=";

    /**
     * query per la modifica dello stato di una prenotazione
     */
    private String sql_update = "UPDATE public.prenotazioni SET stato_prenotazione= {0} WHERE uid_turista= {1} and id_prenotazione= {2} ;";

    /**
     * costruttore del gestore
     * @param utente utente corrente
     * @param dbManager funzioni disponibili
     */
    public GestorePrenotazione(Utente utente , DBManager dbManager) throws SQLException {
        super(utente,dbManager);
        prenotazioni = new ArrayList<>();
        carica();
    }

    /**
     * chiama il db per recuperare le prenotazioni dell' utente
     *
     * @throws SQLException possibile eccezione dal db o dal resultset
     */
    public void carica() throws SQLException {
        ResultSet resultSet = dbManager.select_query(sql_select);
        new DBPrenotazione().genera(resultSet, prenotazioni);
    }

    /**
     * crea una <code>{@link SimplePrenotazione}</code>
     * NON VIENE AGGIORNATA LA DISPONIBILITA DELLA RELATIVA ESPERIENZA
     * @param propEsperienza da prenotare
     * @param posti_prenotati i posti da riservare
     */
    public void crea_prenotazione(Esperienza propEsperienza, int posti_prenotati){
        Prenotazione prenotazione = new SimplePrenotazione(utente_corrente.getID(),
                propEsperienza.getId(),
                propEsperienza.getName(),
                posti_prenotati, propEsperienza.getDataInizio(),
                propEsperienza.getMaxRiserva(),
                propEsperienza.getCostoIndividuale().getValore(), propEsperienza.getCostoIndividuale().getValuta().getCurrencyCode());
        String sql_format = MessageFormat.format(sql_insert, getToken((PropPrenotazione) prenotazione));
        if (dbManager.insert_update_delete_query(sql_format)!= -1){
            prenotazioni.add(prenotazione);
        }
    }

    /**
     * Crea una prenotazione dall' invito e dalla sua relativa esperienza,
     * con l' attuale scadenza della prenotazione uguale a quella dell'invito
     * @param invito_ricevuto invito selezionata
     * @throws SQLException dal db o dal resultset
    */
    public void crea_prenotazione(Invito invito_ricevuto) throws SQLException {
        PropInvito invito = (PropInvito) invito_ricevuto;
        ResultSet resultSet =
                dbManager.select_query("select * from public.esperienze where id_esperienza=" +
                        ((PropInvito) invito_ricevuto).getId_esperienza());

        Esperienza esperienza = new DBEsperienza().crea_singola_esperienza(resultSet);
        Prenotazione prenotazione = new SimplePrenotazione(utente_corrente.getID(),
                esperienza.getId(), esperienza.getName(),
                invito.getPosti_riservati(), invito.getData_scadenza_riserva(),
                esperienza.getCostoIndividuale().getValore(),
                esperienza.getCostoIndividuale().getValuta().getCurrencyCode());

        String format = MessageFormat.format(sql_insert, getToken((PropPrenotazione) prenotazione));
        if(dbManager.insert_update_delete_query(format)!= -1){
            prenotazioni.add(prenotazione);
        };

    }

    /**
     * "INSERT INTO public.prenotazioni (id_esperienza, uid_turista, " +
     *             "nome_esperienza, posti_prenotati, data_inizio_esperienza, data_scadenza_riserva, costo_totale, valuta " +
     *             "stato_prenotazione) VALUES ( {0} , {1} , {2} , {3}, {4} , {5} , {6} , {7} , {8});";
     * @param prenotazione proprietà recuperabili della prenotazione
     * @return <code>Object[]</code> un array di oggetti
     */
    private Object[] getToken(PropPrenotazione prenotazione){
       Object[] token = {
            prenotazione.getID_esperienza(),
            prenotazione.getID_turista(),
            prenotazione.getNomeEsperienza(),
            prenotazione.getPosti(),
            "'"+prenotazione.getScadenza().toString().replace('T', ' ')+"'",
            "'"+prenotazione.getPrezzo_totale()+"'",
            "'"+prenotazione.getValuta()+"'",
            "'"+prenotazione.getStatoPrenotazione().getN()+"'"
        };
        return token;
    }

    /**
     * "UPDATE public.prenotazioni SET stato_prenotazione= {0} WHERE uid_turista= {1} and id_prenotazione= {2} ;"
     * Modifica lo stato di una prenotazione.
     * Run time e tramite query modifica lo stato nel database
     * @param propPrenotazione la <code>{@link Prenotazione}</code>
     * @param nuovo_stato il nuo <code>{@link StatoPrenotazione}</code>
     */
    public void modifica_stato(Prenotazione propPrenotazione, StatoPrenotazione nuovo_stato){
        StatoPrenotazione salva = ((PropPrenotazione) propPrenotazione).getStatoPrenotazione();
        Object[] token = {
                nuovo_stato.getN(),
                ((PropPrenotazione)propPrenotazione).getID_turista(),
                ((PropPrenotazione)propPrenotazione).getID_prenotazione()
        };

        ((FunPrenotazione) propPrenotazione).cambiaStatoPrenotazione(nuovo_stato);
        String sql_up = MessageFormat.format(sql_update, token);
        if(dbManager.insert_update_delete_query(sql_up)==-1){
            ((FunPrenotazione) propPrenotazione).cambiaStatoPrenotazione(salva);
        }
    }

    /**
     * restituisce un esperienza ad un determinato indice
     * @param index l' indice per la selezione
     * @return una <code>{@link SimplePrenotazione}</code>
     * @throws IndexOutOfBoundsException
     */
    public Prenotazione select_prenotazione(int index) throws IndexOutOfBoundsException{
        return prenotazioni.get(index);
    }

    /**
     * dimensione della lista
     * @return numero di prenotazioni effettuate, può essere 0
     */
    public int getSize(){
        return prenotazioni.size();
    }

    @Override
    public void show() {
        prenotazioni.forEach(System.out::println);
    }

    /**
     * restituisce una lista filtrata delle prenotazioni dell' utente
     * @param statoPrenotazione filtro ricerca
     * @return lista prenotazioni (puo essere vuota) null altrimenti
     */
    public List<Prenotazione> filtro(StatoPrenotazione statoPrenotazione){
         return prenotazioni
                    .stream()
                    .collect(Collectors
                            .filtering(prenotazione ->
                                    ((PropPrenotazione) prenotazione).getStatoPrenotazione().equals(statoPrenotazione),
                                    Collectors.toList()));

    }


}
