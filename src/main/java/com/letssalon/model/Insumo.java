// Insumo.java
package com.letssalon.model;

public class Insumo {
    private int codigoInsumo;
    private String nomeInsumo;
    private double valorInsumo;
    private int quantidadeInsumo;
    private String capacidade;
    private int codigoEmpresa;

    public Insumo(int codigoInsumo, String nomeInsumo, double valorInsumo, int quantidadeInsumo, String capacidade, int codigoEmpresa) {
        this.codigoInsumo = codigoInsumo;
        this.nomeInsumo = nomeInsumo;
        this.valorInsumo = valorInsumo;
        this.quantidadeInsumo = quantidadeInsumo;
        this.capacidade = capacidade;
        this.codigoEmpresa = codigoEmpresa;
    }
       public Insumo() {
}

    public int getCodigoInsumo() {
        return codigoInsumo;
    }

    public String getNomeInsumo() {
        return nomeInsumo;
    }

    public double getValorInsumo() {
        return valorInsumo;
    }

    public int getQuantidadeInsumo() {
        return quantidadeInsumo;
    }

    public String getCapacidade() {
        return capacidade;
    }

    public int getCodigoEmpresa() {
        return codigoEmpresa;
    }

    public void setCodigoInsumo(int codigoInsumo) {
        this.codigoInsumo = codigoInsumo;
    }

    public void setNomeInsumo(String nomeInsumo) {
        this.nomeInsumo = nomeInsumo;
    }

    public void setValorInsumo(double valorInsumo) {
        this.valorInsumo = valorInsumo;
    }

    public void setQuantidadeInsumo(int quantidadeInsumo) {
        this.quantidadeInsumo = quantidadeInsumo;
    }

    public void setCapacidade(String capacidade) {
        this.capacidade = capacidade;
    }

    public void setCodigoEmpresa(int codigoEmpresa) {
        this.codigoEmpresa = codigoEmpresa;
    }

}