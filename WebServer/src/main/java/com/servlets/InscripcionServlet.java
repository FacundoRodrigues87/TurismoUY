package com.servlets;
import java.io.IOException;
import java.time.LocalDate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sc.controladores.InscripcionController;
import com.sc.controladores.SalidaController;
import com.sc.datatypes.dataSalida;
import com.sc.entidades.salida;
import com.sc.excepciones.ParametrosInvalidosExcepcion;
import com.sc.excepciones.UsuarioNoExisteExcepcion;
import com.sc.excepciones.UsuarioYaExisteExcepcion;
import com.sc.interfaces.IInscripcionController;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet; 
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.sc.interfaces.ISalidaController;

@WebServlet("/inscripcion")
public class InscripcionServlet extends HttpServlet {
    private IInscripcionController inscripcionController;
    private ISalidaController salidaController;   
    private ObjectMapper objectMapper = new ObjectMapper();

    public InscripcionServlet() {
        inscripcionController = new InscripcionController();
    }
    
    public void salidaServlet(){
        salidaController = new SalidaController();  
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("crear".equals(action)) {
            crearInscripcion(request, response);
        }
    }

    /**
 * Caso de uso: Crear Inscripción
 * @implNote Permite a un turista crear una inscripción en una salida con una actividad específica.
 * @param fecha LocalDate - La fecha de la inscripción.
 * @param cantidad int - La cantidad de inscripciones a crear.
 * @param nombreTurista String - El nombre del turista que realiza la inscripción.
 * @param nombreSalida String - El nombre de la salida a la que se inscribe.
 * @param nombreActividad String - El nombre de la actividad a la que se inscribe.
 * @return JSON - Devuelve un mensaje de éxito o un mensaje de error en formato JSON.
 */
    
     private void crearInscripcion(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            LocalDate fecha = LocalDate.parse(request.getParameter("fecha"));
            int cantidad = Integer.parseInt(request.getParameter("cant"));
            String nombreTurista = request.getParameter("nombreTurista");
            String nombreSalida = request.getParameter("nombreSalida");
            String nombreActividad = request.getParameter("nombreActividad");

            inscripcionController.crearInscripcion(fecha, cantidad, nombreTurista, nombreSalida, nombreActividad);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Inscripción creada exitosamente");
        } catch (ParametrosInvalidosExcepcion | UsuarioYaExisteExcepcion | UsuarioNoExisteExcepcion | NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Error al crear la inscripción: " + e.getMessage());
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("calcularCosto".equals(action)) {
            calcularCosto(request, response);
        }
    }

    /**
 * Caso de uso: Calcular Costo de Inscripción
 * @implNote Permite calcular el costo total de una inscripción en una salida con una actividad específica.
 * @param nombreSalida String - El nombre de la salida.
 * @param nombreActividad String - El nombre de la actividad.
 * @param cantidad int - La cantidad de inscripciones.
 * @return JSON - Devuelve el costo calculado en formato JSON.
 */
    
    private void calcularCosto(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {   
            salida nombreSalida = inscripcionController.convertToSalida(request.getParameter("nombreSalida"));
            String nombreActividad = request.getParameter("nombreActividad");
            int cantidad = Integer.parseInt(request.getParameter("cant"));

            int costo = inscripcionController.calcularCosto(nombreSalida, nombreActividad, cantidad);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("El costo calculado es: " + costo);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Error al calcular el costo: " + e.getMessage());
        }
    }

}

