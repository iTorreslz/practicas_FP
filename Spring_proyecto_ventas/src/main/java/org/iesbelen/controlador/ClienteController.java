package org.iesbelen.controlador;

import java.util.List;

import org.iesbelen.dto.ClienteDTO;
import org.iesbelen.dto.ComercialDTO;
import org.iesbelen.mapstruct.ComercialMapper;
import org.iesbelen.modelo.Cliente;
import org.iesbelen.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class ClienteController {

    @Autowired
    private ComercialMapper comercialMapper;

    private ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/clientes")
    public String listar(Model model) {

        List<Cliente> listaClientes = clienteService.listAll();
        model.addAttribute("listaClientes", listaClientes);

        return "clientes/clientes";
    }

    @GetMapping("/clientes/{id}")
    public String detalle(Model model, @PathVariable Integer id ) {

        Cliente cliente = clienteService.one(id);
        model.addAttribute("cliente", cliente);

        List<ComercialDTO> listaComerciales = clienteService.listComerciales(id);
        model.addAttribute("listaComerciales", listaComerciales);

        return "clientes/detalle-cliente";
    }

    @GetMapping("/clientes/crear")
    public String crear(Model model) {

        Cliente cliente = new Cliente();
        model.addAttribute("cliente", cliente);

        return "clientes/crear-cliente";
    }

    @PostMapping("/clientes/crear")
    public RedirectView submitCrear(@ModelAttribute("cliente") Cliente cliente) {

        clienteService.newCliente(cliente);

        return new RedirectView("/clientes") ;
    }

    @GetMapping("/clientes/editar/{id}")
    public String editar(Model model, @PathVariable Integer id) {

        Cliente cliente = clienteService.one(id);
        model.addAttribute("cliente", cliente);

        return "clientes/editar-cliente";
    }


    @PostMapping("/clientes/editar/{id}")
    public RedirectView submitEditar(@ModelAttribute("cliente") Cliente cliente) {

        clienteService.replaceCliente(cliente);

        return new RedirectView("/clientes");
    }

    @PostMapping("/clientes/borrar/{id}")
    public RedirectView submitBorrar(@PathVariable Integer id) {

        clienteService.deleteCliente(id);

        return new RedirectView("/clientes");
    }
}