package com.sc.excepciones;

public class UsuarioYaExisteExcepcion extends Exception{
    public UsuarioYaExisteExcepcion(String msg) {
        super(msg);
    };
}
