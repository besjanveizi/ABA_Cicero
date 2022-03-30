package it.unicam.cs.ids2122.cicero.model.territorio;

@Deprecated
public class SimpleToponimo implements Toponimo{
    private String toponimo;

    public SimpleToponimo(String toponimo) {
        this.toponimo = toponimo;
    }

    @Override
    public String getName() {
        return toponimo;
    }
}
