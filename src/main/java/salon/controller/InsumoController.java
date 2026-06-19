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
                throw new IllegalArgumentException("Valores de preço, quantidade e rendimento devem ser positivos.");
            }

            // 1. Cadastra o Insumo Base
            Insumo insumo = new Insumo();
            insumo.setNome(nome);
            insumo.setValor(valor);
            insumo.setQuantidade(quantidade);
            insumo.setCapacidade(capacidade);
            
            // Aqui, idealmente, o DAO de Insumo deve retornar o ID gerado (use Statement.RETURN_GENERATED_KEYS no DAO)
            // Para simplificar, vou assumir que você ajustou o insumoDAO.cadastrar() para retornar o ID.
            // Exemplo simulado (você precisará adaptar o DAO para retornar o int gerado):
            // int idGerado = insumoDAO.cadastrarRetornandoId(insumo); 
            
            // 2. Cadastra o rendimento vinculado
            // CadastroInsumo ci = new CadastroInsumo();
            // ci.setInsumoId(idGerado);
            // ci.setRendeAplicacoes(rendimento);
            // cadastroInsumoDAO.cadastrarRendimento(ci);

            // Como o código base do DAO não retorna ID, deixo apenas a inserção simples ativa:
            insumoDAO.cadastrar(insumo);

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Certifique-se de que Valor, Quantidade e Rendimento sejam numéricos.");
        }
    }

    public List<Insumo> listarEstoque() {
        return insumoDAO.listarEstoque();
    }
}