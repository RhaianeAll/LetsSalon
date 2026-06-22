package com.letssalon.dao;

import com.letssalon.model.Empresa;
import com.letssalon.util.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpresaDAO {

    public boolean cadastrar(Empresa empresa) {
        String sql = "INSERT INTO empresa (nome_empresa, telefone_empresa, email_empresa, endereco_empresa) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, empresa.getNomeEmpresa());
            stmt.setString(2, empresa.getTelefoneEmpresa());
            stmt.setString(3, empresa.getEmailEmpresa());
            stmt.setString(4, empresa.getEnderecoEmpresa());
           int linhas = stmt.executeUpdate();
            return linhas > 0;
        } catch (SQLException e) {
            System.out.println(
                    "Erro ao cadastrar empresa: " 
                            + e.getMessage()
            );
            return false;
        }
    }

    public List<Empresa> listar() {
        List<Empresa> lista = new ArrayList<>();
        String sql = "SELECT * FROM empresa";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Empresa e = new Empresa();
                e.setCodigoEmpresa(rs.getInt("codigo_empresa"));
                e.setNomeEmpresa(rs.getString("nome_empresa"));
                e.setTelefoneEmpresa(rs.getString("telefone_empresa"));
                e.setEmailEmpresa(rs.getString("email_empresa"));
                e.setEnderecoEmpresa(rs.getString("endereco_empresa"));
                lista.add(e);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar empresas: " + e.getMessage());
        }
        return lista;
    }

    public boolean atualizar(Empresa empresa) {
        String sql = "UPDATE empresa SET nome_empresa=?, telefone_empresa=?, email_empresa=?, endereco_empresa=? WHERE codigo_empresa=?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, empresa.getNomeEmpresa());
            stmt.setString(2, empresa.getTelefoneEmpresa());
            stmt.setString(3, empresa.getEmailEmpresa());
            stmt.setString(4, empresa.getEnderecoEmpresa());
            stmt.setInt(5, empresa.getCodigoEmpresa());
            int linhas = stmt.executeUpdate();

        return linhas > 0;

    } catch(SQLException e){

        System.out.println(
            "Erro ao atualizar empresa: " + e.getMessage()
        );

        return false;
       }
    }

    public boolean excluir(int codigoEmpresa) {
        String sql = "DELETE FROM empresa WHERE codigo_empresa=?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, codigoEmpresa);
            int linhas = stmt.executeUpdate();

        return linhas > 0;

    } catch(SQLException e){

        System.out.println(
            "Erro ao excluir empresa: " + e.getMessage()
        );

        return false;
        }
    }
}