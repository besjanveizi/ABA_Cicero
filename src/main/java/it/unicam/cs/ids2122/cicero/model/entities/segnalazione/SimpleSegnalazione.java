package it.unicam.cs.ids2122.cicero.model.entities.segnalazione;

/**
 * Rappresenta una semplice implementazione di una{@code Segnalazione} nella piattaforma Cicero.
 */
public class SimpleSegnalazione implements Segnalazione {
    private int id_segnalazione;
    private int id_esperienza;
    private int uid;
    private final String descrizione;
    private SegnalazioneStatus stato;

    public SimpleSegnalazione(int id_segnalazione, int id_esperienza, int uid, String descrizione, SegnalazioneStatus stato){
        this.id_segnalazione=id_segnalazione;
        this.id_esperienza=id_esperienza;
        this.uid=uid;
        this.descrizione=descrizione;
        this.stato=stato;
    }
    @Override
    public int getId() {
        return id_segnalazione;
    }

    @Override
    public int getUId() {
        return uid;
    }

    @Override
    public int getEsperienzaId() {
        return id_esperienza;
    }

    @Override
    public String getDescrizione() {
        return descrizione;
    }

    @Override
    public SegnalazioneStatus getState() {
        return stato;
    }

    @Override
    public String toString() {
        return "SimpleSegnalazione{" +
                "id_segnalazione=" + id_segnalazione +
                ", id_esperienza=" + id_esperienza +
                ", uid=" + uid +
                ", descrizione='" + descrizione + '\'' +
                ", stato=" + stato +
                '}';
    }
}
