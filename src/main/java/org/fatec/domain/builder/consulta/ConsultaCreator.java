package org.fatec.domain.builder.consulta;

import org.fatec.domain.Animal;
import org.fatec.domain.Consulta;
import org.fatec.domain.Veterinario;

public class ConsultaCreator {
    private IConsultaBuilder consulta;

    public ConsultaCreator(IConsultaBuilder consulta) {
        this.consulta = consulta;
    }

    public void criarConsulta(Animal pet, Veterinario veterinario, String data, String diagnostico, String medicamento) {
        consulta.setPet(pet);
        consulta.setVeterinario(veterinario);
        consulta.setData(data);
    }

    public Consulta getConsulta() {
        return consulta.getConsulta();
    }
}
