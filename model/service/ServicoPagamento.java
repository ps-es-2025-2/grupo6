package model.service;

import model.Pagamento;

public class ServicoPagamento {

    /**
     * Recebe um Pagamento genérico e chama o método polimórfico processarPagamento()
     */
    public boolean realizarPagamento(Pagamento pagamento) {

        if (pagamento == null)
            return false;

        // chama o método correto da classe filha:
        // - PagamentoCartao.processarPagamento()
        // - PagamentoPix.processarPagamento()
        // - PagamentoDinheiro.processarPagamento()
        return pagamento.processarPagamento();
    }
}
