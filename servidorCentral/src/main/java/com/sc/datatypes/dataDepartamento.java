package com.sc.datatypes;

import java.util.List;

public class dataDepartamento {
    private String nombre;
    private String descripcion;
    private String url;
    private List<String> actividades;

    public dataDepartamento(String nombre, String descripcion, String url, List<String> actividades) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.url = url;
        this.actividades = actividades;
    }

    public String getNombre() {
        return this.nombre;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public String getUrl() {
        return this.url;
    }

    public List<String> getActividades(){
        return this.actividades;
    };
}
