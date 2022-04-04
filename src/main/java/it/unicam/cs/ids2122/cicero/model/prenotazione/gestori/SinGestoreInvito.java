package it.unicam.cs.ids2122.cicero.model.prenotazione.gestori;

import it.unicam.cs.ids2122.cicero.model.esperienza.IEsperienza;
import it.unicam.cs.ids2122.cicero.model.prenotazione.bean.BeanInvito;
import it.unicam.cs.ids2122.cicero.model.prenotazione.bean.ServiceInvito;
import it.unicam.cs.ids2122.cicero.ruoli.IUtente;
import java.util.Set;

public final class SinGestoreInvito {

    private static SinGestoreInvito sinGestorePagamento= null;

    private IUtente utente_corrente;

   private Set<BeanInvito> ricevuti;

    private SinGestoreInvito(IUtente  corrente){
        this.utente_corrente = corrente;
            carica();
    }

    private void carica() {
        ricevuti = ServiceInvito.getInstance().select(utente_corrente.getEmail());
    }

    public static SinGestoreInvito getInstance(IUtente iUtente)  {
        if(sinGestorePagamento == null){
           sinGestorePagamento = new  SinGestoreInvito(iUtente);
        }return sinGestorePagamento;
    }

    /**
     * crea un invito che verrà inserito nel db
     * @param esperienza l' esperienza selezionata
     * @param mail_invitato mail del destinatario
     */
    public void crea_invito(IEsperienza esperienza, String mail_invitato, int posti_riservati){
      BeanInvito beanInvito = new BeanInvito(esperienza.info().getDataInizio(),esperienza.info().getMaxGiorniRiserva());
      beanInvito.setPosti_riservati(posti_riservati);
      beanInvito.setEmail_destinatario(mail_invitato);
      beanInvito.setId_esperienza(esperienza.getId());
      beanInvito.setImporto(esperienza.info().getCostoIndividuale().getValore());
      beanInvito.setValuta(esperienza.info().getCostoIndividuale().getValuta().toString());
      ServiceInvito.getInstance().insert(beanInvito);
    }


    public Set<BeanInvito> getRicevuti() {
        return ricevuti;
    }
}
