package it.unicam.cs.ids2122.cicero.model.services;

import it.unicam.cs.ids2122.cicero.model.esperienza.IEsperienza;
import it.unicam.cs.ids2122.cicero.model.esperienza.InfoEsperienza;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Singleton Service class per operazioni di persistenza riguardanti le esperienze.
 */
public class ServiceEsperienza extends AbstractService<IEsperienza> {

    private static ServiceEsperienza instance = null;

    private final String table_name = "public.esperienze";
    private final String pk_name = "id_esperienza";
    private final String col_names = "nome, descrizione, data_pubblicazione, data_inizio, data_conclusione, " +
                                     "data_termine, stato, max_partecipanti, min_partecipanti, costo_individuale, " +
                                     "valuta, max_riserva, posti_disponibili";
    private final String col_values = "VALUES ( {0} , {1} , {2} , {3} , {4} , {5} , {6} , {7} , {8} , {9} , {10} , " +
                                      "{11} , {12} , {13} )";
    private final String select_base_query = "SELECT " + pk_name + ", " + col_names + " FROM " + table_name;
    private final String insert_query = "INSERT INTO " + table_name + " (" + col_names + ") " + col_values + ";";

    private ServiceEsperienza() {}

    public static ServiceEsperienza getInstance() {
        if (instance == null)
            instance = new ServiceEsperienza();
        return instance;
    }

    /**
     * Recupera tutte le esperienze create dal {@code Cicerone} dato.
     * @param idCicerone id del {@code Cicerone}.
     * @return {@code Set} delle esperienze.
     */
    public Set<IEsperienza> download(int idCicerone) {
        return parseDataResult(
                getDataResult(select_base_query + "WHERE uid_cicerone = " + idCicerone + ";"));
    }

    /**
     * Carica le informazioni dell'esperienza nel database.
     * @param info informazioni dell'esperienza.
     * @return l'esperienza.
     */
    public IEsperienza upload (InfoEsperienza info) {
        int genKey = getGeneratedKey(
                MessageFormat.format(insert_query, "'" + info.getCiceroneCreatore().getUID()  + "'",
                "'" + info.getNome() + "'", "'" + info.getDescrizione() + "'",
                "'" + info.getDataPubblicazione().toString().replace("T", " ") + "'",
                "'" + info.getDataInizio().toString().replace("T", " ") + "'",
                "'" + info.getDataFine().toString().replace("T", " ") + "'",
                "'" + info.getDataTermine().toString().replace("T", " ") + "'",
                info.getStatus().getCode(), info.getMaxPartecipanti(), info.getMinPartecipanti(),
                "'" + info.getCostoIndividuale().getValore().toString() + "'",
                "'" + info.getCostoIndividuale().getValuta().toString() + "'",
                info.getMaxGiorniRiserva(), info.getPostiDisponibili()));
        // TODO:
        //  upload all the tags using genKey as id_esperienza and Tag.getId() as id_tag, delegate this to ServiceTag.upload(Set<Tag> s)
        //  upload spostamenti, tappe and attivita in their belonged table, delegate this to ServicePercorso.upload(Percorso p)
        //  IEsperienza e = new EsperienzaImpl(genKey, InfoEsperienza);
        return null;
    }

    @Override
    public Set<IEsperienza> parseDataResult(TreeMap<String, HashMap<String, String>> esperienze) {
        Set<IEsperienza> resultSet = new HashSet<>();
        int idEsperienza, uidCicerone, maxPartecipanti, minPartecipanti, maxRiserva, postiDisponibili, stato;
        String nome = "", descrizione = "";
        LocalDateTime dataPubblicazione, dataInizio, dataFine, dataTermine;
        BigDecimal costoIndividuale;
        Currency valuta;

        for (Map.Entry<String, HashMap<String, String>> firstEntry : esperienze.entrySet()) {
            idEsperienza = Integer.parseInt(firstEntry.getKey());
            HashMap<String, String> others = firstEntry.getValue();
            for (Map.Entry<String, String> secondEntry : others.entrySet()) {
                String key = secondEntry.getKey();
                String val = secondEntry.getValue();
                switch (key) {
                    case "nome": nome = val; break;
                    case "uid_cicerone": uidCicerone = Integer.parseInt(val); break;
                    case "descrizione": descrizione = val; break;
                    case "data_pubblicazione": dataPubblicazione =
                            LocalDateTime.parse(val.replace(" ", "T")); break;
                    case "data_inizio": dataInizio =
                            LocalDateTime.parse(val.replace(" ", "T")); break;
                    case "data_conclusione": dataFine =
                            LocalDateTime.parse(val.replace(" ", "T")); break;
                    case "data_termine": dataTermine =
                            LocalDateTime.parse(val.replace(" ", "T")); break;
                    case "stato": stato = Integer.parseInt(val); break;
                    case "max_partecipanti": maxPartecipanti = Integer.parseInt(val); break;
                    case "min_partecipanti": minPartecipanti = Integer.parseInt(val); break;
                    case "costo_individuale": costoIndividuale = new BigDecimal(val); break;
                    case "valuta": valuta = Currency.getInstance(val); break;
                    case "max_riserva": maxRiserva = Integer.parseInt(val); break;
                    case "posti_disponibili": postiDisponibili = Integer.parseInt(val); break;
                    default: break;
                }
            }
            //InfoEsperienza infoE = new InfoEsperienza()
            //resultSet.add();
        }
        return resultSet;
    }
}
