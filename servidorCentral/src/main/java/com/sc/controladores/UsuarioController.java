package com.sc.controladores;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.sc.datatypes.dataUsuario;
import com.sc.entidades.proveedor;
import com.sc.entidades.turista;
import com.sc.entidades.usuario;
import com.sc.excepciones.ParametrosInvalidosExcepcion;
import com.sc.excepciones.UsuarioNoExisteExcepcion;
import com.sc.excepciones.UsuarioYaExisteExcepcion;
import com.sc.interfaces.IUsuarioController;
import com.sc.manejadores.UsuarioManejador;
import com.sc.utilidades.log;


public class UsuarioController implements IUsuarioController {

    public void registrarTurista(String nickname, String nombre, String apellido, String email, String nacionalidad, LocalDate nacimiento) throws ParametrosInvalidosExcepcion, UsuarioYaExisteExcepcion {
        // Validaciones sobre parametros.
        if (!validarTexto(nickname, 3) || !validarTexto(nombre, 1) || !validarTexto(apellido, 1) || !validarTexto(email, 2)) {
            log.error("Parametros invalidos.");
            throw new ParametrosInvalidosExcepcion();
        }

        // Trae la instancia del manejador.
        UsuarioManejador um = UsuarioManejador.getinstance();

        // Valida si el email ya existe.
        if (um.getEmail(email)){
            log.error("[Turista] El correo '" + email + "' ya existe.");
            throw new UsuarioYaExisteExcepcion("El correo '" + email + "' ya existe");
        }

        usuario user = um.getUsuario(nickname);

        // Valida si el usuario ya existe.
        if (user != null) {
            log.error("[Turista] El usuario '" + nickname + "' ya existe.");
            throw new UsuarioYaExisteExcepcion("El nickname '" + nickname + "' ya existe");
        }

        log.info("[Turista] Registrando al turista.. " + nickname);
        user = new turista(nickname, nombre, apellido, email, nacionalidad, nacimiento);
        um.addUsuario(user);
    };


    public dataUsuario mostrarInfo(String nickname) throws UsuarioNoExisteExcepcion {
        // Trae la instancia del manejador.
        UsuarioManejador um = UsuarioManejador.getinstance();

        usuario user = um.getUsuario(nickname);

        if (user == null) {
            // log.error("El usuario no existe.");
            // return null; // ⚠️ Cambiar por excepción.
            throw new UsuarioNoExisteExcepcion("El usuario '" + nickname + "' no existe.");
        }


        dataUsuario dt;
        if (user instanceof proveedor) {
            proveedor userp = (proveedor) user;
            dt = userp.toDataType();
        } else {
            turista usert = (turista) user;
            dt = usert.toDataType();
        }
        return dt;
    };


    public void registrarProveedor(String nickname, String nombre, String apellido, String email, String descripcion, String url, LocalDate nacimiento) throws ParametrosInvalidosExcepcion, UsuarioYaExisteExcepcion {
        // Validaciones sobre parametros.
        if (!validarTexto(nickname, 3) ||
            !validarTexto(nombre, 1) ||
            !validarTexto(apellido, 1) ||
            !validarTexto(email, 2) ||
            !validarTexto(descripcion, 1) ||
            !validarTexto(url, 1))
            {
            log.error("Parametros invalidos.");
            return;
        }

        // Trae la instancia del manejador.
        UsuarioManejador um = UsuarioManejador.getinstance();

        // Valida si el email ya existe.
        if (um.getEmail(email)){
            // log.error("[Proveedor] El correo '" + email + "' ya existe.");
            // return; // ⚠️ Cambiar por excepción.
            throw new UsuarioYaExisteExcepcion("El correo '" + email + "' ya existe");
        }

        usuario user = um.getUsuario(nickname);

        // Valida si el usuario ya existe.
        if (user != null) {
            // log.error("[Proveedor] El usuario '" + nickname + "' ya existe.");
            // return; // ⚠️ Cambiar por excepción.
            throw new UsuarioYaExisteExcepcion("El nickname '" + nickname + "' ya existe");
        }

        log.info("[Proveedor] Registrando al proveedor.. " + nickname);
        user = new proveedor(nickname, nombre, apellido, email, descripcion, url, nacimiento);
        um.addUsuario(user);
    };


