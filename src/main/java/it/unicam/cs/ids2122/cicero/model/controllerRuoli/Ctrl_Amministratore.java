package it.unicam.cs.ids2122.cicero.model.controllerRuoli;

import it.unicam.cs.ids2122.cicero.model.entities.bean.BeanFattura;
import it.unicam.cs.ids2122.cicero.model.entities.bean.BeanInvito;
import it.unicam.cs.ids2122.cicero.model.entities.bean.BeanPrenotazione;
import it.unicam.cs.ids2122.cicero.model.entities.bean.StatoPrenotazione;
import it.unicam.cs.ids2122.cicero.model.entities.esperienza.Esperienza;
import it.unicam.cs.ids2122.cicero.model.entities.esperienza.EsperienzaStatus;
import it.unicam.cs.ids2122.cicero.model.entities.rimborso.RichiestaRimborso;
import it.unicam.cs.ids2122.cicero.model.entities.rimborso.RimborsoStatus;
import it.unicam.cs.ids2122.cicero.model.entities.segnalazione.Segnalazione;
import it.unicam.cs.ids2122.cicero.model.entities.segnalazione.SegnalazioneStatus;
import it.unicam.cs.ids2122.cicero.model.entities.territorio.Area;
import it.unicam.cs.ids2122.cicero.model.gestori.*;
import it.unicam.cs.ids2122.cicero.model.entities.tag.Tag;
import it.unicam.cs.ids2122.cicero.model.entities.tag.TagStatus;
import it.unicam.cs.ids2122.cicero.model.services.ServiceEsperienza;
import it.unicam.cs.ids2122.cicero.ruoli.Amministratore;
import it.unicam.cs.ids2122.cicero.ruoli.IUtente;
import it.unicam.cs.ids2122.cicero.ruoli.UtenteType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
/**
 * Rappresenta un gestore radice un utente <code>Amministratore</code> che elabora le sue interazioni con il sistema.
 */
public class Ctrl_Amministratore extends Ctrl_UtenteAutenticato implements Ctrl_Utente {

    private final GestoreTag gestoreTag;
    private final GestoreAree gestoreAree;
    private final GestoreUtenti gestoreUtenti;
    private final GestoreRicerca gestoreRicerca;
    private final GestoreSegnalazioni gestoreSegnalazioni;
    private final ServiceEsperienza serviceEsperienza;
    private final GestoreRimborsi gestoreRimborsi;
    private final GestoreEsperienze gestoreEsperienze;

    public Ctrl_Amministratore(Amministratore amministratore) {
        super(amministratore);
        impostaMenu();
        gestoreTag = GestoreTag.getInstance();
        gestoreAree = GestoreAree.getInstance();
        gestoreEsperienze = new GestoreEsperienze(amministratore);
        gestoreUtenti = GestoreUtenti.getInstance();
        gestoreSegnalazioni = GestoreSegnalazioni.getInstance();
        serviceEsperienza = ServiceEsperienza.getInstance();
        gestoreRimborsi = new GestoreRimborsi(utente);
        gestoreRicerca = new GestoreRicerca();
    }

    @Override
    protected boolean switchMenu(int scelta) {
        boolean loop = true;
        switch (scelta) {
            case 4:
                definisciArea();
                break;
            case 5:
                definisciTag();
                break;
            case 6:
                gestisciTagProposti();
                break;
            case 7:
                gestisciUtenti();
                break;
            case 8:
                gestisciSegnalazioni();
                break;
            case 9:
                gestisciRichiesteRimborso();
                break;
            case 10:
                cancellaEsperienza();
                break;
            default:
                loop = super.switchMenu(scelta);
        }
        return loop;
    }

