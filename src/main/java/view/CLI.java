package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * emulazione riga di comando
 */
public class CLI {

    /**
     * input
     *
     * @return null se c'è un errore, l' input altrimenti
     */
    public String cmd(){
        String s = null;
        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
        try {
           s = bufferReader.readLine();
           return s;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * visualizza una stringa
     *
     * @param msg il testo
     */
    public void message(String msg){
        System.out.println(msg);
    }


}
