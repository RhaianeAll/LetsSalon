package salon.controller;

import salon.dao.CadastroInsumoDAO;
import salon.dao.EmpresaDAO;
import salon.dao.InsumoDAO;
import salon.model.CadastroInsumo;
import salon.model.Insumo;
import salon.util.ValidadorUtil;
import java.util.List;

public class InsumoController {

    private InsumoDAO insumoDAO;
    private CadastroInsumoDAO cadastroInsumoDAO;
    private EmpresaDAO empresaDAO;

    public InsumoController() {
        this.insumoDAO = new InsumoDAO();
        this.cadastroInsumoDAO = new CadastroInsumoDAO();
        this.empresaDAO = new EmpresaDAO();
    }

    public void cadastrarEstoque(String nome, String valorStr, String quantidadeStr, String capacidade, String rendimentoStr, String nomeEmpresa) {
        if (!ValidadorUtil.camposPreenchidos(nome, valorStr, quantidadeStr, capacidade, rendimentoStr)) {
            throw new IllegalArgumentException("Todos os campos do insumo são obrigatórios.");
        }
        try {
            double valor = Double.parseDouble(valorStr.replace(",", "."));
            int quantidade = Integer.parseInt(quantidadeStr);
            int rendimento = Integer.parseInt(rendimentoStr);

            if (valor < 0 || quantidade < 0 || rendimento <= 0) {
                throw new IllegalArgumentException("Valores não podem ser negativos.");
            }

            int idEmpresa = empresaDAO.buscarIdPorNome(nomeEmpresa);

            Insumo insumo = new Insumo();
            insumo.setNome(nome);
            insumo.setValor(valor);
            insumo.setQuantidade(quantidade);
            insumo.setCapacidade(capacidade);
            insumo.setEmpresaId(idEmpresa);
            
            int idGerado = insumoDAO.cadastrarRetornandoId(insumo);

            if (idGerado != -1) {
                CadastroInsumo ci = new CadastroInsumo();
                ci.setInsumoId(idGerado);
                ci.setRendeAplicacoes(rendimento);
                cadastroInsumoDAO.cadastrarRendimento(ci);
            } else {
                throw new RuntimeException("Erro ao gerar código do Insumo no Banco de Dados.");
            }

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Certifique-se de que Valor, Quantidade e Rendimento sejam números válidos.");
        }
    }

    public List<Insumo> listarEstoque(String nomeEmpresa) {
        return insumoDAO.listarEstoquePorEmpresa(nomeEmpresa);
    }
    
    public int buscarRendimento(int insumoId) {
        CadastroInsumo ci = cadastroInsumoDAO.buscarPorInsumo(insumoId);
        return ci != null ? ci.getRendeAplicacoes() : 0;
    }

    public void atualizarInsumo(int id, String nome, String valorStr, String quantidadeStr, String capacidade, String rendimentoStr) {
        if (!ValidadorUtil.camposPreenchidos(nome, valorStr, quantidadeStr, capacidade, rendimentoStr)) {
            throw new IllegalArgumentException("Todos os campos são obrigatórios para atualizar.");
        }
        try {
            double valor = Double.parseDouble(valorStr.replace(",", "."));
            int quantidade = Integer.parseInt(quantidadeStr);
            int rendimento = Integer.parseInt(rendimentoStr);

            Insumo insumo = new Insumo(id, nome, valor, quantidade, capacidade);
            insumoDAO.atualizar(insumo);

            CadastroInsumo ci = cadastroInsumoDAO.buscarPorInsumo(id);
            if (ci != null) {
                ci.setRendeAplicacoes(rendimento);
                cadastroInsumoDAO.atualizarRendimento(ci);
            } else {
                ci = new CadastroInsumo();
                ci.setInsumoId(id);
                ci.setRendeAplicacoes(rendimento);
                cadastroInsumoDAO.cadastrarRendimento(ci);
            }

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Certifique-se de que Valor e Quantidade sejam números válidos.");
        }
    }

    public void excluirInsumo(int id) {
        cadastroInsumoDAO.excluirPorInsumo(id);
        insumoDAO.excluir(id);
    }
}