package view;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Vaga {

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty codigo = new SimpleStringProperty();
    private final StringProperty setor = new SimpleStringProperty();
    private final BooleanProperty ocupada = new SimpleBooleanProperty();

    public Vaga(int id, String codigo, String setor, boolean ocupada) {
        this.id.set(id);
        this.codigo.set(codigo);
        this.setor.set(setor);
        this.ocupada.set(ocupada);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getCodigo() {
        return codigo.get();
    }

    public StringProperty codigoProperty() {
        return codigo;
    }

    public String getSetor() {
        return setor.get();
    }

    public StringProperty setorProperty() {
        return setor;
    }

    public boolean isOcupada() {
        return ocupada.get();
    }

    public BooleanProperty ocupadaProperty() {
        return ocupada;
    }
}
