package org.fatec.domain.builder.animal;

import org.fatec.domain.Animal;

public interface IAnimalBuilder <T extends Animal> {
    void setDados();
    void setEspecie();
    T getAnimal();
}
