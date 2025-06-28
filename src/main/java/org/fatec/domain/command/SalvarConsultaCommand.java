package org.fatec.domain.command;

import org.fatec.domain.Consulta;
import org.fatec.service.ConsultaService;

import java.util.Optional;

/**
 * Comando concreto para salvar uma consulta
 */
public class SalvarConsultaCommand implements Command {

    private final ConsultaService consultaService;
    private final Consulta consulta;
    private Consulta consultaSalva;
    private Consulta estadoAnterior;
    private boolean isNovaConsulta;

    public SalvarConsultaCommand(ConsultaService consultaService, Consulta consulta) {
        this.consultaService = consultaService;
        this.consulta = consulta;
    }

    @Override
    public void execute() {
        // Verificar se é uma nova consulta ou uma edição
        if (consulta.getId() != null && !consulta.getId().isEmpty()) {
            // É uma edição, portanto armazene o estado anterior
            Optional<Consulta> optConsulta = consultaService.findById(consulta.getId());
            if (optConsulta.isPresent()) {
                this.estadoAnterior = optConsulta.get();
                this.isNovaConsulta = false;
            } else {
                this.isNovaConsulta = true;
            }
        } else {
            // É uma nova consulta
            this.isNovaConsulta = true;
        }

        // Salvar a consulta
        this.consultaSalva = consultaService.save(consulta);
    }

    @Override
    public void undo() {
        if (consultaSalva != null && consultaSalva.getId() != null) {
            if (isNovaConsulta) {
                // Se era uma nova consulta, podemos deletá-la
                consultaService.deleteById(consultaSalva.getId());
            } else if (estadoAnterior != null) {
                // Se era uma edição, restauramos o estado anterior
                consultaService.save(estadoAnterior);
            }
        }
    }

    public Consulta getConsultaSalva() {
        return consultaSalva;
    }
}
