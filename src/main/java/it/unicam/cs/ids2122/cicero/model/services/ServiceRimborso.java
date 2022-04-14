package it.unicam.cs.ids2122.cicero.model.services;

import it.unicam.cs.ids2122.cicero.model.entities.rimborso.RichiestaRimborso;
import it.unicam.cs.ids2122.cicero.model.entities.rimborso.RimborsoStatus;
import it.unicam.cs.ids2122.cicero.model.entities.rimborso.SimpleRichiestaRimborso;


import java.text.MessageFormat;
import java.util.*;

/**
 * Singleton Service class per operazioni di persistenza riguardanti le richieste di rimborso.
 */
public class ServiceRimborso extends AbstractService<RichiestaRimborso>{
    private final String table_name_richieste_rimborsi = "public.richieste_rimborso";
    private final String pk_name = "id_rimborso";
    private final String col_names = "id_fattura, motivo_richiesta, stato, info_esito";
    private final String col_values = "VALUES ( {0} , {1} , {2} , {3} )";
    private final String select_base_query = "SELECT " + pk_name + ", " + col_names + " FROM " + table_name_richieste_rimborsi;
    private final String insert_query = "INSERT INTO " + table_name_richieste_rimborsi + " (" + col_names + ") " + col_values + ";";
    private final String update_query = "UPDATE " + table_name_richieste_rimborsi + " SET stato= {0} WHERE "+pk_name+"= {1}";

    private static ServiceRimborso instance = null;

    private ServiceRimborso(){}

    public static ServiceRimborso getInstance(){
        if(instance==null) instance=new ServiceRimborso();
        return instance;
    }

    /**
     * Recupera tutte le richieste di rimborso.
     * @return Set di richieste di rimborso.
     */
    public Set<RichiestaRimborso> getRichiesteRimborso(){
        return parseDataResult(getDataResult(select_base_query + ";"));
    }


    /**
     * Inserisce una nuova richiesta di rimborso nel sistema.
     * @param id_fattura identificativo della fattura da rimborsare.
     * @param motivoRichiesta stringa che descrive il motivo della richiesta.
     * @param stato stato della richiesta di rimborso.
     * @return Richiesta di rimborso generata
     */
    public RichiestaRimborso insertRichiestaRimborso(int id_fattura, String motivoRichiesta, RimborsoStatus stato){
        int id= getGeneratedKey(MessageFormat.format(insert_query,id_fattura,"'"+motivoRichiesta+"'",stato.getCode(),null));
        return new SimpleRichiestaRimborso(id,id_fattura,motivoRichiesta,stato);
    }

    /**
     * Aggiorna lo stato di una richiesta di rimborso.
     * @param richiesta richiesta di rimborso di cui si vuole cambiare stato.
     * @param stato nuovo stato della richiesta di rimborso.
     */
    public void updateRichiestaRimborsoStatus(RichiestaRimborso richiesta, RimborsoStatus stato){
        getGeneratedKey(MessageFormat.format(update_query,stato.getCode(),richiesta.getId()));
    }

    @Override
    public Set<RichiestaRimborso> parseDataResult(TreeMap<String, HashMap<String, String>> rimborsi) {
        Set<RichiestaRimborso> resultSet = new HashSet<>();
        int id_rimborso =0, stato = 0, id_fattura = 0;
        String motivoRichiesta="",infoEsito = "";
        for (Map.Entry<String, HashMap<String, String>> firstEntry : rimborsi.entrySet()) {
            id_rimborso = Integer.parseInt(firstEntry.getKey());
            HashMap<String, String> others = firstEntry.getValue();
            for (Map.Entry<String, String> secondEntry : others.entrySet()) {
                String key = secondEntry.getKey();
                String val = secondEntry.getValue();
                switch (key) {
                    case "id_fattura": id_fattura = Integer.parseInt(val); break;
                    case "motivo_richiesta": motivoRichiesta = val; break;
                    case "stato": stato = Integer.parseInt(val); break;
                    case "info_esito": infoEsito = val; break;
                    default: break;
                }
            }
            resultSet.add(new SimpleRichiestaRimborso(id_rimborso, id_fattura, motivoRichiesta, RimborsoStatus.fetchStatus(stato), infoEsito));
        }
        return resultSet;
    }
}
