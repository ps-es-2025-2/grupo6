# Testes Unitários - EasyStop

Este diretório contém os testes unitários do projeto EasyStop, utilizando JUnit 5.
Objetivo: validar regras de negócio das entidades (modelo) de forma automatizada.

## Estrutura

Os testes estão organizados seguindo a mesma estrutura de pacotes do código fonte:

```
src/test/java/
└── model/
    ├── VeiculoTest.java
    ├── VagaTest.java
    ├── CheckinTest.java
    ├── CheckoutTest.java
    └── PagamentoTest.java
```

## Como Executar os Testes

### Via Java puro (sem Maven)

Pré-requisitos:
- `junit-platform-console-standalone-1.10.0.jar` disponível em `src/test/java/model/JUnit/`

1) Compilar código principal (raiz do projeto):
```powershell
javac -cp ".;src\test\java\model\JUnit\junit-platform-console-standalone-1.10.0.jar" model\*.java model\enums\*.java controller\*.java view\*.java
```

2) Compilar testes (saída em `target\test-classes`):
```powershell
mkdir -Force target\test-classes
javac -cp ".;src\test\java\model\JUnit\junit-platform-console-standalone-1.10.0.jar" -d target\test-classes src\test\java\model\*.java
```

3) Executar todos os testes:
```powershell
java -jar src\test\java\model\JUnit\junit-platform-console-standalone-1.10.0.jar --class-path ".;target\test-classes" --scan-class-path
```

4) Executar um teste específico:
```powershell
java -jar src\test\java\model\JUnit\junit-platform-console-standalone-1.10.0.jar --class-path ".;target\test-classes" --select-class model.VeiculoTest
```

5) Executar um método específico:
```powershell
java -jar src\test\java\model\JUnit\junit-platform-console-standalone-1.10.0.jar --class-path ".;target\test-classes" --select-method model.CheckoutTest#deveRejeitarValorHoraNegativo
```


### Via IDE

Os testes podem ser executados diretamente pela IDE (IntelliJ IDEA, Eclipse, VS Code, etc.) clicando com o botão direito no arquivo de teste e selecionando "Run Test".

## Cobertura de Testes

### VeiculoTest
- ✅ Criação de veículo com dados válidos
- ✅ Validação de placa (padrão antigo e Mercosul)
- ✅ Validação de modelo, cor e proprietário
- ✅ Rejeição de dados inválidos
- ✅ Normalização de dados (espaços, maiúsculas/minúsculas)
- ✅ Métodos getters/setters
- ✅ Método toString

### VagaTest
- ✅ Criação de vaga com dados válidos
- ✅ Validação de código (apenas números)
- ✅ Validação de setor (apenas letras)
- ✅ Rejeição de dados inválidos
- ✅ Gerenciamento de status de ocupação
- ✅ Métodos getters/setters
- ✅ Método toString

### CheckinTest
- ✅ Criação de checkin com dados válidos
- ✅ Validação de horário de entrada (formato HH:mm)
- ✅ Rejeição de horários inválidos
- ✅ Gerenciamento de status finalizado
- ✅ Métodos getters/setters

### CheckoutTest
- ✅ Criação de checkout com dados válidos
- ✅ Validação de horário de saída
- ✅ Validação de valor por hora (formato decimal e com vírgula)
- ✅ Rejeição de valores negativos ou zero
- ✅ Validação conjunta de campos
- ✅ Métodos getters/setters

### PagamentoTest
- ✅ Criação de pagamento com diferentes tipos (Cartão, PIX, Dinheiro)
- ✅ Geração automática de token único
- ✅ Status inicial PENDENTE
- ✅ Data de pagamento automática
- ✅ Métodos getters/setters
- ✅ Alteração de status

## Tecnologias Utilizadas

- **JUnit 5** (Jupiter) - Framework de testes
## Notas

- Os testes focam em validações de regras de negócio e lógica das entidades
- Não são incluídos testes de integração com banco de dados (requerem configuração adicional)
- Os testes validam tanto casos válidos quanto casos de erro (exceções)

