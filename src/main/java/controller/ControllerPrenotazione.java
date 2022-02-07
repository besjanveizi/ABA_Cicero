package controller;



import model.esperienza.Esperienza;
import model.prenotazione.*;
import model.ruoli.Turista;
import view.CLI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * classi adibita per la gestione di una prenotazione.
 *
 * @param <T> una classe che estende il Turista
 */
public class ControllerPrenotazione<T extends Turista,E extends Esperienza> {


    private T t;

    private E esperienza;

    private IGestorePrenotazioni gestorePrenotazioni;

    private IGestorePagamento iGestorePagamento;

    private IDB database;

    private CLI cli;

    /**
     * @param database il database di riferimento
     * @param t un turista e derivato
     * @param esperienza un derivato della ricerca
     */
    public ControllerPrenotazione(T t, E esperienza, IDB database){
        if(t!=null && esperienza !=null && database!=null){
            this.t = t;
            this.esperienza = esperienza;
            this.database = database;
            cli = new CLI();
            gestorePrenotazioni = new GestorePrenotazioni(t,database);
            iGestorePagamento = new GestorePagamento(t,database);
        }else{
            throw new NullPointerException("errore nei parametri passati");
        }

    }

    /**
     * recupera una lista di ricercati che viene mostrata
     * poi selezionata dal suo indice
     *
     * @return l'esperienza selezionata

    private Esperienza setExp(){
        List<Esperienza> lista = r.
        lista.forEach(esperienza -> cli.message(esperienza.toString()));
        try{
             cli.message("seleziona esperienza");
             int indice = parseInt();
             Objects.checkIndex(indice, lista.size());
             return   lista.get(indice);
            }catch (NumberFormatException numberFormatException) {
                numberFormatException.printStackTrace();
                return null;
            }catch (IndexOutOfBoundsException indexOutOfBoundsException){
                indexOutOfBoundsException.printStackTrace();
                return null;
            }
        }*/

    /**
     * parsa un intero
     *
     * @return intero
     */
    private int parseInt() throws NumberFormatException{
        return Integer.parseInt(cli.cmd());
    }

    /**
     * bind tra il builder e il gestore disponibilità
     *
     * @param builderInterface il builder istanziato
     * @param IGestoreDisponibilita un gestore di disponibilità
     */
    private void selezionaPosti(IBuilderInterface builderInterface, IGestoreDisponibilita IGestoreDisponibilita){
        if(builderInterface != null) {
            while(true){
            cli.message("numero di posti da prenotare");
            int posti = parseInt();
            if (posti > 0) {
                try{
                    builderInterface.buildPostiPrenotati(posti, IGestoreDisponibilita);
                    break;
                }catch (BuilderRunTimeException exception){
                }catch (NumberFormatException numberFormatException){
                    numberFormatException.printStackTrace();
                }
               cli.message("errore, riprovare");
            }
        }
        }
    }

    /**
     * chiede se o meno è una riserva
     *
     * @param iBuilderInterface il builder istanziato
     */
    private void selezionaRiserva(IBuilderInterface iBuilderInterface){
        cli.message("riserva y/n");
        cli.message("di base è false");
        String s = cli.cmd();
        if(s.equals("y")){
            iBuilderInterface.buildRiserva(true);
        }
    }

    /**
     * chiede se o meno c'è un invito
     * se positivo, il gestore provvederà all' inserimento.
     *
     * @param iBuilderInterface il builder istanziato
     */
    private void selezionaInvito(IBuilderInterface iBuilderInterface){
        cli.message("invito y/n");
        cli.message("di base è false");
        String s = cli.cmd();
        if(s.equals("y")){
            iBuilderInterface.buildInvito(true);
            List<String> lista_degli_invitati = inserisciTurista();
            gestorePrenotazioni.prenotazioneConInvito(lista_degli_invitati);
        }
    }

    /**
     * richiede il numero dei turisti da invitare
     * poi richiede l' inserimento manuale dei nomi.
     *
     * @return la lista dei turisti da invitare
     */
    private List<String> inserisciTurista() {
        List<String> turisti = new ArrayList<>();
        cli.message("inserire numero di invitati");
        int numero = -1;
        try{
            numero = parseInt();
        }catch (NumberFormatException numberFormatException){
            numberFormatException.printStackTrace();
            return null;
        }
        cli.message("inserire nome turisti");
        int contatore = 0;
        while (contatore<numero){
            String nome = cli.cmd();
            turisti.add(nome);
            contatore++;
        }
        return turisti;
    }


