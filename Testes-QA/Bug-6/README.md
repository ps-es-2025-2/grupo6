# Bug 6 - Veículo com Check-In Ativo Permite Novo Check-In

## Descrição
**Funcionalidade:** Realizar Check-In

## Passos para Reprodução

**DADO** que estou na tela Check-Out  
**E** percebo que o veículo **OGW-1C84** ainda não fez Check-Out ao entrar novamente  
**QUANDO** vou para a tela Check-In  
**E** aperto em NOVO  
**E** tento iniciar um Check-In  
**ENTÃO** o veículo indicado aparece disponível para seleção, mesmo sem ter feito Check-Out do check-in anterior

![Evidência 1](Evidencia%201.jpg)

![Evidência 2](Evidencia%202.jpg)

## Comportamento Esperado

O sistema não deve permitir que um veículo com check-in ativo (não finalizado) apareça na lista de veículos disponíveis para novo check-in. O veículo só deve aparecer novamente após realizar o Check-Out e o pagamento correspondente.

