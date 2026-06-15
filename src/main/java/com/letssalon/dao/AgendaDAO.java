// AgendaDAO.java
package com.letssalon.dao;

import com.letssalon.model.Agenda;
import com.letssalon.util.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AgendaDAO {

    public void cadastrar(Agenda agenda) {
        String sql = "INSERT INTO agenda (cpf_cliente, data, horario, id_colaborador, codigo_servico, status, codigo_empresa) VALUES (?,?,?,?,?,?,?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, agenda.getCpfCliente());
            stmt.setString(2, agenda.getData());
            stmt.setString(3, agenda.getHorario());
            stmt.setInt(4, agenda.getIdColaborador());
            stmt.setInt(5, agenda.getCodigoServico());
            stmt.setString(6, agenda.getStatus());
            stmt.setInt(7, agenda.getCodigoEmpresa());
            stmt.executeUpdate();
            System.out.println("Agendamento cadastrado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar agendamento: " + e.getMessage());
        }
    }

    public List<Agenda> listar() {
        List<Agenda> lista = new ArrayList<>();
        String sql = "SELECT * FROM agenda";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Agenda a = new Agenda();
                a.setIdAgendamento(rs.getInt("id_agendamento"));
                a.setCpfCliente(rs.getString("cpf_cliente"));
                a.setData(rs.getString("data"));
                a.setHorario(rs.getString("horario"));
                a.setIdColaborador(rs.getInt("id_colaborador"));
                a.setCodigoServico(rs.getInt("codigo_servico"));
                a.setStatus(rs.getString("status"));
                a.setCodigoEmpresa(rs.getInt("codigo_empresa"));
                lista.add(a);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar agendamentos: " + e.getMessage());
        }
        return lista;
    }

    public void confirmar(int idAgendamento) {
        String sql = "UPDATE agenda SET status='CONFIRMADO' WHERE id_agendamento=?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAgendamento);
            stmt.executeUpdate();
            System.out.println("Agendamento confirmado!");
        } catch (SQLException e) {
            System.out.println("Erro ao confirmar agendamento: " + e.getMessage());
        }
    }

    public void excluir(int idAgendamento) {

    String sql =
        "DELETE FROM agenda WHERE id_agendamento=?";

    try (
        Connection conn = Conexao.conectar();
        PreparedStatement stmt =
                conn.prepareStatement(sql)
    ) {

        stmt.setInt(1, idAgendamento);

        stmt.executeUpdate();

    } catch (SQLException e) {

        System.out.println(
            "Erro ao excluir: " + e.getMessage()
        );
    }
}
    
    public void cancelar(int idAgendamento) {

        String sql =
            "UPDATE agenda " +
            "SET status = 'CANCELADO' " +
            "WHERE id_agendamento = ?";

        try (
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)
    ) {

            stmt.setInt(1, idAgendamento);

            stmt.executeUpdate();

            System.out.println(
                "Agendamento cancelado com sucesso!"
        );

    }   catch (SQLException e) {

            System.out.println(
                "Erro ao cancelar agendamento: "
                + e.getMessage()
        );
    }
}

    public boolean verificarConflitoHorario(
        String data,
        String horario,
        int idColaborador
) {

        String sql =
            "SELECT * FROM agenda " +
            "WHERE data = ? " +
            "AND horario = ? " +
            "AND id_colaborador = ? " +
            "AND status != 'CANCELADO'";

        try (
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)
    ) {

            stmt.setString(1, data);
            stmt.setString(2, horario);
            stmt.setInt(3, idColaborador);

            ResultSet rs = stmt.executeQuery();

            return rs.next();

    }   catch (SQLException e) {

            System.out.println(
                "Erro ao verificar conflito: " +
                e.getMessage()
            );

            return false;
    }
}
    public void finalizar(int idAgendamento) {

    String sql =
        "UPDATE agenda " +
        "SET status = 'FINALIZADO' " +
        "WHERE id_agendamento = ?";

    try (
        Connection conn = Conexao.conectar();
        PreparedStatement stmt =
                conn.prepareStatement(sql)
    ) {

        stmt.setInt(1, idAgendamento);

        stmt.executeUpdate();

        System.out.println(
            "Agendamento finalizado!"
        );

    } catch (SQLException e) {

        System.out.println(
            "Erro ao finalizar agendamento: "
            + e.getMessage()
        );
    }
}
}
    
