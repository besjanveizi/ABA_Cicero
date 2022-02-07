package model.prenotazione;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Objects;


public class BuilderPrenotazione <E extends IProprietaEsperienza> implements IBuilderInterface {

    protected int ID;
    protected String nomeEsperienza;
    protected int posti;
    protected boolean riserva =false;
    protected int tempoDiRiserva;
    protected LocalDateTime data_creazione;
    protected LocalDateTime ttl;
    protected float prezzo_singolo;
    protected float prezzo_totale;
    protected boolean con_invito = false;

    public BuilderPrenotazione(){}

    public BuilderPrenotazione (E esperienza) {
        this.ID = esperienza.getId();
        this.nomeEsperienza = esperienza.getName();
        data_creazione = esperienza.getDataInizio();
        tempoDiRiserva = esperienza.getMaxGiorniRiserva();
        ttl = LocalDateTime.now().plus(tempoDiRiserva*24, ChronoUnit.HOURS);
        prezzo_singolo = esperienza.getPrezzo();
    }

    @Override
    public IBuilderInterface buildPostiPrenotati(int posti, IGestoreDisponibilita disponibilita) {
        if(disponibilita.ok() && posti > 0 && disponibilita.getPosti()<=posti) {
                this.posti = posti;
                this.prezzo_totale = posti * prezzo_singolo;
        }else{
            throw new BuilderRunTimeException("Numero posti inserito non valido");
        }
        return this;
    }

    @Override
    public IBuilderInterface buildRiserva(boolean b) {
        this.riserva = b;
        return this;
    }

    @Override
    public IBuilderInterface buildInvito(boolean b) {
        this.con_invito = b;
        return this;
    }

    @Override
    public IBuilderInterface getResult() {
        if(Arrays.stream(this.getClass().getDeclaredFields()).peek(field -> field.setAccessible(true)).filter(field -> {
            Object v = null;
            try {
                v = field.get(this);
                field.setAccessible(false);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if(v ==null){
                return  true;
            }else return false;
        }).count()>=1){
            return null;
        }
        return this;
    }

    @Override
    public IBuilderInterface reBuild(Prenotazione objPrenotazione) {
        this.ID = objPrenotazione.getId();
        this.nomeEsperienza = objPrenotazione.getName();
        this.data_creazione = objPrenotazione.getData_creazione();
        this.tempoDiRiserva = objPrenotazione.getTempoRiserva();
        this.ttl = objPrenotazione.getTtl();
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BuilderPrenotazione<?> that = (BuilderPrenotazione<?>) o;
        return data_creazione.equals(that.data_creazione) &&
                ttl.equals(that.ttl);
    }

    @Override
    public int hashCode() {
        return Objects.hash( data_creazione,ttl);
    }

    @Override
    public String toString() {
        return "BuilderPrenotazione{" +
                ", nomeEsperienza='" + nomeEsperienza + '\'' +
                ", posti=" + posti +
                ", riserva=" + riserva +
                ", data_creazione='" + data_creazione.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + '\'' +
                ", ttl=" + ttl.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) +
                '}';
    }

}
