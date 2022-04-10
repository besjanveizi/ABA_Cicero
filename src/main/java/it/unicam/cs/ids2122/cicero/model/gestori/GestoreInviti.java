package it.unicam.cs.ids2122.cicero.model.gestori;


import it.unicam.cs.ids2122.cicero.model.entities.esperienza.IEsperienza;
import it.unicam.cs.ids2122.cicero.model.prenotazione.bean.BeanInvito;
import it.unicam.cs.ids2122.cicero.model.services.ServiceDisponibilita;
import it.unicam.cs.ids2122.cicero.model.services.ServiceInvito;
import it.unicam.cs.ids2122.cicero.ruoli.Turista;

import java.util.Set;

public final class GestoreInviti {

    private static GestoreInviti sinGestorePagamento= null;

    private Turista utente_corrente;

   private Set<BeanInvito> ricevuti;

    private GestoreInviti(Turista corrente){
        this.utente_corrente = corrente;
            carica();
    }

    private void carica() {
        ricevuti = ServiceInvito.getInstance().select(utente_corrente.getEmail());
    }

    public  static GestoreInviti getInstance(Turista iUtente)  {
        if(sinGestorePagamento == null){
           sinGestorePagamento = new GestoreInviti(iUtente);
        }return sinGestorePagamento;
    }

    /**
     * crea un invito che verrà inserito nel db e modifica i posti disponibili dell' esperienza.
     * @param esperienza l' esperienza selezionata
     * @param mail_invitato mail del destinatario
     */
    public void crea_invito(IEsperienza esperienza, String mail_invitato, int posti_riservati){
      BeanInvito beanInvito = new BeanInvito(esperienza.info().getDataInizio(),esperienza.info().getMaxGiorniRiserva());
      beanInvito.setPosti_riservati(posti_riservati);
      beanInvito.setEmail_destinatario(mail_invitato);
      beanInvito.setId_esperienza(esperienza.getId());
      beanInvito.setImporto(esperienza.info().getCostoIndividuale().op_multi(String.valueOf(posti_riservati)));
      beanInvito.setValuta(esperienza.info().getCostoIndividuale().getValuta().toString());
      ServiceInvito.getInstance().insert(beanInvito);

      esperienza.info().cambiaPostiDisponibili(-posti_riservati);
      ServiceDisponibilita.getInstance().update(esperienza.info().getPostiDisponibili(),esperienza.getId());

    }

    public void rifiuta_invito(BeanInvito beanInvito) {
        ServiceInvito.getInstance().delete(beanInvito.getId_invito());
    }


    public Set<BeanInvito> getRicevuti() {
        return ricevuti;
    }
}
