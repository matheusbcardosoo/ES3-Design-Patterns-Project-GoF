package org.fatec.service;

import org.fatec.domain.Animal;
import org.fatec.domain.Cliente;
import org.fatec.domain.Pessoa;
import org.fatec.domain.Veterinario;
import org.fatec.domain.strategy.ValidationContext;
import org.fatec.domain.strategy.ValidationFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Serviço responsável por realizar validações nos objetos do domínio
 * usando o padrão Strategy
 */
@Service
public class ValidationService {

    /**
     * Valida um objeto Cliente
     * @param cliente Cliente a ser validado
     * @return true se o cliente é válido, false caso contrário
     */
    public boolean validateCliente(Cliente cliente) {
        ValidationContext<Cliente> validator = ValidationFactory.createClienteValidator();
        return validator.validate(cliente);
    }

    /**
     * Valida um objeto Cliente e retorna as mensagens de erro caso existam
     * @param cliente Cliente a ser validado
     * @return Lista de mensagens de erro ou lista vazia se o cliente for válido
     */
    public List<String> validateClienteWithErrors(Cliente cliente) {
        ValidationContext<Cliente> validator = ValidationFactory.createClienteValidator();
        validator.validate(cliente);
        return validator.getErrors();
    }

    /**
     * Valida um objeto Animal
     * @param animal Animal a ser validado
     * @return true se o animal é válido, false caso contrário
     */
    public boolean validateAnimal(Animal animal) {
        ValidationContext<Animal> validator = ValidationFactory.createAnimalValidator();
        return validator.validate(animal);
    }

    /**
     * Valida um objeto Animal e retorna as mensagens de erro caso existam
     * @param animal Animal a ser validado
     * @return Lista de mensagens de erro ou lista vazia se o animal for válido
     */
    public List<String> validateAnimalWithErrors(Animal animal) {
        ValidationContext<Animal> validator = ValidationFactory.createAnimalValidator();
        validator.validate(animal);
        return validator.getErrors();
    }

    /**
     * Valida um objeto Veterinário
     * @param veterinario Veterinário a ser validado
     * @return true se o veterinário é válido, false caso contrário
     */
    public boolean validateVeterinario(Veterinario veterinario) {
        ValidationContext<Veterinario> validator = ValidationFactory.createVeterinarioValidator();
        return validator.validate(veterinario);
    }

    /**
     * Valida um objeto Veterinário e retorna as mensagens de erro caso existam
     * @param veterinario Veterinário a ser validado
     * @return Lista de mensagens de erro ou lista vazia se o veterinário for válido
     */
    public List<String> validateVeterinarioWithErrors(Veterinario veterinario) {
        ValidationContext<Veterinario> validator = ValidationFactory.createVeterinarioValidator();
        validator.validate(veterinario);
        return validator.getErrors();
    }

    /**
     * Valida o email de uma pessoa
     * @param pessoa Pessoa a ter o email validado
     * @return true se o email é válido, false caso contrário
     */
    public boolean validateEmail(Pessoa pessoa) {
        ValidationContext<Pessoa> validator = ValidationFactory.createEmailValidator();
        return validator.validate(pessoa);
    }

    /**
     * Valida o email de uma pessoa e retorna a mensagem de erro caso exista
     * @param pessoa Pessoa a ter o email validado
     * @return Lista de mensagens de erro ou lista vazia se o email for válido
     */
    public List<String> validateEmailWithErrors(Pessoa pessoa) {
        ValidationContext<Pessoa> validator = ValidationFactory.createEmailValidator();
        validator.validate(pessoa);
        return validator.getErrors();
    }
}
