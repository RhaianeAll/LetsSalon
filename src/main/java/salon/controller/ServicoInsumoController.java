package salon.controller;

import salon.dao.ServicoInsumoDAO;
import salon.model.ServicoInsumo;
import salon.util.ValidadorUtil;
import java.util.List;

public class ServicoInsumoController {

    private ServicoInsumoDAO servicoInsumoDAO;

    public ServicoInsumoController() {
        this.servicoInsumoDAO = new ServicoInsumoDAO();
    }

    public void vincularInsumo(int servicoId, int insumoId, String quantidadeNecessariaStr) {
        if (!ValidadorUtil.camposPreenchidos(quantidadeNecessariaStr)) {
            throw new IllegalArgumentException("A quantidade necessária do insumo é obrigatória.");
        }
        try {
            int qtd = Integer.parseInt(quantidadeNecessariaStr);
            if (qtd <= 0) throw new IllegalArgumentException("A quantidade deve ser maior que zero.");

            ServicoInsumo si = new ServicoInsumo();
            si.setServicoId(servicoId);
            si.setInsumoId(insumoId);
            si.setQuantidadeNecessaria(qtd);
            servicoInsumoDAO.vincularInsumoAServico(si);

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("A quantidade deve ser um número inteiro.");
        }
    }

    public List<ServicoInsumo> listarInsumosDoServico(int servicoId) {
        return servicoInsumoDAO.listarInsumosPorServico(servicoId);
    }

    public void limparInsumosDoServico(int servicoId) {
        servicoInsumoDAO.excluirPorServico(servicoId);
    }
}