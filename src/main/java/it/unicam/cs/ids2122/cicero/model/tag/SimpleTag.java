package it.unicam.cs.ids2122.cicero.model.tag;

public class SimpleTag implements Tag {
    private final String name;
    private TagStatus status;

    public SimpleTag(String name, TagStatus status){
        this.name = name;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    @Override
    public TagStatus getState() {
        return status;
    }
}