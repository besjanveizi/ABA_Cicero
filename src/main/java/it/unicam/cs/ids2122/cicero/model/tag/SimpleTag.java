package it.unicam.cs.ids2122.cicero.model.tag;

/**
 * Semplice implementazione di un <code>Tag</code>.
 */
public class SimpleTag implements Tag {

    private final String name;
    private final String descrizione;
    private TagStatus status;

    /**
     * Crea un semplice <code>Tag</code> con il {@link TagStatus} {@code PROPOSTO}.
     * @param name nome del <code>Tag</code>.
     * @param descrizione descrizione del <code>Tag</code>.
     */
    public SimpleTag(String name, String descrizione){
        this.name = name;
        this.descrizione = descrizione;
        this.status = TagStatus.PROPOSTO;
    }

    /**
     * Crea un semplice {@code Tag} impostando il suo stato.
     * @param name nome del {@code Tag}.
     * @param descrizione descrizione del {@code Tag}.
     * @param status stato del {@code Tag}.
     */
    public SimpleTag(String name, String descrizione, TagStatus status) {
        this.name = name;
        this.descrizione = descrizione;
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

    @Override
    public String toString() {
        return "SimpleTag{" +
                "name='" + name + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", status=" + status +
                '}';
    }
}