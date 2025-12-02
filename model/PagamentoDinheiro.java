package model;

import com.j256.ormlite.table.DatabaseTable;

import model.enums.StatusPagamento;

@DatabaseTable(tableName = "pagamentos")
public class PagamentoDinheiro extends Pagamento {

    private double valorRecebido;
    private double troco;

    public PagamentoDinheiro() {}

    public PagamentoDinheiro(Checkout checkout, double valor, double valorRecebido, double troco) {
        super(checkout, "DINHEIRO", valor);
        this.valorRecebido = valorRecebido;
        this.troco = troco;
    }

    @Override
    public boolean processarPagamento() {

        if (valorRecebido < this.valor) {
            this.status = StatusPagamento.RECUSADO;
            return false;
        }

        this.status = StatusPagamento.APROVADO;
        return true;
    }

    public double getValorRecebido() {
        return valorRecebido;
    }

    public double getTroco() {
        return troco;
    }

    public void setValorRecebido(double valorRecebido) {
        this.valorRecebido = valorRecebido;
    }

    public void setTroco(double troco) {
        this.troco = troco;
    }
}
