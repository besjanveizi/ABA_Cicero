package model.prenotazione;

import model.ruoli.Turista;

import java.util.ArrayList;
import java.util.List;

public class GestorePagamento<T extends Turista> implements IGestorePagamento {

    private T t;
    private IDB idb;
    private List<IObjPrenotazione> listaPrenotazioniDaPagare;
    private List<IObjPrenotazione> listaPagamentiEffettuati;

    public <T extends Turista> GestorePagamento(T t, IDB database) {
        //...TODO recupera le prenotazioni dal DB se non sono presenti attualmente delle prenotazioni da pagare o pagate.
        if(database==null){
            listaPrenotazioniDaPagare = new ArrayList<>();
            listaPagamentiEffettuati  = new ArrayList<>();
        }

    }

    @Override
    public void addPagamento(IObjPrenotazione iObjPrenotazione) {
        listaPrenotazioniDaPagare.add(iObjPrenotazione);
    }

    @Override
    public void pagaPrenotazione(int indice) {
        IObjPrenotazione prenotazione = listaPrenotazioniDaPagare.get(indice);
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
    public List<IObjPrenotazione> getListaPagati() {
        return listaPagamentiEffettuati;
    }

    @Override
    public List<IObjPrenotazione> getListaDaPagare() {
        return listaPrenotazioniDaPagare;
    }


}
