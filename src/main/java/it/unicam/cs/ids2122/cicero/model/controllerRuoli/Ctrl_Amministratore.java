package it.unicam.cs.ids2122.cicero.model.controllerRuoli;

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
import it.unicam.cs.ids2122.cicero.view.IView;


import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
/**
 * Rappresenta un gestore radice un utente <code>Amministratore</code> che elabora le sue interazioni con il sistema.
 */
public class Ctrl_Amministratore extends Ctrl_UtenteAutenticato implements Ctrl_Utente {

    public Ctrl_Amministratore(IView<String> view, Amministratore amministratore) {
        super(view, amministratore);
        impostaMenu();
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
                gestisciRimborsi();
                break;
            default:
                loop = super.switchMenu(scelta);
        }
        return loop;
    }

    private void gestisciTagProposti() {
        while(true){
            GestoreTag gestoreTag= GestoreTag.getInstance();
            Set<Tag> proposti = gestoreTag.getTags(e -> e.getState().equals(TagStatus.PROPOSTO));
            Set<String> viewSet=proposti.stream().map(Tag::getName).collect(Collectors.toSet());
            if(proposti.isEmpty()){
                view.message("Attualmente non ci sono Tag proposti nella piattaforma.");
                return;
            }
            view.message("Selezionare il tag che si vuole gestire:",viewSet);
            String nomeTag=view.fetchSingleChoice(viewSet);
            Optional<Tag> selectedTag=proposti.stream().filter(t->t.getName().equals(nomeTag)).findFirst();
            selectedTag.ifPresent(this::approvaTag);
            if(view.fetchChoice("Scegliere una delle seguenti alternative:\n1-Gestisci un altro tag\n2-Esci",2)==2)break;
        }
    }

    private void approvaTag(Tag t){
        view.message("Tag "+t.getName()+" selezionato, descrizione: "+t.getDescrizione()+"."+"\nVuoi approvare il tag?");
        GestoreTag gestoreTag= GestoreTag.getInstance();
        if(view.fetchBool()){
            gestoreTag.changeStatus(t,TagStatus.APPROVATO);
        }else{
            gestoreTag.changeStatus(t,TagStatus.RIFIUTATO);
        }
    }

    private void definisciTag() {
        GestoreTag gestoreTag= GestoreTag.getInstance();
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
            gestoreTag.add(nome,descrizione,TagStatus.APPROVATO);
            view.message("il tag "+nome+" è stato aggiunto alla collezione di tag della piattaforma.");
        }else{
            annullaDefinizione("tag");
        }
    }
    private void annullaDefinizione(String tipo){
        view.message("La definizione del "+tipo+" è stata annullata.");
    }

    private void definisciArea() {
        GestoreAree gestoreAree=GestoreAree.getInstance();
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
        view.message("Vuoi confermare la proposta del toponimo '"+nome+"' con descrizione '"+descrizione+"'?");
        if(view.fetchBool()){
            gestoreAree.add(nome,descrizione);
            view.message("il toponimo '"+nome+"' è stato aggiunto alla collezione di tag della piattaforma.");
        }else{
            annullaDefinizione("toponimo");
        }
    }

    private void gestisciUtenti(){
        while(true){
            GestoreUtenti gestoreUtenti=GestoreUtenti.getInstance();
            Set<IUtente> utenti=gestoreUtenti.getUtenti(u->u.getType().equals(UtenteType.CICERONE)||u.getType().equals(UtenteType.TURISTA));
            Set<String> viewSet=utenti.stream().map(IUtente::getUsername).collect(Collectors.toSet());
            view.message("Selezionare uno degli utenti registrati nella piattaforma:",viewSet);
            String nomeUtente=view.fetchSingleChoice(viewSet);
            IUtente utenteScelto=utenti.stream().filter(u->u.getUsername().equals(nomeUtente)).findFirst().get();
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
            GestoreSegnalazioni gestoreSegnalazioni= GestoreSegnalazioni.getInstance();
            Set<Segnalazione> segnalazioni=gestoreSegnalazioni.getSegnalazioni(s->s.getState().equals(SegnalazioneStatus.PENDING));
            Set<String> viewSet=segnalazioni.stream().map(Segnalazione::getId).collect(Collectors.toSet()).stream().map(String::valueOf).collect(Collectors.toSet());
            view.message("Selezionare una delle segnalazioni presenti nella piattaforma:",viewSet);
            Integer id =Integer.parseInt(view.fetchSingleChoice(viewSet));
            Segnalazione segnalazione= segnalazioni.stream().filter(s->s.getId()==id).findFirst().get();
            view.message("Segnalazione scelta:\nID:"+segnalazione.getId()+"\nID esperienza:"+segnalazione.getEsperienzaId()+"\nId utente:"+segnalazione.getUId()+"\nDescrizione:"+segnalazione.getDescrizione());
            if(view.fetchChoice("Selezionare una delle seguenti alternative:\n1)Accettare la segnalazione e cancellare l'esperienza associata\n2)Rifiutare la segnalazione",2)==1) {
                gestoreSegnalazioni.accettaSegnalazione(segnalazione);
                view.message("Pratica di cancellazione esperienza iniziata");
                ServiceEsperienza serviceEsperienza=ServiceEsperienza.getInstance();
                serviceEsperienza.remove(segnalazione.getEsperienzaId());
            }else{
                gestoreSegnalazioni.rifiutaSegnalazione(segnalazione);
                view.message("Segnalazione rifiutata");
            }
            if(view.fetchChoice("Selezionare una delle seguenti alternative:\n1)Selezionare una nuova segnalazione da gestire\n2)Uscire",2)==2)break;
        }
    }

    private void gestisciRimborsi(){
        view.message("Area in fase di sviluppo.");
    }

    private void impostaMenu() {
        menuItems.add("4) Definisci Nuova Area");
        menuItems.add("5) Definisci Nuovo Tag");
        menuItems.add("6) Gestisci Tag Proposti");
        menuItems.add("7) Gestisci utenti");
        menuItems.add("8) Gestisci segnalazioni");
        menuItems.add("9) Gestisci rimborsi");
    }
}

