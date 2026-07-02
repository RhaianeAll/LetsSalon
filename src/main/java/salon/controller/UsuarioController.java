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
    
    // Método para cadastrar um novo colaborador no sistema
    public String cadastrarColaborador(String nomeCompleto, String cpf, String telefone, String nomeUsuario, String senha, String confirmacaoSenha) {
        
        // 1. Validação de campos vazios
        if (nomeCompleto.isEmpty() || cpf.isEmpty() || telefone.isEmpty() || nomeUsuario.isEmpty() || senha.isEmpty()) {
            throw new IllegalArgumentException("Por favor, preencha todos os campos obrigatórios.");
        }
        
        // 2. Validação de senhas
        if (!senha.equals(confirmacaoSenha)) {
            throw new IllegalArgumentException("As senhas não coincidem. Digite novamente.");
        }

        // 3. TRAVA DE SEGURANÇA: Verifica se o nome de usuário já existe no banco
        // Passamos '0' como ID ignorado, pois é um cadastro novo e ele ainda não tem ID
        if (usuarioDAO.verificarUsuarioExistente(nomeUsuario, 0)) {
            throw new IllegalArgumentException("Este nome de usuário já está em uso. Por favor, escolha outro.");
        }

        // 4. Monta o objeto do novo usuário
        Usuario u = new Usuario();
        u.setNomeCompleto(nomeCompleto);
        u.setCpf(cpf);
        u.setTelefone(telefone);
        u.setUsuario(nomeUsuario); 
        u.setSenha(senha);
        
        // A MÁGICA: Define o nível de permissão desta pessoa no sistema
        u.setTipoUsuario("colaborador"); 
        
        // 5. Salva no banco de dados usando o DAO
        // (Assume-se que você já possui o método 'cadastrar(u)' no seu UsuarioDAO que usamos para os clientes)
        usuarioDAO.cadastrar(u);
        
        // 6. Geração do Código da Empresa (4 dígitos automáticos)
        // Isso simula o código único que o colaborador vai usar para o estúdio dele
        int numeroGerado = (int) (Math.random() * 9000) + 1000; 
        String codigoEmpresa = String.valueOf(numeroGerado);
        
        // Retorna o código gerado para a tela exibir no aviso amarelo!
        return codigoEmpresa; 
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