# **Documento de Visão de Casos de Uso**

# **Grupo 6**

#  **EasyStop MVP**

Escrito por: Thâmara
Editado por: Luiz Felipe Pires

## 1\. Introdução

Este documento apresenta a visão de casos de uso para o MVP do Sistema EasyStop, um sistema de gestão de estacionamentos que automatiza os processos de check-in, check-out, controle de vagas e geração de relatórios.

## 2\. Escopo do Produto

O EasyStop MVP focará nas funcionalidades essenciais para operação básica de um estacionamento:

* Registro de entrada e saída de veículos  
* Cálculo automático de tarifas baseado no tempo  
* Controle de vagas disponíveis e ocupadas  
* Geração de relatórios básicos de gestão  
* Sistema de alertas de capacidade
* Controle de formas de pagamento (Cartão, Dinheiro, PIX) com validações específicas
* Sistema de finalização automática de check-ins via checkout
* Interface gráfica completa para todas as operações
* Atualização em tempo real do status de vagas e pagamentos

## 3\. Casos de Uso Principais

### UC-01: Realizar Check-in de Veículo

*Interface Principal: ICheckinService*  
*java*  
*public interface ICheckinService {*  
    *CheckinResponse realizarCheckin(CheckinRequest request);*  
*}*  
Interfaces de Suporte:
* VeiculoRepository - Busca e gerencia veículos cadastrados
* VagaRepository - Atualiza status de ocupação da vaga  
*IVagaRepository* \- Busca vagas disponíveis  
*IRegistroRepository* \- Persiste registros de entrada  
*IValidadorPlaca* \- Valida formato da placa  
*IValidadorHorario* \- Valida horário de entrada  
Fluxo:

1. Usuário seleciona veículo cadastrado ou cadastra novo veículo
2. Sistema valida se veículo não possui check-in ativo
3. Usuário seleciona vaga disponível da lista
4. Sistema valida horário de entrada (formato e consistência)
5. Sistema persiste check-in e marca vaga como ocupada
6. Interface é atualizada refletindo nova ocupação

---

### UC-02: Realizar Check-out e Calcular Valor

Interface Principal: ICheckoutService  
*java*  
*public interface ICheckoutService {*  
    *CheckoutResponse realizarCheckout(String placa);*  
*}*  
Interfaces de Suporte:

* ICalculadoraTarifa \- Calcula valor baseado no tempo  
* IRegistroRepository \- Busca registro de entrada  
* IVagaRepository \- Libera vaga ocupada  
* IRelogioSistema \- Fornece horário atual para cálculo
* CheckinRepository - Busca check-ins ativos e atualiza status
* ServicoPagamento - Coordena processamento do pagamento

Fluxo:
1. Usuário seleciona check-in ativo para checkout
2. Sistema calcula valor baseado em:
   - Tempo de permanência (mínimo 60 minutos)
   - Valor por hora configurável
   - Horário de saída (com validação de formato)
3. Sistema direciona para fluxo de pagamento
4. Após pagamento aprovado, sistema:
   - Marca check-in como finalizado
   - Libera vaga associada
   - Registra checkout completo

---

### *UC-03: Processar Pagamento*

Interface Principal: ServicoPagamento
*java*
*public class ServicoPagamento {*
    *boolean realizarPagamento(Pagamento pagamento);*
*}*
Interfaces de Suporte:
* PagamentoCartao, PagamentoDinheiro, PagamentoPix - Implementações específicas
* PagamentoRepository - Persiste resultado do pagamento

Fluxo:
1. Sistema direciona para interface específica do tipo de pagamento selecionado
2. *Cartão:* Valida número (16 dígitos), CVV (3 dígitos), validade (MM/AA)
3. *Dinheiro:* Valida valor recebido ≥ valor devido, calcula troco
4. *PIX:* Gera código fictício e valida presença
5. Após aprovação, sistema atualiza status do pagamento
6. CheckoutController é notificado para completar o checkout

---

### UC-04: Monitorar Ocupação do Estacionamento

Interface Principal: IMonitorVagasService  
*java*  
*public interface IMonitorVagasService {*  
    *EstatisticasVagas obterEstatisticasVagas();*  
    *boolean estaProximoLotacao();*  
*}*  
Interfaces de Suporte:

