package controller;

import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;

import model.Checkin;
import model.CheckinRepositorio;
import model.Checkout;
import model.CheckoutRepositorio;
import model.Pagamento;
import model.Repositorio;
import model.Repositorios;
import model.VagaRepositorio;

public class CheckoutController extends AbstractCrudController<Checkout, view.Checkout, Integer> implements Initializable {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    @FXML private TableView<view.Checkout> tabela;
    @FXML private TableColumn<view.Checkout, Number> idCol;
    @FXML private TableColumn<view.Checkout, String> checkinInfoCol;
    @FXML private TableColumn<view.Checkout, LocalDateTime> saidaCol;
    @FXML private TableColumn<view.Checkout, String> valorCol;

    @FXML private javafx.scene.control.ComboBox<Checkin> checkinCombo;
    @FXML private javafx.scene.control.DatePicker saidaDatePicker;
    @FXML private TextField saidaHoraField;
    @FXML private TextField valorHoraField;
    @FXML private Label valorCalculadoLabel;

    @FXML private javafx.scene.control.ComboBox<String> formaPagamentoCombo;

    @FXML private Button adicionarButton;
    @FXML private Button atualizarButton;
    @FXML private Button deletarButton;
    @FXML private Button confirmarButton;
    @FXML private Button cancelarButton;

    // novo campo: pagamento aprovado?
    private boolean pagamentoAprovado = false;
    private Pagamento pagamentoAprovadoObj;


    private final CheckoutRepositorio repositorio = model.Repositorios.CHECKOUTS;
    private final CheckinRepositorio checkinRepositorio = model.Repositorios.CHECKINS;
    private final VagaRepositorio vagaRepositorio = model.Repositorios.VAGAS;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        confirmarButton.setDisable(true); // só libera após pagamento

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        checkinInfoCol.setCellValueFactory(new PropertyValueFactory<>("checkinInfo"));
        saidaCol.setCellValueFactory(new PropertyValueFactory<>("horarioSaida"));
        valorCol.setCellValueFactory(new PropertyValueFactory<>("valorCalculado"));

        checkinCombo.setConverter(new StringConverter<>() {
            @Override
            public String toString(Checkin checkin) {
                if (checkin == null) return "";
                return checkin.getVeiculo().getPlaca() + " - " + checkin.getVaga().getCodigo();
            }

            @Override
            public Checkin fromString(String string) { return null; }
        });

        recarregarCheckins();

        saidaDatePicker.setValue(LocalDate.now());
        saidaHoraField.setText(LocalTime.now().format(TIME_FORMATTER));
        valorHoraField.setText("10.00");
        atualizarValorCalculado();

        super.adicionarButton = adicionarButton;
        super.atualizarButton = atualizarButton;
        super.deletarButton = deletarButton;
        super.confirmarButton = confirmarButton;
        super.cancelarButton = cancelarButton;

        formaPagamentoCombo.setItems(FXCollections.observableArrayList(
            "Dinheiro", "Cartão", "Pix"
        ));
        formaPagamentoCombo.getSelectionModel().select("Dinheiro");

