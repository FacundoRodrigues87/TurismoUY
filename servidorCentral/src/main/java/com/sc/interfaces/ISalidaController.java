package com.sc.interfaces;

import java.time.LocalDate;
import java.util.List;

import com.sc.datatypes.dataSalida;
import com.sc.entidades.salida;
import com.sc.excepciones.ParametrosInvalidosExcepcion;
import com.sc.excepciones.UsuarioNoExisteExcepcion;
import com.sc.excepciones.UsuarioYaExisteExcepcion;

public interface ISalidaController {
    void crearSalida(String nombre, int capacidad, LocalDate fechaAlta, LocalDate fechaSalida, String lugarSalida, String nombreActividad) throws UsuarioYaExisteExcepcion, ParametrosInvalidosExcepcion, UsuarioNoExisteExcepcion;
    List<dataSalida> getAllSalidas();
    dataSalida mostrarDatosSalida(String nombreSalida);
    List<String> obtenerNombresActividadesAsociadas(salida salida);
    List<dataSalida> obtenerSalidasVigentesPorActividad(String nombreActividad);
    void eliminarSalida(String salida);
    
}
