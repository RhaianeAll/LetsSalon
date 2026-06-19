package salon.dao;

import salon.model.Servico;
import salon.util.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicoDAO {

    public void cadastrar(Servico servico) {
        String sql = "INSERT INTO servicos (nome, valor, duracao, empresa_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, servico.getNome());
            stmt.setDouble(2, servico.getValor());
            stmt.setString(3, servico.getDuracao());
            stmt.setInt(4, servico.getEmpresaId());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Servico> listar() {
        List<Servico> lista = new ArrayList<>();
        String sql = "SELECT * FROM servicos";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Servico s = new Servico();
                s.setIdServico(rs.getInt("id_servico"));
                s.setNome(rs.getString("nome"));
                s.setValor(rs.getDouble("valor"));
                s.setDuracao(rs.getString("duracao"));
                lista.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}