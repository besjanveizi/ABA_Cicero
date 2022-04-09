package it.unicam.cs.ids2122.cicero.model.gestori;

import it.unicam.cs.ids2122.cicero.model.entities.tag.Tag;
import it.unicam.cs.ids2122.cicero.model.entities.tag.TagStatus;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Classe che gestisce la collezione di tag presenti nella piattaforma cicero
 */
public class GestoreTag {

    private static GestoreTag instance = null;
    private Set<Tag> allTags;

    private GestoreTag(){
        updateTags();
    }

    public static GestoreTag getInstance() {
        if (instance == null) instance = new GestoreTag();
        return instance;
    }

    private void updateTags() {
        //  TODO: update allTags with database select request from table "tags"
        //   allTags = DBManager.select(DBTable.TAGS) -> ritorna un insieme di oggetti Tag
        allTags = new HashSet<>();  // temporary
    }

    /**
     * Ritorna tutti i <code>Tag</code> che rispettano il predicato dato.
     * @param p predicato sui tag.
     * @return insieme dei <code>Tag</code> che rispettano il predicato.
     */
    public Set<Tag> getTags(Predicate<Tag> p) {
        return allTags.stream().filter(p).collect(Collectors.toSet());
    }

    /**
     * Propone un nuovo <code>Tag</code> all'amministrazione.
     * @param tagName nome
     * @param tagDescription descrizione
     */
    public void add(String tagName, String tagDescription) {
        add(tagName, tagDescription, TagStatus.PROPOSTO);
        /*
            TODO: insert tag into database and update database
                "INSERT INTO TAGS (NOME, STATO, DESCRIZIONE) VALUES('"+t.getName()+"','"+t.getState()+"','"+t.getDescrizione()+"');"
        */
        updateTags();
    }

    /**
     * Aggiunge un nuovo <code>Tag</code> alla piattaforma.
     * @param tagName nome
     * @param tagDescription descrizione
     * @param tagStatus stato
     */
    public void add(String tagName, String tagDescription, TagStatus tagStatus) {
        //Tag tag = new SimpleTag(tagName, tagDescription, tagStatus);
        //allTags.add(tag);
    }

    /**
     * Modifica lo stato di uno specifico <code>Tag</code>.
     * @param tag <code>Tag</code>
     * @param status nuovo stato
     */
    public void changeStatus(Tag tag, TagStatus status){
        /*
            TODO: update old tag
                "update tags set stato="+tag.getState.getCode+ where nome='"+tag.getName+"';"
        */
        updateTags();
    }
}
