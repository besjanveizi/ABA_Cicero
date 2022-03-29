package it.unicam.cs.ids2122.cicero.model.prenotazione_v2.gestori;




import it.unicam.cs.ids2122.cicero.model.prenotazione_v2.persistenza.DBManager;
import it.unicam.cs.ids2122.cicero.model.tag.Tag;
import it.unicam.cs.ids2122.cicero.model.territorio.Toponimo;

import java.util.List;

public class GestoreTT extends AbstractGestore {

    List<Tag> tag_salvati;
    List<Toponimo> toponimi_salvati;

    String sql_select = "SELECT * FROM public.tags ;" ;
    String sql_toponimi = "SELECT * FROM public.toponimi ;";

    public GestoreTT(DBManager dbManager) {
        super(dbManager);

    }

    public void carica_tag_approvati(){

    }

    public void carica_toponimo_approvati(){

    }

    public void carica_toponimo(){

    }

    public void carica_tag(){

    }

    @Override
    public void show() {

    }
}
