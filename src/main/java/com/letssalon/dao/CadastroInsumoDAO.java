package com.letssalon.dao;

import com.letssalon.model.CadastroInsumo;
import com.letssalon.util.Conexao;
import java.sql.*;

public class CadastroInsumoDAO {

    public void cadastrar(CadastroInsumo ci) {
        String sql = "INSERT INTO cadastro_insumo (codigo_insumo, rende_aplicacoes, codigo_servico) VALUES (?,?,?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, ci.getCodigoInsumo());
            stmt.setInt(2, ci.getRendeAplicacoes());
            stmt.setInt(3, ci.getCodigoServico());
            stmt.executeUpdate();
            System.out.println("Cadastro de insumo realizado!");
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar insumo: " + e.getMessage());
        }
    }

    public void excluir(int codigoInsumo, int codigoServico) {
        String sql = "DELETE FROM cadastro_insumo WHERE codigo_insumo=? AND codigo_servico=?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, codigoInsumo);
            stmt.setInt(2, codigoServico);
            stmt.executeUpdate();
            System.out.println("Registro excluído com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao excluir: " + e.getMessage());
        }
    }
}