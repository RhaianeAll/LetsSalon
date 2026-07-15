// CadastroInsumo.java
package com.letssalon.model;

public class CadastroInsumo {
    private int codigoInsumo;
    private int rendeAplicacoes;
    private int codigoServico;
    private String nomeInsumo;
    private String nomeServico;        

     public CadastroInsumo() {
}
     
    public CadastroInsumo(int codigoInsumo, int rendeAplicacoes, int codigoServico) {
        this.codigoInsumo = codigoInsumo;
        this.rendeAplicacoes = rendeAplicacoes;
        this.codigoServico = codigoServico;
    }

    public int getCodigoInsumo() {
        return codigoInsumo;
    }

    public int getRendeAplicacoes() {
        return rendeAplicacoes;
    }

    public int getCodigoServico() {
        return codigoServico;
    }

    public void setCodigoInsumo(int codigoInsumo) {
        this.codigoInsumo = codigoInsumo;
    }

    public void setRendeAplicacoes(int rendeAplicacoes) {
        this.rendeAplicacoes = rendeAplicacoes;
    }

    public void setCodigoServico(int codigoServico) {
        this.codigoServico = codigoServico;
    }
    public String getNomeInsumo() {
    return nomeInsumo;
}

public void setNomeInsumo(String nomeInsumo) {
    this.nomeInsumo = nomeInsumo;
}

public String getNomeServico() {
    return nomeServico;
}

public void setNomeServico(String nomeServico) {
    this.nomeServico = nomeServico;
}
}