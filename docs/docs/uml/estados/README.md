
Diagramas de Estados - EasyStop
Visão Geral
Esta pasta contém os diagramas de estados do sistema EasyStop, elaborados a partir da implementação real do código-fonte. Cada diagrama reflete os atributos de estado existentes nas entidades, enums utilizados e regras de transição implementadas nos controladores, demonstrando o alinhamento entre modelagem e desenvolvimento.

Objetivo
Os diagramas de estados têm como propósito:

Evidenciar o comportamento dinâmico do sistema através de transições bem definidas
Demonstrar alinhamento entre modelagem conceitual e implementação prática
Facilitar a compreensão das regras de negócio através de visualização gráfica
Documentar formalmente os estados possíveis e transições permitidas de cada entidade
Estrutura dos Diagramas
📋 checkin-estados.puml
Estados do Check-in

Atributos de Estado:

finalizado: boolean
Estados:

Ativo - Check-in em andamento (finalizado = false)
Finalizado - Check-in concluído (finalizado = true)
Transições:

Ativo → Finalizado: Finalizar check-in (pagamento aprovado)
Finalizado → Ativo: Desfazer finalização (checkout removido)
Regras de Negócio:

Estado finalizado não permite edição
Estado finalizado não permite remoção
Transições controladas pelo CheckoutController
🚗 vaga-estados.puml
Estados da Vaga

Atributos de Estado:

ocupada: boolean
Estados:

Livre - Vaga disponível (ocupada = false)
Ocupada - Vaga em uso (ocupada = true)
Transições:

Livre → Ocupada: Criar check-in
Ocupada → Livre: Finalizar checkout
Ocupada → Livre: Remover check-in ativo
Livre → Ocupada: Desfazer finalização
Regras de Negócio:

Estado controlado pelos controladores CheckinController e CheckoutController
Transições automáticas baseadas nas operações de check-in/checkout
💳 pagamento-estados.puml
Estados do Pagamento

Atributos de Estado:

status: StatusPagamento (enum)
Estados:

Pendente - Aguardando processamento (PENDENTE)
Aprovado - Pagamento aceito (APROVADO)
Recusado - Pagamento rejeitado (RECUSADO)
Transições:

Pendente → Aprovado: processarPagamento() (dados válidos)
Pendente → Recusado: processarPagamento() (dados inválidos)
Regras de Negócio:

Implementação baseada em Template Method
Checkout só pode ser finalizado com pagamento aprovado
Controle de validação através do método processarPagamento()
🖥️ ui-crud-estados.puml
Estados da Interface CRUD

Estados:

NONE - Interface em repouso
NOVO - Criando nova entidade
ATUALIZAR - Editando entidade existente
DELETAR - Removendo entidade
Transições:

NONE → NOVO: Clicar "Adicionar"
NONE → ATUALIZAR: Clicar "Atualizar"
NONE → DELETAR: Clicar "Deletar"
Estados de Confirmação:

NOVO → NONE: Confirmar/Cancelar
ATUALIZAR → NONE: Confirmar/Cancelar
DELETAR → NONE: Confirmar/Cancelar
Regras de Interface:

Durante operações CRUD: campos habilitados/desabilitados conforme contexto
Botões bloqueados durante processamento
Tabela congelada para evitar modificações simultâneas
Controlado pelo AbstractCrudController
Relação com o Código-Fonte
Mapeamento Implementação-Modelo
Entidade	Atributo de Estado	Controlador	Enum/Classe
Check-in	finalizado: boolean	CheckoutController	-
Vaga	ocupada: boolean	CheckinController/CheckoutController	-
Pagamento	status: StatusPagamento	PagamentoService	StatusPagamento
Interface	state: CrudState	AbstractCrudController	Estados de UI
Padrões Identificados
1.
State Pattern: Implementação explícita de estados em entidades
2.
Template Method: Estrutura para processamento de pagamentos
3.
Observer Pattern: Notificações de mudança de estado entre entidades
4.
State Machine: Transições controladas e validadas
Alinhamento Modelagem-Implementação
Estes diagramas foram elaborados exatamente a partir do código-fonte existente, garantindo:

✅ Fidelidade à Implementação: Cada transição reflete métodos reais do sistema

✅ Atributos Corretos: Estados baseados em propriedades existentes nas entidades

✅ Regras de Negócio: Validações e restrições implementadas nos controladores

✅ Enums Precisos: Estados formais refletidos em tipos enumerados

Como Utilizar
1.
Visualização: Abra os arquivos .puml em qualquer editor PlantUML ou ferramenta online
2.
Validação: Compare com o código-fonte para verificar alinhamento
3.
Documentação: Use como referência para desenvolvimento futuro
4.
Apresentação: Utilize em documentação técnica e apresentações
Manutenção
Atualizações: Modifique diagramas quando o código-fonte evoluir
Validação: Revise periodicamente o alinhamento implementação-modelo
Versionamento: Mantenha sincronia com releases do sistema
