package org.fatec.domain.strategy.animal;

import org.fatec.domain.Animal;
import org.fatec.domain.strategy.ValidationStrategy;

/**
 * Estratégia para validar se o animal possui um proprietário válido
 */
public class AnimalProprietarioValidationStrategy implements ValidationStrategy<Animal> {

    @Override
    public boolean validate(Animal animal) {
        return animal != null &&
               animal.getProprietario() != null &&
               animal.getProprietario().getId() != null &&
               !animal.getProprietario().getId().trim().isEmpty();
    }

    @Override
    public String getErrorMessage() {
        return "O animal deve estar associado a um proprietário válido";
    }
}
