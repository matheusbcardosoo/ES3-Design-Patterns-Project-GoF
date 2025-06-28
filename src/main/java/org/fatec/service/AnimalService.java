package org.fatec.service;

import org.fatec.dao.AnimalDAO;
import org.fatec.domain.Animal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnimalService {

    private final AnimalDAO animalDAO;

    @Autowired
    public AnimalService(AnimalDAO animalDAO) {
        this.animalDAO = animalDAO;
    }

    public List<Animal> findAll() {
        return animalDAO.findAll();
    }

    public Optional<Animal> findById(String id) {
        return animalDAO.findById(id);
    }

    public List<Animal> findByNome(String nome) {
        return animalDAO.findByNomeContainingIgnoreCase(nome);
    }

    public Animal save(Animal animal) {
        return animalDAO.save(animal);
    }

    public void deleteById(String id) {
        animalDAO.deleteById(id);
    }
}
