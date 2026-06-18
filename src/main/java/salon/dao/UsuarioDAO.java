package salon.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import salon.util.Conexao;
import salon.model.Usuario;

public class UsuarioDAO {
    
    public boolean autenticar(String login, String senha) {
        String sql = "SELECT id_usuario FROM usuarios WHERE usuario = ? AND senha = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, login);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();
            
            return rs.next();
        } catch (Exception e) {
            System.err.println("Erro na autenticação: " + e.getMessage());
            return false;
        }
    }
}