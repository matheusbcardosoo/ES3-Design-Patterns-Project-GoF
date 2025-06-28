package org.fatec.dao;

import org.fatec.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ConsultaDAOImpl implements ConsultaDAO {

    private final MongoTemplate mongoTemplate;
    private final AnimalDAO animalDAO;
    private final VeterinarioDAO veterinarioDAO;
    private static final String COLLECTION_NAME = "consulta";

    @Autowired
    public ConsultaDAOImpl(MongoTemplate mongoTemplate, AnimalDAO animalDAO, VeterinarioDAO veterinarioDAO) {
        this.mongoTemplate = mongoTemplate;
        this.animalDAO = animalDAO;
        this.veterinarioDAO = veterinarioDAO;
    }

    @Override
    public List<Consulta> findAll() {
        List<Consulta> consultas = mongoTemplate.findAll(Consulta.class, COLLECTION_NAME);
        // Carregando manualmente as referências para cada consulta
        for (Consulta consulta : consultas) {
            carregarReferencias(consulta);
        }
        return consultas;
    }

    @Override
    public Optional<Consulta> findById(String id) {
        Consulta consulta = mongoTemplate.findById(id, Consulta.class, COLLECTION_NAME);
        if (consulta != null) {
            carregarReferencias(consulta);
        }
        return Optional.ofNullable(consulta);
    }

    @Override
    public List<Consulta> findByPetNomeContainingIgnoreCase(String nomePet) {
        // Como o pet é uma referência, precisamos encontrar primeiro os pets que correspondem
        // ao critério e depois buscar as consultas que os referenciam
        List<Animal> animais = animalDAO.findByNomeContainingIgnoreCase(nomePet);
        List<Consulta> resultados = new ArrayList<>();

        for (Animal animal : animais) {
            Query query = new Query(Criteria.where("pet.$id").is(animal.getId()));
            List<Consulta> consultasParaAnimal = mongoTemplate.find(query, Consulta.class, COLLECTION_NAME);
            for (Consulta consulta : consultasParaAnimal) {
                carregarReferencias(consulta);
                resultados.add(consulta);
            }
        }

        return resultados;
    }

    @Override
    public List<Consulta> findByVeterinarioNomeContainingIgnoreCase(String nomeVet) {
        // Similar ao método acima, mas para veterinários
        List<Veterinario> veterinarios = veterinarioDAO.findByNomeContainingIgnoreCase(nomeVet);
        List<Consulta> resultados = new ArrayList<>();

        for (Veterinario veterinario : veterinarios) {
            Query query = new Query(Criteria.where("veterinario.$id").is(veterinario.getId()));
            List<Consulta> consultasParaVeterinario = mongoTemplate.find(query, Consulta.class, COLLECTION_NAME);
            for (Consulta consulta : consultasParaVeterinario) {
                carregarReferencias(consulta);
                resultados.add(consulta);
            }
        }

        return resultados;
    }

    @Override
    public List<Consulta> findByDataContaining(String data) {
        Query query = new Query();
        query.addCriteria(Criteria.where("data").regex(data));
        List<Consulta> consultas = mongoTemplate.find(query, Consulta.class, COLLECTION_NAME);
        for (Consulta consulta : consultas) {
            carregarReferencias(consulta);
        }
        return consultas;
    }

    @Override
    public Consulta save(Consulta consulta) {
        // Antes de salvar, garantir que as referências sejam tratadas corretamente
        if (consulta.getPet() != null) {
            String petId = consulta.getPet().getId();
            if (petId != null && !petId.isEmpty()) {
                // Verificar se o animal existe e qual é o seu tipo
                Animal petCompleto = null;

                // Primeiro tentamos buscar como Gato
                petCompleto = mongoTemplate.findById(petId, Gato.class);
                if (petCompleto != null) {
                    // É um Gato - Criamos uma referência do tipo Gato
                    Gato gatoRef = new Gato();
                    gatoRef.setId(petId);
                    consulta.setPet(gatoRef);
                    System.out.println("Salvando consulta com referência a GATO: " + petId);
                } else {
                    // Tentamos como Cachorro
                    petCompleto = mongoTemplate.findById(petId, Cachorro.class);
                    if (petCompleto != null) {
                        // É um Cachorro - Criamos uma referência do tipo Cachorro
                        Cachorro cachorroRef = new Cachorro();
                        cachorroRef.setId(petId);
                        consulta.setPet(cachorroRef);
                        System.out.println("Salvando consulta com referência a CACHORRO: " + petId);
                    } else {
                        System.out.println("ALERTA: Pet com ID " + petId + " não encontrado em nenhuma coleção!");
                    }
                }
            }
        }

        // Tratamento do veterinário
        if (consulta.getVeterinario() != null) {
            String vetId = consulta.getVeterinario().getId();
            if (vetId != null && !vetId.isEmpty()) {
                // Verifica se o veterinário existe
                Veterinario vetCompleto = mongoTemplate.findById(vetId, Veterinario.class, "veterinario");
                if (vetCompleto != null) {
                    // Cria uma referência limpa
                    Veterinario vetRef = new Veterinario();
                    vetRef.setId(vetId);
                    consulta.setVeterinario(vetRef);
                    System.out.println("Salvando consulta com referência a VETERINÁRIO: " + vetId);
                } else {
                    System.out.println("ALERTA: Veterinário com ID " + vetId + " não encontrado!");
                }
            }
        }

        // Salva a consulta com as referências adequadas
        return mongoTemplate.save(consulta, COLLECTION_NAME);
    }

    @Override
    public void deleteById(String id) {
        Consulta consulta = mongoTemplate.findById(id, Consulta.class, COLLECTION_NAME);
        if (consulta != null) {
            mongoTemplate.remove(consulta, COLLECTION_NAME);
        }
    }

    /**
     * Método auxiliar para carregar as referências (pet e veterinário) para uma consulta
     */
    private void carregarReferencias(Consulta consulta) {
        if (consulta.getPet() != null && consulta.getPet().getId() != null) {
            String petId = consulta.getPet().getId();
            Animal pet = null;

            // Verifica se o pet já tem tipo definido
            if (consulta.getPet() instanceof Gato) {
                // Se já sabemos que é um Gato, buscamos diretamente na coleção de gatos
                pet = mongoTemplate.findById(petId, Gato.class);
                System.out.println("Buscando gato pelo ID: " + petId);
            } else if (consulta.getPet() instanceof Cachorro) {
                // Se já sabemos que é um Cachorro, buscamos diretamente na coleção de cachorros
                pet = mongoTemplate.findById(petId, Cachorro.class);
                System.out.println("Buscando cachorro pelo ID: " + petId);
            } else {
                // Se não sabemos o tipo, tentamos primeiro como Gato e depois como Cachorro
                pet = mongoTemplate.findById(petId, Gato.class);
                if (pet == null) {
                    pet = mongoTemplate.findById(petId, Cachorro.class);
                    System.out.println("Buscando cachorro (fallback) pelo ID: " + petId);
                } else {
                    System.out.println("Buscando gato (fallback) pelo ID: " + petId);
                }
            }

            // Atualiza o pet na consulta
            if (pet != null) {
                consulta.setPet(pet);
                System.out.println("Pet encontrado e carregado: " + pet.getNome() + " (Tipo: " + pet.getClass().getSimpleName() + ")");
            } else {
                System.out.println("ALERTA: Pet com ID " + petId + " não encontrado em nenhuma coleção!");
            }
        } else {
            System.out.println("ALERTA: Referência a pet nula ou sem ID na consulta: " + consulta.getId());
        }

        if (consulta.getVeterinario() != null && consulta.getVeterinario().getId() != null) {
            // Carrega o veterinário pelo ID
            Veterinario veterinario = mongoTemplate.findById(consulta.getVeterinario().getId(), Veterinario.class, "veterinario");
            consulta.setVeterinario(veterinario);

            if (veterinario == null) {
                System.out.println("ALERTA: Veterinário com ID " + consulta.getVeterinario().getId() + " não encontrado!");
            } else {
                System.out.println("Veterinário encontrado e carregado: " + veterinario.getNome());
            }
        }
    }
}
