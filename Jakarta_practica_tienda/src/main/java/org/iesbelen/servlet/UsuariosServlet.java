package org.iesbelen.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.iesbelen.dao.UsuarioDAO;
import org.iesbelen.dao.UsuarioDAOImpl;
import org.iesbelen.model.Usuario;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "usuariosServlet", value = "/tienda/usuarios/*")
public class UsuariosServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher = null;
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || "/".equals(pathInfo)) {
            UsuarioDAO usuarioDAO = new UsuarioDAOImpl();

            //GET
            //	/usuarios/
            //	/usuarios

            List<Usuario> listaUsuarios = usuarioDAO.getAll();
            request.setAttribute("listaUsuarios", listaUsuarios);
            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/usuarios/usuarios.jsp");

        } else {
            // GET
            // 		/usuarios/{id}
            // 		/usuarios/{id}/
            // 		/usuarios/edit/{id}
            // 		/usuarios/edit/{id}/
            // 		/usuarios/crear
            // 		/usuarios/crear/

            pathInfo = pathInfo.replaceAll("/$", "");
            String[] pathParts = pathInfo.split("/");

            if (pathParts.length == 2 && "crear".equals(pathParts[1])) {

                // GET
                // /usuarios/crear
                dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/usuarios/crear-usuario.jsp");

            } else if (pathParts.length == 3 && "editar".equals(pathParts[1])) {
                UsuarioDAO usuarioDAO = new UsuarioDAOImpl();

                // GET
                // /usuarios/editar/{id}
                try {
                    request.setAttribute("usuario", usuarioDAO.find(Integer.parseInt(pathParts[2])));
                    dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/usuarios/editar-usuario.jsp");

                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                    dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/usuarios/usuarios.jsp");
                }
            } else {
                System.out.println("Opción POST no soportada.");
                dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/usuarios/usuarios.jsp");
            }
        }

        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        pathInfo = pathInfo.replaceAll("/$", "");
        String[] pathParts = pathInfo.split("/");

        RequestDispatcher dispatcher;
        String __method__ = request.getParameter("__method__");

        if (__method__ == null) {
            // Crear uno nuevo
            UsuarioDAO usuarioDAO = new UsuarioDAOImpl();
            String usuario = request.getParameter("usuario");
            String password = request.getParameter("password");
            String rol = request.getParameter("rol");
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setUsuario(usuario);
            nuevoUsuario.setPassword(password);
            nuevoUsuario.setRol(rol);
            usuarioDAO.create(nuevoUsuario);

            response.sendRedirect(request.getContextPath() + "/tienda/usuarios");

        } else if (__method__ != null && "put".equalsIgnoreCase(__method__)) {
            doPut(request, response);
            response.sendRedirect(request.getContextPath() + "/tienda/usuarios");

        } else if (__method__ != null && "delete".equalsIgnoreCase(__method__)) {
            doDelete(request, response);
            response.sendRedirect(request.getContextPath() + "/tienda/usuarios");

        } else {
            System.out.println("Opción POST no soportada.");
            response.sendRedirect(request.getContextPath() + "/tienda/usuarios");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UsuarioDAO usuarioDAO = new UsuarioDAOImpl();
        String codigo = request.getParameter("codigo");
        String user = request.getParameter("usuario");
        String password = request.getParameter("password");
        String rol = request.getParameter("rol");
        Usuario usuario = new Usuario();

        try {
            int id = Integer.parseInt(codigo);
            usuario.setIdUsuario(id);
            usuario.setUsuario(user);
            usuario.setPassword(password);
            usuario.setRol(rol);
            usuarioDAO.update(usuario);

        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        RequestDispatcher dispatcher;
        UsuarioDAO usuarioDAO = new UsuarioDAOImpl();
        String codigo = request.getParameter("codigo");

        try {
            int id = Integer.parseInt(codigo);

            usuarioDAO.delete(id);

        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }
    }
}