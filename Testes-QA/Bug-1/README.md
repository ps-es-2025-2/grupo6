# Bug 1 - Validação de Caracteres Especiais no Campo Modelo

## Descrição
**Funcionalidade:** Cadastrar veículos

## Passos para Reprodução

**DADO** que estou na tela Veículos  
**QUANDO** aperto em NOVO  
**E** em Modelo eu coloco **`KC@-0543`**  
**E** clico em CONFIRMAR  
**ENTÃO** é cadastrado o carro

![Evidência](Evidencia.jpeg)

## Comportamento Esperado

O sistema avisa que não é permitido esse tipo de caractere na placa.

## Observação

Isso acontece também nos campos **Placa**, **Cor** e **Proprietário**.
