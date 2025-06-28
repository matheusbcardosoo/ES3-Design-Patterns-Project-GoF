package org.fatec.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document
public abstract class Animal {
    @Id
    protected String id;
    protected String nome;
    protected String especie;
    protected String raca;
    protected String genero;
    protected int idade;
    protected double peso;
    protected String cor;

    @DBRef
    protected Cliente proprietario;

    public void consultarVeterinario() {
        System.out.println(nome + " está em consulta.");
    }
}
