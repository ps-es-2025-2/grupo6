package view;

import java.time.LocalDateTime;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Checkout {

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final IntegerProperty checkinId = new SimpleIntegerProperty();
    private final StringProperty checkinInfo = new SimpleStringProperty();
    private final ObjectProperty<LocalDateTime> horarioSaida = new SimpleObjectProperty<>();
    private final StringProperty valorCalculado = new SimpleStringProperty();

    public Checkout(int id, int checkinId, String checkinInfo, LocalDateTime horarioSaida, double valorCalculado) {
        this.id.set(id);
        this.checkinId.set(checkinId);
        this.checkinInfo.set(checkinInfo);
        this.horarioSaida.set(horarioSaida);
        this.valorCalculado.set(String.format("%.2f", valorCalculado));
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public int getCheckinId() {
        return checkinId.get();
    }

    public IntegerProperty checkinIdProperty() {
        return checkinId;
    }

    public String getCheckinInfo() {
        return checkinInfo.get();
    }

    public StringProperty checkinInfoProperty() {
        return checkinInfo;
    }

    public LocalDateTime getHorarioSaida() {
        return horarioSaida.get();
    }

    public ObjectProperty<LocalDateTime> horarioSaidaProperty() {
        return horarioSaida;
    }

    public String getValorCalculado() {
        return valorCalculado.get();
    }

    public StringProperty valorCalculadoProperty() {
        return valorCalculado;
    }
}

