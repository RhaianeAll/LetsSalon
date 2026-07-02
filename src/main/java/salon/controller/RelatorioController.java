package salon.controller;

import salon.dao.RelatorioDAO;
import java.util.List;

public class RelatorioController {
    private RelatorioDAO relatorioDAO;

    public RelatorioController() {
        this.relatorioDAO = new RelatorioDAO();
    }

    public List<Object[]> getRegistroAtendimentos() {
        return relatorioDAO.gerarRegistroAtendimentos();
    }
    
    // Adicione no RelatorioController.java
    public List<Object[]> getRelatorioFinanceiro() {
        return relatorioDAO.gerarRelatorioFinanceiro();
    }
    
    public List<Object[]> getRelatorioGeral() {
        return relatorioDAO.gerarRelatorioGeral();
    }

    public List<Object[]> getRelatorioParcial() {
        return relatorioDAO.gerarRelatorioParcial();
    }
}