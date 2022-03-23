package it.unicam.cs.ids2122.cicero.model.prenotazione;

import it.unicam.cs.ids2122.cicero.ruoli.Turista;

import java.util.ArrayList;
import java.util.List;

public class GestorePagamento<T extends Turista> implements IGestorePagamento {

    private T t;
    private IDB idb;
    private List<Prenotazione> listaPrenotazioniDaPagare;
    private List<Prenotazione> listaPagamentiEffettuati;

    public <T extends Turista> GestorePagamento(T t, IDB database) {
        //...TODO recupera le prenotazioni dal DB se non sono presenti attualmente delle prenotazioni da pagare o pagate.
        if(database==null){
            listaPrenotazioniDaPagare = new ArrayList<>();
            listaPagamentiEffettuati  = new ArrayList<>();
        }

    }

    @Override
    public void addPagamento(Prenotazione iObjPrenotazione) {
        listaPrenotazioniDaPagare.add(iObjPrenotazione);
    }

    @Override
    public void pagaPrenotazione(int indice) {
        Prenotazione prenotazione = listaPrenotazioniDaPagare.get(indice);
        //... recupera il conto dal turista.
        //... effettua la transazione sul conto del sistema
        //... sposta la prenotazione
        //... scrittura della prenotazione nel DB.
    }

    @Override
    public void showAll() {
        System.out.println("da pagare");
        listaPrenotazioniDaPagare.forEach(iObjPrenotazione -> System.out.println(iObjPrenotazione.toString()));
        System.out.println(" ");
        System.out.println("pagamenti effettuati");
        listaPagamentiEffettuati.forEach(iObjPrenotazione -> System.out.println(iObjPrenotazione.toString()));
    }

    @Override
    public List<Prenotazione> getListaPagati() {
        return listaPagamentiEffettuati;
    }

    @Override
    public List<Prenotazione> getListaDaPagare() {
        return listaPrenotazioniDaPagare;
    }


}
