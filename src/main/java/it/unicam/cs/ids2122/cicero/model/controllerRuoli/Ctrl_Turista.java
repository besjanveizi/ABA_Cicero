package it.unicam.cs.ids2122.cicero.model.controllerRuoli;

import it.unicam.cs.ids2122.cicero.model.prenotazione_V3.sistema.*;
import it.unicam.cs.ids2122.cicero.ruoli.Turista;
import it.unicam.cs.ids2122.cicero.view.IView;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class Ctrl_Turista extends Ctrl_UtenteAutenticato implements Ctrl_Utente {

    private Map<String, Service> mapServizi;

    public Ctrl_Turista(IView<String> view, Turista turista) {
        super(view, turista);
        //carico i servizi
        impostaMenu();
        mapServizi = new LinkedHashMap<>();
    }

    @Override
    protected boolean switchMenu(int scelta) {
        boolean loop = true;
        switch (scelta) {
            case 4:
                prenotaEsperienza();
                break;
            case 5:
                pagaPrenotazione();
                break;
            case 6:
                invitaEsperienza();
                break;
            case 7:
                gestisciInvitiRicevuti();
                break;
            default:
                loop = super.switchMenu(scelta);
        }
        return loop;
    }

    private void gestisciInvitiRicevuti() {
        try {
            mapServizi.get("inviti").menu();
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void invitaEsperienza() {
        try {
            mapServizi.get("inviti").menu();
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void prenotaEsperienza() {
        try {
            mapServizi.get("prenotazione").menu();
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void pagaPrenotazione() {
        try {
            mapServizi.get("pagamento").menu();
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
    }



    private void init_service_model(){
        try {
            ServizioPrenotazione servizioPrenotazione = new ServizioPrenotazione(super.view,super.utente,mapServizi);
            mapServizi.put("prenotazione",servizioPrenotazione);
            ServizioPagamento servizioPagamento = new ServizioPagamento(super.view,super.utente,mapServizi);
            mapServizi.put("pagamento", servizioPagamento);
            ServizioInvito servizioInvito = new ServizioInvito(super.view,super.utente,mapServizi);
            mapServizi.put("inviti", servizioInvito);
            ServizioRimborso servizioRimborso = new ServizioRimborso(super.view,super.utente,mapServizi);
            mapServizi.put("rimborso", servizioRimborso);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void impostaMenu() {
        menuItems.add("4) Prenota Esperienza");
        menuItems.add("5) Paga Prenotazione");
        menuItems.add("6) Invita ad una Esperienza");
        menuItems.add("7) Gestisci Inviti");
    }
}
