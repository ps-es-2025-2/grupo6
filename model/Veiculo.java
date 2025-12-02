package model;

import java.util.regex.Pattern;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "veiculos")
public class Veiculo {

    // Regras de validação da placa do veículo - padrão ABC1234 ou ABC1D23
    private static final Pattern PLACA_PADRAO =
            Pattern.compile("^[A-Z]{3}[0-9]{4}$");
    private static final Pattern PLACA_MERCOSUL =
            Pattern.compile("^[A-Z]{3}[0-9][A-Z][0-9]{2}$");
    // Regras de validação do modelo do veículo - apenas letras e números
    private static final Pattern MODELO_PATTERN =
            Pattern.compile("^[A-Za-zÀ-ÿ0-9 ]+$");
    // Regras de validação da cor do veículo - apenas letras
    private static final Pattern COR_PATTERN =
            Pattern.compile("^[A-Za-zÀ-ÿ]+$");
    // Regras de validação do proprietário do veículo - apenas letras podendo ter acentuação
    private static final Pattern PROPRIETARIO_PATTERN =
            Pattern.compile("^[A-Za-zÀ-ÿ ]+$");


    @DatabaseField(id = true, columnName = "placa")
    private String placa;

    @DatabaseField(canBeNull = false, columnName = "modelo")
    private String modelo;

    @DatabaseField(canBeNull = false, columnName = "cor")
    private String cor;

    @DatabaseField(canBeNull = false, columnName = "proprietario")
    private String proprietario;

    public Veiculo() {
    }

    public Veiculo(String placa, String modelo, String cor, String proprietario) {
        this.placa = placa;
        this.modelo = modelo;
        this.cor = cor;
        this.proprietario = proprietario;
    }

    public static void validarDados(String placa, String modelo, String cor, String proprietario) {
        if (placa == null || placa.isBlank()) {
            throw new IllegalArgumentException("A placa é obrigatória.");
        }
        String placaNormalizada = placa.trim().toUpperCase();
        if (!PLACA_PADRAO.matcher(placaNormalizada).matches()
                && !PLACA_MERCOSUL.matcher(placaNormalizada).matches()) {
            throw new IllegalArgumentException("Placa inválida. Use o padrão ABC1234 ou ABC1D23.");
        }

        if (modelo == null || modelo.isBlank()) {
            throw new IllegalArgumentException("O modelo é obrigatório.");
        }
        if (!MODELO_PATTERN.matcher(modelo.trim()).matches()) {
            throw new IllegalArgumentException("O modelo contém caracteres inválidos.");
        }

        if (cor == null || cor.isBlank()) {
            throw new IllegalArgumentException("A cor é obrigatória.");
        }
        if (!COR_PATTERN.matcher(cor.trim()).matches()) {
            throw new IllegalArgumentException("A cor contém caracteres inválidos.");
        }

        if (proprietario == null || proprietario.isBlank()) {
            throw new IllegalArgumentException("O proprietário é obrigatório.");
        }
        if (!PROPRIETARIO_PATTERN.matcher(proprietario.trim()).matches()) {
            throw new IllegalArgumentException("O nome do proprietário contém caracteres inválidos.");
        }
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getProprietario() {
        return proprietario;
    }

    public void setProprietario(String proprietario) {
        this.proprietario = proprietario;
    }

    @Override
    public String toString() {
        return placa + " - " + modelo;
    }
}
