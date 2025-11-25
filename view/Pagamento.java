package view;

import java.time.LocalDateTime;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class Pagamento {

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty checkoutInfo = new SimpleStringProperty();
    private final StringProperty metodo = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final StringProperty valorTotal = new SimpleStringProperty();
    private final ObjectProperty<LocalDateTime> dataPagamento = new SimpleObjectProperty<>();

    public Pagamento(int id, String checkoutInfo, String metodo, String status, double valorTotal, LocalDateTime dataPagamento) {
        this.id.set(id);
        this.checkoutInfo.set(checkoutInfo);
        this.metodo.set(metodo);
        this.status.set(status);
        this.valorTotal.set(String.format("%.2f", valorTotal));
        this.dataPagamento.set(dataPagamento);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getCheckoutInfo() {
        return checkoutInfo.get();
    }

    public StringProperty checkoutInfoProperty() {
        return checkoutInfo;
    }

    public String getMetodo() {
        return metodo.get();
    }

    public StringProperty metodoProperty() {
        return metodo;
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public String getValorTotal() {
        return valorTotal.get();
    }

    public StringProperty valorTotalProperty() {
        return valorTotal;
    }

    public LocalDateTime getDataPagamento() {
        return dataPagamento.get();
    }

    public ObjectProperty<LocalDateTime> dataPagamentoProperty() {
        return dataPagamento;
    }
}

