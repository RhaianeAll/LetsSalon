// Servico.java
package com.letssalon.model;

public class Servico {
   private int codigoServico;
private String nomeServico;
private double valorServico;
private String duracaoAproximada;
private int idColaborador;
private int codigoInsumo;
private int quantidadeInsumo;
private int codigoEmpresa;

private String nomeColaborador;
private String nomeInsumo;

    public void setNomeColaborador(String nomeColaborador) {
        this.nomeColaborador = nomeColaborador;
    }

    public void setNomeInsumo(String nomeInsumo) {
        this.nomeInsumo = nomeInsumo;
    }

    public String getNomeColaborador() {
        return nomeColaborador;
    }

    public String getNomeInsumo() {
        return nomeInsumo;
    }

       public Servico() {
}
       
    public Servico(int codigoServico, String nomeServico, double valorServico, String duracaoAproximada, int idColaborador, int codigoInsumo, int quantidadeInsumo, int codigoEmpresa) {
        this.codigoServico = codigoServico;
        this.nomeServico = nomeServico;
        this.valorServico = valorServico;
        this.duracaoAproximada = duracaoAproximada;
        this.idColaborador = idColaborador;
        this.codigoInsumo = codigoInsumo;
        this.quantidadeInsumo = quantidadeInsumo;
        this.codigoEmpresa = codigoEmpresa;
    }

    public int getCodigoServico() {
        return codigoServico;
    }

    public String getNomeServico() {
        return nomeServico;
    }

    public double getValorServico() {
        return valorServico;
    }

    public String getDuracaoAproximada() {
        return duracaoAproximada;
    }

    public int getIdColaborador() {
        return idColaborador;
    }

    public int getCodigoInsumo() {
        return codigoInsumo;
    }

    public int getQuantidadeInsumo() {
        return quantidadeInsumo;
    }

    public int getCodigoEmpresa() {
        return codigoEmpresa;
    }

    public void setCodigoServico(int codigoServico) {
        this.codigoServico = codigoServico;
    }

    public void setNomeServico(String nomeServico) {
        this.nomeServico = nomeServico;
    }

    public void setValorServico(double valorServico) {
        this.valorServico = valorServico;
    }

    public void setDuracaoAproximada(String duracaoAproximada) {
        this.duracaoAproximada = duracaoAproximada;
    }

    public void setIdColaborador(int idColaborador) {
        this.idColaborador = idColaborador;
    }

    public void setCodigoInsumo(int codigoInsumo) {
        this.codigoInsumo = codigoInsumo;
    }

    public void setQuantidadeInsumo(int quantidadeInsumo) {
        this.quantidadeInsumo = quantidadeInsumo;
    }

    public void setCodigoEmpresa(int codigoEmpresa) {
        this.codigoEmpresa = codigoEmpresa;
    }

}