# Arquitetura CSS no EasyStop

## Posicionamento na Arquitetura MVC

### Onde CSS se Encaixa?

O **CSS (Cascading Style Sheets)** faz parte da **camada View** na arquitetura MVC do projeto EasyStop. Ele é responsável exclusivamente pela **apresentação visual** da aplicação, sem interferir na lógica de negócio ou no modelo de dados.

```
┌─────────────────────────────────────────┐
│         ARQUITETURA MVC                 │
├─────────────────────────────────────────┤
│                                         │
│  ┌──────────────┐                       │
│  │    VIEW      │                       │
│  │              │                       │
│  │  • FXML      │ ← Estrutura           │
│  │  • CSS       │ ← Estilização         │
│  │  • ViewModel │ ← Dados para exibição │
│  └──────┬───────┘                       │
│         │                                │
│  ┌──────▼───────┐                       │
│  │ CONTROLLER   │                       │
│  │              │                       │
│  │  • Lógica    │                       │
│  │  • Validação │                       │
│  └──────┬───────┘                       │
│         │                                │
│  ┌──────▼───────┐                       │
│  │    MODEL     │                       │
│  │              │                       │
│  │  • Entidades │                       │
│  │  • Repositórios│                    │
│  └──────────────┘                       │
└─────────────────────────────────────────┘
```

## Responsabilidades do CSS

### O que CSS DEVE fazer:

1. **Estilização Visual**
   - Cores, fontes, espaçamentos
   - Bordas, sombras, efeitos
   - Layout e posicionamento visual
   - Animações e transições

2. **Temas e Variantes**
   - Tema claro/escuro
   - Cores primárias/secundárias
   - Estilos consistentes

3. **Responsividade Visual**
   - Adaptação de tamanhos
   - Estados hover/focus/disabled

### O que CSS NÃO deve fazer:

1. **Lógica de Negócio** → Responsabilidade do Controller
2. **Validação de Dados** → Responsabilidade do Controller
3. **Manipulação de Dados** → Responsabilidade do Model
4. **Eventos Complexos** → Responsabilidade do Controller

## Estrutura de Arquivos

```
grupo6/
├── view/
│   ├── styles/              ← NOVA PASTA PARA CSS
│   │   ├── main.css         ← Stylesheet principal
│   │   ├── components.css   ← Estilos de componentes (opcional)
│   │   └── themes.css       ← Temas alternativos (opcional)
│   ├── *.fxml               ← Arquivos FXML (estrutura)
│   └── AppView.java         ← Carrega o CSS
│
├── controller/              ← Não mexe com CSS
├── model/                   ← Não mexe com CSS
└── docs/
    └── css-architecture.md  ← Esta documentação
```

## Como CSS é Aplicado

### 1. Carregamento Global (AppView.java)

O CSS principal é carregado uma única vez na `Scene` principal:

```java
// Em AppView.java
Scene scene = new Scene(pane);
scene.getStylesheets().add(getClass().getResource("styles/main.css").toExternalForm());
```

**Vantagem**: Todos os FXMLs herdam automaticamente os estilos.

### 2. Carregamento por FXML (Opcional)

Cada FXML pode referenciar seu próprio CSS:

```xml
<!-- No topo do arquivo .fxml -->
<?xml-stylesheet href="styles/componente.css"?>
```

**Uso**: Quando um componente específico precisa de estilos únicos.

### 3. Aplicação Programática (Controller)

Em casos específicos, o Controller pode aplicar classes CSS:

```java
// No Controller
button.getStyleClass().add("custom-button");
```

**Uso**: Quando a classe CSS depende de lógica de negócio.

## Convenções de Nomenclatura

### IDs vs Classes CSS

#### IDs (`fx:id` no FXML)
- **Uso**: Elementos únicos na aplicação
- **Exemplo**: `#adicionarButton`, `#tabela`, `#mainTabPane`
- **Aplicação**: Estilos específicos para um elemento único

#### Classes CSS
- **Uso**: Estilos reutilizáveis para múltiplos elementos
- **Exemplo**: `.button`, `.table-view`, `.text-field`
- **Aplicação**: Estilos genéricos aplicados a vários elementos

### Padrão de Nomenclatura

```css
/* Componentes genéricos */
.button { }
.table-view { }
.text-field { }

/* IDs específicos */
#adicionarButton { }
#confirmarButton { }

/* Classes utilitárias */
.success-message { }
.error-message { }
.warning-message { }
```

## Fluxo de Trabalho Front-end

### 1. Designer/Front-end trabalha em:
```
view/
├── styles/
│   └── main.css        ← Você trabalha aqui
└── *.fxml              ← Você pode adicionar classes CSS aqui
```

### 2. Back-end trabalha em:
```
controller/             ← Lógica de negócio
model/                  ← Dados e persistência
```

### 3. Integração:
- Front-end define estilos no CSS
- Back-end usa `fx:id` e classes no FXML
- Controller pode aplicar classes dinamicamente

## Checklist para Front-end

### Ao adicionar novos estilos:

1. **Verificar se já existe estilo similar**
   - Reutilizar classes existentes quando possível
   - Manter consistência visual

2. **Usar variáveis CSS para cores**
   ```css
   .root {
       -fx-primary-color: #3498DB;
   }
   .button {
       -fx-background-color: -fx-primary-color;
   }
   ```

3. **Documentar estilos complexos**
   ```css
   /* Estilo para botões de ação crítica
    * Aplicado apenas em operações irreversíveis
    */
   #deletarButton { }
   ```

4. **Testar em diferentes estados**
   - Normal, hover, focused, disabled
   - Diferentes tamanhos de tela

5. **Manter separação de responsabilidades**
   - CSS apenas para apresentação
   - Não tentar resolver problemas de lógica com CSS

## Exemplos Práticos

### Exemplo 1: Estilizar um botão específico

**FXML:**
```xml
<Button fx:id="adicionarButton" text="Novo"/>
```

**CSS:**
```css
#adicionarButton {
    -fx-background-color: #27AE60;
}
```

### Exemplo 2: Estilizar todos os botões

**CSS:**
```css
.button {
    -fx-background-color: #3498DB;
    -fx-text-fill: white;
}
```

### Exemplo 3: Aplicar classe dinamicamente (Controller)

**Controller:**
```java
if (condicao) {
    button.getStyleClass().add("success-message");
}
```

**CSS:**
```css
.success-message {
    -fx-text-fill: #27AE60;
    -fx-font-weight: bold;
}
```

## Próximos Passos

1. Estrutura de pastas criada (`view/styles/`)
2. CSS principal criado (`main.css`)
3. AppView modificado para carregar CSS
4. Testar estilos em todos os FXMLs
5. Criar temas alternativos (se necessário)
6. Documentar componentes customizados

## Referências

- [JavaFX CSS Reference Guide](https://openjfx.io/javadoc/11/javafx.graphics/javafx/scene/doc-files/cssref.html)
- [JavaFX CSS Tutorial](https://docs.oracle.com/javafx/2/css_tutorial/jfxpub-css_tutorial.htm)
- Arquitetura MVC do projeto: `docs/easystop-uml.md`

---

**Lembre-se**: CSS é apenas apresentação. Toda lógica fica no Controller e Model!
