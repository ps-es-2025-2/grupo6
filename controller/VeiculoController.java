package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Repositorio;
import model.Veiculo;
import model.VeiculoRepositorio;

import java.net.URL;
import java.util.ResourceBundle;

public class VeiculoController extends AbstractCrudController<Veiculo, view.Veiculo, String> implements Initializable {

    @FXML
    private TableView<view.Veiculo> tabela;
    @FXML
    private TableColumn<view.Veiculo, String> placaCol;
    @FXML
    private TableColumn<view.Veiculo, String> modeloCol;
    @FXML
    private TableColumn<view.Veiculo, String> corCol;
    @FXML
    private TableColumn<view.Veiculo, String> proprietarioCol;

    @FXML
    private TextField placaField;
    @FXML
    private TextField modeloField;
    @FXML
    private TextField corField;
    @FXML
    private TextField proprietarioField;

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

    private final VeiculoRepositorio repositorio = model.Repositorios.VEICULOS;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        placaCol.setCellValueFactory(new PropertyValueFactory<>("placa"));
        modeloCol.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        corCol.setCellValueFactory(new PropertyValueFactory<>("cor"));
        proprietarioCol.setCellValueFactory(new PropertyValueFactory<>("proprietario"));

        super.adicionarButton = adicionarButton;
        super.atualizarButton = atualizarButton;
        super.deletarButton = deletarButton;
        super.confirmarButton = confirmarButton;
        super.cancelarButton = cancelarButton;

        super.initialize();
    }

    @Override
    protected Repositorio<Veiculo, String> getRepositorio() {
        return repositorio;
    }

    @Override
    protected view.Veiculo modelToView(Veiculo entidade) {
        return new view.Veiculo(entidade.getPlaca(), entidade.getModelo(), entidade.getCor(), entidade.getProprietario());
    }

    @Override
    protected Veiculo viewToModel() {
        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca(placaField.getText().trim());
        veiculo.setModelo(modeloField.getText().trim());
        veiculo.setCor(corField.getText().trim());
        veiculo.setProprietario(proprietarioField.getText().trim());
        return veiculo;
    }

    @Override
    protected void preencherCampos(view.Veiculo item) {
        placaField.setText(item.getPlaca());
        modeloField.setText(item.getModelo());
        corField.setText(item.getCor());
        proprietarioField.setText(item.getProprietario());
    }

    @Override
    protected void limparCampos() {
        placaField.clear();
        modeloField.clear();
        corField.clear();
        proprietarioField.clear();
    }

    @Override
    protected void desabilitarCampos(boolean desabilitado) {
        placaField.setDisable(desabilitado && pendingAction != Action.NOVO);
        modeloField.setDisable(desabilitado);
        corField.setDisable(desabilitado);
        proprietarioField.setDisable(desabilitado);
    }

    @Override
    protected TableView<view.Veiculo> getTabela() {
        return tabela;
    }

    @Override
    protected String getIdFromViewModel(view.Veiculo viewModel) {
        return viewModel.getPlaca();
    }

    @Override
    protected void setIdOnEntity(Veiculo entidade, String id) {
        entidade.setPlaca(id);
    }
}

