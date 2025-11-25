package model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "vagas")
public class Vaga {

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
