package salon.dao;

import salon.util.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RelatorioDAO {

    // Retorna uma lista de Arrays de Objetos. Cada Array é uma linha da tabela!
    public List<Object[]> gerarRegistroAtendimentos() {
        List<Object[]> linhas = new ArrayList<>();
        
        String sql = "SELECT a.id_agendamento, u.nome_completo AS cliente, f.nome AS funcionario, " +
                     "s.nome AS servico, a.data_agendamento, a.hora, a.status, s.valor, s.duracao " +
                     "FROM agendamentos a " +
                     "JOIN usuarios u ON a.cliente_id = u.id_usuario " +
                     "JOIN funcionarios f ON a.funcionario_id = f.id_funcionario " +
                     "JOIN servicos s ON a.servico_id = s.id_servico " +
                     "ORDER BY a.data_agendamento DESC, a.hora DESC";
                     
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                // Formata a data para padrão brasileiro
                String data = rs.getDate("data_agendamento").toLocalDate().toString();
                String hora = rs.getTime("hora").toLocalTime().toString();
                String dataHora = data.substring(8,10) + "/" + data.substring(5,7) + "/" + data.substring(0,4) + " " + hora;
                
                // Formata o valor para moeda
                String valor = String.format("R$ %.2f", rs.getDouble("valor"));
                
                // Monta a linha exata como vai aparecer na tabela
                Object[] linha = {
                    rs.getInt("id_agendamento"),
                    rs.getString("cliente"),
                    rs.getString("funcionario"),
                    rs.getString("servico"),
                    dataHora,
                    rs.getString("status"),
                    valor,
                    rs.getString("duracao")
                };
                linhas.add(linha);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return linhas;
    }
    
    // Adicione no RelatorioDAO.java
    public List<Object[]> gerarRelatorioFinanceiro() {
        List<Object[]> linhas = new ArrayList<>();
        
        // Vai buscar apenas os agendamentos 'Concluídos' para garantir que o dinheiro realmente entrou
        String sql = "SELECT a.data_agendamento, a.hora, s.nome AS servico, f.nome AS funcionario, " +
                     "u.nome_completo AS cliente, s.valor AS receita " +
                     "FROM agendamentos a " +
                     "JOIN servicos s ON a.servico_id = s.id_servico " +
                     "JOIN funcionarios f ON a.funcionario_id = f.id_funcionario " +
                     "JOIN usuarios u ON a.cliente_id = u.id_usuario " +
                     "WHERE a.status = 'Concluído' " +
                     "ORDER BY a.data_agendamento DESC, a.hora DESC";
                     
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                String data = rs.getDate("data_agendamento").toLocalDate().toString();
                String dataFormatada = data.substring(8,10) + "/" + data.substring(5,7) + "/" + data.substring(0,4);
                
                double receita = rs.getDouble("receita");
                // Como ainda não temos valores exatos de compras de insumos, o sistema estima 15% de custo
                double custoEstimado = receita * 0.15; 
                double lucroLiquido = receita - custoEstimado;
                
                Object[] linha = {
                    dataFormatada,
                    "Entrada",
                    "Atendimento: " + rs.getString("cliente"),
                    "Serviço",
                    rs.getString("funcionario"),
                    rs.getString("servico"),
                    String.format("R$ %.2f", custoEstimado),
                    String.format("R$ %.2f", receita),
                    String.format("R$ %.2f", lucroLiquido)
                };
                linhas.add(linha);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return linhas;
    }
    
    // 1. DADOS PARA O RELATÓRIO GERAL
    public List<Object[]> gerarRelatorioGeral() {
        List<Object[]> linhas = new ArrayList<>();
        
        String sql = "SELECT f.id_funcionario, f.nome AS funcionario, " +
                     "COUNT(a.id_agendamento) AS qtd_servicos, " +
                     "COUNT(DISTINCT a.cliente_id) AS clientes_atendidos, " +
                     "COALESCE(SUM(s.valor), 0) AS valor_bruto, " +
                     "(SELECT s2.nome FROM agendamentos a2 JOIN servicos s2 ON a2.servico_id = s2.id_servico WHERE a2.funcionario_id = f.id_funcionario AND a2.status = 'Concluído' GROUP BY s2.nome ORDER BY COUNT(a2.id_agendamento) DESC LIMIT 1) AS servico_mais_realizado " +
                     "FROM funcionarios f " +
                     "LEFT JOIN agendamentos a ON f.id_funcionario = a.funcionario_id AND a.status = 'Concluído' " +
                     "LEFT JOIN servicos s ON a.servico_id = s.id_servico " +
                     "GROUP BY f.id_funcionario, f.nome ORDER BY f.nome";
                     
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                double valorBruto = rs.getDouble("valor_bruto");
                double valorLiquido = valorBruto * 0.85; // Custo estimado de 15%
                int qtdServicos = rs.getInt("qtd_servicos");
                
                // Estimativa de insumos utilizados (3 itens por serviço em média)
                String totalInsumos = (qtdServicos * 3) + " unidades";
                String servicoTop = rs.getString("servico_mais_realizado");
                
                Object[] linha = {
                    rs.getInt("id_funcionario"),
                    rs.getString("funcionario"),
                    qtdServicos,
                    servicoTop != null ? servicoTop : "Nenhum",
                    rs.getInt("clientes_atendidos"),
                    totalInsumos,
                    String.format("R$ %.2f", valorBruto),
                    String.format("R$ %.2f", valorLiquido)
                };
                linhas.add(linha);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return linhas;
    }

    // 2. DADOS PARA O RELATÓRIO PARCIAL
    public List<Object[]> gerarRelatorioParcial() {
        List<Object[]> linhas = new ArrayList<>();
        
        String sql = "SELECT f.id_funcionario, f.nome AS funcionario, s.nome AS servico, " +
                     "COUNT(a.id_agendamento) AS qtd_agendamentos, " +
                     "SUM(CASE WHEN a.status = 'Cancelado' THEN 1 ELSE 0 END) AS qtd_cancelamentos, " +
                     "COALESCE(SUM(CASE WHEN a.status = 'Concluído' THEN s.valor ELSE 0 END), 0) AS valor_recebido, " +
                     "(SELECT a2.hora FROM agendamentos a2 WHERE a2.funcionario_id = f.id_funcionario AND a2.servico_id = s.id_servico GROUP BY a2.hora ORDER BY COUNT(a2.id_agendamento) DESC LIMIT 1) AS horario, " +
                     "(SELECT u.nome_completo FROM agendamentos a3 JOIN usuarios u ON a3.cliente_id = u.id_usuario WHERE a3.funcionario_id = f.id_funcionario AND a3.servico_id = s.id_servico GROUP BY u.nome_completo ORDER BY COUNT(a3.id_agendamento) DESC LIMIT 1) AS cliente_frequente " +
                     "FROM funcionarios f " +
                     "JOIN agendamentos a ON f.id_funcionario = a.funcionario_id " +
                     "JOIN servicos s ON a.servico_id = s.id_servico " +
                     "GROUP BY f.id_funcionario, f.nome, s.id_servico, s.nome ORDER BY f.nome, s.nome";
                     
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                String horarioMove = rs.getString("horario");
                String clienteFreq = rs.getString("cliente_frequente");
                
                Object[] linha = {
                    rs.getInt("id_funcionario"),
                    rs.getString("funcionario"),
                    rs.getString("servico"),
                    rs.getInt("qtd_agendamentos"),
                    rs.getInt("qtd_cancelamentos"),
                    String.format("R$ %.2f", rs.getDouble("valor_recebido")),
                    horarioMove != null ? horarioMove : "N/A",
                    clienteFreq != null ? clienteFreq : "N/A"
                };
                linhas.add(linha);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return linhas;
    }
}