    private void gestisciTagProposti() {
        while(true){
            Set<Tag> proposti = gestoreTag.getTags(e -> e.getState().equals(TagStatus.PROPOSTO));
            Set<String> viewSet=proposti.stream().map(Tag::getName).collect(Collectors.toSet());
            if(proposti.isEmpty()){
                view.message("Attualmente non ci sono Tag proposti nella piattaforma.");
                break;
            }
            view.message("Selezionare il tag che si vuole gestire:",viewSet);
            String nomeTag=view.fetchSingleChoice(viewSet);
            Optional<Tag> selectedTag=proposti.stream().filter(t->t.getName().equals(nomeTag)).findFirst();
            selectedTag.ifPresent(this::approvaTag);
            if(view.fetchChoice("Scegliere una delle seguenti alternative:\n1-Gestisci un altro tag\n2-Esci",2)==2)break;
        }
    }

    private void approvaTag(Tag t){
        view.message("Tag '"+t.getName()+"' selezionato\nDescrizione: "+t.getDescrizione()+"\nVuoi approvare il tag? [Y/n]");
        if(view.fetchBool()){
            gestoreTag.changeStatus(t,TagStatus.APPROVATO);
        }else{
            gestoreTag.changeStatus(t,TagStatus.RIFIUTATO);
        }
    }

    private void definisciTag() {
        Set<Tag> allTags = gestoreTag.getTags(t->true);
        boolean done=false,annulla=false;
        String nome="";
        while (!done && !annulla){
            nome=view.ask("Inserire il nome del tag che si vuole definire: ");
            if(nome.isEmpty()){
                view.message("Il nome del tag da creare non può essere vuoto!");
            }else{
                String finalNome = nome;
                Set<Tag> searchByNome =allTags.stream().filter(t->t.getName().equals(finalNome)).collect(Collectors.toSet());
                if(searchByNome.isEmpty()){
                    done=true;
                }else{
                    view.message("Un tag con lo stesso nome è gia presente.");
                    if(view.fetchChoice("Scegliere una delle seguenti alternative:\n1-Inserire un nuovo nome\n2-Esci",2)==2)annulla=true;
                }
            }
        }
        if(annulla){
            annullaDefinizione("tag");
            return;
        }
        done=false;
        String descrizione="";
        while (!done){
            descrizione=view.ask("Inserire la descrizione del tag "+nome+":");
            if(descrizione.isEmpty()){
                view.message("La descrizione inserita non è valida.");
            }else{
                done=true;
            }
        }
        view.message("Vuoi confermare la definizione del tag "+nome+" con descrizione "+descrizione+"?");
        if(view.fetchBool()){
            gestoreTag.definisci(nome,descrizione,TagStatus.APPROVATO);
            view.message("il tag "+nome+" è stato aggiunto alla collezione di tag della piattaforma.");
        }else{
            annullaDefinizione("tag");
        }
    }
    private void annullaDefinizione(String tipo){
        view.message("La definizione del "+tipo+" è stata annullata.");
    }

    private void definisciArea() {
        Set<Area> allAree=gestoreAree.getAree();
        boolean done=false, annulla=false;
        String nome="";
        while (!done && !annulla){
            nome=view.ask("Inserire il nome del toponimo che si vuole definire: ");
            if(nome.isEmpty()){
                view.message("Il nome del toponimo da creare non può essere vuoto!");
            }else{
                String finalNome = nome;
                Set<Area> searchByNome =allAree.stream().filter(a->a.getToponimo().equals(finalNome)).collect(Collectors.toSet());
                if(searchByNome.isEmpty()){
                    done=true;
                }else{
                    view.message("Un toponimo con lo stesso nome è gia presente.");
                    if(view.fetchChoice("Scegliere una delle seguenti alternative:\n1-Inserire un nuovo nome\n2-Esci",2)==2)annulla=true;
                }
            }
        }
        if(annulla){
            annullaDefinizione("toponimo");
            return;
        }
        done=false;
        String descrizione="";
        while (!done){
            descrizione=view.ask("Inserire la descrizione del toponimo '"+nome+"':");
            if(descrizione.isEmpty()){
                view.message("La descrizione inserita non è valida.");
            }else{
                done=true;
            }
        }
        view.message("Vuoi confermare la definizione del toponimo '"+nome+"' con descrizione '"+descrizione+"'?");
        if(view.fetchBool()){
            gestoreAree.add(nome,descrizione);
            view.message("il toponimo '"+nome+"' è stato aggiunto alla collezione di tag della piattaforma.");
        }else{
            annullaDefinizione("toponimo");
        }
    }

