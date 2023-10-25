package com.servlets;
import com.sc.controladores.ActividadController;
import java.io.IOException;
import java.util.List;
import com.sc.datatypes.dataActividad;
import com.sc.excepciones.ParametrosInvalidosExcepcion;
import com.sc.excepciones.UsuarioNoExisteExcepcion;
import com.sc.excepciones.UsuarioYaExisteExcepcion;
import com.sc.interfaces.IActividadController;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/ActividadControllerServlet")
public class ActividadControllerServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private IActividadController actividadController;

    public ActividadControllerServlet() {
        super();
        actividadController = new ActividadController();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String responseStr = "";
        if (action.equalsIgnoreCase("show")) {
            String nombre = request.getParameter("nombre");
            
            /**
 * Caso de uso: Mostrar Datos de Actividad
 * @implNote Permite consultar la información detallada de una actividad en el sistema.
 * @param nombre String - El nombre de la actividad a consultar.
 * @return String - Devuelve los datos de la actividad en formato de cadena.
 */
            
            try {
                dataActividad actividad = actividadController.mostrarDatos(nombre);
                responseStr = actividad.toString();
            } catch (UsuarioNoExisteExcepcion e) {
                responseStr = "Error: " + e.getMessage();
            }
            
            /**
 * Caso de uso: Obtener Todas las Actividades
 * @implNote Permite obtener una lista de todas las actividades registradas en el sistema.
 * @return String - Devuelve una lista de datos de actividades en formato de cadena.
 */

            
        } else if (action.equalsIgnoreCase("getAll")) {
            List<dataActividad> actividades = actividadController.getAllActividades();
            responseStr = actividades.toString();
            
            /**
 * Caso de uso: Obtener Actividades por Departamento
 * @implNote Permite obtener una lista de actividades pertenecientes a un departamento específico.
 * @param nombreDep String - El nombre del departamento.
 * @return String - Devuelve una lista de datos de actividades en formato de cadena.
 */

            
        } else if (action.equalsIgnoreCase("getAllByDepartamento")) {
            String nombreDep = request.getParameter("nombreDep");
            List<dataActividad> actividades = actividadController.getAllActividadesDepartamento(nombreDep);
            responseStr = actividades.toString();
        }

        response.getWriter().println(responseStr);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String responseStr = "";

        /**
 * Caso de uso: Crear Actividad
 * @implNote Permite crear una nueva actividad en el sistema.
 * @param nombreDepto String - El nombre del departamento al que pertenece la actividad.
 * @param nombreProv String - El nombre del proveedor de la actividad.
 * @param nombre String (único) - El nombre de la actividad.
 * @param desc String - La descripción de la actividad.
 * @param duracion int - La duración de la actividad en minutos.
 * @param costoUni int - El costo unitario de la actividad.
 * @param ciudad String - La ciudad donde se realiza la actividad.
 * @param fechaCrea LocalDate - La fecha de creación de la actividad.
 * @return String - Devuelve un mensaje de éxito o un mensaje de error en formato de cadena.
 */
        
        if (action.equalsIgnoreCase("create")) {
            String nombreDepto = request.getParameter("nombreDepto");
            String nombreProv = request.getParameter("nombreProv");
            String nombre = request.getParameter("nombre");
            String desc = request.getParameter("desc");
            int duracion = Integer.parseInt(request.getParameter("duracion"));
            int costoUni = Integer.parseInt(request.getParameter("costoUni"));
            String ciudad = request.getParameter("ciudad");
            LocalDate fechaCrea = LocalDate.parse(request.getParameter("fechaCrea"));

            try {
                actividadController.crearActividad(nombreDepto, nombreProv, nombre, desc, duracion, costoUni, ciudad, fechaCrea);
                responseStr = "Actividad creada correctamente";
            } catch (ParametrosInvalidosExcepcion | UsuarioYaExisteExcepcion | UsuarioNoExisteExcepcion e) {
                responseStr = "Error: " + e.getMessage();
            }
            
            /**
 * Caso de uso: Modificar Actividad
 * @implNote Permite modificar los datos de una actividad existente en el sistema.
 * @param nombre String (único) - El nombre de la actividad a modificar.
 * @param desc String - La nueva descripción de la actividad.
 * @param duracion int - La nueva duración de la actividad en minutos.
 * @param costoUni int - El nuevo costo unitario de la actividad.
 * @param ciudad String - La nueva ciudad donde se realiza la actividad.
 * @param fechaCrea LocalDate - La nueva fecha de creación de la actividad.
 * @return String - Devuelve un mensaje de éxito o un mensaje de error en formato de cadena.
 */
            
        } else if (action.equalsIgnoreCase("modify")) {
            String nombre = request.getParameter("nombre");
            String desc = request.getParameter("desc");
            int duracion = Integer.parseInt(request.getParameter("duracion"));
            int costoUni = Integer.parseInt(request.getParameter("costoUni"));
            String ciudad = request.getParameter("ciudad");
            LocalDate fechaCrea = LocalDate.parse(request.getParameter("fechaCrea"));

            try {
                actividadController.modificarActividad(nombre, desc, duracion, costoUni, ciudad, fechaCrea);
                responseStr = "Actividad modificada correctamente";
            } catch (ParametrosInvalidosExcepcion | UsuarioYaExisteExcepcion | UsuarioNoExisteExcepcion e) {
                responseStr = "Error: " + e.getMessage();
            }
        }
    }
}