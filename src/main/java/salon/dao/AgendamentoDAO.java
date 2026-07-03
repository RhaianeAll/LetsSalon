package salon.dao;

import salon.model.Agendamento;
import salon.util.Conexao;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AgendamentoDAO {

    // --- 1. CRUD BÁSICO RESTAURADO ---
    public void cadastrar(Agendamento a) {
        String sql = "INSERT INTO agendamentos (cliente_id, funcionario_id, servico_id, data_agendamento, hora, status, observacoes, empresa_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, a.getClienteId());
            stmt.setInt(2, a.getFuncionarioId());
            stmt.setInt(3, a.getServicoId());
            stmt.setDate(4, Date.valueOf(a.getDataAgendamento()));
            stmt.setTime(5, Time.valueOf(a.getHora()));
            stmt.setString(6, a.getStatus() != null ? a.getStatus() : "Pendente");
            stmt.setString(7, a.getObservacoes());
            stmt.setInt(8, a.getEmpresaId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao cadastrar agendamento.", e);
        }
    }

    public boolean isHorarioOcupado(int funcionarioId, LocalDate data, LocalTime hora) {
        String sql = "SELECT COUNT(*) FROM agendamentos WHERE funcionario_id = ? AND data_agendamento = ? AND hora = ? AND status != 'Cancelado'";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, funcionarioId);
            stmt.setDate(2, Date.valueOf(data));
            stmt.setTime(3, Time.valueOf(hora));
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public List<Agendamento> listarPendentes() {
        List<Agendamento> lista = new ArrayList<>();
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
                a.setEmpresaId(rs.getInt("empresa_id"));
                if (rs.getDate("data_agendamento") != null) a.setDataAgendamento(rs.getDate("data_agendamento").toLocalDate());
                if (rs.getTime("hora") != null) a.setHora(rs.getTime("hora").toLocalTime());
                a.setStatus(rs.getString("status"));
                a.setNomeCliente(rs.getString("nome_cliente"));
                a.setTelefoneCliente(rs.getString("telefone_cliente"));
                a.setNomeFuncionario(rs.getString("nome_funcionario"));
                a.setNomeServico(rs.getString("nome_servico"));
                lista.add(a);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

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

    public List<Agendamento> listarAgendamentosGerais() {
        List<Agendamento> lista = new ArrayList<>();
        String sql = "SELECT a.*, u.nome_completo AS nome_cliente, u.telefone AS telefone_cliente, " +
                     "f.nome AS nome_funcionario, s.nome AS nome_servico " +
                     "FROM agendamentos a " +
                     "LEFT JOIN usuarios u ON a.cliente_id = u.id_usuario " +
                     "LEFT JOIN funcionarios f ON a.funcionario_id = f.id_funcionario " +
                     "LEFT JOIN servicos s ON a.servico_id = s.id_servico " +
                     "WHERE a.status != 'Pendente' ORDER BY a.data_agendamento ASC, a.hora ASC";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Agendamento a = new Agendamento();
                a.setIdAgendamento(rs.getInt("id_agendamento"));
                a.setClienteId(rs.getInt("cliente_id"));
                a.setFuncionarioId(rs.getInt("funcionario_id"));
                a.setServicoId(rs.getInt("servico_id"));
                a.setEmpresaId(rs.getInt("empresa_id"));
                if (rs.getDate("data_agendamento") != null) a.setDataAgendamento(rs.getDate("data_agendamento").toLocalDate());
                if (rs.getTime("hora") != null) a.setHora(rs.getTime("hora").toLocalTime());
                a.setStatus(rs.getString("status"));
                a.setNomeCliente(rs.getString("nome_cliente"));
                a.setTelefoneCliente(rs.getString("telefone_cliente"));
                a.setNomeFuncionario(rs.getString("nome_funcionario"));
                a.setNomeServico(rs.getString("nome_servico"));
                lista.add(a);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public void atualizar(Agendamento a) {
        String sql = "UPDATE agendamentos SET cliente_id = ?, funcionario_id = ?, servico_id = ?, data_agendamento = ?, hora = ? WHERE id_agendamento = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, a.getClienteId());
            stmt.setInt(2, a.getFuncionarioId());
            stmt.setInt(3, a.getServicoId());
            stmt.setDate(4, Date.valueOf(a.getDataAgendamento()));
            stmt.setTime(5, Time.valueOf(a.getHora()));
            stmt.setInt(6, a.getIdAgendamento());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar agendamento.", e);
        }
    }

    public void excluir(int idAgendamento) {
        String sql = "DELETE FROM agendamentos WHERE id_agendamento = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAgendamento);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao excluir agendamento.", e);
        }
    }

    // --- 2. MÉTODOS MULTI-TENANT (FILTRO POR EMPRESA) ---
    public List<Agendamento> listarPendentesPorEmpresa(String nomeEmpresa) {
        List<Agendamento> lista = new ArrayList<>();
        String sql = "SELECT a.*, u.nome_completo AS nome_cliente, u.telefone AS telefone_cliente, " +
                     "f.nome AS nome_funcionario, s.nome AS nome_servico " +
                     "FROM agendamentos a " +
                     "LEFT JOIN usuarios u ON a.cliente_id = u.id_usuario " +
                     "LEFT JOIN funcionarios f ON a.funcionario_id = f.id_funcionario " +
                     "LEFT JOIN servicos s ON a.servico_id = s.id_servico " +
                     "JOIN empresas e ON a.empresa_id = e.id_empresa " +
                     "WHERE a.status = 'Pendente' AND e.nome_empresa = ? ORDER BY a.data_agendamento, a.hora";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nomeEmpresa);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Agendamento a = new Agendamento();
                    a.setIdAgendamento(rs.getInt("id_agendamento"));
                    a.setClienteId(rs.getInt("cliente_id"));
                    a.setFuncionarioId(rs.getInt("funcionario_id"));
                    a.setServicoId(rs.getInt("servico_id"));
                    a.setEmpresaId(rs.getInt("empresa_id"));
                    if (rs.getDate("data_agendamento") != null) a.setDataAgendamento(rs.getDate("data_agendamento").toLocalDate());
                    if (rs.getTime("hora") != null) a.setHora(rs.getTime("hora").toLocalTime());
                    a.setStatus(rs.getString("status"));
                    a.setNomeCliente(rs.getString("nome_cliente"));
                    a.setTelefoneCliente(rs.getString("telefone_cliente"));
                    a.setNomeFuncionario(rs.getString("nome_funcionario"));
                    a.setNomeServico(rs.getString("nome_servico"));
                    lista.add(a);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public List<Agendamento> listarAgendamentosGeraisPorEmpresa(String nomeEmpresa) {
        List<Agendamento> lista = new ArrayList<>();
        String sql = "SELECT a.*, u.nome_completo AS nome_cliente, u.telefone AS telefone_cliente, " +
                     "f.nome AS nome_funcionario, s.nome AS nome_servico " +
                     "FROM agendamentos a " +
                     "LEFT JOIN usuarios u ON a.cliente_id = u.id_usuario " +
                     "LEFT JOIN funcionarios f ON a.funcionario_id = f.id_funcionario " +
                     "LEFT JOIN servicos s ON a.servico_id = s.id_servico " +
                     "JOIN empresas e ON a.empresa_id = e.id_empresa " +
                     "WHERE a.status != 'Pendente' AND e.nome_empresa = ? ORDER BY a.data_agendamento ASC, a.hora ASC";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nomeEmpresa);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Agendamento a = new Agendamento();
                    a.setIdAgendamento(rs.getInt("id_agendamento"));
                    a.setClienteId(rs.getInt("cliente_id"));
                    a.setFuncionarioId(rs.getInt("funcionario_id"));
                    a.setServicoId(rs.getInt("servico_id"));
                    a.setEmpresaId(rs.getInt("empresa_id"));
                    if (rs.getDate("data_agendamento") != null) a.setDataAgendamento(rs.getDate("data_agendamento").toLocalDate());
                    if (rs.getTime("hora") != null) a.setHora(rs.getTime("hora").toLocalTime());
                    a.setStatus(rs.getString("status"));
                    a.setNomeCliente(rs.getString("nome_cliente"));
                    a.setTelefoneCliente(rs.getString("telefone_cliente"));
                    a.setNomeFuncionario(rs.getString("nome_funcionario"));
                    a.setNomeServico(rs.getString("nome_servico"));
                    lista.add(a);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    // ==========================================
    // 3. NOVOS MÉTODOS PARA O DASHBOARD (NOTIFICAÇÕES)
    // ==========================================
    
    public int contarPendentesPorEmpresa(String nomeEmpresa) {
        String sql = "SELECT COUNT(*) FROM agendamentos a JOIN empresas e ON a.empresa_id = e.id_empresa " +
                     "WHERE a.status = 'Pendente' AND e.nome_empresa = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nomeEmpresa);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    public List<Agendamento> listarConcluidosAntigosPorEmpresa(String nomeEmpresa, LocalDate dataLimite, int limite) {
        List<Agendamento> lista = new ArrayList<>();
        String sql = "SELECT a.*, u.nome_completo AS nome_cliente, s.nome AS nome_servico " +
                     "FROM agendamentos a " +
                     "JOIN usuarios u ON a.cliente_id = u.id_usuario " +
                     "JOIN servicos s ON a.servico_id = s.id_servico " +
                     "JOIN empresas e ON a.empresa_id = e.id_empresa " +
                     "WHERE a.status = 'Concluído' AND e.nome_empresa = ? AND a.data_agendamento <= ? " +
                     "ORDER BY a.data_agendamento DESC, a.hora DESC LIMIT ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nomeEmpresa);
            stmt.setDate(2, Date.valueOf(dataLimite));
            stmt.setInt(3, limite);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Agendamento a = new Agendamento();
                    a.setDataAgendamento(rs.getDate("data_agendamento").toLocalDate());
                    a.setHora(rs.getTime("hora").toLocalTime());
                    a.setNomeCliente(rs.getString("nome_cliente"));
                    a.setNomeServico(rs.getString("nome_servico"));
                    lista.add(a);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }
    
    // ==========================================
    // MÉTODOS EXCLUSIVOS DO CLIENTE
    // ==========================================
    
    public List<Agendamento> listarAgendamentosDoCliente(int clienteId, String nomeEmpresa) {
        List<Agendamento> lista = new ArrayList<>();
        String sql = "SELECT a.*, f.nome AS nome_funcionario, s.nome AS nome_servico " +
                     "FROM agendamentos a " +
                     "JOIN funcionarios f ON a.funcionario_id = f.id_funcionario " +
                     "JOIN servicos s ON a.servico_id = s.id_servico " +
                     "JOIN empresas e ON a.empresa_id = e.id_empresa " +
                     "WHERE a.cliente_id = ? AND e.nome_empresa = ? ORDER BY a.data_agendamento DESC, a.hora DESC";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, clienteId);
            stmt.setString(2, nomeEmpresa);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Agendamento a = new Agendamento();
                    a.setIdAgendamento(rs.getInt("id_agendamento"));
                    a.setDataAgendamento(rs.getDate("data_agendamento").toLocalDate());
                    a.setHora(rs.getTime("hora").toLocalTime());
                    a.setStatus(rs.getString("status"));
                    a.setNomeFuncionario(rs.getString("nome_funcionario"));
                    a.setNomeServico(rs.getString("nome_servico"));
                    lista.add(a);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public int contarPendentesDoCliente(int clienteId, String nomeEmpresa) {
        String sql = "SELECT COUNT(*) FROM agendamentos a JOIN empresas e ON a.empresa_id = e.id_empresa " +
                     "WHERE a.cliente_id = ? AND e.nome_empresa = ? AND a.status = 'Pendente'";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, clienteId);
            stmt.setString(2, nomeEmpresa);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }
    
    public List<Agendamento> listarPendentesDoFuncionario(int idFuncionario) {
        List<Agendamento> lista = new ArrayList<>();
        String sql = "SELECT a.*, u.nome_completo AS nome_cliente, u.telefone AS telefone_cliente, " +
                     "f.nome AS nome_funcionario, s.nome AS nome_servico " +
                     "FROM agendamentos a " +
                     "LEFT JOIN usuarios u ON a.cliente_id = u.id_usuario " +
                     "LEFT JOIN funcionarios f ON a.funcionario_id = f.id_funcionario " +
                     "LEFT JOIN servicos s ON a.servico_id = s.id_servico " +
                     "WHERE a.status = 'Pendente' AND a.funcionario_id = ? ORDER BY a.data_agendamento, a.hora";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idFuncionario);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Agendamento a = new Agendamento();
                    a.setIdAgendamento(rs.getInt("id_agendamento"));
                    a.setClienteId(rs.getInt("cliente_id"));
                    a.setFuncionarioId(rs.getInt("funcionario_id"));
                    a.setServicoId(rs.getInt("servico_id"));
                    if (rs.getDate("data_agendamento") != null) a.setDataAgendamento(rs.getDate("data_agendamento").toLocalDate());
                    if (rs.getTime("hora") != null) a.setHora(rs.getTime("hora").toLocalTime());
                    a.setStatus(rs.getString("status"));
                    a.setNomeCliente(rs.getString("nome_cliente"));
                    a.setTelefoneCliente(rs.getString("telefone_cliente"));
                    a.setNomeFuncionario(rs.getString("nome_funcionario"));
                    a.setNomeServico(rs.getString("nome_servico"));
                    lista.add(a);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public List<Agendamento> listarGeraisDoFuncionario(int idFuncionario) {
        List<Agendamento> lista = new ArrayList<>();
        String sql = "SELECT a.*, u.nome_completo AS nome_cliente, u.telefone AS telefone_cliente, " +
                     "f.nome AS nome_funcionario, s.nome AS nome_servico " +
                     "FROM agendamentos a " +
                     "LEFT JOIN usuarios u ON a.cliente_id = u.id_usuario " +
                     "LEFT JOIN funcionarios f ON a.funcionario_id = f.id_funcionario " +
                     "LEFT JOIN servicos s ON a.servico_id = s.id_servico " +
                     "WHERE a.status != 'Pendente' AND a.funcionario_id = ? ORDER BY a.data_agendamento ASC, a.hora ASC";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idFuncionario);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Agendamento a = new Agendamento();
                    a.setIdAgendamento(rs.getInt("id_agendamento"));
                    a.setClienteId(rs.getInt("cliente_id"));
                    a.setFuncionarioId(rs.getInt("funcionario_id"));
                    a.setServicoId(rs.getInt("servico_id"));
                    if (rs.getDate("data_agendamento") != null) a.setDataAgendamento(rs.getDate("data_agendamento").toLocalDate());
                    if (rs.getTime("hora") != null) a.setHora(rs.getTime("hora").toLocalTime());
                    a.setStatus(rs.getString("status"));
                    a.setNomeCliente(rs.getString("nome_cliente"));
                    a.setTelefoneCliente(rs.getString("telefone_cliente"));
                    a.setNomeFuncionario(rs.getString("nome_funcionario"));
                    a.setNomeServico(rs.getString("nome_servico"));
                    lista.add(a);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }
}