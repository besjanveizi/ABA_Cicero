package it.unicam.cs.ids2122.cicero.model.tag;

/**
 * Semplice implementazione di un <code>Tag</code>.
 */
public class SimpleTag implements Tag {

    private final String name;
    private TagStatus status;

    /**
     * Crea un semplice <code>Tag</code>.
     * @param name nome del <code>Tag</code>.
     * @param status stato del <code>Tag</code>.
     */
    public SimpleTag(String name, TagStatus status){
        this.name = name;
        this.status = status;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public TagStatus getState() {
        return status;
    }
}