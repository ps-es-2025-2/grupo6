package model.service;

public class ServicoPagamento {

    public boolean processarPagamentoCartao(String numero, String nome, String validade, String cvv) {

        // Aqui você implementa sua lógica real de pagamento
        // Por enquanto, vamos simular:

        if (numero.startsWith("4") && cvv.length() == 3) {
            return true; // Simula pagamento aprovado
        }

        return false; // Pagamento negado
    }
}
