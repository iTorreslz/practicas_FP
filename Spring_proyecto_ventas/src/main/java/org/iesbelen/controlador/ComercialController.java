package org.iesbelen.controlador;

import org.iesbelen.dao.ComercialDAO;
import org.iesbelen.dao.ComercialDAOImpl;
import org.iesbelen.dto.ComercialDTO;
import org.iesbelen.modelo.Cliente;
import org.iesbelen.modelo.Comercial;
import org.iesbelen.modelo.Pedido;
import org.iesbelen.service.ClienteService;
import org.iesbelen.service.ComercialService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Controller
public class ComercialController {

    private ComercialService comercialService;

    public ComercialController(ComercialService comercialService) {
        this.comercialService = comercialService;
    }

    @GetMapping("/comerciales")
    public String listar(Model model) {

        List<Comercial> listaComerciales = comercialService.listAll();
        List<ComercialDTO> listaComercialesDTO = listaComerciales.stream()
                .map(comercial -> new ComercialDTO(comercial))
                .toList();

        listaComercialesDTO.forEach(comercialDTO -> {
            comercialDTO.setTotalPedidos(comercialService.total(comercialDTO.getId()));
            comercialDTO.setMediaPedidos(comercialService.media(comercialDTO.getId()));
        });
        model.addAttribute("listaComerciales", listaComercialesDTO);

        return "comerciales/comerciales";
    }

    @GetMapping("/comerciales/{id}")
    public String detalle(Model model, @PathVariable Integer id) {

        Comercial comercial = comercialService.one(id);
        model.addAttribute("comercial", comercial);

        return "comerciales/detalle-comercial";
    }

    @GetMapping("/comerciales/crear")
    public String crear(Model model) {

        Comercial comercial = new Comercial();
        model.addAttribute("comercial", comercial);

        return "comerciales/crear-comercial";
    }

    @GetMapping("/comerciales/{id}/pedidos")
    public String pedidos(Model model, @PathVariable Integer id) {

        Comercial comercial = comercialService.one(id);
        model.addAttribute("comercial", comercial);

        List<Pedido> listaPedidos = comercialService.verPedidos(id);
        model.addAttribute("listaPedidos", listaPedidos);

        Pedido pedidoMax = listaPedidos.stream()
                .max(Comparator.comparingDouble(Pedido::getTotal))
                .orElse(null);
        Pedido pedidoMin = listaPedidos.stream()
                .min(Comparator.comparingDouble(Pedido::getTotal))
                .orElse(null);
        model.addAttribute("pedidoMax", pedidoMax);
        model.addAttribute("pedidoMin", pedidoMin);

        List<Cliente> listaClientes = comercialService.verClientes(id);
        listaClientes.sort(Comparator.comparingDouble((Cliente cliente) ->
                        listaPedidos.stream()
                                .filter(pedido -> pedido.getId_cliente() == cliente.getId() && pedido.getId_comercial() == id)
                                .mapToDouble(Pedido::getTotal)
                                .sum())
                .reversed());
        model.addAttribute("listaClientes", listaClientes);

        return "comerciales/ver-pedidos";
    }

    @PostMapping("/comerciales/crear")
    public RedirectView submitCrear(@ModelAttribute("comercial") Comercial comercial) {

        comercialService.newComercial(comercial);

        return new RedirectView("/comerciales");
    }

    @GetMapping("/comerciales/editar/{id}")
    public String editar(Model model, @PathVariable Integer id) {

        Comercial comercial = comercialService.one(id);
        model.addAttribute("comercial", comercial);

        return "comerciales/editar-comercial";
    }


    @PostMapping("/comerciales/editar/{id}")
    public RedirectView submitEditar(@ModelAttribute("comercial") Comercial comercial) {

        comercialService.replaceComercial(comercial);

        return new RedirectView("/comerciales");
    }

    @PostMapping("/comerciales/borrar/{id}")
    public RedirectView submitBorrar(@PathVariable Integer id) {

        comercialService.deleteComercial(id);

        return new RedirectView("/comerciales");
    }
}