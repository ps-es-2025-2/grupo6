package model;

public class VeiculoRepositorio extends Repositorio<Veiculo, String> {

    public VeiculoRepositorio(Database database) {
        super(database, Veiculo.class);
    }
}

