package model.prenotazione;

import java.util.List;

public interface IGestorePagamento {

    void addPagamento(Prenotazione iObjPrenotazione);

    void pagaPrenotazione(int indice);

    void showAll();

    List<Prenotazione> getListaPagati();

    List<Prenotazione> getListaDaPagare();

}
