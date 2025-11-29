package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import model.PagamentoDinheiro;
import model.service.ServicoPagamento;
import model.Checkout;
import model.enums.StatusPagamento;

public class PagamentoDinheiroController {

    @FXML private TextField valorPagamentoField;
    @FXML private TextField valorRecebidoField;
    @FXML private TextField trocoField;

    private final ServicoPagamento servicoPagamento = new ServicoPagamento();

    private Checkout checkout;
    private double valor;        // valor da compra
    private double valorRecebido; 
    private double troco;

    // callback para o CheckoutController
    private java.util.function.Consumer<PagamentoDinheiro> callbackPagamento;

    public void setCallbackPagamento(java.util.function.Consumer<PagamentoDinheiro> callback) {
        this.callbackPagamento = callback;
    }

    // -------------------------------------------------------------------
    // Recebendo dados do Checkout (valor total da compra)
    // -------------------------------------------------------------------
    public void receberDados(double valor) {
        this.valor = valor;
        valorPagamentoField.setText(String.format("%.2f", valor));
    }

    // -------------------------------------------------------------------
    // Calcula troco em tempo real
    // -------------------------------------------------------------------
    @FXML
    private void calcularTroco() {
        try {
            String recebidoStr = valorRecebidoField.getText().replace(",", ".");

            if (recebidoStr.isEmpty()) {
                trocoField.setText("");
                return;
            }

            valorRecebido = Double.parseDouble(recebidoStr);
            troco = valorRecebido - valor;

            if (troco < 0) {
                trocoField.setText("Valor insuficiente");
            } else {
                trocoField.setText(String.format("%.2f", troco));
            }

        } catch (NumberFormatException e) {
            trocoField.setText("Inválido");
        }
    }

    // -------------------------------------------------------------------
    // Confirmar o pagamento
    // -------------------------------------------------------------------
    @FXML
    private void confirmarPagamento() {

        if (valorRecebidoField.getText().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Digite o valor recebido!").show();
            return;
        }

        try {
            valorRecebido = Double.parseDouble(valorRecebidoField.getText().replace(",", "."));
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Valor recebido inválido!").show();
            return;
        }

        if (valorRecebido < valor) {
            new Alert(Alert.AlertType.WARNING, "Valor insuficiente para pagamento!").show();
            return;
        }

        // Criar objeto de pagamento
        PagamentoDinheiro pagamento = new PagamentoDinheiro(
                checkout,
                valor,
                valorRecebido,
                troco
        );

        // Processamento pelo serviço
        boolean aprovado = servicoPagamento.realizarPagamento(pagamento);
        pagamento.setStatus(
                aprovado ? StatusPagamento.APROVADO : StatusPagamento.RECUSADO
        );

        if (!aprovado) {
            new Alert(Alert.AlertType.ERROR, "Erro ao processar pagamento.").show();
            return;
        }

        new Alert(Alert.AlertType.INFORMATION, "Pagamento realizado com sucesso!").show();

        // retorna o pagamento ao CheckoutController
        if (callbackPagamento != null) {
            callbackPagamento.accept(pagamento);
        }

        Stage stage = (Stage) valorPagamentoField.getScene().getWindow();
        stage.close();
    }

    public void setValorPagamento(double valor) {
        valorPagamentoField.setText(String.format("%.2f", valor));
        this.valor = valor; // se quiser guardar para cálculo do troco
    }
    

    // -------------------------------------------------------------------
    // Cancelar
    // -------------------------------------------------------------------
    @FXML
    private void cancelar() {
        Stage stage = (Stage) valorPagamentoField.getScene().getWindow();
        stage.close();
    }
}
