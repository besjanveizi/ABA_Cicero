package model.esperienza;

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
