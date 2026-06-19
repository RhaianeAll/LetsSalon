package salon.controller;

import salon.dao.UsuarioDAO;
import salon.model.Usuario;
import salon.util.ValidadorUtil;

public class UsuarioController {

    private UsuarioDAO usuarioDAO;

    public UsuarioController() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public void cadastrarCliente(String nome, String cpf, String telefone, String login, String senha, String confirmacaoSenha) {
        
        // 1. Validação de campos vazios
        if (!ValidadorUtil.camposPreenchidos(nome, cpf, telefone, login, senha, confirmacaoSenha)) {
            throw new IllegalArgumentException("Todos os campos são obrigatórios.");
        }

        // 2. Validação de CPF
        if (!ValidadorUtil.isCpfValido(cpf)) {
            throw new IllegalArgumentException("O CPF informado é inválido.");
        }

        // 3. Validação de Senha
        if (!senha.equals(confirmacaoSenha)) {
            throw new IllegalArgumentException("As senhas não coincidem.");
        }

        // 4. Monta o objeto e manda pro DAO
        Usuario novoCliente = new Usuario();
        novoCliente.setNomeCompleto(nome);
        novoCliente.setCpf(cpf.replaceAll("[^0-9]", "")); // Limpa a formatação
        novoCliente.setTelefone(telefone);
        novoCliente.setUsuarioLogin(login);
        novoCliente.setSenha(senha);
        novoCliente.setTipoUsuario("cliente"); // Força o tipo para evitar falhas de segurança
        
        usuarioDAO.cadastrar(novoCliente);
    }
}