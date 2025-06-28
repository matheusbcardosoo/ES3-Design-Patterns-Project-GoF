package org.fatec.domain;

import lombok.Getter;
import lombok.Setter;
import org.fatec.domain.factory.IFactory;

@Getter
@Setter
public class Gato extends Animal implements IFactory {

    // Construtor sem argumentos requerido pelo MongoDB
    public Gato() {
        super();
    }

    public Gato(String nome, String especie, String genero, int idade, double peso, String cor) {
        super();
        this.nome = nome;
        this.especie = especie;
        this.genero = genero;
        this.idade = idade;
        this.peso = peso;
        this.cor = cor;
    }
}
