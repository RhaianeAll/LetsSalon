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
    
    public String cadastrarColaborador(String nomeCompleto, String cpf, String telefone, String nomeUsuario, String senha, String confirmacaoSenha) {
        if (nomeCompleto.isEmpty() || cpf.isEmpty() || telefone.isEmpty() || nomeUsuario.isEmpty() || senha.isEmpty()) {
            throw new IllegalArgumentException("Por favor, preencha todos os campos obrigatórios.");
        }
        if (!senha.equals(confirmacaoSenha)) {
            throw new IllegalArgumentException("As senhas não coincidem. Digite novamente.");
        }
        if (usuarioDAO.verificarUsuarioExistente(nomeUsuario, 0)) {
            throw new IllegalArgumentException("Este nome de usuário já está em uso. Por favor, escolha outro.");
        }

        Usuario u = new Usuario();
        u.setNomeCompleto(nomeCompleto);
        u.setCpf(cpf);
        u.setTelefone(telefone);
        u.setUsuario(nomeUsuario); 
        u.setSenha(senha);
        u.setTipoUsuario("colaborador"); 
        
        usuarioDAO.cadastrar(u); // Salva o usuário primeiro
        
        int numeroGerado = (int) (Math.random() * 9000) + 1000; 
        String codigoEmpresa = String.valueOf(numeroGerado);
        
        // A MÁGICA: Guarda o código gerado no banco de dados!
        usuarioDAO.atualizarCodigoEmpresa(nomeUsuario, codigoEmpresa);
        
        return codigoEmpresa; 
    }
    
    public Usuario fazerLoginColaborador(String login, String senha, String codigoEmpresa) {
        if (login.isEmpty() || senha.isEmpty() || codigoEmpresa.isEmpty()) {
            throw new IllegalArgumentException("Por favor, preencha o usuário, a senha e o código da empresa.");
        }
        
        Usuario u = usuarioDAO.autenticarColaborador(login, senha, codigoEmpresa);
        
        if (u == null) {
            throw new IllegalArgumentException("Usuário, Senha ou Código da Empresa incorretos!");
        }
        return u;
    }
    
    public Usuario fazerLogin(String login, String senha) {
        if (login == null || login.trim().isEmpty() || senha == null || senha.trim().isEmpty()) {
            throw new IllegalArgumentException("Por favor, preencha o usuário e a senha.");
        }
        
        Usuario u = usuarioDAO.autenticar(login, senha);
        
        if (u == null) {
            // AQUI ESTÁ A TRAVA! Se a senha for errada, ele para o código aqui e lança esse erro:
            throw new IllegalArgumentException("Usuário ou senha incorretos!");
        }
        
        return u;
    }
    
    public Usuario buscarAdmin(String login) {
        return usuarioDAO.buscarAdmin(login); // Chama o método exclusivo
    }

    public void atualizarAdmin(Usuario u, String novaSenha, String confirmacao) {
        boolean atualizarSenha = false;
        
        if (novaSenha != null && !novaSenha.trim().isEmpty()) {
            if (!novaSenha.equals(confirmacao)) {
                throw new IllegalArgumentException("As senhas não coincidem!");
            }
            u.setSenha(novaSenha);
            atualizarSenha = true;
        }
        
        usuarioDAO.atualizarAdmin(u, atualizarSenha);
    }
    
    public Usuario buscarUsuarioPorLogin(String login) {
        return usuarioDAO.buscarUsuarioPorLogin(login);
    }

    public void atualizarPerfil(Usuario u, String novaSenha, String confirmacao) {
        
        // ---> A TRAVA DE NOME DUPLICADO PARA A EDIÇÃO <---
        // Passamos o ID do usuário para o sistema ignorá-lo na hora da busca!
        if (usuarioDAO.verificarUsuarioExistente(u.getUsuario(), u.getIdUsuario())) {
            throw new IllegalArgumentException("Este nome de usuário já pertence a outra pessoa. Escolha outro.");
        }
        
        boolean atualizarSenha = false;
        
        if (novaSenha != null && !novaSenha.trim().isEmpty()) {
            if (!novaSenha.equals(confirmacao)) {
                throw new IllegalArgumentException("As senhas não coincidem! Tente novamente.");
            }
            u.setSenha(novaSenha);
            atualizarSenha = true;
        }
        
        usuarioDAO.atualizarPerfilUsuario(u, atualizarSenha);
    }
}