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

@WebServlet(name = "loginServlet", value = "/tienda/login/*")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher = null;
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || "/".equals(pathInfo)) {

            //GET
            //	/login/
            //	/login

            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/usuarios/login.jsp");
        }
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();

        if (pathInfo == null || "/".equals(pathInfo)) {
            //POST
            //	/login/
            //	/login

            HttpSession session = request.getSession();

            UsuarioDAO usuarioDAO = new UsuarioDAOImpl();
            List<Usuario> usuarioList = usuarioDAO.getAll();

            String user = request.getParameter("usuario");
            String passwd = request.getParameter("password");

            usuarioList.forEach(usuario -> {
                if (usuario.getUsuario().equals(user) && usuario.getPassword().equals(passwd)) {
                    session.setAttribute("usuario-logado", usuario);
                }
            });

            if (session.getAttribute("usuario-logado") == null) {
                response.sendRedirect(request.getContextPath() + "/tienda/login");
            } else {
                response.sendRedirect(request.getContextPath() + "/");
            }
        }
    }
}