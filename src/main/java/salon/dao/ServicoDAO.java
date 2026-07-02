package salon.dao;

import salon.model.Servico;
import salon.util.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicoDAO {

    // Retorna o ID gerado pelo banco para usarmos nos insumos!
    public int cadastrarRetornandoId(Servico servico) {
        String sql = "INSERT INTO servicos (nome, valor, duracao, empresa_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, servico.getNome());
            stmt.setDouble(2, servico.getValor());
            stmt.setString(3, servico.getDuracao());
            stmt.setInt(4, servico.getEmpresaId());
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // Captura o ID
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao cadastrar serviço.", e);
        }
        return -1;
    }

    public List<Servico> listarPorEmpresa(int empresaId) {
        List<Servico> lista = new ArrayList<>();
        String sql = "SELECT * FROM servicos WHERE empresa_id = ? ORDER BY id_servico";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setInt(1, empresaId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Servico s = new Servico();
                    s.setIdServico(rs.getInt("id_servico"));
                    s.setNome(rs.getString("nome"));
                    s.setValor(rs.getDouble("valor"));
                    s.setDuracao(rs.getString("duracao"));
                    s.setEmpresaId(rs.getInt("empresa_id"));
                    lista.add(s);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    // Método para buscar todos os serviços do banco
    public List<Servico> listar() {
        List<Servico> lista = new ArrayList<>();
        String sql = "SELECT * FROM servicos";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Servico s = new Servico();
                s.setIdServico(rs.getInt("id_servico"));
                s.setNome(rs.getString("nome"));
                s.setValor(rs.getDouble("valor"));
                s.setDuracao(rs.getString("duracao"));
                lista.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void atualizar(Servico servico) {
        String sql = "UPDATE servicos SET nome = ?, valor = ?, duracao = ? WHERE id_servico = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, servico.getNome());
            stmt.setDouble(2, servico.getValor());
            stmt.setString(3, servico.getDuracao());
            stmt.setInt(4, servico.getIdServico());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar serviço.", e);
        }
    }

    public void excluir(int idServico) {
        String sql = "DELETE FROM servicos WHERE id_servico = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idServico);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao excluir serviço.", e);
        }
    }
}