package model;

import java.sql.SQLException;

public class PagamentoRepositorio extends Repositorio<Pagamento, Integer> {

    public PagamentoRepositorio(Database database) {
        super(database, Pagamento.class);
    }

    public Pagamento buscarPorCheckout(int checkoutId) {
        try {
            return getDao().queryBuilder()
                    .where().eq("checkout_id", checkoutId)
                    .queryForFirst();
        } catch (SQLException e) {
            System.err.println("Erro ao buscar pagamento por checkout: " + e.getMessage());
            return null;
        }
    }
}

