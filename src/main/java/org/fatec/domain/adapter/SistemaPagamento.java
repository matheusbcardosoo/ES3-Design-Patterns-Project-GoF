package org.fatec.domain.adapter;

/**
 * Interface para o sistema de pagamento da clínica
 */
public interface SistemaPagamento {
    void realizarPagamento(double valor, String descricao);
    String verificarStatus(String codigoTransacao);
}
