package com.sc.interfaces;

import java.time.LocalDate;
import java.util.List;

import com.sc.datatypes.dataPaquete;
import com.sc.excepciones.NoExisteExcepcion;
import com.sc.excepciones.ParametrosInvalidosExcepcion;
import com.sc.excepciones.UsuarioNoExisteExcepcion;
import com.sc.excepciones.UsuarioYaExisteExcepcion;
import com.sc.excepciones.YaExisteExcepcion;

public interface IPaqueteController {
    public abstract void crearPaquete(String nombre, String descripcion, int periodoVal, int descuento, LocalDate fechaAlta) throws ParametrosInvalidosExcepcion, UsuarioYaExisteExcepcion;
    public abstract dataPaquete mostrarInfo(String nombre)throws UsuarioNoExisteExcepcion;
    public abstract List<dataPaquete> listarPaquetes();
    public abstract void agregarActividadPaquete(String nombrePaquete, String nombreActividad)throws NoExisteExcepcion, YaExisteExcepcion;
    public abstract List<String> obtenerNombresPaquetes();
}
