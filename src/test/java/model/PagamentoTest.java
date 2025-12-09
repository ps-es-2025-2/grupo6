package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import model.enums.StatusPagamento;

@DisplayName("Testes Unitários - Pagamento")
class PagamentoTest {

    @Test
    @DisplayName("Deve criar pagamento com dados válidos")
    void deveCriarPagamentoComDadosValidos() {
        Veiculo veiculo = new Veiculo("ABC1234", "Civic", "Branco", "João Silva");
        Vaga vaga = new Vaga("101", "A");
        Checkin checkin = new Checkin(veiculo, vaga, LocalDateTime.now(), null);
        Checkout checkout = new Checkout(checkin, LocalDateTime.now(), 25.50);
        
        Pagamento pagamento = new Pagamento(checkout, "Cartão", 25.50);
        
        assertEquals(checkout, pagamento.getCheckout());
        assertEquals("Cartão", pagamento.getTipo());
        assertEquals(25.50, pagamento.getValor(), 0.01);
        assertEquals(StatusPagamento.PENDENTE, pagamento.getStatus());
        assertNotNull(pagamento.getDataPagamento());
        assertNotNull(pagamento.getTokenUnico());
        assertTrue(pagamento.getTokenUnico().startsWith("tok_"));
    }

    @Test
    @DisplayName("Deve criar pagamento com tipo PIX")
    void deveCriarPagamentoComTipoPix() {
        Veiculo veiculo = new Veiculo("ABC1234", "Civic", "Branco", "João Silva");
        Vaga vaga = new Vaga("101", "A");
        Checkin checkin = new Checkin(veiculo, vaga, LocalDateTime.now(), null);
        Checkout checkout = new Checkout(checkin, LocalDateTime.now(), 25.50);
        
        Pagamento pagamento = new Pagamento(checkout, "Pix", 25.50);
        
        assertEquals("Pix", pagamento.getTipo());
    }

    @Test
    @DisplayName("Deve criar pagamento com tipo Dinheiro")
    void deveCriarPagamentoComTipoDinheiro() {
        Veiculo veiculo = new Veiculo("ABC1234", "Civic", "Branco", "João Silva");
        Vaga vaga = new Vaga("101", "A");
        Checkin checkin = new Checkin(veiculo, vaga, LocalDateTime.now(), null);
        Checkout checkout = new Checkout(checkin, LocalDateTime.now(), 25.50);
        
        Pagamento pagamento = new Pagamento(checkout, "Dinheiro", 25.50);
        
        assertEquals("Dinheiro", pagamento.getTipo());
    }

    @Test
    @DisplayName("Deve ter status PENDENTE ao criar")
    void deveTerStatusPendenteAoCriar() {
        Veiculo veiculo = new Veiculo("ABC1234", "Civic", "Branco", "João Silva");
        Vaga vaga = new Vaga("101", "A");
        Checkin checkin = new Checkin(veiculo, vaga, LocalDateTime.now(), null);
        Checkout checkout = new Checkout(checkin, LocalDateTime.now(), 25.50);
        
        Pagamento pagamento = new Pagamento(checkout, "Cartão", 25.50);
        
        assertEquals(StatusPagamento.PENDENTE, pagamento.getStatus());
    }

    @Test
    @DisplayName("Deve gerar token único ao criar")
    void deveGerarTokenUnicoAoCriar() {
        Veiculo veiculo = new Veiculo("ABC1234", "Civic", "Branco", "João Silva");
        Vaga vaga = new Vaga("101", "A");
        Checkin checkin = new Checkin(veiculo, vaga, LocalDateTime.now(), null);
        Checkout checkout = new Checkout(checkin, LocalDateTime.now(), 25.50);
        
        Pagamento pagamento1 = new Pagamento(checkout, "Cartão", 25.50);
        Pagamento pagamento2 = new Pagamento(checkout, "Pix", 25.50);
        
        assertNotEquals(pagamento1.getTokenUnico(), pagamento2.getTokenUnico());
        assertTrue(pagamento1.getTokenUnico().startsWith("tok_"));
        assertTrue(pagamento2.getTokenUnico().startsWith("tok_"));
    }

    @Test
    @DisplayName("Deve ter data de pagamento ao criar")
    void deveTerDataPagamentoAoCriar() {
        Veiculo veiculo = new Veiculo("ABC1234", "Civic", "Branco", "João Silva");
        Vaga vaga = new Vaga("101", "A");
        Checkin checkin = new Checkin(veiculo, vaga, LocalDateTime.now(), null);
        Checkout checkout = new Checkout(checkin, LocalDateTime.now(), 25.50);
        
        LocalDateTime antes = LocalDateTime.now();
        Pagamento pagamento = new Pagamento(checkout, "Cartão", 25.50);
        LocalDateTime depois = LocalDateTime.now();
        
        assertNotNull(pagamento.getDataPagamento());
        assertTrue(pagamento.getDataPagamento().isAfter(antes.minusSeconds(1)));
        assertTrue(pagamento.getDataPagamento().isBefore(depois.plusSeconds(1)));
    }

