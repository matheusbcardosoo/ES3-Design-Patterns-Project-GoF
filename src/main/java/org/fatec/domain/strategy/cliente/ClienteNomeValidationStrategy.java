package org.fatec.domain.strategy.cliente;

import org.fatec.domain.Cliente;
import org.fatec.domain.strategy.ValidationStrategy;

/**
 * Estratégia para validar se o nome do cliente não está vazio
 */
public class ClienteNomeValidationStrategy implements ValidationStrategy<Cliente> {

    @Override
    public boolean validate(Cliente cliente) {
        return cliente != null && cliente.getNome() != null && !cliente.getNome().trim().isEmpty();
    }

    @Override
    public String getErrorMessage() {
        return "O nome do cliente não pode estar vazio";
    }
}
