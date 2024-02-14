package org.iesbelen.dao;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Repository
//Utilizo lombok para generar el constructor
@AllArgsConstructor
public class PedidoDAOImpl implements PedidoDAO {

    //JdbcTemplate se inyecta por el constructor de la clase automáticamente
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Devuelve lista con todos los Pedidos.
     */
    @Override
    public List<Pedido> getAll() {

        List<Pedido> listaPedidos = jdbcTemplate.query(
                "SELECT * FROM pedido",
                (rs, rowNum) -> new Pedido(rs.getLong("id"),
                        rs.getDouble("total"),
                        rs.getDate("fecha"),
                        rs.getLong("id_cliente"),
                        rs.getLong("id_comercial"))
        );

        log.info("Devueltos {} registros.", listaPedidos.size());

        return listaPedidos;
    }
}