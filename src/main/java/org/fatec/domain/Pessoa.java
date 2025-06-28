package org.fatec.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document
public abstract class Pessoa {
    @Id
    private String id;
    private String nome;
    private Telefone telefone;
    private UserRolesEnum userRole;
    private String email;
    private String cpf;

    public Pessoa(String nome, Telefone telefone, UserRolesEnum userRole) {
        this.nome = nome;
        this.telefone = telefone;
        this.userRole = userRole;
    }

    public Pessoa(String nome, Telefone telefone, UserRolesEnum userRole, String email) {
        this.nome = nome;
        this.telefone = telefone;
        this.userRole = userRole;
        this.email = email;
    }
}
