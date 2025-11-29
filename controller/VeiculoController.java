package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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

    // ----------------------------- NOVAS VALIDAÇÕES -----------------------------

    private boolean validarCampos() {

        String placa = placaField.getText().trim().toUpperCase();
        String modelo = modeloField.getText().trim();
        String cor = corField.getText().trim();
        String proprietario = proprietarioField.getText().trim();

        // Regex de placa Mercosul: LLLNLNN
        if (!placa.matches("^[A-Z]{3}[0-9][A-Z0-9][0-9]{2}$")) {
            alert("Placa inválida. Use o padrão Mercosul (ex: ABC1D23).");
            return false;
        }

        // Verifica duplicidade da placa
        if (repositorio.existsById(placa)) {
            alert("Já existe um veículo cadastrado com esta placa.");
            return false;
        }

        // Modelo: letras, números e espaços
        if (!modelo.matches("^[A-Za-z0-9 ]{2,40}$")) {
            alert("Modelo inválido. Use apenas letras, números e espaços.");
            return false;
        }

        // Cor: somente letras e espaços
        if (!cor.matches("^[A-Za-zÀ-ÖØ-öø-ÿ ]{3,20}$")) {
            alert("Cor inválida. Use apenas letras e espaços.");
            return false;
        }

        // Proprietário: somente letras e espaços
        if (!proprietario.matches("^[A-Za-zÀ-ÖØ-öø-ÿ ]{3,50}$")) {
            alert("Nome do proprietário inválido. Use apenas letras e espaços.");
            return false;
        }

        return true;
    }

    private void alert(String msg) {
        new Alert(Alert.AlertType.ERROR, msg).show();
    }

    // ---------------------------------------------------------------------------

    @Override
    protected view.Veiculo modelToView(Veiculo entidade) {
        return new view.Veiculo(entidade.getPlaca(), entidade.getModelo(), entidade.getCor(), entidade.getProprietario());
    }

    @Override
    protected Veiculo viewToModel() {

        // 🔥 Valida antes de montar o objeto
        if (!validarCampos()) return null;

        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca(placaField.getText().trim().toUpperCase());
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
