package model;

import model.esperienza.SimpleTag;

import java.util.ArrayList;
import java.util.List;
/**
 * Classe che gestisce la collezione di tag presenti nella piattaforma cicero
 */
public class GestoreTag {
    private List<SimpleTag> Tags;
    public GestoreTag(){
        Tags=new ArrayList<>();
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
}
