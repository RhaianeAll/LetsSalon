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

        if (servicoId <= 0 || insumoId <= 0) {
            throw new IllegalArgumentException("É necessário selecionar um Serviço e um Insumo válidos.");
        }

        try {
            int quantidadeNecessaria = Integer.parseInt(quantidadeNecessariaStr);

            if (quantidadeNecessaria <= 0) {
                throw new IllegalArgumentException("A quantidade gasta do insumo deve ser maior que zero.");
            }

            ServicoInsumo si = new ServicoInsumo();
            si.setServicoId(servicoId);
            si.setInsumoId(insumoId);
            si.setQuantidadeNecessaria(quantidadeNecessaria);

            servicoInsumoDAO.vincularInsumoAServico(si);

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("A quantidade deve ser um número inteiro.");
        }
    }

    public List<ServicoInsumo> listarInsumosDoServico(int servicoId) {
        if (servicoId <= 0) {
            throw new IllegalArgumentException("ID do serviço inválido.");
        }
        return servicoInsumoDAO.listarInsumosPorServico(servicoId);
    }
}