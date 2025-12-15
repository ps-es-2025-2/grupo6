# EasyStop

## Visão Geral

O **EasyStop** é um sistema acadêmico desenvolvido para apoiar a gestão de estacionamentos, automatizando processos de entrada e saída de veículos, controle de vagas e processamento de pagamentos. O projeto foi elaborado no contexto da disciplina de **Projeto de Software**, com foco em organização arquitetural, aplicação de padrões de projeto e boas práticas de engenharia de software.

---

## Objetivo do Projeto

O objetivo principal do EasyStop é substituir controles manuais por um fluxo digital estruturado, reduzindo erros operacionais, aumentando a confiabilidade das informações e melhorando a experiência de funcionários e gestores de estacionamentos.

O sistema busca demonstrar, na prática, a aplicação de conceitos arquiteturais e princípios de design estudados ao longo do curso.

---

## Arquitetura do Sistema

O EasyStop adota uma arquitetura baseada no padrão **Model–View–Controller (MVC)**, organizada em camadas bem definidas:

* **Apresentação**: Interface gráfica desenvolvida com JavaFX e FXML.
* **Controllers**: Responsáveis por intermediar a interação entre a interface e a lógica do sistema.
* **Serviços**: Orquestram os casos de uso e atuam como fachada da aplicação.
* **Domínio**: Contém as entidades e regras de negócio centrais.
* **Persistência**: Gerenciada por repositórios utilizando ORMLite e banco de dados SQLite.

Uma descrição arquitetural detalhada pode ser encontrada no arquivo `arquitetura-sistema.md`.

---

## Padrões e Princípios Aplicados

O projeto faz uso de padrões arquiteturais e de projeto para garantir organização, extensibilidade e manutenibilidade:

* MVC (Model–View–Controller)
* Template Method para processamento de pagamentos
* Strategy (implícito) para variação de meios de pagamento
* Repository para abstração de acesso a dados
* Facade na camada de serviços
* Singleton para controle de acesso ao banco de dados

Também são aplicados princípios como SRP, OCP, LSP, DIP e Separation of Concerns.

---

## Funcionalidades Principais

* Registro de entrada e saída de veículos
* Controle de vagas disponíveis e ocupadas
* Processamento de pagamentos em diferentes meios (Cartão, PIX e Dinheiro)
* Cálculo de valores e validação de transações
* Persistência local das informações

---

## Tecnologias Utilizadas

* **Java**
* **JavaFX / FXML**
* **ORMLite**
* **SQLite**

---

## Estrutura do Projeto

A organização dos diretórios segue as camadas arquiteturais do sistema:

* `model/` – Entidades de domínio e regras de negócio
* `repository/` – Persistência e acesso a dados
* `service/` – Coordenação dos casos de uso
* `controller/` – Controle da interação com a interface
* `view/` – Interface gráfica

---

## Contexto Acadêmico

Este projeto foi desenvolvido exclusivamente para fins acadêmicos, como parte das atividades avaliativas da disciplina de Projeto de Software. Seu foco principal é a demonstração de conceitos arquiteturais, padrões de projeto e princípios de design de software.

---

## Autoria

Projeto desenvolvido pelo **Grupo 6**, com contribuição de:

* Thâmara Cordeiro de Castro

