package org.fatec.domain.strategy.animal;

import org.fatec.domain.Animal;
import org.fatec.domain.strategy.ValidationStrategy;

/**
 * Estratégia para validar se o nome do animal não está vazio
 */
public class AnimalNomeValidationStrategy implements ValidationStrategy<Animal> {

    @Override
    public boolean validate(Animal animal) {
        return animal != null && animal.getNome() != null && !animal.getNome().trim().isEmpty();
    }

    @Override
    public String getErrorMessage() {
        return "O nome do animal não pode estar vazio";
    }
}
