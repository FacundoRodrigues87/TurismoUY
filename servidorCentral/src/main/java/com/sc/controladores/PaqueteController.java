package com.sc.controladores;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.sc.datatypes.dataActividad;
import com.sc.datatypes.dataPaquete;
import com.sc.entidades.actividad;
import com.sc.entidades.paquete;
import com.sc.excepciones.YaExisteExcepcion;
import com.sc.excepciones.NoExisteExcepcion;
import com.sc.excepciones.ParametrosInvalidosExcepcion;
import com.sc.excepciones.UsuarioNoExisteExcepcion;
import com.sc.excepciones.UsuarioYaExisteExcepcion;
import com.sc.interfaces.IPaqueteController;
import com.sc.manejadores.ActividadManejador;
import com.sc.manejadores.PaqueteManejador;
import com.sc.utilidades.log;

// import com.sc.utilidades.validaciones;

public class PaqueteController implements IPaqueteController {
    
    public void crearPaquete(String nombre, String descripcion, int periodoVal, int descuento, LocalDate fechaAlta) throws ParametrosInvalidosExcepcion, UsuarioYaExisteExcepcion{
        // Code

        if(!validarTexto(nombre, 1) ||
            !validarTexto(descripcion, 1))
            {
            log.error("[PaqueteController] Error con los parametros String");
        	throw new ParametrosInvalidosExcepcion();
            // TODO: ⚠️ Aplicar excepciones.
        }
        PaqueteManejador pm = PaqueteManejador.getinstance();
        paquete paquete = pm.getPaquete(nombre);

        if (paquete != null) {
            log.error("El paquete: " + nombre + " ya existe.");
        	throw new UsuarioYaExisteExcepcion("El paquete: " + nombre + " ya existe.");
            
        }
        LocalDate fechaActual = LocalDate.now();

        // log.warning("FechaActual: " + fechaActual.getYear() + "/" + fechaActual.getMonthValue() + "/" + fechaActual.getDayOfMonth());
        paquete = new paquete(nombre, descuento, descripcion, periodoVal, LocalDate.of(fechaActual.getYear(), fechaActual.getMonthValue(), fechaActual.getDayOfMonth()));
        pm.addPaquete(paquete);
    };

    public List<String> obtenerNombresPaquetes() {
        PaqueteManejador pm = PaqueteManejador.getinstance();
        List<paquete> paquetes = pm.getAllPaquetes();
        
        if (paquetes == null) {
            return new ArrayList<>(); // Devuelve una lista vacía si no hay paquetes.
        }
    
        List<String> nombresPaquetes = new ArrayList<>();
    
        for (paquete paquete : paquetes) {
            nombresPaquetes.add(paquete.getNombre());
        }
    
        return nombresPaquetes;
    }
    

    public void agregarActividadPaquete(String nombrePaquete, String nombreActividad) throws NoExisteExcepcion, YaExisteExcepcion {
        PaqueteManejador pm = PaqueteManejador.getinstance();

        paquete paquete = pm.getPaquete(nombrePaquete);

        if (paquete == null) {
            log.error("El paquete " + nombrePaquete + " no existe");
        	throw new NoExisteExcepcion("El paquete: " + nombrePaquete + " NO existe.");
        }

        ActividadManejador am = ActividadManejador.getinstance();

        actividad actividad = am.getActividad(nombreActividad);

        if (actividad == null) {
            log.error("La actividad " + nombreActividad + " no existe.");
            throw new NoExisteExcepcion("La actividad: " + nombreActividad + " NO existe.");
        }
        
        boolean yaExiste = false;
        for (actividad actividadAux : paquete.getActividades()) {
            log.warning(actividadAux.getNombre());
            if (actividadAux.getNombre().equals(nombreActividad)) {
                yaExiste = true;
                break;
            };
        }

        if (yaExiste) {
            throw new YaExisteExcepcion("La actividad ya existe dentro de paquete");
        }

        paquete.addActividad(actividad);

        // Actualizo
        am.updateActividad(actividad);
        pm.updatePaquete(paquete);
    }


    public dataPaquete mostrarInfo(String nombre) throws UsuarioNoExisteExcepcion {
        PaqueteManejador pm = PaqueteManejador.getinstance();

        paquete paquete = pm.getPaquete(nombre);

        if (paquete == null) {
            log.error("[PaqueteController] El paquete " + nombre + " no existe.");
            throw new UsuarioNoExisteExcepcion("El paquete: " + nombre + " ya existe.");
        }
        dataPaquete dp = paquete.toDataType();
        return dp;
    };

    public List<dataPaquete> listarPaquetes() {
        PaqueteManejador pm = PaqueteManejador.getinstance();

        List<paquete> paquetes = pm.getAllPaquetes();
        List<dataPaquete> dataPaquetes = new ArrayList<dataPaquete>(); 

        if (paquetes == null) {
            return null;
        }

        for (paquete paquete : paquetes) {
            dataPaquete dtPaquete = paquete.toDataType();
            dataPaquetes.add(dtPaquete);
        };
        return dataPaquetes;
    }

    /*
        * Level 1: Textos simples. Valida que no sea vacio, que no empiece o termine con espacio y que al menos tenga 1 letra o número.
        * Level 2: Correos. Valida lo mismo que el nivel 1, además valida que contenga una sola @ para los correos.
        * Livel 3: Nicknames. Valida lo mismo que el nivel 1, además valida que no contenga espacios. Sirve para nombres de usuario.
    */
    private static boolean validarTexto(String texto, int nivel) {
        switch (nivel) {
            case 1:
                return validarNivel1(texto);
            case 2:
                return validarNivel1(texto) && contarCaracter(texto, '@') == 1;
            case 3:
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

    private static int contarCaracter(String texto, char caracter) {
        int count = 0;
        for (char c : texto.toCharArray()) {
            if (c == caracter) {
                count++;
            }
        }
        return count;
    }

}