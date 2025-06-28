package org.fatec.domain.builder.animal;

import org.fatec.domain.Animal;

public class AnimalCreator <T extends Animal> {
    private IAnimalBuilder<T> builder;

    public AnimalCreator(IAnimalBuilder<T> builder) {
        this.builder = builder;
    }

    public void criarAnimal() {
        builder.setDados();
        builder.setEspecie();
    }

    public T getAnimal() {
        return builder.getAnimal();
    }

}