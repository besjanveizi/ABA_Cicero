package it.unicam.cs.ids2122.cicero.model.prenotazione_V3.sistema;

import it.unicam.cs.ids2122.cicero.model.esperienza.Esperienza;
import it.unicam.cs.ids2122.cicero.model.prenotazione_V3.persistenza.DBManager;
import it.unicam.cs.ids2122.cicero.model.tag.Tag;

import it.unicam.cs.ids2122.cicero.model.territorio.Area;
import it.unicam.cs.ids2122.cicero.view.IView;

import java.sql.SQLException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ServizioRicerca<E extends Esperienza> implements Service<E> {


    IView<String> cliView;
    public ServizioRicerca(IView cliView) {
    }



    private void showUltimaRicerca(){

    }

    private void dispose(){

    }

    @Override
    public E menu() {
        boolean flag = true;
        while (flag) {
            cliView.message("menu ricerca");
            cliView.message("0 uscita");
            cliView.message("1 ricerca esperienza toponimi tags");
            cliView.message("2 ricerca esperienza per nome");
            cliView.message("3 ricerca esperienza per prezzo");
            //.. etc..
            cliView.message("4 visualizza ricerca");
            cliView.message("5 seleziona esperienza ed esci");
            cliView.message("6 clear ultima ricerca");
            switch (cliView.ask("inserire")) {
                case "0": flag=false; break;
                case "1": break;
                case "2":
                //... continua
                case "4": ;
                    break;
                case "5": return null;
                case "6": dispose();
                default: continue;

            }

        }return null;
    }


}
