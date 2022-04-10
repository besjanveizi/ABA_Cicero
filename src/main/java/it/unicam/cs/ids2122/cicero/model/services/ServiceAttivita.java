package it.unicam.cs.ids2122.cicero.model.services;

import it.unicam.cs.ids2122.cicero.model.entities.esperienza.percorso.Attivita;

import java.util.*;

public class ServiceAttivita extends AbstractService<Attivita> {

    private static ServiceAttivita instance = null;

    private final String table_name = "public.attivita";
    private final String pk_name = "id_attivita";
    private final String col_names = "id_tappa, nome, descrizione, indice_attivita";
    private final String col_values = "VALUES ( {0} , {1} , {2} , {3} )";
    private final String select_base_query = "SELECT " + pk_name + ", " + col_names + " FROM " + table_name;
    private final String insert_query = "INSERT INTO " + table_name + " (" + col_names + ") " + col_values + ";";

    private ServiceAttivita() {}


    public List<Attivita> downloadAll(int idTappa) {
        return parseDataResult(
                getDataResult(select_base_query + " WHERE id_tappa = " + idTappa + ";"));
    }

    public static ServiceAttivita getInstance() {
        if (instance == null)
            instance = new ServiceAttivita();
        return instance;
    }

    @Override
    public List<Attivita> parseDataResult(TreeMap<String, HashMap<String, String>> attivita) {
        List<Attivita> resultList = new ArrayList<>();

        for (Map.Entry<String, HashMap<String, String>> firstEntry : attivita.entrySet()) {

            int indiceAttivita = 0, idAttivita = Integer.parseInt(firstEntry.getKey());
            String nome = "", descrizione = "";
            HashMap<String, String> others = firstEntry.getValue();

            for (Map.Entry<String, String> secondEntry : others.entrySet()) {
                String key = secondEntry.getKey();
                String val = secondEntry.getValue();
                switch (key) {
                    case "nome": nome = val; break;
                    case "descrizione": descrizione = val; break;
                    case "indice_attivita": indiceAttivita = Integer.parseInt(val); break;
                    default: break;
                }
            }
            Attivita a = new Attivita(indiceAttivita, nome, descrizione);
            a.setId(idAttivita);
            resultList.add(a);
        }
        resultList.sort((a1, a2) -> a1.getIndice() < a2.getIndice() ? -1 : 0);
        return resultList;
    }
}
