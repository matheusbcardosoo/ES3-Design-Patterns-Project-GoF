package org.fatec.domain.adapter;

/**
 * Simulação de um sistema de pagamento externo com uma interface diferente
 */
public class SistemaPagamentoExterno {

    /**
     * Processa um pagamento no sistema externo
     * @param valorTotal o valor a ser pago
     * @param detalhes detalhes da transação
     * @return código de rastreamento da transação
     */
    public String processarTransacao(double valorTotal, String detalhes) {
        // Simulando processamento de pagamento
        String codigoTransacao = "TX-" + Math.round(Math.random() * 10000);
        System.out.println("Sistema Externo: Processando pagamento de R$ " + valorTotal +
                           " - " + detalhes +
                           " - Código: " + codigoTransacao);
        return codigoTransacao;
    }

    /**
     * Consulta o status de uma transação no sistema externo
     * @param codigoRastreamento código da transação
     * @return status atual da transação
     */
    public int consultarTransacao(String codigoRastreamento) {
        // Simulando consulta de status (1 = aprovado, 0 = pendente, -1 = recusado)
        int statusCodigo = (int) Math.round(Math.random()); // Simplificado para 0 ou 1
        System.out.println("Sistema Externo: Consultando transação " + codigoRastreamento +
                           " - Status: " + (statusCodigo == 1 ? "Aprovado" : "Pendente"));
        return statusCodigo;
    }
}
