package salon.dao;

import salon.model.Agendamento;
import salon.util.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AgendamentoDAO {

    public void cadastrar(Agendamento a) {
        String sql = "INSERT INTO agendamentos (cliente_id, funcionario_id, servico_id, data, hora, status, observacoes) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, a.getClienteId());
            stmt.setInt(2, a.getFuncionarioId());
            stmt.setInt(3, a.getServicoId());
            stmt.setDate(4, Date.valueOf(a.getData()));
            stmt.setTime(5, Time.valueOf(a.getHora()));
            stmt.setString(6, a.getStatus());
            stmt.setString(7, a.getObservacoes());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarStatus(int idAgendamento, String novoStatus) {
        String sql = "UPDATE agendamentos SET status = ? WHERE id_agendamento = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, novoStatus); // Ex: 'Confirmado', 'Finalizado'
            stmt.setInt(2, idAgendamento);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Agendamento> listarPorStatus(String status) {
        List<Agendamento> lista = new ArrayList<>();
        String sql = "SELECT * FROM agendamentos WHERE status = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, status);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Agendamento a = new Agendamento();
                    a.setIdAgendamento(rs.getInt("id_agendamento"));
                    a.setClienteId(rs.getInt("cliente_id"));
                    a.setFuncionarioId(rs.getInt("funcionario_id"));
                    a.setServicoId(rs.getInt("servico_id"));
                    a.setData(rs.getDate("data").toLocalDate());
                    a.setHora(rs.getTime("hora").toLocalTime());
                    a.setStatus(rs.getString("status"));
                    lista.add(a);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}