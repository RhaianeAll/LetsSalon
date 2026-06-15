// Agenda.java
package com.letssalon.model;

public class Agenda {
    private int idAgendamento;
    private String cpfCliente;
    private String data;
    private String horario;
    private int idColaborador;
    private int codigoServico;
    private String status;
    private int codigoEmpresa;

    public Agenda() {
}
    
    public Agenda(int idAgendamento, String cpfCliente, String data, String horario, int idColaborador, int codigoServico, String status, int codigoEmpresa) {
        this.idAgendamento = idAgendamento;
        this.cpfCliente = cpfCliente;
        this.data = data;
        this.horario = horario;
        this.idColaborador = idColaborador;
        this.codigoServico = codigoServico;
        this.status = status;
        this.codigoEmpresa = codigoEmpresa;
    }

    public int getIdAgendamento() {
        return idAgendamento;
    }

    public String getCpfCliente() {
        return cpfCliente;
    }

    public String getData() {
        return data;
    }

    public String getHorario() {
        return horario;
    }

    public int getIdColaborador() {
        return idColaborador;
    }

    public int getCodigoServico() {
        return codigoServico;
    }

    public String getStatus() {
        return status;
    }

    public int getCodigoEmpresa() {
        return codigoEmpresa;
    }

    public void setIdAgendamento(int idAgendamento) {
        this.idAgendamento = idAgendamento;
    }

    public void setCpfCliente(String cpfCliente) {
        this.cpfCliente = cpfCliente;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public void setIdColaborador(int idColaborador) {
        this.idColaborador = idColaborador;
    }

    public void setCodigoServico(int codigoServico) {
        this.codigoServico = codigoServico;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCodigoEmpresa(int codigoEmpresa) {
        this.codigoEmpresa = codigoEmpresa;
    }
    
}