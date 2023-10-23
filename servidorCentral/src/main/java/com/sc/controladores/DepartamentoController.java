package com.sc.controladores;

import java.util.ArrayList;
import java.util.List;
import com.sc.datatypes.dataDepartamento;
import com.sc.entidades.departamento;
import com.sc.excepciones.ParametrosInvalidosExcepcion;
import com.sc.excepciones.UsuarioYaExisteExcepcion;
import com.sc.interfaces.IDepartamentoController;
import com.sc.manejadores.DepartamentoManejador;
import com.sc.utilidades.log;
import com.sc.entidades.actividad;

public class DepartamentoController implements IDepartamentoController {

    public void crearDepartamento(String nombre, String descripcion, String url) throws ParametrosInvalidosExcepcion, UsuarioYaExisteExcepcion{
        // Validaciones sobre parametros.
        if (!validarTexto(nombre, 1) ||
            !validarTexto(descripcion, 1) ||
            !validarTexto(url, 1))
            {
            log.error("[crearDepartamento] Parametros invalidos." + nombre);
            throw new ParametrosInvalidosExcepcion();
        }        
        DepartamentoManejador dm = DepartamentoManejador.getinstance();
        departamento d = dm.getDepartamento(nombre);

        if (d != null) {
            // TODO: Aplicar verificación con minusculas.
            log.error("El departamento '" + nombre + "' existe");
            throw new UsuarioYaExisteExcepcion("El departamento " + nombre + " ya existe");
        }

        departamento depto = new departamento(nombre, descripcion, url);
        dm.addDepartamento(depto);
    };

    public List<dataDepartamento> listarDepartamentos(){
        DepartamentoManejador dm = DepartamentoManejador.getinstance();
        List<departamento> deptos = dm.getAllDepartamento();

        if (deptos == null) {
            return null;
        };

        List<dataDepartamento> listaDT = new ArrayList<>();

        for (departamento dep : deptos) {
            List<String> actividades = new ArrayList<String>();
            for (actividad act : dep.getActividades()) {
                actividades.add(act.getNombre());
            }
            listaDT.add(dep.toDataType());
        }
    
        return listaDT;
    };

    /*
        * Level 1: Textos simples. Valida que no sea vacio, que no empiece o termine con espacio y que al menos tenga 1 letra o número.
        * Livel 2: Nicknames. Valida lo mismo que el nivel 1, además valida que no contenga espacios. Sirve para nombres de usuario.
    */
    private static boolean validarTexto(String texto, int nivel) {
        switch (nivel) {
            case 1:
                return validarNivel1(texto);
            case 2:
                return validarNivel1(texto) && !texto.contains(" ");
            default:
                return false;
        }
    }

    private static boolean validarNivel1(String texto) {
        if (texto == null || texto.isEmpty()) {
            return false;
        }
        if (texto.trim().length() != texto.length()) {
            return false;
        }
        return texto.matches(".*[a-zA-Z0-9].*");
    }
}
