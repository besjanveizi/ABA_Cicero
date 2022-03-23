package it.unicam.cs.ids2122.cicero.model.tag;

public class SimpleTag implements Tag{
    private String Name;

    public SimpleTag(String Name){
        this.Name=Name;
    }

    public String getName() {
        return Name;
    }
}