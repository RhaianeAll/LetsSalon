package salon.dao;

import salon.model.Empresa;
import salon.util.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpresaDAO {
    
    // CREATE (Já estava a funcionar perfeitamente!)
    public void cadastrar(Empresa e) {
        String sql = "INSERT INTO empresas (nome_empresa, telefone, email) VALUES (?, ?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, e.getNomeEmpresa());
            stmt.setString(2, e.getTelefoneEmpresa());
            stmt.setString(3, e.getEmailEmpresa());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Erro ao cadastrar empresa no banco de dados.", ex);
        }
    }
    
    // READ (Listar para a tabela - CORRIGIDO PARA id_empresa)
    public List<Empresa> listarTodas() {
        String sql = "SELECT * FROM empresas ORDER BY id_empresa"; // Alterado aqui
        List<Empresa> empresas = new ArrayList<>();

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Empresa e = new Empresa();
                e.setIdEmpresa(rs.getInt("id_empresa")); // Alterado aqui
                e.setNomeEmpresa(rs.getString("nome_empresa"));
                e.setTelefoneEmpresa(rs.getString("telefone"));
                e.setEmailEmpresa(rs.getString("email"));
                
                empresas.add(e);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("Erro ao listar empresas no DAO: " + ex.getMessage());
        }
        return empresas;
    }

    // UPDATE (CORRIGIDO PARA id_empresa)
    public void atualizar(Empresa e) {
        String sql = "UPDATE empresas SET nome_empresa = ?, telefone = ?, email = ? WHERE id_empresa = ?"; // Alterado aqui
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, e.getNomeEmpresa());
            stmt.setString(2, e.getTelefoneEmpresa());
            stmt.setString(3, e.getEmailEmpresa());
            stmt.setInt(4, e.getIdEmpresa());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Erro ao atualizar empresa no banco de dados.", ex);
        }
    }

    // DELETE (CORRIGIDO PARA id_empresa)
    public void excluir(int idEmpresa) {
        String sql = "DELETE FROM empresas WHERE id_empresa = ?"; // Alterado aqui
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idEmpresa);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Erro ao excluir empresa no banco de dados.", ex);
        }
    }
}