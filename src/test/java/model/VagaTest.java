package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes Unitários - Vaga")
class VagaTest {

    @Test
    @DisplayName("Deve criar vaga com dados válidos")
    void deveCriarVagaComDadosValidos() {
        Vaga vaga = new Vaga("101", "A");
        
        assertEquals("101", vaga.getCodigo());
        assertEquals("A", vaga.getSetor());
        assertFalse(vaga.isOcupada());
    }

    @Test
    @DisplayName("Deve validar código e setor válidos")
    void deveValidarCodigoESetorValidos() {
        assertDoesNotThrow(() -> {
            Vaga.validarDados("101", "A");
        });
    }

    @Test
    @DisplayName("Deve rejeitar código nulo")
    void deveRejeitarCodigoNulo() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Vaga.validarDados(null, "A");
        });
        assertEquals("O código da vaga é obrigatório.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve rejeitar código vazio")
    void deveRejeitarCodigoVazio() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Vaga.validarDados("", "A");
        });
        assertEquals("O código da vaga é obrigatório.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve rejeitar código com letras")
    void deveRejeitarCodigoComLetras() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Vaga.validarDados("A101", "A");
        });
        assertTrue(exception.getMessage().contains("caracteres inválidos"));
    }

    @Test
    @DisplayName("Deve aceitar código apenas com números")
    void deveAceitarCodigoApenasNumeros() {
        assertDoesNotThrow(() -> {
            Vaga.validarDados("12345", "A");
        });
    }

    @Test
    @DisplayName("Deve aceitar código com espaços (normalizar)")
    void deveAceitarCodigoComEspacos() {
        assertDoesNotThrow(() -> {
            Vaga.validarDados("  101  ", "A");
        });
    }

    @Test
    @DisplayName("Deve rejeitar setor nulo")
    void deveRejeitarSetorNulo() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Vaga.validarDados("101", null);
        });
        assertEquals("O setor é obrigatório.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve rejeitar setor vazio")
    void deveRejeitarSetorVazio() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Vaga.validarDados("101", "");
        });
        assertEquals("O setor é obrigatório.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve rejeitar setor com números")
    void deveRejeitarSetorComNumeros() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Vaga.validarDados("101", "A1");
        });
        assertEquals("O setor contém caracteres inválidos.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve aceitar setor apenas com letras")
    void deveAceitarSetorApenasLetras() {
        assertDoesNotThrow(() -> {
            Vaga.validarDados("101", "ABC");
        });
    }

    @Test
    @DisplayName("Deve aceitar setor em minúsculas")
    void deveAceitarSetorMinusculas() {
        assertDoesNotThrow(() -> {
            Vaga.validarDados("101", "a");
        });
    }

    @Test
    @DisplayName("Deve permitir alterar status de ocupação")
    void devePermitirAlterarStatusOcupacao() {
        Vaga vaga = new Vaga("101", "A");
        assertFalse(vaga.isOcupada());
        
        vaga.setOcupada(true);
        assertTrue(vaga.isOcupada());
        
        vaga.setOcupada(false);
        assertFalse(vaga.isOcupada());
    }

    @Test
    @DisplayName("Deve permitir alterar código")
    void devePermitirAlterarCodigo() {
        Vaga vaga = new Vaga("101", "A");
        vaga.setCodigo("202");
        assertEquals("202", vaga.getCodigo());
    }

    @Test
    @DisplayName("Deve permitir alterar setor")
    void devePermitirAlterarSetor() {
        Vaga vaga = new Vaga("101", "A");
        vaga.setSetor("B");
        assertEquals("B", vaga.getSetor());
    }

    @Test
    @DisplayName("Deve retornar toString correto para vaga livre")
    void deveRetornarToStringVagaLivre() {
        Vaga vaga = new Vaga("101", "A");
        String resultado = vaga.toString();
        assertEquals("101 - A (livre)", resultado);
    }

    @Test
    @DisplayName("Deve retornar toString correto para vaga ocupada")
    void deveRetornarToStringVagaOcupada() {
        Vaga vaga = new Vaga("101", "A");
        vaga.setOcupada(true);
        String resultado = vaga.toString();
        assertEquals("101 - A (ocupada)", resultado);
    }
}

