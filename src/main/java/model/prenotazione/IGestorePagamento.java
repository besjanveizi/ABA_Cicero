package model.prenotazione;

import java.util.List;

public interface IGestorePagamento {

    void addPagamento(IObjPrenotazione iObjPrenotazione);

    void pagaPrenotazione(int indice);

    void showAll();

    List<IObjPrenotazione> getListaPagati();

    List<IObjPrenotazione> getListaDaPagare();
}
