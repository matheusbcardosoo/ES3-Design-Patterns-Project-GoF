package org.fatec.domain.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Invocador (Invoker) para o padrão Command
 * Responsável por executar os comandos e manter um histórico das operações
 */
public class CommandInvoker {

    private final Stack<Command> comandosExecutados = new Stack<>();
    private final List<Command> historico = new ArrayList<>();

    /**
     * Executa um comando e o adiciona ao histórico
     * @param command Comando a ser executado
     */
    public void executeCommand(Command command) {
        command.execute();
        comandosExecutados.push(command);
        historico.add(command);
    }

    /**
     * Desfaz o último comando executado
     * @return true se o comando foi desfeito com sucesso, false caso contrário
     */
    public boolean undoLastCommand() {
        if (!comandosExecutados.isEmpty()) {
            Command lastCommand = comandosExecutados.pop();
            try {
                lastCommand.undo();
                return true;
            } catch (UnsupportedOperationException e) {
                // Comando não suporta undo
                return false;
            }
        }
        return false;
    }

    /**
     * Retorna o histórico de comandos executados
     * @return Lista de comandos executados
     */
    public List<Command> getHistorico() {
        return new ArrayList<>(historico);
    }

    /**
     * Limpa o histórico de comandos
     */
    public void limparHistorico() {
        comandosExecutados.clear();
        historico.clear();
    }
}
