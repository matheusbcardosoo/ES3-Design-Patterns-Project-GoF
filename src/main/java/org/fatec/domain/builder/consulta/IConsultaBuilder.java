package org.fatec.domain.builder.consulta;

import org.fatec.domain.Animal;
import org.fatec.domain.Consulta;
import org.fatec.domain.Veterinario;

public interface IConsultaBuilder {
    void setPet(Animal pet);
    void setVeterinario(Veterinario veterinario);
    void setData(String data);
    void setDiagnostico(String diagnostico);
    void setMedicamento(String medicamento);
    Consulta getConsulta();
}
