package it.unicam.cs.ids2122.cicero.model.prenotazione.bean;



public class PrenotazioneImp implements IPrenotazione {

    private int id_prenotazione;

    private BeanInfoPrenotazione beanInfoPrenotazione;

    public PrenotazioneImp(int id_prenotazione, BeanInfoPrenotazione beanInfoPrenotazione){
        this.beanInfoPrenotazione = beanInfoPrenotazione;
        this.id_prenotazione = getId();
    }

    @Override
    public int getId() {
        return id_prenotazione;
    }

    @Override
    public BeanInfoPrenotazione info() {
        return beanInfoPrenotazione;
    }



}
