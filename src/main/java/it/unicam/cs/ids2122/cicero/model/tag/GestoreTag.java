package it.unicam.cs.ids2122.cicero.model.tag;

import it.unicam.cs.ids2122.cicero.persistence.services.ServiceTag;

import java.util.HashSet;
import java.util.Optional;
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
        ServiceTag service= ServiceTag.getInstance();
        allTags=service.getTags();
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
        ServiceTag service= ServiceTag.getInstance();
        service.insertTag(tagName,tagDescription,TagStatus.PROPOSTO);
        updateTags();
    }

    /**
     * Aggiunge un nuovo <code>Tag</code> alla piattaforma.
     * @param tagName nome
     * @param tagDescription descrizione
     * @param tagStatus stato
     */
    public void add(String tagName, String tagDescription, TagStatus tagStatus) {
        ServiceTag service= ServiceTag.getInstance();
        service.insertTag(tagName,tagDescription,tagStatus);
        updateTags();
    }

    /**
     * Modifica lo stato di uno specifico <code>Tag</code>.
     * @param tag <code>Tag</code>
     * @param status nuovo stato
     */
    public int changeStatus(Tag tag, TagStatus status){
        ServiceTag service= ServiceTag.getInstance();
        int ret=service.updateTagStatus(tag,status);
        updateTags();
        return ret;
    }
}
