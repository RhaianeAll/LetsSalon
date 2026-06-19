package salon.controller;

import salon.dao.EmpresaDAO;
import salon.model.Empresa;
import salon.util.ValidadorUtil;

public class EmpresaController {

    private EmpresaDAO empresaDAO;

    public EmpresaController() {
        this.empresaDAO = new EmpresaDAO();
    }

    public void cadastrarEmpresa(String nome, String telefone, String email) {
        
        // Validação de campos em branco
        if (!ValidadorUtil.camposPreenchidos(nome, telefone, email)) {
            throw new IllegalArgumentException("Todos os campos da empresa (Nome, Telefone e E-mail) são obrigatórios.");
        }

        // Validação básica de formato de e-mail
        if (!email.contains("@") || !email.contains(".")) {
            throw new IllegalArgumentException("Por favor, insira um endereço de e-mail válido.");
        }

        Empresa empresa = new Empresa();
        empresa.setNomeEmpresa(nome);
        empresa.setTelefone(telefone);
        empresa.setEmail(email);

        empresaDAO.cadastrar(empresa);
    }
    
    // Futuramente, você pode adicionar aqui os métodos listarEmpresas(), atualizarEmpresa() e excluirEmpresa()
}