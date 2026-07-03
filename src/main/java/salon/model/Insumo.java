package salon.model;

public class Insumo {
    private int idInsumo;
    private String nome;
    private double valor;
    private int quantidade;
    private String capacidade;
    private int empresaId; // Nova variável para separar por empresa!

    public Insumo() {}

    public Insumo(int idInsumo, String nome, double valor, int quantidade, String capacidade) {
        this.idInsumo = idInsumo;
        this.nome = nome;
        this.valor = valor;
        this.quantidade = quantidade;
        this.capacidade = capacidade;
    }

    public int getIdInsumo() { return idInsumo; }
    public void setIdInsumo(int idInsumo) { this.idInsumo = idInsumo; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public String getCapacidade() { return capacidade; }
    public void setCapacidade(String capacidade) { this.capacidade = capacidade; }

    public int getEmpresaId() { return empresaId; }
    public void setEmpresaId(int empresaId) { this.empresaId = empresaId; }
}