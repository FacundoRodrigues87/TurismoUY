package com.sc.entidades;

import javax.persistence.*;
import com.sc.datatypes.dataSalida;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class salida {
    @Id
    @Column
    private String nombreS;

    @Column
    private int capacidad;

    private LocalDate fechaAlta;

    private LocalDate fechaSalida;

    @Column
    private String lugarSalida;

    @ManyToMany(mappedBy = "salidas")
    private List<actividad> actividades;
    
    @OneToMany(mappedBy = "salida")
    private List<inscripcion> inscripciones;


    public salida() {
        // Constructor vacío requerido por JPA
    }

    public salida(String nombre, int capacidad, LocalDate fechaAlta, LocalDate fechaSalida, String lugarSalida) {
        this.nombreS = nombre;
        this.capacidad = capacidad;
        this.fechaAlta = fechaAlta;
        this.fechaSalida = fechaSalida;
        this.lugarSalida = lugarSalida;
        this.actividades = new ArrayList<actividad>();
        this.inscripciones = new ArrayList<inscripcion>();
    }

    // Getters y setters

    public String getNombre() {
        return nombreS;
    }

    public void setNombre(String nombre) {
        this.nombreS = nombre;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public String getLugarSalida() {
        return lugarSalida;
    }

    public void setLugarSalida(String lugarSalida) {
        this.lugarSalida = lugarSalida;
    }

    public List<actividad> getActividades() {
        return actividades;
    }

    public void setActividades(List<actividad> actividades) {
        this.actividades = actividades;
    }
    
    public void addActividad(actividad actividad) {
    	this.actividades.add(actividad);
    }

    public List<inscripcion> getInscripciones() {
        return inscripciones;
    }

    public void addInscripcion(inscripcion inscripcion) {
        this.inscripciones.add(inscripcion);
    }

    public void setInscripciones(List<inscripcion> inscripciones) {
        this.inscripciones = inscripciones;
    }

    public dataSalida toDataType() {
        List<actividad> dataActividades = new ArrayList<actividad>();

        if (this.actividades != null) {
            for (actividad actividad : this.actividades) {
                dataActividades.add(actividad);
            }
        } else { dataActividades = null; }

        dataSalida dt = new dataSalida(this.nombreS, this.capacidad, this.fechaAlta, this.fechaSalida, this.lugarSalida, dataActividades);
        return dt;
    }

    public dataSalida toDataTypeWithoutActividades() {
        dataSalida dt = new dataSalida(this.nombreS, this.capacidad, this.fechaAlta, this.fechaSalida, this.lugarSalida, null);
        return dt;
    }
}
