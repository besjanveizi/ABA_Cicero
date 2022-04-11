package it.unicam.cs.ids2122.cicero.model.entities.rimborso;

public interface RichiestaRimborso {
    /**
     * Recupera l'id della richiesta di rimborso.
     * @return identificativo della richiesta di rimborso.
     */
    int getId();

    /**
     * Recupera l'id della fattura da rimborsare.
     * @return identificativo della fattura da rimborsare.
     */
    int getIdFattura();

    /**
     * Recupera le motivazioni della richiesta di rimborso.
     * @return stringa contenente il motivo della richiesta di rimborso.
     */
    String getMotivoRichiesta();

    /**
     * Recupera lo stato della richiesta di rimborso.
     * @return stato attuale della richiesta di rimborso.
     */
    RimborsoStatus getState();

    /**
     *
     * @return
     */
    String getInfoEsito();
}
