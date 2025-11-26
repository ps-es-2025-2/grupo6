package view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Veiculo {

    private final StringProperty placa = new SimpleStringProperty();
    private final StringProperty modelo = new SimpleStringProperty();
    private final StringProperty cor = new SimpleStringProperty();
    private final StringProperty proprietario = new SimpleStringProperty();

    public Veiculo(String placa, String modelo, String cor, String proprietario) {
        this.placa.set(placa);
        this.modelo.set(modelo);
        this.cor.set(cor);
        this.proprietario.set(proprietario);
    }

    public String getPlaca() {
        return placa.get();
    }

    public StringProperty placaProperty() {
        return placa;
    }

    public String getModelo() {
        return modelo.get();
    }

    public StringProperty modeloProperty() {
        return modelo;
    }

    public String getCor() {
        return cor.get();
    }

    public StringProperty corProperty() {
        return cor;
    }

    public String getProprietario() {
        return proprietario.get();
    }

    public StringProperty proprietarioProperty() {
        return proprietario;
    }
}

