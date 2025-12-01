# Arquitetura de Componentização - EasyStop

## Objetivo

Implementar **componentização** no front-end para demonstrar conhecimento de **Projeto de Software**, aplicando **padrões de design** e **princípios de arquitetura**.

## Arquitetura de Componentes

### Hierarquia de Componentes

```
┌─────────────────────────────────────────┐
│      ARQUITETURA DE COMPONENTES          │
├─────────────────────────────────────────┤
│                                         │
│  ┌──────────────────────┐               │
│  │   UIComponent        │               │
│  │   (Interface)        │               │
│  └──────────┬───────────┘               │
│             │                            │
│    ┌────────┴────────┐                   │
│    │                 │                   │
│  ┌─▼──────┐    ┌─────▼──────┐            │
│  │Action  │    │FormField   │            │
│  │Button  │    │            │            │
│  │Bar     │    │            │            │
│  └────────┘    └────────────┘            │
│                                         │
│  ┌──────────────────────┐               │
│  │ CrudTableLayout      │               │
│  │ (Composite)          │               │
│  └──────────────────────┘               │
│                                         │
│  ┌──────────────────────┐               │
│  │ ComponentFactory     │               │
│  │ (Factory Method)     │               │
│  └──────────────────────┘               │
└─────────────────────────────────────────┘
```

## Padrões de Design Aplicados

### 1. **Component Pattern** (Padrão Componente)

**Onde**: Interface `UIComponent`

**Objetivo**: Tratar componentes simples e complexos de forma uniforme.

```java
public interface UIComponent {
    Node getNode();
    void initialize();
    void setDisabled(boolean disabled);
}
```

**Benefícios**:
- Consistência na interface
- Facilita composição de componentes
- Permite tratamento uniforme

### 2. **Composite Pattern** (Padrão Composto)

**Onde**: `ActionButtonBar`, `CrudTableLayout`

**Objetivo**: Compor objetos em estruturas de árvore para representar hierarquias parte-todo.

```java
// ActionButtonBar compõe múltiplos botões
public class ActionButtonBar implements UIComponent {
    private Button adicionarButton;
    private Button atualizarButton;
    // ... outros botões
}

// CrudTableLayout compõe tabela + formulário + botões
public class CrudTableLayout implements UIComponent {
    private TableView<?> tableView;
    private Pane formPane;
    private ActionButtonBar buttonBar;
}
```

**Benefícios**:
- Reutilização de código
- Estrutura hierárquica clara
- Facilita manutenção

### 3. **Factory Method Pattern** (Padrão Factory)

**Onde**: `ComponentFactory`

**Objetivo**: Centralizar a criação de componentes, garantindo consistência.

```java
public class ComponentFactory {
    public static ActionButtonBar createActionButtonBar() { }
    public static FormField createTextField(String label) { }
    public static FormField createTextArea(String label) { }
    // ...
}
```

**Benefícios**:
- Encapsula lógica de criação
- Facilita mudanças futuras
- Reduz acoplamento

### 4. **Builder Pattern** (Padrão Construtor)

**Onde**: `ComponentFactory.FormBuilder`

**Objetivo**: Construir objetos complexos passo a passo.

```java
GridPane form = ComponentFactory.formBuilder()
    .addTextField("Nome", true)
    .addTextField("Email")
    .addComboBox("Tipo", comboBox)
    .setHgap(12)
    .setVgap(8)
    .build();
```

**Benefícios**:
- Construção flexível
- Código mais legível
- Validação durante construção

### 5. **Template Method Pattern** (Padrão Template)

**Onde**: `CrudTableLayout`

**Objetivo**: Define a estrutura comum de layouts CRUD.

```java
public class CrudTableLayout {
    // Estrutura padrão: Tabela + Formulário + Botões
    private void setupLayout() {
        root.setCenter(tableView);
        root.setBottom(formWithButtons);
    }
}
```

**Benefícios**:
- Reutilização de estrutura comum
- Consistência visual
- Facilita manutenção

## Estrutura de Arquivos

```
grupo6/
├── view/
│   ├── components/              ← NOVA PASTA DE COMPONENTES
│   │   ├── UIComponent.java     ← Interface base
│   │   ├── ActionButtonBar.java ← Componente de botões
│   │   ├── ActionButtonBar.fxml ← FXML do componente
│   │   ├── FormField.java       ← Componente de campo
│   │   ├── CrudTableLayout.java ← Layout CRUD padrão
│   │   └── ComponentFactory.java← Factory de componentes
│   │
│   ├── styles/                  ← CSS (apresentação)
│   ├── *.fxml                   ← Views (podem usar componentes)
│   └── AppView.java
│
└── docs/
    └── component-architecture.md ← Esta documentação
```

## Fluxo de Uso

### Exemplo 1: Usando ComponentFactory

