package org.fatec.domain.factory;

import org.fatec.domain.Cachorro;
import org.fatec.domain.Gato;
import org.fatec.domain.builder.animal.AnimalCreator;
import org.fatec.domain.builder.animal.CachorroBuilder;
import org.fatec.domain.builder.animal.GatoBuilder;

public class ConcreteAnimalFactory extends AnimalFactory{
    @Override
    public IFactory getAnimal(String animal) {
        switch (animal.toLowerCase()) {
            case "cachorro":
                AnimalCreator<Cachorro> cachorroCreator = new AnimalCreator<>(new CachorroBuilder());
                cachorroCreator.criarAnimal();
                return cachorroCreator.getAnimal();
            case "gato":
                AnimalCreator<Gato> gatoCreator = new AnimalCreator<>(new GatoBuilder());
                gatoCreator.criarAnimal();
                return gatoCreator.getAnimal();
            default:
                throw new IllegalArgumentException("Tipo de animal inválido: " + animal);
        }
    }
}
