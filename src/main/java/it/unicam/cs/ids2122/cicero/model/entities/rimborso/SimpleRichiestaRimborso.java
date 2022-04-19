package it.unicam.cs.ids2122.cicero.model.entities.rimborso;

public class SimpleRichiestaRimborso implements RichiestaRimborso {
    private int id;
    private int idFattura;
    private final String motivoRichiesta;
    private RimborsoStatus stato;
    private String infoEsito;

    public SimpleRichiestaRimborso(int id, int idFattura,String motivoRichiesta, RimborsoStatus stato){
        this.id=id;
        this.idFattura=idFattura;
        this.motivoRichiesta=motivoRichiesta;
        this.stato=stato;
    }

    public SimpleRichiestaRimborso(int id, int idFattura,String motivoRichiesta, RimborsoStatus stato,String infoEsito){
        this.id=id;
        this.idFattura=idFattura;
        this.motivoRichiesta=motivoRichiesta;
        this.stato=stato;
        this.infoEsito=infoEsito;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getIdFattura() {
        return idFattura;
    }

    @Override
    public String getMotivoRichiesta() {
        return motivoRichiesta;
    }

    @Override
    public RimborsoStatus getState() {
        return stato;
    }

    @Override
    public String getInfoEsito() {
        return infoEsito;
    }

    public void setInfoEsito(String infoEsito) {
        this.infoEsito = infoEsito;
    }

    @Override
    public String toString() {
        return "\n---INFO RICHIESTA DI RIMBORSO---" +
                "\nID richiesta: " + id +
                "\nID fattura associata: " + idFattura +
                "\nMotivo della richiesta: '" + motivoRichiesta + '\'' +
                "\nStato della richiesta: " + stato +
                "\nInfo Esito deciso: '" + infoEsito + '\'';
    }

    @Override
    public String shortToString() {
        return "\nID fattura associata: " + idFattura +
                "\nMotivo della richiesta: '" + motivoRichiesta + '\'';
    }
}
