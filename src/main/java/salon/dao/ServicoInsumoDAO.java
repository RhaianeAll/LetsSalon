package salon.dao;

import salon.model.ServicoInsumo;
import salon.util.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicoInsumoDAO {

    public void vincularInsumoAServico(ServicoInsumo si) {
        String sql = "INSERT INTO servico_insumo (servico_id, insumo_id, quantidade_necessaria) VALUES (?, ?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, si.getServicoId());
            stmt.setInt(2, si.getInsumoId());
            stmt.setInt(3, si.getQuantidadeNecessaria());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ServicoInsumo> listarInsumosPorServico(int idServico) {
        List<ServicoInsumo> lista = new ArrayList<>();
        String sql = "SELECT * FROM servico_insumo WHERE servico_id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idServico);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ServicoInsumo si = new ServicoInsumo();
                    si.setId(rs.getInt("id"));
                    si.setServicoId(rs.getInt("servico_id"));
                    si.setInsumoId(rs.getInt("insumo_id"));
                    si.setQuantidadeNecessaria(rs.getInt("quantidade_necessaria"));
                    lista.add(si);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}