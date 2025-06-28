package org.fatec.domain.strategy;

import org.fatec.domain.Animal;
import org.fatec.domain.Cliente;
import org.fatec.domain.Pessoa;
import org.fatec.domain.Veterinario;
import org.fatec.domain.strategy.animal.*;
import org.fatec.domain.strategy.cliente.*;
import org.fatec.domain.strategy.pessoa.*;
import org.fatec.domain.strategy.veterinario.*;

/**
 * Factory para criação e configuração de validadores para diferentes tipos de objetos
 */
public class ValidationFactory {

    /**
     * Cria um contexto de validação configurado para Cliente
     * @return ValidationContext para Cliente
     */
    public static ValidationContext<Cliente> createClienteValidator() {
        ValidationContext<Cliente> context = new ValidationContext<>();
        context.addStrategy(new ClienteNomeValidationStrategy());
        context.addStrategy(new ClienteTelefoneValidationStrategy());
        context.addStrategy(new ClienteEnderecoValidationStrategy());
        return context;
    }

    /**
     * Cria um contexto de validação configurado para Animal
     * @return ValidationContext para Animal
     */
    public static ValidationContext<Animal> createAnimalValidator() {
        ValidationContext<Animal> context = new ValidationContext<>();
        context.addStrategy(new AnimalNomeValidationStrategy());
        context.addStrategy(new AnimalIdadeValidationStrategy());
        context.addStrategy(new AnimalPesoValidationStrategy());
        context.addStrategy(new AnimalProprietarioValidationStrategy());
        return context;
    }

    /**
     * Cria um contexto de validação configurado para Veterinário
     * @return ValidationContext para Veterinário
     */
    public static ValidationContext<Veterinario> createVeterinarioValidator() {
        ValidationContext<Veterinario> context = new ValidationContext<>();
        context.addStrategy(new VeterinarioCrmvValidationStrategy());
        return context;
    }

    /**
     * Cria um contexto de validação para email (aplicável a qualquer Pessoa)
     * @return ValidationContext para email
     */
    public static ValidationContext<Pessoa> createEmailValidator() {
        ValidationContext<Pessoa> context = new ValidationContext<>();
        context.addStrategy(new EmailValidationStrategy());
        return context;
    }
}
