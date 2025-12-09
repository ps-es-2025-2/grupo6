package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Repositorio;
import model.Vaga;
import model.VagaRepositorio;

public class VagaController extends AbstractCrudController<Vaga, view.Vaga, Integer> implements Initializable {

    @FXML
    private TableView<view.Vaga> tabela;
    @FXML
    private TableColumn<view.Vaga, Number> idCol;
    @FXML
    private TableColumn<view.Vaga, String> codigoCol;
    @FXML
    private TableColumn<view.Vaga, String> setorCol;
    @FXML
    private TableColumn<view.Vaga, Boolean> ocupadaCol;

    @FXML
    private TextField codigoField;
    @FXML
    private TextField setorField;
    @FXML
    private CheckBox ocupadaCheck;

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

    private final VagaRepositorio repositorio = model.Repositorios.VAGAS;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        codigoCol.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        setorCol.setCellValueFactory(new PropertyValueFactory<>("setor"));
        ocupadaCol.setCellValueFactory(new PropertyValueFactory<>("ocupada"));

        ocupadaCheck.setDisable(true);

        super.adicionarButton = adicionarButton;
        super.atualizarButton = atualizarButton;
        super.deletarButton = deletarButton;
        super.confirmarButton = confirmarButton;
        super.cancelarButton = cancelarButton;

        super.initialize();
        atualizarTabela();

        iniciarAutoRefresh();
    }

    private void atualizarTabela() {
        try {
            tabela.getItems().setAll(
                    repositorio.loadAll().stream().map(this::modelToView).toList()
            );
        } catch (Exception e) {
            System.err.println("Erro ao atualizar tabela: " + e.getMessage());
        }
    }

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
    @Override
    protected Repositorio<Vaga, Integer> getRepositorio() {
        return repositorio;
    }

    @Override
    protected view.Vaga modelToView(Vaga entidade) {
        return new view.Vaga(entidade.getId(), entidade.getCodigo(), entidade.getSetor(), entidade.isOcupada());
    }

    @Override
    protected Vaga viewToModel() {
        String codigo = codigoField.getText() != null ? codigoField.getText().trim() : "";
        String setor = setorField.getText() != null ? setorField.getText().trim() : "";

        Vaga.validarDados(codigo, setor);
        Vaga vaga = new Vaga();
        vaga.setCodigo(codigo.toUpperCase());
        vaga.setSetor(setor);
        vaga.setOcupada(ocupadaCheck.isSelected());
        return vaga;
    }

    @Override
    protected void preencherCampos(view.Vaga item) {
        codigoField.setText(item.getCodigo());
        setorField.setText(item.getSetor());
        ocupadaCheck.setSelected(item.isOcupada());
    }

    @Override
    protected void limparCampos() {
        codigoField.clear();
        setorField.clear();
        ocupadaCheck.setSelected(false);
    }

    @Override
    protected void desabilitarCampos(boolean desabilitado) {
        codigoField.setDisable(desabilitado);
        setorField.setDisable(desabilitado);
    }

    @Override
    protected TableView<view.Vaga> getTabela() {
        return tabela;
    }

    @Override
    protected Integer getIdFromViewModel(view.Vaga viewModel) {
        return viewModel.getId();
    }

    @Override
    protected void setIdOnEntity(Vaga entidade, Integer id) {
    }
}

