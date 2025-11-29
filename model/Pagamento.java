package model;

import java.time.LocalDateTime;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import model.enums.StatusPagamento;

@DatabaseTable(tableName = "pagamentos")
public class Pagamento {

    @DatabaseField(generatedId = true)
    protected int id;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, canBeNull = false, columnName = "checkout_id")
    protected Checkout checkout;

    @DatabaseField(canBeNull = false)
    protected String tipo; // Cartao, Pix, Dinheiro

    @DatabaseField(canBeNull = false)
    protected double valor;

    @DatabaseField(canBeNull = false, dataType = DataType.ENUM_STRING)
    protected StatusPagamento status;

    @DatabaseField(canBeNull = false, dataType = DataType.SERIALIZABLE)
    protected LocalDateTime dataPagamento;
    
    @DatabaseField(canBeNull = false, columnName = "token_unico")
    private String tokenUnico;

    public Pagamento() {}

    public Pagamento(Checkout checkout, String tipo, double valor) {
        this.checkout = checkout;
        this.tipo = tipo;
        this.valor = valor;
        this.status = StatusPagamento.PENDENTE;
        this.dataPagamento = LocalDateTime.now();
        this.tokenUnico = gerarToken();
    }

    private String gerarToken() {
        return "tok_" + java.util.UUID.randomUUID().toString().replace("-", "");
    }
    public boolean processarPagamento() {return false;};
    // Getters
    public String getTokenUnico() { return tokenUnico; }
    public int getId() { return id; }
    public Checkout getCheckout() { return checkout; }
    public void setCheckout(Checkout checkout) { this.checkout = checkout; }
    public String getTipo() { return tipo; }
    public StatusPagamento getStatus() { return status; }
    public double getValor() { return valor; }
    public void setValor(double valor){this.valor = valor;}
    public LocalDateTime getDataPagamento() { return dataPagamento; }
    public void setStatus(StatusPagamento status) { this.status = status; }
    public void setDataPagamento(LocalDateTime dataPagamento) {this.dataPagamento = dataPagamento;}
}
