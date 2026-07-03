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
    
    public String buscarNomePorId(int idEmpresa) {
        return empresaDAO.buscarNomePorId(idEmpresa);
    }

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
    
    public List<Empresa> listarEmpresas() {
        return empresaDAO.listarTodas();
    }

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

    public void excluirEmpresa(int id) {
        empresaDAO.excluir(id);
    }

    // NOVO: Método que as Views vão usar para descobrir o ID da empresa logada!
    public int buscarIdPorNome(String nomeEmpresa) {
        return empresaDAO.buscarIdPorNome(nomeEmpresa);
    }
}