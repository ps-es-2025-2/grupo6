# Bug 7 - Coluna Check-Out Exibe NULL na Tela de Pagamentos

## Descrição
**Funcionalidade:** Visualizar Pagamentos

## Passos para Reprodução

**DADO** que estou na tela Pagamentos  
**QUANDO** olho a coluna **Check-Out**  
**ENTÃO** noto que as informações estão exibindo **NULL** ao invés das informações do checkout

![Evidência](Evidencia.jpg)

## Comportamento Esperado

A coluna Check-Out deve exibir as informações do checkout no formato **"PLACA - CÓDIGO_VAGA"** (exemplo: "OGW-1C84 - A"), permitindo identificar qual veículo e em qual vaga o pagamento foi realizado.