    private void gestisciUtenti(){
        while(true){
            Set<IUtente> utenti=gestoreUtenti.getUtenti(u->u.getType().equals(UtenteType.CICERONE)||u.getType().equals(UtenteType.TURISTA));
            Set<String> viewSet=utenti.stream().map(IUtente::getUsername).collect(Collectors.toSet());
            view.message("Selezionare uno degli utenti registrati nella piattaforma:",viewSet);
            String nomeUtente=view.fetchSingleChoice(viewSet);
            IUtente utenteScelto=utenti.stream().filter(u->u.getUsername().equals(nomeUtente)).findFirst().orElseThrow();
            view.message("Utente scelto: \nusername:"+utenteScelto.getUsername()+"\nID:"+utenteScelto.getUID()+"\nTipo:"+utenteScelto.getType()+"\nEmail:"+utenteScelto.getEmail()+"\nProcedere con l'eliminazione dell'utente selezionato?");
            if(view.fetchBool()){
                gestoreUtenti.deleteUtente(utenteScelto.getUID());
                view.message("L'utente scelto è stato eliminato.");
            }else {
                break;
            }
            if(view.fetchChoice("Selezionare una delle seguenti alternative:\n1)Selezionare un nuovo profilo da gestire\n2)Uscire",2)==2)break;
        }
    }

    private void gestisciSegnalazioni(){
        while(true){
            Set<Segnalazione> segnalazioni=gestoreSegnalazioni.getSegnalazioni(s->s.getState().equals(SegnalazioneStatus.PENDING));
            if(segnalazioni.isEmpty()){
                view.message("Non sono presenti segnalazioni nella piattaforma.");
                break;
            }
            Set<String> viewSet=segnalazioni.stream().map(Segnalazione::getId).collect(Collectors.toSet()).stream().map(String::valueOf).collect(Collectors.toSet());
            view.message("Selezionare una delle segnalazioni presenti nella piattaforma:",viewSet);
            int id = Integer.parseInt(view.fetchSingleChoice(viewSet));
            Segnalazione segnalazione= segnalazioni.stream().filter(s->s.getId()==id).findFirst().orElseThrow();
            view.message("Segnalazione scelta:\nID:"+segnalazione.getId()+"\nID esperienza:"+segnalazione.getEsperienzaId()+"\nId utente:"+segnalazione.getUId()+"\nDescrizione:"+segnalazione.getDescrizione());
            if(view.fetchChoice("Selezionare una delle seguenti alternative:\n1)Accettare la segnalazione e cancellare l'esperienza associata\n2)Rifiutare la segnalazione",2)==1) {
                gestoreSegnalazioni.accettaSegnalazione(segnalazione);
                view.message("Pratica di cancellazione esperienza iniziata");
            }else{
                gestoreSegnalazioni.rifiutaSegnalazione(segnalazione);
                view.message("Segnalazione rifiutata");
            }
            if(view.fetchChoice("Selezionare una delle seguenti alternative:\n1)Selezionare una nuova segnalazione da gestire\n2)Uscire",2)==2)break;
        }
    }

