package org.fatec.service;

import org.bson.types.ObjectId;
import org.fatec.dao.ConsultaDAO;
import org.fatec.domain.Consulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConsultaService {

    private final ConsultaDAO consultaDAO;

    @Autowired
    public ConsultaService(ConsultaDAO consultaDAO) {
        this.consultaDAO = consultaDAO;
    }

    public List<Consulta> findAll() {
        return consultaDAO.findAll();
    }

    public Optional<Consulta> findById(String id) {
        return consultaDAO.findById(id);
    }

    public List<Consulta> findByPetNome(String nomePet) {
        return consultaDAO.findByPetNomeContainingIgnoreCase(nomePet);
    }

    public List<Consulta> findByVeterinarioNome(String nomeVet) {
        return consultaDAO.findByVeterinarioNomeContainingIgnoreCase(nomeVet);
    }

    public List<Consulta> findByData(String data) {
        return consultaDAO.findByDataContaining(data);
    }

    public Consulta save(Consulta consulta) {
        // Se for uma nova consulta (sem ID), gerar um novo ID
        if (consulta.getId() == null || consulta.getId().isEmpty()) {
            consulta.setId(new ObjectId().toString());
        }
        return consultaDAO.save(consulta);
    }

    public void deleteById(String id) {
        consultaDAO.deleteById(id);
    }
}
