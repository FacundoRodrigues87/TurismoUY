package com.sc.entidades;

import javax.persistence.*;

import com.sc.datatypes.dataActividad;
import com.sc.datatypes.dataPaquete;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class paquete {
	@Id @Column(name = "nombre_paquete")
	private String nombre;
	@Column
	private int descuento;
	@Column
	private String descripcion;
	private int validez;
	private LocalDate fechaAlta;

    @JoinTable(
		name = "paquete_actividad",
        joinColumns = @JoinColumn(name = "fk_actividad"),
        inverseJoinColumns = @JoinColumn(name = "fk_paquete")
		)
	@ManyToMany()
	private List<actividad> actividades;
	
	public paquete() {};

	public paquete(String nombre, int descuento, String descripcion, int validez, LocalDate fechaAlta) {
		super();
		this.nombre = nombre;
		this.descuento = descuento;
		this.descripcion = descripcion;
		this.validez = validez;
		this.fechaAlta = fechaAlta;
		this.actividades = new ArrayList<actividad>();
	}
	
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getDescuento() {
		return descuento;
	}
	public void setDescuento(int descuento) {
		this.descuento = descuento;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getValidez() {
		return validez;
	}
	public void setValidez(int validez) {
		this.validez = validez;
	}
	public void addActividad(actividad actividad) {
		actividades.add(actividad);
	}
	public int getCantidadActividades() {
		return actividades.size();
	}
	public LocalDate getFechaAlta() {
		return this.fechaAlta;
	}
	public void setFechaAlta(LocalDate fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public List<actividad> getActividades() {
		return this.actividades;
	}
	public dataPaquete toDataType() {
		List<dataActividad> dataActividades = new ArrayList<dataActividad>();
		for (actividad actividad : this.actividades) {
			dataActividades.add(actividad.toDataType());
		}
		dataPaquete dt = new dataPaquete(this.nombre, this.descuento, this.descripcion, this.validez, this.fechaAlta, dataActividades);
		return dt;
	}
	
	public dataPaquete toDataTypeWithoutActividades() {
		dataPaquete dt = new dataPaquete(this.nombre, this.descuento, this.descripcion, this.validez, this.fechaAlta, null);
		return dt;
	}
	
	
}
