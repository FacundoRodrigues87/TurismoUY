package com.sc.interfaces;

import java.time.LocalDate;

import com.sc.entidades.salida;
import com.sc.excepciones.ParametrosInvalidosExcepcion;
import com.sc.excepciones.UsuarioNoExisteExcepcion;
import com.sc.excepciones.UsuarioYaExisteExcepcion;

public interface IInscripcionController {
	public abstract void crearInscripcion(LocalDate fecha, int cant, String nombreTursita, String nombreSalida, String nombreAct) throws ParametrosInvalidosExcepcion, UsuarioYaExisteExcepcion, UsuarioNoExisteExcepcion;;
	public abstract int calcularCosto(salida salida, String nombreAct, int cant);
}