package salon.controller;

import salon.dao.UsuarioDAO;
import salon.model.Usuario;
import salon.util.Sessao;
import salon.util.ValidadorUtil;

public class LoginController {

    private UsuarioDAO usuarioDAO;

    public LoginController() {
        this.usuarioDAO = new UsuarioDAO();
    }

    // Retorna true se o login der certo, false se der errado
    public boolean fazerLogin(String login, String senha) {
        if (!ValidadorUtil.camposPreenchidos(login, senha)) {
            throw new IllegalArgumentException("Por favor, preencha usuário e senha.");
        }

        Usuario usuarioAutenticado = usuarioDAO.autenticar(login, senha);

        if (usuarioAutenticado != null) {
            // Salva o usuário logado na sessão global
            Sessao.getInstancia().setUsuarioLogado(usuarioAutenticado);
            return true;
        }
        return false;
    }

    public void fazerLogout() {
        Sessao.getInstancia().encerrarSessao();
    }
}