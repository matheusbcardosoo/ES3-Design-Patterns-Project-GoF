package org.fatec.domain.strategy.pessoa;

import org.fatec.domain.Pessoa;
import org.fatec.domain.strategy.ValidationStrategy;
import java.util.regex.Pattern;

/**
 * Estratégia para validar se o email está em formato válido
 */
public class EmailValidationStrategy implements ValidationStrategy<Pessoa> {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    @Override
    public boolean validate(Pessoa pessoa) {
        if (pessoa == null || pessoa.getEmail() == null || pessoa.getEmail().trim().isEmpty()) {
            return false;
        }

        return EMAIL_PATTERN.matcher(pessoa.getEmail()).matches();
    }

    @Override
    public String getErrorMessage() {
        return "O email está em formato inválido";
    }
}
