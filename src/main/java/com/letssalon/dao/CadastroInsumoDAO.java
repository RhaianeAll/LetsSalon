package com.letssalon.dao;

import com.letssalon.model.CadastroInsumo;
import com.letssalon.util.Conexao;
import java.sql.*;

public class CadastroInsumoDAO {

    public boolean cadastrar(CadastroInsumo ci) {
        String sql = "INSERT INTO cadastro_insumo (codigo_insumo, rende_aplicacoes, codigo_servico) VALUES (?,?,?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, ci.getCodigoInsumo());
            stmt.setInt(2, ci.getRendeAplicacoes());
            stmt.setInt(3, ci.getCodigoServico());
            int linhas = stmt.executeUpdate();
            return linhas > 0;
        } catch (SQLException e) {
            System.out.println(
                    "Erro ao cadastrar insumo: " 
                            + e.getMessage()
            );
            return false;
        }
    }

 public boolean excluir(int codigoInsumo, int codigoServico) {

    String sql = "DELETE FROM cadastro_insumo WHERE codigo_insumo=? AND codigo_servico=?";

    try (
        Connection conn = Conexao.conectar();
        PreparedStatement stmt = conn.prepareStatement(sql)
    ) {

        stmt.setInt(1, codigoInsumo);
        stmt.setInt(2, codigoServico);

        return stmt.executeUpdate() > 0;

    } catch (SQLException e) {

        System.out.println("Erro ao excluir: " + e.getMessage());

        return false;
    }
}
        public ResultSet listarRelacionamentos() {

    String sql =
        "SELECT ci.codigo_insumo, " +
        "i.nome_insumo, " +
        "ci.rende_aplicacoes, " +
        "ci.codigo_servico, " +
        "s.nome_servico " +
        "FROM cadastro_insumo ci " +
        "INNER JOIN insumo i ON i.codigo_insumo = ci.codigo_insumo " +
        "INNER JOIN servico s ON s.codigo_servico = ci.codigo_servico";

    try {

        Connection conn = Conexao.conectar();

        PreparedStatement stmt = conn.prepareStatement(sql);

        return stmt.executeQuery();

    } catch (SQLException e) {

        System.out.println("Erro ao listar relacionamento: " + e.getMessage());

        return null;
    }
}
     public java.util.List<CadastroInsumo> listar() {

    java.util.List<CadastroInsumo> lista = new java.util.ArrayList<>();

    String sql =
        "SELECT " +
        "ci.codigo_insumo, " +
        "i.nome_insumo, " +
        "ci.rende_aplicacoes, " +
        "ci.codigo_servico, " +
        "s.nome_servico " +
        "FROM cadastro_insumo ci " +
        "INNER JOIN insumo i " +
        "ON ci.codigo_insumo = i.codigo_insumo " +
        "INNER JOIN servico s " +
        "ON ci.codigo_servico = s.codigo_servico " +
        "ORDER BY i.nome_insumo";

    try (
        Connection conn = Conexao.conectar();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()
    ) {

        while (rs.next()) {

            CadastroInsumo ci = new CadastroInsumo();

            ci.setCodigoInsumo(
                    rs.getInt("codigo_insumo"));

            ci.setNomeInsumo(
                    rs.getString("nome_insumo"));

            ci.setRendeAplicacoes(
                    rs.getInt("rende_aplicacoes"));

            ci.setCodigoServico(
                    rs.getInt("codigo_servico"));

            ci.setNomeServico(
                    rs.getString("nome_servico"));

            lista.add(ci);
        }

    } catch (SQLException e) {

        System.out.println(
                "Erro ao listar relacionamento: "
                + e.getMessage());
    }

    return lista;
}   
    }