package salon.controller;

import salon.dao.EmpresaDAO;
import salon.model.Empresa;
import salon.util.ValidadorUtil;
import java.util.List;

public class EmpresaController {

    private EmpresaDAO empresaDAO;

    public EmpresaController() {
        this.empresaDAO = new EmpresaDAO();
    }

    // CADASTRA
    public void cadastrarEmpresa(String nome, String telefone, String email) {
        if (!ValidadorUtil.camposPreenchidos(nome, telefone, email)) {
            throw new IllegalArgumentException("Todos os campos da empresa (Nome, Telefone e E-mail) são obrigatórios.");
        }
        if (!email.contains("@") || !email.contains(".")) {
            throw new IllegalArgumentException("Por favor, insira um endereço de e-mail válido.");
        }

        Empresa empresa = new Empresa();
        empresa.setNomeEmpresa(nome);
        empresa.setTelefoneEmpresa(telefone);
        empresa.setEmailEmpresa(email);

        empresaDAO.cadastrar(empresa);
    }
    
    // LISTA
    public List<Empresa> listarEmpresas() {
        return empresaDAO.listarTodas();
    }

    // ATUALIZA
    public void atualizarEmpresa(int id, String nome, String telefone, String email) {
        if (!ValidadorUtil.camposPreenchidos(nome, telefone, email)) {
            throw new IllegalArgumentException("Todos os campos da empresa são obrigatórios para atualização.");
        }
        if (!email.contains("@") || !email.contains(".")) {
            throw new IllegalArgumentException("Por favor, insira um endereço de e-mail válido.");
        }

        Empresa empresa = new Empresa(id, nome, telefone, email);
        empresaDAO.atualizar(empresa);
    }

    // EXCLUI
    public void excluirEmpresa(int id) {
        empresaDAO.excluir(id);
    }
}