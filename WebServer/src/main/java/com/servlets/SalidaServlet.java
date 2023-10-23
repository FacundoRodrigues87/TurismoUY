import java.io.IOException;
import java.time.LocalDate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sc.controladores.SalidaController;
import com.sc.excepciones.ParametrosInvalidosExcepcion;
import com.sc.excepciones.UsuarioNoExisteExcepcion;
import com.sc.excepciones.UsuarioYaExisteExcepcion;
import com.sc.datatypes.dataSalida;
import com.sc.interfaces.ISalidaController;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet("/salida")
public class SalidaServlet extends HttpServlet {
    private ISalidaController salidaController;
    private ObjectMapper objectMapper = new ObjectMapper(); // Jackson's ObjectMapper for JSON serialization/deserialization

    public SalidaServlet() {
    salidaController = new SalidaController();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("crear".equals(action)) {
            crearSalida(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("mostrarDatosSalida".equals(action)) {
            mostrarDatosSalida(request, response);
        } else if ("getAllSalidas".equals(action)) {
            getAllSalidas(request, response);
        }
    }

    private void crearSalida(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String nombre = request.getParameter("nombre");
            int capacidad = Integer.parseInt(request.getParameter("capacidad"));
            LocalDate fechaAlta = LocalDate.parse(request.getParameter("fechaAlta"));
            LocalDate fechaSalida = LocalDate.parse(request.getParameter("fechaSalida"));
            String lugarSalida = request.getParameter("lugarSalida");
            String nombreActividad = request.getParameter("nombreActividad");

            salidaController.crearSalida(nombre, capacidad, fechaAlta, fechaSalida, lugarSalida, nombreActividad);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Salida creada exitosamente");
        } catch (ParametrosInvalidosExcepcion | UsuarioYaExisteExcepcion | UsuarioNoExisteExcepcion | NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Error al crear la salida: " + e.getMessage());
        }
    }

    private void mostrarDatosSalida(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String nombreSalida = request.getParameter("nombreSalida");
        dataSalida data = salidaController.mostrarDatosSalida(nombreSalida);
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(data));
    }

    private void getAllSalidas(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<dataSalida> data = salidaController.getAllSalidas();

        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(data));
    }
}

