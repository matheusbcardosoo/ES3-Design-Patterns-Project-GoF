package org.fatec.domain.strategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Contexto que utiliza as estratégias de validação
 */
public class ValidationContext<T> {

    private final List<ValidationStrategy<T>> strategies = new ArrayList<>();
    private final List<String> errors = new ArrayList<>();

    /**
     * Adiciona uma estratégia de validação
     * @param strategy Estratégia a ser adicionada
     */
    public void addStrategy(ValidationStrategy<T> strategy) {
        strategies.add(strategy);
    }

    /**
     * Executa todas as estratégias de validação para o objeto
     * @param obj Objeto a ser validado
     * @return true se todas as validações passaram, false caso contrário
     */
    public boolean validate(T obj) {
        errors.clear();
        boolean valid = true;

        for (ValidationStrategy<T> strategy : strategies) {
            if (!strategy.validate(obj)) {
                errors.add(strategy.getErrorMessage());
                valid = false;
            }
        }

        return valid;
    }

    /**
     * Retorna as mensagens de erro das validações que falharam
     * @return Lista de mensagens de erro
     */
    public List<String> getErrors() {
        return new ArrayList<>(errors);
    }
}
