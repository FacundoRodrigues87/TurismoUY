package com.sc.interfaces;

import java.time.LocalDate;
import java.util.List;
import com.sc.datatypes.dataActividad;
import com.sc.excepciones.ParametrosInvalidosExcepcion;
import com.sc.excepciones.UsuarioNoExisteExcepcion;
import com.sc.excepciones.UsuarioYaExisteExcepcion;

public interface IActividadController {
    /**
     * Caso de uso:
     * @param nombreDepto String
     * @param nombreProv String
     * @param nombre String
     * @param desc String
     * @param duracion int
     * @param costoUni int
     * @param ciudad String
     * @param fechaCrea LocalDate
     * @throws ParametrosInvalidosExcepcion
     * @throws UsuarioYaExisteExcepcion
     * @throws UsuarioNoExisteExcepcion
     */
    public abstract void crearActividad(String nombreDepto, String nombreProv, String nombre, String desc, int duracion, int costoUni, String ciudad, LocalDate fechaCrea) throws ParametrosInvalidosExcepcion, UsuarioYaExisteExcepcion, UsuarioNoExisteExcepcion;
    
    /**
     * Caso de uso:
     * @param nombreAct String
     * @return dataActividad
     * @throws UsuarioNoExisteExcepcion
     */
    public abstract dataActividad mostrarDatos(String nombreAct) throws UsuarioNoExisteExcepcion;
    /**
     * Caso de uso:
     * @param nombre String
     * @param desc String
     * @param duracion int
     * @param costoUni int
     * @param ciudad String
     * @param fechaCrea LocalDate
     * @throws ParametrosInvalidosExcepcion
     * @throws UsuarioYaExisteExcepcion
     * @throws UsuarioNoExisteExcepcion
     */
    public abstract void modificarActividad(String nombre, String desc, int duracion, int costoUni, String ciudad, LocalDate fechaCrea) throws ParametrosInvalidosExcepcion, UsuarioYaExisteExcepcion, UsuarioNoExisteExcepcion;
    
    /**
     * Lista todas las actividades.
     * @return Lista de dataActividad
     */
    public abstract List<dataActividad> getAllActividades();

    /**
     * Lista todas las actividades de un Departamento
     * @param nombreDep String
     * @return Lista de dataActividad
     */
    public abstract List<dataActividad> getAllActividadesDepartamento(String nombreDep);

    public abstract List<dataActividad> getAllActividadesDepartamentoSinPaquete(String nombreDep, String nombrePaquete);
    public abstract List<String> getNombresSalidasAsociadas(String nombreActividad);
    public abstract List<dataActividad> getActividadesNoPaquete(String nombreDepartamento, String nombrePaquete) throws UsuarioNoExisteExcepcion;
}
