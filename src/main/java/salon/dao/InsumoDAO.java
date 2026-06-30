package salon.dao;

import salon.model.Insumo;
import salon.util.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InsumoDAO {

    // NOVA FUNÇÃO: Cadastra e devolve o ID!
    // NOVA FUNÇÃO: Cadastra e devolve o ID usando a forma mais segura do PostgreSQL!
    public int cadastrarRetornandoId(Insumo insumo) {
        // Usamos o 'RETURNING id_insumo' direto no código SQL
        String sql = "INSERT INTO insumos (nome, valor, quantidade, capacidade) VALUES (?, ?, ?, ?) RETURNING id_insumo";
        
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, insumo.getNome());
            stmt.setDouble(2, insumo.getValor());
            stmt.setInt(3, insumo.getQuantidade());
            stmt.setString(4, insumo.getCapacidade());
            
            // Alteramos de executeUpdate() para executeQuery() para conseguir ler o ID que voltou
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_insumo"); // Retorna o ID perfeitamente
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao cadastrar insumo.", e);
        }
        return -1;
    }

    public List<Insumo> listarEstoque() {
        List<Insumo> lista = new ArrayList<>();
        String sql = "SELECT * FROM insumos ORDER BY id_insumo";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Insumo i = new Insumo();
                i.setIdInsumo(rs.getInt("id_insumo"));
                i.setNome(rs.getString("nome"));
                i.setValor(rs.getDouble("valor"));
                i.setQuantidade(rs.getInt("quantidade"));
                i.setCapacidade(rs.getString("capacidade"));
                lista.add(i);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    public void atualizar(Insumo insumo) {
        String sql = "UPDATE insumos SET nome = ?, valor = ?, quantidade = ?, capacidade = ? WHERE id_insumo = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, insumo.getNome());
            stmt.setDouble(2, insumo.getValor());
            stmt.setInt(3, insumo.getQuantidade());
            stmt.setString(4, insumo.getCapacidade());
            stmt.setInt(5, insumo.getIdInsumo());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar insumo.", e);
        }
    }

    public void excluir(int idInsumo) {
        String sql = "DELETE FROM insumos WHERE id_insumo = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idInsumo);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao excluir insumo.", e);
        }
    }
}