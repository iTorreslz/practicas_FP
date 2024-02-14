package org.iesbelen.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.iesbelen.dao.EmpleadoDAO;
import org.iesbelen.dao.EmpleadoDAOImpl;
import org.iesbelen.model.Empleado;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "empleadoServlet", value = "/empleados/*")
public class EmpleadoServlet extends HttpServlet {
    /**
     * HTTP Method: GET
     * Paths:
     * /empleados/
     * /empleados/editar{id}
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher = null;
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || "/".equals(pathInfo)) {
            EmpleadoDAO emplDAO = new EmpleadoDAOImpl();

            //GET
            // /empleados/
            // /empleados

            List<Empleado> listaEmpleados = emplDAO.getAll();
            request.setAttribute("listaEmpleados", listaEmpleados);
            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/empleado/empleado.jsp");
        } else {

            // GET
            // 		/empleados/editar/{id}
            // 		/empleados/editar/{id}/

            pathInfo = pathInfo.replaceAll("/$", "");
            String[] pathParts = pathInfo.split("/");

            if (pathParts.length == 3 && "editar".equals(pathParts[1])) {

                EmpleadoDAO fabDAO = new EmpleadoDAOImpl();

                // GET
                // /fabricantes/editar/{id}
                try {
                    request.setAttribute("empleado", fabDAO.find(Integer.parseInt(pathParts[2])));
                    dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/empleado/editar-empleado.jsp");

                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                    dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/empleado/empleado.jsp");
                }

            } else {
                System.out.println("Opción POST no soportada.");
                dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/empleados/empleados.jsp");

            }
        }
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EmpleadoDAO emplDAO = new EmpleadoDAOImpl();
        String nombre = request.getParameter("nombre");
        String apellido1 = request.getParameter("apellido1");
        String apellido2 = request.getParameter("apellido2");
        Empleado empl = new Empleado();

        try {
            empl.setNombre(nombre);
            empl.setApellido1(apellido1);
            empl.setApellido1(apellido2);
            emplDAO.update(empl);

        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }
    }
}