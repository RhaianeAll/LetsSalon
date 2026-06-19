package salon.dao;

import salon.model.Insumo;
import salon.util.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InsumoDAO {

    public void cadastrar(Insumo insumo) {
        String sql = "INSERT INTO insumos (nome, valor, quantidade, capacidade) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, insumo.getNome());
            stmt.setDouble(2, insumo.getValor());
            stmt.setInt(3, insumo.getQuantidade());
            stmt.setString(4, insumo.getCapacidade());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Insumo> listarEstoque() {
        List<Insumo> lista = new ArrayList<>();
        String sql = "SELECT * FROM insumos";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Insumo i = new Insumo();
                i.setIdInsumo(rs.getInt("id_insumo"));
                i.setNome(rs.getString("nome"));
                i.setQuantidade(rs.getInt("quantidade"));
                i.setCapacidade(rs.getString("capacidade"));
                lista.add(i);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    public void atualizarQuantidade(int idInsumo, int novaQuantidade) {
        String sql = "UPDATE insumos SET quantidade = ? WHERE id_insumo = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, novaQuantidade);
            stmt.setInt(2, idInsumo);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}