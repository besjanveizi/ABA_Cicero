package it.unicam.cs.ids2122.cicero.model.territorio;

public class SimpleToponimo implements Toponimo{
    private String Name;

    public SimpleToponimo(String Name){
        this.Name=Name;
    }

    @Override
    public String getName() {
        return Name;
    }
}
