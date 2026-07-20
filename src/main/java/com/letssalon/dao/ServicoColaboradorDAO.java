package com.letssalon.dao;

import com.letssalon.model.Colaborador;
import com.letssalon.util.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicoColaboradorDAO {

    public boolean vincular(int codigoServico, int idColaborador) {

        String sql =
            "INSERT INTO servico_colaborador " +
            "(codigo_servico, id_colaborador) " +
            "VALUES (?, ?)";

        try (
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, codigoServico);
            stmt.setInt(2, idColaborador);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                "Erro ao vincular profissional: "
                + e.getMessage()
            );

            return false;
        }
    }

    public boolean remover(int codigoServico, int idColaborador) {

        String sql =
            "DELETE FROM servico_colaborador " +
            "WHERE codigo_servico=? " +
            "AND id_colaborador=?";

        try (
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, codigoServico);
            stmt.setInt(2, idColaborador);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                "Erro ao remover vínculo: "
                + e.getMessage()
            );

            return false;
        }
    }

    public List<Colaborador> listarPorServico(int codigoServico) {

        List<Colaborador> lista = new ArrayList<>();

        String sql =
            "SELECT c.* " +
            "FROM colaborador c " +
            "INNER JOIN servico_colaborador sc " +
            "ON c.id_colaborador = sc.id_colaborador " +
            "WHERE sc.codigo_servico = ? " +
            "ORDER BY c.nome_colaborador";

        try (
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, codigoServico);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Colaborador c = new Colaborador();

                c.setIdColaborador(
                    rs.getInt("id_colaborador")
                );

                c.setNomeColaborador(
                    rs.getString("nome_colaborador")
                );

                lista.add(c);
            }

        } catch (SQLException e) {

            System.out.println(
                "Erro ao listar profissionais: "
                + e.getMessage()
            );
        }

        return lista;
    }
}