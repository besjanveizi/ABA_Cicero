package it.unicam.cs.ids2122.cicero.model.services;

import it.unicam.cs.ids2122.cicero.model.entities.tag.SimpleTag;
import it.unicam.cs.ids2122.cicero.model.entities.tag.Tag;
import it.unicam.cs.ids2122.cicero.model.entities.tag.TagStatus;

import java.text.MessageFormat;
import java.util.*;

/**
 * Singleton Service class per operazioni di persistenza riguardanti i tag.
 */
public class ServiceTag extends AbstractService<Tag> {

    private static ServiceTag instance = null;
    private ServiceTagsEsperienze serviceTE;

    private final String table_name_tags = "public.tags";
    private final String pk_name = "id_tag";
    private final String col_names = "nome, stato, descrizione";
    private final String col_values = "VALUES ( {0} , {1} , {2} )";
    private final String select_base_query = "SELECT " + pk_name + ", " + col_names + " FROM " + table_name_tags;
    private final String insert_query = "INSERT INTO " + table_name_tags + " (" + col_names + ") " + col_values + ";";
    private final String update_query = "UPDATE " + table_name_tags + " SET stato= {0} WHERE id_tag= {1}";


    private ServiceTag() {
        serviceTE = new ServiceTagsEsperienze();
    }

    public static ServiceTag getInstance() {
        if (instance == null)
            instance = new ServiceTag();
        return instance;
    }

    /**
     * Recupera i {@code Tag} cui lo stato &egrave come quello dato.
     * @param status {@link TagStatus} da filtrare.
     * @return {@code Set} dei {@code Tag} cui stato &egrave quello dato.
     */
    public Set<Tag> getTags(TagStatus status) {
        return parseDataResult(getDataResult(select_base_query + " WHERE stato = " + status.getCode() + ";"));
    }

    public Set<Tag> downloadTagsOf(int idEsperienza) {
        Set<Tag> downloadedTags = new HashSet<>();
        Set<Integer> tagIDsSet = serviceTE.download(idEsperienza);
        tagIDsSet.forEach(id -> download(id).ifPresent(downloadedTags::add));
        return downloadedTags;
    }

    public void uploadTagsOf(int idEsperienza, Set<Tag> tagSet) {
        serviceTE.upload(idEsperienza, tagSet);
    }

    public Optional<Tag> download(int idTag) {
        return parseDataResult(getDataResult(select_base_query + " WHERE id_tag = " + idTag + ";")).stream().findFirst();
    }

    /**
     * Recupera tutti i {@code Tag}.
     * @return {@code Set} di tutti i {@code Tag}.
     */
    public Set<Tag> getTags() {
        return parseDataResult(getDataResult(select_base_query + ";"));
    }

    /**
     * Inserisce una entry nella tabella dei {@code Tag} usando come valori le informazioni date.
     * @param nome nome del {@code Tag}.
     * @param descrizione descrizione del {@code Tag}.
     * @param stato {@link TagStatus} del {@code Tag}.
     * @return il {@code Tag} creato dalle informazioni date.
     */
    public Tag insertTag(String nome, String descrizione, TagStatus stato) {
        int id = getGeneratedKey(MessageFormat.format(insert_query, "'"+nome+"'", stato.getCode(), "'"+descrizione+"'"));
        return new SimpleTag(id, nome, descrizione, stato);
    }

    @Override
    public Set<Tag> parseDataResult(TreeMap<String, HashMap<String, String>> tags) {

        Set<Tag> resultSet = new HashSet<>();
        int id, stato = 0;
        String nome = "", descrizione = "";

        for (Map.Entry<String, HashMap<String, String>> firstEntry : tags.entrySet()) {
            id = Integer.parseInt(firstEntry.getKey());
            HashMap<String, String> others = firstEntry.getValue();
            for (Map.Entry<String, String> secondEntry : others.entrySet()) {
                String key = secondEntry.getKey();
                String val = secondEntry.getValue();
                switch (key) {
                    case "nome": nome = val; break;
                    case "stato": stato = Integer.parseInt(val); break;
                    case "descrizione": descrizione = val; break;
                    default: break;
                }
            }
            resultSet.add(new SimpleTag(id, nome, descrizione, TagStatus.fetchStatus(stato)));
        }

        return resultSet;
    }

    public int updateTagStatus(Tag tag,TagStatus status){
        return getGeneratedKey(MessageFormat.format(update_query,status.getCode(),tag.getId()));
    }

    private static class ServiceTagsEsperienze extends AbstractService<Integer> {
        private final String table_name = "public.tags_esperienze";
        private final String pk_T = "id_tag";
        private final String pk_E = "id_esperienza";
        private final String select_query = "SELECT " + pk_T + " FROM " + table_name;
        private String insert_query = "INSERT INTO " + table_name + " (" + pk_T + ", " + pk_E + ") VALUES ";

        public Set<Integer> download(int idEsperienza) {
            return parseDataResult(getDataResult(select_query + " WHERE id_esperienza = " +
                    idEsperienza + ";"));
        }

        public void upload(int idEsperienza, Set<Tag> tagSet) {
            StringBuilder s = new StringBuilder();
            tagSet.forEach( tag -> {
                s.append("(").append(tag.getId()).append(", ").append(idEsperienza).append("), ");
            });
            insert_query = insert_query + s;
            int lastIndex = insert_query.length();
            insert_query = insert_query.substring(0, lastIndex-2);
            int genKey = getGeneratedKey(insert_query);
        }

        @Override
        public Set<Integer> parseDataResult(TreeMap<String, HashMap<String, String>> dataResult) {
            Set<Integer> resultSet = new HashSet<>();
            int idTag;
            for (Map.Entry<String, HashMap<String, String>> firstEntry : dataResult.entrySet()) {
                idTag = Integer.parseInt(firstEntry.getKey());
                resultSet.add(idTag);
            }
            return resultSet;
        }
    }
}
