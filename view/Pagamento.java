package view;

public class Pagamento {

    private final String id;
    private final String tipo;
    private final String valor;
    private final String status;
    private final String dataPagamento;

    private final String checkoutId; // <-- NOVO CAMPO

    public Pagamento(int id, String tipo, String valor, String status, String dataPagamento, int checkoutId) {
        this.id = String.valueOf(id);
        this.tipo = tipo;
        this.valor = valor;
        this.status = status;
        this.dataPagamento = dataPagamento;
        this.checkoutId = String.valueOf(checkoutId);
    }

    public String getId() { return id; }
    public String getTipo() { return tipo; }
    public String getValor() { return valor; }
    public String getStatus() { return status; }
    public String getDataPagamento() { return dataPagamento; }

    public String getCheckoutId() { return checkoutId; }
}
