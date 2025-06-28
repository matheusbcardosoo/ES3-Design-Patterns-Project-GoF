package org.fatec.domain.command;

/**
 * Interface base para o padrão Command
 */
public interface Command {

    /**
     * Executa a operação encapsulada pelo comando
     */
    void execute();

    /**
     * Desfaz a operação executada pelo comando (opcional)
     */
    default void undo() {
        throw new UnsupportedOperationException("Operação não suporta desfazer");
    }
}
