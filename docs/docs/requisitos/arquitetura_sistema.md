# Descrição Arquitetural do Sistema EasyStop

## Controle de Versões da Documentação

| Nome | Data da Atualização |
|---|---|
| Thâmara Cordeiro de Castro | 30/11 |
| Thâmara Cordeiro de Castro | 13/12 |
| Thâmara Cordeiro de Castro | 15/12 |

Trabalho acadêmico da disciplina de Projeto de Software – Grupo 6.

---

## 1. Introdução

O sistema **EasyStop** foi projetado com uma arquitetura modular baseada no padrão **Model–View–Controller (MVC)**. Essa organização promove separação clara de responsabilidades entre domínio, controle e interface, favorecendo manutenibilidade, extensibilidade e baixo acoplamento. A aplicação utiliza JavaFX/FXML para interface gráfica, ORMLite para persistência de dados e SQLite como banco local.

---

## 2. Contexto do Sistema

O EasyStop tem como objetivo apoiar a gestão de estacionamentos, substituindo processos manuais por um fluxo digital estruturado. O sistema organiza entradas e saídas de veículos, controle de vagas e processamento de pagamentos, reduzindo erros operacionais e melhorando a experiência tanto de funcionários quanto de clientes.

Para o gestor, o sistema oferece maior visibilidade sobre ocupação, faturamento e histórico de operações, permitindo controle mais preciso e confiável do negócio.

---

## 3. Decisões Arquiteturais

A arquitetura foi definida para atender aos principais fluxos do sistema, como check-in, check-out e pagamento, com foco em evolução e manutenção.

### Uso do padrão MVC

O padrão MVC foi adotado para isolar a interface gráfica da lógica de negócio e do domínio, permitindo mudanças na apresentação sem impacto direto nas regras centrais do sistema.

### Persistência com ORMLite e SQLite

A combinação de ORMLite e SQLite foi escolhida para simplificar o acesso a dados e atender ao contexto de uma aplicação local, reduzindo complexidade e dependências externas.

### Regras de pagamento no domínio

As regras de processamento de pagamento estão concentradas no domínio, evitando lógica de negócio na interface ou em camadas inadequadas.

---

## 4. Padrões Arquiteturais e de Projeto

Além do MVC, o sistema utiliza padrões de projeto para organizar e estruturar comportamentos importantes:

- **Template Method** para definir o fluxo comum de processamento de pagamentos.
- **Strategy (implícito)** para permitir variação do algoritmo conforme o meio de pagamento.
- **Repository** para abstração do acesso a dados.
- **Facade** na camada de serviços para simplificar a execução dos casos de uso.
- **Singleton** para controle de acesso à conexão com o banco de dados.

Esses padrões contribuem para uma arquitetura coesa e alinhada aos princípios SOLID.

---

## 5. Arquitetura Geral

### Visão em Camadas

O sistema é organizado em camadas bem definidas:

- **Apresentação**: interface JavaFX/FXML.
- **Controle**: controllers responsáveis por intermediar ações do usuário.
- **Serviços**: orquestração dos casos de uso.
- **Domínio**: entidades e regras de negócio.
- **Persistência**: repositórios e infraestrutura de banco de dados.

---

## 6. Fluxo Arquitetural de um Caso de Uso

No processamento de pagamento, o fluxo inicia-se na interface, onde o usuário seleciona o meio de pagamento. O controller encaminha a solicitação para a camada de serviços, que instancia o tipo de pagamento adequado.

O domínio executa o método `processarPagamento`, definido na classe abstrata **Pagamento**, seguindo o Template Method. Após a validação, os dados são persistidos pelos repositórios e o resultado retorna ao controller para atualização da interface.

---

## 7. Princípios de Design Aplicados

O projeto segue princípios clássicos de design de software:

- **Single Responsibility Principle (SRP)**: cada classe possui uma responsabilidade bem definida.
- **Open/Closed Principle (OCP)**: novos meios de pagamento podem ser adicionados sem alterar código existente.
- **Liskov Substitution Principle (LSP)**: subclasses de pagamento substituem a classe base sem impacto funcional.
- **Dependency Inversion Principle (DIP)**: dependência de abstrações, como interfaces de repositório.
- **Separation of Concerns (SoC)**: responsabilidades distribuídas por camadas.

O equilíbrio entre alta coesão e baixo acoplamento favorece a evolução do sistema.

---

## 8. Template Method no Processamento de Pagamentos

A classe abstrata **Pagamento** define o algoritmo geral de processamento, enquanto as subclasses implementam regras específicas.

```
Pagamento (abstrata)
 ├── PagamentoCartao
 ├── PagamentoPix
 └── PagamentoDinheiro
```

Cada tipo de pagamento valida seus próprios dados e atualiza o status da transação conforme as regras de negócio.

---

## 9. Estrutura de Pastas e Organização

A organização do projeto reflete diretamente as camadas arquiteturais:

- **model**: entidades de domínio e regras de negócio.
- **repository**: acesso a dados e persistência com ORMLite.
- **service**: coordenação dos casos de uso.
- **controller**: controle da interação com a interface.
- **view**: arquivos FXML e componentes visuais.

Essa estrutura facilita navegação, compreensão e manutenção do código.

