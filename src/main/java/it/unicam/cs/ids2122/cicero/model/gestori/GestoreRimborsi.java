package it.unicam.cs.ids2122.cicero.model.gestori;


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



}
