import java.io.IOException;
import java.time.LocalDate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sc.controladores.InscripcionController;
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
    private ObjectMapper objectMapper = new ObjectMapper();

    public InscripcionServlet() {
        inscripcionController = new InscripcionController();
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("crear".equals(action)) {
            crearInscripcion(request, response);
        }
    }

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

    private void calcularCosto(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            dataSalida nombreSalida = inscripcionController.nameSalida(request.getParameter("nombreSalida"));
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

