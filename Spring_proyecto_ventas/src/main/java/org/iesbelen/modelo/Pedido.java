package org.iesbelen.modelo;

import java.util.Date;

public class Pedido {
    private long id;
    private double total;
    private Date fecha;
    private long id_cliente;
    private long id_comercial;

    public Pedido(long id, double total, Date fecha, long id_cliente, long id_comercial) {
        this.id = id;
        this.total = total;
        this.fecha = fecha;
        this.id_cliente = id_cliente;
        this.id_comercial = id_comercial;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public long getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(long id_cliente) {
        this.id_cliente = id_cliente;
    }

    public long getId_comercial() {
        return id_comercial;
    }

    public void setId_comercial(long id_comercial) {
        this.id_comercial = id_comercial;
    }
}