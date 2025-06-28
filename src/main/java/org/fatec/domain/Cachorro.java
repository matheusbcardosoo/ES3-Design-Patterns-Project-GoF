package org.fatec.domain;

import lombok.Getter;
import lombok.Setter;
import org.fatec.domain.factory.IFactory;

@Getter
@Setter
public class Cachorro extends Animal implements IFactory {

    // Construtor sem argumentos requerido pelo MongoDB
    public Cachorro() {
        super();
    }

    public Cachorro(String nome, String genero, int idade, double peso, String cor) {
        super();
        this.nome = nome;
        this.especie = "Canino";
        this.genero = genero;
        this.idade = idade;
        this.peso = peso;
        this.cor = cor;
    }
}
