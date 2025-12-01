package view.components;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.io.IOException;

/**
 * Componente reutilizável que representa uma barra de botões de ação.
 * 
 * Aplica o padrão Composite, encapsulando múltiplos botões em um único componente.
 * Reduz duplicação de código nos FXMLs.
 * 
 */
public class ActionButtonBar implements UIComponent {
    
    @FXML
    private HBox buttonContainer;
    
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
    
    private Node root;
    
    /**
     * Construtor padrão que carrega o FXML do componente.
     */
    public ActionButtonBar() {
        loadFXML();
    }
    
    /**
     * Carrega o arquivo FXML do componente.
     */
    private void loadFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ActionButtonBar.fxml"));
            loader.setController(this);
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar ActionButtonBar.fxml", e);
        }
    }
    
    @Override
    public Node getNode() {
        return root;
    }
    
    @Override
    public void initialize() {
        // Configurações iniciais podem ser adicionadas aqui
        resetButtons();
    }
    
    @Override
    public void setDisabled(boolean disabled) {
        adicionarButton.setDisable(disabled);
        atualizarButton.setDisable(disabled);
        deletarButton.setDisable(disabled);
        confirmarButton.setDisable(disabled);
        cancelarButton.setDisable(disabled);
    }
    
    @Override
    public boolean isDisabled() {
        return adicionarButton.isDisabled() && 
               atualizarButton.isDisabled() && 
               deletarButton.isDisabled();
    }
    
    /**
     * Define o handler para o botão Adicionar.
     */
    public void setOnAdicionar(EventHandler<ActionEvent> handler) {
        adicionarButton.setOnAction(handler);
    }
    
    /**
     * Define o handler para o botão Atualizar.
     */
    public void setOnAtualizar(EventHandler<ActionEvent> handler) {
        atualizarButton.setOnAction(handler);
    }
    
    /**
     * Define o handler para o botão Deletar.
     */
    public void setOnDeletar(EventHandler<ActionEvent> handler) {
        deletarButton.setOnAction(handler);
    }
    
    /**
     * Define o handler para o botão Confirmar.
     */
    public void setOnConfirmar(EventHandler<ActionEvent> handler) {
        confirmarButton.setOnAction(handler);
    }
    
    /**
     * Define o handler para o botão Cancelar.
     */
    public void setOnCancelar(EventHandler<ActionEvent> handler) {
        cancelarButton.setOnAction(handler);
    }
    
    /**
     * Reseta os botões para o estado inicial.
     */
    public void resetButtons() {
        adicionarButton.setDisable(false);
        atualizarButton.setDisable(true);
        deletarButton.setDisable(true);
        confirmarButton.setDisable(true);
        cancelarButton.setDisable(true);
    }
    
    /**
     * Configura os botões para o modo de edição.
     */
    public void setEditMode() {
        adicionarButton.setDisable(true);
        atualizarButton.setDisable(true);
        deletarButton.setDisable(true);
        confirmarButton.setDisable(false);
        cancelarButton.setDisable(false);
    }
    
    /**
     * Configura os botões para o modo de criação.
     */
    public void setCreateMode() {
        adicionarButton.setDisable(true);
        atualizarButton.setDisable(true);
        deletarButton.setDisable(true);
        confirmarButton.setDisable(false);
        cancelarButton.setDisable(false);
    }
    
    /**
     * Configura os botões para o modo de seleção.
     */
    public void setSelectionMode() {
        adicionarButton.setDisable(false);
        atualizarButton.setDisable(false);
        deletarButton.setDisable(false);
        confirmarButton.setDisable(true);
        cancelarButton.setDisable(true);
    }
    
    // Getters para acesso direto aos botões (se necessário)
    public Button getAdicionarButton() {
        return adicionarButton;
    }
    
    public Button getAtualizarButton() {
        return atualizarButton;
    }
    
    public Button getDeletarButton() {
        return deletarButton;
    }
    
    public Button getConfirmarButton() {
        return confirmarButton;
    }
    
    public Button getCancelarButton() {
        return cancelarButton;
    }
}

