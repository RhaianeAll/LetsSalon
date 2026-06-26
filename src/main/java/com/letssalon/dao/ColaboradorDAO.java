package com.letssalon.dao;

import com.letssalon.model.Colaborador;
import com.letssalon.util.Conexao;
import com.letssalon.util.SenhaUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ColaboradorDAO {

    public boolean cadastrar(Colaborador colaborador) {

    String sql =
        "INSERT INTO colaborador " +
        "(cpf_colaborador, nome_colaborador, funcao_colaborador, " +
        "telefone_colaborador, foto_colaborador, email_colaborador, " +
        "senha_colaborador, perfil, codigo_empresa) " +
        "VALUES (?,?,?,?,?,?,?,?,?)";

    try (
        Connection conn = Conexao.conectar();
        PreparedStatement stmt = conn.prepareStatement(sql)
    ) {

        stmt.setString(1, colaborador.getCpfColaborador());
        stmt.setString(2, colaborador.getNomeColaborador());
        stmt.setString(3, colaborador.getFuncaoColaborador());
        stmt.setString(4, colaborador.getTelefoneColaborador());
        stmt.setString(5, colaborador.getFotoColaborador());
        stmt.setString(6, colaborador.getEmailColaborador());

        String senhaHash =
            SenhaUtil.criptografarSenha(
                colaborador.getSenhaColaborador()
            );

        stmt.setString(7, senhaHash);

        stmt.setString(8, colaborador.getPerfil());
        stmt.setInt(9, colaborador.getCodigoEmpresa());

        int linhas = stmt.executeUpdate();

        return linhas > 0;

    } catch (SQLException e) {

        System.out.println(
            "Erro ao cadastrar colaborador: "
            + e.getMessage()
        );

        return false;

        }
    }

    public List<Colaborador> listarPorEmpresa(int codigoEmpresa) {
        List<Colaborador> lista = new ArrayList<>();
        String sql = "SELECT * FROM colaborador WHERE codigo_empresa=?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, codigoEmpresa);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Colaborador c = new Colaborador();
                c.setIdColaborador(rs.getInt("id_colaborador"));
                c.setCpfColaborador(rs.getString("cpf_colaborador"));
                c.setNomeColaborador(rs.getString("nome_colaborador"));
                c.setFuncaoColaborador(rs.getString("funcao_colaborador"));
                c.setTelefoneColaborador(rs.getString("telefone_colaborador"));
                c.setFotoColaborador(rs.getString("foto_colaborador"));
                c.setPerfil(rs.getString("perfil"));
                c.setCodigoEmpresa(rs.getInt("codigo_empresa"));
                lista.add(c);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar colaboradores por empresa: " + e.getMessage());
        }
        return lista;
    }
    public List<Colaborador> buscarPorNomeOuCpf(   
        String busca,
        int codigoEmpresa) {

        System.out.println("Buscando: " + busca);
        
    List<Colaborador> lista = new ArrayList<>();

    String sql =
        "SELECT * FROM colaborador " +
        "WHERE codigo_empresa = ? " +
        "AND (" +
        "LOWER(nome_colaborador) LIKE LOWER(?) " +
        "OR cpf_colaborador LIKE ?" +
        ")";

    try (
        Connection conn = Conexao.conectar();
        PreparedStatement stmt =
                conn.prepareStatement(sql)
    ) {

        stmt.setInt(1, codigoEmpresa);
        stmt.setString(2, "%" + busca + "%");
        stmt.setString(3, "%" + busca + "%");

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {

            Colaborador c = new Colaborador();

            c.setIdColaborador(
                rs.getInt("id_colaborador")
            );

            c.setNomeColaborador(
                rs.getString("nome_colaborador")
            );

            c.setCpfColaborador(
                rs.getString("cpf_colaborador")
            );

            c.setTelefoneColaborador(
                rs.getString("telefone_colaborador")
            );

            c.setFuncaoColaborador(
                rs.getString("funcao_colaborador")
            );

            lista.add(c);
        }

    } catch (SQLException e) {

        System.out.println(
            "Erro ao buscar colaborador: "
            + e.getMessage()
        );
    }
    System.out.println("Encontrados: " + lista.size());
    return lista;
}
    public List<Colaborador> buscarPorEmpresa(
        String filtro,
        int codigoEmpresa) {

    List<Colaborador> lista = new ArrayList<>();

    String sql =
        "SELECT * FROM colaborador " +
        "WHERE codigo_empresa = ? " +
        "AND (" +
        "LOWER(nome_colaborador) LIKE LOWER(?) " +
        "OR cpf_colaborador LIKE ? " +
        "OR LOWER(funcao_colaborador) LIKE LOWER(?)" +
        ")";

    try (
        Connection conn = Conexao.conectar();
        PreparedStatement stmt =
                conn.prepareStatement(sql)
    ) {

        String pesquisa = "%" + filtro + "%";

        stmt.setInt(1, codigoEmpresa);
        stmt.setString(2, pesquisa);
        stmt.setString(3, pesquisa);
        stmt.setString(4, pesquisa);

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {

            Colaborador c = new Colaborador();

            c.setIdColaborador(
                rs.getInt("id_colaborador")
            );

            c.setCpfColaborador(
                rs.getString("cpf_colaborador")
            );

            c.setNomeColaborador(
                rs.getString("nome_colaborador")
            );

            c.setFuncaoColaborador(
                rs.getString("funcao_colaborador")
            );

            c.setTelefoneColaborador(
                rs.getString("telefone_colaborador")
            );

            c.setFotoColaborador(
                rs.getString("foto_colaborador")
            );

            c.setPerfil(
                rs.getString("perfil")
            );

            c.setCodigoEmpresa(
                rs.getInt("codigo_empresa")
            );

            lista.add(c);
        }

    } catch (SQLException e) {

        System.out.println(
            "Erro ao buscar colaboradores: "
            + e.getMessage()
        );
    }

    return lista;
}

    public List<Colaborador> listar() {
        List<Colaborador> lista = new ArrayList<>();
        String sql = "SELECT * FROM colaborador";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Colaborador c = new Colaborador();
                c.setIdColaborador(rs.getInt("id_colaborador"));
                c.setCpfColaborador(rs.getString("cpf_colaborador"));
                c.setNomeColaborador(rs.getString("nome_colaborador"));
                c.setFuncaoColaborador(rs.getString("funcao_colaborador"));
                c.setTelefoneColaborador(rs.getString("telefone_colaborador"));
                c.setFotoColaborador(rs.getString("foto_colaborador"));
                c.setPerfil(rs.getString("perfil"));
                c.setCodigoEmpresa(rs.getInt("codigo_empresa"));
                lista.add(c);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar colaboradores: " + e.getMessage());
        }
        return lista;
    }

    public boolean atualizar(Colaborador colaborador) {
        String sql = "UPDATE colaborador SET cpf_colaborador=?, nome_colaborador=?, funcao_colaborador=?, telefone_colaborador=?, foto_colaborador=?, senha_colaborador=?, perfil=?, codigo_empresa=? WHERE id_colaborador=?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, colaborador.getCpfColaborador());
            stmt.setString(2, colaborador.getNomeColaborador());
            stmt.setString(3, colaborador.getFuncaoColaborador());
            stmt.setString(4, colaborador.getTelefoneColaborador());
            stmt.setString(5, colaborador.getFotoColaborador());
            stmt.setString(6, colaborador.getSenhaColaborador());
            stmt.setString(7, colaborador.getPerfil());
            stmt.setInt(8, colaborador.getCodigoEmpresa());
            stmt.setInt(9, colaborador.getIdColaborador());
            int linhas = stmt.executeUpdate();

        return linhas > 0;

    } catch(SQLException e){

        System.out.println(
            "Erro ao atualizar colaborador: " + e.getMessage()
        );

        return false;
        }
    }

    public boolean excluir(int idColaborador) {
        String sql = "DELETE FROM colaborador WHERE id_colaborador=?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idColaborador);
            int linhas = stmt.executeUpdate();

        return linhas > 0;

    } catch(SQLException e){

        System.out.println(
            "Erro ao excluir colaborador: " + e.getMessage()
        );

        return false;
        }
    }

public Colaborador buscarPorLogin(String email, String senha, int codigoEmpresa) {

    String sql = "SELECT * FROM colaborador WHERE email_colaborador=? AND codigo_empresa=?";

    try (Connection conn = Conexao.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, email);
        stmt.setInt(2, codigoEmpresa);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {

            String senhaBanco = rs.getString("senha_colaborador");

            // Verifica senha com BCrypt
            if (SenhaUtil.verificarSenha(senha, senhaBanco)) {

                Colaborador c = new Colaborador();

                c.setIdColaborador(rs.getInt("id_colaborador"));
                c.setNomeColaborador(rs.getString("nome_colaborador"));
                c.setFuncaoColaborador(rs.getString("funcao_colaborador"));
                c.setPerfil(rs.getString("perfil"));
                c.setCodigoEmpresa(rs.getInt("codigo_empresa"));
                c.setEmailColaborador(rs.getString("email_colaborador"));

                return c;
            }
        }

    } catch (SQLException e) {
        System.out.println("Erro ao buscar login: " + e.getMessage());
    }

    return null;
}
}
