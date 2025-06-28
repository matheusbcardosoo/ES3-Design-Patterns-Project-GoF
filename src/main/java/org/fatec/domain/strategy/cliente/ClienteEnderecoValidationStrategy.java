package org.fatec.domain.strategy.cliente;

import org.fatec.domain.Cliente;
import org.fatec.domain.strategy.ValidationStrategy;

/**
 * Estratégia para validar se o endereço do cliente está preenchido corretamente
 */
public class ClienteEnderecoValidationStrategy implements ValidationStrategy<Cliente> {

    @Override
    public boolean validate(Cliente cliente) {
        return cliente != null &&
               cliente.getEndereco() != null &&
               cliente.getEndereco().getLogradouro() != null &&
               !cliente.getEndereco().getLogradouro().trim().isEmpty() &&
               cliente.getEndereco().getNumero() != null &&
               !cliente.getEndereco().getNumero().trim().isEmpty() &&
               !cliente.getEndereco().getNumero().equals("0");
    }

    @Override
    public String getErrorMessage() {
        return "O endereço do cliente deve ser preenchido corretamente (rua e número são obrigatórios)";
    }
}
