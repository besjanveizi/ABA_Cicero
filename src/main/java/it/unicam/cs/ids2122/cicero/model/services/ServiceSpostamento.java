package it.unicam.cs.ids2122.cicero.model.services;

import it.unicam.cs.ids2122.cicero.model.entities.esperienza.percorso.Percorso;
import it.unicam.cs.ids2122.cicero.model.entities.esperienza.percorso.Spostamento;
import it.unicam.cs.ids2122.cicero.model.entities.esperienza.percorso.Tappa;

import java.text.MessageFormat;
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

    /**
     * Scarica il percorso dell'esperienza cui id &egrave quello dato
     * @param idEsperienza id dell'esperienza cui si desidera il percorso
     * @return percorso dell'esperienza
     */
    public Percorso downloadPercorso(int idEsperienza) {
        List<Spostamento> resultList = parseDataResult(
                getDataResult(select_base_query + " WHERE id_esperienza = "+idEsperienza+";"));
        Percorso p = new Percorso();
        p.addAllSpostamenti(resultList);
        return p;
    }

    /**
     * Carica il percorso dell'esperienza indicata.
     * @param percorso percorso da caricare.
     * @param idEsperienza id dell'esperienza indicata
     */
    public void upload(Percorso percorso, int idEsperienza) {
        int i = 0;
        for (Spostamento s : percorso.getSpostamenti()) {
            Tappa partenza = s.getPartenza();
            Tappa destinazione = s.getDestinazione();
            ServiceTappa.getInstance().upload(partenza);
            ServiceTappa.getInstance().upload(destinazione);
            int gen_key = getGeneratedKey(
                    MessageFormat.format(insert_query, idEsperienza, partenza.getId(),
                            destinazione.getId(), "'" + s.getInfoSpostamento() + "'", i++));
            s.setId(gen_key);
        }
    }

    @Override
    public List<Spostamento> parseDataResult(TreeMap<String, HashMap<String, String>> spostamenti) {

        List<Spostamento> resultList = new ArrayList<>();

        for (Map.Entry<String, HashMap<String, String>> firstEntry : spostamenti.entrySet()) {

            int idSpostamento = Integer.parseInt(firstEntry.getKey());
            int indiceSpostamento = 0;
            Tappa partenza = null, destinazione = null;
            String infoSpostamento = "";
            HashMap<String, String> others = firstEntry.getValue();

            for (Map.Entry<String, String> secondEntry : others.entrySet()) {
                String key = secondEntry.getKey();
                String val = secondEntry.getValue();
                switch (key) {
                    case "id_tappa_partenza":
                        try {
                            partenza = ServiceTappa.getInstance().download(Integer.parseInt(val));
                        } catch (PersistenceErrorException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "id_tappa_destinazione":
                        try {
                            destinazione = ServiceTappa.getInstance().download(Integer.parseInt(val));
                        } catch (PersistenceErrorException e) {
                            e.printStackTrace();
                        }break;
                    case "info_spostamento": infoSpostamento = val; break;
                    case "indice_spostamento": indiceSpostamento = Integer.parseInt(val); break;
                    default: break;
                }
            }

            Spostamento spostamento = new Spostamento(indiceSpostamento, partenza, destinazione, infoSpostamento);
            spostamento.setId(idSpostamento);
            resultList.add(spostamento);
        }
        resultList.sort((s1, s2) -> s1.getIndice() < s2.getIndice() ? -1 : 0);
        return resultList;
    }
}
