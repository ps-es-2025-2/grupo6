# EasyStop - Gestão de Estacionamentos


SOBRE O PROJETO
---------------
EasyStop é uma aplicação desktop JavaFX estruturada em arquitetura MVC completa,
com formulários FXML prontos para Scene Builder e persistência em SQLite via
ORMLite. O sistema cobre todo o ciclo operacional de um estacionamento:
cadastro de veículos, administração de vagas, check-in, checkout e registro de
pagamentos.

PILHA TECNOLÓGICA
-----------------
- Java 11+
- JavaFX 11 (FXML/Scene Builder)
- ORMLite 6.x (DAO + annotations)


ESTRUTURA DE PASTAS
-------------------
- `model/`          → Entidades ORMLite, repositórios e acesso ao banco.
- `view/`           → View-models (JavaFX Properties), FXML e `AppView`.
- `controller/`     → Controllers concretos herdando de `AbstractCrudController`.
- `database/`       → Scripts SQL (`database/easystop_schema.sql`).
- `docs/`           → Documentação e diagrama UML (`docs/easystop-uml.md`).

COMO EXECUTAR
-------------

### 1️⃣ Pré-requisitos

Antes de rodar o EasyStop, você precisa instalar e configurar algumas ferramentas:

### Java JDK 11+
- Faça download em: [https://www.oracle.com/java/technologies/javase-jdk11-downloads.html](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)  
- Instale seguindo as instruções do seu sistema operacional.  
- Configure a variável de ambiente `JAVA_HOME`.

### BlueJ (Opcional para visualização e execução)
- Faça download em: [https://www.bluej.org/](https://www.bluej.org/)  
- Instale e configure o BlueJ para utilizar o JDK 11+.
- BlueJ permite abrir o projeto, compilar, executar classes Java e visualizar diagramas de relacionamento entre as classes.

### Bibliotecas Necessárias (JARs) [Siga o Tutorial](https://github.com/marceloakira/tutorials/tree/main/orm)
O EasyStop utiliza ORMLite para persistência de dados e SQLite como banco de dados leve. Baixe as seguintes bibliotecas:

- `sqlite-jdbc` – Driver JDBC para SQLite  
- `ormlite-core` – Biblioteca principal do ORMLite  
- `ormlite-jdbc` – Conector JDBC do ORMLite  
- `slf4j-api` – API de logging  
- `slf4j-simple` – Implementação simples do SLF4J


**Como adicionar as bibliotecas no BlueJ:**
1. Abra BlueJ → Preferências → Bibliotecas  
2. Clique em **Adicionar** e selecione os JARs baixados  
3. Reinicie o BlueJ

### 2️⃣ Clonando o Projeto do GitHub

Para obter a versão mais recente do EasyStop:

1. Abra o terminal ou prompt de comando.  
2. Navegue até a pasta onde deseja clonar o projeto.  
3. Execute o comando:

   ```bash
   git clone https://github.com/abraao8401/EasyStop.git


### 3️⃣  Configuração do Banco de Dados

O EasyStop utiliza SQLite, que cria um arquivo `.db` local automaticamente.

- Nenhuma instalação adicional é necessária.  
- O arquivo `easystop.db` será criado na primeira execução do projeto.

### 4️⃣ Executando o Projeto

1. Abra o BlueJ e carregue o projeto EasyStop.  
2. Compile todas as classes (`Compile All`).  
3. Execute a classe principal:`view.AppView`.  
4. O JavaFX carregará a interface com abas para Veículos, Vagas, Check-in, Checkout e Pagamentos.

TUTORIAL PASSO A PASSO
----------------------
1. **Veículos** – Abra a aba *Veículos*, cadastre placa única, modelo, cor e
   proprietário. Confirme para salvar.
2. **Vagas** – Na aba *Vagas*, informe código e setor. O campo “ocupada” é
   apenas leitura e reflete o estado atual.
3. **Check-in** – Escolha veículo, vaga livre e confirme data/hora. A vaga é
   marcada como ocupada automaticamente.
4. **Checkout** – Selecione um check-in pendente, defina horário de saída e o
   valor-hora (padrão R$10). Ao confirmar, o check-in é finalizado e a vaga
   liberada.
5. **Pagamento** – Escolha um checkout sem pagamento, selecione método (Cartão,
   PIX ou Dinheiro) e status. Cada checkout aceita somente um pagamento.

DETALHES ARQUITETURAIS
----------------------
- Controllers convertem entidades ↔ view-models garantindo TableViews reativas.
- `AbstractCrudController` padroniza botões, estados e carregamento inicial.
- Regras como bloqueio/liberação de vagas e cálculo do valor/hora ficam nas
  camadas de model/repositorio.
