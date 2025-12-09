package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;

@DisplayName("Testes Unitários - Checkout")
class CheckoutTest {

    @Test
    @DisplayName("Deve criar checkout com dados válidos")
    void deveCriarCheckoutComDadosValidos() {
        Veiculo veiculo = new Veiculo("ABC1234", "Civic", "Branco", "João Silva");
        Vaga vaga = new Vaga("101", "A");
        Checkin checkin = new Checkin(veiculo, vaga, LocalDateTime.now(), null);
        LocalDateTime horarioSaida = LocalDateTime.now();
        double valorCalculado = 25.50;
        
        Checkout checkout = new Checkout(checkin, horarioSaida, valorCalculado);
        
        assertEquals(checkin, checkout.getCheckin());
        assertEquals(horarioSaida, checkout.getHorarioSaida());
        assertEquals(valorCalculado, checkout.getValorCalculado(), 0.01);
    }

    @Test
    @DisplayName("Deve validar horário de saída no formato correto")
    void deveValidarHorarioSaidaFormatoCorreto() {
        assertDoesNotThrow(() -> {
            Checkout.validarHorarioSaida("14:30");
        });
    }

    @Test
    @DisplayName("Deve rejeitar horário de saída nulo")
    void deveRejeitarHorarioSaidaNulo() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Checkout.validarHorarioSaida(null);
        });
        assertEquals("A data e hora são obrigatórias.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve rejeitar horário de saída vazio")
    void deveRejeitarHorarioSaidaVazio() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Checkout.validarHorarioSaida("");
        });
        assertEquals("A data e hora são obrigatórias.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve rejeitar horário de saída inválido")
    void deveRejeitarHorarioSaidaInvalido() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Checkout.validarHorarioSaida("25:00");
        });
        assertTrue(exception.getMessage().contains("formato inválido"));
    }

    @Test
    @DisplayName("Deve validar valor por hora válido (formato decimal)")
    void deveValidarValorHoraFormatoDecimal() {
        assertDoesNotThrow(() -> {
            Checkout.validarValorHora("10.50");
        });
    }

    @Test
    @DisplayName("Deve validar valor por hora válido (formato com vírgula)")
    void deveValidarValorHoraFormatoVirgula() {
        assertDoesNotThrow(() -> {
            Checkout.validarValorHora("10,50");
        });
    }

    @Test
    @DisplayName("Deve validar valor por hora válido (número inteiro)")
    void deveValidarValorHoraNumeroInteiro() {
        assertDoesNotThrow(() -> {
            Checkout.validarValorHora("10");
        });
    }

    @Test
    @DisplayName("Deve rejeitar valor por hora nulo")
    void deveRejeitarValorHoraNulo() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Checkout.validarValorHora(null);
        });
        assertEquals("O valor por hora é obrigatório.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve rejeitar valor por hora vazio")
    void deveRejeitarValorHoraVazio() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Checkout.validarValorHora("");
        });
        assertEquals("O valor por hora é obrigatório.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve rejeitar valor por hora negativo")
    void deveRejeitarValorHoraNegativo() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Checkout.validarValorHora("-10.00");
        });
        // Com regex atual, valores negativos são tratados como caracteres inválidos
        assertTrue(exception.getMessage().contains("caracteres inválidos"));
    }

    @Test
    @DisplayName("Deve rejeitar valor por hora zero")
    void deveRejeitarValorHoraZero() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Checkout.validarValorHora("0");
        });
        assertEquals("O valor por hora deve ser maior que zero.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve rejeitar valor por hora com caracteres inválidos")
    void deveRejeitarValorHoraCaracteresInvalidos() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Checkout.validarValorHora("abc");
        });
        assertTrue(exception.getMessage().contains("caracteres inválidos"));
    }

    @Test
    @DisplayName("Deve validar campos de checkout juntos")
    void deveValidarCamposCheckoutJuntos() {
        assertDoesNotThrow(() -> {
            Checkout.validarCampos("14:30", "10.50");
        });
    }

    @Test
    @DisplayName("Deve rejeitar quando horário é inválido na validação conjunta")
    void deveRejeitarHorarioInvalidoNaValidacaoConjunta() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Checkout.validarCampos("25:00", "10.50");
        });
        assertTrue(exception.getMessage().contains("formato inválido"));
    }

    @Test
    @DisplayName("Deve rejeitar quando valor é inválido na validação conjunta")
    void deveRejeitarValorInvalidoNaValidacaoConjunta() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Checkout.validarCampos("14:30", "-10.00");
        });
        assertTrue(exception.getMessage().contains("caracteres inválidos"));
    }

    @Test
    @DisplayName("Deve permitir alterar checkin")
    void devePermitirAlterarCheckin() {
        Veiculo veiculo = new Veiculo("ABC1234", "Civic", "Branco", "João Silva");
        Vaga vaga = new Vaga("101", "A");
        Checkin checkin1 = new Checkin(veiculo, vaga, LocalDateTime.now(), null);
        Checkin checkin2 = new Checkin(veiculo, vaga, LocalDateTime.now().plusHours(1), null);
        Checkout checkout = new Checkout(checkin1, LocalDateTime.now(), 25.50);
        
        checkout.setCheckin(checkin2);
        assertEquals(checkin2, checkout.getCheckin());
    }

    @Test
    @DisplayName("Deve permitir alterar horário de saída")
    void devePermitirAlterarHorarioSaida() {
        Veiculo veiculo = new Veiculo("ABC1234", "Civic", "Branco", "João Silva");
        Vaga vaga = new Vaga("101", "A");
        Checkin checkin = new Checkin(veiculo, vaga, LocalDateTime.now(), null);
        LocalDateTime horario1 = LocalDateTime.now();
        LocalDateTime horario2 = horario1.plusHours(2);
        Checkout checkout = new Checkout(checkin, horario1, 25.50);
        
        checkout.setHorarioSaida(horario2);
        assertEquals(horario2, checkout.getHorarioSaida());
    }

    @Test
    @DisplayName("Deve permitir alterar valor calculado")
    void devePermitirAlterarValorCalculado() {
        Veiculo veiculo = new Veiculo("ABC1234", "Civic", "Branco", "João Silva");
        Vaga vaga = new Vaga("101", "A");
        Checkin checkin = new Checkin(veiculo, vaga, LocalDateTime.now(), null);
        Checkout checkout = new Checkout(checkin, LocalDateTime.now(), 25.50);
        
        checkout.setValorCalculado(30.00);
        assertEquals(30.00, checkout.getValorCalculado(), 0.01);
    }

    @Test
    @DisplayName("Deve permitir alterar ID")
    void devePermitirAlterarId() {
        Veiculo veiculo = new Veiculo("ABC1234", "Civic", "Branco", "João Silva");
        Vaga vaga = new Vaga("101", "A");
        Checkin checkin = new Checkin(veiculo, vaga, LocalDateTime.now(), null);
        Checkout checkout = new Checkout(checkin, LocalDateTime.now(), 25.50);
        
        checkout.setId(100);
        assertEquals(100, checkout.getId());
    }
}