    /**
     * richiede la conferma
     * se positiva richiama un gestore e crea un oggetto prenotazione
     * da inserire nella lista dal gestore
     *
     * @param iBuilderInterface il builder istanziato
     */
    private void conferma(IBuilderInterface iBuilderInterface,IGestoreDisponibilita iGestoreDisponibilita){
        String s = "";
        cli.message("conferma y/n");
        s = cli.cmd();
        if(s.equals("n")){
            return;
        }
        if(s.equals("y")){
            gestorePrenotazioni.aggiungiPrenotazione(new SimplePrenotazione((BuilderPrenotazione) iBuilderInterface.getResult()));
            iGestoreDisponibilita.aggiorna();
            cli.message("prenotazione avvenuta");
        }
    }


    /**
     * - sotto ipotesi che la ricerca sia già avvenuta
     * - recupera i metodi privati interni per rendere leggibile il flusso
     * - realizza il seq diagram.
     */
    public void richiestaDiPrenotazione() throws NullPointerException{
        cli.message("prenota esperienza selezionata");
        if(esperienza!=null){
            IGestoreDisponibilita disponibilita = new GestoreDisponibilita(esperienza,database);
            IBuilderInterface builder = gestorePrenotazioni.prenotaEsperienza((IProprietaEsperienza) esperienza);
            selezionaPosti(builder, disponibilita);
            selezionaRiserva(builder);
            selezionaInvito(builder);
            conferma(builder,disponibilita);
            System.out.println("ciao e divertiti");
        }else throw new NullPointerException("nessuna esperienza selezionata");
    }

    /**
     * recupera le esperienze, associate al turista
     * inserimento indice
     * conferma
     * rimozione
     * chiamata interna al gestore prenotazione
     * per la rimozione della prenotazione.
     */
    public void annullaPrenotazione() {
        cli.message("sequenza di annullamento");
        gestorePrenotazioni.showAll();
        cli.message("inserire indice");
        int indice = parseInt();
        cli.message("conferma y/n");
        String s = cli.cmd();
        if (s.equals("y")) {
            try{
                Objects.checkIndex(indice, gestorePrenotazioni.recuperaEsperienzeAssociate().size());
                gestorePrenotazioni.annullaPrenotazione(indice);
            }catch (IndexOutOfBoundsException e){
                e.printStackTrace();
                System.out.println("abort");
            }catch (NumberFormatException e){
                e.printStackTrace();
                System.out.println("abort");
            }
        }
    }


    /**
     * il turista decide di pagare la prenotazione
     */
    public void modificaStatoPrenotazione(){
        gestorePrenotazioni.showAll();
        List<Prenotazione> recupero = gestorePrenotazioni.recuperaEsperienzeAssociate();
        cli.message("indice della prenotazione da modificare");
        int indice = -1;
        Prenotazione iObjPrenotazione = checkIndex(recupero,indice);
        if(iObjPrenotazione==null) return;
        IBuilderInterface iBuilderInterface = gestorePrenotazioni.modificaPrenotazione(iObjPrenotazione);
        cli.message("vuoi confermare la prenotazione?  y/n");
        cli.message(" se y reindirizzamento al sistema di pagamento");
        String s = cli.cmd();
        if(s.equals("y")){
            Prenotazione iObjPrenotazione1 =  new SimplePrenotazione((BuilderPrenotazione) iBuilderInterface.buildRiserva(false).getResult());
            gestorePrenotazioni.annullaPrenotazione(indice);
            iGestorePagamento.addPagamento(iObjPrenotazione1);
            pagamento();
        }else{
           recupero.add(iObjPrenotazione);
        }
    }

    private void pagamento(){
        cli.message("sezione pagamento");
        iGestorePagamento.showAll();
        cli.message("selezionare l' indice della prenotazione da pagare");
        try {
            int indice = parseInt();
            Objects.checkIndex(indice, iGestorePagamento.getListaDaPagare().size());
            iGestorePagamento.pagaPrenotazione(indice);
        }catch (IndexOutOfBoundsException |NumberFormatException numberFormatException){
                return;
        }
    }

    private Prenotazione checkIndex(List<Prenotazione> recupero,int indice) {
        indice = parseInt();
        try{
            return recupero.remove(indice); // temporaneo
        }catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }
       return null;
    }


}
