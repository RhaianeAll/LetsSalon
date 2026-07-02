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

    public Usuario autenticar(String login, String senha) {
        String sql = "SELECT * FROM usuarios WHERE usuario = ? AND senha = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, login);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("id_usuario"));
                u.setNomeCompleto(rs.getString("nome_completo"));
                u.setTipoUsuario(rs.getString("tipo_usuario"));
                // Preencher demais campos se necessário para a sessão
                return u;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Login falhou
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

    // Atualiza os dados do Administrador
    public void atualizarAdmin(Usuario u, boolean atualizarSenha) {
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
            throw new RuntimeException("Erro ao atualizar dados: " + e.getMessage()); 
        }
    }
}