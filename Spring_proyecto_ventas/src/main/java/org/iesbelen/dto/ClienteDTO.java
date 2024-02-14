package org.iesbelen.dto;

import org.iesbelen.modelo.Cliente;
import org.iesbelen.modelo.Comercial;

import java.util.List;

public class ClienteDTO extends Cliente {

    private int pedidosTrimestre;
    private int pedidosSemestre;
    private int pedidosAnyo;
    private int pedidosLustro;

    public ClienteDTO() {
    }

    public int getPedidosTrimestre() {
        return pedidosTrimestre;
    }

    public void setPedidosTrimestre(int pedidosTrimestre) {
        this.pedidosTrimestre = pedidosTrimestre;
    }

    public int getPedidosSemestre() {
        return pedidosSemestre;
    }

    public void setPedidosSemestre(int pedidosSemestre) {
        this.pedidosSemestre = pedidosSemestre;
    }

    public int getPedidosAnyo() {
        return pedidosAnyo;
    }

    public void setPedidosAnyo(int pedidosAnyo) {
        this.pedidosAnyo = pedidosAnyo;
    }

    public int getPedidosLustro() {
        return pedidosLustro;
    }

    public void setPedidosLustro(int pedidosLustro) {
        this.pedidosLustro = pedidosLustro;
    }
}