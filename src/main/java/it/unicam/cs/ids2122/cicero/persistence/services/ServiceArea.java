package it.unicam.cs.ids2122.cicero.persistence.services;

import it.unicam.cs.ids2122.cicero.model.territorio.Area;
import it.unicam.cs.ids2122.cicero.model.territorio.SimpleArea;

import java.text.MessageFormat;
import java.util.*;

/**
 * Singleton Service class per operazioni di persistenza riguardanti le aree.
 */
public class ServiceArea extends AbstractService<Area> {

    private static ServiceArea instance = null;

    private final String table_name = "public.aree";
    private final String pk_name = "id_area";
    private final String col_names = "toponimo, descrizione";
    private final String col_values = "VALUES ( {0} , {1} )";
    private final String select_base_query = "SELECT " + pk_name + ", " + col_names + " FROM " + table_name;
    private final String insert_query = "INSERT INTO " + table_name + " (" + col_names + ") " + col_values + ";";

    private ServiceArea() {}

    public static ServiceArea getInstance() {
        if (instance == null)
            instance = new ServiceArea();
        return instance;
    }

    /**
     * Recupera tutte le aree dal database.
     * @return {@code Set} di {@code Area}.
     */
    public Set<Area> getAree() {
        return parseDataResult(getDataResult(select_base_query));
    }

    /**
     * Inserisce una entry nella tabella delle aree, usando come valori le informazioni date.
     * @param toponimo toponimo dell'{@code Area}.
     * @param descrizione descrizione dell'{@code Area}.
     * @return l''{@code Area} creata dalle informazioni date.
     */
    public Area insertArea(String toponimo, String descrizione) {
        int id = getGeneratedKey(MessageFormat.format(insert_query, "'"+toponimo+"'", "'"+descrizione+"'"));
        return new SimpleArea(id, toponimo, descrizione);
    }

    @Override
    public Set<Area> parseDataResult(TreeMap<String, HashMap<String, String>> aree) {

        Set<Area> resultSet = new HashSet<>();
        int id;
        String toponimo = "", descrizione = "";

        for (Map.Entry<String, HashMap<String, String>> firstEntry : aree.entrySet()) {
            id = Integer.parseInt(firstEntry.getKey());
            HashMap<String, String> others = firstEntry.getValue();
            for (Map.Entry<String, String> secondEntry : others.entrySet()) {
                String key = secondEntry.getKey();
                String val = secondEntry.getValue();
                switch (key) {
                    case "toponimo": toponimo = val; break;
                    case "descrizione": descrizione = val; break;
                    default: break;
                }
            }
            resultSet.add(new SimpleArea(id, toponimo, descrizione));
        }
        return resultSet;
    }
}
