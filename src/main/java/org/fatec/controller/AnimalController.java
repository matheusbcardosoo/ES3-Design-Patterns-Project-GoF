package org.fatec.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.fatec.domain.Animal;
import org.fatec.domain.Cachorro;
import org.fatec.domain.Cliente;
import org.fatec.domain.Gato;
import org.fatec.service.AnimalService;
import org.fatec.service.ClienteService;
import org.fatec.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/animais")
public class AnimalController {

    private final AnimalService animalService;
    private final ClienteService clienteService;
    private final ValidationService validationService;

    @Autowired
    public AnimalController(AnimalService animalService, ClienteService clienteService, ValidationService validationService) {
        this.animalService = animalService;
        this.clienteService = clienteService;
        this.validationService = validationService;
    }

    @GetMapping
    public ModelAndView listarAnimais() {
        ModelAndView mv = new ModelAndView("animal/lista");
        List<Animal> animais = animalService.findAll();
        mv.addObject("animais", animais);
        return mv;
    }

    @GetMapping("/novo")
    public ModelAndView formNovoAnimal() {
        ModelAndView mv = new ModelAndView("animal/form");

        // Usar uma implementação concreta como padrão em vez da classe abstrata Animal
        mv.addObject("animal", new Cachorro());

        // Adicionando uma lista de tipos de animais disponíveis
        List<String> tiposAnimais = Arrays.asList("Cachorro", "Gato");
        mv.addObject("tiposAnimais", tiposAnimais);

        // Lista de clientes para selecionar o tutor
        List<Cliente> clientes = clienteService.findAll();
        mv.addObject("clientes", clientes);

        return mv;
    }

    @PostMapping
    public ModelAndView salvarAnimal(@RequestParam("tipo") String tipo,
                                     @RequestParam(value = "clienteId", required = false) String clienteId,
                                     HttpServletRequest request,
                                     RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView("redirect:/animais");
        try {
            // Criar a instância concreta com base no tipo selecionado
            Animal animal;
            if ("Cachorro".equals(tipo)) {
                animal = new Cachorro();
            } else if ("Gato".equals(tipo)) {
                animal = new Gato();
            } else {
                throw new IllegalArgumentException("Tipo de animal inválido: " + tipo);
            }

            // Mapear manualmente os campos do formulário para o objeto animal
            String id = request.getParameter("id");
            if (id != null && !id.isEmpty()) {
                animal.setId(id);
            }

            animal.setNome(request.getParameter("nome"));
            animal.setEspecie(request.getParameter("especie"));
            animal.setRaca(request.getParameter("raca"));
            animal.setGenero(request.getParameter("genero"));

            try {
                String idade = request.getParameter("idade");
                if (idade != null && !idade.isEmpty()) {
                    animal.setIdade(Integer.parseInt(idade));
                }

                String peso = request.getParameter("peso");
                if (peso != null && !peso.isEmpty()) {
                    animal.setPeso(Double.parseDouble(peso));
                }
            } catch (NumberFormatException e) {
                redirectAttributes.addFlashAttribute("erro", "Formato de número inválido nos campos idade ou peso");
                return new ModelAndView("redirect:/animais/novo");
            }

            animal.setCor(request.getParameter("cor"));

            // Configurar o proprietário se fornecido
            if (clienteId != null && !clienteId.isEmpty()) {
                Optional<Cliente> clienteOpt = clienteService.findById(clienteId);
                if (clienteOpt.isPresent()) {
                    Cliente cliente = clienteOpt.get();
                    animal.setProprietario(cliente);
                }
            }

            // Aplicar validação usando o padrão Strategy
            List<String> erros = validationService.validateAnimalWithErrors(animal);
            if (!erros.isEmpty()) {
                mv.setViewName("animal/form");
                mv.addObject("animal", animal);
                mv.addObject("erros", erros);
                // Recarregar dados necessários para o formulário
                List<String> tiposAnimais = Arrays.asList("Cachorro", "Gato");
                mv.addObject("tiposAnimais", tiposAnimais);
                List<Cliente> clientes = clienteService.findAll();
                mv.addObject("clientes", clientes);
                return mv;
            }

            // Se chegou aqui, a validação passou
            if (clienteId != null && !clienteId.isEmpty()) {
                Optional<Cliente> clienteOpt = clienteService.findById(clienteId);
                if (clienteOpt.isPresent()) {
                    Cliente cliente = clienteOpt.get();

                    // Primeiro salvar o animal para obter um ID
                    animal = animalService.save(animal);

                    // Adicionar o animal à lista de pets do cliente
                    cliente.adicionarPet(animal);

                    // Salvar o cliente atualizado com o novo pet
                    clienteService.save(cliente);
                }
            } else {
                // Se não tiver proprietário, apenas salvar o animal
                animalService.save(animal);
            }

            redirectAttributes.addFlashAttribute("mensagem", "Animal salvo com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao salvar animal: " + e.getMessage());
            mv.setViewName("redirect:/animais/novo");
        }
        return mv;
    }

    @GetMapping("/{id}")
    public ModelAndView visualizarAnimal(@PathVariable String id, RedirectAttributes redirectAttributes) {
        Optional<Animal> animal = animalService.findById(id);
        ModelAndView mv = new ModelAndView();

        if (animal.isPresent()) {
            mv.setViewName("animal/visualizar");
            mv.addObject("animal", animal.get());
        } else {
            mv.setViewName("redirect:/animais");
            redirectAttributes.addFlashAttribute("erro", "Animal não encontrado");
        }
        return mv;
    }

    @GetMapping("/{id}/editar")
    public ModelAndView formEditarAnimal(@PathVariable String id, RedirectAttributes redirectAttributes) {
        Optional<Animal> animal = animalService.findById(id);
        ModelAndView mv = new ModelAndView();

        if (animal.isPresent()) {
            mv.setViewName("animal/form");
            mv.addObject("animal", animal.get());

            // Lista de clientes para selecionar o tutor
            List<Cliente> clientes = clienteService.findAll();
            mv.addObject("clientes", clientes);
        } else {
            mv.setViewName("redirect:/animais");
            redirectAttributes.addFlashAttribute("erro", "Animal não encontrado");
        }
        return mv;
    }

    @GetMapping("/{id}/excluir")
    public ModelAndView excluirAnimal(@PathVariable String id, RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView("redirect:/animais");
        try {
            animalService.deleteById(id);
            redirectAttributes.addFlashAttribute("mensagem", "Animal excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao excluir animal: " + e.getMessage());
        }
        return mv;
    }

    @GetMapping("/buscar")
    public ModelAndView buscarAnimais(@RequestParam String nome) {
        ModelAndView mv = new ModelAndView("animal/lista");
        List<Animal> animais = animalService.findByNome(nome);
        mv.addObject("animais", animais);
        mv.addObject("termoBusca", nome);
        return mv;
    }
}
