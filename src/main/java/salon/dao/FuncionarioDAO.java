package salon.dao;

import salon.model.Funcionario;
import salon.util.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {

    // --- 1. CRUD BÁSICO RESTAURADO ---
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
                f.setLogin(rs.getString("login"));
                f.setSenha(rs.getString("senha"));
                f.setEmpresaId(rs.getInt("empresa_id"));
                lista.add(f);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

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

    // --- 2. MÉTODOS MULTI-TENANT (FILTRO POR EMPRESA) ---
    public List<Funcionario> listarFuncionariosPorEmpresa(String nomeEmpresa) {
        List<Funcionario> lista = new ArrayList<>();
        String sql = "SELECT f.* FROM funcionarios f JOIN empresas e ON f.empresa_id = e.id_empresa WHERE e.nome_empresa = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nomeEmpresa);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Funcionario f = new Funcionario();
                    f.setIdFuncionario(rs.getInt("id_funcionario"));
                    f.setNome(rs.getString("nome"));
                    f.setCpf(rs.getString("cpf"));
                    f.setTelefone(rs.getString("telefone"));
                    f.setCargo(rs.getString("cargo"));
                    f.setLogin(rs.getString("login"));
                    f.setSenha(rs.getString("senha"));
                    f.setEmpresaId(rs.getInt("empresa_id"));
                    lista.add(f);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }
}