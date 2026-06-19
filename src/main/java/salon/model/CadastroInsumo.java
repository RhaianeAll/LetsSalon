package salon.model;

public class CadastroInsumo {
    private int id;
    private int insumoId;
    private int rendeAplicacoes;

    public CadastroInsumo() {}

    public CadastroInsumo(int id, int insumoId, int rendeAplicacoes) {
        this.id = id;
        this.insumoId = insumoId;
        this.rendeAplicacoes = rendeAplicacoes;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getInsumoId() { return insumoId; }
    public void setInsumoId(int insumoId) { this.insumoId = insumoId; }

    public int getRendeAplicacoes() { return rendeAplicacoes; }
    public void setRendeAplicacoes(int rendeAplicacoes) { this.rendeAplicacoes = rendeAplicacoes; }
}