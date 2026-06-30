package salon.controller;

import salon.dao.CadastroInsumoDAO;
import salon.dao.InsumoDAO;
import salon.model.CadastroInsumo;
import salon.model.Insumo;
import salon.util.ValidadorUtil;
import java.util.List;

public class InsumoController {

    private InsumoDAO insumoDAO;
    private CadastroInsumoDAO cadastroInsumoDAO;

    public InsumoController() {
        this.insumoDAO = new InsumoDAO();
        this.cadastroInsumoDAO = new CadastroInsumoDAO();
    }

    public void cadastrarEstoque(String nome, String valorStr, String quantidadeStr, String capacidade, String rendimentoStr) {
        if (!ValidadorUtil.camposPreenchidos(nome, valorStr, quantidadeStr, capacidade, rendimentoStr)) {
            throw new IllegalArgumentException("Todos os campos do insumo são obrigatórios.");
        }
        try {
            double valor = Double.parseDouble(valorStr.replace(",", "."));
            int quantidade = Integer.parseInt(quantidadeStr);
            int rendimento = Integer.parseInt(rendimentoStr);

            if (valor < 0 || quantidade < 0 || rendimento <= 0) {
                throw new IllegalArgumentException("Valores de preço, quantidade e rendimento não podem ser negativos.");
            }

            // 1. Cadastra o Insumo Base e pega o ID gerado
            Insumo insumo = new Insumo();
            insumo.setNome(nome);
            insumo.setValor(valor);
            insumo.setQuantidade(quantidade);
            insumo.setCapacidade(capacidade);
            
            int idGerado = insumoDAO.cadastrarRetornandoId(insumo);

            if (idGerado != -1) {
                // 2. Cadastra o Rendimento na tabela auxiliar vinculado ao ID
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

    public List<Insumo> listarEstoque() {
        return insumoDAO.listarEstoque();
    }
    
    // Busca o rendimento para colocar na tabela da tela
    public int buscarRendimento(int insumoId) {
        CadastroInsumo ci = cadastroInsumoDAO.buscarPorInsumo(insumoId);
        return ci != null ? ci.getRendeAplicacoes() : 0;
    }

    public void atualizarInsumo(int id, String nome, String valorStr, String quantidadeStr, String capacidade, String rendimentoStr) {
        if (!ValidadorUtil.camposPreenchidos(nome, valorStr, quantidadeStr, capacidade, rendimentoStr)) {
            throw new IllegalArgumentException("Todos os campos do insumo são obrigatórios para atualizar.");
        }
        try {
            double valor = Double.parseDouble(valorStr.replace(",", "."));
            int quantidade = Integer.parseInt(quantidadeStr);
            int rendimento = Integer.parseInt(rendimentoStr);

            // 1. Atualiza o Insumo Base
            Insumo insumo = new Insumo(id, nome, valor, quantidade, capacidade);
            insumoDAO.atualizar(insumo);

            // 2. Atualiza (ou insere) na tabela de rendimentos
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
        // Exclui a chave estrangeira primeiro para não dar erro
        cadastroInsumoDAO.excluirPorInsumo(id);
        insumoDAO.excluir(id);
    }
}