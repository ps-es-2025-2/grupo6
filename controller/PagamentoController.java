package controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import model.Checkout;
import model.CheckoutRepositorio;
import model.Pagamento;
import model.PagamentoRepositorio;
import model.Repositorio;

public class PagamentoController extends AbstractCrudController<Pagamento, view.Pagamento, Integer> implements Initializable {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    @FXML
    private TableView<view.Pagamento> tabela;
    @FXML
    private TableColumn<view.Pagamento, Number> idCol;
    @FXML
    private TableColumn<view.Pagamento, String> checkoutCol;
    @FXML
    private TableColumn<view.Pagamento, String> metodoCol;
    @FXML
    private TableColumn<view.Pagamento, String> statusCol;
    @FXML
    private TableColumn<view.Pagamento, String> valorCol;
    @FXML
    private TableColumn<view.Pagamento, LocalDateTime> dataCol;

    @FXML
    private ComboBox<Checkout> checkoutCombo;
    @FXML
    private ComboBox<String> metodoCombo;
    @FXML
    private ComboBox<String> statusCombo;
    @FXML
    private DatePicker dataPicker;
    @FXML
    private TextField horaField;
    @FXML
    private Label valorCalculadoLabel;

    @FXML
    private Button adicionarButton;
    @FXML
    private Button atualizarButton;
    @FXML
    private Button deletarButton;
    @FXML
    private Button confirmarButton;
    @FXML
    private Button cancelarButton;

