package it.unicam.cs.ids2122.cicero.model.gestori;

import it.unicam.cs.ids2122.cicero.model.entities.tag.Tag;
import it.unicam.cs.ids2122.cicero.model.entities.tag.TagStatus;
import it.unicam.cs.ids2122.cicero.model.services.ServiceTag;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Classe che gestisce la collezione di <code>Tag</code> presenti nella piattaforma cicero
 */
public class GestoreTag {

    private static GestoreTag instance = null;
    private Set<Tag> allTags;
    private final ServiceTag service;

    private GestoreTag() {
        service = ServiceTag.getInstance();
        updateTags();
    }

    /**
     * @return l'istanza aggiornata del gestore dei <code>Tag</code>.
     */
    public static GestoreTag getInstance() {
        if (instance == null) instance = new GestoreTag();
        return instance;
    }

    private void updateTags() {
        allTags = new HashSet<>();
        allTags = service.getTags();
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
    public void proponi(String tagName, String tagDescription) {
        service.insertTag(tagName,tagDescription,TagStatus.PROPOSTO);
        updateTags();
    }

    /**
     * Aggiunge un nuovo <code>Tag</code> alla piattaforma.
     * @param tagName nome
     * @param tagDescription descrizione
     * @param tagStatus stato
     */
    public void definisci(String tagName, String tagDescription, TagStatus tagStatus) {
        ServiceTag service= ServiceTag.getInstance();
        allTags.add(service.insertTag(tagName,tagDescription,tagStatus));
    }

    /**
     * Modifica lo stato di uno specifico <code>Tag</code>.
     * @param tag <code>Tag</code>
     * @param status nuovo stato
     */
    public void changeStatus(Tag tag, TagStatus status){
        ServiceTag service= ServiceTag.getInstance();
        service.updateTagStatus(tag,status);
        updateTags();
    }
}
