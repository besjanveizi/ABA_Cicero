package model.esperienza;

import controller.InfoEsperienza;

/**
 * Rappresenta un director per un'<code>Esperienza</code>.
 */
public class DirectorEsperienza {

    private final InfoEsperienza infoE;

    /**
     * Crea un director a cui vengono forniti le informazioni dell'<code>Esperienza</code> che deve essere creata.
     * @param infoE informazioni della nuova <code>Esperienza</code>.
     */
    public DirectorEsperienza(InfoEsperienza infoE) {
        this.infoE = infoE;
    }

    /**
     * Crea una semplice <code>Esperienza</code> passo per passo, istruendo il builder dato.
     * @param builder entità che si occupa di mettere in atto i vari passi istruiti dal director.
     */
    public void creaSimpleEsperienza(BuilderEsperienza builder) {
        builder.setNome(infoE.getNomeE());
        builder.setInizio(infoE.get_dI());
        builder.setFine(infoE.get_dF());
        builder.setMin(infoE.getMinP());
        builder.setMax(infoE.getMaxP());
        builder.setGiorniRiserva(infoE.getMaxRiserva());
    }
}
