package model;

import java.util.regex.Pattern;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "vagas")
public class Vaga {

    // Regras de validação do código da vaga - apenas números
    private static final Pattern CODIGO_PATTERN =
            Pattern.compile("^[0-9]+$");
    // Regras de validação do setor da vaga - apenas letras
    private static final Pattern SETOR_PATTERN =
            Pattern.compile("^[A-Za-z]+$");

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false)
    private String codigo;

    @DatabaseField(canBeNull = false)
    private String setor;

    @DatabaseField(canBeNull = false)
    private boolean ocupada;

    public Vaga() {}

    public Vaga(String codigo, String setor) {
        this.codigo = codigo;
        this.setor = setor;
        this.ocupada = false;
    }

    public static void validarDados(String codigo, String setor) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("O código da vaga é obrigatório.");
        }
        String codigoNormalizado = codigo.trim().toUpperCase();
        if (!CODIGO_PATTERN.matcher(codigoNormalizado).matches()) {
            throw new IllegalArgumentException("O código da vaga contém caracteres inválidos. Use apenas números.");
        }

        if (setor == null || setor.isBlank()) {
            throw new IllegalArgumentException("O setor é obrigatório.");
        }
        if (!SETOR_PATTERN.matcher(setor.trim()).matches()) {
            throw new IllegalArgumentException("O setor contém caracteres inválidos.");
        }
    }

    public int getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public boolean isOcupada() {
        return ocupada;
    }

    public void setOcupada(boolean ocupada) {
        this.ocupada = ocupada;
    }

    @Override
    public String toString() {
        return codigo + " - " + setor + (ocupada ? " (ocupada)" : " (livre)");
    }
}
