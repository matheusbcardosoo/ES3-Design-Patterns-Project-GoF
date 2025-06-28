package org.fatec.domain.strategy.veterinario;

import org.fatec.domain.Veterinario;
import org.fatec.domain.strategy.ValidationStrategy;

/**
 * Estratégia para validar se o CRMV do veterinário está preenchido corretamente
 */
public class VeterinarioCrmvValidationStrategy implements ValidationStrategy<Veterinario> {

    @Override
    public boolean validate(Veterinario veterinario) {
        return veterinario != null &&
               veterinario.getCrmv() != null &&
               !veterinario.getCrmv().trim().isEmpty();
    }

    @Override
    public String getErrorMessage() {
        return "O CRMV do veterinário deve ser preenchido";
    }
}
