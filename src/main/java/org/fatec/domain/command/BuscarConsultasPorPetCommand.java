package org.fatec.domain.command;

import org.fatec.domain.Consulta;
import org.fatec.service.ConsultaService;

import java.util.List;

/**
 * Comando concreto para buscar consultas por pet
 */
public class BuscarConsultasPorPetCommand implements Command {

    private final ConsultaService consultaService;
    private final String nomePet;
    private List<Consulta> resultado;

    public BuscarConsultasPorPetCommand(ConsultaService consultaService, String nomePet) {
        this.consultaService = consultaService;
        this.nomePet = nomePet;
    }

    @Override
    public void execute() {
        this.resultado = consultaService.findByPetNome(nomePet);
    }

    /**
     * Retorna o resultado da busca após a execução do comando
     * @return Lista de consultas encontradas
     */
    public List<Consulta> getResultado() {
        return resultado;
    }
}
