package salon.controller;

import salon.dao.FinanceiroDAO;
import salon.dao.EmpresaDAO;
import salon.model.Financeiro;
import salon.util.ValidadorUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class FinanceiroController {

    private FinanceiroDAO financeiroDAO;
    private EmpresaDAO empresaDAO;

    public FinanceiroController() {
        this.financeiroDAO = new FinanceiroDAO();
        this.empresaDAO = new EmpresaDAO();
    }

    public void registrarMovimentacao(String tipo, String descricao, String categoria, String valorStr, String dataStr, Integer funcionarioId, Integer servicoId, String custoInsumosStr, String nomeEmpresa) {
        
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

            double lucro = 0.0;
            if (tipo.equalsIgnoreCase("entrada")) { lucro = valor - custoInsumos; } 
            else { lucro = -valor; }

            int idEmpresa = empresaDAO.buscarIdPorNome(nomeEmpresa);

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
            f.setEmpresaId(idEmpresa); // Blinda a finança à empresa

            financeiroDAO.cadastrar(f);

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Formato numérico inválido para Valor ou Custo de Insumos.");
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de data inválido. Use DD/MM/AAAA.");
        }
    }

    public List<Financeiro> obterRelatorioGeral(String nomeEmpresa) {
        return financeiroDAO.listarRelatorio(nomeEmpresa);
    }
}