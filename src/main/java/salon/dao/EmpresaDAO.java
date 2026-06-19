package salon.dao;

import salon.model.Empresa;
import salon.util.Conexao;
import java.sql.*;

public class EmpresaDAO {
    public void cadastrar(Empresa e) {
        String sql = "INSERT INTO empresas (nome_empresa, telefone, email) VALUES (?, ?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, e.getNomeEmpresa());
            stmt.setString(2, e.getTelefone());
            stmt.setString(3, e.getEmail());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}