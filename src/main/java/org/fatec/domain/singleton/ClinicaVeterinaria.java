package org.fatec.domain.singleton;

import org.fatec.domain.Agendamento;
import org.fatec.domain.Animal;
import org.fatec.domain.Consulta;
import org.fatec.domain.Veterinario;
import org.fatec.domain.builder.consulta.ConsultaBuilder;
import org.fatec.domain.builder.consulta.ConsultaCreator;

import java.util.ArrayList;
import java.util.List;

public class ClinicaVeterinaria implements Agendamento {

    // Instância única da clínica
    private static volatile ClinicaVeterinaria instance;

    private final List<Consulta> consultas;

    // Construtor privado para evitar instâncias externas
    private ClinicaVeterinaria() {
        this.consultas = new ArrayList<>();
    }

    // Método para obter a instância única (thread-safe)
    public static ClinicaVeterinaria getInstance() {
        if (instance == null) {
            synchronized (ClinicaVeterinaria.class) {
                if (instance == null) {
                    instance = new ClinicaVeterinaria();
                }
            }
        }
        return instance;
    }

    @Override
    public void agendarConsulta(Animal pet, Veterinario veterinario, String data) {
        // Usando o Builder para criar a consulta
        ConsultaCreator consultaCreator = new ConsultaCreator(new ConsultaBuilder());
        consultaCreator.criarConsulta(pet, veterinario, data, null, null);
        consultas.add(consultaCreator.getConsulta());
    }

    @Override
    public void emitirDiagnostico(Animal pet, String diagnostico) {
        for (Consulta consulta : consultas) {
            if (consulta.getPet().equals(pet)) {
                consulta.setDiagnostico(diagnostico);
                break;
            }
        }
    }

    @Override
    public void prescreverMedicamento(Animal pet, String medicamento) {
        for (Consulta consulta : consultas) {
            if (consulta.getPet().equals(pet)) {
                consulta.setMedicamento(medicamento);
                break;
            }
        }
    }

    @Override
    public void finalizarConsulta(Consulta consulta, String diagnostico, String medicamento) {
        emitirDiagnostico(consulta.getPet(), diagnostico);
        prescreverMedicamento(consulta.getPet(), medicamento);
    }

    public List<Consulta> listarConsultas() {
        return consultas;
    }

    // Método para obter a lista de consultas
    public List<Consulta> getConsultas() {
        return new ArrayList<>(consultas);
    }
}