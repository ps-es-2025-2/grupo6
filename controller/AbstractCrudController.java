package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

public abstract class AbstractCrudController<E, V, ID> {

    protected enum Action {
        NONE, NOVO, ATUALIZAR, DELETAR
    }

    protected Action pendingAction = Action.NONE;

    @FXML
    protected Button adicionarButton;
    @FXML
    protected Button atualizarButton;
    @FXML
    protected Button deletarButton;
    @FXML
    protected Button confirmarButton;
    @FXML
    protected Button cancelarButton;

    protected abstract model.Repositorio<E, ID> getRepositorio();

    protected abstract V modelToView(E entidade);

    protected abstract E viewToModel();

    protected abstract void preencherCampos(V item);

    protected abstract void limparCampos();

    protected abstract void desabilitarCampos(boolean desabilitado);

    protected abstract TableView<V> getTabela();

    protected abstract ID getIdFromViewModel(V viewModel);

    protected abstract void setIdOnEntity(E entidade, ID id);

    protected void beforeCreate(E entidade) throws Exception {
    }

    protected void afterCreate(E entidade) {
    }

    protected void beforeUpdate(E entidade) throws Exception {
    }

    protected void afterUpdate(E entidade) {
    }

    protected void beforeDelete(E entidade) throws Exception {
    }

    protected void afterDelete(E entidade) {
    }

    public void initialize() {
        getTabela().setItems(loadAll());
        getTabela().getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (pendingAction == Action.NONE) {
                        handleItemSelected(newSelection);
                    }
                });
        resetToInitialState();
    }

    protected void handleItemSelected(V item) {
        if (item != null) {
            preencherCampos(item);
            desabilitarCampos(true);
            adicionarButton.setDisable(false);
            atualizarButton.setDisable(false);
            deletarButton.setDisable(false);
            confirmarButton.setDisable(true);
            cancelarButton.setDisable(true);
        } else {
            limparCampos();
            adicionarButton.setDisable(false);
            atualizarButton.setDisable(true);
            deletarButton.setDisable(true);
            confirmarButton.setDisable(true);
            cancelarButton.setDisable(true);
        }
    }

    protected ObservableList<V> loadAll() {
        ObservableList<V> lista = FXCollections.observableArrayList();
        for (E entidade : getRepositorio().loadAll()) {
            lista.add(modelToView(entidade));
        }
        return lista;
    }

    @FXML
    public void onAdicionar() {
        getTabela().getSelectionModel().clearSelection();
        limparCampos();
        setPendingAction(Action.NOVO);
    }

    @FXML
    public void onAtualizar() {
        if (getTabela().getSelectionModel().getSelectedItem() != null) {
            setPendingAction(Action.ATUALIZAR);
        } else {
            new Alert(Alert.AlertType.WARNING, "Por favor, selecione um item para atualizar.").show();
        }
    }

    @FXML
    public void onDeletar() {
        if (getTabela().getSelectionModel().getSelectedItem() != null) {
            setPendingAction(Action.DELETAR);
        } else {
            new Alert(Alert.AlertType.WARNING, "Por favor, selecione um item para deletar.").show();
        }
    }

    @FXML
    public void onConfirmar() {
        switch (pendingAction) {
            case NOVO:
                doCreate();
                break;
            case ATUALIZAR:
                doUpdate();
                break;
            case DELETAR:
                doDelete();
                break;
            default:
                break;
        }
    }

    @FXML
    public void onCancelar() {
        resetToInitialState();
        V selecionado = getTabela().getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            preencherCampos(selecionado);
        }
    }

    private void doCreate() {
        try {
            E entidade = viewToModel();
            beforeCreate(entidade);
            E salvo = getRepositorio().create(entidade);
            afterCreate(salvo);
            V viewItem = modelToView(salvo);
            getTabela().getItems().add(viewItem);
            getTabela().getSelectionModel().select(viewItem);
            resetToInitialState();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Erro ao Salvar: " + e.getMessage()).show();
        }
    }

    private void doUpdate() {
        V selecionado = getTabela().getSelectionModel().getSelectedItem();
        try {
            E entidade = viewToModel();
            ID id = getIdFromViewModel(selecionado);
            setIdOnEntity(entidade, id);
            beforeUpdate(entidade);
            getRepositorio().update(entidade);
            afterUpdate(entidade);
            V atualizado = modelToView(entidade);
            int index = getTabela().getItems().indexOf(selecionado);
            getTabela().getItems().set(index, atualizado);
            getTabela().getSelectionModel().select(atualizado);
            resetToInitialState();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Erro ao Atualizar: " + e.getMessage()).show();
        }
    }

    private void doDelete() {
        V selecionado = getTabela().getSelectionModel().getSelectedItem();
        try {
            ID id = getIdFromViewModel(selecionado);
            E entidade = getRepositorio().loadFromId(id);
            beforeDelete(entidade);
            getRepositorio().delete(entidade);
            afterDelete(entidade);
            getTabela().getItems().remove(selecionado);
            resetToInitialState();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Erro ao Deletar: " + e.getMessage()).show();
        }
    }

    protected void setPendingAction(Action action) {
        pendingAction = action;
        boolean isConfirmationMode = (action != Action.NONE);

        desabilitarCampos(action != Action.NOVO && action != Action.ATUALIZAR);

        adicionarButton.setDisable(isConfirmationMode);
        atualizarButton.setDisable(isConfirmationMode || getTabela().getSelectionModel().getSelectedItem() == null);
        deletarButton.setDisable(isConfirmationMode || getTabela().getSelectionModel().getSelectedItem() == null);
        confirmarButton.setDisable(!isConfirmationMode);
        cancelarButton.setDisable(!isConfirmationMode);
    }

    protected void resetToInitialState() {
        setPendingAction(Action.NONE);
        limparCampos();
        getTabela().getSelectionModel().clearSelection();
        handleItemSelected(null);
    }
}