// AgendaDAO.java
package com.letssalon.dao;

import com.letssalon.model.Agenda;
import com.letssalon.util.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.LocalTime;

public class AgendaDAO {

    public boolean cadastrar(Agenda agenda) {

    String sql =
        "INSERT INTO agenda " +
        "(cpf_cliente, data, horario, id_colaborador, codigo_servico, status, codigo_empresa) " +
        "VALUES (?,?,?,?,?,?,?)";

    try (
        Connection conn = Conexao.conectar();
        PreparedStatement stmt = conn.prepareStatement(sql)
    ) {

        DateTimeFormatter formatterData =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate data =
            LocalDate.parse(
                agenda.getData(),
                formatterData
            );

        LocalTime horario =
            LocalTime.parse(
                agenda.getHorario()
            );

        stmt.setString(1, agenda.getCpfCliente());
        stmt.setDate(2, java.sql.Date.valueOf(data));
        stmt.setTime(3, java.sql.Time.valueOf(horario));
        stmt.setInt(4, agenda.getIdColaborador());
        stmt.setInt(5, agenda.getCodigoServico());
        stmt.setString(6, agenda.getStatus());
        stmt.setInt(7, agenda.getCodigoEmpresa());

        int linhas = stmt.executeUpdate();

        return linhas > 0;

    } catch (Exception e) {

        System.out.println(
            "Erro ao cadastrar agendamento: "
            + e.getMessage()
        );

        return false;
    }
}

    public List<Agenda> listar() {
        List<Agenda> lista = new ArrayList<>();
        String sql = "SELECT * FROM agenda " +
                     "WHERE status = ('CONFIRMADO')";
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
    
    public List<Object[]> listarComNomes() {

    List<Object[]> lista = new ArrayList<>();

    String sql =
        "SELECT " +
        "a.id_agendamento, " +
        "a.data, " +
        "a.horario, " +
        "c.nome_cliente, " +
        "col.nome_colaborador, " +
        "s.nome_servico " +
        "FROM agenda a " +
        "INNER JOIN cliente c " +
        "ON a.cpf_cliente = c.cpf_cliente " +
        "INNER JOIN colaborador col " +
        "ON a.id_colaborador = col.id_colaborador " +
        "INNER JOIN servico s " +
        "ON a.codigo_servico = s.codigo_servico " +
        "WHERE a.status NOT IN ('CANCELADO', 'FINALIZADO')";

    try (
        Connection conn = Conexao.conectar();
        PreparedStatement stmt =
                conn.prepareStatement(sql);
        ResultSet rs =
                stmt.executeQuery()
    ) {
    DateTimeFormatter formatoData =
        DateTimeFormatter.ofPattern("dd/MM/yyyy");

    DateTimeFormatter formatoHora =
        DateTimeFormatter.ofPattern("HH:mm");

        while (rs.next()) {
    String dataFormatada =
        rs.getDate("data")
          .toLocalDate()
          .format(formatoData);

    String horaFormatada =
        rs.getTime("horario")
          .toLocalTime()
          .format(formatoHora);
            lista.add(new Object[]{
                rs.getInt("id_agendamento"),
                dataFormatada,
                horaFormatada,
                rs.getString("nome_cliente"),
                rs.getString("nome_colaborador"),
                rs.getString("nome_servico")
            });
        }

    } catch (SQLException e) {

        System.out.println(
            "Erro ao listar agendamentos: "
            + e.getMessage()
        );
    }

    return lista;
}

    public boolean confirmar(int idAgendamento) {
        String sql = "UPDATE agenda SET status='CONFIRMADO' WHERE id_agendamento=?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAgendamento);
            int linhas = stmt.executeUpdate();
            return linhas > 0;
        } catch (SQLException e) {
            System.out.println(
                    "Erro ao confirmar agendamento: " + e.getMessage()
            ); 
            return false;
        }
    }

    public boolean excluir(int idAgendamento) {

    String sql =
        "DELETE FROM agenda WHERE id_agendamento=?";

    try (
        Connection conn = Conexao.conectar();
        PreparedStatement stmt =
                conn.prepareStatement(sql)
    ) {

        stmt.setInt(1, idAgendamento);

        int linhas = stmt.executeUpdate();

        return linhas > 0;

    } catch(SQLException e){

        System.out.println(
            "Erro ao excluir: " + e.getMessage()
        );

        return false;
    }
}
    
    public boolean cancelar(int idAgendamento) {

        String sql =
            "UPDATE agenda " +
            "SET status = 'CANCELADO' " +
            "WHERE id_agendamento = ?";

        try (
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)
    ) {

            stmt.setInt(1, idAgendamento);

            int linhas = stmt.executeUpdate();

        return linhas > 0;

    } catch(SQLException e){

        System.out.println(
            "Erro ao cancelar agendamento: " + e.getMessage()
        );

        return false;
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

            DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate dataAgenda =
            LocalDate.parse(data, formatter);

        LocalTime horaAgenda =
            LocalTime.parse(horario);

        stmt.setDate(
            1,
            java.sql.Date.valueOf(dataAgenda)
        );

        stmt.setTime(
            2,
            java.sql.Time.valueOf(horaAgenda)
        );

        stmt.setInt(3, idColaborador);

        ResultSet rs = stmt.executeQuery();
        
        

        return rs.next();

    } catch (Exception e) {

        System.out.println(
            "Erro ao verificar conflito: "
            + e.getMessage()
        );

        return false;
    }
}
    public boolean finalizar(int idAgendamento) {

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

        int linhas = stmt.executeUpdate();

        return linhas > 0;

    } catch(SQLException e){

        System.out.println(
            "Erro ao finalizar agendamento: " + e.getMessage()
        );

        return false;
    }
}
    public List<Object[]> listarPendentesComNomes() {

    List<Object[]> lista = new ArrayList<>();

    String sql =
        "SELECT " +
        "a.id_agendamento, " +
        "c.nome_cliente, " +
        "c.telefone_cliente, " +
        "a.data, " +
        "a.horario, " +
        "col.nome_colaborador, " +
        "s.nome_servico " +
        "FROM agenda a " +
        "INNER JOIN cliente c " +
        "ON a.cpf_cliente = c.cpf_cliente " +
        "INNER JOIN colaborador col " +
        "ON a.id_colaborador = col.id_colaborador " +
        "INNER JOIN servico s " +
        "ON a.codigo_servico = s.codigo_servico " +
        "WHERE a.status = 'PENDENTE'";
    
    try (
        Connection conn = Conexao.conectar();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()
    ) {
        
    DateTimeFormatter formatoData =
        DateTimeFormatter.ofPattern("dd/MM/yyyy");

    DateTimeFormatter formatoHora =
        DateTimeFormatter.ofPattern("HH:mm");
    
        while (rs.next()) {

    String dataHora =
        rs.getDate("data")
          .toLocalDate()
          .format(formatoData)
        + " "
        +
        rs.getTime("horario")
          .toLocalTime()
          .format(formatoHora);

    lista.add(new Object[]{
        rs.getInt("id_agendamento"),
        rs.getString("nome_cliente"),
        rs.getString("telefone_cliente"),
        dataHora,
        rs.getString("nome_colaborador"),
        rs.getString("nome_servico")
    });
        }
    }   catch (SQLException e) {

        System.out.println(
            "Erro ao listar pendentes: "
            + e.getMessage()
              );
    }
        return lista;
    }
}
   