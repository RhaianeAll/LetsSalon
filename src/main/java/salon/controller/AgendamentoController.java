package salon.controller;

import salon.dao.AgendamentoDAO;
import salon.model.Agendamento;
import salon.util.ValidadorUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class AgendamentoController {

    private AgendamentoDAO agendamentoDAO;

    public AgendamentoController() {
        this.agendamentoDAO = new AgendamentoDAO();
    }

    // Os IDs vêm das JComboBoxes da tela, que terão o objeto associado selecionado
    public void criarAgendamento(int idCliente, int idFuncionario, int idServico, String dataStr, String horaStr, String observacoes) {
        
        if (!ValidadorUtil.camposPreenchidos(dataStr, horaStr)) {
            throw new IllegalArgumentException("Data e hora são obrigatórios.");
        }

        if (idCliente <= 0 || idFuncionario <= 0 || idServico <= 0) {
            throw new IllegalArgumentException("Selecione o cliente, o profissional e o serviço.");
        }

        try {
            // Converte as Strings da View para Objetos de Tempo
            DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate data = LocalDate.parse(dataStr, formatterData);
            
            DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime hora = LocalTime.parse(horaStr, formatterHora);

            // Regra de negócio: não agendar no passado
            if (data.isBefore(LocalDate.now())) {
                throw new IllegalArgumentException("Não é possível agendar em datas passadas.");
            }

            Agendamento agendamento = new Agendamento();
            agendamento.setClienteId(idCliente);
            agendamento.setFuncionarioId(idFuncionario);
            agendamento.setServicoId(idServico);
            agendamento.setData(data);
            agendamento.setHora(hora);
            agendamento.setStatus("Pendente");
            agendamento.setObservacoes(observacoes);

            agendamentoDAO.cadastrar(agendamento);

        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de data ou hora inválido. Use DD/MM/AAAA e HH:MM.");
        }
    }
    
    public void confirmarAgendamento(int idAgendamento) {
        agendamentoDAO.atualizarStatus(idAgendamento, "Confirmado");
    }
    
    public void cancelarAgendamento(int idAgendamento) {
        agendamentoDAO.atualizarStatus(idAgendamento, "Cancelado");
    }
}