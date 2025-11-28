package model;

import java.time.LocalDateTime;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import model.enums.StatusPagamento;


public class PagamentoCartao extends Pagamento {

   
    private String ultimos4; 
    private String bandeira;

    // Dados sensíveis NÃO são armazenados.
    private transient String numeroCartao;
    private transient String nomeTitular;
    private transient String validade;
    private transient String cvv;

    public PagamentoCartao() {}

    public PagamentoCartao(Checkout checkout, double valor,
                           String numeroCartao, String nomeTitular,
                           String validade, String cvv) {

        super(checkout, "CARTAO", valor);
        this.numeroCartao = numeroCartao;
        this.nomeTitular = nomeTitular;
        this.validade = validade;
        this.cvv = cvv;

        this.bandeira = detectarBandeira(numeroCartao);
        this.ultimos4 = numeroCartao.substring(numeroCartao.length() - 4);
       
    }



    private String detectarBandeira(String numero) {
        if (numero.startsWith("4")) return "VISA";
        if (numero.matches("^5[1-5].*")) return "MASTERCARD";
        if (numero.startsWith("34") || numero.startsWith("37")) return "AMEX";
        return "DESCONHECIDA";
    }

    @Override
    public boolean processarPagamento() {
        // -------------------------------------------
        // Validar usando regex (apenas em memória)
        // -------------------------------------------
        boolean numeroValido = numeroCartao.matches("^\\d{16}$");
        boolean cvvValido = cvv.matches("^\\d{3}$");
        boolean validadeValida = validade.matches("^(0[1-9]|1[0-2])\\/\\d{2}$");
    
        if (!numeroValido || !cvvValido || !validadeValida) {
            this.status = StatusPagamento.RECUSADO;
            return false;
        }
    
        // Tudo válido
        this.status = StatusPagamento.APROVADO;
    
        // Remover dados sensíveis da memória
        this.numeroCartao = null;
        this.cvv = null;
        this.nomeTitular = null;
        this.validade = null;
    
        return true;
    }
    

    
    public String getUltimos4() { return ultimos4; }
    public String getBandeira() { return bandeira; }
}
