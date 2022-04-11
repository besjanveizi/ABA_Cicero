package it.unicam.cs.ids2122.cicero.model.services;

import it.unicam.cs.ids2122.cicero.model.entities.esperienza.EsperienzaStatus;
import it.unicam.cs.ids2122.cicero.model.entities.esperienza.SimpleEsperienza;
import it.unicam.cs.ids2122.cicero.model.entities.esperienza.percorso.Percorso;
import it.unicam.cs.ids2122.cicero.model.entities.tag.Tag;
import it.unicam.cs.ids2122.cicero.ruoli.*;
import it.unicam.cs.ids2122.cicero.util.Money;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Singleton Service class per operazioni di persistenza riguardanti le esperienze.
 */
public class ServiceEsperienza extends AbstractService<SimpleEsperienza> {

    private static ServiceEsperienza instance = null;

    private final String table_name = "public.esperienze";
    private final String pk_name = "id_esperienza";
    private final String col_names = "uid_cicerone, nome, descrizione, data_pubblicazione, data_inizio, " +
                                     "data_conclusione, data_termine, stato, max_partecipanti, min_partecipanti, " +
                                     "costo_individuale, valuta, max_riserva, posti_disponibili";
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
    public Set<SimpleEsperienza> download(int idCicerone) {
        return parseDataResult(
                getDataResult(select_base_query + " WHERE uid_cicerone = " + idCicerone + ";"));
    }

    /**
     * Recupera tutte le esperienze salvate nella piattaforma.
     * @return {@code Set} delle esperienze.
     */
    public Set<SimpleEsperienza> download() {
        return parseDataResult(
                getDataResult(select_base_query + ";"));
    }

    /**
     * Carica le informazioni dell'esperienza nel database.
     * @param nomeE nome dell'esperienza
     * @param cicerone cicerone creatore.
     * @param descrizioneE descrizione dell'esperienza
     * @param dI data d'inizio dell'esperienza
     * @param dF data di conclusione dell'esperienza
     * @param minP numero minimo dei partecipanti all'esperienza
     * @param maxP numero minimo dei partecipanti all'esperienza
     * @param percorso percorso dell'esperienza
     * @param costoIndividuale costo individuale dell'esperienza
     * @param maxRiserva numero massimo di giorni che &egrave permesso riservare un posto all'esperienza
     * @param chosenTags tag selezionati per l'esperienza
     * @return l'esperienza
     */
    public SimpleEsperienza upload(String nomeE, Cicerone cicerone, String descrizioneE, LocalDateTime dI,
                                   LocalDateTime dF, int minP, int maxP, Percorso percorso, Money costoIndividuale,
                                   int maxRiserva, Set<Tag> chosenTags) {

        LocalDateTime dP = LocalDateTime.now();
        LocalDateTime dT = LocalDateTime.now().plusDays(2);
        EsperienzaStatus status = EsperienzaStatus.IDLE;

        int idEgenKey = getGeneratedKey(
                MessageFormat.format(insert_query, "'" + cicerone.getUID()  + "'",
                        "'" + nomeE + "'", "'" + descrizioneE + "'",
                        "'" + dP.toString().replace("T", " ") + "'",
                        "'" + dI.toString().replace("T", " ") + "'",
                        "'" + dF.toString().replace("T", " ") + "'",
                        "'" + dT.toString().replace("T", " ") + "'",
                        status.getCode(), maxP, minP, "'" + costoIndividuale.getValore().toString() + "'",
                        "'" + costoIndividuale.getValuta().toString() + "'", maxRiserva, maxP));
        ServiceSpostamento.getInstance().upload(percorso, idEgenKey);
        ServiceTag.getInstance().uploadTagsOf(idEgenKey, chosenTags);
        return new SimpleEsperienza(idEgenKey, nomeE, cicerone, descrizioneE, dI, dF, maxP, minP, percorso,
                costoIndividuale, maxRiserva, chosenTags, maxP, status, dP, dT);
    }

    @Override
    public Set<SimpleEsperienza> parseDataResult(TreeMap<String, HashMap<String, String>> esperienze) {
        Set<SimpleEsperienza> resultSet = new HashSet<>();
        int idEsperienza, uidCicerone = 0, maxPartecipanti = 0, minPartecipanti = 0, maxRiserva = 0, postiDisponibili = 0;
        String nome = "", descrizione = "";
        LocalDateTime dataPubblicazione = null, dataInizio = null, dataFine = null, dataTermine = null;
        BigDecimal costoIndividuale = null;
        Currency valuta = null;
        EsperienzaStatus stato = null;

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
                    case "stato": stato = EsperienzaStatus.fetchStatus(Integer.parseInt(val)); break;
                    case "max_partecipanti": maxPartecipanti = Integer.parseInt(val); break;
                    case "min_partecipanti": minPartecipanti = Integer.parseInt(val); break;
                    case "costo_individuale": costoIndividuale = new BigDecimal(val); break;
                    case "valuta": valuta = Currency.getInstance(val); break;
                    case "max_riserva": maxRiserva = Integer.parseInt(val); break;
                    case "posti_disponibili": postiDisponibili = Integer.parseInt(val); break;
                    default: break;
                }
            }

            Money costo = new Money(costoIndividuale, valuta);
            Cicerone ciceroneAutore = getCiceroneAutore(uidCicerone);
            Percorso percorso = ServiceSpostamento.getInstance().downloadPercorso(idEsperienza);
            Set<Tag> tags = ServiceTag.getInstance().downloadTagsOf(idEsperienza);

            SimpleEsperienza esperienza = new SimpleEsperienza(idEsperienza, nome, ciceroneAutore, descrizione,
                    dataInizio, dataFine, maxPartecipanti, minPartecipanti, percorso, costo,
                    maxRiserva, tags, postiDisponibili, stato, dataPubblicazione, dataTermine);
            resultSet.add(esperienza);
        }
        return resultSet;
    }

    private Cicerone getCiceroneAutore(int uidCicerone) {
        Cicerone ciceroneAutore = null;
        Optional<IUtente> optIU = ServiceUtente.getInstance().getUser(uidCicerone);
        if (optIU.isPresent()) {
            IUtente iu = optIU.get();
            if (iu.getType() == UtenteType.CICERONE) {
                ciceroneAutore = new Cicerone(iu.getUID(), iu.getUsername(), iu.getEmail(), iu.getPassword());
            }
        }
        return ciceroneAutore;
    }

    public SimpleEsperienza downloadE(int i) {
        return parseDataResult(
                getDataResult(select_base_query + " WHERE id_esperienza = " + i + ";")).stream().findFirst().orElse(null);
    }
}
