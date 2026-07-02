package salon.dao;

import salon.model.Agendamento;
import salon.util.Conexao;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AgendamentoDAO {

    // 1. CADASTRAR
    public void cadastrar(Agendamento a) {
        String sql = "INSERT INTO agendamentos (cliente_id, funcionario_id, servico_id, data_agendamento, hora, status, observacoes) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, a.getClienteId());
            stmt.setInt(2, a.getFuncionarioId());
            stmt.setInt(3, a.getServicoId());
            stmt.setDate(4, Date.valueOf(a.getDataAgendamento()));
            stmt.setTime(5, Time.valueOf(a.getHora()));
            stmt.setString(6, a.getStatus() != null ? a.getStatus() : "Pendente");
            stmt.setString(7, a.getObservacoes());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao cadastrar agendamento.", e);
        }
    }

    // 2. VERIFICAR SE O HORÁRIO ESTÁ OCUPADO (Regra de Negócio Crucial)
    public boolean isHorarioOcupado(int funcionarioId, LocalDate data, LocalTime hora) {
        String sql = "SELECT COUNT(*) FROM agendamentos WHERE funcionario_id = ? AND data_agendamento = ? AND hora = ? AND status != 'Cancelado'";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, funcionarioId);
            stmt.setDate(2, Date.valueOf(data));
            stmt.setTime(3, Time.valueOf(hora));
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Retorna true se já existir agendamento
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 3. LISTAR PENDENTES (Fazendo JOIN para buscar os nomes reais para a sua Interface)
    public List<Agendamento> listarPendentes() {
        List<Agendamento> lista = new ArrayList<>();
        // Ajustado para usar u.nome_completo conforme sua tabela de usuários
        String sql = "SELECT a.*, u.nome_completo AS nome_cliente, u.telefone AS telefone_cliente, " +
                     "f.nome AS nome_funcionario, s.nome AS nome_servico " +
                     "FROM agendamentos a " +
                     "LEFT JOIN usuarios u ON a.cliente_id = u.id_usuario " +
                     "LEFT JOIN funcionarios f ON a.funcionario_id = f.id_funcionario " +
                     "LEFT JOIN servicos s ON a.servico_id = s.id_servico " +
                     "WHERE a.status = 'Pendente' ORDER BY a.data_agendamento, a.hora";
                     
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Agendamento a = new Agendamento();
                a.setIdAgendamento(rs.getInt("id_agendamento"));
                a.setClienteId(rs.getInt("cliente_id"));
                a.setFuncionarioId(rs.getInt("funcionario_id"));
                a.setServicoId(rs.getInt("servico_id"));
                
                // Conversão segura de data/hora
                if (rs.getDate("data_agendamento") != null) {
                    a.setDataAgendamento(rs.getDate("data_agendamento").toLocalDate());
                }
                if (rs.getTime("hora") != null) {
                    a.setHora(rs.getTime("hora").toLocalTime());
                }
                
                a.setStatus(rs.getString("status"));
                
                // Preenchendo os dados auxiliares que serão exibidos na tela
                a.setNomeCliente(rs.getString("nome_cliente"));
                a.setTelefoneCliente(rs.getString("telefone_cliente"));
                a.setNomeFuncionario(rs.getString("nome_funcionario"));
                a.setNomeServico(rs.getString("nome_servico"));
                
                lista.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao listar agendamentos pendentes.", e);
        }
        return lista;
    }

    // 4. ATUALIZAR STATUS (Para os botões de "Confirmar" da tela)
    public void atualizarStatus(int idAgendamento, String novoStatus) {
        String sql = "UPDATE agendamentos SET status = ? WHERE id_agendamento = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, novoStatus);
            stmt.setInt(2, idAgendamento);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar status do agendamento.", e);
        }
    }
}