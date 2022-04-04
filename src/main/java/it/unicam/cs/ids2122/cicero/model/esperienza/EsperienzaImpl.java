package it.unicam.cs.ids2122.cicero.model.esperienza;

import com.google.common.base.Objects;

public class EsperienzaImpl implements IEsperienza {

    private final int id;
    private final InfoEsperienza infoEsperienza;

    public EsperienzaImpl(int id, InfoEsperienza infoEsperienza) {
        this.id = id;
        this.infoEsperienza = infoEsperienza;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public InfoEsperienza info() {
        return infoEsperienza;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EsperienzaImpl that = (EsperienzaImpl) o;
        return getId() == that.getId() && Objects.equal(infoEsperienza, that.infoEsperienza);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId(), infoEsperienza);
    }
}
