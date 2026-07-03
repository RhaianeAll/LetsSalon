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
    public boolean atualizar(Agenda agenda) {

    String sql =
        "UPDATE agenda SET " +
        "cpf_cliente = ?, " +
        "data = ?, " +
        "horario = ?, " +
        "id_colaborador = ?, " +
        "codigo_servico = ? " +
        "WHERE id_agendamento = ?";

    try (
        Connection conn = Conexao.conectar();
        PreparedStatement stmt = conn.prepareStatement(sql)
    ) {

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate data =
                LocalDate.parse(
                        agenda.getData(),
                        formatter
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
        stmt.setInt(6, agenda.getIdAgendamento());

        return stmt.executeUpdate() > 0;

    } catch (Exception e) {

        System.out.println(
                "Erro ao atualizar agendamento: "
                + e.getMessage()
        );

        return false;
    }
}

    public List<Agenda> listar() {
        List<Agenda> lista = new ArrayList<>();
       String sql =
    "SELECT * FROM agenda " +
    "WHERE status <> 'CANCELADO' " +
    "AND status <> 'FINALIZADO'";
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
    
    public List<Object[]> listarComNomes(int codigoEmpresa) {

    List<Object[]> lista = new ArrayList<>();

    String sql =
    "SELECT " +
    "a.id_agendamento, " +
    "a.cpf_cliente, " +
    "a.id_colaborador, " +
    "a.codigo_servico, " +
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
        "WHERE a.status NOT IN ('CANCELADO', 'FINALIZADO') " +
        "AND a.codigo_empresa = ? ";

    try (
    Connection conn = Conexao.conectar();
    PreparedStatement stmt =
            conn.prepareStatement(sql)
) {

    stmt.setInt(1, codigoEmpresa);

    ResultSet rs = stmt.executeQuery();
   
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
    rs.getString("cpf_cliente"),
    rs.getInt("id_colaborador"),
    rs.getInt("codigo_servico"),
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

    try (
        Connection conn = Conexao.conectar();
        PreparedStatement stmt = conn.prepareStatement(sql)
    ) {

        stmt.setInt(1, idAgendamento);

        int linhas = stmt.executeUpdate();

        return linhas > 0;

    } catch (SQLException e) {

        System.out.println(
            "Erro ao confirmar agendamento: "
            + e.getMessage()
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
    
        public boolean verificarConflitoHorarioAtualizacao(
        int idAgendamento,
        String data,
        String horario,
        int idColaborador
) {

    String sql =
        "SELECT * FROM agenda " +
        "WHERE data = ? " +
        "AND horario = ? " +
        "AND id_colaborador = ? " +
        "AND id_agendamento <> ? " +
        "AND status <> 'CANCELADO'";

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
                java.sql.Date.valueOf(dataAgenda));

        stmt.setTime(
                2,
                java.sql.Time.valueOf(horaAgenda));

        stmt.setInt(3, idColaborador);

        stmt.setInt(4, idAgendamento);

        ResultSet rs = stmt.executeQuery();

        return rs.next();

    } catch (Exception e) {

        System.out.println(
                "Erro ao verificar conflito: "
                + e.getMessage());

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
    public List<Object[]> listarPendentesComNomes(int codigoEmpresa) {

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
        "WHERE a.status = 'PENDENTE' " +
        "AND a.codigo_empresa = ? ";
    
    try (
    Connection conn = Conexao.conectar();
    PreparedStatement stmt = conn.prepareStatement(sql)
) {

    stmt.setInt(1, codigoEmpresa);

    ResultSet rs = stmt.executeQuery();
        
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
    public List<Object[]> buscarAgendamentos(
        String busca,
        int codigoEmpresa
) {

    List<Object[]> lista = new ArrayList<>();

    String sql =
        "SELECT " +
        "a.id_agendamento, " +
"a.cpf_cliente, " +
"a.id_colaborador, " +
"a.codigo_servico, " +
"a.data, " +
"a.horario, " +
"c.nome_cliente, " +
        "col.nome_colaborador, " +
        "s.nome_servico " +
        "FROM agenda a " +
        "INNER JOIN cliente c ON a.cpf_cliente = c.cpf_cliente " +
        "INNER JOIN colaborador col ON a.id_colaborador = col.id_colaborador " +
        "INNER JOIN servico s ON a.codigo_servico = s.codigo_servico " +
        "WHERE a.codigo_empresa = ? " +
        "AND (" +
        "LOWER(c.nome_cliente) LIKE LOWER(?) " +
        "OR LOWER(col.nome_colaborador) LIKE LOWER(?) " +
        "OR LOWER(s.nome_servico) LIKE LOWER(?)" +
        ")";

    try (
        Connection conn = Conexao.conectar();
        PreparedStatement stmt =
                conn.prepareStatement(sql)
    ) {

        stmt.setInt(1, codigoEmpresa);

        String filtro = "%" + busca + "%";

        stmt.setString(2, filtro);
        stmt.setString(3, filtro);
        stmt.setString(4, filtro);

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {

            DateTimeFormatter formatoData =
        DateTimeFormatter.ofPattern("dd/MM/yyyy");

DateTimeFormatter formatoHora =
        DateTimeFormatter.ofPattern("HH:mm");

String data =
        rs.getDate("data")
          .toLocalDate()
          .format(formatoData);

String hora =
        rs.getTime("horario")
          .toLocalTime()
          .format(formatoHora);

lista.add(new Object[]{
    rs.getInt("id_agendamento"),
    rs.getString("cpf_cliente"),
    rs.getInt("id_colaborador"),
    rs.getInt("codigo_servico"),
    data,
    hora,
    rs.getString("nome_cliente"),
    rs.getString("nome_colaborador"),
    rs.getString("nome_servico")
});
        }

    } catch (SQLException e) {

        System.out.println(
            "Erro na busca de agendamentos: "
            + e.getMessage()
        );
    }

    return lista;
}
    // Lista os agendamentos do cliente com nomes
public List<Object[]> listarPorCliente(String cpfCliente) {

    List<Object[]> lista = new ArrayList<>();

    String sql =
        "SELECT " +
        "a.id_agendamento, " +
        "a.data, " +
        "a.horario, " +
        "col.nome_colaborador, " +
        "s.nome_servico, " +
        "a.status, " +
        "e.nome_empresa " +
        "FROM agenda a " +
        "INNER JOIN colaborador col " +
        "ON a.id_colaborador = col.id_colaborador " +
        "INNER JOIN servico s " +
        "ON a.codigo_servico = s.codigo_servico " +
        "INNER JOIN empresa e " +
        "ON a.codigo_empresa = e.codigo_empresa " +
        "WHERE a.cpf_cliente = ? " +
        "ORDER BY a.data DESC, a.horario DESC";

    try (
        Connection conn = Conexao.conectar();
        PreparedStatement stmt = conn.prepareStatement(sql)
    ) {

        stmt.setString(1, cpfCliente);

        ResultSet rs = stmt.executeQuery();

        DateTimeFormatter formatoData =
                DateTimeFormatter.ofPattern("dd/MM/yyyy");

        DateTimeFormatter formatoHora =
                DateTimeFormatter.ofPattern("HH:mm");

        while (rs.next()) {

            String data =
                    rs.getDate("data")
                      .toLocalDate()
                      .format(formatoData);

            String hora =
                    rs.getTime("horario")
                      .toLocalTime()
                      .format(formatoHora);

            lista.add(new Object[]{
                    rs.getInt("id_agendamento"),
                    data,
                    hora,
                    rs.getString("nome_colaborador"),
                    rs.getString("nome_servico"),
                    rs.getString("status"),
                    rs.getString("nome_empresa")
            });
        }

    } catch (SQLException e) {

        System.out.println(
                "Erro ao listar agendamentos do cliente: "
                + e.getMessage()
        );
    }

    return lista;
}
public List<Object[]> listarFinalizadosAntigos(int codigoEmpresa) {

    List<Object[]> lista = new ArrayList<>();

    String sql =
        "SELECT " +
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
        "WHERE a.codigo_empresa = ? " +
        "AND a.status = 'FINALIZADO' " +
        "ORDER BY a.data";

    try (
        Connection conn = Conexao.conectar();
        PreparedStatement stmt = conn.prepareStatement(sql)
    ) {

        stmt.setInt(1, codigoEmpresa);

        ResultSet rs = stmt.executeQuery();

        DateTimeFormatter formatoData =
                DateTimeFormatter.ofPattern("dd/MM/yyyy");

        DateTimeFormatter formatoHora =
                DateTimeFormatter.ofPattern("HH:mm");

        while (rs.next()) {

            LocalDate data =
                    rs.getDate("data").toLocalDate();

            long dias =
                    java.time.temporal.ChronoUnit.DAYS
                            .between(data, LocalDate.now());

            if (dias >= 15) {

                lista.add(new Object[]{

                    rs.getString("nome_cliente"),

                    rs.getString("telefone_cliente"),

                    data.format(formatoData)
                    + " "
                    + rs.getTime("horario")
                          .toLocalTime()
                          .format(formatoHora),

                    rs.getString("nome_colaborador"),

                    rs.getString("nome_servico"),

                    dias + " dias atrás"
                });

            }

        }

    } catch (SQLException e) {

        System.out.println(
            "Erro ao listar atendimentos: "
            + e.getMessage()
        );

    }

    return lista;
}
}
   