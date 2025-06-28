package org.fatec.domain.command;

import org.fatec.domain.Consulta;
import org.fatec.service.ConsultaService;

import java.util.List;

/**
 * Comando concreto para buscar consultas por data
 */
public class BuscarConsultasPorDataCommand implements Command {

    private final ConsultaService consultaService;
    private final String data;
    private List<Consulta> resultado;

    public BuscarConsultasPorDataCommand(ConsultaService consultaService, String data) {
        this.consultaService = consultaService;
        this.data = data;
    }

    @Override
    public void execute() {
        this.resultado = consultaService.findByData(data);
    }

    /**
     * Retorna o resultado da busca após a execução do comando
     * @return Lista de consultas encontradas
     */
    public List<Consulta> getResultado() {
        return resultado;
    }
}
