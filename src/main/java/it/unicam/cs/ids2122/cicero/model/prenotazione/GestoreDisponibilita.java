package it.unicam.cs.ids2122.cicero.model.prenotazione;

import it.unicam.cs.ids2122.cicero.model.esperienza.Esperienza;

import java.util.Objects;

public class GestoreDisponibilita<E extends Esperienza,I extends IDB> implements IGestoreDisponibilita {


    private int posti;
    private boolean status;
    private E e;
    private I i;

    public GestoreDisponibilita(E e, I i){
            this.e = e;
            this.i = i;
    }


    /**
     * recupera le informazioni dalla vista relativa
     * a quell' esperienza,cioè se è disponibile e il numero di posti
     * disponibili
     */
    public  void recuperaInformazioneDisponibilita(){
        if(this.e!=null){
            //..TODO DB
        }
    }

    @Override
    public int getPosti() {
        return Objects.requireNonNullElse(posti, -1);
    }

    @Override
    public boolean ok() {
        return Objects.requireNonNullElseGet(status, null);
    }

    @Override
    public void aggiorna() {
        //.. TODO modifica la disponibilità dell' esperienza corrente nel DATABASE
    }


}