    private final PagamentoRepositorio repositorio = model.Repositorios.PAGAMENTOS;
    private final CheckoutRepositorio checkoutRepositorio = model.Repositorios.CHECKOUTS;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        checkoutCol.setCellValueFactory(new PropertyValueFactory<>("checkoutInfo"));
        metodoCol.setCellValueFactory(new PropertyValueFactory<>("metodo"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        valorCol.setCellValueFactory(new PropertyValueFactory<>("valorTotal"));
        dataCol.setCellValueFactory(new PropertyValueFactory<>("dataPagamento"));

        checkoutCombo.setConverter(new StringConverter<>() {
            @Override
            public String toString(Checkout checkout) {
                if (checkout == null) return "";
                return checkout.getCheckin().getVeiculo().getPlaca() + " - " + checkout.getCheckin().getVaga().getCodigo();
            }

            @Override
            public Checkout fromString(String string) {
                return null;
            }
        });

        metodoCombo.setItems(FXCollections.observableArrayList("Cartão", "PIX", "Dinheiro"));
        statusCombo.setItems(FXCollections.observableArrayList("Pendente", "Pago", "Cancelado"));
        metodoCombo.getSelectionModel().selectFirst();
        statusCombo.getSelectionModel().select("Pago");

        dataPicker.setValue(LocalDate.now());
        horaField.setText(LocalTime.now().format(TIME_FORMATTER));

        carregarCheckoutsDisponiveis();

        super.adicionarButton = adicionarButton;
        super.atualizarButton = atualizarButton;
        super.deletarButton = deletarButton;
        super.confirmarButton = confirmarButton;
        super.cancelarButton = cancelarButton;

        super.initialize();
    }

    @Override
    protected Repositorio<Pagamento, Integer> getRepositorio() {
        return repositorio;
    }

    @Override
    protected view.Pagamento modelToView(Pagamento entidade) {
        return new view.Pagamento(
                entidade.getId(),
                entidade.getCheckout().getCheckin().getVeiculo().getPlaca() + " - " + entidade.getCheckout().getCheckin().getVaga().getCodigo(),
                entidade.getMetodo(),
                entidade.getStatus(),
                entidade.getValorTotal(),
                entidade.getDataPagamento()
        );
    }

    @Override
    protected Pagamento viewToModel() {
        Pagamento pagamento = new Pagamento();
        pagamento.setCheckout(checkoutCombo.getValue());
        pagamento.setMetodo(metodoCombo.getValue());
        pagamento.setStatus(statusCombo.getValue());
        pagamento.setValorTotal(obterValorCheckout());
        pagamento.setDataPagamento(resolveDataHora());
        return pagamento;
    }

    private double obterValorCheckout() {
        Checkout checkout = checkoutCombo.getValue();
        if (checkout == null) return 0d;
        return checkout.getValorCalculado();
    }

    private LocalDateTime resolveDataHora() {
        LocalDate data = dataPicker.getValue();
        if (data == null) data = LocalDate.now();
        LocalTime hora;
        try {
            hora = LocalTime.parse(horaField.getText(), TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            hora = LocalTime.now();
        }
        return LocalDateTime.of(data, hora);
    }

    @Override
    protected void preencherCampos(view.Pagamento item) {
        try {
            Pagamento pagamento = repositorio.loadFromId(item.getId());
            if (pagamento != null) {
                garantirCheckoutDisponivel(pagamento.getCheckout());
                checkoutCombo.getSelectionModel().select(pagamento.getCheckout());
                metodoCombo.getSelectionModel().select(pagamento.getMetodo());
                statusCombo.getSelectionModel().select(pagamento.getStatus());
                dataPicker.setValue(pagamento.getDataPagamento().toLocalDate());
                horaField.setText(pagamento.getDataPagamento().toLocalTime().format(TIME_FORMATTER));
                valorCalculadoLabel.setText(String.format("R$ %.2f", pagamento.getValorTotal()));
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Falha ao preencher campos: " + e.getMessage()).show();
        }
    }

    @Override
    protected void limparCampos() {
        carregarCheckoutsDisponiveis();
        metodoCombo.getSelectionModel().clearSelection();
        statusCombo.getSelectionModel().select("Pago");
        dataPicker.setValue(LocalDate.now());
        horaField.setText(LocalTime.now().format(TIME_FORMATTER));
        valorCalculadoLabel.setText(String.format("R$ %.2f", obterValorCheckout()));
    }

    @Override
    protected void desabilitarCampos(boolean desabilitado) {
        checkoutCombo.setDisable(desabilitado || pendingAction == Action.ATUALIZAR);
        metodoCombo.setDisable(desabilitado);
        statusCombo.setDisable(desabilitado);
        dataPicker.setDisable(desabilitado);
        horaField.setDisable(desabilitado);
    }

    @Override
    protected TableView<view.Pagamento> getTabela() {
        return tabela;
    }

    @Override
    protected Integer getIdFromViewModel(view.Pagamento viewModel) {
        return viewModel.getId();
    }

    @Override
    protected void setIdOnEntity(Pagamento entidade, Integer id) {
        // auto
    }

    @Override
    protected void beforeCreate(Pagamento entidade) throws Exception {
        if (entidade.getCheckout() == null) {
            throw new IllegalArgumentException("Selecione um checkout para registrar o pagamento.");
        }
        if (repositorio.buscarPorCheckout(entidade.getCheckout().getId()) != null) {
            throw new IllegalStateException("Este checkout já possui pagamento registrado.");
        }
    }

    @Override
    protected void afterCreate(Pagamento entidade) {
        carregarCheckoutsDisponiveis();
    }

    @Override
    protected void afterDelete(Pagamento entidade) {
        carregarCheckoutsDisponiveis();
    }

    private void carregarCheckoutsDisponiveis() {
        List<Checkout> todos = checkoutRepositorio.loadAll();
        todos.removeIf(checkout -> repositorio.buscarPorCheckout(checkout.getId()) != null);
        checkoutCombo.setItems(FXCollections.observableArrayList(todos));
        if (!todos.isEmpty()) {
            checkoutCombo.getSelectionModel().selectFirst();
            valorCalculadoLabel.setText(String.format("R$ %.2f", obterValorCheckout()));
        } else {
            checkoutCombo.getSelectionModel().clearSelection();
            valorCalculadoLabel.setText("R$ 0,00");
        }
    }

    private void garantirCheckoutDisponivel(Checkout checkout) {
        if (checkout == null) return;
        boolean presente = checkoutCombo.getItems().stream().anyMatch(c -> c.getId() == checkout.getId());
        if (!presente) {
            checkoutCombo.getItems().add(checkout);
        }
    }

    @FXML
    private void onCheckoutChanged() {
        valorCalculadoLabel.setText(String.format("R$ %.2f", obterValorCheckout()));
    }
}

