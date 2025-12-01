package view.components;

import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

/**
 * Componente reutilizável para campos de formulário.
 * 
 * Encapsula Label + Control em um único componente, aplicando
 * o padrão Composite. Facilita a criação de formulários consistentes.
 * 
 */
public class FormField implements UIComponent {
    
    private final Label label;
    private final Control control;
    private final GridPane container;
    private final boolean required;
    
    /**
     * Construtor para criar um campo de formulário.
     * 
     * @param labelText Texto do label
     * @param control Control JavaFX (TextField, ComboBox, etc.)
     * @param required Se o campo é obrigatório
     */
    public FormField(String labelText, Control control, boolean required) {
        this.label = new Label(labelText);
        this.control = control;
        this.required = required;
        this.container = new GridPane();
        
        setupField();
    }
    
    /**
     * Construtor simplificado (campo não obrigatório).
     */
    public FormField(String labelText, Control control) {
        this(labelText, control, false);
    }
    
    /**
     * Configura o layout do campo.
     */
    private void setupField() {
        container.setHgap(12);
        container.setVgap(8);
        
        // Configura constraints do GridPane
        GridPane.setHgrow(control, Priority.ALWAYS);
        
        // Adiciona label e control ao container
        container.add(label, 0, 0);
        container.add(control, 1, 0);
        
        // Estiliza label se obrigatório
        if (required) {
            label.getStyleClass().add("required-field");
            label.setText(label.getText() + " *");
        }
    }
    
    @Override
    public Node getNode() {
        return container;
    }
    
    @Override
    public void initialize() {
        // Nada a fazer aqui, já está configurado no construtor
    }
    
    @Override
    public void setDisabled(boolean disabled) {
        control.setDisable(disabled);
        if (disabled) {
            label.getStyleClass().add("disabled-label");
        } else {
            label.getStyleClass().remove("disabled-label");
        }
    }
    
    @Override
    public boolean isDisabled() {
        return control.isDisabled();
    }
    
    /**
     * Retorna o control do campo.
     */
    public Control getControl() {
        return control;
    }
    
    /**
     * Retorna o label do campo.
     */
    public Label getLabel() {
        return label;
    }
    
    /**
     * Verifica se o campo é obrigatório.
     */
    public boolean isRequired() {
        return required;
    }
    
    /**
     * Adiciona o campo a um GridPane na posição especificada.
     */
    public void addToGrid(GridPane grid, int row) {
        grid.add(label, 0, row);
        grid.add(control, 1, row);
        GridPane.setHgrow(control, Priority.ALWAYS);
    }
}

