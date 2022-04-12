package it.unicam.cs.ids2122.cicero.model.gestori;


import it.unicam.cs.ids2122.cicero.model.entities.bean.BeanFattura;
import it.unicam.cs.ids2122.cicero.model.entities.bean.BeanPrenotazione;
import it.unicam.cs.ids2122.cicero.model.entities.bean.StatoPrenotazione;
import it.unicam.cs.ids2122.cicero.model.entities.esperienza.Esperienza;
import it.unicam.cs.ids2122.cicero.model.entities.esperienza.EsperienzaStatus;
import it.unicam.cs.ids2122.cicero.model.services.ServiceEsperienza;
import it.unicam.cs.ids2122.cicero.ruoli.IUtente;
import it.unicam.cs.ids2122.cicero.ruoli.Turista;

public final class GestoreRimborsi {

    private static GestoreRimborsi gestoreRimborsi = null;

    private IUtente iUtente;

    private GestoreRimborsi(IUtente iUtente){
        this.iUtente = iUtente;
    }


    public static GestoreRimborsi getInstance(Turista turista){
        if(gestoreRimborsi ==null){
            gestoreRimborsi = new GestoreRimborsi(turista);
        }return gestoreRimborsi;
    }


    public boolean rimborsa(BeanPrenotazione beanPrenotazione){
        Esperienza esperienza = ServiceEsperienza.getInstance().getEsperienza(beanPrenotazione.getID_esperienza());
        if(esperienza.getStatus().equals(EsperienzaStatus.IDLE) || esperienza.getStatus().equals(EsperienzaStatus.VALIDA) ){
            return true;
        }else{
            return false;
        }
    }


}
