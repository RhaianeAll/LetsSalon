package salon.dao;

import salon.model.Usuario;
import salon.util.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public void cadastrar(Usuario u) {
        String sql = "INSERT INTO usuarios (nome_completo, cpf, telefone, usuario, senha, tipo_usuario) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, u.getNomeCompleto());
            stmt.setString(2, u.getCpf());
            stmt.setString(3, u.getTelefone());
            stmt.setString(4, u.getUsuario());
            stmt.setString(5, u.getSenha());
            stmt.setString(6, u.getTipoUsuario());
            stmt.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException("Erro ao cadastrar usuário.", e); }
    }

    public List<Usuario> listarTodos() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios ORDER BY id_usuario";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("id_usuario"));
                u.setNomeCompleto(rs.getString("nome_completo"));
                u.setUsuario(rs.getString("usuario"));
                u.setTipoUsuario(rs.getString("tipo_usuario"));
                lista.add(u);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public Usuario autenticar(String login, String senha) {
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
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

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
                    return u; 
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public void atualizarCodigoEmpresa(String login, String codigo) {
        String sql = "UPDATE usuarios SET codigo_empresa = ? WHERE usuario = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codigo);
            stmt.setString(2, login);
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public Usuario buscarUsuarioPorLogin(String login) {
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
        } catch (SQLException e) { System.err.println("Erro: " + e.getMessage()); }
        return null;
    }

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
        } catch (SQLException e) { throw new RuntimeException("Erro ao atualizar BD: " + e.getMessage()); }
    }

    public boolean verificarUsuarioExistente(String nomeUsuario, int idIgnorado) {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE usuario = ? AND id_usuario != ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nomeUsuario.trim());
            stmt.setInt(2, idIgnorado);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean verificarTelefoneExistente(String telefone, int idIgnorado) {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE telefone = ? AND id_usuario != ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, telefone.trim());
            stmt.setInt(2, idIgnorado);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    // ==========================================
    // MÉTODOS DE RECUPERAÇÃO DE SENHA
    // ==========================================

    public boolean verificarEmailExistente(String email) {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE email = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email.trim());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public void atualizarSenhaPorEmail(String email, String novaSenha) {
        String sql = "UPDATE usuarios SET senha = ? WHERE email = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, novaSenha);
            stmt.setString(2, email.trim());
            stmt.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException("Erro ao redefinir senha.", e); }
    }
}