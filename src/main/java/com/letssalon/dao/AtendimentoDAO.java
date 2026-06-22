package com.letssalon.dao;

import com.letssalon.model.Atendimento;
import com.letssalon.util.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class AtendimentoDAO {

    public boolean cadastrar(Atendimento atendimento) {
        String sql = "INSERT INTO atendimento (id_colaborador, codigo_servico, cpf_cliente, data_hora, valor_arrecadado, codigo_insumo, qtd_aplicacoes, custo_insumo, codigo_empresa) VALUES (?,?,?,?,?,?,?,?,?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, atendimento.getIdColaborador());
            stmt.setInt(2, atendimento.getCodigoServico());
            stmt.setString(3, atendimento.getCpfCliente());
            DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern(
            "dd/MM/yyyy HH:mm"
             );

            LocalDateTime dataHora =
            LocalDateTime.parse(
            atendimento.getDataHora(),
            formatter
    );

            stmt.setTimestamp(
            4,
            Timestamp.valueOf(dataHora)
);
            stmt.setDouble(5, atendimento.getValorArrecadado());
            stmt.setInt(6, atendimento.getCodigoInsumo());
            stmt.setInt(7, atendimento.getQtdAplicacoes());
            stmt.setDouble(8, atendimento.getCustoInsumo());
            stmt.setInt(9, atendimento.getCodigoEmpresa());
            int linhas = stmt.executeUpdate();
            return linhas > 0;
            } catch (SQLException e) {
            System.out.println(
                    "Erro ao registrar atendimento: " 
                            + e.getMessage()
            );
            return false;
        }
    }

    public List<Atendimento> listar() {
        List<Atendimento> lista = new ArrayList<>();
        String sql = "SELECT * FROM atendimento";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Atendimento a = new Atendimento();
                a.setIdAtendimento(rs.getInt("id_atendimento"));
                a.setIdColaborador(rs.getInt("id_colaborador"));
                a.setCodigoServico(rs.getInt("codigo_servico"));
                a.setCpfCliente(rs.getString("cpf_cliente"));
                a.setDataHora(rs.getString("data_hora"));
                a.setValorArrecadado(rs.getDouble("valor_arrecadado"));
                a.setCodigoInsumo(rs.getInt("codigo_insumo"));
                a.setQtdAplicacoes(rs.getInt("qtd_aplicacoes"));
                a.setCustoInsumo(rs.getDouble("custo_insumo"));
                a.setCodigoEmpresa(rs.getInt("codigo_empresa"));
                lista.add(a);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar atendimentos: " + e.getMessage());
        }
        return lista;
    }

    // Busca atendimentos de mais de 15 dias atrás para o dashboard
    public List<Atendimento> listarMaisde15Dias() {
        List<Atendimento> lista = new ArrayList<>();
        String sql = "SELECT * FROM atendimento WHERE data_hora < NOW() - INTERVAL '15 days'";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Atendimento a = new Atendimento();
                a.setIdAtendimento(rs.getInt("id_atendimento"));
                a.setIdColaborador(rs.getInt("id_colaborador"));
                a.setCodigoServico(rs.getInt("codigo_servico"));
                a.setCpfCliente(rs.getString("cpf_cliente"));
                a.setDataHora(rs.getString("data_hora"));
                a.setValorArrecadado(rs.getDouble("valor_arrecadado"));
                lista.add(a);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar atendimentos: " + e.getMessage());
        }
        return lista;
    }

    // Calcula valor líquido = valor bruto - custo insumos
    public double calcularValorLiquido(int codigoEmpresa) {
        String sql = "SELECT SUM(valor_arrecadado) - SUM(custo_insumo) FROM atendimento WHERE codigo_empresa=?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, codigoEmpresa);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getDouble(1);
        } catch (SQLException e) {
            System.out.println("Erro ao calcular valor líquido: " + e.getMessage());
        }
        return 0;
    }
}