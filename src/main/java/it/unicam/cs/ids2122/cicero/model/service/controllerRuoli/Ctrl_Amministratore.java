package it.unicam.cs.ids2122.cicero.model.service.controllerRuoli;

import it.unicam.cs.ids2122.cicero.model.tag.GestoreTag;
import it.unicam.cs.ids2122.cicero.model.tag.Tag;
import it.unicam.cs.ids2122.cicero.model.tag.TagStatus;
import it.unicam.cs.ids2122.cicero.model.territorio.Area;
import it.unicam.cs.ids2122.cicero.model.territorio.GestoreAree;
import it.unicam.cs.ids2122.cicero.ruoli.Amministratore;
import it.unicam.cs.ids2122.cicero.view.IView;


import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
            default:
                loop = super.switchMenu(scelta);
        }
        return loop;
    }

    private void gestisciTagProposti() {

        while(true){
            GestoreTag gestoreTag= GestoreTag.getInstance();
            Set<Tag> allTags = gestoreTag.getTags(e -> e.getState().equals(TagStatus.PROPOSTO));
            Set<String> viewSet=allTags.stream().map(Tag::getName).collect(Collectors.toSet());
            if(allTags.isEmpty()){
                view.message("Attualmente non ci sono Tag proposti nella piattaforma.");
                return;
            }
            view.message("Selezionare il tag che si vuole gestire:",viewSet);
            String nomeTag=view.fetchSingleChoice(viewSet);
            Optional<Tag> selectedTag=allTags.stream().filter(t->t.getName().equals(nomeTag)).findFirst();
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
        Set<Tag> allTags = gestoreTag.getTags(e -> e.getState().equals(TagStatus.APPROVATO)||e.getState().equals(TagStatus.PROPOSTO));
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
        view.message("Vuoi confermare la proposta del tag "+nome+" con descrizione "+descrizione+"?");
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

    private void impostaMenu() {
        menuItems.add("4) Definisci Nuova Area");
        menuItems.add("5) Definisci Nuovo Tag");
        menuItems.add("6) Gestisci Tag Proposti");
    }
}
