package com.sc.interfaces;

import java.time.LocalDate;
import java.util.List;
import com.sc.datatypes.dataUsuario;
import com.sc.excepciones.ParametrosInvalidosExcepcion;
import com.sc.excepciones.UsuarioNoExisteExcepcion;
import com.sc.excepciones.UsuarioYaExisteExcepcion;

public interface IUsuarioController {

     /**
     * Caso de uso:
     * @implNote Registro :D
     * @param nickname String (unico)
     * @param nombre String
     * @param apellido String
     * @param email String (unico)
     * @param nacionalidad String
     * @param nacimiento LocalDate
     */
    void registrarTurista(String nickname, String nombre, String apellido, String email, String nacionalidad, LocalDate nacimiento) throws ParametrosInvalidosExcepcion, UsuarioYaExisteExcepcion;

    /**
     * Caso de uso:
     * @param nickname String
     * @return dataUsuario (DataType)
     * @throws UsuarioNoExisteExcepcion
     */
    dataUsuario mostrarInfo(String nickname) throws UsuarioNoExisteExcepcion;

    /**
     * Caso de uso:
     * @implNote Registro :D
     * @param nickname String (unico)
     * @param nombre String
     * @param apellido String
     * @param email String (unico)
     * @param descripcion String
     * @param url String
     * @param nacimiento LocalDate
     */
    void registrarProveedor(String nickname, String nombre, String apellido, String email, String descripcion, String url, LocalDate nacimiento) throws ParametrosInvalidosExcepcion, UsuarioYaExisteExcepcion;

    /**
     * Caso de uso:
     * @param nickname String (unico)
     * @param nombre String
     * @param apellido String
     * @param nacimiento LocalDate
     * @throws ParametrosInvalidosExcepcion
     */
    void modificarUsuario(String nickname, String nombre, String apellido, LocalDate nacimiento) throws ParametrosInvalidosExcepcion;
    
    /**
     * Lista todos los usuarios.
     * @return List de dataUsuario
     */
    List<dataUsuario> listarUsuarios();

    List<dataUsuario> listarProveedores();
}
