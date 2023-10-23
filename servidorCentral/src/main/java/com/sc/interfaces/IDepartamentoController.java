package com.sc.interfaces;

import java.util.List;

import com.sc.datatypes.dataDepartamento;
import com.sc.excepciones.ParametrosInvalidosExcepcion;
import com.sc.excepciones.UsuarioYaExisteExcepcion;

public interface IDepartamentoController {
    public abstract void crearDepartamento(String nombre, String descripcion, String url)throws ParametrosInvalidosExcepcion, UsuarioYaExisteExcepcion;
    public abstract List<dataDepartamento> listarDepartamentos();
}
