package model;

import java.time.LocalDateTime;

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

    public double getValorCalculado() {
        return valorCalculado;
    }

    public void setValorCalculado(double valorCalculado) {
        this.valorCalculado = valorCalculado;
    }
}

