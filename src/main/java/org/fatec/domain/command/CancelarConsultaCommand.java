package org.fatec.domain.command;

import org.fatec.service.ConsultaService;

/**
 * Comando concreto para cancelar uma consulta
 */
public class CancelarConsultaCommand implements Command {

    private final ConsultaService consultaService;
    private final String consultaId;
    private Object consultaOriginal;

    public CancelarConsultaCommand(ConsultaService consultaService, String consultaId) {
        this.consultaService = consultaService;
        this.consultaId = consultaId;
    }

    @Override
    public void execute() {
        // Opcionalmente, armazenar o estado original para possibilitar a operação undo
        consultaOriginal = consultaService.findById(consultaId).orElse(null);
        consultaService.deleteById(consultaId);
    }

    @Override
    public void undo() {
        // Reimplementar para utilizar a consulta original se necessário
        throw new UnsupportedOperationException("Não é possível desfazer um cancelamento de consulta");
    }
}
