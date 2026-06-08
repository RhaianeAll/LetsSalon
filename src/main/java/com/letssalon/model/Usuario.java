/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author diogenes
 */
package com.letssalon.model;

public class Usuario {
    private String nomeUsuario;
    private String cpfUsuario;
    private String emailUsuario;
    private String senhaUsuario;
    private String telefoneUsuario;
    private String perfil;
    private int codigoEmpresa;

    public Usuario(String nomeUsuario, String cpfUsuario, String emailUsuario, String senhaUsuario, String telefoneUsuario, String perfil, int codigoEmpresa) {
        this.nomeUsuario = nomeUsuario;
        this.cpfUsuario = cpfUsuario;
        this.emailUsuario = emailUsuario;
        this.senhaUsuario = senhaUsuario;
        this.telefoneUsuario = telefoneUsuario;
        this.perfil = perfil;
        this.codigoEmpresa = codigoEmpresa;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public String getCpfUsuario() {
        return cpfUsuario;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public String getSenhaUsuario() {
        return senhaUsuario;
    }

    public String getTelefoneUsuario() {
        return telefoneUsuario;
    }

    public String getPerfil() {
        return perfil;
    }

    public int getCodigoEmpresa() {
        return codigoEmpresa;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public void setCpfUsuario(String cpfUsuario) {
        this.cpfUsuario = cpfUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public void setSenhaUsuario(String senhaUsuario) {
        this.senhaUsuario = senhaUsuario;
    }

    public void setTelefoneUsuario(String telefoneUsuario) {
        this.telefoneUsuario = telefoneUsuario;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public void setCodigoEmpresa(int codigoEmpresa) {
        this.codigoEmpresa = codigoEmpresa;
    }
    
}


