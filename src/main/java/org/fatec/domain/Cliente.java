package org.fatec.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.fatec.domain.factory.IFactory;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "clientes")
public class Cliente extends Pessoa implements IFactory {
    private Endereco endereco;

    @DBRef(lazy = false)
    private List<Animal> pets;

    public Cliente (String nome, Telefone telefone, UserRolesEnum userRole, Endereco endereco, List<Animal> pets) {
        super(nome, telefone, userRole);
        this.endereco = endereco;
        this.pets = pets;
    }

    // Método auxiliar para adicionar um pet à lista
    public void adicionarPet(Animal pet) {
        if (this.pets == null) {
            this.pets = new ArrayList<>();
        }
        if (!this.pets.contains(pet)) {
            this.pets.add(pet);
        }
    }
}
