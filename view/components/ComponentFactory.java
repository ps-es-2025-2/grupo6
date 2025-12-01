package view.components;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory para criação de componentes UI.
 * 
 * Aplica o padrão Factory Method, centralizando a criação
 * de componentes e garantindo consistência.
 * 
 */
public class ComponentFactory {
    
    /**
     * Cria uma barra de botões de ação padrão.
     */
    public static ActionButtonBar createActionButtonBar() {
        return new ActionButtonBar();
    }
    
    /**
     * Cria um campo de formulário com TextField.
     */
    public static FormField createTextField(String label, boolean required) {
        TextField field = new TextField();
        return new FormField(label, field, required);
    }
    
    /**
     * Cria um campo de formulário com TextField (não obrigatório).
     */
    public static FormField createTextField(String label) {
        return createTextField(label, false);
    }
    
    /**
     * Cria um campo de formulário com TextArea.
     */
    public static FormField createTextArea(String label, boolean required) {
        TextArea area = new TextArea();
        area.setPrefRowCount(3);
        return new FormField(label, area, required);
    }
    
    /**
     * Cria um campo de formulário com ComboBox.
     */
    public static <T> FormField createComboBox(String label, ComboBox<T> comboBox, boolean required) {
        return new FormField(label, comboBox, required);
    }
    
    /**
     * Cria um campo de formulário com DatePicker.
     */
    public static FormField createDatePicker(String label, boolean required) {
        DatePicker picker = new DatePicker();
        return new FormField(label, picker, required);
    }
    
    /**
     * Cria um campo de formulário com CheckBox.
     */
    public static FormField createCheckBox(String label, CheckBox checkBox) {
        return new FormField(label, checkBox, false);
    }
    
    /**
     * Cria um formulário completo a partir de uma lista de campos.
     */
    public static Pane createForm(List<FormField> fields) {
        GridPane form = new GridPane();
        form.setHgap(12);
        form.setVgap(8);
        form.setPadding(new javafx.geometry.Insets(12));
        
        for (int i = 0; i < fields.size(); i++) {
            fields.get(i).addToGrid(form, i);
        }
        
        return form;
    }
    
    /**
     * Cria um formulário usando o padrão Builder.
     */
    public static FormBuilder formBuilder() {
        return new FormBuilder();
    }
    
    /**
     * Builder para criação de formulários.
     * Aplica o padrão Builder para construção flexível de formulários.
     */
    public static class FormBuilder {
        private final List<FormField> fields = new ArrayList<>();
        private int hgap = 12;
        private int vgap = 8;
        private javafx.geometry.Insets padding = new javafx.geometry.Insets(12);
        
        public FormBuilder addField(FormField field) {
            fields.add(field);
            return this;
        }
        
        public FormBuilder addTextField(String label) {
            return addField(createTextField(label));
        }
        
        public FormBuilder addTextField(String label, boolean required) {
            return addField(createTextField(label, required));
        }
        
        public FormBuilder addTextArea(String label) {
            return addField(createTextArea(label, false));
        }
        
        public FormBuilder addComboBox(String label, ComboBox<?> comboBox) {
            return addField(createComboBox(label, comboBox, false));
        }
        
        public FormBuilder addDatePicker(String label) {
            return addField(createDatePicker(label, false));
        }
        
        public FormBuilder setHgap(int hgap) {
            this.hgap = hgap;
            return this;
        }
        
        public FormBuilder setVgap(int vgap) {
            this.vgap = vgap;
            return this;
        }
        
        public FormBuilder setPadding(javafx.geometry.Insets padding) {
            this.padding = padding;
            return this;
        }
        
        public GridPane build() {
            GridPane form = new GridPane();
            form.setHgap(hgap);
            form.setVgap(vgap);
            form.setPadding(padding);
            
            for (int i = 0; i < fields.size(); i++) {
                fields.get(i).addToGrid(form, i);
            }
            
            return form;
        }
    }
}

