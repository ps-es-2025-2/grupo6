package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes Unitários - Veiculo")
class VeiculoTest {

    @Test
    @DisplayName("Deve criar veículo com dados válidos")
    void deveCriarVeiculoComDadosValidos() {
        Veiculo veiculo = new Veiculo("ABC1234", "Civic", "Branco", "João Silva");
        
        assertEquals("ABC1234", veiculo.getPlaca());
        assertEquals("Civic", veiculo.getModelo());
        assertEquals("Branco", veiculo.getCor());
        assertEquals("João Silva", veiculo.getProprietario());
    }

    @Test
    @DisplayName("Deve validar placa no padrão antigo (ABC1234)")
    void deveValidarPlacaPadraoAntigo() {
        assertDoesNotThrow(() -> {
            Veiculo.validarDados("ABC1234", "Civic", "Branco", "João Silva");
        });
    }

    @Test
    @DisplayName("Deve validar placa no padrão Mercosul (ABC1D23)")
    void deveValidarPlacaMercosul() {
        assertDoesNotThrow(() -> {
            Veiculo.validarDados("ABC1D23", "Civic", "Branco", "João Silva");
        });
    }

    @Test
    @DisplayName("Deve rejeitar placa nula")
    void deveRejeitarPlacaNula() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Veiculo.validarDados(null, "Civic", "Branco", "João Silva");
        });
        assertEquals("A placa é obrigatória.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve rejeitar placa vazia")
    void deveRejeitarPlacaVazia() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Veiculo.validarDados("", "Civic", "Branco", "João Silva");
        });
        assertEquals("A placa é obrigatória.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve rejeitar placa inválida")
    void deveRejeitarPlacaInvalida() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Veiculo.validarDados("ABC123", "Civic", "Branco", "João Silva");
        });
        assertTrue(exception.getMessage().contains("Placa inválida"));
    }

    @Test
    @DisplayName("Deve aceitar placa com espaços (normalizar)")
    void deveAceitarPlacaComEspacos() {
        assertDoesNotThrow(() -> {
            Veiculo.validarDados("  ABC1234  ", "Civic", "Branco", "João Silva");
        });
    }

    @Test
    @DisplayName("Deve aceitar placa em minúsculas (normalizar)")
    void deveAceitarPlacaMinusculas() {
        assertDoesNotThrow(() -> {
            Veiculo.validarDados("abc1234", "Civic", "Branco", "João Silva");
        });
    }

    @Test
    @DisplayName("Deve rejeitar modelo nulo")
    void deveRejeitarModeloNulo() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Veiculo.validarDados("ABC1234", null, "Branco", "João Silva");
        });
        assertEquals("O modelo é obrigatório.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve rejeitar modelo com caracteres inválidos")
    void deveRejeitarModeloComCaracteresInvalidos() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Veiculo.validarDados("ABC1234", "Civic@2020", "Branco", "João Silva");
        });
        assertEquals("O modelo contém caracteres inválidos.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve aceitar modelo com números e letras")
    void deveAceitarModeloComNumeros() {
        assertDoesNotThrow(() -> {
            Veiculo.validarDados("ABC1234", "Civic 2020", "Branco", "João Silva");
        });
    }

    @Test
    @DisplayName("Deve rejeitar cor nula")
    void deveRejeitarCorNula() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Veiculo.validarDados("ABC1234", "Civic", null, "João Silva");
        });
        assertEquals("A cor é obrigatória.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve rejeitar cor com números")
    void deveRejeitarCorComNumeros() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Veiculo.validarDados("ABC1234", "Civic", "Branco123", "João Silva");
        });
        assertEquals("A cor contém caracteres inválidos.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve aceitar cor com acentuação")
    void deveAceitarCorComAcentuacao() {
        assertDoesNotThrow(() -> {
            Veiculo.validarDados("ABC1234", "Civic", "Amarelo", "João Silva");
        });
    }

    @Test
    @DisplayName("Deve rejeitar proprietário nulo")
    void deveRejeitarProprietarioNulo() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Veiculo.validarDados("ABC1234", "Civic", "Branco", null);
        });
        assertEquals("O proprietário é obrigatório.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve rejeitar proprietário com números")
    void deveRejeitarProprietarioComNumeros() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Veiculo.validarDados("ABC1234", "Civic", "Branco", "João 123");
        });
        assertEquals("O nome do proprietário contém caracteres inválidos.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve aceitar proprietário com acentuação e espaços")
    void deveAceitarProprietarioComAcentuacao() {
        assertDoesNotThrow(() -> {
            Veiculo.validarDados("ABC1234", "Civic", "Branco", "José da Silva");
        });
    }

    @Test
    @DisplayName("Deve retornar toString correto")
    void deveRetornarToStringCorreto() {
        Veiculo veiculo = new Veiculo("ABC1234", "Civic", "Branco", "João Silva");
        String resultado = veiculo.toString();
        assertEquals("ABC1234 - Civic", resultado);
    }

    @Test
    @DisplayName("Deve permitir alterar placa")
    void devePermitirAlterarPlaca() {
        Veiculo veiculo = new Veiculo("ABC1234", "Civic", "Branco", "João Silva");
        veiculo.setPlaca("XYZ5678");
        assertEquals("XYZ5678", veiculo.getPlaca());
    }

    @Test
    @DisplayName("Deve permitir alterar modelo")
    void devePermitirAlterarModelo() {
        Veiculo veiculo = new Veiculo("ABC1234", "Civic", "Branco", "João Silva");
        veiculo.setModelo("Corolla");
        assertEquals("Corolla", veiculo.getModelo());
    }
}

