package salon.controller;

import salon.dao.AgendamentoDAO;
import salon.model.Agendamento;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AgendamentoController {

    private AgendamentoDAO agendamentoDAO;

    public AgendamentoController() {
        this.agendamentoDAO = new AgendamentoDAO();
    }

    public void solicitarAgendamento(int clienteId, int funcionarioId, int servicoId, LocalDate data, LocalTime hora) {
        // Validação 1: Impede agendamento no passado (opcional, mas recomendado)
        if (data.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Não é possível agendar em datas passadas.");
        }

        // Validação 2: Choque de Horários
        if (agendamentoDAO.isHorarioOcupado(funcionarioId, data, hora)) {
            throw new IllegalArgumentException("O(a) profissional já possui um serviço marcado neste horário!");
        }

        // Se passou pelas validações, monta o objeto e salva
        Agendamento a = new Agendamento();
        a.setClienteId(clienteId);
        a.setFuncionarioId(funcionarioId);
        a.setServicoId(servicoId);
        a.setDataAgendamento(data);
        a.setHora(hora);
        a.setStatus("Pendente"); // Sempre entra como pendente até aprovação do admin

        agendamentoDAO.cadastrar(a);
    }

    public List<Agendamento> listarConfirmacoesPendentes() {
        return agendamentoDAO.listarPendentes();
    }

    public void aprovarAgendamento(int idAgendamento) {
        agendamentoDAO.atualizarStatus(idAgendamento, "Confirmado");
    }

    public void recusarOuCancelarAgendamento(int idAgendamento) {
        agendamentoDAO.atualizarStatus(idAgendamento, "Cancelado");
    }
}