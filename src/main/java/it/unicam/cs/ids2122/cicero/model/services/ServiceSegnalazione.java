package it.unicam.cs.ids2122.cicero.model.services;

import it.unicam.cs.ids2122.cicero.model.entities.segnalazione.Segnalazione;
import it.unicam.cs.ids2122.cicero.model.entities.segnalazione.SegnalazioneStatus;
import it.unicam.cs.ids2122.cicero.model.entities.segnalazione.SimpleSegnalazione;


import java.text.MessageFormat;
import java.util.*;

/**
 * Singleton Service class per operazioni di persistenza riguardanti le segnalazioni.
 */
public class ServiceSegnalazione extends AbstractService<Segnalazione>{
    private final String table_name_segnalazioni = "public.segnalazioni";
    private final String pk_name = "id_segnalazione";
    private final String col_names = "id_esperienza, uid, descrizione, stato";
    private final String col_values = "VALUES ( {0} , {1} , {2} , {3} )";
    private final String select_base_query = "SELECT " + pk_name + ", " + col_names + " FROM " + table_name_segnalazioni;
    private final String insert_query = "INSERT INTO " + table_name_segnalazioni + " (" + col_names + ") " + col_values + ";";
    private final String update_query = "UPDATE " + table_name_segnalazioni + " SET stato= {0} WHERE "+pk_name+"= {1}";

    private static ServiceSegnalazione instance = null;

    private ServiceSegnalazione(){ }

    public static ServiceSegnalazione getInstance(){
        if (instance == null)
            instance = new ServiceSegnalazione();
        return instance;
    }

    /**
     * Recupera le segnalazioni relative ad una certa esperienza.
     * @param id_Esperienza identificativo dell'esperienza.
     * @return Set di segnalazioni.
     */
    public Set<Segnalazione> getSegnalazioni(int id_Esperienza){
        return parseDataResult(getDataResult(select_base_query + " WHERE id_esperienza = " + id_Esperienza + ";"));
    }

    /**
     * Recupera le segnalazioni con lo stesso stato passato.
     * @param status stato che contraddistingue le segnalazioni che si vogliono recuperare.
     * @return Set di segnalazioni.
     */
    public Set<Segnalazione> getSegnalazioni(SegnalazioneStatus status){
        return parseDataResult(getDataResult(select_base_query + " WHERE stato = " + status.getCode() + ";"));
    }

    /**
     * Recupera tutte le segnalazioni.
     * @return Set di segnalazioni.
     */
    public Set<Segnalazione> getSegnalazioni(){
        return parseDataResult(getDataResult(select_base_query + ";"));
    }

    /**
     * Inserisce una nuova segnalazione nel sistema.
     * @param id_esperienza identificativo dell'esperienza relativa alla segnalazione
     * @param uid identificativo dell'utente.
     * @param descrizione stringa contenente il testo della segnalazioni.
     * @param stato stato della segnalazione.
     * @return Segnalazione generata.
     */
    public Segnalazione insertSegnalazione(int id_esperienza, int uid, String descrizione, SegnalazioneStatus stato){
        int id = getGeneratedKey(MessageFormat.format(insert_query, id_esperienza, uid , "'"+descrizione+"'", stato.getCode()));
        return new SimpleSegnalazione(id,id_esperienza, uid, descrizione, stato);
    }

    /**
     * Aggiorna lo stato di una segnalazione.
     * @param segnalazione segnalazione di cui si vuole cambiare stato.
     * @param stato nuovo stato della segnalazione.
     */
    public void updateSegnalazioneStatus(Segnalazione segnalazione, SegnalazioneStatus stato){
        getGeneratedKey(MessageFormat.format(update_query,stato.getCode(),segnalazione.getId()));
    }

    @Override
    public Set<Segnalazione> parseDataResult(TreeMap<String, HashMap<String, String>> segnalazioni) {
        Set<Segnalazione> resultSet = new HashSet<>();
        int id_segnalazione =0, stato = 0, uid = 0, id_esperienza = 0;
        String descrizione = "";
        for (Map.Entry<String, HashMap<String, String>> firstEntry : segnalazioni.entrySet()) {
            id_segnalazione = Integer.parseInt(firstEntry.getKey());
            HashMap<String, String> others = firstEntry.getValue();
            for (Map.Entry<String, String> secondEntry : others.entrySet()) {
                String key = secondEntry.getKey();
                String val = secondEntry.getValue();
                switch (key) {
                    case "id_esperienza": id_esperienza = Integer.parseInt(val); break;
                    case "stato": stato = Integer.parseInt(val); break;
                    case "descrizione": descrizione = val; break;
                    default: break;
                }
            }
            resultSet.add(new SimpleSegnalazione(id_segnalazione, id_esperienza, uid, descrizione, SegnalazioneStatus.fetchStatus(stato)));
        }
        return resultSet;
    }
}
