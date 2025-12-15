# Elicitação e Validação de Requisitos – Sistema EasyStop

## 1. Introdução

Este documento descreve o processo de **elicitação e validação de requisitos** do sistema **EasyStop**, desenvolvido no contexto acadêmico da disciplina de Projeto de Software. O objetivo deste artefato é apresentar como os requisitos do sistema foram identificados, analisados e validados, bem como justificar as decisões tomadas durante esse processo.

A elicitação foi conduzida de forma incremental, combinando observação do domínio, discussão em grupo e análise de cenários reais de uso, permitindo a construção de uma visão consistente e alinhada às necessidades do problema abordado.

---

## 2. Contexto da Elicitação

A motivação inicial para o desenvolvimento do EasyStop surgiu a partir de experiências reais vivenciadas por integrantes do grupo. Uma das integrantes reside na região central da cidade e utiliza estacionamentos com alta frequência, seja para atividades acadêmicas, profissionais ou pessoais.

Durante esse uso recorrente, foi possível observar que a maioria dos estacionamentos da região ainda opera com controles manuais ou sistemas extremamente limitados, baseados em anotações em papel ou registros pouco estruturados. Essa realidade resulta em filas, atrasos no atendimento, erros no cálculo do tempo de permanência e inconsistências no valor cobrado dos clientes.

Essa vivência prática permitiu ao grupo identificar, de forma concreta, problemas recorrentes no domínio de estacionamentos urbanos, servindo como ponto de partida para a definição dos requisitos do sistema.

---

## 3. Técnicas de Elicitação Utilizadas

A elicitação de requisitos foi realizada por meio das seguintes abordagens:

- **Observação do domínio**: análise do funcionamento de estacionamentos reais, com foco nos processos de entrada, saída, controle de vagas e pagamento.
- **Discussões em grupo**: troca de experiências entre os integrantes, especialmente a partir do uso frequente de estacionamentos em áreas centrais.
- **Cenários de uso**: definição de situações típicas enfrentadas por atendentes e clientes, utilizadas como base para a criação dos casos de uso.

Essas técnicas permitiram levantar requisitos funcionais e não funcionais de maneira progressiva, evitando soluções abstratas e mantendo o foco em problemas reais.

---

## 4. Identificação dos Requisitos

A partir das atividades de elicitação, foram identificadas necessidades centrais do sistema, tais como:

- Registrar de forma confiável a entrada e saída de veículos
- Controlar a ocupação e disponibilidade de vagas em tempo real
- Automatizar o cálculo de tempo de permanência e valores a pagar
- Reduzir erros humanos associados a registros manuais
- Fornecer informações claras para apoio à tomada de decisão do atendente

Essas necessidades foram refinadas e organizadas na forma de **casos de uso**, descritos em documento específico, garantindo rastreabilidade entre o problema identificado e a solução proposta.

---

## 5. Validação dos Requisitos

A validação dos requisitos ocorreu de maneira contínua ao longo do desenvolvimento do projeto. Cada requisito elicitado foi analisado quanto à sua relevância, viabilidade técnica e alinhamento com o escopo acadêmico da disciplina.

A construção dos casos de uso serviu como principal instrumento de validação, permitindo verificar se os fluxos definidos representavam corretamente as situações observadas no domínio real. Sempre que inconsistências eram identificadas, os requisitos eram revisados e ajustados em consenso pelo grupo.

Além disso, a validação considerou a coerência entre requisitos, arquitetura proposta e tecnologias adotadas, assegurando que as funcionalidades descritas pudessem ser efetivamente suportadas pela solução arquitetural definida.

---

## 6. Restrições e Premissas

Durante a elicitação e validação, algumas restrições foram consideradas:

- O sistema deveria ser desenvolvido como aplicação desktop, utilizando tecnologias já estudadas na disciplina.
- O escopo deveria ser compatível com o tempo disponível para desenvolvimento acadêmico.
- Serviços externos reais, como gateways de pagamento, seriam simulados.

Como premissa, assumiu-se que os usuários do sistema possuem conhecimento básico para operar aplicações desktop e que os dados iniciais, como veículos e vagas, estariam previamente cadastrados.

---

## 7. Considerações Finais

O processo de elicitação e validação de requisitos do EasyStop foi fortemente influenciado por experiências reais de uso de estacionamentos, especialmente em contextos urbanos de alta rotatividade. Essa proximidade com o problema permitiu a definição de requisitos mais realistas e alinhados às necessidades do domínio.

A abordagem adotada contribuiu para a construção de um sistema coerente, com requisitos bem definidos e validados de forma contínua, servindo como base sólida para os demais artefatos do projeto.