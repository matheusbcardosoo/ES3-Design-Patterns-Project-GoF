package org.fatec.dao;

import org.fatec.domain.Cliente;
import java.util.List;
import java.util.Optional;

public interface ClienteDAO {
    List<Cliente> findAll();
    Optional<Cliente> findById(String id);
    List<Cliente> findByNomeContainingIgnoreCase(String nome);
    Cliente save(Cliente cliente);
    void deleteById(String id);
}
