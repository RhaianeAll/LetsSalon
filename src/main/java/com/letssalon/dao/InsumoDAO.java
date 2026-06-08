package com.letssalon.dao;

import com.letssalon.model.Insumo;
import com.letssalon.util.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InsumoDAO {

    public void cadastrar(Insumo insumo) {
        String sql = "INSERT INTO insumo (nome_insumo, valor_insumo, quantidade_insumo, capacidade, codigo_empresa) VALUES (?,?,?,?,?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, insumo.getNomeInsumo());
            stmt.setDouble(2, insumo.getValorInsumo());
            stmt.setInt(3, insumo.getQuantidadeInsumo());
            stmt.setString(4, insumo.getCapacidade());
            stmt.setInt(5, insumo.getCodigoEmpresa());
            stmt.executeUpdate();
            System.out.println("Insumo cadastrado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar insumo: " + e.getMessage());
        }
    }

    public List<Insumo> listar() {
        List<Insumo> lista = new ArrayList<>();
        String sql = "SELECT * FROM insumo";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Insumo i = new Insumo();
                i.setCodigoInsumo(rs.getInt("codigo_insumo"));
                i.setNomeInsumo(rs.getString("nome_insumo"));
                i.setValorInsumo(rs.getDouble("valor_insumo"));
                i.setQuantidadeInsumo(rs.getInt("quantidade_insumo"));
                i.setCapacidade(rs.getString("capacidade"));
                i.setCodigoEmpresa(rs.getInt("codigo_empresa"));
                lista.add(i);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar insumos: " + e.getMessage());
        }
        return lista;
    }

    public void atualizar(Insumo insumo) {
        String sql = "UPDATE insumo SET nome_insumo=?, valor_insumo=?, quantidade_insumo=?, capacidade=?, codigo_empresa=? WHERE codigo_insumo=?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, insumo.getNomeInsumo());
            stmt.setDouble(2, insumo.getValorInsumo());
            stmt.setInt(3, insumo.getQuantidadeInsumo());
            stmt.setString(4, insumo.getCapacidade());
            stmt.setInt(5, insumo.getCodigoEmpresa());
            stmt.setInt(6, insumo.getCodigoInsumo());
            stmt.executeUpdate();
            System.out.println("Insumo atualizado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar insumo: " + e.getMessage());
        }
    }

    public void excluir(int codigoInsumo) {
        String sql = "DELETE FROM insumo WHERE codigo_insumo=?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, codigoInsumo);
            stmt.executeUpdate();
            System.out.println("Insumo excluído com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao excluir insumo: " + e.getMessage());
        }
    }
}