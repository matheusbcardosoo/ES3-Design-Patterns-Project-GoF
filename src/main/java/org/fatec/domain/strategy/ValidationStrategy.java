package org.fatec.domain.strategy;

/**
 * Interface que define a estratégia para validação de objetos
 */
public interface ValidationStrategy<T> {

    /**
     * Método para validar um objeto
     * @param obj Objeto a ser validado
     * @return true se o objeto é válido, false caso contrário
     */
    boolean validate(T obj);

    /**
     * Retorna a mensagem de erro caso a validação falhe
     * @return Mensagem de erro
     */
    String getErrorMessage();
}
