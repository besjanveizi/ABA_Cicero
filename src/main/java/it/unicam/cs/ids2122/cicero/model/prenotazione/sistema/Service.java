package it.unicam.cs.ids2122.cicero.model.prenotazione.sistema;

import java.sql.SQLException;

public interface Service<T>  {

   T menu() throws SQLException;

}
