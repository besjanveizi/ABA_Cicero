package it.unicam.cs.ids2122.cicero.model.territorio;

/**
 * Semplice implementazione di un'<code>Area</code>.
 */
public class SimpleArea implements Area {
    private String toponimo;
    private String descrizione;

    /**
     * Crea un area impostando il toponimo e una descrizione.
     * @param toponimo toponimo dell'area.
     * @param descrizione descrizione dell'area.
     */
    public SimpleArea(String toponimo, String descrizione) {
        this.toponimo = toponimo;
        this.descrizione = descrizione;
    }

    @Override
    public String getToponimo() {
        return this.toponimo;
    }
}
