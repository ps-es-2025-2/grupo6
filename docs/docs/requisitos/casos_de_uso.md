# Casos de Uso – Sistema EasyStop

Documento acadêmico de casos de uso do sistema **EasyStop**, elaborado para a disciplina de Projeto de Software.

Autoria: Thâmara Cordeiro de Castro  
Versão: 2.0

---

## Legenda

- **Ator Principal**: quem inicia a ação
- **Atores Secundários**: sistemas ou entidades auxiliares
- **Fluxo Alternativo (FA)**: caminhos opcionais
- **Fluxo de Exceção (FE)**: situações de erro

---

## Épico 1 – Registro de Entrada e Saída de Veículos

### UC-01 – Registrar Check-in de Veículo

**Objetivo**  
Registrar a entrada de um veículo no estacionamento, associando-o a uma vaga livre e registrando data e hora de entrada.

**Atores**  
Atendente (principal)  
Sistema EasyStop (secundário)

**Pré-condições**  
O sistema deve estar operacional. Devem existir veículos cadastrados e ao menos uma vaga disponível.

**Fluxo Principal**  
O atendente acessa a funcionalidade de check-in, seleciona um veículo e uma vaga livre, informa data e hora de entrada e confirma a operação. O sistema valida os dados, registra o check-in e marca a vaga como ocupada.

**Fluxos Alternativos**  
Caso dados obrigatórios não sejam informados ou o horário esteja em formato inválido, o sistema impede o registro e informa o erro.

**Fluxos de Exceção**  
Falhas na atualização do estado da vaga resultam em mensagem de erro ao usuário.

**Pós-condições**  
O check-in é persistido como não finalizado e a vaga passa a estar ocupada.

---

### UC-02 – Registrar Check-out

**Objetivo**  
Finalizar o atendimento de um veículo, calcular o valor devido, processar o pagamento e liberar a vaga.

**Atores**  
Atendente (principal)  
Sistema EasyStop (secundário)

**Pré-condições**  
Deve existir ao menos um check-in ativo.

**Fluxo Principal**  
O atendente seleciona um check-in ativo, informa data e hora de saída e valor por hora. O sistema calcula o valor total, processa o pagamento, finaliza o check-in e libera a vaga.

**Fluxos Alternativos**  
O valor total é recalculado automaticamente sempre que o horário de saída ou valor por hora é alterado.

**Fluxos de Exceção**  
Pagamento não aprovado, horário inválido ou falhas de persistência impedem a conclusão do checkout.

**Pós-condições**  
O check-in é finalizado, a vaga é liberada e o pagamento é registrado.

---

### UC-03 – Registrar Pagamento

**Objetivo**  
Registrar o pagamento de um checkout por cartão, Pix ou dinheiro.

**Atores**  
Atendente  
Gateway de Pagamento (simulado)

**Pré-condições**  
Deve existir um checkout pendente de pagamento.

**Fluxo Principal**  
O atendente seleciona o meio de pagamento, informa os dados necessários e o sistema processa a transação. Pagamentos aprovados permitem a finalização do checkout.

**Fluxos Alternativos**  
No pagamento em dinheiro, o sistema calcula automaticamente o troco.

---

## Épico 2 – Visualização e Orientação de Vagas

### UC-04 – Exibir Mapa de Vagas

**Objetivo**  
Permitir a visualização gráfica do estado das vagas do estacionamento.

**Status**  
Ainda não implementado.

---

### UC-05 – Sugerir Vaga Livre

**Objetivo**  
Sugerir automaticamente uma vaga livre ao atendente durante o check-in.

**Fluxo Principal**  
O sistema analisa as vagas disponíveis e retorna a vaga mais adequada conforme critérios definidos.

---

## Épico 3 – Controle de Ocupação e Alertas

### UC-06 – Monitorar Ocupação

**Objetivo**  
Exibir, em tempo real, o número de vagas ocupadas e o percentual de ocupação do estacionamento.

---

### UC-07 – Emitir Alerta de Lotação

**Objetivo**  
Emitir alerta quando o estacionamento atingir ocupação igual ou superior a 85%.

---

## Épico 4 – Relatórios e Métricas

### UC-08 – Gerar Relatório de Faturamento

**Objetivo**  
Gerar relatório financeiro diário com dados consolidados.

---

### UC-09 – Calcular Tempo Médio de Permanência

**Status**  
Ainda não implementado.

---

### UC-10 – Identificar Horários de Pico

**Status**  
Ainda não implementado.

---

### UC-11 – Histórico de Clientes

**Status**  
Ainda não implementado.
