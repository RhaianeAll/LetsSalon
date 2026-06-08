// Colaborador.java
package com.letssalon.model;

public class Colaborador {
    private int idColaborador;
    private String cpfColaborador;
    private String nomeColaborador;
    private String funcaoColaborador;
    private String telefoneColaborador;
    private String fotoColaborador;
    private String senhaColaborador;
    private String perfil;
    private int codigoEmpresa;
    private String emailColaborador;

    // Construtor vazio - PRECISA existir
public Colaborador() {
}

// Construtor com todos os parâmetros - gerado pelo NetBeans
public Colaborador(int idColaborador, String cpfColaborador, String nomeColaborador, 
                   String funcaoColaborador, String telefoneColaborador, String fotoColaborador, 
                   String senhaColaborador, String perfil, int codigoEmpresa, String emailColaborador) {
    this.idColaborador = idColaborador;
    this.cpfColaborador = cpfColaborador;
    this.nomeColaborador = nomeColaborador;
    this.funcaoColaborador = funcaoColaborador;
    this.telefoneColaborador = telefoneColaborador;
    this.fotoColaborador = fotoColaborador;
    this.senhaColaborador = senhaColaborador;
    this.perfil = perfil;
    this.codigoEmpresa = codigoEmpresa;
    this.emailColaborador = emailColaborador;
}
    
    public int getIdColaborador() {
        return idColaborador;
    }

    public String getCpfColaborador() {
        return cpfColaborador;
    }

    public String getNomeColaborador() {
        return nomeColaborador;
    }

    public String getFuncaoColaborador() {
        return funcaoColaborador;
    }

    public String getTelefoneColaborador() {
        return telefoneColaborador;
    }

    public String getFotoColaborador() {
        return fotoColaborador;
    }

    public String getSenhaColaborador() {
        return senhaColaborador;
    }

    public String getPerfil() {
        return perfil;
    }

    public int getCodigoEmpresa() {
        return codigoEmpresa;
    }

    public void setIdColaborador(int idColaborador) {
        this.idColaborador = idColaborador;
    }

    public void setCpfColaborador(String cpfColaborador) {
        this.cpfColaborador = cpfColaborador;
    }

    public void setNomeColaborador(String nomeColaborador) {
        this.nomeColaborador = nomeColaborador;
    }

    public void setFuncaoColaborador(String funcaoColaborador) {
        this.funcaoColaborador = funcaoColaborador;
    }

    public void setTelefoneColaborador(String telefoneColaborador) {
        this.telefoneColaborador = telefoneColaborador;
    }

    public void setFotoColaborador(String fotoColaborador) {
        this.fotoColaborador = fotoColaborador;
    }

  
    public void setSenhaColaborador(String senhaColaborador) {
        this.senhaColaborador = senhaColaborador;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public void setCodigoEmpresa(int codigoEmpresa) {
        this.codigoEmpresa = codigoEmpresa;
    }

    public String getEmailColaborador() {
        return emailColaborador;
    }

    public void setEmailColaborador(String emailColaborador) {
        this.emailColaborador = emailColaborador;
    }
    

}