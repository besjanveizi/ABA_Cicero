package it.unicam.cs.ids2122.cicero.model.services;

import it.unicam.cs.ids2122.cicero.model.entities.esperienza.percorso.Attivita;
import it.unicam.cs.ids2122.cicero.model.entities.esperienza.percorso.Tappa;
import it.unicam.cs.ids2122.cicero.model.entities.territorio.Area;

import java.text.MessageFormat;
import java.util.*;

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

    public void upload(Tappa t) {
        int gen_key = getGeneratedKey(MessageFormat.format(insert_query, t.getArea().getId(), "'" + t.getInfo() + "'"));
        t.setId(gen_key);
        t.getListAttivita().forEach(a -> ServiceAttivita.getInstance().uploadAttivitaOf(t, a));
    }

    public Tappa download(int idTappa) throws PersistenceErrorException {
        Tappa t;
        Set<Tappa> resultSet = parseDataResult(
                getDataResult(select_base_query + " WHERE id_tappa = " + idTappa +";"));
        if (!resultSet.isEmpty()) {
             t = resultSet.stream().findFirst().get();
        }
        else {
            logger.severe("Data consistency error: couldn't find Tappa with id= " + idTappa + ".\n");
            throw new PersistenceErrorException();
        }
        List<Attivita> attivitaList = ServiceAttivita.getInstance().downloadAll(idTappa);
        t.addAllAttivita(attivitaList);
        return t;
    }


    @Override
    public Set<Tappa> parseDataResult(TreeMap<String, HashMap<String, String>> tappe) {
        Set<Tappa> resultSet = new HashSet<>();
        int id_tappa, id_area = 0;
        String info = "";

        for (Map.Entry<String, HashMap<String, String>> firstEntry : tappe.entrySet()) {
            id_tappa = Integer.parseInt(firstEntry.getKey());
            HashMap<String, String> others = firstEntry.getValue();
            for (Map.Entry<String, String> secondEntry : others.entrySet()) {
                String key = secondEntry.getKey();
                String val = secondEntry.getValue();
                switch (key) {
                    case "id_area": id_area = Integer.parseInt(val); break;
                    case "info": info = val; break;
                    default: break;
                }
            }
            Area area = null;
            try {
                area = ServiceArea.getInstance().getArea(id_area);
            } catch (PersistenceErrorException e) {
                e.printStackTrace();
            }
            Tappa t = new Tappa(area, info);
            t.setId(id_tappa);
            resultSet.add(t);
        }
        return resultSet;
    }
}
