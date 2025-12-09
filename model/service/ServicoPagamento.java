package model.service;

import model.Pagamento;

public class ServicoPagamento {

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
