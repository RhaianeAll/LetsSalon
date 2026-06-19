package salon.util;

import salon.model.Funcionario;
import salon.model.Usuario;

public class Sessao {
    
    // Instância única global (Singleton)
    private static Sessao instancia;
    
    private Usuario usuarioLogado;
    private Funcionario funcionarioLogado;

    private Sessao() {}

    public static Sessao getInstancia() {
        if (instancia == null) {
            instancia = new Sessao();
        }
        return instancia;
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
        this.funcionarioLogado = null; // Garante que apenas um tipo de perfil fique ativo
    }

    public Funcionario getFuncionarioLogado() {
        return funcionarioLogado;
    }

    public void setFuncionarioLogado(Funcionario funcionarioLogado) {
        this.funcionarioLogado = funcionarioLogado;
        this.usuarioLogado = null;
    }
    
    public void encerrarSessao() {
        this.usuarioLogado = null;
        this.funcionarioLogado = null;
    }
}