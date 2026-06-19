package salon.controller;

import salon.dao.ServicoDAO;
import salon.model.Servico;
import salon.util.ValidadorUtil;

import java.util.List;

public class ServicoController {

    private ServicoDAO servicoDAO;

    public ServicoController() {
        this.servicoDAO = new ServicoDAO();
    }

    public void cadastrarServico(String nome, String valorStr, String duracao, int empresaId) {
        
        if (!ValidadorUtil.camposPreenchidos(nome, valorStr, duracao)) {
            throw new IllegalArgumentException("Por favor, preencha nome, valor e duração do serviço.");
        }

        try {
            // Substitui vírgula por ponto para evitar erro de parse no Java
            valorStr = valorStr.replace(",", ".");
            double valor = Double.parseDouble(valorStr);

            if (valor < 0) {
                throw new IllegalArgumentException("O valor do serviço não pode ser negativo.");
            }

            Servico servico = new Servico();
            servico.setNome(nome);
            servico.setValor(valor);
            servico.setDuracao(duracao);
            servico.setEmpresaId(empresaId);

            servicoDAO.cadastrar(servico);

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("O valor informado deve ser numérico (ex: 150.00).");
        }
    }

    public List<Servico> listarServicos() {
        return servicoDAO.listar();
    }
}