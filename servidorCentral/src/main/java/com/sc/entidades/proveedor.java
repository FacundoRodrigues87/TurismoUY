package com.sc.entidades;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import com.sc.datatypes.dataActividad;
import com.sc.datatypes.dataSalida;
import com.sc.datatypes.dataUsuario;

@Entity
@DiscriminatorValue("P")
public class proveedor extends usuario {
	@Column
	private String url;
	@Column
	private String descripcion;

	@OneToMany(mappedBy = "proveedor", targetEntity = actividad.class)
	private List<actividad> actividades;
	
	public proveedor(String nickname, String nombre, String apellido, String email, String descripcion, String url, LocalDate nacimiento) {
		super(nickname, nombre, apellido, email, nacimiento);
		this.url = url;
		this.descripcion = descripcion;
		this.actividades = new ArrayList<actividad>();
	}

	// Constructor vacio pedido por JPA.
	public proveedor(){};


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void addActividad(actividad actividad) {
		this.actividades.add(actividad);
	}


	public dataUsuario toDataType() {
		List<dataSalida> dataSalidas = new ArrayList<dataSalida>();

		if (this.actividades != null) {
			for (actividad actividad : this.actividades) {
				if (actividad.getSalidas() != null) {
					// log.warning("es diferente de null y " + actividad.getSalidas().size());
					for (salida salida : actividad.getSalidas()) {
						// log.warning("Coso " + salida.getNombre());
						dataSalidas.add(salida.toDataTypeWithoutActividades());
					}
				}
			}
		}

		List<dataActividad> dataActividades = new ArrayList<dataActividad>();

		if (this.actividades != null) {
			for (actividad actividad : this.actividades) {
				dataActividades.add(actividad.toDataType());
			}
		} else {
			dataActividades = null;
		}

		dataUsuario dt = new dataUsuario(	this.nickname,
											this.nombre,
											this.apellido,
											this.email,
											null,
											this.nacimiento,
											true,
											this.descripcion,
											this.url,
											dataActividades,
											dataSalidas);
		return dt;
	}

	public dataUsuario toDataTypeWithoutCollections() {
		dataUsuario dt = new dataUsuario(	this.nickname,
											this.nombre,
											this.apellido,
											this.email,
											null,
											this.nacimiento,
											true,
											this.descripcion,
											this.url,
											null,
											null);
		return dt;
	}
}
