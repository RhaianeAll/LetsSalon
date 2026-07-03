package salon.controller;

import salon.dao.RelatorioDAO;
import java.util.List;

public class RelatorioController {
    private RelatorioDAO relatorioDAO;

    public RelatorioController() {
        this.relatorioDAO = new RelatorioDAO();
    }

    public List<Object[]> getRegistroAtendimentos(String nomeEmpresa) {
        return relatorioDAO.gerarRegistroAtendimentos(nomeEmpresa);
    }
    
    public List<Object[]> getRelatorioFinanceiro(String nomeEmpresa) {
        return relatorioDAO.gerarRelatorioFinanceiro(nomeEmpresa);
    }
    
    public List<Object[]> getRelatorioGeral(String nomeEmpresa) {
        return relatorioDAO.gerarRelatorioGeral(nomeEmpresa);
    }

    public List<Object[]> getRelatorioParcial(String nomeEmpresa) {
        return relatorioDAO.gerarRelatorioParcial(nomeEmpresa);
    }
}