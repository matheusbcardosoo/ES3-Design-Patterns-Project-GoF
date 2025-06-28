package org.fatec.domain.builder.consulta;

import org.fatec.domain.Animal;
import org.fatec.domain.Veterinario;
import org.fatec.domain.Consulta;

public class ConsultaBuilder implements IConsultaBuilder {
    private Consulta consulta;


    @Override
    public void setPet(Animal pet) {
        consulta = new Consulta();
        consulta.setPet(pet);
    }

    @Override
    public void setVeterinario(Veterinario veterinario) {
        consulta.setVeterinario(veterinario);
    }

    @Override
    public void setData(String data) {
        consulta.setData("08/04/2025 - 10:00");
    }

    @Override
    public void setDiagnostico(String diagnostico) {
        consulta.setDiagnostico(diagnostico);
    }

    @Override
    public void setMedicamento(String medicamento) {
        consulta.setMedicamento(medicamento);
    }

    @Override
    public Consulta getConsulta() {
        return consulta;
    }
}
