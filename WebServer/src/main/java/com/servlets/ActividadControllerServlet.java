package com.servlets;

import com.sc.controladores.ActividadController;
import java.io.IOException;
import java.util.List;
import com.sc.datatypes.dataActividad;
import com.sc.excepciones.UsuarioNoExisteExcepcion;
import com.sc.interfaces.IActividadController;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ActividadControllerServlet")
public class ActividadControllerServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private IActividadController actividadController;

    public ActividadControllerServlet() {
        super();
        actividadController = new ActividadController();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (action.equalsIgnoreCase("show")) {
            String nombre = request.getParameter("nombre");

            try {
                dataActividad actividad = actividadController.mostrarDatos(nombre);
                responseStr = actividad.toString();
            } catch (UsuarioNoExisteExcepcion e) {
                responseStr = "Error: " + e.getMessage();
            }
        } else if (action.equalsIgnoreCase("getAll")) {
            List<dataActividad> actividades = actividadController.getAllActividades();
            responseStr = actividades.toString();
        } else if (action.equalsIgnoreCase("getAllByDepartamento")) {
            String nombreDep = request.getParameter("nombreDep");
            List<dataActividad> actividades = actividadController.getAllActividadesDepartamento(nombreDep);
            responseStr = actividades.toString();
        }

        response.getWriter().println(responseStr);
    }
}

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String responseStr = "";

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
