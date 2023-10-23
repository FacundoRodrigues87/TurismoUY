package com.sc.controladores;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.sc.datatypes.dataSalida;
import com.sc.entidades.actividad;
import com.sc.entidades.departamento;
import com.sc.entidades.salida;
import com.sc.excepciones.ParametrosInvalidosExcepcion;
import com.sc.excepciones.UsuarioNoExisteExcepcion;
import com.sc.excepciones.UsuarioYaExisteExcepcion;
import com.sc.interfaces.ISalidaController;
import com.sc.manejadores.ActividadManejador;
import com.sc.manejadores.DepartamentoManejador;
import com.sc.manejadores.SalidaManejador;
import com.sc.utilidades.log;

public class SalidaController implements ISalidaController {

	public  void crearSalida(String nombre, int capacidad, LocalDate fechaAlta, LocalDate fechaSalida, String lugarSalida, String nombreActividad) throws UsuarioYaExisteExcepcion, ParametrosInvalidosExcepcion, UsuarioNoExisteExcepcion {
		// Validaciones sobre parámetros.
	    if (!validarTexto(nombre, 1) ||
	        capacidad <= 0 ||
	        fechaAlta == null ||
	        fechaSalida == null ||
	        !validarTexto(lugarSalida, 1) ||
	        nombreActividad == null || nombreActividad.isEmpty()) { // Verificar que haya un nombre de actividad
	        log.error("[crearSalida] Parámetros inválidos: " + nombre);
	        throw new ParametrosInvalidosExcepcion();
	    }

	    SalidaManejador sm = SalidaManejador.getInstance();
	    salida s = sm.getSalida(nombre);
	    
	    if (s != null) {
	        log.error("La salida '" + nombre + "' existe");
			throw new UsuarioYaExisteExcepcion("La salida '" + s.getNombre() + "' ya existe");
	    
	    }

	    // Obtener la entidad de actividad correspondiente al nombre
	    ActividadManejador am = ActividadManejador.getinstance();
	    actividad a = am.getActividad(nombreActividad);

	    if (a == null) {
	        log.error("La actividad '" + nombreActividad + "' no existe");
	        throw new UsuarioNoExisteExcepcion("La salida '" + nombreActividad + "' ya existe");
	        
	    }

	    // Utilizar el constructor adecuado de salida
	    salida nuevaSalida = new salida(nombre, capacidad, fechaAlta, fechaSalida, lugarSalida);
	    
	    nuevaSalida.addActividad(a);
	    a.addSalida(nuevaSalida);
	    sm.addSalida(nuevaSalida);
	    am.updateActividad(a);
	    
	    DepartamentoManejador dm = DepartamentoManejador.getinstance();	 
	    List<departamento> LDepartamentos = dm.getAllDepartamento();
	    for(departamento depto : LDepartamentos){
	    	List<actividad> LActividades = depto.getActividades();
	    	for(actividad act : LActividades) {
	    		if(act.getNombre().equals(a.getNombre())) {
	    			act.setSalidas(a.getSalidas());
	    		    System.out.println("La actividad " + act.getNombre() + " si se remplazo");
	    			return;
	    		}
	    	}
	    }
	    System.out.println("La actividad " + a.getNombre() + " no se remplazo");
	}
	
	public List<dataSalida> getAllSalidas(){
        List<dataSalida> dataSalidas = new ArrayList<dataSalida>();
		
		SalidaManejador sm = SalidaManejador.getInstance();
		
		List<salida> salidas = sm.getAllSalidas();
		
		if (salidas == null) {
				return null;
			}
			
		for (salida salida : salidas) {
				dataSalidas.add(salida.toDataTypeWithoutActividades());
			}
			
		return dataSalidas;
		// List<dataSalida> LDtSalida = new ArrayList<dataSalida>();
        // DepartamentoManejador dm = DepartamentoManejador.getinstance();
        // List <departamento> LDepto = dm.getAllDepartamento();
        // //Recorro todos los departamentos para poder buscar la actividad que
        // //me pasan por parametro
        // if (LDepto == null) {
        //     return null;
        // }
        // for(departamento depto: LDepto){
        //     // log.warning(depto.getNombre());
        //     List<actividad> LAct = depto.getActividades();
        //     if(LAct == null) {
        //         return null;
        //     }
        //     // log.warning("Cantidad de actividades: " + LAct.size());
        //     for(actividad act : LAct) {
        //         List<salida>LSalida = act.getSalidas();
        //         if(LSalida == null) {
        //             return null;
        //         }
        //         for(salida salida: LSalida){
        //             dataSalida DtSalida = new dataSalida(salida.getNombre(),
        //                     salida.getCapacidad(),
        //                     salida.getFechaAlta(),
        //                     salida.getFechaSalida(),
        //                     salida.getLugarSalida(),
        //                     salida.getActividades());
        //             LDtSalida.add(DtSalida);
        //         }
        //     }
        // }
        // return LDtSalida;
    }
    
    public dataSalida mostrarDatosSalida(String NombreSalida) {
    	SalidaManejador sm = SalidaManejador.getInstance();
    	salida salida = sm.getSalida(NombreSalida);
        if (salida == null)
        	return null;
    	return salida.toDataType();
    }
    
	public List<String> obtenerNombresActividadesAsociadas(salida salida) {
		List<String> nombresActividades = new ArrayList<>();
		for (actividad actividad : salida.getActividades()) {
				nombresActividades.add(actividad.getNombre());
		}
		return nombresActividades;
	}

	public List<dataSalida> obtenerSalidasVigentesPorActividad(String nombreActividad) {
	    List<dataSalida> listaSalidasPorActividad = new ArrayList<>();
	    ActividadManejador am = ActividadManejador.getinstance();
	    actividad actividad = am.getActividad(nombreActividad);

	    if (actividad != null) {
	            List<salida> salidas = actividad.getSalidas();
	            LocalDate fechaActual = LocalDate.now();

	            for (salida s : salidas) {
	                    // Validar si la salida está vigente (fechaActual >= fecha de alta y fechaActual <= fecha de salida)
	                    if (fechaActual.compareTo(s.getFechaAlta()) >= 0 && fechaActual.compareTo(s.getFechaSalida()) <= 0) {
	                            dataSalida ds = new dataSalida(
	                                    s.getNombre(),
	                                    s.getCapacidad(),
	                                    s.getFechaAlta(),
	                                    s.getFechaSalida(),
	                                    s.getLugarSalida(),
	                                    s.getActividades()
	                            );
	                            listaSalidasPorActividad.add(ds);
	                    }
	            }
	    }


	    return listaSalidasPorActividad.isEmpty() ? null : listaSalidasPorActividad;

	}

	public void eliminarSalida(String salida){
		
		SalidaManejador sm = SalidaManejador.getInstance();
		sm.deleteSalida(salida);
			
	}


    private static boolean validarTexto(String texto, int nivel) {
        switch (nivel) {
            case 1:
                return validarNivel1(texto);
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
