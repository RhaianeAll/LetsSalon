package salon.controller;

import salon.dao.FuncionarioDAO;
import salon.model.Funcionario;
import salon.util.ValidadorUtil;
import java.util.List;

public class FuncionarioController {

    private FuncionarioDAO funcionarioDAO;

    public FuncionarioController() {
        this.funcionarioDAO = new FuncionarioDAO();
    }
    
    public List<Funcionario> listarFuncionarios() {
        return funcionarioDAO.listar();
    }

    public void cadastrarFuncionario(String nome, String cpf, String telefone, String login, String senha, String cargo, int empresaId) {
        if (!ValidadorUtil.camposPreenchidos(nome, cpf, telefone, login, senha, cargo)) {
            throw new IllegalArgumentException("Todos os campos do funcionário são obrigatórios.");
        }
        if (empresaId <= 0) {
            throw new IllegalArgumentException("É necessário vincular o funcionário a uma empresa.");
        }

        Funcionario func = new Funcionario();
        func.setNome(nome);
        func.setCpf(cpf.replaceAll("[^0-9]", "")); // Limpa a máscara
        func.setTelefone(telefone);
        func.setLogin(login);
        func.setSenha(senha);
        func.setCargo(cargo);
        func.setEmpresaId(empresaId);

        funcionarioDAO.cadastrar(func);
    }

    public List<Funcionario> listarFuncionarios(String nomeEmpresa) {
        return funcionarioDAO.listarFuncionariosPorEmpresa(nomeEmpresa);
    }

    public void atualizarFuncionario(int id, String nome, String cpf, String telefone, String login, String senha, String cargo) {
        if (!ValidadorUtil.camposPreenchidos(nome, cpf, telefone, login, senha, cargo)) {
            throw new IllegalArgumentException("Todos os campos do funcionário são obrigatórios para atualização.");
        }

        Funcionario func = new Funcionario();
        func.setIdFuncionario(id);
        func.setNome(nome);
        func.setCpf(cpf.replaceAll("[^0-9]", "")); // Limpa a máscara
        func.setTelefone(telefone);
        func.setLogin(login);
        func.setSenha(senha);
        func.setCargo(cargo);

        funcionarioDAO.atualizar(func);
    }

    public void excluirFuncionario(int id) {
        funcionarioDAO.excluir(id);
    }
}