* IVagaRepository \- Contabiliza vagas ocupadas/livres  
* IAlertaService \- Dispara notificações quando necessário

---

### UC-05: Gerar Relatórios

Interface Principal: IGeradorRelatorios  
*java*  
*public interface IGeradorRelatorios {*  
    *RelatorioFaturamento gerarRelatorioFaturamento(LocalDate data);*  
    *RelatorioOcupacao gerarRelatorioOcupacao(LocalDate data);*  
*}*  
Interfaces de Suporte:

* IRegistroRepository \- Fornece dados históricos  
* ICalculadoraMetricas \- Calcula médias e estatísticas

### 4\. Arquitetura e Implementação

###Padrões Arquiteturais:
* MVC (Model-View-Controller) com JavaFX
* Repository Pattern para acesso a dados
* Injeção de dependência simplificada
* Polimorfismo para processamento de pagamentos

###Componentes Principais:
* AbstractCrudController - Base reutilizável para operações CRUD
* Database - Gerenciador centralizado de conexão SQLite
* Repositorios - Factory estático para todos os repositórios
* Entidades ORMLite com mapeamento objeto-relacional

###Fluxo de Dados:
* Check-in → Ocupa vaga → Checkout → Processa pagamento → Libera vaga
* Estado consistente mantido através de transações de banco de dados
* Validações em múltiplas camadas (UI, negócio, persistência)

## 5\. Benefícios desta Abordagem

### Para Desenvolvedores:

* Testabilidade: Interfaces pequenas são fáceis de mockar em testes unitários  
* Manutenibilidade: Mudanças em uma funcionalidade não afetam outras  
* Clareza: Cada interface documenta explicitamente sua responsabilidade

### Para a Equipe:

* Desenvolvimento Paralelo: Múltiplos desenvolvedores podem trabalhar em interfaces diferentes  
* Integração Simplificada: Contratos bem definidos entre módulos  
* Reuso: Interfaces especializadas podem ser compostas em novos serviços

### *Benefícios da Implementação:*

*Para a Manutenção:*
* Base de código coesa com separação clara de responsabilidades
* Hooks (before/after) para extensão de funcionalidades
* Tratamento consistente de erros e validações
* Auto-refresh para dados em tempo real

*Para a Experiência do Usuário:*
* Interface intuitiva com feedback imediato
* Fluxos guiados para operações complexas
* Prevenção de erros através de validações proativas
* Status visual claro para vagas e pagamentos

## 6\. Exemplo de Implementação

### Backend (Java/Spring)

*java*

1. *@Service*  
2. *@RequiredArgsConstructor*  
3. *public class CheckinService implements ICheckinService {*  
4.       
5.     *private final IVagaRepository vagaRepository;*  
6.     *private final IRegistroRepository registroRepository;*  
7.     *private final IValidadorPlaca validadorPlaca;*  
8.     *private final IValidadorHorario validadorHorario;*  
9.       
10.     *@Override*  
11.     *public CheckinResponse realizarCheckin(CheckinRequest request) {*  
12.         *validadorPlaca.validar(request.placa());*  
13.         *validadorHorario.validar(request.horarioEntrada());*  
14.           
15.         *Vaga vaga \= vagaRepository.findFirstLivre()*  
16.             *.orElseThrow(() \-\> new EstacionamentoLotadoException());*  
17.           
18.         *Registro registro \= new Registro(request.placa(), request.horarioEntrada(), vaga);*  
19.         *registroRepository.save(registro);*  
20.           
21.         *vaga.ocupar();*  
22.         *vagaRepository.save(vaga);*  
23.           
24.         *return new CheckinResponse(vaga.getNumero());*  
25.     *}*

*}*

26. 

## 4\. Principais Requisitos Não Funcionais

* Desempenho: Tempo de resposta máximo de 2 segundos para operações críticas  
* Disponibilidade: 99,5% de disponibilidade mensal  
* Usabilidade: Interface intuitiva que permita conclusão de check-in/check-out em até 30 segundos  
* Segurança: Criptografia de dados sensíveis em trânsito e em repouso

## 5\. Tecnologias

* Frontend: Vue.js  
* Backend: Java/Groovy com Spring Boot  
* Documentação: Swagger/OpenAPI  
* Testes: TestRails para gestão de casos de teste  
* Banco de dados: A definir (sugestão: PostgreSQL para produção)

