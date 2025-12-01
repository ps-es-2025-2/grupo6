package view.components;

import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 * Componente reutilizável que encapsula o layout padrão CRUD.
 * 
 * Aplica o padrão Template Method, definindo a estrutura comum
 * de todas as telas CRUD (tabela + formulário + botões).
 * 
 */
public class CrudTableLayout implements UIComponent {
    
    private final BorderPane root;
    private final TableView<?> tableView;
    private final Pane formPane;
    private final ActionButtonBar buttonBar;
    
    /**
     * Construtor que cria o layout CRUD padrão.
     * 
     * @param tableView Tabela de dados
     * @param formPane Painel do formulário
     * @param buttonBar Barra de botões de ação
     */
    public CrudTableLayout(TableView<?> tableView, Pane formPane, ActionButtonBar buttonBar) {
        this.tableView = tableView;
        this.formPane = formPane;
        this.buttonBar = buttonBar;
        this.root = new BorderPane();
        
        setupLayout();
    }
    
    /**
     * Configura o layout do componente.
     */
    private void setupLayout() {
        // Tabela no centro
        root.setCenter(tableView);
        
        // Formulário e botões na parte inferior
        BorderPane bottomPane = new BorderPane();
        bottomPane.setCenter(formPane);
        bottomPane.setBottom(buttonBar.getNode());
        
        root.setBottom(bottomPane);
    }
    
    @Override
    public Node getNode() {
        return root;
    }
    
    @Override
    public void initialize() {
        if (buttonBar != null) {
            buttonBar.initialize();
        }
    }
    
    @Override
    public void setDisabled(boolean disabled) {
        tableView.setDisable(disabled);
        formPane.setDisable(disabled);
        if (buttonBar != null) {
            buttonBar.setDisabled(disabled);
        }
    }
    
    @Override
    public boolean isDisabled() {
        return tableView.isDisabled() && formPane.isDisabled();
    }
    
    /**
     * Retorna a tabela do layout.
     */
    public TableView<?> getTableView() {
        return tableView;
    }
    
    /**
     * Retorna o painel do formulário.
     */
    public Pane getFormPane() {
        return formPane;
    }
    
    /**
     * Retorna a barra de botões.
     */
    public ActionButtonBar getButtonBar() {
        return buttonBar;
    }
}

