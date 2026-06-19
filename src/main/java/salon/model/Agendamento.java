package salon.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Agendamento {
    private int idAgendamento;
    private int clienteId;
    private int funcionarioId;
    private int servicoId;
    private LocalDate data;
    private LocalTime hora;
    private String status;
    private String observacoes;

    public Agendamento() {}

    public Agendamento(int idAgendamento, int clienteId, int funcionarioId, int servicoId, LocalDate data, LocalTime hora, String status, String observacoes) {
        this.idAgendamento = idAgendamento;
        this.clienteId = clienteId;
        this.funcionarioId = funcionarioId;
        this.servicoId = servicoId;
        this.data = data;
        this.hora = hora;
        this.status = status;
        this.observacoes = observacoes;
    }

    public int getIdAgendamento() { return idAgendamento; }
    public void setIdAgendamento(int idAgendamento) { this.idAgendamento = idAgendamento; }

    public int getClienteId() { return clienteId; }
    public void setClienteId(int clienteId) { this.clienteId = clienteId; }

    public int getFuncionarioId() { return funcionarioId; }
    public void setFuncionarioId(int funcionarioId) { this.funcionarioId = funcionarioId; }

    public int getServicoId() { return servicoId; }
    public void setServicoId(int servicoId) { this.servicoId = servicoId; }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }

    public LocalTime getHora() { return hora; }
    public void setHora(LocalTime hora) { this.hora = hora; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
}