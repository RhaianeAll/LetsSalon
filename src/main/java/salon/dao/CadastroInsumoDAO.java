package salon.dao;

import salon.model.CadastroInsumo;
import salon.util.Conexao;
import java.sql.*;

public class CadastroInsumoDAO {

    public void cadastrarRendimento(CadastroInsumo ci) {
        String sql = "INSERT INTO cadastro_insumos (insumo_id, rende_aplicacoes) VALUES (?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, ci.getInsumoId());
            stmt.setInt(2, ci.getRendeAplicacoes());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public CadastroInsumo buscarPorInsumo(int insumoId) {
        String sql = "SELECT * FROM cadastro_insumos WHERE insumo_id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, insumoId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    CadastroInsumo ci = new CadastroInsumo();
                    ci.setId(rs.getInt("id"));
                    ci.setInsumoId(rs.getInt("insumo_id"));
                    ci.setRendeAplicacoes(rs.getInt("rende_aplicacoes"));
                    return ci;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}