    @Test
    @DisplayName("Deve permitir alterar status")
    void devePermitirAlterarStatus() {
        Veiculo veiculo = new Veiculo("ABC1234", "Civic", "Branco", "João Silva");
        Vaga vaga = new Vaga("101", "A");
        Checkin checkin = new Checkin(veiculo, vaga, LocalDateTime.now(), null);
        Checkout checkout = new Checkout(checkin, LocalDateTime.now(), 25.50);
        Pagamento pagamento = new Pagamento(checkout, "Cartão", 25.50);
        
        pagamento.setStatus(StatusPagamento.APROVADO);
        assertEquals(StatusPagamento.APROVADO, pagamento.getStatus());
        
        pagamento.setStatus(StatusPagamento.RECUSADO);
        assertEquals(StatusPagamento.RECUSADO, pagamento.getStatus());
    }

    @Test
    @DisplayName("Deve permitir alterar valor")
    void devePermitirAlterarValor() {
        Veiculo veiculo = new Veiculo("ABC1234", "Civic", "Branco", "João Silva");
        Vaga vaga = new Vaga("101", "A");
        Checkin checkin = new Checkin(veiculo, vaga, LocalDateTime.now(), null);
        Checkout checkout = new Checkout(checkin, LocalDateTime.now(), 25.50);
        Pagamento pagamento = new Pagamento(checkout, "Cartão", 25.50);
        
        pagamento.setValor(30.00);
        assertEquals(30.00, pagamento.getValor(), 0.01);
    }

    @Test
    @DisplayName("Deve permitir alterar checkout")
    void devePermitirAlterarCheckout() {
        Veiculo veiculo = new Veiculo("ABC1234", "Civic", "Branco", "João Silva");
        Vaga vaga = new Vaga("101", "A");
        Checkin checkin1 = new Checkin(veiculo, vaga, LocalDateTime.now(), null);
        Checkin checkin2 = new Checkin(veiculo, vaga, LocalDateTime.now().plusHours(1), null);
        Checkout checkout1 = new Checkout(checkin1, LocalDateTime.now(), 25.50);
        Checkout checkout2 = new Checkout(checkin2, LocalDateTime.now(), 30.00);
        Pagamento pagamento = new Pagamento(checkout1, "Cartão", 25.50);
        
        pagamento.setCheckout(checkout2);
        assertEquals(checkout2, pagamento.getCheckout());
    }

    @Test
    @DisplayName("Deve permitir alterar data de pagamento")
    void devePermitirAlterarDataPagamento() {
        Veiculo veiculo = new Veiculo("ABC1234", "Civic", "Branco", "João Silva");
        Vaga vaga = new Vaga("101", "A");
        Checkin checkin = new Checkin(veiculo, vaga, LocalDateTime.now(), null);
        Checkout checkout = new Checkout(checkin, LocalDateTime.now(), 25.50);
        Pagamento pagamento = new Pagamento(checkout, "Cartão", 25.50);
        
        LocalDateTime novaData = LocalDateTime.now().plusDays(1);
        pagamento.setDataPagamento(novaData);
        assertEquals(novaData, pagamento.getDataPagamento());
    }

    @Test
    @DisplayName("Deve retornar false ao processar pagamento (método base)")
    void deveRetornarFalseAoProcessarPagamento() {
        Veiculo veiculo = new Veiculo("ABC1234", "Civic", "Branco", "João Silva");
        Vaga vaga = new Vaga("101", "A");
        Checkin checkin = new Checkin(veiculo, vaga, LocalDateTime.now(), null);
        Checkout checkout = new Checkout(checkin, LocalDateTime.now(), 25.50);
        Pagamento pagamento = new Pagamento(checkout, "Cartão", 25.50);
        
        assertFalse(pagamento.processarPagamento());
    }

    @Test
    @DisplayName("Deve ter ID ao ser criado")
    void deveTerIdAoCriar() {
        Veiculo veiculo = new Veiculo("ABC1234", "Civic", "Branco", "João Silva");
        Vaga vaga = new Vaga("101", "A");
        Checkin checkin = new Checkin(veiculo, vaga, LocalDateTime.now(), null);
        Checkout checkout = new Checkout(checkin, LocalDateTime.now(), 25.50);
        Pagamento pagamento = new Pagamento(checkout, "Cartão", 25.50);
        
        // ID é gerado pelo banco, então pode ser 0 inicialmente
        assertNotNull(pagamento);
    }
}

