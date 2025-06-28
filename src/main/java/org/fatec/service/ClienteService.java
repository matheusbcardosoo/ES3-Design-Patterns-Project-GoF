package org.fatec.service;

import org.bson.types.ObjectId;
import org.fatec.dao.ClienteDAO;
import org.fatec.domain.Animal;
import org.fatec.domain.Cliente;
import org.fatec.domain.UserRolesEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ClienteService {

    private final ClienteDAO clienteDAO;
    private final AnimalService animalService;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public ClienteService(ClienteDAO clienteDAO, AnimalService animalService, MongoTemplate mongoTemplate) {
        this.clienteDAO = clienteDAO;
        this.animalService = animalService;
        this.mongoTemplate = mongoTemplate;
    }

    public List<Cliente> findAll() {
        return clienteDAO.findAll();
    }

    public Optional<Cliente> findById(String id) {
        return clienteDAO.findById(id);
    }

    public List<Cliente> findByNome(String nome) {
        return clienteDAO.findByNomeContainingIgnoreCase(nome);
    }

    public Cliente save(Cliente cliente) {
        // Se for um novo cliente (sem ID), garantir que o userRole esteja configurado
        if (cliente.getId() == null || cliente.getId().isEmpty()) {
            if (cliente.getUserRole() == null) {
                cliente.setUserRole(UserRolesEnum.CLIENTE);
            }

            // Gerar um novo ID para o cliente se ele não tiver um
            cliente.setId(new ObjectId().toString());
        }
        return clienteDAO.save(cliente);
    }

    public void deleteById(String id) {
        clienteDAO.deleteById(id);
    }
}
