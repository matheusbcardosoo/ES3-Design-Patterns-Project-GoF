package org.fatec.dao;

import org.fatec.domain.Veterinario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class VeterinarioDAOImpl implements VeterinarioDAO {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public VeterinarioDAOImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Veterinario> findAll() {
        return mongoTemplate.findAll(Veterinario.class);
    }

    @Override
    public Optional<Veterinario> findById(String id) {
        Veterinario veterinario = mongoTemplate.findById(id, Veterinario.class);
        return Optional.ofNullable(veterinario);
    }

    @Override
    public List<Veterinario> findByNomeContainingIgnoreCase(String nome) {
        Query query = new Query();
        query.addCriteria(Criteria.where("nome").regex(nome, "i"));
        return mongoTemplate.find(query, Veterinario.class);
    }

    @Override
    public Veterinario findByCrmv(String crmv) {
        Query query = new Query();
        query.addCriteria(Criteria.where("crmv").is(crmv));
        return mongoTemplate.findOne(query, Veterinario.class);
    }

    @Override
    public Veterinario save(Veterinario veterinario) {
        return mongoTemplate.save(veterinario);
    }

    @Override
    public void deleteById(String id) {
        Veterinario veterinario = mongoTemplate.findById(id, Veterinario.class);
        if (veterinario != null) {
            mongoTemplate.remove(veterinario);
        }
    }
}

