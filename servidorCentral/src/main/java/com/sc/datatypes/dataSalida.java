package com.sc.datatypes;

import java.time.LocalDate;
import java.util.List;

import com.sc.entidades.actividad;

public class dataSalida {
    private String nombre;
    private int capacidad;
    private LocalDate fechaAlta;
    private LocalDate fechaSalida;
    private String lugarSalida;
    private List<actividad> actividades;

    public dataSalida(String nombre, int capacidad, LocalDate fechaAlta, LocalDate fechaSalida, String lugarSalida, List<actividad> actividades ) {
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.fechaAlta = fechaAlta;
        this.fechaSalida = fechaSalida;
        this.lugarSalida = lugarSalida;
        this.actividades = actividades;
    }

    public String getNombre() {
        return this.nombre;
    }

    public int getCapacidad() {
        return this.capacidad;
    }

    public LocalDate getFechaAlta() {
        return this.fechaAlta;
    }

    public LocalDate getFechaSalida() {
        return this.fechaSalida;
    }

    public String getLugarSalida() {
        return this.lugarSalida;
    }

	public List<actividad> getActividades() {
		return actividades;
	}
}
