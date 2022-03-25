package it.unicam.cs.ids2122.cicero.model.tag;

import it.unicam.cs.ids2122.cicero.model.tag.SimpleTag;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Classe che gestisce la collezione di tag presenti nella piattaforma cicero
 */
public class GestoreTag {
    private Set<Tag> allTags;
    private List<SimpleTag> Tags;
    public GestoreTag(){
        Tags=new ArrayList<>();
        allTags = updateTags();
    }

    private Set<Tag> updateTags() {
        //  TODO: update allTags with database select request from table "tags"
        //   return DBManager.select(DBTable.TAGS) -> crea e ritorna un HashSet di oggetti Tag
        return new HashSet<>();  // temporary
    }

    /**
     *
     * @return la lista di tutti i tag presenti
     */
    public List<SimpleTag> getAllTags(){
        return new ArrayList<>(Tags);
    }

    //il seguente metodo è a scopo di testing
    public List<SimpleTag> getList(){return Tags;}

    public Set<Tag> getTags(Predicate<Tag> p) {
        return allTags.stream().filter(p).collect(Collectors.toSet());
    }
}
