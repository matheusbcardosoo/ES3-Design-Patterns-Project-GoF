package org.fatec.dao;

import org.fatec.domain.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ClienteDAOImpl implements ClienteDAO {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public ClienteDAOImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Cliente> findAll() {
        return mongoTemplate.findAll(Cliente.class);
    }

    @Override
    public Optional<Cliente> findById(String id) {
        Cliente cliente = mongoTemplate.findById(id, Cliente.class);
        return Optional.ofNullable(cliente);
    }

    @Override
    public List<Cliente> findByNomeContainingIgnoreCase(String nome) {
        Query query = new Query();
        query.addCriteria(Criteria.where("nome").regex(nome, "i"));
        return mongoTemplate.find(query, Cliente.class);
    }

    @Override
    public Cliente save(Cliente cliente) {
        return mongoTemplate.save(cliente);
    }

    @Override
    public void deleteById(String id) {
        Cliente cliente = mongoTemplate.findById(id, Cliente.class);
        if (cliente != null) {
            mongoTemplate.remove(cliente);
        }
    }
}

