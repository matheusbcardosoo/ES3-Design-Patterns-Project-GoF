package org.fatec.dao;

import org.fatec.domain.Consulta;
import java.util.List;
import java.util.Optional;

public interface ConsultaDAO {
    List<Consulta> findAll();
    Optional<Consulta> findById(String id);
    List<Consulta> findByPetNomeContainingIgnoreCase(String nomePet);
    List<Consulta> findByVeterinarioNomeContainingIgnoreCase(String nomeVet);
    List<Consulta> findByDataContaining(String data);
    Consulta save(Consulta consulta);
    void deleteById(String id);
}
