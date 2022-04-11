package it.unicam.cs.ids2122.cicero.model.entities.territorio;

import com.google.common.base.Objects;

/**
 * Semplice implementazione di un'{@code Area}.
 */
public class SimpleArea implements Area {

    private int id;
    private String toponimo;
    private String descrizione;

    /**
     * Crea una semplice {@code Area}.
     * @param id id dell'{@code Area}.
     * @param toponimo toponimo dell'{@code Area}.
     * @param descrizione descrizione dell'{@code Area}.
     */
    public SimpleArea(int id, String toponimo, String descrizione) {
        this.id = id;
        this.toponimo = toponimo;
        this.descrizione = descrizione;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getToponimo() {
        return toponimo;
    }

    @Override
    public String getDescrizione() {
        return descrizione;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleArea that = (SimpleArea) o;
        return getId() == that.getId() && Objects.equal(getToponimo(), that.getToponimo()) && Objects.equal(getDescrizione(), that.getDescrizione());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId(), getToponimo(), getDescrizione());
    }

    @Override
    public String toString() {
        return "SimpleArea{" +
                "id=" + id +
                ", toponimo='" + toponimo + '\'' +
                ", descrizione='" + descrizione + '\'' +
                '}';
    }
}
