package salon.controller;

import salon.dao.UsuarioDAO;
import salon.model.Usuario;
import java.util.List;

public class UsuarioController {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public void cadastrarCliente(String nomeCompleto, String cpf, String telefone, String nomeUsuario, String senha, String confirmacaoSenha) {
        if (nomeCompleto.isEmpty() || cpf.isEmpty() || telefone.isEmpty() || nomeUsuario.isEmpty() || senha.isEmpty()) {
            throw new IllegalArgumentException("Por favor, preencha todos os campos obrigatórios.");
        }
        if (!senha.equals(confirmacaoSenha)) { throw new IllegalArgumentException("As senhas não coincidem. Digite novamente."); }
        if (usuarioDAO.verificarUsuarioExistente(nomeUsuario, 0)) { throw new IllegalArgumentException("Este nome de usuário já está em uso."); }
        if (usuarioDAO.verificarTelefoneExistente(telefone, 0)) { throw new IllegalArgumentException("Já existe um usuário com este telefone."); }

        Usuario u = new Usuario(); u.setNomeCompleto(nomeCompleto); u.setCpf(cpf); u.setTelefone(telefone); u.setUsuario(nomeUsuario); u.setSenha(senha); u.setTipoUsuario("cliente"); 
        usuarioDAO.cadastrar(u);
    }

    public String cadastrarColaborador(String nomeCompleto, String cpf, String telefone, String nomeUsuario, String senha, String confirmacaoSenha) {
        if (nomeCompleto.isEmpty() || cpf.isEmpty() || telefone.isEmpty() || nomeUsuario.isEmpty() || senha.isEmpty()) {
            throw new IllegalArgumentException("Por favor, preencha todos os campos obrigatórios.");
        }
        if (!senha.equals(confirmacaoSenha)) { throw new IllegalArgumentException("As senhas não coincidem."); }
        if (usuarioDAO.verificarUsuarioExistente(nomeUsuario, 0)) { throw new IllegalArgumentException("Este nome de usuário já está em uso."); }
        if (usuarioDAO.verificarTelefoneExistente(telefone, 0)) { throw new IllegalArgumentException("Já existe um usuário com este telefone."); }

        Usuario u = new Usuario(); u.setNomeCompleto(nomeCompleto); u.setCpf(cpf); u.setTelefone(telefone); u.setUsuario(nomeUsuario); u.setSenha(senha); u.setTipoUsuario("colaborador"); 
        usuarioDAO.cadastrar(u); 
        
        int numeroGerado = (int) (Math.random() * 9000) + 1000; 
        String codigoEmpresa = String.valueOf(numeroGerado);
        usuarioDAO.atualizarCodigoEmpresa(nomeUsuario, codigoEmpresa);
        return codigoEmpresa; 
    }

    public Usuario fazerLoginCliente(String login, String senha) {
        if (login.isEmpty() || senha.isEmpty()) throw new IllegalArgumentException("Por favor, preencha o usuário e a senha.");
        Usuario u = usuarioDAO.autenticar(login, senha);
        if (u == null) throw new IllegalArgumentException("Usuário ou senha incorretos!");
        if (!u.getTipoUsuario().equals("cliente")) throw new IllegalArgumentException("Acesso negado. Esta área é restrita a clientes.");
        return u;
    }

    public Usuario fazerLoginColaborador(String login, String senha, String codigoEmpresa) {
        if (login.isEmpty() || senha.isEmpty() || codigoEmpresa.isEmpty()) throw new IllegalArgumentException("Por favor, preencha usuário, senha e código.");
        Usuario u = usuarioDAO.autenticarColaborador(login, senha, codigoEmpresa);
        if (u == null) throw new IllegalArgumentException("Usuário, Senha ou Código incorretos!");
        return u;
    }

    public Usuario buscarUsuarioPorLogin(String login) {
        return usuarioDAO.buscarUsuarioPorLogin(login);
    }

    public void atualizarPerfil(Usuario u, String novaSenha, String confirmacao) {
        if (usuarioDAO.verificarUsuarioExistente(u.getUsuario(), u.getIdUsuario())) throw new IllegalArgumentException("Este usuário já pertence a outra pessoa.");
        if (usuarioDAO.verificarTelefoneExistente(u.getTelefone(), u.getIdUsuario())) throw new IllegalArgumentException("Telefone vinculado a outra conta.");
        
        boolean atualizarSenha = false;
        if (novaSenha != null && !novaSenha.trim().isEmpty()) {
            if (!novaSenha.equals(confirmacao)) throw new IllegalArgumentException("As senhas não coincidem!");
            u.setSenha(novaSenha); atualizarSenha = true;
        }
        usuarioDAO.atualizarPerfilUsuario(u, atualizarSenha);
    }

    public List<Usuario> listarUsuarios() { return usuarioDAO.listarTodos(); }

    // ==========================================
    // NOVA FUNÇÃO: GERAR E ENVIAR SENHA
    // ==========================================
    public void recuperarSenha(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Por favor, digite o seu endereço de e-mail.");
        }
        if (!usuarioDAO.verificarEmailExistente(email)) {
            throw new IllegalArgumentException("Este e-mail não foi encontrado. Você já adicionou um e-mail ao seu perfil?");
        }

        // 1. Gera uma senha aleatória de 6 caracteres
        String caracteres = "ABCDEFGHJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@";
        StringBuilder senhaProvisoria = new StringBuilder();
        java.util.Random rnd = new java.util.Random();
        for (int i = 0; i < 6; i++) {
            senhaProvisoria.append(caracteres.charAt(rnd.nextInt(caracteres.length())));
        }

        // 2. Tenta enviar o E-mail PRIMEIRO (Para não mudar a senha no banco se a net falhar)
        try {
            salon.util.EmailUtil.enviarEmailRecuperacao(email, senhaProvisoria.toString());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Falha ao enviar e-mail. Verifique a internet e as configurações do remetente.");
        }

        // 3. Se enviou com sucesso, atualiza no Banco de Dados
        usuarioDAO.atualizarSenhaPorEmail(email, senhaProvisoria.toString());
    }
}