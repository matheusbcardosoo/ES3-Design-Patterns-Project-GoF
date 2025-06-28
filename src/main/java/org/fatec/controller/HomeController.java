package org.fatec.controller;

import org.fatec.service.AnimalService;
import org.fatec.service.ClienteService;
import org.fatec.service.ConsultaService;
import org.fatec.service.VeterinarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    private final ClienteService clienteService;
    private final AnimalService animalService;
    private final VeterinarioService veterinarioService;
    private final ConsultaService consultaService;

    @Autowired
    public HomeController(
            ClienteService clienteService,
            AnimalService animalService,
            VeterinarioService veterinarioService,
            ConsultaService consultaService
    ) {
        this.clienteService = clienteService;
        this.animalService = animalService;
        this.veterinarioService = veterinarioService;
        this.consultaService = consultaService;
    }

    @GetMapping("/")
    public ModelAndView home() {
        ModelAndView mv = new ModelAndView("home");

        // Contagens para dashboard
        long totalClientes = clienteService.findAll().size();
        long totalAnimais = animalService.findAll().size();
        long totalVeterinarios = veterinarioService.findAll().size();
        long totalConsultas = consultaService.findAll().size();

        mv.addObject("totalClientes", totalClientes);
        mv.addObject("totalAnimais", totalAnimais);
        mv.addObject("totalVeterinarios", totalVeterinarios);
        mv.addObject("totalConsultas", totalConsultas);

        return mv;
    }
}
