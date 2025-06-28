package org.fatec.controller;

import org.fatec.domain.Veterinario;
import org.fatec.service.ValidationService;
import org.fatec.service.VeterinarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/veterinarios")
public class VeterinarioController {

    private final VeterinarioService veterinarioService;
    private final ValidationService validationService;

    @Autowired
    public VeterinarioController(VeterinarioService veterinarioService, ValidationService validationService) {
        this.veterinarioService = veterinarioService;
        this.validationService = validationService;
    }

    @GetMapping
    public ModelAndView listarVeterinarios() {
        ModelAndView mv = new ModelAndView("veterinario/lista");
        List<Veterinario> veterinarios = veterinarioService.findAll();
        mv.addObject("veterinarios", veterinarios);
        return mv;
    }

    @GetMapping("/novo")
    public ModelAndView formNovoVeterinario() {
        ModelAndView mv = new ModelAndView("veterinario/form");
        mv.addObject("veterinario", new Veterinario());
        return mv;
    }

    @PostMapping
    public ModelAndView salvarVeterinario(@ModelAttribute Veterinario veterinario, RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView("redirect:/veterinarios");
        try {
            // Aplicar validação usando o padrão Strategy
            List<String> erros = validationService.validateVeterinarioWithErrors(veterinario);
            if (!erros.isEmpty()) {
                mv.setViewName("veterinario/form");
                mv.addObject("veterinario", veterinario);
                mv.addObject("erros", erros);
                return mv;
            }

            // Se chegou aqui, a validação passou
            veterinarioService.save(veterinario);
            redirectAttributes.addFlashAttribute("mensagem", "Veterinário salvo com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao salvar veterinário: " + e.getMessage());
            mv.setViewName("redirect:/veterinarios/novo");
        }
        return mv;
    }

    @GetMapping("/{id}")
    public ModelAndView visualizarVeterinario(@PathVariable String id, RedirectAttributes redirectAttributes) {
        Optional<Veterinario> veterinario = veterinarioService.findById(id);
        ModelAndView mv = new ModelAndView();

        if (veterinario.isPresent()) {
            mv.setViewName("veterinario/visualizar");
            mv.addObject("veterinario", veterinario.get());
        } else {
            mv.setViewName("redirect:/veterinarios");
            redirectAttributes.addFlashAttribute("erro", "Veterinário não encontrado");
        }
        return mv;
    }

    @GetMapping("/{id}/editar")
    public ModelAndView formEditarVeterinario(@PathVariable String id, RedirectAttributes redirectAttributes) {
        Optional<Veterinario> veterinario = veterinarioService.findById(id);
        ModelAndView mv = new ModelAndView();

        if (veterinario.isPresent()) {
            mv.setViewName("veterinario/form");
            mv.addObject("veterinario", veterinario.get());
        } else {
            mv.setViewName("redirect:/veterinarios");
            redirectAttributes.addFlashAttribute("erro", "Veterinário não encontrado");
        }
        return mv;
    }

    @GetMapping("/{id}/excluir")
    public ModelAndView excluirVeterinario(@PathVariable String id, RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView("redirect:/veterinarios");
        try {
            veterinarioService.deleteById(id);
            redirectAttributes.addFlashAttribute("mensagem", "Veterinário excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao excluir veterinário: " + e.getMessage());
        }
        return mv;
    }

    @GetMapping("/buscar")
    public ModelAndView buscarVeterinarios(@RequestParam String nome) {
        ModelAndView mv = new ModelAndView("veterinario/lista");
        List<Veterinario> veterinarios = veterinarioService.findByNome(nome);
        mv.addObject("veterinarios", veterinarios);
        mv.addObject("termoBusca", nome);
        return mv;
    }
}
