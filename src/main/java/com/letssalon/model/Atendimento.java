// Atendimento.java
package com.letssalon.model;

public class Atendimento {
    private int idAtendimento;
    private int idColaborador;
    private int codigoServico;
    private String cpfCliente;
    private String dataHora;
    private double valorArrecadado;
    private int codigoInsumo;
    private int qtdAplicacoes;
    private double custoInsumo;
    private int codigoEmpresa;

    public Atendimento(int idAtendimento, int idColaborador, int codigoServico, String cpfCliente, String dataHora, double valorArrecadado, int codigoInsumo, int qtdAplicacoes, double custoInsumo, int codigoEmpresa) {
        this.idAtendimento = idAtendimento;
        this.idColaborador = idColaborador;
        this.codigoServico = codigoServico;
        this.cpfCliente = cpfCliente;
        this.dataHora = dataHora;
        this.valorArrecadado = valorArrecadado;
        this.codigoInsumo = codigoInsumo;
        this.qtdAplicacoes = qtdAplicacoes;
        this.custoInsumo = custoInsumo;
        this.codigoEmpresa = codigoEmpresa;
    }
    
       public Atendimento() {
}

    public int getIdAtendimento() {
        return idAtendimento;
    }

    public int getIdColaborador() {
        return idColaborador;
    }

    public int getCodigoServico() {
        return codigoServico;
    }

    public String getCpfCliente() {
        return cpfCliente;
    }

    public String getDataHora() {
        return dataHora;
    }

    public double getValorArrecadado() {
        return valorArrecadado;
    }

    public int getCodigoInsumo() {
        return codigoInsumo;
    }

    public int getQtdAplicacoes() {
        return qtdAplicacoes;
    }

    public double getCustoInsumo() {
        return custoInsumo;
    }

    public int getCodigoEmpresa() {
        return codigoEmpresa;
    }

    public void setIdAtendimento(int idAtendimento) {
        this.idAtendimento = idAtendimento;
    }

    public void setIdColaborador(int idColaborador) {
        this.idColaborador = idColaborador;
    }

    public void setCodigoServico(int codigoServico) {
        this.codigoServico = codigoServico;
    }

    public void setCpfCliente(String cpfCliente) {
        this.cpfCliente = cpfCliente;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

    public void setValorArrecadado(double valorArrecadado) {
        this.valorArrecadado = valorArrecadado;
    }

    public void setCodigoInsumo(int codigoInsumo) {
        this.codigoInsumo = codigoInsumo;
    }

    public void setQtdAplicacoes(int qtdAplicacoes) {
        this.qtdAplicacoes = qtdAplicacoes;
    }

    public void setCustoInsumo(double custoInsumo) {
        this.custoInsumo = custoInsumo;
    }

    public void setCodigoEmpresa(int codigoEmpresa) {
        this.codigoEmpresa = codigoEmpresa;
    }
    
}