```java
// No Controller
public class VeiculoController {
    
    @FXML
    private BorderPane rootPane;
    
    public void initialize() {
        // Criar componentes usando Factory
        ActionButtonBar buttonBar = ComponentFactory.createActionButtonBar();
        
        // Configurar handlers
        buttonBar.setOnAdicionar(e -> onAdicionar());
        buttonBar.setOnAtualizar(e -> onAtualizar());
        
        // Adicionar ao layout
        rootPane.setBottom(buttonBar.getNode());
    }
}
```

### Exemplo 2: Usando FormBuilder

```java
// Criar formulário usando Builder
GridPane form = ComponentFactory.formBuilder()
    .addTextField("Placa", true)
    .addTextField("Modelo", true)
    .addTextField("Cor")
    .addTextField("Proprietário", true)
    .setHgap(12)
    .setVgap(8)
    .build();
```

### Exemplo 3: Usando CrudTableLayout

```java
// Criar layout CRUD completo
TableView<Veiculo> tabela = new TableView<>();
GridPane formulario = criarFormulario();
ActionButtonBar botoes = ComponentFactory.createActionButtonBar();

CrudTableLayout layout = new CrudTableLayout(tabela, formulario, botoes);
rootPane.setCenter(layout.getNode());
```

## Separação de Responsabilidades

### Camada de Componentes (`view/components/`)

**Responsabilidades**:
- Definição de componentes reutilizáveis
- Encapsulamento de lógica de apresentação
- Implementação de padrões de design
- Interface uniforme (UIComponent)

**NÃO faz**:
- Lógica de negócio (Controller)
- Acesso a dados (Model)
- Validação complexa (Controller)

### Camada de Views (`view/*.fxml`)

**Responsabilidades**:
- Estrutura de layout
- Composição de componentes
- Bindings simples

### Camada de Controllers (`controller/`)

**Responsabilidades**:
- Orquestração de componentes
- Lógica de negócio
- Validação de dados
- Coordenação View ↔ Model

## Vantagens da Componentização

### 1. **Reutilização**
- Componentes podem ser usados em múltiplas telas
- Reduz duplicação de código
- Facilita manutenção

### 2. **Consistência**
- Interface uniforme entre componentes
- Comportamento previsível
- Visual consistente

### 3. **Testabilidade**
- Componentes podem ser testados isoladamente
- Facilita testes unitários
- Reduz acoplamento

### 4. **Manutenibilidade**
- Mudanças em um componente afetam todas as telas
- Código organizado e modular
- Facilita evolução

### 5. **Escalabilidade**
- Fácil adicionar novos componentes
- Extensibilidade via interfaces
- Suporta crescimento do projeto

## Comparação: Antes vs Depois

### Antes (Sem Componentização)

```xml
<!-- Repetido em cada FXML -->
<HBox alignment="CENTER_RIGHT" spacing="10">
    <Button fx:id="adicionarButton" text="Novo"/>
    <Button fx:id="atualizarButton" text="Editar"/>
    <Button fx:id="deletarButton" text="Excluir"/>
    <Button fx:id="confirmarButton" text="Confirmar"/>
    <Button fx:id="cancelarButton" text="Cancelar"/>
</HBox>
```

**Problemas**:
- Código duplicado
- Difícil manutenção
- Inconsistências possíveis
- Não demonstra conhecimento de arquitetura

### Depois (Com Componentização)

```java
// Uma vez criado, reutilizado em todos os lugares
ActionButtonBar buttonBar = ComponentFactory.createActionButtonBar();
buttonBar.setOnAdicionar(e -> onAdicionar());
```

**Benefícios**:
- Código reutilizável
- Fácil manutenção
- Consistência garantida
- Demonstra conhecimento de padrões de design

## Conceitos de Projeto de Software Aplicados

### 1. **Modularidade**
Componentes são módulos independentes e reutilizáveis.

### 2. **Separação de Responsabilidades**
Cada componente tem uma responsabilidade única e bem definida.

### 3. **Abstração**
Interface `UIComponent` abstrai detalhes de implementação.

### 4. **Encapsulamento**
Componentes encapsulam comportamento e estado interno.

### 5. **Polimorfismo**
Diferentes componentes implementam a mesma interface.

### 6. **Design Patterns**
Aplicação prática de padrões de design reconhecidos.

## Referências

- **Design Patterns**: Gang of Four (GoF)
- **Component Pattern**: Padrão de composição de objetos
- **Factory Pattern**: Padrão de criação de objetos
- **Builder Pattern**: Padrão de construção de objetos
- **Composite Pattern**: Padrão de estrutura de objetos

## Próximos Passos

1. Estrutura de componentes criada
2. Padrões de design implementados
3. Refatorar FXMLs para usar componentes
4. Criar mais componentes específicos (se necessário)
5. Documentar componentes individuais
6. Criar testes unitários para componentes

---

**Lembre-se**: Componentização demonstra conhecimento de **arquitetura de software** e **padrões de design**, elevando o nível do projeto!