    public void modificarUsuario(String nickname, String nombre, String apellido, LocalDate nacimiento) throws ParametrosInvalidosExcepcion {
        // Validaciones sobre parametros.
        if (!validarTexto(nickname, 3) ||
            !validarTexto(nombre, 1) ||
            !validarTexto(apellido, 1))
            {
            throw new ParametrosInvalidosExcepcion();
        }

        // Trae la instancia del manejador.
        UsuarioManejador um = UsuarioManejador.getinstance();

        usuario user = um.getUsuario(nickname);

        if (user == null){
            // throw new UsuarioNoExisteExcepcion("El usuario" + nickname + " no existe.");
            log.error("Usuario '" + nickname + "' no existe");
            return;
        }

        user.setNombre(nombre);
        user.setApellido(apellido);
        user.setNacimiento(nacimiento);

        um.updateUsuario(user);

        log.info("[modificarUsuario] El usuario '" + nickname + "' se actualizó con exito");
    };


    public List<dataUsuario> listarUsuarios(){
        // Trae la instancia del manejador.
        UsuarioManejador um = UsuarioManejador.getinstance();
        List<usuario> LUsuarios = um.getAllUsuario();
        List<dataUsuario> LDataUsuarios = new ArrayList <dataUsuario>();
        if (LUsuarios == null) {
			return null;
		}
        for(usuario user: LUsuarios){
        	if(user instanceof turista){
        		turista tur = (turista) user;
        		dataUsuario DtUsuario = new dataUsuario(tur.getNickname(), tur.getNombre(),tur.getApellido(),tur.getEmail(),tur.getNacionalidad(),tur.getNacimiento(),false,null,null,null,null);
        		LDataUsuarios.add(DtUsuario);
        	}
        	if(user instanceof proveedor){
        		proveedor prov = (proveedor) user;
        		dataUsuario DtUsuario = new dataUsuario(prov.getNickname(),prov.getNombre(),prov.getApellido(),prov.getEmail(),null,prov.getNacimiento(),true,prov.getDescripcion(),prov.getUrl(),null,null);
        		LDataUsuarios.add(DtUsuario);
        	}
        	
        }
        // Ordena los proveedores primero, luego los turistas
        Collections.sort(LUsuarios, new Comparator<usuario>() {
            @Override
            public int compare(usuario u1, usuario u2) {
                if (u1 instanceof proveedor && u2 instanceof turista) {
                    return -1; // Proveedor antes que Turista
                } else if (u1 instanceof turista && u2 instanceof proveedor) {
                    return 1; // Turista después de Proveedor
                }
                return 0; // Mismo tipo o ambos no son Proveedores ni Turistas
            }
        });
        return LDataUsuarios;
    };

    public List<dataUsuario> listarProveedores() {
        // Trae la instancia del manejador.
        UsuarioManejador um = UsuarioManejador.getinstance();
        List<dataUsuario> dataProveedores = new ArrayList<dataUsuario>();

        List<proveedor> proveedores = um.getAllProveedor();

        for (proveedor proveedor : proveedores) {
            log.warning(proveedor.getNickname());
            dataProveedores.add(proveedor.toDataType());
        }
        
        return dataProveedores;
    }

    public List<String> listarTuristas() {
        // Trae la instancia del manejador.
        UsuarioManejador um = UsuarioManejador.getinstance();
        List<usuario> LUsuarios = um.getAllUsuario();
        List<String> LNicknamesTuristas = new ArrayList<>();
    
        if (LUsuarios != null) {
            for (usuario user : LUsuarios) {
                if (user instanceof turista) {
                    LNicknamesTuristas.add(user.getNickname());
                }
            }
        }
    
        return LNicknamesTuristas;
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
