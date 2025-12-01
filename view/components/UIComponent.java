package view.components;

import javafx.scene.Node;

/**
 * Interface base para componentes UI reutilizáveis.
 * 
 * Aplica o padrão Component do Design Patterns, permitindo que
 * componentes complexos sejam tratados de forma uniforme.
 * 
 */
public interface UIComponent {
    
    /**
     * Retorna o nó JavaFX que representa este componente.
     * Permite que o componente seja inserido em qualquer container.
     * 
     * @return Node JavaFX do componente
     */
    Node getNode();
    
    /**
     * Inicializa o componente após sua criação.
     * Útil para configurações que dependem do contexto.
     */
    void initialize();
    
    /**
     * Habilita ou desabilita o componente.
     * 
     * @param disabled true para desabilitar, false para habilitar
     */
    void setDisabled(boolean disabled);
    
    /**
     * Verifica se o componente está desabilitado.
     * 
     * @return true se desabilitado, false caso contrário
     */
    boolean isDisabled();
}

