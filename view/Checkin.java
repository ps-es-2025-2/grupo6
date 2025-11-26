package view;

import java.time.LocalDateTime;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Checkin {

    private final StringProperty id = new SimpleStringProperty();
    private final StringProperty placa = new SimpleStringProperty();
    private final StringProperty vaga = new SimpleStringProperty();
    private final ObjectProperty<LocalDateTime> horarioEntrada = new SimpleObjectProperty<>();
    private final StringProperty observacao = new SimpleStringProperty();

    public Checkin(int id, String placa, String vaga, LocalDateTime horarioEntrada, String observacao) {
        this.id.set(String.valueOf(id));
        this.placa.set(placa);
        this.vaga.set(vaga);
        this.horarioEntrada.set(horarioEntrada);
        this.observacao.set(observacao);
    }

    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public String getPlaca() {
        return placa.get();
    }

    public StringProperty placaProperty() {
        return placa;
    }

    public String getVaga() {
        return vaga.get();
    }

    public StringProperty vagaProperty() {
        return vaga;
    }

    public LocalDateTime getHorarioEntrada() {
        return horarioEntrada.get();
    }

    public ObjectProperty<LocalDateTime> horarioEntradaProperty() {
        return horarioEntrada;
    }

    public String getObservacao() {
        return observacao.get();
    }

    public StringProperty observacaoProperty() {
        return observacao;
    }
}
