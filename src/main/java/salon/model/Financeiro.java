package salon.model;

import java.time.LocalDate;

public class Financeiro {
    private int id;
    private String tipo; // 'entrada' ou 'saida'
    private String descricao;
    private String categoria;
    private double valor;
    private LocalDate data;
    private Integer funcionarioId; // Pode ser null dependendo da movimentação
    private Integer servicoId; // Pode ser null
    private double custoInsumos;
    private double lucro;

    public Financeiro() {}

    public Financeiro(int id, String tipo, String descricao, String categoria, double valor, LocalDate data, Integer funcionarioId, Integer servicoId, double custoInsumos, double lucro) {
        this.id = id;
        this.tipo = tipo;
        this.descricao = descricao;
        this.categoria = categoria;
        this.valor = valor;
        this.data = data;
        this.funcionarioId = funcionarioId;
        this.servicoId = servicoId;
        this.custoInsumos = custoInsumos;
        this.lucro = lucro;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }

    public Integer getFuncionarioId() { return funcionarioId; }
    public void setFuncionarioId(Integer funcionarioId) { this.funcionarioId = funcionarioId; }

    public Integer getServicoId() { return servicoId; }
    public void setServicoId(Integer servicoId) { this.servicoId = servicoId; }

    public double getCustoInsumos() { return custoInsumos; }
    public void setCustoInsumos(double custoInsumos) { this.custoInsumos = custoInsumos; }

    public double getLucro() { return lucro; }
    public void setLucro(double lucro) { this.lucro = lucro; }
}