    private void gestisciRichiesteRimborso(){
        while(true){
            RichiestaRimborso richiesta = selezionaRichiesta(
                    gestoreRimborsi.getRimborsi(r -> r.getState().equals(RimborsoStatus.PENDING)));
            if (richiesta == null) {
                view.message("Non ci sono richieste di rimborso in attesa di gestione.");
                return;
            }
            view.message(richiesta.toString());
            int scelta = view.fetchChoice("\n1) Accettare la richiesta di rimborso" +
                    "\n2) Rifiutare la richiesta di rimborso", 2);
            String motivo = view.ask("Inserisci il motivo della scelta appena fatta:");
            if(scelta == 1) {
                BeanFattura beanFattura = gestoreRimborsi.accettaRichiestaRimborso(richiesta, motivo);
                view.message("Richiesta di rimborso accettata");
                new GestoreFatture( utente).crea_fattura(beanFattura);
                view.message("Rimborso effettuato");
            }else{
               gestoreRimborsi.rifiutaRichiestaRimborso(richiesta, motivo);
                view.message("Richiesta di rimborso rifiutata");
            }
            if(view.fetchChoice("\n1) Selezionare una nuova richiesta di rimborso da gestire" +
                    "\n2) Uscire",2) == 2) break;
        }
    }

    private RichiestaRimborso selezionaRichiesta(Set<RichiestaRimborso> richieste) {
        if (richieste.isEmpty()) {
            return null;
        }
        List<String> viewList = new ArrayList<>();
        List<Integer> idList = new ArrayList<>();
        int i = 1;
        for (RichiestaRimborso r : richieste) {
            viewList.add(i++ + ") "+ r.shortToString());
            idList.add(r.getId());
        }
        view.message("Richieste di rimborso:", viewList);
        int indice = view.fetchChoice("Scegli l'indice della richiesta", viewList.size());
        int idRichiesta = idList.get(indice-1);
        return richieste.stream().filter(p -> p.getId() == idRichiesta).findFirst().orElseThrow();
    }

    private void cancellaEsperienza() {
        Set<Esperienza> esperienze = gestoreEsperienze
                .getAllEsperienze(e-> e.getStatus()== EsperienzaStatus.IDLE || e.getStatus()== EsperienzaStatus.VALIDA);
        Esperienza e = selezionaEsperienza(esperienze);
        if (e == null){
            view.message("Non ci sono esperienze da cancellare");
        }
        else {
            view.message(e.toString());
            Set<BeanPrenotazione> prenotazioni = gestoreEsperienze.getPrenotazioni(e,  p -> !p.getStatoPrenotazione().equals(StatoPrenotazione.CANCELLATA));
            Set<BeanInvito> inviti = gestoreEsperienze.getInviti(e);
            if (!prenotazioni.isEmpty() || !inviti.isEmpty()) {
                view.message("\nLa cancellazione dell'esperienza comporterà la cancellazione automatica " +
                        "(e rimborso se previsto) di " + prenotazioni.size() + " prenotazioni e "  + inviti.size() +
                        " inviti associati.\n");
            }
            int scelta = view.fetchChoice("1) Prosegui con la cancellazione\n2) Torna al menu principale",
                    2);
            if (scelta == 2) {
                view.message("L'esperienza non è stata cancellata");
            }
            else {
                gestoreEsperienze.cancellaEsperienza(e, prenotazioni, inviti);
                view.message("L'esperienza '" + e.getName() + "' è stata cancellata");
            }
        }
    }

    @Override
    protected Set<Esperienza> setRicerca(String filtroNome, Set<Area> filtroAree, Set<Tag> filtroTags) {
        return gestoreRicerca.ricerca_avanzata(filtroNome, filtroTags, filtroAree);
    }

    private void impostaMenu() {
        menuItems.add("4) Definisci Nuova Area");
        menuItems.add("5) Definisci Nuovo Tag");
        menuItems.add("6) Gestisci Tag Proposti");
        menuItems.add("7) Gestisci utenti");
        menuItems.add("8) Gestisci segnalazioni");
        menuItems.add("9) Gestisci richieste rimborso");
        menuItems.add("10) Cancella Esperienza");
    }
}

