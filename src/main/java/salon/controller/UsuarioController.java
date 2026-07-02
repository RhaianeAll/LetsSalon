package salon.controller;

import java.util.List;
import salon.dao.UsuarioDAO;
import salon.model.Usuario;
import salon.util.ValidadorUtil;

public class UsuarioController {

    private UsuarioDAO usuarioDAO;

    public UsuarioController() {
        this.usuarioDAO = new UsuarioDAO();
    }
    
    public List<Usuario> listarUsuarios() {
        return usuarioDAO.listarTodos();
    }

    public void cadastrarCliente(String nomeCompleto, String cpf, String telefone, String nomeUsuario, String senha, String confirmacaoSenha) {
        
        // 1. Validações básicas
        if (nomeCompleto.isEmpty() || cpf.isEmpty() || telefone.isEmpty() || nomeUsuario.isEmpty() || senha.isEmpty()) {
            throw new IllegalArgumentException("Por favor, preencha todos os campos.");
        }
        
        if (!senha.equals(confirmacaoSenha)) {
            throw new IllegalArgumentException("As senhas não coincidem. Digite novamente.");
        }

        // 2. Monta o objeto
        Usuario u = new Usuario();
        u.setNomeCompleto(nomeCompleto);
        u.setCpf(cpf);
        u.setTelefone(telefone);
        u.setUsuario(nomeUsuario); 
        u.setSenha(senha); // Num sistema real, usaríamos criptografia aqui (ex: BCrypt)
        u.setTipoUsuario("cliente"); // Força o tipo para 'cliente'
        
        // 3. Salva no banco
        usuarioDAO.cadastrar(u);
    }
    
    public Usuario buscarAdmin(String login) {
        return usuarioDAO.buscarPorUsuario(login);
    }

    public void atualizarAdmin(Usuario u, String novaSenha, String confirmacao) {
        boolean atualizarSenha = false;
        
        // Se ele digitou algo na senha, vamos validar e atualizar
        if (novaSenha != null && !novaSenha.trim().isEmpty()) {
            if (!novaSenha.equals(confirmacao)) {
                throw new IllegalArgumentException("As senhas não coincidem!");
            }
            u.setSenha(novaSenha);
            atualizarSenha = true;
        }
        
        usuarioDAO.atualizarAdmin(u, atualizarSenha);
    }
}