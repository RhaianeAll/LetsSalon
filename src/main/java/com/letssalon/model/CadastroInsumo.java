// CadastroInsumo.java
package com.letssalon.model;

public class CadastroInsumo {
    private int codigoInsumo;
    private int rendeAplicacoes;
    private int codigoServico;

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
    
}