package com.sc.excepciones;

public class NoExisteExcepcion extends Exception {

    /**
     * Excepción. :D
     * @param msg String. Mensaje que se va a mostrar en presentación.
     */
    public NoExisteExcepcion(String msg) {
        super(msg);
    }
}
