package it.unicam.cs.ids2122.cicero.model.service.dbservices;

public enum DBTable {
    ESPERIENZE("esperienze"),
    UTENTI_REGISTRATI("utenti_registrati"),
    TAGS("tags"),
    AREE("aree");

    private String tableName;
    DBTable(String tableName) {
        this.tableName = tableName;
    }
}
