package controller;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Pagamento;
import model.Repositorio;
import model.enums.StatusPagamento;

public class PagamentoController implements Initializable {

    @FXML private TableView<view.Pagamento> tabela;

    @FXML private TableColumn<view.Pagamento, String> checkoutCol;
    @FXML private TableColumn<view.Pagamento, String> idCol;
    @FXML private TableColumn<view.Pagamento, String> tipoCol;
    @FXML private TableColumn<view.Pagamento, String> valorCol;
    @FXML private TableColumn<view.Pagamento, String> statusCol;
    @FXML private TableColumn<view.Pagamento, String> dataCol;

    @FXML private TextField codigoField;
    @FXML private TextField valorField;
    @FXML private CheckBox statusCheck;

    @FXML private Button atualizarStatusButton;
    @FXML private Button cancelarButton;

    private final model.PagamentoRepositorio repositorio = model.Repositorios.PAGAMENTOS;
    private static final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private Pagamento pagamentoSelecionado;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        tipoCol.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        valorCol.setCellValueFactory(new PropertyValueFactory<>("valor"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        dataCol.setCellValueFactory(new PropertyValueFactory<>("dataPagamento"));
        checkoutCol.setCellValueFactory(new PropertyValueFactory<>("checkoutId"));

        codigoField.setDisable(true);
        valorField.setDisable(true);

        tabela.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, novo) -> {
            if (novo != null) preencherCampos(novo);
        });

        // carregamento inicial
        atualizarTabela();

        // início do auto refresh
        iniciarAutoRefresh();
    }

    // --------------------------------------------
    // NOVO MÉTODO – atualiza a tabela inteira
    // --------------------------------------------
    private void atualizarTabela() {
        try {
            tabela.getItems().setAll(
                    repositorio.loadAll().stream().map(this::modelToView).toList()
            );
        } catch (Exception e) {
            System.err.println("Erro ao atualizar tabela: " + e.getMessage());
        }
    }

    // --------------------------------------------
    // NOVO MÉTODO – auto refresh a cada 3 segundos
    // --------------------------------------------
    private void iniciarAutoRefresh() {
        javafx.animation.Timeline timeline = new javafx.animation.Timeline(
                new javafx.animation.KeyFrame(
                        javafx.util.Duration.seconds(3),
                        e -> atualizarTabela()
                )
        );
        timeline.setCycleCount(javafx.animation.Animation.INDEFINITE);
        timeline.play();
    }

    private view.Pagamento modelToView(Pagamento entidade) {

        int checkoutId = entidade.getCheckout() != null ? entidade.getCheckout().getId() : 0;

        return new view.Pagamento(
                entidade.getId(),
                entidade.getTipo(),
                String.format("R$ %.2f", entidade.getValor()),
                entidade.getStatus().name(),
                entidade.getDataPagamento().format(fmt),
                checkoutId
        );
    }

    private void preencherCampos(view.Pagamento item) {
        try {
            pagamentoSelecionado = repositorio.loadFromId(Integer.parseInt(item.getId()));
            if (pagamentoSelecionado == null) {
                new Alert(Alert.AlertType.ERROR, "Pagamento não encontrado no repositório.").show();
                return;
            }

            codigoField.setText(item.getTipo());
            valorField.setText(item.getValor());
            statusCheck.setSelected(item.getStatus().equals("APROVADO"));

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,
                    "Erro ao carregar os dados do pagamento:\n" + e.getMessage()).show();
        }
    }

    @FXML
    private void onAtualizarStatus() {
        if (pagamentoSelecionado == null) {
            new Alert(Alert.AlertType.WARNING, "Selecione um pagamento.").show();
            return;
        }

        try {
            StatusPagamento novoStatus =
                    statusCheck.isSelected() ? StatusPagamento.APROVADO : StatusPagamento.PENDENTE;

            boolean sucesso = repositorio.updateStatus(pagamentoSelecionado.getId(), novoStatus);

            if (!sucesso) {
                new Alert(Alert.AlertType.ERROR, "Falha ao atualizar o status no banco.").show();
                return;
            }

            pagamentoSelecionado.setStatus(novoStatus);

            atualizarTabela();

            new Alert(Alert.AlertType.INFORMATION, "Status atualizado com sucesso!").show();

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,
                    "Erro ao atualizar o pagamento:\n" + e.getMessage()).show();
        }
    }

    @FXML
    private void onCancelar() {
        tabela.getSelectionModel().clearSelection();
        codigoField.clear();
        valorField.clear();
        statusCheck.setSelected(false);
        pagamentoSelecionado = null;
    }
}
