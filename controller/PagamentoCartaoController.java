package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.service.ServicoPagamento;

public class PagamentoCartaoController {

    @FXML private TextField numeroCartaoField;
    @FXML private TextField nomeTitularField;
    @FXML private TextField validadeField;
    @FXML private PasswordField cvvField;

    private final ServicoPagamento servicoPagamento = new ServicoPagamento();

    @FXML
    private void onConfirmarPagamento() {
        String numero = numeroCartaoField.getText();
        String nome = nomeTitularField.getText();
        String validade = validadeField.getText();
        String cvv = cvvField.getText();

        //⚠ Validação simples
        if (numero.isEmpty() || nome.isEmpty() || validade.isEmpty() || cvv.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Preencha todos os campos!").show();
            return;
        }

        boolean sucesso = servicoPagamento.processarPagamentoCartao(
                numero, nome, validade, cvv
        );

        if (sucesso) {
            new Alert(Alert.AlertType.INFORMATION,
                    "Pagamento realizado com sucesso!")
                    .show();

            voltarParaCheckout();
        } else {
            new Alert(Alert.AlertType.ERROR,
                    "Erro ao processar pagamento. Tente novamente.")
                    .show();

            voltarParaCheckout();
        }
    }

    private void voltarParaCheckout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/checkout.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) numeroCartaoField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Checkout");
            stage.show();

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,
                    "Erro ao voltar para checkout: " + e.getMessage())
                    .show();
        }
    }
}
