package org.fatec.domain.adapter;

/**
 * Adapter para o Sistema de Pagamento Externo
 * Implementa a interface SistemaPagamento e adapta para usar o SistemaPagamentoExterno
 */
public class SistemaPagamentoAdapter implements SistemaPagamento {
    private SistemaPagamentoExterno sistemaPagamentoExterno;

    public SistemaPagamentoAdapter(SistemaPagamentoExterno sistemaPagamentoExterno) {
        this.sistemaPagamentoExterno = sistemaPagamentoExterno;
    }

    @Override
    public void realizarPagamento(double valor, String descricao) {
        // Adapta a chamada para o formato do sistema externo
        String codigoTransacao = sistemaPagamentoExterno.processarTransacao(valor, descricao);
        System.out.println("Adaptador: Pagamento realizado com sucesso. Código: " + codigoTransacao);
    }

    @Override
    public String verificarStatus(String codigoTransacao) {
        // Adapta a chamada de verificação de status e converte o resultado
        int statusCodigo = sistemaPagamentoExterno.consultarTransacao(codigoTransacao);

        String statusTexto;
        switch (statusCodigo) {
            case 1:
                statusTexto = "APROVADO";
                break;
            case 0:
                statusTexto = "PENDENTE";
                break;
            case -1:
                statusTexto = "RECUSADO";
                break;
            default:
                statusTexto = "DESCONHECIDO";
        }

        return "Status da transação " + codigoTransacao + ": " + statusTexto;
    }
}
