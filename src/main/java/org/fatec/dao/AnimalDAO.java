package org.fatec.dao;

import org.fatec.domain.Animal;
import java.util.List;
import java.util.Optional;

public interface AnimalDAO {
    List<Animal> findAll();
    Optional<Animal> findById(String id);
    List<Animal> findByNomeContainingIgnoreCase(String nome);
    Animal save(Animal animal);
    void deleteById(String id);
}
