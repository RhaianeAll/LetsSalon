package salon.dao;

import salon.model.Empresa;
import salon.util.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpresaDAO {

    // ==========================================
    // 1. CRUD BÁSICO DE EMPRESAS
    // ==========================================

    public void cadastrar(Empresa empresa) {
        String sql = "INSERT INTO empresas (nome_empresa, telefone, email) VALUES (?, ?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setString(1, empresa.getNomeEmpresa());
            stmt.setString(2, empresa.getTelefoneEmpresa());
            stmt.setString(3, empresa.getEmailEmpresa());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao cadastrar empresa.", e);
        }
    }

    public List<Empresa> listarTodas() {
        List<Empresa> lista = new ArrayList<>();
        String sql = "SELECT * FROM empresas ORDER BY id_empresa";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
             
            while (rs.next()) {
                Empresa e = new Empresa();
                e.setIdEmpresa(rs.getInt("id_empresa"));
                e.setNomeEmpresa(rs.getString("nome_empresa"));
                e.setTelefoneEmpresa(rs.getString("telefone"));
                e.setEmailEmpresa(rs.getString("email"));
                lista.add(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void atualizar(Empresa empresa) {
        String sql = "UPDATE empresas SET nome_empresa = ?, telefone = ?, email = ? WHERE id_empresa = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setString(1, empresa.getNomeEmpresa());
            stmt.setString(2, empresa.getTelefoneEmpresa());
            stmt.setString(3, empresa.getEmailEmpresa());
            stmt.setInt(4, empresa.getIdEmpresa());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar empresa.", e);
        }
    }

    public void excluir(int idEmpresa) {
        String sql = "DELETE FROM empresas WHERE id_empresa = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setInt(1, idEmpresa);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao excluir empresa.", e);
        }
    }

    // ==========================================
    // 2. MÉTODO PARA A ARQUITETURA MULTI-TENANT
    // ==========================================
    
    // Converte o nome da empresa que vem da tela (View) no ID real dela no banco de dados
    public int buscarIdPorNome(String nomeEmpresa) {
        String sql = "SELECT id_empresa FROM empresas WHERE nome_empresa = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setString(1, nomeEmpresa);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_empresa");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Retorna -1 se der erro ou a empresa não for encontrada
    }
    
    public String buscarNomePorId(int idEmpresa) {
        String sql = "SELECT nome_empresa FROM empresas WHERE id_empresa = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idEmpresa);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return rs.getString("nome_empresa");
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return "Studio";
    }
}