package it.unicam.cs.ids2122.cicero.model.prenotazione_V3.sistema;

import java.sql.SQLException;

public interface Service<T>  {

   T menu() throws SQLException;

}