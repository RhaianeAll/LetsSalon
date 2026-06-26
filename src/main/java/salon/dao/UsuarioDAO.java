package salon.dao;

import salon.model.Usuario;
import salon.util.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public void cadastrar(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nome_completo, cpf, telefone, usuario, senha, foto, tipo_usuario) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, usuario.getNomeCompleto());
            stmt.setString(2, usuario.getCpf());
            stmt.setString(3, usuario.getTelefone());
            stmt.setString(4, usuario.getUsuarioLogin());
            stmt.setString(5, usuario.getSenha());
            //stmt.setBytes(6, usuario.getFoto()); // Upload da imagem em bytes
            stmt.setString(7, usuario.getTipoUsuario());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Recomenda-se tratar ou usar JOptionPane na View
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
}