package org.fatec.dao;

import org.fatec.domain.Veterinario;
import java.util.List;
import java.util.Optional;

public interface VeterinarioDAO {
    List<Veterinario> findAll();
    Optional<Veterinario> findById(String id);
    List<Veterinario> findByNomeContainingIgnoreCase(String nome);
    Veterinario findByCrmv(String crmv);
    Veterinario save(Veterinario veterinario);
    void deleteById(String id);
}
