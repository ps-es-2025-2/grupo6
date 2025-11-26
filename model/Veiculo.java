package model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "veiculos")
public class Veiculo {

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
