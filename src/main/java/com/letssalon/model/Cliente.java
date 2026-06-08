package com.letssalon.model;

public class Cliente {
    private String cpfCliente;
    private String nomeCliente;
    private String enderecoCliente;
    private String telefoneCliente;
    private String emailCliente;
    private String dataNascimento;
    private String senhaCliente;
    private String perfil;

    public Cliente() {
}
    public Cliente(String cpfCliente, String nomeCliente, String enderecoCliente, String telefoneCliente, String emailCliente, String dataNascimento, String senhaCliente, String perfil) {
        this.cpfCliente = cpfCliente;
        this.nomeCliente = nomeCliente;
        this.enderecoCliente = enderecoCliente;
        this.telefoneCliente = telefoneCliente;
        this.emailCliente = emailCliente;
        this.dataNascimento = dataNascimento;
        this.senhaCliente = senhaCliente;
        this.perfil = perfil;
    }

    public String getCpfCliente() {
        return cpfCliente;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public String getEnderecoCliente() {
        return enderecoCliente;
    }

    public String getTelefoneCliente() {
        return telefoneCliente;
    }

    public String getEmailCliente() {
        return emailCliente;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

   
    public String getSenhaCliente() {
        return senhaCliente;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setCpfCliente(String cpfCliente) {
        this.cpfCliente = cpfCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public void setEnderecoCliente(String enderecoCliente) {
        this.enderecoCliente = enderecoCliente;
    }

    public void setTelefoneCliente(String telefoneCliente) {
        this.telefoneCliente = telefoneCliente;
    }

    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

      public void setSenhaCliente(String senhaCliente) {
        this.senhaCliente = senhaCliente;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }
    
    
}