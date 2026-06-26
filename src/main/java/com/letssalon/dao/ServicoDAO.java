package com.letssalon.dao;

import com.letssalon.model.Servico;
import com.letssalon.util.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicoDAO {

    public boolean cadastrar(Servico servico) {
        String sql = "INSERT INTO servico (nome_servico, valor_servico, duracao_aproximada, id_colaborador, codigo_insumo, quantidade_insumo, codigo_empresa) VALUES (?,?,?,?,?,?,?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, servico.getNomeServico());
            stmt.setDouble(2, servico.getValorServico());
            stmt.setString(3, servico.getDuracaoAproximada());
            stmt.setInt(4, servico.getIdColaborador());
            stmt.setInt(5, servico.getCodigoInsumo());
            stmt.setInt(6, servico.getQuantidadeInsumo());
            stmt.setInt(7, servico.getCodigoEmpresa());
            int linhas = stmt.executeUpdate();
            return linhas > 0;
        } catch (SQLException e) {
            System.out.println(
                    "Erro ao cadastrar serviço: " 
                            + e.getMessage()
            );
            return false;
        }
    }

    public List<Servico> listar() {
        List<Servico> lista = new ArrayList<>();
        String sql = "SELECT * FROM servico";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Servico s = new Servico();
                s.setCodigoServico(rs.getInt("codigo_servico"));
                s.setNomeServico(rs.getString("nome_servico"));
                s.setValorServico(rs.getDouble("valor_servico"));
                s.setDuracaoAproximada(rs.getString("duracao_aproximada"));
                s.setIdColaborador(rs.getInt("id_colaborador"));
                s.setCodigoInsumo(rs.getInt("codigo_insumo"));
                s.setQuantidadeInsumo(rs.getInt("quantidade_insumo"));
                s.setCodigoEmpresa(rs.getInt("codigo_empresa"));
                lista.add(s);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar serviços: " + e.getMessage());
        }
        return lista;
    }
public List<Servico> listarPorEmpresa(int codigoEmpresa) {

    List<Servico> lista = new ArrayList<>();

    String sql =
        "SELECT * FROM servico " +
        "WHERE codigo_empresa = ?";

    try (
        Connection conn = Conexao.conectar();
        PreparedStatement stmt =
                conn.prepareStatement(sql)
    ) {

        stmt.setInt(1, codigoEmpresa);

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {

            Servico s = new Servico();

            s.setCodigoServico(
                rs.getInt("codigo_servico")
            );

            s.setNomeServico(
                rs.getString("nome_servico")
            );

            s.setValorServico(
                rs.getDouble("valor_servico")
            );

            s.setDuracaoAproximada(
                rs.getString("duracao_aproximada")
            );

            s.setIdColaborador(
                rs.getInt("id_colaborador")
            );

            s.setCodigoInsumo(
                rs.getInt("codigo_insumo")
            );

            s.setQuantidadeInsumo(
                rs.getInt("quantidade_insumo")
            );

            s.setCodigoEmpresa(
                rs.getInt("codigo_empresa")
            );

            lista.add(s);
        }

    } catch (SQLException e) {

        System.out.println(
            "Erro ao listar serviços: "
            + e.getMessage()
        );
    }

    return lista;
}
    
    public boolean atualizar(Servico servico) {
        String sql = "UPDATE servico SET nome_servico=?, valor_servico=?, duracao_aproximada=?, id_colaborador=?, codigo_insumo=?, quantidade_insumo=?, codigo_empresa=? WHERE codigo_servico=?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, servico.getNomeServico());
            stmt.setDouble(2, servico.getValorServico());
            stmt.setString(3, servico.getDuracaoAproximada());
            stmt.setInt(4, servico.getIdColaborador());
            stmt.setInt(5, servico.getCodigoInsumo());
            stmt.setInt(6, servico.getQuantidadeInsumo());
            stmt.setInt(7, servico.getCodigoEmpresa());
            stmt.setInt(8, servico.getCodigoServico());
            int linhas = stmt.executeUpdate();

        return linhas > 0;

    } catch(SQLException e){

        System.out.println(
            "Erro ao atualizar serviço: " + e.getMessage()
        );

        return false;
        }
    }

    public boolean excluir(int codigoServico) {
        String sql = "DELETE FROM servico WHERE codigo_servico=?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, codigoServico);
           int linhas = stmt.executeUpdate();

        return linhas > 0;

    } catch(SQLException e){

        System.out.println(
            "Erro ao excluir serviço: " + e.getMessage()
        );

        return false;
        }
    }
    public List<Servico> buscarPorNome(
        String busca,
        int codigoEmpresa
) {

    List<Servico> lista = new ArrayList<>();

    String sql =
        "SELECT * FROM servico " +
        "WHERE codigo_empresa = ? " +
        "AND LOWER(nome_servico) LIKE LOWER(?)";

    try (
        Connection conn = Conexao.conectar();
        PreparedStatement stmt =
                conn.prepareStatement(sql)
    ) {

        stmt.setInt(1, codigoEmpresa);
        stmt.setString(2, "%" + busca + "%");

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {

            Servico s = new Servico();

            s.setCodigoServico(
                rs.getInt("codigo_servico")
            );

            s.setNomeServico(
                rs.getString("nome_servico")
            );

            s.setValorServico(
                rs.getDouble("valor_servico")
            );

            s.setDuracaoAproximada(
                rs.getString("duracao_aproximada")
            );

            s.setCodigoInsumo(
                rs.getInt("codigo_insumo")
            );

            s.setQuantidadeInsumo(
                rs.getInt("quantidade_insumo")
            );

            s.setCodigoEmpresa(
                rs.getInt("codigo_empresa")
            );

            lista.add(s);
        }

    } catch (SQLException e) {

        System.out.println(
            "Erro ao buscar serviço: "
            + e.getMessage()
        );
    }

    return lista;
}
}
        