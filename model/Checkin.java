package model;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "checkins")
public class Checkin {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, canBeNull = false, columnName = "veiculo_placa")
    private Veiculo veiculo;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, canBeNull = false, columnName = "vaga_id")
    private Vaga vaga;

    @DatabaseField(canBeNull = false, dataType = DataType.SERIALIZABLE, columnName = "horario_entrada")
    private LocalDateTime horarioEntrada;

    @DatabaseField(canBeNull = true, columnName = "observacao")
    private String observacao;

    @DatabaseField(canBeNull = false, columnName = "finalizado")
    private boolean finalizado = false;

    public static final Pattern HORA_MINUTO_PATTERN =
    Pattern.compile("^([01][0-9]|2[0-3]):[0-5][0-9]$");

    public Checkin() {
    }

    public Checkin(Veiculo veiculo, Vaga vaga, LocalDateTime horarioEntrada, String observacao) {
        this.veiculo = veiculo;
        this.vaga = vaga;
        this.horarioEntrada = horarioEntrada;
        this.observacao = observacao;
        this.finalizado = false;
    }

    public int getId() {
        return id;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public Vaga getVaga() {
        return vaga;
    }

    public void setVaga(Vaga vaga) {
        this.vaga = vaga;
    }

    public LocalDateTime getHorarioEntrada() {
        return horarioEntrada;
    }

    public void setHorarioEntrada(LocalDateTime horarioEntrada) {
        this.horarioEntrada = horarioEntrada;
    }

    public static void validarHorarioEntrada(String horarioEntrada) {
        if (horarioEntrada == null || horarioEntrada.isBlank()) {
            throw new IllegalArgumentException("A data e hora são obrigatórias.");
        }
        if (!HORA_MINUTO_PATTERN.matcher(horarioEntrada.trim()).matches()) {
            throw new IllegalArgumentException("A hora de entrada esta em um formato inválido.");
        }
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public boolean isFinalizado() {
        return finalizado;
    }

    public void setFinalizado(boolean finalizado) {
        this.finalizado = finalizado;
    }
}
