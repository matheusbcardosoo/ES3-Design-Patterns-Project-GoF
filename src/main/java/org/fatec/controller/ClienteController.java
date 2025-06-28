package org.fatec.controller;

import org.fatec.domain.Cliente;
import org.fatec.domain.UserRolesEnum;
import org.fatec.service.ClienteService;
import org.fatec.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;
    private final ValidationService validationService;

    @Autowired
    public ClienteController(ClienteService clienteService, ValidationService validationService) {
        this.clienteService = clienteService;
        this.validationService = validationService;
    }

    @GetMapping
    public ModelAndView listarClientes() {
        ModelAndView mv = new ModelAndView("cliente/lista");
        List<Cliente> clientes = clienteService.findAll();
        mv.addObject("clientes", clientes);
        return mv;
    }

    @GetMapping("/novo")
    public ModelAndView formNovoCliente() {
        ModelAndView mv = new ModelAndView("cliente/form");
        mv.addObject("cliente", new Cliente());
        return mv;
    }

    @PostMapping
    public ModelAndView salvarCliente(@ModelAttribute Cliente cliente, RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView("redirect:/clientes");
        try {
            // Garantir que o userRole esteja configurado
            if (cliente.getUserRole() == null) {
                cliente.setUserRole(UserRolesEnum.CLIENTE);
            }

            // Validar o cliente usando o padrão Strategy
            List<String> erros = validationService.validateClienteWithErrors(cliente);
            if (!erros.isEmpty()) {
                mv.setViewName("cliente/form");
                mv.addObject("cliente", cliente);
                mv.addObject("erros", erros);
                return mv;
            }

            // Verificar se estamos criando um novo cliente
            boolean isNovoCliente = (cliente.getId() == null || cliente.getId().isEmpty());

            // Salvar o cliente
            Cliente clienteSalvo = clienteService.save(cliente);

            // Se o ID ainda estiver vazio após o salvamento, é um problema
            if (clienteSalvo.getId() == null || clienteSalvo.getId().isEmpty()) {
                System.err.println("ATENÇÃO: ID do cliente continua vazio após salvamento!");
            } else {
                System.out.println("Cliente salvo com ID: " + clienteSalvo.getId());
            }

            redirectAttributes.addFlashAttribute("mensagem", "Cliente salvo com sucesso!");
        } catch (Exception e) {
            e.printStackTrace(); // Isso vai imprimir o stack trace completo no console
            redirectAttributes.addFlashAttribute("erro", "Erro ao salvar cliente: " + e.getMessage());
            mv.setViewName("redirect:/clientes/novo");
        }
        return mv;
    }

    @GetMapping("/{id}")
    public ModelAndView visualizarCliente(@PathVariable String id, RedirectAttributes redirectAttributes) {
        Optional<Cliente> cliente = clienteService.findById(id);
        ModelAndView mv = new ModelAndView();

        if (cliente.isPresent()) {
            mv.setViewName("cliente/visualizar");
            mv.addObject("cliente", cliente.get());
        } else {
            mv.setViewName("redirect:/clientes");
            redirectAttributes.addFlashAttribute("erro", "Cliente não encontrado");
        }
        return mv;
    }

    @GetMapping("/{id}/editar")
    public ModelAndView formEditarCliente(@PathVariable String id, RedirectAttributes redirectAttributes) {
        Optional<Cliente> cliente = clienteService.findById(id);
        ModelAndView mv = new ModelAndView();

        if (cliente.isPresent()) {
            mv.setViewName("cliente/form");
            mv.addObject("cliente", cliente.get());
        } else {
            mv.setViewName("redirect:/clientes");
            redirectAttributes.addFlashAttribute("erro", "Cliente não encontrado");
        }
        return mv;
    }

    @GetMapping("/{id}/excluir")
    public ModelAndView excluirCliente(@PathVariable String id, RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView("redirect:/clientes");
        try {
            clienteService.deleteById(id);
            redirectAttributes.addFlashAttribute("mensagem", "Cliente excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao excluir cliente: " + e.getMessage());
        }
        return mv;
    }

    @GetMapping("/buscar")
    public ModelAndView buscarClientes(@RequestParam String nome) {
        ModelAndView mv = new ModelAndView("cliente/lista");
        List<Cliente> clientes = clienteService.findByNome(nome);
        mv.addObject("clientes", clientes);
        mv.addObject("termoBusca", nome);
        return mv;
    }
}
