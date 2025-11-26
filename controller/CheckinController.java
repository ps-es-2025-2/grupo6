package controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Checkin;
import model.CheckinRepositorio;
import model.Repositorio;
import model.Vaga;
import model.VagaRepositorio;
import model.Veiculo;
import model.VeiculoRepositorio;

public class CheckinController extends AbstractCrudController<Checkin, view.Checkin, Integer> implements Initializable {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    @FXML
    private TableView<view.Checkin> tabela;
    @FXML
    private TableColumn<view.Checkin, String> idCol;
    @FXML
    private TableColumn<view.Checkin, String> placaCol;
    @FXML
    private TableColumn<view.Checkin, String> vagaCol;
    @FXML
    private TableColumn<view.Checkin, LocalDateTime> entradaCol;
    @FXML
    private TableColumn<view.Checkin, String> observacaoCol;

    @FXML
    private ComboBox<Veiculo> veiculoCombo;
    @FXML
    private ComboBox<Vaga> vagaCombo;
    @FXML
    private javafx.scene.control.DatePicker entradaDatePicker;
    @FXML
    private TextField entradaHoraField;
    @FXML
    private TextArea observacaoArea;

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

    private final CheckinRepositorio repositorio = model.Repositorios.CHECKINS;
    private final VeiculoRepositorio veiculoRepositorio = model.Repositorios.VEICULOS;
    private final VagaRepositorio vagaRepositorio = model.Repositorios.VAGAS;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        placaCol.setCellValueFactory(new PropertyValueFactory<>("placa"));
        vagaCol.setCellValueFactory(new PropertyValueFactory<>("vaga"));
        entradaCol.setCellValueFactory(new PropertyValueFactory<>("horarioEntrada"));
        observacaoCol.setCellValueFactory(new PropertyValueFactory<>("observacao"));

        entradaDatePicker.setValue(LocalDate.now());
        entradaHoraField.setText(LocalTime.now().format(TIME_FORMATTER));

        veiculoCombo.setItems(FXCollections.observableArrayList(veiculoRepositorio.loadAll()));
        vagaCombo.setItems(FXCollections.observableArrayList(vagaRepositorio.listarLivres()));

        super.adicionarButton = adicionarButton;
        super.atualizarButton = atualizarButton;
        super.deletarButton = deletarButton;
        super.confirmarButton = confirmarButton;
        super.cancelarButton = cancelarButton;

