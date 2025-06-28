package org.fatec.domain.strategy.animal;

import org.fatec.domain.Animal;
import org.fatec.domain.strategy.ValidationStrategy;

/**
 * Estratégia para validar se o peso do animal está dentro de limites razoáveis
 */
public class AnimalPesoValidationStrategy implements ValidationStrategy<Animal> {

    private static final double PESO_MINIMO = 0.1; // Peso mínimo em kg
    private static final double PESO_MAXIMO = 150.0; // Peso máximo em kg

    @Override
    public boolean validate(Animal animal) {
        return animal != null && animal.getPeso() >= PESO_MINIMO && animal.getPeso() <= PESO_MAXIMO;
    }

    @Override
    public String getErrorMessage() {
        return "O peso do animal deve estar entre " + PESO_MINIMO + " e " + PESO_MAXIMO + " kg";
    }
}
