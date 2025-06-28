package org.fatec.domain.factory;

import org.fatec.domain.*;
import org.fatec.domain.builder.pessoa.*;

public class ConcretePessoaFactory extends PessoaFactory {
    @Override
    public IFactory getUser(UserRolesEnum userRole) {
        switch (userRole) {
            case CLIENTE:
                PessoaCreator<Cliente> clienteCreator = new PessoaCreator<>(new ClienteBuilder());
                clienteCreator.criarPessoa();
                return clienteCreator.getPessoa();

            case VETERINARIO:
                PessoaCreator<Veterinario> veterinarioCreator = new PessoaCreator<>(new VeterinarioBuilder());
                veterinarioCreator.criarPessoa();
                return veterinarioCreator.getPessoa();

            default:
                throw new IllegalArgumentException("Tipo de usuário inválido: " + userRole);
        }
    }
}
