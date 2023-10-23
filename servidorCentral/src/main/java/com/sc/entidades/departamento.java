package com.sc.entidades;

import javax.persistence.*;
import com.sc.datatypes.dataDepartamento;
import java.util.ArrayList;
import java.util.List;

@Entity
public class departamento {
	@Id @Column
	private String nombre;
	private String descripcion;
	private String url;
	@OneToMany(mappedBy = "departamento")
	private List<actividad> actividades;

	// Constructor vacio pedido por JPA.
	public departamento() {};

	public departamento(String nombre, String descripcion, String url) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.url = url;
		this.actividades = new ArrayList<actividad>();
	}

	public String getNombre() {
		return nombre;
	}
	
	public String getDescripcion(){
		return this.descripcion;
	}

	public String getUrl(){
		return this.url;
	}

	public List<actividad> getActividades() {
		return this.actividades;
	};

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void agregarActividad(actividad actividad){
		this.actividades.add(actividad);

	};

	public void removerActividad(actividad actividad){

	};

	public boolean existeActividad(String nombre){
		return false;
	};

	public int cantidadActividades() {
		return 0;
	}
	public dataDepartamento toDataType() {
		List<String> dataActividades = new ArrayList<String>();

		if (this.actividades != null) {
			for (actividad actividad : this.actividades) {
				dataActividades.add(actividad.getNombre());
			}
		} else { dataActividades = null;}

		dataDepartamento dt = new dataDepartamento(this.nombre, this.descripcion, this.url, dataActividades);
		return dt;
	}

	public dataDepartamento toDataTypeWithoutActividades() {
		dataDepartamento dt = new dataDepartamento(this.nombre, this.descripcion, this.url, null);
		return dt;
	}
}
