# Pasta de Estilos CSS - EasyStop

## Localização na Arquitetura

Esta pasta contém os arquivos CSS do projeto, que fazem parte da **camada View** na arquitetura MVC.

```
Arquitetura MVC:
├── Model      → Dados e lógica de negócio
├── View       → Apresentação (FXML + CSS + ViewModels)
│   └── styles/ ← Você está aqui!
└── Controller → Coordenação entre View e Model
```

## Arquivos

### `main.css`
Stylesheet principal da aplicação. Contém:
- Variáveis CSS (cores, espaçamentos)
- Estilos para componentes JavaFX (botões, tabelas, campos de texto)
- Classes utilitárias
- Animações e transições

## Como Usar

### 1. Estilizar por Classe CSS
```css
/* Aplica a todos os botões */
.button {
    -fx-background-color: #3498DB;
}
```

### 2. Estilizar por ID (fx:id no FXML)
```css
/* Aplica apenas ao botão com fx:id="adicionarButton" */
#adicionarButton {
    -fx-background-color: #27AE60;
}
```

### 3. Adicionar Classes no FXML
```xml
<Button fx:id="meuBotao" styleClass="custom-button" text="Clique"/>
```

## Carregamento

O CSS é carregado automaticamente em `AppView.java`:
```java
scene.getStylesheets().add(cssUrl.toExternalForm());
```

Todos os FXMLs herdam os estilos automaticamente.

## Documentação Completa

Veja `docs/css-architecture.md` para documentação detalhada sobre:
- Arquitetura CSS no projeto
- Convenções de nomenclatura
- Boas práticas
- Exemplos práticos

## Lembre-se

- **CSS = Apresentação Visual** (camada View)
- **Controller = Lógica de Negócio**
- **Model = Dados e Persistência**

Mantenha a separação de responsabilidades!
