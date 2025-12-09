package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;

@DisplayName("Testes Unitários - Checkin")
class CheckinTest {

    @Test
    @DisplayName("Deve criar checkin com dados válidos")
    void deveCriarCheckinComDadosValidos() {
        Veiculo veiculo = new Veiculo("ABC1234", "Civic", "Branco", "João Silva");
        Vaga vaga = new Vaga("101", "A");
        LocalDateTime horario = LocalDateTime.now();
        
        Checkin checkin = new Checkin(veiculo, vaga, horario, "Observação teste");
        
        assertEquals(veiculo, checkin.getVeiculo());
        assertEquals(vaga, checkin.getVaga());
        assertEquals(horario, checkin.getHorarioEntrada());
        assertEquals("Observação teste", checkin.getObservacao());
        assertFalse(checkin.isFinalizado());
    }

    @Test
    @DisplayName("Deve validar horário de entrada no formato correto")
    void deveValidarHorarioEntradaFormatoCorreto() {
        assertDoesNotThrow(() -> {
            Checkin.validarHorarioEntrada("14:30");
        });
    }

    @Test
    @DisplayName("Deve validar horário de entrada no limite (00:00)")
    void deveValidarHorarioEntradaLimiteInferior() {
        assertDoesNotThrow(() -> {
            Checkin.validarHorarioEntrada("00:00");
        });
    }

    @Test
    @DisplayName("Deve validar horário de entrada no limite (23:59)")
    void deveValidarHorarioEntradaLimiteSuperior() {
        assertDoesNotThrow(() -> {
            Checkin.validarHorarioEntrada("23:59");
        });
    }

    @Test
    @DisplayName("Deve rejeitar horário de entrada nulo")
    void deveRejeitarHorarioEntradaNulo() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Checkin.validarHorarioEntrada(null);
        });
        assertEquals("A data e hora são obrigatórias.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve rejeitar horário de entrada vazio")
    void deveRejeitarHorarioEntradaVazio() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Checkin.validarHorarioEntrada("");
        });
        assertEquals("A data e hora são obrigatórias.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve rejeitar horário de entrada inválido (formato incorreto)")
    void deveRejeitarHorarioEntradaFormatoIncorreto() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Checkin.validarHorarioEntrada("14:30:00");
        });
        assertTrue(exception.getMessage().contains("formato inválido"));
    }

    @Test
    @DisplayName("Deve rejeitar horário de entrada com hora inválida (24:00)")
    void deveRejeitarHorarioEntradaHoraInvalida() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Checkin.validarHorarioEntrada("24:00");
        });
        assertTrue(exception.getMessage().contains("formato inválido"));
    }

    @Test
    @DisplayName("Deve rejeitar horário de entrada com minuto inválido (14:60)")
    void deveRejeitarHorarioEntradaMinutoInvalido() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Checkin.validarHorarioEntrada("14:60");
        });
        assertTrue(exception.getMessage().contains("formato inválido"));
    }

    @Test
    @DisplayName("Deve aceitar horário de entrada com espaços (normalizar)")
    void deveAceitarHorarioEntradaComEspacos() {
        assertDoesNotThrow(() -> {
            Checkin.validarHorarioEntrada("  14:30  ");
        });
    }

    @Test
    @DisplayName("Deve permitir alterar veículo")
    void devePermitirAlterarVeiculo() {
        Veiculo veiculo1 = new Veiculo("ABC1234", "Civic", "Branco", "João Silva");
        Veiculo veiculo2 = new Veiculo("XYZ5678", "Corolla", "Preto", "Maria Santos");
        Vaga vaga = new Vaga("101", "A");
        Checkin checkin = new Checkin(veiculo1, vaga, LocalDateTime.now(), null);
        
        checkin.setVeiculo(veiculo2);
        assertEquals(veiculo2, checkin.getVeiculo());
    }

    @Test
    @DisplayName("Deve permitir alterar vaga")
    void devePermitirAlterarVaga() {
        Veiculo veiculo = new Veiculo("ABC1234", "Civic", "Branco", "João Silva");
        Vaga vaga1 = new Vaga("101", "A");
        Vaga vaga2 = new Vaga("202", "B");
        Checkin checkin = new Checkin(veiculo, vaga1, LocalDateTime.now(), null);
        
        checkin.setVaga(vaga2);
        assertEquals(vaga2, checkin.getVaga());
    }

    @Test
    @DisplayName("Deve permitir alterar observação")
    void devePermitirAlterarObservacao() {
        Veiculo veiculo = new Veiculo("ABC1234", "Civic", "Branco", "João Silva");
        Vaga vaga = new Vaga("101", "A");
        Checkin checkin = new Checkin(veiculo, vaga, LocalDateTime.now(), null);
        
        checkin.setObservacao("Nova observação");
        assertEquals("Nova observação", checkin.getObservacao());
    }

    @Test
    @DisplayName("Deve permitir marcar checkin como finalizado")
    void devePermitirMarcarCheckinComoFinalizado() {
        Veiculo veiculo = new Veiculo("ABC1234", "Civic", "Branco", "João Silva");
        Vaga vaga = new Vaga("101", "A");
        Checkin checkin = new Checkin(veiculo, vaga, LocalDateTime.now(), null);
        
        assertFalse(checkin.isFinalizado());
        checkin.setFinalizado(true);
        assertTrue(checkin.isFinalizado());
    }

    @Test
    @DisplayName("Deve permitir alterar horário de entrada")
    void devePermitirAlterarHorarioEntrada() {
        Veiculo veiculo = new Veiculo("ABC1234", "Civic", "Branco", "João Silva");
        Vaga vaga = new Vaga("101", "A");
        LocalDateTime horario1 = LocalDateTime.now();
        LocalDateTime horario2 = horario1.plusHours(1);
        Checkin checkin = new Checkin(veiculo, vaga, horario1, null);
        
        checkin.setHorarioEntrada(horario2);
        assertEquals(horario2, checkin.getHorarioEntrada());
    }
}

