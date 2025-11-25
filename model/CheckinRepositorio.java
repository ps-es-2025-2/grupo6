package model;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class CheckinRepositorio extends Repositorio<Checkin, Integer> {

    public CheckinRepositorio(Database database) {
        super(database, Checkin.class);
    }

    public List<Checkin> listarAtivos() {
        try {
            return getDao().queryBuilder()
                    .where().eq("finalizado", false)
                    .query();
        } catch (SQLException e) {
            System.err.println("Erro ao listar check-ins: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<Checkin> listarFinalizados() {
        try {
            return getDao().queryBuilder()
                    .where().eq("finalizado", true)
                    .query();
        } catch (SQLException e) {
            System.err.println("Erro ao listar check-ins finalizados: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}

