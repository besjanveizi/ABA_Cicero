package it.unicam.cs.ids2122.cicero.model.prenotazione_v2.utenti;

public enum UtenteType {

   TURISTA(2),
    CICERONE(1),
    ADMIN(0);

    int i;
    UtenteType(int i) {
        this.i = i;
    }

    public int getI() {
        return i;
    }
}
