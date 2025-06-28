package org.fatec.domain.command;

import org.fatec.domain.Consulta;
import org.fatec.service.ConsultaService;

import java.util.List;

/**
 * Comando concreto para buscar consultas por veterinário
 */
public class BuscarConsultasPorVeterinarioCommand implements Command {

    private final ConsultaService consultaService;
    private final String nomeVeterinario;
    private List<Consulta> resultado;

    public BuscarConsultasPorVeterinarioCommand(ConsultaService consultaService, String nomeVeterinario) {
        this.consultaService = consultaService;
        this.nomeVeterinario = nomeVeterinario;
    }

    @Override
    public void execute() {
        this.resultado = consultaService.findByVeterinarioNome(nomeVeterinario);
    }

    /**
     * Retorna o resultado da busca após a execução do comando
     * @return Lista de consultas encontradas
     */
    public List<Consulta> getResultado() {
        return resultado;
    }
}
