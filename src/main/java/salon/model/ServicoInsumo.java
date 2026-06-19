package salon.model;

public class ServicoInsumo {
    private int id;
    private int servicoId;
    private int insumoId;
    private int quantidadeNecessaria;

    public ServicoInsumo() {}

    public ServicoInsumo(int id, int servicoId, int insumoId, int quantidadeNecessaria) {
        this.id = id;
        this.servicoId = servicoId;
        this.insumoId = insumoId;
        this.quantidadeNecessaria = quantidadeNecessaria;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getServicoId() { return servicoId; }
    public void setServicoId(int servicoId) { this.servicoId = servicoId; }

    public int getInsumoId() { return insumoId; }
    public void setInsumoId(int insumoId) { this.insumoId = insumoId; }

    public int getQuantidadeNecessaria() { return quantidadeNecessaria; }
    public void setQuantidadeNecessaria(int quantidadeNecessaria) { this.quantidadeNecessaria = quantidadeNecessaria; }
}