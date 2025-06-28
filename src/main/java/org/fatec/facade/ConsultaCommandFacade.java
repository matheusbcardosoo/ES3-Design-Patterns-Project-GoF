package org.fatec.facade;

import org.fatec.domain.Animal;
import org.fatec.domain.Consulta;
import org.fatec.domain.Veterinario;
import org.fatec.domain.command.*;
import org.fatec.service.AnimalService;
import org.fatec.service.ConsultaService;
import org.fatec.service.VeterinarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Fachada (Facade) para operações de consultas usando o padrão Command
 */
@Component
public class ConsultaCommandFacade {

    private final ConsultaService consultaService;
    private final AnimalService animalService;
    private final VeterinarioService veterinarioService;
    private final CommandInvoker commandInvoker;

    @Autowired
    public ConsultaCommandFacade(
            ConsultaService consultaService,
            AnimalService animalService,
            VeterinarioService veterinarioService) {
        this.consultaService = consultaService;
        this.animalService = animalService;
        this.veterinarioService = veterinarioService;
        this.commandInvoker = new CommandInvoker();
    }

    /**
     * Agenda uma nova consulta utilizando o padrão Command
     * @param consulta Consulta a ser agendada
     * @return Consulta salva
     */
    public Consulta agendarConsulta(Consulta consulta) {
        SalvarConsultaCommand command = new SalvarConsultaCommand(consultaService, consulta);
        commandInvoker.executeCommand(command);
        return command.getConsultaSalva();
    }

    /**
     * Salva uma consulta utilizando o padrão Command
     * @param consulta Consulta a ser salva
     * @param petId ID do animal
     * @param veterinarioId ID do veterinário
     * @return Consulta salva
     */
    public Consulta salvarConsulta(Consulta consulta, String petId, String veterinarioId) {
        // Associar animal e veterinário
        if (petId != null && !petId.isEmpty()) {
            Optional<Animal> animalOpt = animalService.findById(petId);
            if (animalOpt.isPresent()) {
                consulta.setPet(animalOpt.get());
            }
        }

        if (veterinarioId != null && !veterinarioId.isEmpty()) {
            Optional<Veterinario> vetOpt = veterinarioService.findById(veterinarioId);
            if (vetOpt.isPresent()) {
                consulta.setVeterinario(vetOpt.get());
            }
        }

        // Criar e executar o comando
        SalvarConsultaCommand command = new SalvarConsultaCommand(consultaService, consulta);
        commandInvoker.executeCommand(command);

        return command.getConsultaSalva();
    }

    /**
     * Cancela uma consulta utilizando o padrão Command
     * @param consultaId ID da consulta a ser cancelada
     */
    public void cancelarConsulta(String consultaId) {
        CancelarConsultaCommand command = new CancelarConsultaCommand(consultaService, consultaId);
        commandInvoker.executeCommand(command);
    }

    /**
     * Atualiza o diagnóstico de uma consulta utilizando o padrão Command
     * @param consultaId ID da consulta
     * @param novoDiagnostico Novo diagnóstico
     */
    public void atualizarDiagnostico(String consultaId, String novoDiagnostico) {
        AtualizarDiagnosticoCommand command = new AtualizarDiagnosticoCommand(
                consultaService, consultaId, novoDiagnostico);
        commandInvoker.executeCommand(command);
    }

    /**
     * Busca consultas por pet utilizando o padrão Command
     * @param nomePet Nome do pet
     * @return Lista de consultas
     */
    public List<Consulta> buscarConsultasPorPet(String nomePet) {
        BuscarConsultasPorPetCommand command = new BuscarConsultasPorPetCommand(consultaService, nomePet);
        commandInvoker.executeCommand(command);
        return command.getResultado();
    }

    /**
     * Busca consultas por veterinário utilizando o padrão Command
     * @param nomeVeterinario Nome do veterinário
     * @return Lista de consultas
     */
    public List<Consulta> buscarConsultasPorVeterinario(String nomeVeterinario) {
        BuscarConsultasPorVeterinarioCommand command = new BuscarConsultasPorVeterinarioCommand(
                consultaService, nomeVeterinario);
        commandInvoker.executeCommand(command);
        return command.getResultado();
    }

    /**
     * Busca consultas por data utilizando o padrão Command
     * @param data Data das consultas (formato: dd/MM/yyyy)
     * @return Lista de consultas
     */
    public List<Consulta> buscarConsultasPorData(String data) {
        BuscarConsultasPorDataCommand command = new BuscarConsultasPorDataCommand(
                consultaService, data);
        commandInvoker.executeCommand(command);
        return command.getResultado();
    }

    /**
     * Desfaz a última operação realizada
     * @return true se a operação foi desfeita com sucesso, false caso contrário
     */
    public boolean desfazerUltimaOperacao() {
        return commandInvoker.undoLastCommand();
    }

    /**
     * Retorna o histórico de comandos executados
     * @return Lista de comandos executados
     */
    public List<Command> getHistoricoComandos() {
        return commandInvoker.getHistorico();
    }
}
