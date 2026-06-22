package com.letssalon.dao;

import com.letssalon.model.Insumo;
import com.letssalon.util.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InsumoDAO {

    public boolean cadastrar(Insumo insumo) {
        String sql = "INSERT INTO insumo (nome_insumo, valor_insumo, quantidade_insumo, capacidade, codigo_empresa) VALUES (?,?,?,?,?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, insumo.getNomeInsumo());
            stmt.setDouble(2, insumo.getValorInsumo());
            stmt.setInt(3, insumo.getQuantidadeInsumo());
            stmt.setString(4, insumo.getCapacidade());
            stmt.setInt(5, insumo.getCodigoEmpresa());
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
    

    public boolean atualizar(Insumo insumo) {
        String sql = "UPDATE insumo SET nome_insumo=?, valor_insumo=?, quantidade_insumo=?, capacidade=?, codigo_empresa=? WHERE codigo_insumo=?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, insumo.getNomeInsumo());
            stmt.setDouble(2, insumo.getValorInsumo());
            stmt.setInt(3, insumo.getQuantidadeInsumo());
            stmt.setString(4, insumo.getCapacidade());
            stmt.setInt(5, insumo.getCodigoEmpresa());
            stmt.setInt(6, insumo.getCodigoInsumo());
           int linhas = stmt.executeUpdate();

        return linhas > 0;

    } catch(SQLException e){

        System.out.println(
            "Erro ao atualizar insumo: " + e.getMessage()
        );

        return false;
        }
    }

    public boolean excluir(int codigoInsumo) {
        String sql = "DELETE FROM insumo WHERE codigo_insumo=?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, codigoInsumo);
           int linhas = stmt.executeUpdate();

        return linhas > 0;

    } catch(SQLException e){

        System.out.println(
            "Erro ao excluir insumo: " + e.getMessage()
        );

        return false;
        }
    }
    public Insumo buscarPorCodigo(int codigoInsumo) {

    String sql =
        "SELECT * FROM insumo WHERE codigo_insumo = ?";

    try (
        Connection conn = Conexao.conectar();
        PreparedStatement stmt =
                conn.prepareStatement(sql)
    ) {

        stmt.setInt(1, codigoInsumo);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {

            Insumo i = new Insumo();

            i.setCodigoInsumo(
                    rs.getInt("codigo_insumo")
            );

            i.setNomeInsumo(
                    rs.getString("nome_insumo")
            );

            return i;
        }

    } catch (SQLException e) {

        System.out.println(
            "Erro ao buscar insumo: "
            + e.getMessage()
        );
    }

    return null;
}
 
   
}