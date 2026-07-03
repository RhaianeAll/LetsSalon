package salon.dao;

import salon.model.Usuario;
import salon.util.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    // Método para CADASTRAR um novo usuário/cliente no banco
    public void cadastrar(Usuario u) {
        String sql = "INSERT INTO usuarios (nome_completo, cpf, telefone, usuario, senha, tipo_usuario) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, u.getNomeCompleto());
            stmt.setString(2, u.getCpf());
            stmt.setString(3, u.getTelefone());
            stmt.setString(4, u.getUsuario());
            stmt.setString(5, u.getSenha());
            stmt.setString(6, u.getTipoUsuario()); // Será definido como 'cliente' no Controller
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Lança o erro real para a tela em vez de esconder!
            throw new RuntimeException("Erro no banco: " + e.getMessage(), e); 
        }
    }

    // Método de Autenticação Unificado e Seguro (Diferencia Maiúsculas de Minúsculas)
    public Usuario autenticar(String login, String senha) {
        // Removido o LOWER para forçar a busca exata (RhaianeAll != rhaianeAll)
        String sql = "SELECT * FROM usuarios WHERE usuario = ? AND senha = ?";
        
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setString(1, login.trim());
            stmt.setString(2, senha.trim());
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuario u = new Usuario();
                    u.setIdUsuario(rs.getInt("id_usuario"));
                    u.setNomeCompleto(rs.getString("nome_completo"));
                    u.setUsuario(rs.getString("usuario"));
                    u.setTipoUsuario(rs.getString("tipo_usuario")); 
                    return u;
                }
            }
        } catch (SQLException e) { 
            e.printStackTrace(); 
        }
        return null;
    }

    public List<Usuario> listarClientes() {
        List<Usuario> clientes = new ArrayList<>();
        String sql = "SELECT * FROM usuarios WHERE tipo_usuario = 'cliente'";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("id_usuario"));
                u.setNomeCompleto(rs.getString("nome_completo"));
                u.setCpf(rs.getString("cpf"));
                u.setTelefone(rs.getString("telefone"));
                clientes.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }
    
    public List<Usuario> listarTodos() {
        List<Usuario> lista = new ArrayList<>();
        // Trazemos todos os utilizadores ordenados por nome para a caixa de seleção ficar organizada
        String sql = "SELECT * FROM usuarios ORDER BY nome_completo";
        
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("id_usuario"));
                u.setNomeCompleto(rs.getString("nome_completo"));
                u.setCpf(rs.getString("cpf"));
                u.setTelefone(rs.getString("telefone"));
                u.setUsuario(rs.getString("usuario"));
                u.setTipoUsuario(rs.getString("tipo_usuario"));
                lista.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    // Busca o usuário logado pelo "login" (nome de usuário)
    public Usuario buscarPorUsuario(String login) {
        String sql = "SELECT * FROM usuarios WHERE usuario = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, login);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuario u = new Usuario();
                    u.setIdUsuario(rs.getInt("id_usuario"));
                    u.setNomeCompleto(rs.getString("nome_completo"));
                    u.setTelefone(rs.getString("telefone"));
                    u.setEmail(rs.getString("email"));
                    u.setCargo(rs.getString("cargo"));
                    u.setUsuario(rs.getString("usuario"));
                    u.setSenha(rs.getString("senha"));
                    u.setFoto(rs.getBytes("foto")); // Puxa a imagem do banco
                    return u;
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }
    
    

    // Busca EXCLUSIVA para o Administrador ou Colaborador
    public Usuario buscarAdmin(String login) {
        // A mágica da separação: aceita quem for 'administrador' OU 'colaborador'
        String sql = "SELECT * FROM usuarios WHERE usuario = ? AND tipo_usuario IN ('administrador', 'colaborador')";
        
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setString(1, login);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuario u = new Usuario();
                    u.setIdUsuario(rs.getInt("id_usuario"));
                    u.setNomeCompleto(rs.getString("nome_completo"));
                    u.setTelefone(rs.getString("telefone"));
                    u.setUsuario(rs.getString("usuario"));
                    u.setSenha(rs.getString("senha"));
                    
                    // Mantém o tipo exato que veio do banco (colaborador ou administrador)
                    u.setTipoUsuario(rs.getString("tipo_usuario")); 
                    
                    // Proteção extra para colunas opcionais
                    try { u.setFoto(rs.getBytes("foto")); } catch (Exception ignore) {}
                    try { u.setEmail(rs.getString("email")); } catch (Exception ignore) {}
                    try { u.setCargo(rs.getString("cargo")); } catch (Exception ignore) {}
                    
                    return u;
                }
            }
        } catch (SQLException e) { 
            System.err.println("Erro ao buscar Admin: " + e.getMessage()); 
        }
        return null;
    }

    // Atualiza APENAS administradores e colaboradores
    public void atualizarAdmin(Usuario u, boolean atualizarSenha) {
        String sql = atualizarSenha 
            ? "UPDATE usuarios SET nome_completo=?, telefone=?, email=?, cargo=?, usuario=?, senha=?, foto=? WHERE id_usuario=? AND tipo_usuario IN ('administrador', 'colaborador')"
            : "UPDATE usuarios SET nome_completo=?, telefone=?, email=?, cargo=?, usuario=?, foto=? WHERE id_usuario=? AND tipo_usuario IN ('administrador', 'colaborador')";
            
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, u.getNomeCompleto());
            stmt.setString(2, u.getTelefone());
            stmt.setString(3, u.getEmail());
            stmt.setString(4, u.getCargo());
            stmt.setString(5, u.getUsuario());
            
            if (atualizarSenha) {
                stmt.setString(6, u.getSenha());
                stmt.setBytes(7, u.getFoto());
                stmt.setInt(8, u.getIdUsuario());
            } else {
                stmt.setBytes(6, u.getFoto());
                stmt.setInt(7, u.getIdUsuario());
            }
            stmt.executeUpdate();
        } catch (SQLException e) { 
            throw new RuntimeException("Erro ao atualizar perfil: " + e.getMessage()); 
        }
    }
    
    // Verifica se o nome de usuário já existe no banco.
    public boolean verificarUsuarioExistente(String nomeUsuario, int idIgnorado) {
        // Removido o LOWER para permitir a criação de "Admin" e "admin" como contas diferentes
        String sql = "SELECT COUNT(*) FROM usuarios WHERE usuario = ? AND id_usuario != ?";
        
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setString(1, nomeUsuario.trim());
            stmt.setInt(2, idIgnorado);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int quantidade = rs.getInt(1);
                    return quantidade > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Busca os dados do usuário para preencher a tela de edição
    public Usuario buscarUsuarioPorLogin(String login) {
        // Removido o LOWER para buscar exatamente como foi digitado
        String sql = "SELECT * FROM usuarios WHERE usuario = ?";
        
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setString(1, login.trim());
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuario u = new Usuario();
                    u.setIdUsuario(rs.getInt("id_usuario"));
                    u.setNomeCompleto(rs.getString("nome_completo"));
                    u.setTelefone(rs.getString("telefone"));
                    u.setUsuario(rs.getString("usuario"));
                    u.setTipoUsuario(rs.getString("tipo_usuario"));
                    
                    try { u.setFoto(rs.getBytes("foto")); } catch (Exception ignore) {}
                    try { u.setEmail(rs.getString("email")); } catch (Exception ignore) {}
                    try { u.setCargo(rs.getString("cargo")); } catch (Exception ignore) {}
                    
                    return u;
                }
            }
        } catch (SQLException e) { 
            System.err.println("Erro ao buscar usuário: " + e.getMessage()); 
        }
        return null;
    }

    // 2. Salva as alterações feitas na tela
    public void atualizarPerfilUsuario(Usuario u, boolean atualizarSenha) {
        String sql = atualizarSenha 
            ? "UPDATE usuarios SET nome_completo=?, telefone=?, email=?, cargo=?, usuario=?, senha=?, foto=? WHERE id_usuario=?"
            : "UPDATE usuarios SET nome_completo=?, telefone=?, email=?, cargo=?, usuario=?, foto=? WHERE id_usuario=?";
            
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setString(1, u.getNomeCompleto());
            stmt.setString(2, u.getTelefone());
            stmt.setString(3, u.getEmail());
            stmt.setString(4, u.getCargo());
            stmt.setString(5, u.getUsuario());
            
            if (atualizarSenha) {
                stmt.setString(6, u.getSenha());
                stmt.setBytes(7, u.getFoto());
                stmt.setInt(8, u.getIdUsuario());
            } else {
                stmt.setBytes(6, u.getFoto());
                stmt.setInt(7, u.getIdUsuario());
            }
            
            stmt.executeUpdate();
            
        } catch (SQLException e) { 
            throw new RuntimeException("Erro ao atualizar o banco de dados: " + e.getMessage()); 
        }
    }
    
    // Grava o código de 4 dígitos vinculado ao usuário
    public void atualizarCodigoEmpresa(String login, String codigo) {
        String sql = "UPDATE usuarios SET codigo_empresa = ? WHERE usuario = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codigo);
            stmt.setString(2, login);
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    // Login super rigoroso: Exige Usuário + Senha + Código da Empresa!
    public Usuario autenticarColaborador(String login, String senha, String codigo) {
        String sql = "SELECT * FROM usuarios WHERE usuario = ? AND senha = ? AND codigo_empresa = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setString(1, login.trim());
            stmt.setString(2, senha.trim());
            stmt.setString(3, codigo.trim());
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuario u = new Usuario();
                    u.setIdUsuario(rs.getInt("id_usuario"));
                    u.setNomeCompleto(rs.getString("nome_completo"));
                    u.setUsuario(rs.getString("usuario"));
                    u.setTipoUsuario(rs.getString("tipo_usuario"));
                    return u; // Só deixa entrar se as 3 coisas estiverem corretas!
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }
    
}