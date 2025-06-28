package org.fatec.domain.builder.animal;

import org.fatec.domain.Animal;
import org.fatec.domain.Gato;

public class GatoBuilder implements IAnimalBuilder<Gato> {
    private Gato gato;
    private String nome;
    private String genero;
    private int idade;
    private double peso;
    private String cor;

    public GatoBuilder() {
        this.gato = new Gato();
    }

    public GatoBuilder comNome(String nome) {
        this.nome = nome;
        return this;
    }

    public GatoBuilder comGenero(String genero) {
        this.genero = genero;
        return this;
    }

    public GatoBuilder comIdade(int idade) {
        this.idade = idade;
        return this;
    }

    public GatoBuilder comPeso(double peso) {
        this.peso = peso;
        return this;
    }

    public GatoBuilder comCor(String cor) {
        this.cor = cor;
        return this;
    }

    @Override
    public void setDados() {
        gato.setNome(nome != null ? nome : "");
        gato.setGenero(genero != null ? genero : "");
        gato.setIdade(idade);
        gato.setPeso(peso);
        gato.setCor(cor != null ? cor : "");
    }

    @Override
    public void setEspecie() {
        gato.setEspecie("Felino");
    }

    @Override
    public Gato getAnimal() {
        return gato;
    }
}
