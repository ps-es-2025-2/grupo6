
# Diagramas UML – EasyStop

Este diretório reúne os diagramas UML do sistema **EasyStop**, utilizados como artefatos
formais de apoio à modelagem de requisitos, análise do domínio e validação do alinhamento
entre documentação e implementação.

Os diagramas aqui descritos foram elaborados com base no **Documento de Casos de Uso – EasyStop (Versão 2.0)** e têm como objetivo substituir representações informais e arquivos binários, priorizando documentação versionável e rastreável.

Sempre que possível, os diagramas devem ser mantidos em formato de código-fonte
(PlantUML ou equivalente), permitindo controle de versão, revisão colaborativa e
avaliação técnica mais precisa.

---

## Diagrama Global de Casos de Uso

**Arquivo sugerido:**  
`usecase-diagram-geral.puml`

**Finalidade:**  
Apresentar uma visão geral das funcionalidades do sistema EasyStop, evidenciando os
atores envolvidos e os principais casos de uso organizados por épicos.

**Casos de uso representados:**
- UC-01 – Registrar Check-in de Veículo
- UC-02 – Registrar Check-out
- UC-03 – Registrar Pagamento
- UC-04 – Exibir Mapa de Vagas
- UC-05 – Sugerir Vaga Livre
- UC-06 – Monitorar Ocupação
- UC-07 – Emitir Alerta de Lotação
- UC-08 – Gerar Relatório de Faturamento
- UC-09 – Calcular Tempo Médio de Permanência (não implementado)
- UC-10 – Identificar Horários de Pico (não implementado)
- UC-11 – Histórico de Clientes (não implementado)

---

## Diagramas de Atividades

**Finalidade:**  
Detalhar o fluxo de execução dos casos de uso, incluindo fluxo principal, fluxos
alternativos e fluxos de exceção descritos na documentação de requisitos.

**Diagramas recomendados:**
- UC-01 – Registrar Check-in (`uc01-atividade.puml`)
- UC-02 – Registrar Check-out (`uc02-atividade.puml`)
- UC-03 – Registrar Pagamento (`uc03-atividade.puml`)
- UC-06 – Monitorar Ocupação (`uc06-atividade.puml`)
- UC-07 – Emitir Alerta de Lotação (`uc07-atividade.puml`)

Esses diagramas reforçam a clareza operacional e a rastreabilidade entre requisitos e comportamento do sistema.

---

## Diagramas de Sequência

**Finalidade:**  
Representar a interação entre os atores e as camadas do sistema (visão, controle e
modelo), evidenciando a ordem das mensagens trocadas durante a execução dos casos de uso.

**Diagramas sugeridos:**
- UC-01 – Registrar Check-in (`uc01-sequencia.puml`)
- UC-02 – Registrar Check-out (`uc02-sequencia.puml`)
- UC-03 – Registrar Pagamento (`uc03-sequencia.puml`)
- UC-07 – Emitir Alerta de Lotação (`uc07-sequencia.puml`)

---

## Diagramas de Estados

**Finalidade:**  
Representar os ciclos de vida e mudanças de estado das principais entidades do domínio.

**Diagramas recomendados:**
- Estado da Vaga (`estado-vaga.puml`)
- Estado do Check-in / Check-out (`estado-checkin.puml`)
- Estado do Pagamento (`estado-pagamento.puml`)

Esses diagramas estão diretamente relacionados aos casos de uso UC-01, UC-02 e UC-03.

---

## Observações sobre Implementação

Os casos de uso **UC-09, UC-10 e UC-11** ainda não foram implementados. Os diagramas
associados a esses casos têm caráter conceitual e de planejamento, sendo utilizados para
demonstrar a visão futura do sistema e a rastreabilidade dos requisitos.

---

## Organização Recomendada do Diretório

```text
docs/uml/
├── usecase-diagram-geral.puml
├── atividades/
│   ├── uc01-atividade.puml
│   ├── uc02-atividade.puml
│   ├── uc03-atividade.puml
│   ├── uc06-atividade.puml
│   └── uc07-atividade.puml
├── sequencia/
│   ├── uc01-sequencia.puml
│   ├── uc02-sequencia.puml
│   ├── uc03-sequencia.puml
│   └── uc07-sequencia.puml
├── estados/
│   ├── estado-vaga.puml
│   ├── estado-checkin.puml
│   └── estado-pagamento.puml
└── README.md
