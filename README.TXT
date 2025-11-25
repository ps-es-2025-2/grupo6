================================================================================
TÍTULO: EasyStop - Gestão de Estacionamentos com JavaFX + ORMLite
================================================================================

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
- SQLite (padrão) ou MariaDB (configurável em `model/Repositorios`)

ESTRUTURA DE PASTAS
-------------------
- `model/`          → Entidades ORMLite, repositórios e acesso ao banco.
- `view/`           → View-models (JavaFX Properties), FXML e `AppView`.
- `controller/`     → Controllers concretos herdando de `AbstractCrudController`.
- `database/`       → Scripts SQL (`database/easystop_schema.sql`).
- `docs/`           → Documentação e diagrama UML (`docs/easystop-uml.md`).

COMO EXECUTAR
-------------
1. Garanta Java 11+ e dependências JavaFX/ORMLite no classpath.
2. (Opcional) Ajuste `model/Repositorios` para MariaDB trocando a URL JDBC.
3. Rode `view.AppView` (ou `controller.AppController.main`). O arquivo
   `easystop.db` será criado automaticamente.

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
  camadas de model/repositorio, mantendo os controllers livres de SQL.
- Scripts SQL e UML documentam o domínio e facilitam migração para MariaDB.

AUTORIA
-------
EasyStop foi reescrito integralmente por GPT-5.1 Codex (Cursor Agent) em 2025,
com base nas especificações fornecidas pelo cliente.