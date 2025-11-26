package model;

public class Repositorios {

    private static final Database DATABASE = new Database("jdbc:sqlite:easystop.db");

    public static final VeiculoRepositorio VEICULOS = new VeiculoRepositorio(DATABASE);
    public static final VagaRepositorio VAGAS = new VagaRepositorio(DATABASE);
    public static final CheckinRepositorio CHECKINS = new CheckinRepositorio(DATABASE);
    public static final CheckoutRepositorio CHECKOUTS = new CheckoutRepositorio(DATABASE);
    public static final PagamentoRepositorio PAGAMENTOS = new PagamentoRepositorio(DATABASE);

    private Repositorios() {
    }

    public static Database getDatabase() {
        return DATABASE;
    }
}