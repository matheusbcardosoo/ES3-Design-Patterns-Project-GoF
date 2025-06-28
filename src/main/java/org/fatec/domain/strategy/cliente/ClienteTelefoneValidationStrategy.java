package org.fatec.domain.strategy.cliente;

import org.fatec.domain.Cliente;
import org.fatec.domain.strategy.ValidationStrategy;

/**
 * Estratégia para validar se o telefone do cliente está preenchido corretamente
 */
public class ClienteTelefoneValidationStrategy implements ValidationStrategy<Cliente> {

    @Override
    public boolean validate(Cliente cliente) {
        return cliente != null &&
               cliente.getTelefone() != null &&
               cliente.getTelefone().getNumero() != null &&
               !cliente.getTelefone().getNumero().trim().isEmpty();
    }

    @Override
    public String getErrorMessage() {
        return "O telefone do cliente deve ser preenchido corretamente";
    }
}
