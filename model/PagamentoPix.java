package model;

import com.j256.ormlite.table.DatabaseTable;

import model.enums.StatusPagamento;

@DatabaseTable(tableName = "pagamentos")
public class PagamentoPix extends Pagamento {

    private String codigoPix;

    public PagamentoPix() {}

    public PagamentoPix(Checkout checkout, double valor, String codigoPix) {
        super(checkout, "PIX", valor);
        this.codigoPix = codigoPix;
    }

    @Override
    public boolean processarPagamento() {

        // Aqui você pode implementar validações reais.
        // Por enquanto, PIX sempre será aprovado se o código não estiver vazio.

        if (codigoPix == null || codigoPix.isBlank()) {
            this.status = StatusPagamento.RECUSADO;
            return false;
        }

        this.status = StatusPagamento.APROVADO;
        return true;
    }

    // -----------------------------------------------------
    // Getters e Setters
    // -----------------------------------------------------
    public String getCodigoPix() {
        return codigoPix;
    }

    public void setCodigoPix(String codigoPix) {
        this.codigoPix = codigoPix;
    }
}
