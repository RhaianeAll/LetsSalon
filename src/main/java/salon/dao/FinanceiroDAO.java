package salon.dao;

import salon.model.Financeiro;
import salon.util.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FinanceiroDAO {

    public void cadastrar(Financeiro f) {
        String sql = "INSERT INTO financeiro (tipo, descricao, categoria, valor, data, funcionario_id, servico_id, custo_insumos, lucro, empresa_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, f.getTipo());
            stmt.setString(2, f.getDescricao());
            stmt.setString(3, f.getCategoria());
            stmt.setDouble(4, f.getValor());
            stmt.setDate(5, Date.valueOf(f.getData()));
            
            if (f.getFuncionarioId() != null) stmt.setInt(6, f.getFuncionarioId());
            else stmt.setNull(6, Types.INTEGER);
            
            if (f.getServicoId() != null) stmt.setInt(7, f.getServicoId());
            else stmt.setNull(7, Types.INTEGER);
            
            stmt.setDouble(8, f.getCustoInsumos());
            stmt.setDouble(9, f.getLucro());
            stmt.setInt(10, f.getEmpresaId()); // A MÁGICA: Grava o ID da empresa!
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Financeiro> listarRelatorio(String nomeEmpresa) {
        List<Financeiro> lista = new ArrayList<>();
        // O JOIN blinda o relatório para mostrar apenas as contas desta empresa
        String sql = "SELECT f.* FROM financeiro f JOIN empresas e ON f.empresa_id = e.id_empresa WHERE e.nome_empresa = ? ORDER BY f.data DESC";
        
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setString(1, nomeEmpresa);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Financeiro f = new Financeiro();
                    f.setId(rs.getInt("id"));
                    f.setTipo(rs.getString("tipo"));
                    f.setDescricao(rs.getString("descricao"));
                    f.setCategoria(rs.getString("categoria"));
                    f.setValor(rs.getDouble("valor"));
                    f.setData(rs.getDate("data").toLocalDate());
                    f.setFuncionarioId(rs.getInt("funcionario_id"));
                    f.setServicoId(rs.getInt("servico_id"));
                    f.setCustoInsumos(rs.getDouble("custo_insumos"));
                    f.setLucro(rs.getDouble("lucro"));
                    f.setEmpresaId(rs.getInt("empresa_id"));
                    lista.add(f);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}