package org.fatec.dao;

import org.fatec.domain.Animal;
import org.fatec.domain.Cachorro;
import org.fatec.domain.Gato;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AnimalDAOImpl implements AnimalDAO {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public AnimalDAOImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Animal> findAll() {
        List<Animal> animais = new ArrayList<>();
        animais.addAll(mongoTemplate.findAll(Gato.class));
        animais.addAll(mongoTemplate.findAll(Cachorro.class));
        // Adicione outras subclasses de Animal aqui se houver
        return animais;
    }

    @Override
    public Optional<Animal> findById(String id) {
        // Tentamos encontrar o animal em cada uma das coleções de subclasses
        Animal animal = mongoTemplate.findById(id, Gato.class);
        if (animal == null) {
            animal = mongoTemplate.findById(id, Cachorro.class);
        }
        // Adicione outras subclasses de Animal aqui se houver
        return Optional.ofNullable(animal);
    }

    @Override
    public List<Animal> findByNomeContainingIgnoreCase(String nome) {
        Query query = new Query();
        query.addCriteria(Criteria.where("nome").regex(nome, "i"));

        List<Animal> animais = new ArrayList<>();
        animais.addAll(mongoTemplate.find(query, Gato.class));
        animais.addAll(mongoTemplate.find(query, Cachorro.class));
        // Adicione outras subclasses de Animal aqui se houver
        return animais;
    }

    @Override
    public Animal save(Animal animal) {
        return mongoTemplate.save(animal);
    }

    @Override
    public void deleteById(String id) {
        // Verificamos qual é o tipo de animal antes de tentar remover
        Animal animal = findById(id).orElse(null);
        if (animal != null) {
            mongoTemplate.remove(animal);
        }
    }
}
