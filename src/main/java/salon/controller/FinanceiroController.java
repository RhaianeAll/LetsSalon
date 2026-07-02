package salon.controller;

import salon.dao.FinanceiroDAO;
import salon.model.Financeiro;
import salon.util.ValidadorUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class FinanceiroController {

    private FinanceiroDAO financeiroDAO;

    public FinanceiroController() {
        this.financeiroDAO = new FinanceiroDAO();
    }

    public void registrarMovimentacao(String tipo, String descricao, String categoria, String valorStr, String dataStr, Integer funcionarioId, Integer servicoId, String custoInsumosStr) {
        
        if (!ValidadorUtil.camposPreenchidos(tipo, descricao, valorStr, dataStr)) {
            throw new IllegalArgumentException("Tipo, Descrição, Valor e Data são obrigatórios.");
        }

        try {
            double valor = Double.parseDouble(valorStr.replace(",", "."));
            double custoInsumos = 0.0;
            
            if (custoInsumosStr != null && !custoInsumosStr.trim().isEmpty()) {
                custoInsumos = Double.parseDouble(custoInsumosStr.replace(",", "."));
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate data = LocalDate.parse(dataStr, formatter);

            // Cálculo dinâmico do lucro
            double lucro = 0.0;
            if (tipo.equalsIgnoreCase("entrada")) {
                lucro = valor - custoInsumos;
            } else {
                lucro = -valor; // Se for saída, é contabilizado como valor negativo no balanço
            }

            Financeiro f = new Financeiro();
            f.setTipo(tipo.toLowerCase());
            f.setDescricao(descricao);
            f.setCategoria(categoria);
            f.setValor(valor);
            f.setData(data);
            f.setFuncionarioId(funcionarioId);
            f.setServicoId(servicoId);
            f.setCustoInsumos(custoInsumos);
            f.setLucro(lucro);

            financeiroDAO.cadastrar(f);

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Formato numérico inválido para Valor ou Custo de Insumos.");
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de data inválido. Use DD/MM/AAAA.");
        }
    }
    
    

    public List<Financeiro> obterRelatorioGeral() {
        return financeiroDAO.listarRelatorio();
    }
}