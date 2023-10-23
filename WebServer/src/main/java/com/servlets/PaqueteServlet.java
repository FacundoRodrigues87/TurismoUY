package com.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import com.sc.interfaces.IPaqueteController;
import com.sc.datatypes.dataPaquete;
import com.sc.controladores.PaqueteController;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/PaqueteServlet")
public class PaqueteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IPaqueteController controller;
    
    public PaqueteServlet() {
        controller = new PaqueteController();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        ObjectMapper objectMapper = new ObjectMapper();

        /**
 * Caso de uso: Mostrar Información de Paquete
 * @implNote Permite consultar la información detallada de un paquete en el sistema.
 * @param nombre String - El nombre del paquete a consultar.
 * @return JSON - Devuelve los datos del paquete en formato JSON.
 */
        
        if(action.equalsIgnoreCase("mostrarInfo")) {
            String nombre = request.getParameter("nombre");
            try {
                dataPaquete info = controller.mostrarInfo(nombre);
                response.getWriter().write(objectMapper.writeValueAsString(info));
            } catch (Exception e) {
                response.getWriter().write(objectMapper.writeValueAsString(e.getMessage()));
            }
            
            /**
 * Caso de uso: Listar Paquetes
 * @implNote Permite obtener una lista de todos los paquetes registrados en el sistema.
 * @return JSON - Devuelve una lista de datos de paquetes en formato JSON.
 */
            
        } else if(action.equalsIgnoreCase("listarPaquetes")) {
            try {
                List<dataPaquete> paquetes = controller.listarPaquetes();
                response.getWriter().write(objectMapper.writeValueAsString(paquetes));
            } catch (Exception e) {
                response.getWriter().write(objectMapper.writeValueAsString(e.getMessage()));
            }
            
            /**
 * Caso de uso: Obtener Nombres de Paquetes
 * @implNote Permite obtener una lista de nombres de todos los paquetes registrados en el sistema.
 * @return JSON - Devuelve una lista de nombres de paquetes en formato JSON.
 */

            
        } else if(action.equalsIgnoreCase("nombresPaquetes")) {
            try {
                List<String> nombresPaquetes = controller.obtenerNombresPaquetes();
                response.getWriter().write(objectMapper.writeValueAsString(nombresPaquetes));
            } catch (Exception e) {
                response.getWriter().write(objectMapper.writeValueAsString(e.getMessage()));
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        ObjectMapper objectMapper = new ObjectMapper();

        /**
 * Caso de uso: Crear Paquete
 * @implNote Permite crear un nuevo paquete en el sistema.
 * @param nombre String (único) - El nombre del paquete.
 * @param descripcion String - La descripción del paquete.
 * @param periodoVal int - El período de validez del paquete en días.
 * @param descuento int - El descuento aplicado al paquete.
 * @param fechaAlta LocalDate - La fecha de alta del paquete.
 * @return JSON - Devuelve un mensaje de éxito o un mensaje de error en formato JSON.
 */
        
        if(action.equalsIgnoreCase("crearPaquete")) {
            String nombre = request.getParameter("nombre");
            String descripcion = request.getParameter("descripcion");
            int periodoVal = Integer.parseInt(request.getParameter("periodoVal"));
            int descuento = Integer.parseInt(request.getParameter("descuento"));
            LocalDate fechaAlta = LocalDate.parse(request.getParameter("fechaAlta"));
            
            try {
                controller.crearPaquete(nombre, descripcion, periodoVal, descuento, fechaAlta);
                response.getWriter().write(objectMapper.writeValueAsString("Paquete creado con éxito"));
            } catch (Exception e) {
                response.getWriter().write(objectMapper.writeValueAsString(e.getMessage()));
            }
            
            /**
 * Caso de uso: Agregar Actividad a Paquete
 * @implNote Permite agregar una actividad específica a un paquete existente en el sistema.
 * @param nombrePaquete String - El nombre del paquete al que se agregará la actividad.
 * @param nombreActividad String - El nombre de la actividad a agregar al paquete.
 * @return JSON - Devuelve un mensaje de éxito o un mensaje de error en formato JSON.
 */
            
        } else if(action.equalsIgnoreCase("agregarActividadPaquete")) {
            String nombrePaquete = request.getParameter("nombrePaquete");
            String nombreActividad = request.getParameter("nombreActividad");            
            try {
                controller.agregarActividadPaquete(nombrePaquete,  nombreActividad);
                response.getWriter().write(objectMapper.writeValueAsString("Actividad agregada con éxito"));
            } catch (Exception e) {
                response.getWriter().write(objectMapper.writeValueAsString(e.getMessage()));
            }
        }
    }
}
