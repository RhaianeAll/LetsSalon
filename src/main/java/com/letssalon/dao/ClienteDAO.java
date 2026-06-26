package com.letssalon.dao;

import com.letssalon.model.Cliente;
import com.letssalon.util.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ClienteDAO {

    public boolean cadastrar(Cliente cliente) {

    String sql =
        "INSERT INTO cliente " +
        "(cpf_cliente, nome_cliente, endereco_cliente, telefone_cliente, " +
        "email_cliente, data_nascimento, senha_cliente, perfil) " +
        "VALUES (?,?,?,?,?,?,?,?)";

    try (
        Connection conn = Conexao.conectar();
        PreparedStatement stmt = conn.prepareStatement(sql)
    ) {

        stmt.setString(1, cliente.getCpfCliente());
        stmt.setString(2, cliente.getNomeCliente());
        stmt.setString(3, cliente.getEnderecoCliente());
        stmt.setString(4, cliente.getTelefoneCliente());
        stmt.setString(5, cliente.getEmailCliente());

        DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate data =
            LocalDate.parse(
                cliente.getDataNascimento(),
                formatter
            );

        stmt.setDate(
            6,
            java.sql.Date.valueOf(data)
        );

        String senhaHash =
            BCrypt.hashpw(
                cliente.getSenhaCliente(),
                BCrypt.gensalt()
            );

        stmt.setString(7, senhaHash);
        stmt.setString(8, cliente.getPerfil());

        int linhas = stmt.executeUpdate();

        return linhas > 0;

    } catch (Exception e) {

        String erro = e.getMessage();

        if (erro != null &&
            erro.contains("unique_cpf_cliente")) {

            System.out.println("CPF já cadastrado!");

        } else if (
            erro != null &&
            erro.contains("unique_email_cliente")
        ) {

            System.out.println("E-mail já cadastrado!");

        } else {

            e.printStackTrace();

        }

        return false;
    }
}

    public List<Cliente> listar() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM cliente";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Cliente c = new Cliente();
                c.setCpfCliente(rs.getString("cpf_cliente"));
                c.setNomeCliente(rs.getString("nome_cliente"));
                c.setEnderecoCliente(rs.getString("endereco_cliente"));
                c.setTelefoneCliente(rs.getString("telefone_cliente"));
                c.setEmailCliente(rs.getString("email_cliente"));
                c.setDataNascimento(rs.getString("data_nascimento"));
                c.setPerfil(rs.getString("perfil"));
                lista.add(c);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar clientes: " + e.getMessage());
        }
        return lista;
    }

    public boolean atualizar(Cliente cliente) {
        String sql = "UPDATE cliente SET nome_cliente=?, endereco_cliente=?, telefone_cliente=?, email_cliente=?, data_nascimento=?, senha_cliente=?, perfil=? WHERE cpf_cliente=?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNomeCliente());
            stmt.setString(2, cliente.getEnderecoCliente());
            stmt.setString(3, cliente.getTelefoneCliente());
            stmt.setString(4, cliente.getEmailCliente());
            stmt.setString(5, cliente.getDataNascimento());
            stmt.setString(6, cliente.getSenhaCliente());
            stmt.setString(7, cliente.getPerfil());
            stmt.setString(8, cliente.getCpfCliente());
            int linhas = stmt.executeUpdate();

        return linhas > 0;

    } catch(SQLException e){

        System.out.println(
            "Erro ao atualizar cliente: " + e.getMessage()
        );

        return false;
        }
    }

    public boolean excluir(String cpfCliente) {
        String sql = "DELETE FROM cliente WHERE cpf_cliente=?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpfCliente);
           int linhas = stmt.executeUpdate();

        return linhas > 0;

    } catch(SQLException e){

        System.out.println(
            "Erro ao excluir : " + e.getMessage()
        );

        return false;
        }
    }

public Cliente buscarPorLogin(String email, String senha) {

    String sql = "SELECT * FROM cliente WHERE email_cliente=?";

    try (
        Connection conn = Conexao.conectar();
        PreparedStatement stmt = conn.prepareStatement(sql)
    ) {

        stmt.setString(1, email);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {

            String senhaHash = rs.getString("senha_cliente");

            boolean senhaCorreta =
                org.mindrot.jbcrypt.BCrypt.checkpw(
                    senha,
                    senhaHash
                );

            if (senhaCorreta) {

                Cliente c = new Cliente();

                c.setCpfCliente(rs.getString("cpf_cliente"));
                c.setNomeCliente(rs.getString("nome_cliente"));
                c.setPerfil(rs.getString("perfil"));
                c.setEmailCliente(rs.getString("email_cliente"));

                return c;
            }
        }

    } catch (SQLException e) {

        System.out.println(
            "Erro ao buscar login cliente: "
            + e.getMessage()
        );
    }

    return null;
}
    public boolean redefinirSenha(String email, String novaSenha) {

    String sql = "UPDATE cliente SET senha_cliente=? WHERE email_cliente=?";

    try (
        Connection conn = Conexao.conectar();
        PreparedStatement stmt = conn.prepareStatement(sql)
    ) {
        String senhaHash = org.mindrot.jbcrypt.BCrypt.hashpw(
         novaSenha,
         org.mindrot.jbcrypt.BCrypt.gensalt()
);
        stmt.setString(1, senhaHash);
        stmt.setString(2, email);

        int linhasAfetadas = stmt.executeUpdate();

        return linhasAfetadas > 0;

    } catch (SQLException e) {

        System.out.println(
            "Erro ao redefinir senha: " + e.getMessage()
        );

        return false;
    }
}
    public List<Cliente> buscar(String filtro) {

    List<Cliente> lista = new ArrayList<>();

    String sql =
        "SELECT * FROM cliente " +
        "WHERE nome_cliente ILIKE ? " +
        "OR cpf_cliente ILIKE ? " +
        "OR email_cliente ILIKE ?";

    try (
        Connection conn = Conexao.conectar();
        PreparedStatement stmt =
            conn.prepareStatement(sql)
    ) {

        String pesquisa = "%" + filtro + "%";

        stmt.setString(1, pesquisa);
        stmt.setString(2, pesquisa);
        stmt.setString(3, pesquisa);

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {

            Cliente c = new Cliente();

            c.setCpfCliente(
                rs.getString("cpf_cliente")
            );

            c.setNomeCliente(
                rs.getString("nome_cliente")
            );

            c.setEnderecoCliente(
                rs.getString("endereco_cliente")
            );

            c.setTelefoneCliente(
                rs.getString("telefone_cliente")
            );

            c.setEmailCliente(
                rs.getString("email_cliente")
            );

            c.setDataNascimento(
                rs.getString("data_nascimento")
            );

            c.setPerfil(
                rs.getString("perfil")
            );

            lista.add(c);
        }

    } catch (SQLException e) {

        System.out.println(
            "Erro ao buscar clientes: "
            + e.getMessage()
        );
    }

    return lista;
}
}