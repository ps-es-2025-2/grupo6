package model;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class VagaRepositorio extends Repositorio<Vaga, Integer> {

    public VagaRepositorio(Database database) {
        super(database, Vaga.class);
    }

    public List<Vaga> listarLivres() {
        try {
            return getDao().queryBuilder().where().eq("ocupada", false).query();
        } catch (SQLException e) {
            System.err.println("Erro ao listar vagas livres: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public void atualizarOcupacao(Vaga vaga, boolean ocupada) throws SQLException {
        vaga.setOcupada(ocupada);
        update(vaga);
    }
}

