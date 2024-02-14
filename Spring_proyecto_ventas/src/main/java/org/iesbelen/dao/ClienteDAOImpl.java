package org.iesbelen.dao;

import lombok.extern.slf4j.Slf4j;
import org.iesbelen.modelo.Cliente;
import org.iesbelen.modelo.Comercial;
import org.iesbelen.modelo.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

//Anotación lombok para logging (traza) de la aplicación
@Slf4j
//Un Repository es un componente y a su vez un estereotipo de Spring 
//que forma parte de la ‘capa de persistencia’.
@Repository
public class ClienteDAOImpl implements ClienteDAO {

    //Plantilla jdbc inyectada automáticamente por el framework Spring, gracias a la anotación @Autowired.
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Inserta en base de datos el nuevo Cliente, actualizando el id en el bean Cliente.
     */
    @Override
    public synchronized void create(Cliente cliente) {

        //Desde java15+ se tiene la triple quote """ para bloques de texto como cadenas.
        String sqlInsert = """
                INSERT INTO cliente (nombre, apellido1, apellido2, ciudad, categoría) 
                VALUES  (     ?,         ?,         ?,       ?,         ?)
                  """;

        KeyHolder keyHolder = new GeneratedKeyHolder();
        //Con recuperación de id generado
        int rows = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sqlInsert, new String[]{"id"});
            int idx = 1;
            ps.setString(idx++, cliente.getNombre());
            ps.setString(idx++, cliente.getApellido1());
            ps.setString(idx++, cliente.getApellido2());
            ps.setString(idx++, cliente.getCiudad());
            ps.setInt(idx, cliente.getCategoria());
            return ps;
        }, keyHolder);

        cliente.setId(keyHolder.getKey().intValue());

        //Sin recuperación de id generado
//		int rows = jdbcTemplate.update(sqlInsert,
//							cliente.getNombre(),
//							cliente.getApellido1(),
//							cliente.getApellido2(),
//							cliente.getCiudad(),
//							cliente.getCategoria()
//					);

        log.info("Insertados {} registros.", rows);
    }

    /**
     * Devuelve lista con todos los Clientes.
     */
    @Override
    public List<Cliente> getAll() {

        List<Cliente> listFab = jdbcTemplate.query(
                "SELECT * FROM cliente",
                (rs, rowNum) -> new Cliente(rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido1"),
                        rs.getString("apellido2"),
                        rs.getString("ciudad"),
                        rs.getInt("categoría")
                )
        );

        log.info("Devueltos {} registros.", listFab.size());

        return listFab;
    }

    /**
     * Devuelve lista de Comerciales asociados con el ID de Cliente dado.
     */
    public List<Comercial> getComerciales(int id) {

        String query = "SELECT DISTINCT c.* FROM comercial c " +
                "INNER JOIN pedido p ON c.id = p.id_comercial " +
                "INNER JOIN cliente cl ON p.id_cliente = cl.id " +
                "WHERE p.id_cliente = ";

        List<Comercial> listaComerciales = jdbcTemplate.query(
                query + id,
                (rs, rowNum) -> new Comercial(rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido1"),
                        rs.getString("apellido2"),
                        rs.getFloat("comisión")
                )
        );

        log.info("Devueltos {} registros.", listaComerciales.size());

        return listaComerciales;
    }

    public List<Pedido> getPedidosComercial(int id_cliente, int id_comercial) {

        String query = "SELECT * FROM pedido WHERE id_comercial = "
                + id_comercial + " AND id_cliente = " + id_cliente;

        List<Pedido> listaPedidos = jdbcTemplate.query(query,
                (rs, rowNum) -> new Pedido(rs.getLong("id"),
                        rs.getDouble("total"),
                        rs.getDate("fecha"),
                        rs.getLong("id_cliente"),
                        rs.getLong("id_comercial")
                )
        );

        log.info("Devueltos {} registros.", listaPedidos.size());

        return listaPedidos;

    }

    public List<Pedido> getPedidosCliente(int id_cliente) {
        String query = "SELECT * FROM pedido WHERE id_cliente = " + id_cliente;

        List<Pedido> listaPedidos = jdbcTemplate.query(query,
                (rs, rowNum) -> new Pedido(rs.getLong("id"),
                        rs.getDouble("total"),
                        rs.getDate("fecha"),
                        rs.getLong("id_cliente"),
                        rs.getLong("id_comercial")
                )
        );

        log.info("Devueltos {} registros.", listaPedidos.size());

        return listaPedidos;
    }

    /**
     * Devuelve Optional de Cliente con el ID dado.
     */
    @Override
    public Optional<Cliente> find(int id) {

        Cliente fab = jdbcTemplate
                .queryForObject("SELECT * FROM cliente WHERE id = ?"
                        , (rs, rowNum) -> new Cliente(rs.getInt("id"),
                                rs.getString("nombre"),
                                rs.getString("apellido1"),
                                rs.getString("apellido2"),
                                rs.getString("ciudad"),
                                rs.getInt("categoría"))
                        , id
                );

        if (fab != null) {
            return Optional.of(fab);
        } else {
            log.info("Cliente no encontrado.");
            return Optional.empty();
        }

    }

    /**
     * Actualiza Cliente con campos del bean Cliente según ID del mismo.
     */
    @Override
    public void update(Cliente cliente) {

        int rows = jdbcTemplate.update("""
                        UPDATE cliente SET 
                        				nombre = ?, 
                        				apellido1 = ?, 
                        				apellido2 = ?,
                        				ciudad = ?,
                        				categoría = ?  
                        		WHERE id = ?
                        """, cliente.getNombre()
                , cliente.getApellido1()
                , cliente.getApellido2()
                , cliente.getCiudad()
                , cliente.getCategoria()
                , cliente.getId());

        log.info("Update de Cliente con {} registros actualizados.", rows);
    }

    /**
     * Borra Cliente con ID proporcionado.
     */
    @Override
    public void delete(long id) {

        int rows = jdbcTemplate.update("DELETE FROM cliente WHERE id = ?", id);

        log.info("Delete de Cliente con {} registros eliminados.", rows);
    }

}
