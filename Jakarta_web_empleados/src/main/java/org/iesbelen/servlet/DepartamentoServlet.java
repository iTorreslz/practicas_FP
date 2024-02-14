package org.iesbelen.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.iesbelen.dao.DepartamentoDAO;
import org.iesbelen.dao.DepartamentoDAOImpl;
import org.iesbelen.model.Departamento;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "departamentoServlet", value = "/departamentos/*")
public class DepartamentoServlet extends HttpServlet {
    /**
     * HTTP Method: GET
     * Paths:
     * /departamentos/
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher = null;
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || "/".equals(pathInfo)) {
            DepartamentoDAO depDAO = new DepartamentoDAOImpl();

            //GET
            // /departamentos/
            // /departamentos

            List<Departamento> listaDepartamentos = depDAO.getAll();
            request.setAttribute("listaDepartamentos", listaDepartamentos);
            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/departamento/departamento.jsp");
        } else {
            // GET
            // /departamentos/crear
            // /departamentos/crear/
            pathInfo = pathInfo.replaceAll("/$", "");
            String[] pathParts = pathInfo.split("/");
            if (pathParts.length == 2 && "crear".equals(pathParts[1])) {
                // GET
                // /departamentos/crear
                dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/departamento/crear-departamento.jsp");
            }
        }
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher;
        String __method__ = request.getParameter("__method__");

        if (__method__ == null) {
            // Crear uno nuevo
            DepartamentoDAO depDAO = new DepartamentoDAOImpl();
            String nombre = request.getParameter("nombre");
            Departamento nuevoDep = new Departamento();
            nuevoDep.setNombre(nombre);
            nuevoDep.setPresupuesto(0);
            nuevoDep.setGastos(0);
            depDAO.create(nuevoDep);

        } else {
            System.out.println("Opción POST no soportada.");
        }
        response.sendRedirect(request.getContextPath() + "/departamentos");
    }
}

