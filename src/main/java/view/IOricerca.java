package view;

import model.esperienza.SimpleEsperienza;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class IOricerca {
    /** Questo metodo permette di stampare in ordine una lista di elementi per permettere la selezione tramite linea di comando all'utente
     *
     * @param list  Lista di elementi
     * @param <C>   tipo di elementi contenuti nella lista
     */
    public  <C> void showOrderedList(List<C> list,String text){
        if(list.isEmpty()){
            System.out.println(text+" vuoto");
        }else{
            System.out.println(text);
            int i=1;
            for (C elem:list) {
                System.out.print(i+": "+elem.toString());
            }
        }
    }

    /** Questo metodo legge stringhe contententi interi inseriti dall'utente tramite linea di comando e li restituisce come array
     *
     * @return array di interi
     */
    public int[] readIndexes(){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String  lines="";
        try {
            lines = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] strs = lines.trim().split("\\s+");
        int[] ret=new int[strs.length];
        for (int i = 0; i < strs.length; i++) {
            ret[i] = Integer.parseInt(strs[i])-1;
        }
        return ret;
    }

    public void startRicerca(){
        System.out.println("Per effettuare una ricerca inserire i numeri associati al toponimo o al tag con cui si desidera filtrare la lista di esperienze");
    }

    /**
     * Stampa in console tutte le informazioni riguardanti l'esperienza selezionata
     * @param esperienza l'esperienza di cui si vogliono conoscere i dettagli
     */
    public void showEsperienza(SimpleEsperienza esperienza) {
        System.out.println("Esperienza "+esperienza.getName()+", ID:"+esperienza.getId());
        System.out.println("Organizzata da: "+esperienza.getCiceroneCreatore());
        System.out.println("Data di inizio: "+esperienza.getDataInizio()+", Data di fine: "+esperienza.getDataFine());
        System.out.println("Tag: "+esperienza.getTagsAssociati().toString());
        System.out.println("Toponimi: "+esperienza.getToponimiAssociati().toString());
        System.out.println("Minimo numero di partecipanti: "+esperienza.getMinPartecipanti()+", massimo numero di partecipanti: "+ esperienza.getMaxPartecipanti());
        System.out.println("I posti potranno essere riservati per un massimo di "+esperienza.getMaxGiorniRiserva()+" giorni prima del pagamento.");
    }
}
