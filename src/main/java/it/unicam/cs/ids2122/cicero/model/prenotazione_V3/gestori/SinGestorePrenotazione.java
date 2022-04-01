package it.unicam.cs.ids2122.cicero.model.prenotazione_V3.gestori;

import it.unicam.cs.ids2122.cicero.model.esperienza.Esperienza;
import it.unicam.cs.ids2122.cicero.model.prenotazione_V3.Prenotazione;
import it.unicam.cs.ids2122.cicero.model.prenotazione_V3.SimplePrenotazione;
import it.unicam.cs.ids2122.cicero.model.prenotazione_V3.StatoPrenotazione;
import it.unicam.cs.ids2122.cicero.model.prenotazione_V3.invito.Invito;
import it.unicam.cs.ids2122.cicero.model.prenotazione_V3.persistenza.DBEsperienza;
import it.unicam.cs.ids2122.cicero.model.prenotazione_V3.persistenza.DBPrenotazione;
import it.unicam.cs.ids2122.cicero.persistence.DBManager;
import it.unicam.cs.ids2122.cicero.ruoli.IUtente;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SinGestorePrenotazione {



    private static SinGestorePrenotazione sinGestorePrenotazione = null;

    private IUtente utente_corrente;

    private DBManager dbManager = DBManager.getInstance();

    /**
     * lista delle prenotazioni effettuale riferite all' utente corrente
     */
    private List<Prenotazione> prenotazioni;

    /**
     * sostituzione della stringa <code>{@link java.sql.PreparedStatement}</code>  /**
     *      *  private String sql_insert = "INSERT INTO public.prenotazioni (id_esperienza, uid_turista, " +
     *      *             "nome_esperienza, posti_prenotati, , data_scadenza_riserva, costo_totale, valuta " +
     *      *             "stato_prenotazione) VALUES ( {0} , {1} , {2} , {3}, {4} , {5} , {6} , {7} );";
     *      */
    private String sql_insert = "INSERT INTO public.prenotazioni (id_esperienza, uid_turista, stato_prenotazione, " +
            " posti_prenotati, data_prenotazione , data_scadenza_riserva, costo_totale, valuta " +
            ") VALUES ( {0} , {1} , {2} , {3}, {4} , {5} , {6} , {7} );";

    /**
     * recupera le esperienze dell' utente corrente, tramite query
     */
    private String sql_select = "SELECT * FROM public.prenotazioni WHERE uid_turista=";

    /**
     * query per la modifica dello stato di una prenotazione
     */
    private String sql_update = "UPDATE public.prenotazioni SET stato_prenotazione= {0} WHERE uid_turista= {1} and id_prenotazione= {2} ;";



    private SinGestorePrenotazione(IUtente iUtente) throws SQLException {
        this.utente_corrente = iUtente;
        prenotazioni = new ArrayList<>();
        carica();
    }

    public static SinGestorePrenotazione getInstance(IUtente iUtente ) throws SQLException {
        if(sinGestorePrenotazione==null){
            return new SinGestorePrenotazione(iUtente);
        }
        else
            return sinGestorePrenotazione;
    }

    /**
     * chiama il db per recuperare le prenotazioni dell' utente
     *
     * @throws SQLException possibile eccezione dal db o dal resultset
     */
    public void carica() throws SQLException {
        ResultSet resultSet = dbManager.select_query(sql_select+utente_corrente.getID()+";");
        if(resultSet!=null) {
            if (!prenotazioni.isEmpty()) {
                prenotazioni = new ArrayList<>();
            }
            new DBPrenotazione().genera(resultSet, prenotazioni);
            resultSet.close();
        }
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
                posti_prenotati, propEsperienza.getDataInizio(),
                propEsperienza.getMaxRiserva(),
                propEsperienza.getCostoIndividuale().getValore(), propEsperienza.getCostoIndividuale().getValuta().getCurrencyCode());
        String sql_format = MessageFormat.format(sql_insert, getToken(prenotazione));
        int id_generato = dbManager.insert_update_delete_query(sql_format);
        if (id_generato != -1){
            prenotazione.assegna_id(id_generato);
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
        ResultSet resultSet =
                dbManager.select_query("select * from public.esperienze where id_esperienza=" +
                        invito_ricevuto.getId_esperienza());

        Esperienza esperienza = new DBEsperienza().crea_singola_esperienza(resultSet);
        Prenotazione prenotazione = new SimplePrenotazione(utente_corrente.getID(),
                esperienza.getId(), invito_ricevuto.getPosti_riservati(),
                invito_ricevuto.getData_scadenza_riserva(),
                esperienza.getCostoIndividuale().getValore(),
                esperienza.getCostoIndividuale().getValuta().toString());

        String format = MessageFormat.format(sql_insert, getToken(prenotazione));
        int id_generato = dbManager.insert_update_delete_query(format);
        if(id_generato!= -1){
            prenotazione.assegna_id(id_generato);
            prenotazioni.add(prenotazione);
        };

    }

    /**
     private String sql_insert = "INSERT INTO public.prenotazioni (id_esperienza, uid_turista, stato_prenotazione, " +
     " posti_prenotati, data_prenotazione , data_scadenza_riserva, costo_totale, valuta " +
     ") VALUES ( {0} , {1} , {2} , {3}, {4} , {5} , {6} , {7} );";
     * @param prenotazione proprietà recuperabili della prenotazione
     * @return <code>Object[]</code> un array di oggetti
     */
    private Object[] getToken(Prenotazione prenotazione){
        Object[] token = {
                prenotazione.getID_esperienza(),
                prenotazione.getID_turista(),
                "'"+prenotazione.getStatoPrenotazione().getN()+"'",
                prenotazione.getPosti(),
                "'"+prenotazione.getDataPrenotazione().toString().replace('T', ' ')+"'",
                "'"+prenotazione.getScadenza().toString().replace('T', ' ')+"'",
                "'"+prenotazione.getPrezzo_totale()+"'",
                "'"+prenotazione.getValuta()+"'",
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
        StatoPrenotazione salva = propPrenotazione.getStatoPrenotazione();
        Object[] token = {
                nuovo_stato.getN(),
                propPrenotazione.getID_turista(),
                propPrenotazione.getID_prenotazione()
        };
        propPrenotazione.cambiaStatoPrenotazione(nuovo_stato);
        String sql_up = MessageFormat.format(sql_update, token);
        if(dbManager.insert_update_delete_query(sql_up)==-1){
            propPrenotazione.cambiaStatoPrenotazione(salva);
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
                                        prenotazione.getStatoPrenotazione().equals(statoPrenotazione),
                                Collectors.toList()));

    }

    /**
     * Recupera prenotazione dal suo identificativo
     * @param id identificativo
     * @return la prenotazione, sempre presente.
     */
    public Prenotazione getPrenotazione_by_Id(int id){
        return prenotazioni.stream().filter(prenotazione -> prenotazione.getID_prenotazione()==id).findFirst().get();
    }


}