        super.initialize();
    }

    @Override
    protected Repositorio<Checkin, Integer> getRepositorio() {
        return repositorio;
    }

    @Override
    protected view.Checkin modelToView(Checkin entidade) {
        return new view.Checkin(
                entidade.getId(),
                entidade.getVeiculo().getPlaca(),
                entidade.getVaga().getCodigo(),
                entidade.getHorarioEntrada(),
                entidade.getObservacao()
        );
    }

    @Override
    protected Checkin viewToModel() {
        Checkin checkin = new Checkin();
        checkin.setVeiculo(veiculoCombo.getValue());
        checkin.setVaga(vagaCombo.getValue());
        checkin.setHorarioEntrada(resolveDataHora());
        checkin.setObservacao(observacaoArea.getText());
        return checkin;
    }

    private LocalDateTime resolveDataHora() {
        LocalDate data = entradaDatePicker.getValue();
        if (data == null) {
            data = LocalDate.now();
        }
        LocalTime hora;
        try {
            hora = LocalTime.parse(entradaHoraField.getText(), TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            hora = LocalTime.now();
        }
        return LocalDateTime.of(data, hora);
    }

    @Override
    protected void preencherCampos(view.Checkin item) {
        veiculoCombo.getSelectionModel().select(
                veiculoCombo.getItems().stream()
                        .filter(v -> v.getPlaca().equals(item.getPlaca()))
                        .findFirst().orElse(null));
        Vaga vagaSelecionada = vagaRepositorio.loadAll().stream()
                .filter(v -> v.getCodigo().equals(item.getVaga()))
                .findFirst().orElse(null);
        garantirVagaDisponivel(vagaSelecionada);
        vagaCombo.getSelectionModel().select(
                vagaCombo.getItems().stream()
                        .filter(v -> v.getCodigo().equals(item.getVaga()))
                        .findFirst().orElse(null));
        entradaDatePicker.setValue(item.getHorarioEntrada().toLocalDate());
        entradaHoraField.setText(item.getHorarioEntrada().toLocalTime().format(TIME_FORMATTER));
        observacaoArea.setText(item.getObservacao());
    }

    @Override
    protected void limparCampos() {
        veiculoCombo.getSelectionModel().clearSelection();
        vagaCombo.getSelectionModel().clearSelection();
        entradaDatePicker.setValue(LocalDate.now());
        entradaHoraField.setText(LocalTime.now().format(TIME_FORMATTER));
        observacaoArea.clear();
        atualizarListasAuxiliares();
    }

    @Override
    protected void desabilitarCampos(boolean desabilitado) {
        veiculoCombo.setDisable(desabilitado);
        vagaCombo.setDisable(desabilitado || pendingAction == Action.ATUALIZAR);
        entradaDatePicker.setDisable(desabilitado);
        entradaHoraField.setDisable(desabilitado);
        observacaoArea.setDisable(desabilitado);
    }

    @Override
    protected TableView<view.Checkin> getTabela() {
        return tabela;
    }

    @Override
    protected Integer getIdFromViewModel(view.Checkin viewModel) {
        return Integer.parseInt(viewModel.getId());
    }

    @Override
    protected void setIdOnEntity(Checkin entidade, Integer id) {
        // o ID é auto gerado
    }

    @Override
    protected void beforeCreate(Checkin entidade) throws Exception {
        validarCamposObrigatorios(entidade);
    }

    @Override
    protected void beforeUpdate(Checkin entidade) throws Exception {
        validarCamposObrigatorios(entidade);
    }

    private void validarCamposObrigatorios(Checkin entidade) {
        if (entidade.getVeiculo() == null) {
            throw new IllegalArgumentException("Selecione um veículo para o check-in.");
        }
        if (entidade.getVaga() == null) {
            throw new IllegalArgumentException("Selecione uma vaga disponível.");
        }
    }

    @Override
    protected void afterCreate(Checkin entidade) {
        try {
            vagaRepositorio.atualizarOcupacao(entidade.getVaga(), true);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Falha ao marcar vaga ocupada: " + e.getMessage()).show();
        }
        atualizarListasAuxiliares();
    }

    @Override
    protected void afterDelete(Checkin entidade) {
        try {
            if (!entidade.isFinalizado()) {
                vagaRepositorio.atualizarOcupacao(entidade.getVaga(), false);
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Falha ao liberar vaga: " + e.getMessage()).show();
        }
        atualizarListasAuxiliares();
    }

    private void atualizarListasAuxiliares() {
        veiculoCombo.setItems(FXCollections.observableArrayList(veiculoRepositorio.loadAll()));
        vagaCombo.setItems(FXCollections.observableArrayList(vagaRepositorio.listarLivres()));
    }

    private void garantirVagaDisponivel(Vaga vaga) {
        if (vaga == null) return;
        boolean presente = vagaCombo.getItems().stream().anyMatch(v -> v.getId() == vaga.getId());
        if (!presente) {
            vagaCombo.getItems().add(vaga);
        }
    }

    @FXML
    @Override
    public void onAtualizar() {
        view.Checkin selecionado = tabela.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            Checkin entidade;
            try {
                entidade = repositorio.loadFromId(Integer.parseInt(selecionado.getId()));
            } catch (Exception e) {
                entidade = null;
            }
            if (entidade != null && entidade.isFinalizado()) {
                new Alert(Alert.AlertType.WARNING, "Não é possível editar um check-in já finalizado.").show();
                return;
            }
        }
        super.onAtualizar();
    }

    @FXML
    @Override
    public void onDeletar() {
        view.Checkin selecionado = tabela.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            try {
                Checkin entidade = repositorio.loadFromId(Integer.parseInt(selecionado.getId()));
                if (entidade != null && entidade.isFinalizado()) {
                    new Alert(Alert.AlertType.WARNING, "Não é possível remover um check-in já finalizado.").show();
                    return;
                }
            } catch (Exception e) {
                // ignora e deixa o fluxo continuar
            }
        }
        super.onDeletar();
    }
}

