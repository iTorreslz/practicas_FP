package org.iesbelen.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.iesbelen.dao.FabricanteDAO;
import org.iesbelen.dao.FabricanteDAOImpl;
import org.iesbelen.dto.FabricanteDTO;
import org.iesbelen.model.Fabricante;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

@WebServlet(name = "fabricantesServlet", value = "/tienda/fabricantes/*")
public class FabricantesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher = null;
        String pathInfo = request.getPathInfo(); //

        if (pathInfo == null || "/".equals(pathInfo)) {
            FabricanteDAO fabricanteDAO = new FabricanteDAOImpl();

            //GET
            //	/fabricantes/
            //	/fabricantes

            List<Fabricante> listaFabricantes = fabricanteDAO.getAll();
            List<FabricanteDTO> listaFabricantesDTO = listaFabricantes.stream()
                    .map(fabricante -> new FabricanteDTO(fabricante))
                    .toList();

            listaFabricantesDTO.forEach(fabricanteDTO ->
                    fabricanteDTO.setNumeroProductos(fabricanteDAO.getCountProductos(fabricanteDTO.getIdFabricante()).get()));

            if (request.getParameter("ordenar-por") == null) {

                request.setAttribute("listaFabricantesDTO", listaFabricantesDTO);

            } else {
                if (request.getParameter("ordenar-por").equals("nombre")) {
                    if (request.getParameter("modo").equals("asc")) {
                        List<FabricanteDTO> listaFilter = listaFabricantesDTO.stream()
                                .sorted(Comparator.comparing(FabricanteDTO::getNombre))
                                .toList();
                        request.setAttribute("listaFabricantesDTO", listaFilter);
                    } else {
                        List<FabricanteDTO> listaFilter = listaFabricantesDTO.stream()
                                .sorted(Comparator.comparing(FabricanteDTO::getNombre).reversed())
                                .toList();
                        request.setAttribute("listaFabricantesDTO", listaFilter);
                    }
                } else {
                    if (request.getParameter("modo").equals("asc")) {
                        List<FabricanteDTO> listaFilter = listaFabricantesDTO.stream()
                                .sorted(Comparator.comparingInt(FabricanteDTO::getIdFabricante))
                                .toList();
                        request.setAttribute("listaFabricantesDTO", listaFilter);
                    } else {
                        List<FabricanteDTO> listaFilter = listaFabricantesDTO.stream()
                                .sorted(Comparator.comparingInt(FabricanteDTO::getIdFabricante).reversed())
                                .toList();
                        request.setAttribute("listaFabricantesDTO", listaFilter);
                    }
                }
            }

            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/fabricantes/fabricantes.jsp");

        } else {
            // GET
            // 		/fabricantes/{id}
            // 		/fabricantes/{id}/
            // 		/fabricantes/edit/{id}
            // 		/fabricantes/edit/{id}/
            // 		/fabricantes/crear
            // 		/fabricantes/crear/

            pathInfo = pathInfo.replaceAll("/$", "");
            String[] pathParts = pathInfo.split("/");

            if (pathParts.length == 2 && "crear".equals(pathParts[1])) {

                // GET
                // /fabricantes/crear
                dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/fabricantes/crear-fabricante.jsp");

            } else if (pathParts.length == 2) {
                FabricanteDAO fabricanteDAO = new FabricanteDAOImpl();
                // GET
                // /fabricantes/{id}
                try {
                    request.setAttribute("fabricante", fabricanteDAO.find(Integer.parseInt(pathParts[1])));
                    dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/fabricantes/detalle-fabricante.jsp");

                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                    dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/fabricantes/fabricantes.jsp");
                }
            } else if (pathParts.length == 3 && "editar".equals(pathParts[1])) {
                FabricanteDAO fabricanteDAO = new FabricanteDAOImpl();

                // GET
                // /fabricantes/editar/{id}
                try {
                    request.setAttribute("fabricante", fabricanteDAO.find(Integer.parseInt(pathParts[2])));
                    dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/fabricantes/editar-fabricante.jsp");

                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                    dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/fabricantes/fabricantes.jsp");
                }
            } else {
                System.out.println("Opción POST no soportada.");
                dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/fabricantes/fabricantes.jsp");

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
            FabricanteDAO fabricanteDAO = new FabricanteDAOImpl();
            String nombre = request.getParameter("nombre");
            Fabricante nuevoFab = new Fabricante();
            nuevoFab.setNombre(nombre);
            fabricanteDAO.create(nuevoFab);

        } else if (__method__ != null && "put".equalsIgnoreCase(__method__)) {
            doPut(request, response);

        } else if (__method__ != null && "delete".equalsIgnoreCase(__method__)) {
            doDelete(request, response);
        } else {
            System.out.println("Opción POST no soportada.");
        }

        response.sendRedirect(request.getContextPath() + "/tienda/fabricantes");
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        FabricanteDAO fabricanteDAO = new FabricanteDAOImpl();
        String codigo = request.getParameter("codigo");
        String nombre = request.getParameter("nombre");
        Fabricante fabricante = new Fabricante();

        try {
            int id = Integer.parseInt(codigo);
            fabricante.setIdFabricante(id);
            fabricante.setNombre(nombre);
            fabricanteDAO.update(fabricante);

        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        RequestDispatcher dispatcher;
        FabricanteDAO fabricanteDAO = new FabricanteDAOImpl();
        String codigo = request.getParameter("codigo");

        try {
            int id = Integer.parseInt(codigo);

            fabricanteDAO.delete(id);

        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }
    }
}