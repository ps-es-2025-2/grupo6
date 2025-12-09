package model;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "checkouts")
public class Checkout {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, canBeNull = false, unique = true, columnName = "checkin_id")
    private Checkin checkin;

    @DatabaseField(canBeNull = false, dataType = DataType.SERIALIZABLE, columnName = "horario_saida")
    private LocalDateTime horarioSaida;

    @DatabaseField(canBeNull = false, columnName = "valor_calculado")
    private double valorCalculado;

    private static final Pattern HORA_MINUTO_PATTERN = Pattern.compile("^([01][0-9]|2[0-3]):[0-5][0-9]$");
    private static final Pattern VALOR_MONETARIO_PATTERN = Pattern.compile("^[0-9]+([.,][0-9]{1,2})?$");

    public Checkout() {
    }

    public Checkout(Checkin checkin, LocalDateTime horarioSaida, double valorCalculado) {
        this.checkin = checkin;
        this.horarioSaida = horarioSaida;
        this.valorCalculado = valorCalculado;
    }

    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public Checkin getCheckin() {
        return checkin;
    }

    public void setCheckin(Checkin checkin) {
        this.checkin = checkin;
    }

    public LocalDateTime getHorarioSaida() {
        return horarioSaida;
    }

    public void setHorarioSaida(LocalDateTime horarioSaida) {
        this.horarioSaida = horarioSaida;
    }

    public static void validarCampos(String horarioSaida, String valorHora) {
        validarHorarioSaida(horarioSaida);
        validarValorHora(valorHora);
    }

    public static void validarHorarioSaida(String horarioSaida) {
        if (horarioSaida == null || horarioSaida.isBlank()) {
            throw new IllegalArgumentException("A data e hora são obrigatórias.");
        }
        if (!HORA_MINUTO_PATTERN.matcher(horarioSaida.trim()).matches()) {
            throw new IllegalArgumentException("A hora de saida esta em um formato inválido.");
        }
    }


    public static void validarValorHora(String valorHora) {
        if (valorHora == null || valorHora.isBlank()) {
            throw new IllegalArgumentException("O valor por hora é obrigatório.");
        }
        
        String valorNormalizado = valorHora.trim().replace(",", ".");
        
        if (!VALOR_MONETARIO_PATTERN.matcher(valorNormalizado).matches()) {
            throw new IllegalArgumentException("O valor por hora contém caracteres inválidos. Use apenas números com ponto ou vírgula (ex: 10.00 ou 10,00).");
        }
        
        try {
            double valor = Double.parseDouble(valorNormalizado);
            if (valor < 0) {
                throw new IllegalArgumentException("O valor por hora não pode ser negativo.");
            }
            if (valor == 0) {
                throw new IllegalArgumentException("O valor por hora deve ser maior que zero.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("O valor por hora não é um número válido.");
        }
    }

    public double getValorCalculado() {
        return valorCalculado;
    }

    public void setValorCalculado(double valorCalculado) {
        this.valorCalculado = valorCalculado;
    }
}

