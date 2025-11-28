package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.service.ServicoPagamento;
import model.PagamentoCartao;
import model.Checkout;
import model.enums.StatusPagamento; // << IMPORTANTE

public class PagamentoCartaoController {

    @FXML private TextField numeroCartaoField;
    @FXML private TextField nomeTitularField;
    @FXML private TextField validadeField;
    @FXML private PasswordField cvvField;

    private PagamentoCartao pagamentoCartao;
    private final ServicoPagamento servicoPagamento = new ServicoPagamento();
    private Checkout checkout;
    private double valor;

    // callback para o CheckoutController
    private java.util.function.Consumer<Boolean> callbackPagamento;

    public void setCallbackPagamento(java.util.function.Consumer<Boolean> callback) {
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
                checkout,
                valor,
                numero,
                nome,
                validade,
                cvv
        );

        boolean aprovado = servicoPagamento.realizarPagamento(pagamentoCartao);

        if (aprovado) {
            pagamentoCartao.setStatus(StatusPagamento.APROVADO);
            new Alert(Alert.AlertType.INFORMATION, "Pagamento aprovado!").show();
        } else {
            pagamentoCartao.setStatus(StatusPagamento.RECUSADO);
            new Alert(Alert.AlertType.ERROR, "Pagamento recusado. Tente novamente.").show();
        }

        // retorna ao controller pai
        if (callbackPagamento != null) {
            callbackPagamento.accept(aprovado);
        }

        // fecha a janela atual
        Stage stage = (Stage) numeroCartaoField.getScene().getWindow();
        stage.close();
    }

    // recebe os dados enviados pelo CheckoutController
    public void receberDados(Checkout checkout, double valor) {
        this.checkout = checkout;
        this.valor = valor;
    }
}
