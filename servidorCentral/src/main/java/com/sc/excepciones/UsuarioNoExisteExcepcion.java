package com.sc.excepciones;

public class UsuarioNoExisteExcepcion extends Exception {
    public UsuarioNoExisteExcepcion(String msg) {
        super(msg);
    }
}
