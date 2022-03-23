package it.unicam.cs.ids2122.cicero.model.territorio;

import it.unicam.cs.ids2122.cicero.model.territorio.SimpleToponimo;
import it.unicam.cs.ids2122.cicero.model.territorio.Toponimo;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe che gestisce la collezione di toponimi presenti nella piattaforma cicero
 */
public class GestoreToponimi {
    private List<SimpleToponimo> Toponimi;
    public GestoreToponimi(){
        Toponimi=new ArrayList<>();
    }

    /**
     *
     * @return lista di tutti i toponimi presenti
     */
    public List<Toponimo> getAllToponimi(){
        return new ArrayList<>(Toponimi);
    }

    //il seguente metodo è a scopo di testing
    public List<SimpleToponimo> getList(){return Toponimi;}
}