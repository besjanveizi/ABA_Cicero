package it.unicam.cs.ids2122.cicero.model.prenotazione.sistema;

import it.unicam.cs.ids2122.cicero.model.esperienza.Esperienza;
import it.unicam.cs.ids2122.cicero.model.prenotazione.gestori.GestoreBacheca;
import it.unicam.cs.ids2122.cicero.model.prenotazione.persistenza.DBManager;
import it.unicam.cs.ids2122.cicero.model.tag.Tag;

import it.unicam.cs.ids2122.cicero.model.territorio.Toponimo;
import it.unicam.cs.ids2122.cicero.view.IView;

import java.sql.SQLException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ServizioRicerca<E extends Esperienza> implements Service<E> {

    private DBManager dbManager;
    private IView<String> cliView;
    private GestoreBacheca gestoreBacheca;
    private List<Esperienza> ultima_ricerca;

    public ServizioRicerca(IView cliView,DBManager dbManager) throws SQLException {
        this.dbManager = dbManager;
        this.cliView = cliView;
        gestoreBacheca = new GestoreBacheca(dbManager);
    }


    private void ricerca_esperienza_toponimi_tag()  {
        Set<Toponimo> toponimi = new HashSet<>();
        Set<Tag> tags = new HashSet();
        String text =  null;
        while(true){
            cliView.message(" 'exit' per uscire, 'conferma' per confermare la selezione");
            cliView.message("inserire toponimi/o ");
            while(true){
                text = cliView.ask("inserire");
                if(text.equals("exit") || text.equals("conferma"))break;
                //toponimi.add(new SimpleToponimo(text));
            }
            cliView.message(" 'exit' per uscire, 'conferma' per confermare la selezione");
            cliView.message("inserire tag/s ");
            while(true){
                text = cliView.ask("inserire");
                if(text.equals("exit") || text.equals("conferma"))break;
                //tags.add(new SimpleTag(text));
            }
            break;
        }
        ultima_ricerca = ricerca(toponimi, tags);
    }

    private List<Esperienza> ricerca(Set<Toponimo> toponimi, Set<Tag> tags)  {
        Set<Toponimo> temp = new HashSet<>();
        Set<Tag> tempt = new HashSet<>();
        List<Esperienza> result = new ArrayList<>();
        gestoreBacheca.getAllEsperienze().stream().parallel().forEach(e ->  e.getAree().forEach(set -> {
            temp.addAll(toponimi);
            temp.retainAll((Collection<Toponimo>) set);
            if(!temp.isEmpty()){
                tempt.addAll(tags);
                tempt.retainAll(e.getTags());
                if (!tempt.isEmpty()){
                    result.add(e);
                }
            }
            temp.clear();
            tempt.clear();
        }));
        return result;
    }


    private Esperienza setEsp_selezionata(){
        if(ultima_ricerca!= null || !ultima_ricerca.isEmpty()){
            ultima_ricerca.stream().forEach(System.out::println);
            cliView.message("seleziona indice esperienza");
            try{
                return ultima_ricerca.get(cliView.fetchInt());
            }catch (IndexOutOfBoundsException e){
                System.out.println("errore durante la selezione");
            }
        }
        return null;
    }

    private void ricerca_nome(){
        String text= cliView.ask("inserisci nome");
        ultima_ricerca = gestoreBacheca.getAllEsperienze()
                .stream()
                .collect(Collectors
                        .filtering(esperienza -> esperienza.getName().equals(text) , Collectors.toList()));
    }


    private void ricerca_by_predicato(Predicate<Esperienza> predicato){
        ultima_ricerca = gestoreBacheca.getAllEsperienze()
                .stream()
                .collect(Collectors
                        .filtering(predicato , Collectors.toList()));
    }

    private void soutUltimaRicerca(){
        ultima_ricerca.stream().forEach(System.out::println);
    }

    private void dispose(){
        ultima_ricerca = null;
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
                case "1": ricerca_esperienza_toponimi_tag();break;
                case "2": ricerca_nome();
                //... continua
                case "4": soutUltimaRicerca();
                    break;
                case "5": return (E) setEsp_selezionata();
                case "6": dispose();
                default: continue;

            }

        }return null;
    }


}
