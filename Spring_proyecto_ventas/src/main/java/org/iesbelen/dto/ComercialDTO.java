package org.iesbelen.dto;

import org.iesbelen.modelo.Comercial;

public class ComercialDTO extends Comercial {
    private int totalPedidos;
    private double mediaPedidos;
    private int pedidosCliente;

    public ComercialDTO() {
    }

    public ComercialDTO(Comercial comercial) {
        this.setId(comercial.getId());
        this.setNombre(comercial.getNombre());
        this.setApellido1(comercial.getApellido1());
        this.setApellido2(comercial.getApellido2());
        this.setComision(comercial.getComision());
    }

    public int getTotalPedidos() {
        return totalPedidos;
    }

    public void setTotalPedidos(int totalPedidos) {
        this.totalPedidos = totalPedidos;
    }

    public double getMediaPedidos() {
        return mediaPedidos;
    }

    public void setMediaPedidos(double mediaPedidos) {
        this.mediaPedidos = mediaPedidos;
    }

    public int getPedidosCliente() {
        return pedidosCliente;
    }

    public void setPedidosCliente(int pedidosCliente) {
        this.pedidosCliente = pedidosCliente;
    }
}
