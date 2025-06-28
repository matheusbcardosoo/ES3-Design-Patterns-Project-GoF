package org.fatec.domain.builder.pessoa;

import org.fatec.domain.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteBuilder implements IPessoaBuilder<Cliente> {
    private Cliente cliente;
    private String nome;
    private Telefone telefone;
    private Endereco endereco;
    private List<Animal> pets = new ArrayList<>();

    public ClienteBuilder() {
        this.cliente = new Cliente();
    }

    public ClienteBuilder comNome(String nome) {
        this.nome = nome;
        return this;
    }

    public ClienteBuilder comTelefone(String ddd, String numero) {
        this.telefone = new Telefone(ddd, numero);
        return this;
    }

    public ClienteBuilder comEndereco(Endereco endereco) {
        this.endereco = endereco;
        return this;
    }

    public ClienteBuilder adicionarPet(Animal pet) {
        if (this.pets == null) {
            this.pets = new ArrayList<>();
        }
        this.pets.add(pet);
        return this;
    }

    @Override
    public void setDadosPessoais() {
        cliente.setNome(nome != null ? nome : "");
        cliente.setTelefone(telefone != null ? telefone : new Telefone("", ""));
        cliente.setEndereco(endereco);
    }

    @Override
    public void setRole() {
        cliente.setUserRole(UserRolesEnum.CLIENTE);
    }

    @Override
    public void setPets() {
        cliente.setPets(pets);
    }

    @Override
    public Cliente getPessoa() {
        return cliente;
    }
}
