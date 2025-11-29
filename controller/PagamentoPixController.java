package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import model.PagamentoPix;
import model.service.ServicoPagamento;
import model.Checkout;
import model.enums.StatusPagamento;

import java.util.UUID;
import java.util.function.Consumer;

public class PagamentoPixController {

    @FXML private TextField valorPagamentoField;
    @FXML private TextArea pixCodeTextArea;

    private final ServicoPagamento servicoPagamento = new ServicoPagamento();

    private Checkout checkout;
    private double valor;          // valor total da compra
    private String codigoPix;      // código PIX gerado

    // callback para o CheckoutController
    private Consumer<PagamentoPix> callbackPagamento;

    public void setCallbackPagamento(Consumer<PagamentoPix> callback) {
        this.callbackPagamento = callback;
    }

    // -------------------------------------------------------------------
    // Recebe dados do checkout (valor total)
    // -------------------------------------------------------------------
    public void receberDados(double valor) {
        this.valor = valor;
        valorPagamentoField.setText(String.format("%.2f", valor));

        gerarCodigoPix();
    }

    // -------------------------------------------------------------------
    // Geração do código PIX fictício
    // -------------------------------------------------------------------
    private void gerarCodigoPix() {
        // Código simples simulando padrão BR Code
        this.codigoPix = "00020126360014BR.GOV.BCB.PIX0123"
                + UUID.randomUUID().toString().replace("-", "")
                + "5204000053039865406" + valor;

        pixCodeTextArea.setText(this.codigoPix);
    }

    // -------------------------------------------------------------------
    // Confirmar pagamento PIX
    // -------------------------------------------------------------------
    @FXML
    private void confirmarPagamento() {

        if (codigoPix == null || codigoPix.isBlank()) {
            new Alert(Alert.AlertType.ERROR, "Código PIX inválido!").show();
            return;
        }

        // Criar objeto de pagamento
        PagamentoPix pagamento = new PagamentoPix(
                checkout,
                valor,
                codigoPix
        );

        // Processamento do pagamento
        boolean aprovado = servicoPagamento.realizarPagamento(pagamento);
        pagamento.setStatus(
                aprovado ? StatusPagamento.APROVADO : StatusPagamento.RECUSADO
        );

        if (!aprovado) {
            new Alert(Alert.AlertType.ERROR, "Erro ao processar pagamento PIX.").show();
            return;
        }

        new Alert(Alert.AlertType.INFORMATION, "Pagamento PIX aprovado!").show();

        // Retorna ao CheckoutController
        if (callbackPagamento != null) {
            callbackPagamento.accept(pagamento);
        }

        Stage stage = (Stage) valorPagamentoField.getScene().getWindow();
        stage.close();
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
