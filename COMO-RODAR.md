# Como Rodar o EasyStop

## Pré-requisitos

### 1. Java JDK 11 ou superior
- **Download**: [Oracle JDK](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) ou [OpenJDK](https://adoptium.net/)
- **Verificar instalação**: Abra o terminal e execute:
  ```bash
  java -version
  javac -version
  ```
- Deve mostrar versão 11 ou superior

### 2. JavaFX 11+
- O JavaFX está incluído no projeto ou precisa ser baixado separadamente
- **Download**: [JavaFX SDK](https://openjfx.io/)

### 3. Bibliotecas ORMLite (JARs)
Baixe os seguintes JARs:
- `sqlite-jdbc-X.X.X.jar` - Driver JDBC para SQLite
- `ormlite-core-X.X.X.jar` - Biblioteca principal do ORMLite
- `ormlite-jdbc-X.X.X.jar` - Conector JDBC do ORMLite
- `slf4j-api-X.X.X.jar` - API de logging
- `slf4j-simple-X.X.X.jar` - Implementação do SLF4J

**Onde baixar**: [Maven Central](https://mvnrepository.com/) ou siga o [tutorial do professor](https://github.com/marceloakira/tutorials/tree/main/orm)

---

## Método 1: Usando BlueJ (Recomendado)

### Passo 1: Instalar BlueJ
1. Baixe em: [https://www.bluej.org/](https://www.bluej.org/)
2. Instale o BlueJ
3. Configure para usar JDK 11+:
   - BlueJ → Preferências → Bibliotecas
   - Selecione o JDK 11+

### Passo 2: Adicionar Bibliotecas no BlueJ
1. Abra BlueJ
2. Vá em **Tools → Preferences** (ou **Edit → Preferences** no Windows)
3. Aba **Libraries**
4. Clique em **Add JAR...**
5. Adicione cada JAR baixado (sqlite-jdbc, ormlite-core, ormlite-jdbc, slf4j-api, slf4j-simple)
6. Clique em **OK** e reinicie o BlueJ

### Passo 3: Abrir o Projeto
1. No BlueJ, clique em **Project → Open Project...**
2. Navegue até a pasta `grupo6`
3. Selecione a pasta e clique em **Open**

### Passo 4: Executar o Projeto

No BlueJ, você executa diretamente a classe principal:

1. **Encontre a classe `AppView`** na janela do BlueJ (está na pasta `view`)
2. **Clique com botão direito** em `AppView`
3. Selecione **`void main(String[] args)`**
4. Clique em **OK**

**O BlueJ vai:**
- Compilar automaticamente a classe `AppView` e todas as suas dependências
- Executar a aplicação JavaFX

**Se aparecer erro de compilação:**
- O BlueJ mostrará quais classes têm erros (aparecem com um círculo vermelho)
- Compile essas classes manualmente (clique direito → Compile)
- Se o erro for com `StatusPagamento`, compile essa classe primeiro:
  - Navegue até `model/enums/StatusPagamento`
  - Clique direito → **Compile**
  - Depois execute `AppView` novamente

**Nota**: O BlueJ não tem opção "Compile All" - ele compila automaticamente quando você executa ou quando você compila uma classe individual.

---

## Método 2: Linha de Comando (Terminal/CMD)

### Passo 1: Configurar Classpath

#### Windows (PowerShell ou CMD):
```powershell
# Navegue até a pasta do projeto
cd "C:\Users\muril\OneDrive - Universidade Federal de Goiás\2.Curso_CC\3.0-SEMESTRE\ProjSw\projeto_Final\grupo6"

# Configure o classpath com todos os JARs
$env:CLASSPATH = ".;C:\caminho\para\sqlite-jdbc.jar;C:\caminho\para\ormlite-core.jar;C:\caminho\para\ormlite-jdbc.jar;C:\caminho\para\slf4j-api.jar;C:\caminho\para\slf4j-simple.jar"
```

#### Linux/Mac:
```bash
# Navegue até a pasta do projeto
cd /caminho/para/grupo6

# Configure o classpath
export CLASSPATH=".:/caminho/para/sqlite-jdbc.jar:/caminho/para/ormlite-core.jar:/caminho/para/ormlite-jdbc.jar:/caminho/para/slf4j-api.jar:/caminho/para/slf4j-simple.jar"
```

### Passo 2: Compilar o Projeto
```bash
# Compilar todos os arquivos Java
javac -d . -cp "$CLASSPATH" controller/*.java model/*.java model/enums/*.java model/service/*.java view/*.java view/components/*.java

# No Windows, use:
javac -d . -cp "%CLASSPATH%" controller/*.java model/*.java model/enums/*.java model/service/*.java view/*.java view/components/*.java
```

### Passo 3: Executar com JavaFX
```bash
# Se JavaFX estiver no classpath
java --module-path /caminho/para/javafx/lib --add-modules javafx.controls,javafx.fxml -cp "$CLASSPATH" view.AppView

# Ou se JavaFX estiver no classpath diretamente:
java -cp "$CLASSPATH" view.AppView
```

---

## Método 3: Script de Execução (Windows)

Crie um arquivo `rodar.bat` na pasta `grupo6`:

```batch
@echo off
echo Compilando EasyStop...
javac -d . -encoding UTF-8 controller/*.java model/*.java model/enums/*.java model/service/*.java view/*.java view/components/*.java

if %errorlevel% neq 0 (
    echo Erro na compilacao!
    pause
    exit /b 1
)

echo Compilacao concluida!
echo Executando aplicacao...
java -cp ".;libs/*" view.AppView

pause
```

**Nota**: Coloque todos os JARs na pasta `libs/` e ajuste o caminho no script.

---

## Solução de Problemas

### Erro: "Cannot find symbol: view.components"
**Solução**: 
- Verifique se a pasta `view/components/` existe
- Verifique se os arquivos `.java` estão compilados
- Recompile todos os arquivos

### Erro: "JavaFX runtime components are missing"
**Solução**:
- Adicione JavaFX ao classpath
- Ou use: `--module-path /caminho/javafx/lib --add-modules javafx.controls,javafx.fxml`

### Erro: "ClassNotFoundException: com.j256.ormlite..."
**Solução**:
- Verifique se os JARs do ORMLite estão no classpath
- Adicione-os no BlueJ (Preferences → Libraries)

### Erro: "Could not find or load main class view.AppView"
**Solução**:
- Certifique-se de estar na pasta `grupo6` ao executar
- Verifique se a classe foi compilada: `ls view/AppView.class` (Linux/Mac) ou `dir view\AppView.class` (Windows)

### Erro ao carregar FXML
**Solução**:
- Verifique se os arquivos `.fxml` estão na pasta `view/`
- Verifique se o caminho no código está correto
- No `AppView.java`, o caminho é: `"view/app.fxml"`

### Erro ao carregar CSS
**Solução**:
- Verifique se `view/styles/main.css` existe
- O código em `AppView.java` deve referenciar: `"view/styles/main.css"`

---

## Verificação Rápida

Antes de executar, verifique:

- [ ] Java 11+ instalado (`java -version`)
- [ ] Todos os JARs baixados e no classpath
- [ ] Pasta `view/components/` existe com todos os arquivos
- [ ] Arquivo `view/styles/main.css` existe
- [ ] Banco de dados `easystop.db` será criado automaticamente

---

## Após Executar

Quando a aplicação abrir, você verá:
- Interface com abas: Veículos, Vagas, Check-in, Checkout, Pagamentos
- Estilos CSS aplicados (cores, botões estilizados)
- Funcionalidade CRUD completa

### Teste Rápido:
1. Aba **Veículos** → Clique em **Novo**
2. Preencha: Placa, Modelo, Cor, Proprietário
3. Clique em **Confirmar**
4. O veículo deve aparecer na tabela!

---

## Documentação Adicional

- **Arquitetura**: `docs/component-architecture.md`
- **CSS**: `docs/css-architecture.md`

---

## Ainda com Problemas?

1. Verifique se está na pasta correta (`grupo6`)
2. Recompile tudo (compile manualmente no BlueJ)
3. Verifique os logs de erro no console
4. Confirme que todos os JARs estão no classpath
