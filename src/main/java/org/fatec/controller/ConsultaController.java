package org.fatec.controller;

import org.fatec.domain.Animal;
import org.fatec.domain.Consulta;
import org.fatec.domain.Veterinario;
import org.fatec.facade.ConsultaCommandFacade;
import org.fatec.service.AnimalService;
import org.fatec.service.ConsultaService;
import org.fatec.service.VeterinarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/consultas")
public class ConsultaController {

    private final ConsultaService consultaService;
    private final AnimalService animalService;
    private final VeterinarioService veterinarioService;
    private final ConsultaCommandFacade consultaCommandFacade;

    @Autowired
    public ConsultaController(
            ConsultaService consultaService,
            AnimalService animalService,
            VeterinarioService veterinarioService,
            ConsultaCommandFacade consultaCommandFacade) {
        this.consultaService = consultaService;
        this.animalService = animalService;
        this.veterinarioService = veterinarioService;
        this.consultaCommandFacade = consultaCommandFacade;
    }

    @GetMapping
    public ModelAndView listarConsultas() {
        ModelAndView mv = new ModelAndView("consulta/lista");
        List<Consulta> consultas = consultaService.findAll();
        mv.addObject("consultas", consultas);
        return mv;
    }

    @GetMapping("/nova")
    public ModelAndView formNovaConsulta() {
        ModelAndView mv = new ModelAndView("consulta/form");
        mv.addObject("consulta", new Consulta());

        // Dados para os selects do formulário
        List<Animal> animais = animalService.findAll();
        List<Veterinario> veterinarios = veterinarioService.findAll();

        mv.addObject("animais", animais);
        mv.addObject("veterinarios", veterinarios);

        return mv;
    }

    @PostMapping
    public ModelAndView salvarConsulta(
            @RequestParam("petId") String petId,
            @RequestParam("veterinarioId") String veterinarioId,
            @ModelAttribute Consulta consulta,
            RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView("redirect:/consultas");
        try {
            // Utilizar o padrão Command através da fachada
            consultaCommandFacade.salvarConsulta(consulta, petId, veterinarioId);
            redirectAttributes.addFlashAttribute("mensagem", "Consulta agendada com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao agendar consulta: " + e.getMessage());
            mv.setViewName("redirect:/consultas/nova");
        }
        return mv;
    }

    @GetMapping("/{id}")
    public ModelAndView visualizarConsulta(@PathVariable String id, RedirectAttributes redirectAttributes) {
        Optional<Consulta> consulta = consultaService.findById(id);
        ModelAndView mv = new ModelAndView();

        if (consulta.isPresent()) {
            mv.setViewName("consulta/visualizar");
            mv.addObject("consulta", consulta.get());
        } else {
            mv.setViewName("redirect:/consultas");
            redirectAttributes.addFlashAttribute("erro", "Consulta não encontrada");
        }
        return mv;
    }

    @GetMapping("/{id}/editar")
    public ModelAndView formEditarConsulta(@PathVariable String id, RedirectAttributes redirectAttributes) {
        Optional<Consulta> consulta = consultaService.findById(id);
        ModelAndView mv = new ModelAndView();

        if (consulta.isPresent()) {
            mv.setViewName("consulta/form");
            mv.addObject("consulta", consulta.get());

            // Dados para os selects do formulário
            List<Animal> animais = animalService.findAll();
            List<Veterinario> veterinarios = veterinarioService.findAll();

            mv.addObject("animais", animais);
            mv.addObject("veterinarios", veterinarios);
        } else {
            mv.setViewName("redirect:/consultas");
            redirectAttributes.addFlashAttribute("erro", "Consulta não encontrada");
        }
        return mv;
    }

    @GetMapping("/{id}/excluir")
    public ModelAndView excluirConsulta(@PathVariable String id, RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView("redirect:/consultas");
        try {
            // Utilizar o padrão Command através da fachada
            consultaCommandFacade.cancelarConsulta(id);
            redirectAttributes.addFlashAttribute("mensagem", "Consulta cancelada com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao cancelar consulta: " + e.getMessage());
        }
        return mv;
    }

    @GetMapping("/buscar/pet")
    public ModelAndView buscarConsultasPorPet(@RequestParam String nomePet) {
        ModelAndView mv = new ModelAndView("consulta/lista");
        // Utilizar o padrão Command através da fachada
        List<Consulta> consultas = consultaCommandFacade.buscarConsultasPorPet(nomePet);
        mv.addObject("consultas", consultas);
        mv.addObject("termoBusca", nomePet);
        mv.addObject("tipoBusca", "pet");
        return mv;
    }

    @GetMapping("/buscar/veterinario")
    public ModelAndView buscarConsultasPorVeterinario(@RequestParam String nomeVet) {
        ModelAndView mv = new ModelAndView("consulta/lista");
        // Utilizar o padrão Command através da fachada
        List<Consulta> consultas = consultaCommandFacade.buscarConsultasPorVeterinario(nomeVet);
        mv.addObject("consultas", consultas);
        mv.addObject("termoBusca", nomeVet);
        mv.addObject("tipoBusca", "veterinario");
        return mv;
    }

    @GetMapping("/buscar/data")
    public ModelAndView buscarConsultasPorData(@RequestParam String data) {
        ModelAndView mv = new ModelAndView("consulta/lista");
        // Utilizar o padrão Command através da fachada
        List<Consulta> consultas = consultaCommandFacade.buscarConsultasPorData(data);
        mv.addObject("consultas", consultas);
        mv.addObject("termoBusca", data);
        mv.addObject("tipoBusca", "data");
        return mv;
    }

    /**
     * Endpoint para desfazer a última operação realizada
     */
    @GetMapping("/desfazer")
    public ModelAndView desfazerUltimaOperacao(RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView("redirect:/consultas");

        boolean sucesso = consultaCommandFacade.desfazerUltimaOperacao();

        if (sucesso) {
            redirectAttributes.addFlashAttribute("mensagem", "Última operação desfeita com sucesso!");
        } else {
            redirectAttributes.addFlashAttribute("erro", "Não foi possível desfazer a última operação.");
        }

        return mv;
    }
}
