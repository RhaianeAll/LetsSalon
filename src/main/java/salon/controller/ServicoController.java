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
    
    // Método que a View está tentando chamar!
    public List<Servico> listarServicos() {
        return servicoDAO.listar();
    }

    // Agora retorna int!
    public int cadastrarServico(String nome, String valorStr, String duracao, int empresaId) {
        if (!ValidadorUtil.camposPreenchidos(nome, valorStr, duracao)) {
            throw new IllegalArgumentException("Por favor, preencha nome, valor e duração.");
        }
        try {
            double valor = Double.parseDouble(valorStr.replace(",", "."));
            if (valor < 0) throw new IllegalArgumentException("O valor não pode ser negativo.");

            Servico servico = new Servico();
            servico.setNome(nome);
            servico.setValor(valor);
            servico.setDuracao(duracao);
            servico.setEmpresaId(empresaId);

            return servicoDAO.cadastrarRetornandoId(servico); // Devolve o ID!

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("O valor informado deve ser numérico (ex: 150.00).");
        }
    }

    public List<Servico> listarServicos(int empresaId) {
        return servicoDAO.listarPorEmpresa(empresaId);
    }

    public void atualizarServico(int id, String nome, String valorStr, String duracao) {
        if (!ValidadorUtil.camposPreenchidos(nome, valorStr, duracao)) {
            throw new IllegalArgumentException("Por favor, preencha nome, valor e duração.");
        }
        try {
            double valor = Double.parseDouble(valorStr.replace(",", "."));
            Servico servico = new Servico(id, nome, valor, duracao, 0); // EmpresaID não muda aqui
            servicoDAO.atualizar(servico);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("O valor informado deve ser numérico.");
        }
    }

    public void excluirServico(int id) {
        servicoDAO.excluir(id);
    }
}