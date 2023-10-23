package com.servlets;

import com.sc.controladores.UsuarioController;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import com.sc.datatypes.dataUsuario;
import com.sc.interfaces.IUsuarioController;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private IUsuarioController userController;

    public UserServlet() {
        super();
        userController = new UsuarioController();

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String nickname = request.getParameter("nickname");
        PrintWriter out = response.getWriter();

        try {
            switch (action) {
                case "mostrarInfo":
                    dataUsuario usuarioInfo = userController.mostrarInfo(nickname);
                    out.println("Usuario encontrado: " + usuarioInfo.toString());
                    break;
                case "listarUsuarios":
                    List<dataUsuario> usuarios = userController.listarUsuarios();
                    out.println("Lista de usuarios: " + usuarios.toString());
                    break;
                case "listarProveedores":
                    List<dataUsuario> proveedores = userController.listarProveedores();
                    out.println("Lista de proveedores: " + proveedores.toString());
                    break;
                default:
                    out.println("Acción no reconocida");
                    break;
            }
        } catch (Exception e) {
            out.println("Error al realizar la acción: " + e.getMessage());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String nickname = request.getParameter("nickname");
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String email = request.getParameter("email");
        String nacionalidad = request.getParameter("nacionalidad");
        String nacimiento = request.getParameter("nacimiento");
        String descripcion = request.getParameter("descripcion");
        String url = request.getParameter("url");

        try {
            switch (action) {
                case "registrarTurista":
                    userController.registrarTurista(nickname, nombre, apellido, email, nacionalidad, LocalDate.parse(nacimiento));
                    break;
                case "registrarProveedor":
                    userController.registrarProveedor(nickname, nombre, apellido, email, descripcion, url, LocalDate.parse(nacimiento));
                    break;
                case "modificarUsuario":
                    userController.modificarUsuario(nickname, nombre, apellido, LocalDate.parse(nacimiento));
                    break;
                default:
                    response.getWriter().println("Acción no reconocida");
                    break;
            }
        } catch (Exception e) {
            response.getWriter().println("Error al realizar la acción: " + e.getMessage());
        }
    }
}
