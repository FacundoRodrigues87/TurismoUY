package com.sc.controladores;

import com.sc.entidades.actividad;
import com.sc.entidades.departamento;
import com.sc.entidades.proveedor;
import com.sc.entidades.salida;
import com.sc.entidades.usuario;
import com.sc.entidades.paquete;
import com.sc.excepciones.ParametrosInvalidosExcepcion;
import com.sc.excepciones.UsuarioYaExisteExcepcion;
import com.sc.interfaces.IActividadController;
import com.sc.manejadores.ActividadManejador;
import com.sc.manejadores.DepartamentoManejador;
import com.sc.manejadores.PaqueteManejador;
import com.sc.manejadores.UsuarioManejador;
import com.sc.utilidades.log;
import com.sc.datatypes.dataActividad;
import com.sc.excepciones.UsuarioNoExisteExcepcion;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ActividadController implements IActividadController {

	public void crearActividad(String nombreDepto, String nombreProv, String nombre, String desc, int duracion, int costoUni, String ciudad, LocalDate fechaCrea) throws ParametrosInvalidosExcepcion, UsuarioYaExisteExcepcion, UsuarioNoExisteExcepcion{
		
        // Validaciones sobre parametros.
        if (!validarTexto(nombreDepto, 1) || !validarTexto(nombre, 1) || !validarTexto(desc, 1) || !validarTexto(ciudad, 1)) {
            // log.error("Parametros invalidos.");
            // return;
            throw new ParametrosInvalidosExcepcion();
        }
        
        DepartamentoManejador dm = DepartamentoManejador.getinstance();
        departamento depto = dm.getDepartamento(nombreDepto);
        
        if(depto == null) {
        	// log.error("El departamento " + nombreDepto + "no existe");
        	throw new UsuarioNoExisteExcepcion("El departamento " + nombreDepto + " no existe");
        }
        
		ActividadManejador am = ActividadManejador.getinstance();
		
        if(am.getActividad(nombre) != null){
        	// log.error("La actividad '" + nombre + "' ya existe.");
        	throw new UsuarioYaExisteExcepcion("La actividad " + nombre + " ya existe");
        }
        
        UsuarioManejador um = UsuarioManejador.getinstance();
        usuario user = um.getUsuario(nombreProv);
        proveedor prov = (proveedor) user;
        
        if(prov == null) {
        	log.error("El proveedor " + nombreProv + "no existe");
        	throw new UsuarioNoExisteExcepcion("El proveedor " + nombreProv + " no existe");
        }
        
		actividad act = new actividad(nombre, desc, duracion, costoUni, ciudad, fechaCrea);
		
        act.setDepartamento(depto);
        act.setProveedor(prov);

        prov.addActividad(act);

        am.addActividad(act);
        um.updateUsuario(prov);

        depto.agregarActividad(act);
        dm.updateDepartamento(depto);
	}
	
	public dataActividad mostrarDatos(String nombreAct) throws UsuarioNoExisteExcepcion{
		ActividadManejador am = ActividadManejador.getinstance();
		actividad act = am.getActividad(nombreAct);		
		if (act == null) {
        	log.error("La actividad '" + nombreAct + "' ya existe.");
        	throw new UsuarioNoExisteExcepcion("La actividad " + nombreAct + " ya existe");
		}
		dataActividad DTAct = act.toDataType();		
		return DTAct;		
	};
	
	public void modificarActividad(String nombreAct, String desc, int duracion, int costoUni, String ciudad, LocalDate fechaCrea)throws ParametrosInvalidosExcepcion, UsuarioNoExisteExcepcion, UsuarioYaExisteExcepcion{
        if (!validarTexto(nombreAct, 1) || !validarTexto(desc, 1) || !validarTexto(ciudad, 1)) {
            log.error("Parametros invalidos.");
            throw new ParametrosInvalidosExcepcion();
        }
	      
        ActividadManejador am = ActividadManejador.getinstance();
        actividad act = am.getActividad(nombreAct);
        
        if(act == null) {
        	log.error("La actividad " + nombreAct + "no existe");
        	throw new UsuarioNoExisteExcepcion("La actividad " + nombreAct + " no existe");
        }
        
        DepartamentoManejador dm = DepartamentoManejador.getinstance();
        List <departamento> LDepto = dm.getAllDepartamento();
        //Recorro todos los departamentos para poder buscar la actividad que
        //me pasan por parametro
        for(departamento depto: LDepto){
        	List<actividad> LAct = depto.getActividades();
        	for(actividad act2 : LAct) {
        		if(act2.getNombre().equals(nombreAct)) {
        			act2.setDescripcion(desc);
        			act2.setDuracion(duracion);
        			act2.setCosteUni(costoUni);
        			act2.setCiudad(ciudad);
        			act2.setFechaCrea(fechaCrea);
        	        am.updateActividad(act2);
        		}
        	}
        }    
	}  
	
    public List<dataActividad> getAllActividades() {      
    	List<dataActividad> LDtAct = new ArrayList<>();
        DepartamentoManejador dm = DepartamentoManejador.getinstance();
        List <departamento> LDepto = dm.getAllDepartamento();
        //Recorro todos los departamentos para poder buscar la actividad que
        //me pasan por parametro
        if (LDepto == null) {
			return null;
		}
        for(departamento depto: LDepto){
            // log.warning(depto.getNombre());
            List<actividad> LAct = depto.getActividades();
            // log.warning("Cantidad de actividades: " + LAct.size());
            for(actividad act : LAct) {
            	dataActividad DtAct = act.toDataType();
            	LDtAct.add(DtAct);
        	}
        }
        return LDtAct;
    }

    public List<dataActividad> getAllActividadesDepartamento(String nombreDep) {
        List<dataActividad> LDtAct = new ArrayList<>();
           DepartamentoManejador dm = DepartamentoManejador.getinstance();
        List<departamento> deptos = dm.getAllDepartamento();
        
        if (deptos != null) {
            for (departamento depto : deptos) {
                if (depto != null && depto.getNombre() != null && depto.getNombre().equals(nombreDep)) {
                    List<actividad> LAct = depto.getActividades();
                    if (LAct != null) {
                        for (actividad act : LAct) {
                            LDtAct.add(act.toDataType());
                        }
                    }
                    break;
                }
            }
        }
        if (LDtAct.isEmpty())
            return null; 
        return LDtAct;
    }

    public List<dataActividad> getAllActividadesDepartamentoSinPaquete(String nombreDep, String nombrePaquete) {
        List<dataActividad> LDtAct = new ArrayList<>();
        DepartamentoManejador dm = DepartamentoManejador.getinstance();
        List<departamento> deptos = dm.getAllDepartamento();

        if (deptos != null) {
            for (departamento depto : deptos) {
                if (depto != null && depto.getNombre() != null && depto.getNombre().equals(nombreDep)) {
                    List<actividad> LAct = depto.getActividades();
                    if (LAct != null) {
                        for (actividad act : LAct) {
                            // Verifica si la actividad no tiene paquetes asociados
                            if (act.getPaquetes() == null || act.getPaquetes().isEmpty()) {
                                LDtAct.add(act.toDataType());
                            }
                        }
                    }
                    break;
                }
            }
        }
        return LDtAct;
    }

    public List<String> getNombresSalidasAsociadas(String nombreActividad) {
        ActividadManejador am = ActividadManejador.getinstance();
        actividad actividad = am.getActividad(nombreActividad);

        if (actividad == null) {
            // Si no se encuentra la actividad, puedes manejar el error como desees.
            return null;
        }

        List<salida> salidasAsociadas = actividad.getSalidas();
        List<String> nombresSalidas = new ArrayList<>();

        for (salida salida : salidasAsociadas) {
            nombresSalidas.add(salida.getNombre());
        }

        return nombresSalidas;
    }

    public List<dataActividad> getActividadesNoPaquete(String nombreDepartamento, String nombrePaquete) throws UsuarioNoExisteExcepcion {
        PaqueteManejador pm = PaqueteManejador.getinstance();
        DepartamentoManejador dm = DepartamentoManejador.getinstance();
        paquete paquete = pm.getPaquete(nombrePaquete);
        departamento departamento = dm.getDepartamento(nombreDepartamento);

        if (paquete == null) {
            log.error("El paquete: " + nombrePaquete + " no existe.");
        	throw new UsuarioNoExisteExcepcion("La actividad " + nombrePaquete + " ya existe");
        }
        
        if (departamento == null) {
            log.error("El departamento " + nombreDepartamento + " no existe.");
        	throw new UsuarioNoExisteExcepcion("La actividad " + nombreDepartamento + " ya existe");
        }

        List<dataActividad> dtActividades = new ArrayList<dataActividad>();
        List<actividad> actividades = departamento.getActividades();

        if (actividades == null) {
            return null;
        }

        for (actividad actividad : actividades) {
            // log.warning(actividad.getNombre());
            boolean existe = false;
            if (paquete.getActividades() != null) {
                for (actividad actividadP : paquete.getActividades()) {
                    if (actividad.getNombre().equals(actividadP.getNombre())) {
                        existe = true;
                        break;
                    }
                }
            }
            if (!existe) {
                dtActividades.add(actividad.toDataType());
            }
        }
        if (dtActividades.size() == 0) { dtActividades = null; }
        return dtActividades;
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
