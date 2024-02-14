package org.iesbelen.service;

import org.iesbelen.dao.ClienteDAO;
import org.iesbelen.dao.ComercialDAO;
import org.iesbelen.dao.PedidoDAO;
import org.iesbelen.modelo.Cliente;
import org.iesbelen.modelo.Comercial;
import org.iesbelen.modelo.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ComercialService {

    @Autowired
    private ComercialDAO comercialDAO;
    @Autowired
    private PedidoDAO pedidoDAO;
    @Autowired
    private ClienteDAO clienteDAO;

    public List<Comercial> listAll() {
        return comercialDAO.getAll();
    }

    public int total(Integer id) {
        Optional<Integer> totalPedidos = comercialDAO.getTotalPedidos(id);

        if (totalPedidos.isPresent())
            return totalPedidos.get();
        else
            return 0;
    }

    public double media(Integer id) {
        Optional<Integer> totalPedidosComercial = comercialDAO.getTotalPedidos(id);

        List<Pedido> listaPedidosGeneral = pedidoDAO.getAll();

        long totalPedidosGeneral = listaPedidosGeneral.stream().count();
        long totalPedidosCom;
        double media;

        if (totalPedidosComercial.isPresent()) {

            totalPedidosCom = (long) totalPedidosComercial.get();
            media = (double) totalPedidosCom / totalPedidosGeneral;

            return media;

        } else {
            return 0;
        }
    }

    public Comercial one(Integer id) {
        Optional<Comercial> optComercial = comercialDAO.find(id);
        if (optComercial.isPresent())
            return optComercial.get();
        else
            return null;
    }

    public List<Pedido> verPedidos(Integer id) {

        List<Pedido> allPedidos = pedidoDAO.getAll();
        List<Pedido> comercialPedidos = new ArrayList<>();
        allPedidos.forEach(pedido -> {
            if (pedido.getId_comercial() == id) {
                comercialPedidos.add(pedido);
            }
        });
        return comercialPedidos;
    }

    public List<Cliente> verClientes(Integer id) {

        Optional<List<Cliente>> listaClientes = comercialDAO.getClientes(id);

        if (listaClientes.isPresent()) {

            return listaClientes.get();

        } else {
            System.out.println("La lista está vacía");
            return null;
        }
    }

    public void newComercial(Comercial comercial) {
        comercialDAO.create(comercial);
    }

    public void replaceComercial(Comercial comercial) {
        comercialDAO.update(comercial);
    }

    public void deleteComercial(int id) {
        comercialDAO.delete(id);
    }
}