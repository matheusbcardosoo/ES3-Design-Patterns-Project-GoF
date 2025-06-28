package org.fatec.domain;

public interface Agendamento {
    void agendarConsulta(Animal pet, Veterinario veterinario, String data);
    void emitirDiagnostico(Animal pet, String diagnostico);
    void prescreverMedicamento(Animal pet, String medicamento);
    void finalizarConsulta(Consulta consulta, String diagnostico, String medicamento);
}