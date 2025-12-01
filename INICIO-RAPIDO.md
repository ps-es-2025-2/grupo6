# Início Rápido - EasyStop

## Forma Mais Simples de Rodar

### Opção 1: BlueJ (Mais Fácil)

1. **Abra o BlueJ**
2. **Project → Open Project...**
3. Selecione a pasta `grupo6`
4. **Execute a aplicação:**
   - Clique com botão direito em **`AppView`** (na pasta `view`)
   - Selecione **`void main(String[] args)`**
   - Clique em **OK**
5. O BlueJ vai compilar automaticamente todas as classes necessárias
6. Se aparecer algum erro de compilação, o BlueJ mostrará quais classes precisam ser compiladas

**Pronto!** A aplicação deve abrir!

**Nota**: O BlueJ compila automaticamente as dependências quando você executa. Se houver erro com `StatusPagamento`, compile essa classe primeiro (clique direito → Compile) e depois execute novamente.

---

### Opção 2: Script Automático

#### Windows:
1. Abra a pasta `grupo6` no Explorador
2. Clique duas vezes em **`EXECUTAR.bat`**
3. Aguarde a compilação e execução

#### Linux/Mac:
```bash
cd grupo6
chmod +x EXECUTAR.sh
./EXECUTAR.sh
```

**Nota**: Os scripts foram atualizados para compilar na ordem correta!

---

### Opção 3: Linha de Comando

```bash
# 1. Entre na pasta do projeto
cd grupo6

# 2. Compile na ordem correta (enums primeiro!)
javac -d . model/enums/*.java
javac -d . -cp "." model/*.java model/service/*.java
javac -d . -cp "." view/*.java view/components/*.java
javac -d . -cp "." controller/*.java

# 3. Execute
java -cp "." controller.AppController
```

---

## Se Der Erro: NoClassDefFoundError - StatusPagamento

Este é um erro comum! A solução é compilar na ordem correta.

**Solução Rápida no BlueJ:**
1. Compile `model/enums/StatusPagamento` primeiro
2. Depois execute `AppView` novamente

---

## Outros Erros

### Erro: "Java não encontrado"
- Instale o Java JDK 11+ de [adoptium.net](https://adoptium.net/)

### Erro: "ClassNotFoundException"
- Adicione as bibliotecas ORMLite no BlueJ:
  - **Tools → Preferences → Libraries → Add JAR...**
  - Adicione: sqlite-jdbc, ormlite-core, ormlite-jdbc, slf4j-api, slf4j-simple

### Erro: "JavaFX runtime components are missing"
- Baixe JavaFX de [openjfx.io](https://openjfx.io/)
- Adicione ao classpath ou use `--module-path`

---

## Documentação Completa

Para instruções detalhadas, veja: **`COMO-RODAR.md`**

---

## Checklist Rápido

- [ ] Java 11+ instalado
- [ ] BlueJ instalado (ou linha de comando configurada)
- [ ] Bibliotecas ORMLite baixadas
- [ ] Projeto aberto no BlueJ (ou na pasta `grupo6`)
- [ ] Compilar enums primeiro!
