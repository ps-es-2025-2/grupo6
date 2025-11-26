package model;

import java.time.LocalDateTime;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "pagamentos")
public class Pagamento {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, canBeNull = false, unique = true, columnName = "checkout_id")
    private Checkout checkout;

    @DatabaseField(canBeNull = false, columnName = "metodo")
    private String metodo;

    @DatabaseField(canBeNull = false, columnName = "status")
    private String status;

    @DatabaseField(canBeNull = false, columnName = "valor_total")
    private double valorTotal;

    @DatabaseField(canBeNull = false, dataType = DataType.SERIALIZABLE, columnName = "data_pagamento")
    private LocalDateTime dataPagamento;

    public Pagamento() {
    }

    public Pagamento(Checkout checkout, String metodo, String status, double valorTotal, LocalDateTime dataPagamento) {
        this.checkout = checkout;
        this.metodo = metodo;
        this.status = status;
        this.valorTotal = valorTotal;
        this.dataPagamento = dataPagamento;
    }

    public int getId() {
        return id;
    }

    public Checkout getCheckout() {
        return checkout;
    }

    public void setCheckout(Checkout checkout) {
        this.checkout = checkout;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public LocalDateTime getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDateTime dataPagamento) {
        this.dataPagamento = dataPagamento;
    }
}

