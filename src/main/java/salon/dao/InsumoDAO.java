package salon.dao;

import salon.model.Insumo;
import salon.util.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InsumoDAO {

    // --- 1. CRUD BÁSICO RESTAURADO ---
    public void cadastrar(Insumo insumo) {
        String sql = "INSERT INTO insumos (nome, valor, quantidade, capacidade, empresa_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, insumo.getNome());
            stmt.setDouble(2, insumo.getValor());
            stmt.setInt(3, insumo.getQuantidade());
            stmt.setString(4, insumo.getCapacidade());
            stmt.setInt(5, insumo.getEmpresaId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao cadastrar insumo.", e);
        }
    }

    public int cadastrarRetornandoId(Insumo insumo) {
        String sql = "INSERT INTO insumos (nome, valor, quantidade, capacidade, empresa_id) VALUES (?, ?, ?, ?, ?) RETURNING id_insumo";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, insumo.getNome());
            stmt.setDouble(2, insumo.getValor());
            stmt.setInt(3, insumo.getQuantidade());
            stmt.setString(4, insumo.getCapacidade());
            stmt.setInt(5, insumo.getEmpresaId());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_insumo");
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
                i.setEmpresaId(rs.getInt("empresa_id"));
                lista.add(i);
            }
        } catch (SQLException e) { e.printStackTrace(); }
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

    // --- 2. MÉTODOS MULTI-TENANT (FILTRO POR EMPRESA) ---
    public List<Insumo> listarEstoquePorEmpresa(String nomeEmpresa) {
        List<Insumo> lista = new ArrayList<>();
        String sql = "SELECT i.* FROM insumos i JOIN empresas e ON i.empresa_id = e.id_empresa WHERE e.nome_empresa = ? ORDER BY i.id_insumo";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nomeEmpresa);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Insumo i = new Insumo();
                    i.setIdInsumo(rs.getInt("id_insumo"));
                    i.setNome(rs.getString("nome"));
                    i.setValor(rs.getDouble("valor"));
                    i.setQuantidade(rs.getInt("quantidade"));
                    i.setCapacidade(rs.getString("capacidade"));
                    i.setEmpresaId(rs.getInt("empresa_id"));
                    lista.add(i);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }
}