        super.initialize();
    }

    @Override
    protected Repositorio<Checkout, Integer> getRepositorio() {
        return repositorio;
    }

    @Override
    protected view.Checkout modelToView(Checkout entidade) {
        return new view.Checkout(
                entidade.getId(),
                entidade.getCheckin().getId(),
                entidade.getCheckin().getVeiculo().getPlaca() + " - " + entidade.getCheckin().getVaga().getCodigo(),
                entidade.getHorarioSaida(),
                entidade.getValorCalculado());
    }

    @Override
    protected Checkout viewToModel() {
        Checkout checkout = new Checkout();
        checkout.setCheckin(checkinCombo.getValue());
        checkout.setHorarioSaida(resolveDataHora());
        checkout.setValorCalculado(calcularValor(checkout.getCheckin(), checkout.getHorarioSaida()));
        return checkout;
    }

    private LocalDateTime resolveDataHora() {
        LocalDate data = saidaDatePicker.getValue();
        if (data == null) data = LocalDate.now();
        LocalTime hora;
        try { hora = LocalTime.parse(saidaHoraField.getText(), TIME_FORMATTER); }
        catch (DateTimeParseException e) { hora = LocalTime.now(); }
        return LocalDateTime.of(data, hora);
    }

    private double calcularValor(Checkin checkin, LocalDateTime saida) {
        if (checkin == null || saida == null) return 0d;
        LocalDateTime entrada = checkin.getHorarioEntrada();
        long minutos = Math.max(Duration.between(entrada, saida).toMinutes(), 60);
        double horas = minutos / 60.0;
        double valorHora;
        try { valorHora = Double.parseDouble(valorHoraField.getText().trim()); }
        catch (NumberFormatException e) { valorHora = 0d; }
        double valor = horas * valorHora;
        valorCalculadoLabel.setText(String.format("R$ %.2f", valor));
        return valor;
    }

    @Override
    protected void preencherCampos(view.Checkout item) {
        try {
            Checkout checkout = repositorio.loadFromId(item.getId());
            if (checkout != null) {
                Checkin checkin = checkout.getCheckin();
                garantirCheckinDisponivel(checkin);
                checkinCombo.getSelectionModel().select(checkin);
                saidaDatePicker.setValue(checkout.getHorarioSaida().toLocalDate());
                saidaHoraField.setText(checkout.getHorarioSaida().toLocalTime().format(TIME_FORMATTER));
                valorCalculadoLabel.setText(String.format("R$ %.2f", checkout.getValorCalculado()));
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Não foi possível carregar o checkout selecionado: " + e.getMessage()).show();
        }
    }

    @Override
    protected void limparCampos() {
        recarregarCheckins();
        saidaDatePicker.setValue(LocalDate.now());
        saidaHoraField.setText(LocalTime.now().format(TIME_FORMATTER));
        valorHoraField.setText("10.00");
        valorCalculadoLabel.setText("R$ 0,00");

        pagamentoAprovado = false;
        confirmarButton.setDisable(true);
    }

    @Override
    protected void desabilitarCampos(boolean desabilitado) {
        checkinCombo.setDisable(desabilitado);
        saidaDatePicker.setDisable(desabilitado);
        saidaHoraField.setDisable(desabilitado);
        valorHoraField.setDisable(desabilitado);
    }

    @Override
    protected TableView<view.Checkout> getTabela() { return tabela; }

    @Override
    protected Integer getIdFromViewModel(view.Checkout viewModel) { return viewModel.getId(); }

    @Override
    protected void setIdOnEntity(Checkout entidade, Integer id) { }

    @Override
    protected void beforeCreate(Checkout entidade) throws Exception {
    
        validarObrigatorios(entidade);
    
        if (!pagamentoAprovado) {
            throw new IllegalArgumentException("O pagamento ainda não foi aprovado.");
        }
    
        // checkout será salvo depois, então nada aqui
    }
    

    @Override
    protected void beforeUpdate(Checkout entidade) throws Exception {
        validarObrigatorios(entidade);
        if (!pagamentoAprovado) {
            throw new IllegalArgumentException("O pagamento ainda não foi aprovado.");
        }
    }

    private void validarObrigatorios(Checkout entidade) {
        if (entidade.getCheckin() == null) {
            throw new IllegalArgumentException("Selecione um check-in para finalizar.");
        }
    }

   @Override
protected void afterCreate(Checkout checkoutSalvo) {

    try {
        // vincula o checkout ao pagamento aprovado
        pagamentoAprovadoObj.setCheckout(checkoutSalvo);
        pagamentoAprovadoObj.setValor(checkoutSalvo.getValorCalculado());

        // salva pagamento
        Repositorios.PAGAMENTOS.create(pagamentoAprovadoObj);

    } catch (Exception e) {
        new Alert(Alert.AlertType.ERROR, "Erro ao salvar pagamento: " + e.getMessage()).show();
    }

    // finalizar vaga/checkin
    finalizarCheckin(checkoutSalvo);
}


    @Override
    protected void afterDelete(Checkout entidade) {
        desfazerFinalizacao(entidade);
    }

    private void finalizarCheckin(Checkout checkout) {
        try {
            Checkin checkin = checkout.getCheckin();
            checkin.setFinalizado(true);
            checkinRepositorio.update(checkin);
            vagaRepositorio.atualizarOcupacao(checkin.getVaga(), false);
            recarregarCheckins();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Não foi possível finalizar o check-in: " + e.getMessage()).show();
        }
    }

    private void desfazerFinalizacao(Checkout checkout) {
        try {
            Checkin checkin = checkout.getCheckin();
            checkin.setFinalizado(false);
            checkinRepositorio.update(checkin);
            vagaRepositorio.atualizarOcupacao(checkin.getVaga(), true);
            recarregarCheckins();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Não foi possível desfazer finalização: " + e.getMessage()).show();
        }
    }

    private void recarregarCheckins() {
        checkinCombo.setItems(FXCollections.observableArrayList(checkinRepositorio.listarAtivos()));
    }

    private void garantirCheckinDisponivel(Checkin checkin) {
        if (checkin == null) return;
        boolean presente = checkinCombo.getItems().stream().anyMatch(c -> c.getId() == checkin.getId());
        if (!presente) checkinCombo.getItems().add(checkin);
    }

    private void atualizarValorCalculado() {
        Checkin selecionado = checkinCombo.getValue();
        if (selecionado != null) calcularValor(selecionado, resolveDataHora());
        else valorCalculadoLabel.setText("R$ 0,00");
    }

    @FXML
    private void onHorarioChanged() {
        atualizarValorCalculado();
    }

    private void abrirTelaPagamentoCartao() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/pagamentoCartao.fxml"));
            Parent root = loader.load();
    
            PagamentoCartaoController controller = loader.getController();
    
            // callback agora recebe objeto pagamento, não boolean
            controller.setCallbackPagamento(pagamento -> {
                this.pagamentoAprovado = true;
                this.pagamentoAprovadoObj = pagamento;
                confirmarButton.setDisable(false);
            });
    
            Stage stage = new Stage();
            stage.setTitle("Pagamento - Cartão");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
    
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Erro ao abrir tela de pagamento: " + e.getMessage()).show();
        }
    }

   private void abrirTelaPagamentoDinheiro() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/pagamentoDinheiro.fxml"));
        Parent root = loader.load();

        PagamentoDinheiroController controller = loader.getController();

        // 🔥 Calcula o valor da compra
        double valorCompra = calcularValor(checkinCombo.getValue(), resolveDataHora());

        // 🔥 Envia SOMENTE o valor
        controller.setValorPagamento(valorCompra);

        // Callback (continua igual)
        controller.setCallbackPagamento(pagamento -> {
            this.pagamentoAprovado = true;
            this.pagamentoAprovadoObj = pagamento;
            confirmarButton.setDisable(false);
        });

        Stage stage = new Stage();
        stage.setTitle("Pagamento - Dinheiro");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();

    } catch (Exception e) {
        new Alert(Alert.AlertType.ERROR, "Erro ao abrir tela de pagamento: " + e.getMessage()).show();
    }
}

    
private void abrirTelaPagamentoPix() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/pagamentoPix.fxml"));
        Parent root = loader.load();

        PagamentoPixController controller = loader.getController();

        // 🔥 1 — calcula o valor da compra
        double valorCompra = calcularValor(checkinCombo.getValue(), resolveDataHora());

        // 🔥 2 — envia o valor para o controller
        controller.receberDados(valorCompra);

        // 🔥 3 — callback igual aos outros pagamentos
        controller.setCallbackPagamento(pagamento -> {
            this.pagamentoAprovado = true;
            this.pagamentoAprovadoObj = pagamento;
            confirmarButton.setDisable(false);
        });

        Stage stage = new Stage();
        stage.setTitle("Pagamento - Pix");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();

    } catch (Exception e) {
        new Alert(Alert.AlertType.ERROR, "Erro ao abrir tela de pagamento: " + e.getMessage()).show();
    }
}

    
    

    @FXML
    private void onFormaPagamentoChanged(ActionEvent event) {
        String selected = formaPagamentoCombo.getValue();
    
        if ("Cartão".equals(selected)) {
            abrirTelaPagamentoCartao();
        } 
        else if ("Dinheiro".equals(selected)) {
            abrirTelaPagamentoDinheiro();
        } 
        else if ("Pix".equals(selected)) {
            abrirTelaPagamentoPix();
        }
    }
    

    // 🔥 chamado pela tela de pagamento
   public void onPagamentoConcluido(boolean aprovado) {
    this.pagamentoAprovado = aprovado;
    confirmarButton.setDisable(!aprovado);

    if (!aprovado) {
        new Alert(Alert.AlertType.ERROR, "Pagamento não aprovado.").show();
    }
}
}
