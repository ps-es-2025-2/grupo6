package model;

import model.enums.StatusPagamento;
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
    public boolean updateStatus(int pagamentoId, StatusPagamento novoStatus) {
    try {
        // Atualização parcial usando UpdateBuilder (melhor prática)
        var updateBuilder = getDao().updateBuilder();

        updateBuilder.updateColumnValue("status", novoStatus);
        updateBuilder.where().eq("id", pagamentoId);

        int linhasAfetadas = updateBuilder.update();

        return linhasAfetadas > 0; // true se atualizou

    } catch (SQLException e) {
        System.err.println("Erro ao atualizar status do pagamento: " + e.getMessage());
        return false;
    }
}

}

