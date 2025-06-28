package org.fatec.domain.builder.animal;

import org.fatec.domain.Animal;
import org.fatec.domain.Cachorro;

public class CachorroBuilder implements IAnimalBuilder<Cachorro> {
    private Cachorro cachorro;
    private String nome;
    private String genero;
    private int idade;
    private double peso;
    private String cor;

    public CachorroBuilder() {
        this.cachorro = new Cachorro();
    }

    public CachorroBuilder comNome(String nome) {
        this.nome = nome;
        return this;
    }

    public CachorroBuilder comGenero(String genero) {
        this.genero = genero;
        return this;
    }

    public CachorroBuilder comIdade(int idade) {
        this.idade = idade;
        return this;
    }

    public CachorroBuilder comPeso(double peso) {
        this.peso = peso;
        return this;
    }

    public CachorroBuilder comCor(String cor) {
        this.cor = cor;
        return this;
    }

    @Override
    public void setDados() {
        cachorro.setNome(nome != null ? nome : "");
        cachorro.setGenero(genero != null ? genero : "");
        cachorro.setIdade(idade);
        cachorro.setPeso(peso);
        cachorro.setCor(cor != null ? cor : "");
    }

    @Override
    public void setEspecie() {
        cachorro.setEspecie("Canino");
    }

    @Override
    public Cachorro getAnimal() {
        return cachorro;
    }
}
