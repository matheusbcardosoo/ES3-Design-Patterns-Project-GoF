package org.fatec.domain.strategy.animal;

import org.fatec.domain.Animal;
import org.fatec.domain.strategy.ValidationStrategy;

/**
 * Estratégia para validar se a idade do animal está dentro de limites razoáveis
 */
public class AnimalIdadeValidationStrategy implements ValidationStrategy<Animal> {

    private static final int IDADE_MAXIMA = 30; // Idade máxima razoável para um animal de estimação

    @Override
    public boolean validate(Animal animal) {
        return animal != null && animal.getIdade() >= 0 && animal.getIdade() <= IDADE_MAXIMA;
    }

    @Override
    public String getErrorMessage() {
        return "A idade do animal deve estar entre 0 e " + IDADE_MAXIMA + " anos";
    }
}
