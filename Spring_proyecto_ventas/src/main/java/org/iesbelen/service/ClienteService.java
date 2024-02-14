package org.iesbelen.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.iesbelen.dao.ClienteDAO;
import org.iesbelen.dto.ClienteDTO;
import org.iesbelen.dto.ComercialDTO;
import org.iesbelen.mapstruct.ClienteMapper;
import org.iesbelen.mapstruct.ComercialMapper;
import org.iesbelen.modelo.Cliente;
import org.iesbelen.modelo.Comercial;
import org.iesbelen.modelo.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private ClienteDAO clienteDAO;

    public List<Cliente> listAll() {
        return clienteDAO.getAll();
    }

    public List<ComercialDTO> listComerciales(Integer id) {
        List<Comercial> listaComerciales = clienteDAO.getComerciales(id);

        List<ComercialDTO> listaComercialesDTO = new ArrayList<>();
        listaComerciales.forEach(comercial -> {
            List<Pedido> pedidos = clienteDAO.getPedidosComercial(id, comercial.getId());
            ComercialDTO comercialDTO = ComercialMapper.INSTANCE.comercialAComercialDTO(comercial, pedidos.size());
            listaComercialesDTO.add(comercialDTO);
        });

        return listaComercialesDTO;
    }

    public Cliente one(Integer id) {
        Optional<Cliente> optionalCliente = clienteDAO.find(id);
        if (optionalCliente.isPresent()) {

            Cliente cliente = optionalCliente.get();
            List<Pedido> pedidos = clienteDAO.getPedidosCliente(id);

            int pedidosAnyo = 0;

            for (int i = 0; i < pedidos.size(); i++) {
                if (pedidos.get(i).getFecha().getYear() == pedidos.get(i + 1).getFecha().getYear()) {
                    pedidosAnyo++;
                }
            }

            ClienteDTO clienteDTO = ClienteMapper.INSTANCE.clienteAClienteDTO(cliente, );

            return cliente;

        } else {

            return null;
        }
    }

    public void newCliente(Cliente cliente) {
        clienteDAO.create(cliente);
    }

    public void replaceCliente(Cliente cliente) {
        clienteDAO.update(cliente);
    }

    public void deleteCliente(int id) {
        clienteDAO.delete(id);
    }
}