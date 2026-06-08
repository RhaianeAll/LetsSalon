package com.letssalon.model;

public class Empresa {
    
    private int codigoEmpresa;
    private String nomeEmpresa;
    private String telefoneEmpresa;
    private String emailEmpresa;
    private String enderecoEmpresa;

    // Getters e Setters
    public int getCodigoEmpresa() { return codigoEmpresa; }
    public void setCodigoEmpresa(int codigoEmpresa) { this.codigoEmpresa = codigoEmpresa; }

    public String getNomeEmpresa() { return nomeEmpresa; }
    public void setNomeEmpresa(String nomeEmpresa) { this.nomeEmpresa = nomeEmpresa; }

    public String getTelefoneEmpresa() { return telefoneEmpresa; }
    public void setTelefoneEmpresa(String telefoneEmpresa) { this.telefoneEmpresa = telefoneEmpresa; }

    public String getEmailEmpresa() { return emailEmpresa; }
    public void setEmailEmpresa(String emailEmpresa) { this.emailEmpresa = emailEmpresa; }

    public String getEnderecoEmpresa() { return enderecoEmpresa; }
    public void setEnderecoEmpresa(String enderecoEmpresa) { this.enderecoEmpresa = enderecoEmpresa; }
}