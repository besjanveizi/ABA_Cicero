package it.unicam.cs.ids2122.cicero.persistence.services;

import it.unicam.cs.ids2122.cicero.model.esperienza.IEsperienza;
import it.unicam.cs.ids2122.cicero.persistence.PGManager;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

public class ServiceEsperienza extends AbstractService<IEsperienza> {

    private static ServiceEsperienza instance = null;

    private final PGManager dbManager = PGManager.getInstance();

    private Set<IEsperienza> esperienze;

    private ServiceEsperienza() {}

    public static ServiceEsperienza getInstance() {
        if (instance == null)
            instance = new ServiceEsperienza();
        return instance;
    }

    private final String base_query = "SELECT id_esperienza, uid_cicerone, " +
            "nome, descrizione, data_pubblicazione, data_inizio, data_conclusione, data_termine, stato, " +
            "max_partecipanti, min_partecipanti, costo_individuale, valuta, max_riserva, posti_disponibili " +
            "FROM esperienze";

    public Set<IEsperienza> download(int idCicerone) {
        return execute(base_query + "WHERE uid_cicerone = " + idCicerone + ";");
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
                    case "data_pubblicazione": dataPubblicazione = LocalDateTime.parse(val); break;
                    case "data_inizio": dataInizio = LocalDateTime.parse(val); break;
                    case "data_conclusione": dataFine = LocalDateTime.parse(val); break;
                    case "data_termine": dataTermine = LocalDateTime.parse(val); break;
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
            //resultSet.add();
        }
        return resultSet;
    }
}
