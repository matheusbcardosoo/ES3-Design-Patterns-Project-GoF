package org.fatec.domain.command;

import org.fatec.domain.Consulta;
import org.fatec.service.ConsultaService;

import java.util.Optional;

/**
 * Comando concreto para atualizar o diagnóstico de uma consulta
 */
public class AtualizarDiagnosticoCommand implements Command {

    private final ConsultaService consultaService;
    private final String consultaId;
    private final String novoDiagnostico;
    private String diagnosticoAnterior;

    public AtualizarDiagnosticoCommand(ConsultaService consultaService,
                                     String consultaId,
                                     String novoDiagnostico) {
        this.consultaService = consultaService;
        this.consultaId = consultaId;
        this.novoDiagnostico = novoDiagnostico;
    }

    @Override
    public void execute() {
        Optional<Consulta> consultaOpt = consultaService.findById(consultaId);
        if (consultaOpt.isPresent()) {
            Consulta consulta = consultaOpt.get();
            // Guardar diagnóstico antigo para undo
            diagnosticoAnterior = consulta.getDiagnostico();
            // Atualizar com novo diagnóstico
            consulta.setDiagnostico(novoDiagnostico);
            consultaService.save(consulta);
        } else {
            throw new IllegalArgumentException("Consulta não encontrada com ID: " + consultaId);
        }
    }

    @Override
    public void undo() {
        if (diagnosticoAnterior != null) {
            Optional<Consulta> consultaOpt = consultaService.findById(consultaId);
            if (consultaOpt.isPresent()) {
                Consulta consulta = consultaOpt.get();
                consulta.setDiagnostico(diagnosticoAnterior);
                consultaService.save(consulta);
            }
        }
    }
}
