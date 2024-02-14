package org.iesbelen.dao;

import java.util.List;
import java.util.Optional;

import org.iesbelen.modelo.Cliente;
import org.iesbelen.modelo.Comercial;
import org.iesbelen.modelo.Pedido;

public interface ClienteDAO {

	public void create(Cliente cliente);
	public List<Cliente> getAll();
	public List<Comercial> getComerciales(int id);
	public List<Pedido> getPedidosComercial (int id_cliente, int id_comercial);
	public List<Pedido> getPedidosCliente (int id_cliente);
	public Optional<Cliente> find(int id);
	public void update(Cliente cliente);
	public void delete(long id);
	
}
