package salon.model;

public class Servico {
    private int idServico;
    private String nome;
    private double valor;
    private String duracao;
    private int empresaId;

    public Servico() {}

    public Servico(int idServico, String nome, double valor, String duracao, int empresaId) {
        this.idServico = idServico;
        this.nome = nome;
        this.valor = valor;
        this.duracao = duracao;
        this.empresaId = empresaId;
    }

    public int getIdServico() { return idServico; }
    public void setIdServico(int idServico) { this.idServico = idServico; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    public String getDuracao() { return duracao; }
    public void setDuracao(String duracao) { this.duracao = duracao; }

    public int getEmpresaId() { return empresaId; }
    public void setEmpresaId(int empresaId) { this.empresaId = empresaId; }
    
    @Override
    public String toString() {
        return this.nome; // É isto que vai aparecer escrito na ComboBox!
    }
}