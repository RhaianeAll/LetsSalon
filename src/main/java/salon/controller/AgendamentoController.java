package salon.controller;

import salon.dao.AgendamentoDAO;
import salon.dao.EmpresaDAO;
import salon.model.Agendamento;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AgendamentoController {
    
    private AgendamentoDAO agendamentoDAO;
    private EmpresaDAO empresaDAO;

    public AgendamentoController() {
        this.agendamentoDAO = new AgendamentoDAO();
        this.empresaDAO = new EmpresaDAO();
    }

    public List<Agendamento> listarConfirmacoesPendentesPorEmpresa(String nomeEmpresa) {
        return agendamentoDAO.listarPendentesPorEmpresa(nomeEmpresa);
    }

    public List<Agendamento> listarAgendamentosGeraisPorEmpresa(String nomeEmpresa) {
        return agendamentoDAO.listarAgendamentosGeraisPorEmpresa(nomeEmpresa);
    }

    public void solicitarAgendamento(int idCliente, int idFuncionario, int idServico, LocalDate data, LocalTime hora, String nomeEmpresa) {
        if (data.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Não é possível agendar em datas passadas.");
        }
        
        // CORREÇÃO: Usando a variável 'idFuncionario' exatamente como foi escrita nos parâmetros acima!
        if (agendamentoDAO.isHorarioOcupado(idFuncionario, data, hora)) {
            throw new IllegalArgumentException("O(a) profissional já possui um serviço marcado neste horário!");
        }

        int idEmpresa = empresaDAO.buscarIdPorNome(nomeEmpresa);
        
        Agendamento a = new Agendamento();
        a.setClienteId(idCliente);
        a.setFuncionarioId(idFuncionario); // Aqui também usa o 'idFuncionario' corretamente
        a.setServicoId(idServico);
        a.setDataAgendamento(data);
        a.setHora(hora);
        a.setStatus("Pendente");
        a.setEmpresaId(idEmpresa);
        
        agendamentoDAO.cadastrar(a);
    }
    
    public void aprovarAgendamento(int idAgendamento) {
        agendamentoDAO.atualizarStatus(idAgendamento, "Agendado");
    }

    public void alterarStatus(int idAgendamento, String novoStatus) {
        agendamentoDAO.atualizarStatus(idAgendamento, novoStatus);
    }
    
    public void excluirAgendamento(int idAgendamento) {
        agendamentoDAO.excluir(idAgendamento);
    }
    
    public void atualizarAgendamento(int id, int clienteId, int funcionarioId, int servicoId, LocalDate data, LocalTime hora) {
        Agendamento a = new Agendamento();
        a.setIdAgendamento(id);
        a.setClienteId(clienteId);
        a.setFuncionarioId(funcionarioId);
        a.setServicoId(servicoId);
        a.setDataAgendamento(data);
        a.setHora(hora);
        agendamentoDAO.atualizar(a);
    }

    public void recusarOuCancelarAgendamento(int idAgendamento) {
        agendamentoDAO.atualizarStatus(idAgendamento, "Cancelado");
    }

    // --- NOVOS MÉTODOS PARA O DASHBOARD ---
    
    public int contarPendentes(String nomeEmpresa) {
        return agendamentoDAO.contarPendentesPorEmpresa(nomeEmpresa);
    }

    public List<Agendamento> listarConcluidosAntigos(String nomeEmpresa) {
        // Calcula a data de 15 dias atrás
        LocalDate dataLimite = LocalDate.now().minusDays(15);
        // Traz apenas 5 resultados para não lotar a tela inicial
        return agendamentoDAO.listarConcluidosAntigosPorEmpresa(nomeEmpresa, dataLimite, 5);
    }
}