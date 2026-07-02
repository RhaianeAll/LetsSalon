package salon.dao;

import salon.model.Funcionario;
import salon.util.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {

    // CREATE (Mantido o seu original)
    public void cadastrar(Funcionario func) {
        String sql = "INSERT INTO funcionarios (nome, cpf, telefone, login, senha, cargo, empresa_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, func.getNome());
            stmt.setString(2, func.getCpf());
            stmt.setString(3, func.getTelefone());
            stmt.setString(4, func.getLogin());
            stmt.setString(5, func.getSenha());
            stmt.setString(6, func.getCargo());
            stmt.setInt(7, func.getEmpresaId());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao cadastrar funcionário.", e);
        }
    }

    // READ (Atualizado para trazer todos os campos e filtrar por empresa)
    public List<Funcionario> listarPorEmpresa(int empresaId) {
        List<Funcionario> lista = new ArrayList<>();
        String sql = "SELECT * FROM funcionarios WHERE empresa_id = ? ORDER BY id_funcionario";
        
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, empresaId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Funcionario f = new Funcionario();
                    f.setIdFuncionario(rs.getInt("id_funcionario"));
                    f.setNome(rs.getString("nome"));
                    f.setCpf(rs.getString("cpf"));
                    f.setTelefone(rs.getString("telefone"));
                    f.setLogin(rs.getString("login"));
                    f.setSenha(rs.getString("senha"));
                    f.setCargo(rs.getString("cargo"));
                    f.setEmpresaId(rs.getInt("empresa_id"));
                    lista.add(f);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    // Método para buscar todos os funcionários do banco
    public List<Funcionario> listar() {
        List<Funcionario> lista = new ArrayList<>();
        String sql = "SELECT * FROM funcionarios";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Funcionario f = new Funcionario();
                f.setIdFuncionario(rs.getInt("id_funcionario"));
                f.setNome(rs.getString("nome"));
                f.setCpf(rs.getString("cpf"));
                f.setTelefone(rs.getString("telefone"));
                f.setCargo(rs.getString("cargo"));
                lista.add(f);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // UPDATE
    public void atualizar(Funcionario func) {
        String sql = "UPDATE funcionarios SET nome = ?, cpf = ?, telefone = ?, login = ?, senha = ?, cargo = ? WHERE id_funcionario = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, func.getNome());
            stmt.setString(2, func.getCpf());
            stmt.setString(3, func.getTelefone());
            stmt.setString(4, func.getLogin());
            stmt.setString(5, func.getSenha());
            stmt.setString(6, func.getCargo());
            stmt.setInt(7, func.getIdFuncionario());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar funcionário.", e);
        }
    }

    // DELETE
    public void excluir(int idFuncionario) {
        String sql = "DELETE FROM funcionarios WHERE id_funcionario = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idFuncionario);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao excluir funcionário.", e);
        }
    }
}