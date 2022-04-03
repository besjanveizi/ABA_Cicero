package it.unicam.cs.ids2122.cicero.model.tag;

/**
 * Semplice implementazione di un {@code Tag}.
 */
public class SimpleTag implements Tag {

    private final int id;

    private final String name;
    private final String descrizione;
    private TagStatus status;

    /**
     * Crea un semplice {@code Tag}.
     * @param id id del {@code Tag}.
     * @param name nome del {@code Tag}.
     * @param descrizione descrizione del {@code Tag}.
     * @param status {@link TagStatus} del {@code Tag}.
     */
    public SimpleTag(int id, String name, String descrizione, TagStatus status) {
        this.id = id;
        this.name = name;
        this.descrizione = descrizione;
        this.status = status;
    }

    public int getId() {
        return id;
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
    public String getDescrizione() { return descrizione; }

    public String toString() {
        return "SimpleTag{" +
                "name='" + name + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", status=" + status +
                '}';
    }
}