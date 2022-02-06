package model.esperienza;

public class SimpleTag implements Tag{
    private String Name;

    public SimpleTag(String Name){
        this.Name=Name;
    }

    public String getName() {
        return Name;
    }
}