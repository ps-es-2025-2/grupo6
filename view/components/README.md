# Componentes UI - EasyStop

## Objetivo

Esta pasta contém **componentes reutilizáveis** do front-end, implementando **padrões de design** e **princípios de arquitetura de software**.

## Estrutura

```
components/
├── UIComponent.java          → Interface base para todos os componentes
├── ActionButtonBar.java      → Barra de botões de ação (Composite)
├── ActionButtonBar.fxml      → FXML do componente
├── FormField.java            → Campo de formulário reutilizável
├── CrudTableLayout.java      → Layout padrão CRUD (Template Method)
├── ComponentFactory.java     → Factory para criar componentes (Factory Method)
└── README.md                 → Este arquivo
```

## Padrões de Design

### 1. Component Pattern
- **Interface**: `UIComponent`
- **Objetivo**: Tratar componentes de forma uniforme

### 2. Composite Pattern
- **Classes**: `ActionButtonBar`, `CrudTableLayout`
- **Objetivo**: Compor objetos em estruturas hierárquicas

### 3. Factory Method
- **Classe**: `ComponentFactory`
- **Objetivo**: Centralizar criação de componentes

### 4. Builder Pattern
- **Classe**: `ComponentFactory.FormBuilder`
- **Objetivo**: Construir objetos complexos passo a passo

### 5. Template Method
- **Classe**: `CrudTableLayout`
- **Objetivo**: Definir estrutura comum de layouts

## Como Usar

### Exemplo 1: Criar Barra de Botões

```java
ActionButtonBar buttonBar = ComponentFactory.createActionButtonBar();
buttonBar.setOnAdicionar(e -> onAdicionar());
buttonBar.setOnAtualizar(e -> onAtualizar());
buttonBar.setOnDeletar(e -> onDeletar());
buttonBar.setOnConfirmar(e -> onConfirmar());
buttonBar.setOnCancelar(e -> onCancelar());

// Adicionar ao layout
pane.setBottom(buttonBar.getNode());
```

### Exemplo 2: Criar Campo de Formulário

```java
FormField placaField = ComponentFactory.createTextField("Placa", true);
FormField modeloField = ComponentFactory.createTextField("Modelo", true);

// Adicionar a um GridPane
GridPane form = new GridPane();
placaField.addToGrid(form, 0);
modeloField.addToGrid(form, 1);
```

### Exemplo 3: Criar Formulário Completo (Builder)

```java
GridPane form = ComponentFactory.formBuilder()
    .addTextField("Placa", true)
    .addTextField("Modelo", true)
    .addTextField("Cor")
    .addTextField("Proprietário", true)
    .setHgap(12)
    .setVgap(8)
    .build();
```

### Exemplo 4: Criar Layout CRUD Completo

```java
TableView<Veiculo> tabela = criarTabela();
GridPane formulario = criarFormulario();
ActionButtonBar botoes = ComponentFactory.createActionButtonBar();

CrudTableLayout layout = new CrudTableLayout(tabela, formulario, botoes);
rootPane.setCenter(layout.getNode());
```

## Documentação Completa

Veja `docs/component-architecture.md` para:
- Arquitetura detalhada
- Padrões de design explicados
- Exemplos avançados
- Comparação antes/depois

## Princípios

1. **Reutilização**: Componentes devem ser reutilizáveis
2. **Encapsulamento**: Lógica interna não exposta
3. **Consistência**: Interface uniforme via `UIComponent`
4. **Separação**: Componentes não contêm lógica de negócio

## Por que Componentização?

- Demonstra conhecimento de **arquitetura de software**
- Aplica **padrões de design** reconhecidos
- Reduz **duplicação de código**
- Facilita **manutenção** e **evolução**
- Melhora **testabilidade**

---

**Componentização = Projeto de Software Profissional!**
