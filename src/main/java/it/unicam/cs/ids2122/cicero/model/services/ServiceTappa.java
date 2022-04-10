package it.unicam.cs.ids2122.cicero.model.services;

import it.unicam.cs.ids2122.cicero.model.entities.esperienza.percorso.Tappa;
import it.unicam.cs.ids2122.cicero.ruoli.IUtente;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;

public class ServiceTappa extends AbstractService<Tappa> {

    private static ServiceTappa instance = null;

    private final String table_name = "public.tappe";
    private final String pk_name = "id_tappa";
    private final String col_names = "id_area, info";
    private final String col_values = "VALUES ( {0} , {1} )";
    private final String select_base_query = "SELECT " + pk_name + ", " + col_names + " FROM " + table_name;
    private final String insert_query = "INSERT INTO " + table_name + " (" + col_names + ") " + col_values + ";";


    private ServiceTappa() {}


    public static ServiceTappa getInstance() {
        if (instance == null)
            instance = new ServiceTappa();
        return instance;
    }

    public Tappa download(int idTappa) {
        Set<Tappa> resultSet = parseDataResult(
                getDataResult(select_base_query + " WHERE id_tappa = '" + idTappa +";"));
        return null;
    }


    @Override
    public Set<Tappa> parseDataResult(TreeMap<String, HashMap<String, String>> dataResult) {
        return null;
    }
}
