# Visão Geral do Sistema EasyStop

## 1. Introdução

O **EasyStop** é um sistema acadêmico desenvolvido para apoiar a gestão de estacionamentos, substituindo controles manuais por um fluxo digital estruturado. O sistema foi projetado no contexto da disciplina de Projeto de Software, com foco na aplicação de conceitos de engenharia de software, arquitetura em camadas, padrões de projeto e boas práticas de organização e manutenção de código.

A solução busca organizar e automatizar os processos de entrada e saída de veículos, controle de vagas, cálculo de permanência e registro de pagamentos, garantindo maior confiabilidade das informações e redução de erros operacionais.

---

## 2. Problema e Motivação

A gestão manual de estacionamentos envolve anotações em papel, cálculos manuais de tempo e valor, além de controle impreciso das vagas disponíveis. Esses fatores aumentam a probabilidade de erros humanos, atrasos no atendimento, inconsistências financeiras e dificuldade de auditoria.

O EasyStop surge como resposta a essas dificuldades, oferecendo um sistema que centraliza as informações, automatiza cálculos e mantém o histórico das operações, proporcionando maior transparência e controle para atendentes e gestores.

---

## 3. Objetivos do Sistema

O sistema tem como objetivos principais:

- Registrar entradas e saídas de veículos de forma estruturada
- Controlar a ocupação e disponibilidade de vagas
- Automatizar o cálculo de tempo de permanência e valores a serem pagos
- Registrar pagamentos utilizando diferentes meios
- Fornecer informações consolidadas para acompanhamento operacional

---

## 4. Escopo do Sistema

O EasyStop contempla funcionalidades relacionadas ao controle operacional de estacionamentos, incluindo:

- Check-in e check-out de veículos
- Associação de veículos a vagas
- Processamento de pagamentos em dinheiro, cartão e Pix
- Monitoramento da ocupação do estacionamento
- Emissão de alertas de lotação

Funcionalidades de análise avançada e relatórios gerenciais encontram-se descritas na documentação, ainda que algumas não estejam implementadas.

---

## 5. Usuários do Sistema

O principal usuário do sistema é o **Atendente**, responsável por operar as funcionalidades de check-in, check-out, visualização de vagas, processamento de pagamentos e acompanhamento da ocupação.

O sistema EasyStop atua como agente automatizado para validações, cálculos, persistência de dados e atualização dos estados das entidades envolvidas.

---

## 6. Visão Funcional

De forma geral, o funcionamento do sistema ocorre da seguinte maneira:

O atendente registra a entrada de um veículo selecionando uma vaga disponível. Durante a permanência do veículo, o sistema mantém o controle da ocupação. No momento da saída, o atendente registra o checkout, informa os dados necessários, o sistema calcula o valor devido e processa o pagamento. Após a conclusão, a vaga é liberada e os registros são persistidos.

---

## 7. Restrições e Premissas

O sistema foi desenvolvido como uma aplicação desktop, utilizando Java, JavaFX e banco de dados SQLite. A persistência é realizada localmente, sem dependência de serviços externos reais para pagamento, sendo os gateways simulados conforme descrito na documentação.

O uso do sistema pressupõe que veículos e vagas estejam previamente cadastrados e que o sistema esteja operacional durante as atividades de atendimento.

---

## 8. Relação com Outras Documentações

Esta visão geral complementa os seguintes documentos do projeto:

- `arquitetura-sistema.md`, que descreve a estrutura arquitetural e decisões técnicas
- `casos-de-uso.md`, que detalha os fluxos funcionais do sistema
- README principal do projeto, que apresenta uma visão resumida da aplicação

---

## 9. Contexto Acadêmico

Este documento tem finalidade exclusivamente acadêmica e integra o conjunto de artefatos exigidos na disciplina de Projeto de Software, servindo como base para compreensão global do sistema EasyStop.