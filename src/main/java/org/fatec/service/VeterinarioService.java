package org.fatec.service;

import org.bson.types.ObjectId;
import org.fatec.dao.VeterinarioDAO;
import org.fatec.domain.UserRolesEnum;
import org.fatec.domain.Veterinario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VeterinarioService {

    private final VeterinarioDAO veterinarioDAO;

    @Autowired
    public VeterinarioService(VeterinarioDAO veterinarioDAO) {
        this.veterinarioDAO = veterinarioDAO;
    }

    public List<Veterinario> findAll() {
        return veterinarioDAO.findAll();
    }

    public Optional<Veterinario> findById(String id) {
        return veterinarioDAO.findById(id);
    }

    public List<Veterinario> findByNome(String nome) {
        return veterinarioDAO.findByNomeContainingIgnoreCase(nome);
    }

    public Veterinario findByCrmv(String crmv) {
        return veterinarioDAO.findByCrmv(crmv);
    }

    public Veterinario save(Veterinario veterinario) {
        // Se for um novo veterinário (sem ID), garantir que o userRole esteja configurado
        if (veterinario.getId() == null || veterinario.getId().isEmpty()) {
            if (veterinario.getUserRole() == null) {
                veterinario.setUserRole(UserRolesEnum.VETERINARIO);
            }

            // Gerar um novo ID para o veterinário se ele não tiver um
            veterinario.setId(new ObjectId().toString());
        }
        return veterinarioDAO.save(veterinario);
    }

    public void deleteById(String id) {
        veterinarioDAO.deleteById(id);
    }
}
