package com.sc.datatypes;

import java.time.LocalDate;
import java.util.List;

public class dataPaquete {
	private String nombre;
	private int descuento;
	private String descripcion;
	private int validez;
	private LocalDate fechaAlta;
    private List<dataActividad> actividades;

    // String nombre, int descuento, String descripcion, int validez, LocalDate fechaAlta
    public dataPaquete(String nombre, int descuento, String descripcion, int validez, LocalDate fechaAlta, List<dataActividad> actividades) {
        this.nombre = nombre;
        this.descuento = descuento;
        this.descripcion = descripcion;
        this.validez = validez;
        this.fechaAlta = fechaAlta;
        this.actividades = actividades;
    }

    public String getNombre() {
        return this.nombre;
    }
    public int getDescuento() {
        return this.descuento;
    }
    public String getDescripcion() {
        return this.descripcion;
    }
    public int getValidez() {
        return this.validez;
    }
    public LocalDate getFechaAlta() {
        return this.fechaAlta;
    }
    public List<dataActividad> getActividades() {
        return this.actividades;
    }
}
