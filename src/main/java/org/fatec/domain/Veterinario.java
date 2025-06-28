package org.fatec.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.fatec.domain.factory.IFactory;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Veterinario extends Pessoa implements IFactory {
    private String crmv;
    private String especialidade;

    public Veterinario (String nome, Telefone telefone, UserRolesEnum userRole, String crmv) {
        super(nome, telefone, userRole);
        this.crmv = crmv;
    }
}
