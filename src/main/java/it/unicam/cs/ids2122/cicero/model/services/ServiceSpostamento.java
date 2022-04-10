package it.unicam.cs.ids2122.cicero.model.services;

import it.unicam.cs.ids2122.cicero.model.entities.esperienza.percorso.Percorso;
import it.unicam.cs.ids2122.cicero.model.entities.esperienza.percorso.Spostamento;
import it.unicam.cs.ids2122.cicero.model.entities.esperienza.percorso.Tappa;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.*;

public class ServiceSpostamento extends AbstractService<Spostamento> {

    private static ServiceSpostamento instance = null;

    private final String table_name = "public.spostamenti";
    private final String pk_name = "id_spostamento";
    private final String col_names = "id_esperienza, id_tappa_partenza, id_tappa_destinazione, info_spostamento, " +
            "indice_spostamento";
    private final String col_values = "VALUES ( {0} , {1} , {2} , {3} , {4} )";
    private final String select_base_query = "SELECT " + pk_name + ", " + col_names + " FROM " + table_name;
    private final String insert_query = "INSERT INTO " + table_name + " (" + col_names + ") " + col_values + ";";


    private ServiceSpostamento() {}


    public static ServiceSpostamento getInstance() {
        if (instance == null)
            instance = new ServiceSpostamento();
        return instance;
    }

    public Percorso download(int idEsperienza) {
        return null;
    }

    public void upload(Percorso percorso, int idEsperienza) {
        int i = 0;
        for (Spostamento s : percorso.getSpostamenti()) {
            int gen_key = getGeneratedKey(
                    MessageFormat.format(insert_query, idEsperienza, s.getPartenza().getId(),
                            s.getDestinazione().getId(), "'" + s.getInfoSpostamento() + "'", i++));
        }
    }

    @Override
    public List<Spostamento> parseDataResult(TreeMap<String, HashMap<String, String>> spostamenti) {

        List<Spostamento> resultList = new ArrayList<>();
        int idSpostamento, idEsperienza, idTappaPartenza = 0, idTappaDestinazione = 0, indiceSpostamento;
        String infoSpostamento;

        for (Map.Entry<String, HashMap<String, String>> firstEntry : spostamenti.entrySet()) {
            idSpostamento = Integer.parseInt(firstEntry.getKey());
            HashMap<String, String> others = firstEntry.getValue();
            for (Map.Entry<String, String> secondEntry : others.entrySet()) {
                String key = secondEntry.getKey();
                String val = secondEntry.getValue();
                switch (key) {
                    case "id_esperienza": idEsperienza = Integer.parseInt(val); break;
                    case "id_tappa_partenza": idTappaPartenza = Integer.parseInt(val); break;
                    case "id_tappa_destinazione": idTappaDestinazione = Integer.parseInt(val); break;
                    case "info_spostamento": infoSpostamento = val; break;
                    case "indice_spostamento": indiceSpostamento = Integer.parseInt(val); break;
                    default: break;
                }
            }
            Tappa partenza = ServiceTappa.getInstance().download(idTappaPartenza);
            Tappa destinazione = ServiceTappa.getInstance().download(idTappaDestinazione);



            //InfoEsperienza infoE = new InfoEsperienza()
            //resultSet.add();
        }



        return null;
    }
}
