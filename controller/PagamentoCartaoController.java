package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Checkout;
import model.PagamentoCartao;
import model.enums.StatusPagamento;
import model.service.ServicoPagamento;

public class PagamentoCartaoController {

    @FXML private TextField numeroCartaoField;
    @FXML private TextField nomeTitularField;
    @FXML private TextField validadeField;
    @FXML private PasswordField cvvField;

    private PagamentoCartao pagamentoCartao;
    private final ServicoPagamento servicoPagamento = new ServicoPagamento();
    private Checkout checkout;
    private double valor;

    private java.util.function.Consumer<PagamentoCartao> callbackPagamento;

    public void setCallbackPagamento(java.util.function.Consumer<PagamentoCartao> callback) {
        this.callbackPagamento = callback;
    }

    @FXML
    private void onConfirmarPagamento() {

        String numero = numeroCartaoField.getText();
        String nome = nomeTitularField.getText();
        String validade = validadeField.getText();
        String cvv = cvvField.getText();

        if (numero.isEmpty() || nome.isEmpty() || validade.isEmpty() || cvv.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Preencha todos os campos!").show();
            return;
        }

        pagamentoCartao = new PagamentoCartao(
                null,
                0,
                numero,
                nome,
                validade,
                cvv
        );

        boolean aprovado = servicoPagamento.realizarPagamento(pagamentoCartao);
        pagamentoCartao.setStatus(
                aprovado ? StatusPagamento.APROVADO : StatusPagamento.RECUSADO
        );

        if (!aprovado) {
            new Alert(Alert.AlertType.ERROR, "Pagamento recusado. Tente novamente.").show();
            return;
        }

        new Alert(Alert.AlertType.INFORMATION, "Pagamento aprovado!").show();

        if (callbackPagamento != null) {
            callbackPagamento.accept(pagamentoCartao);
        }

        Stage stage = (Stage) numeroCartaoField.getScene().getWindow();
        stage.close();
    }

    public void receberDados(Checkout checkout, double valor) {
        this.checkout = checkout;
        this.valor = valor;
    }
}
