package com.letssalon.view;

import com.letssalon.dao.ColaboradorDAO;
import com.letssalon.dao.EmpresaDAO;
import com.letssalon.model.Colaborador;
import com.letssalon.model.Empresa;
import com.letssalon.util.MascaraUtil;
import com.letssalon.util.SenhaUtil;
import com.letssalon.util.CPFUtil;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import com.letssalon.util.CoresUtil;
import com.letssalon.util.BotaoUtil;

public class TelaInscricao extends JFrame {

    private JTextField txtNome;
    private JFormattedTextField txtCpf, txtTelefone;
    private JTextField txtNomeEmpresa, txtFuncao, txtEmailColaborador;
    private JPasswordField txtSenha, txtConfirmarSenha;
    private JButton btnInscrever, btnVoltar;

    public TelaInscricao() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Let's Salon - Inscreva-se");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(CoresUtil.ROSA_FUNDO);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets  = new Insets(7, 10, 7, 10);
        gbc.fill    = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // ── Título ──
        JLabel lblTitulo = new JLabel("INSCREVA-SE", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(201, 72, 105));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(lblTitulo, gbc);
        gbc.gridwidth = 1;

        // ── Linha 1: Nome Completo ──
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Nome Completo:"), gbc);
        txtNome = new JTextField(20);
        gbc.gridx = 1;
        add(txtNome, gbc);

        // ── Linha 2: CPF ──
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("CPF:"), gbc);
        txtCpf = MascaraUtil.criarCampoCPF();
        gbc.gridx = 1;
        add(txtCpf, gbc);

        // ── Linha 3: Telefone ──
        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Telefone (Whatsapp):"), gbc);
        txtTelefone = MascaraUtil.criarCampoTelefone();
        gbc.gridx = 1;
        add(txtTelefone, gbc);

        // ── Linha 4: Nome da Empresa ──
        gbc.gridx = 0; gbc.gridy = 4;
        add(new JLabel("Nome da Empresa:"), gbc);
        txtNomeEmpresa = new JTextField(20);
        gbc.gridx = 1;
        add(txtNomeEmpresa, gbc);

        // ── Linha 5: Função ──
        gbc.gridx = 0; gbc.gridy = 5;
        add(new JLabel("Função:"), gbc);
        txtFuncao = new JTextField(20);
        gbc.gridx = 1;
        add(txtFuncao, gbc);

        // ── Linha 6: E-mail ──
        gbc.gridx = 0; gbc.gridy = 6;
        add(new JLabel("E-mail:"), gbc);
        txtEmailColaborador = new JTextField(20);
        gbc.gridx = 1;
        add(txtEmailColaborador, gbc);

        // ── Linha 7: Senha ──
        gbc.gridx = 0; gbc.gridy = 7;
        add(new JLabel("Senha:"), gbc);
        txtSenha = new JPasswordField(20);
        gbc.gridx = 1;
        add(SenhaUtil.criarPainelSenha(txtSenha), gbc);

        // ── Linha 8: Confirmar Senha ──
        gbc.gridx = 0; gbc.gridy = 8;
        add(new JLabel("Confirme a Senha:"), gbc);
        txtConfirmarSenha = new JPasswordField(20);
        gbc.gridx = 1;
        add(txtConfirmarSenha, gbc);

        // ── Linha 9: Botão INSCREVER-SE ──
        btnInscrever = BotaoUtil.criarBotao("INSCREVER-SE", new Color(201, 72, 105));
        gbc.gridx = 0; gbc.gridy = 9; gbc.gridwidth = 2;
        add(btnInscrever, gbc);

        // ── Linha 10: Botão VOLTAR ──
        btnVoltar = BotaoUtil.criarBotao("VOLTAR", CoresUtil.CINZA_VOLTAR);
        gbc.gridy = 10;
        add(btnVoltar, gbc);

        // ── Ações ──
        btnInscrever.addActionListener(e -> realizarInscricao());
        btnVoltar.addActionListener(e -> {
            new TelaSelecao().setVisible(true);
            dispose();
        });
    }

    private void realizarInscricao() {
        String senha     = new String(txtSenha.getPassword());
        String confirmar = new String(txtConfirmarSenha.getPassword());

        // Validação de campos obrigatórios
        if (txtNome.getText().trim().isEmpty()
                || txtCpf.getText().trim().isEmpty()
                || txtNomeEmpresa.getText().trim().isEmpty()
                || txtEmailColaborador.getText().trim().isEmpty()
                || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos obrigatórios!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!senha.equals(confirmar)) {
            JOptionPane.showMessageDialog(this, "As senhas não coincidem!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!CPFUtil.validarCPF(txtCpf.getText())) {

    JOptionPane.showMessageDialog(
        this,
        "CPF inválido!",
        "Erro",
        JOptionPane.ERROR_MESSAGE
    );

    return;
}

        // Busca ou cria a empresa
        EmpresaDAO empresaDAO = new EmpresaDAO();
        int codigoEmpresa = 0;
        for (Empresa emp : empresaDAO.listar()) {
            if (emp.getNomeEmpresa().equalsIgnoreCase(txtNomeEmpresa.getText().trim())) {
                codigoEmpresa = emp.getCodigoEmpresa();
                break;
            }
        }
        if (codigoEmpresa == 0) {
            Empresa novaEmpresa = new Empresa();
            novaEmpresa.setNomeEmpresa(txtNomeEmpresa.getText().trim());
            empresaDAO.cadastrar(novaEmpresa);
            for (Empresa emp : empresaDAO.listar()) {
                if (emp.getNomeEmpresa().equalsIgnoreCase(txtNomeEmpresa.getText().trim())) {
                    codigoEmpresa = emp.getCodigoEmpresa();
                    break;
                }
            }
        }

        // Cadastra o colaborador administrador
        Colaborador colaborador = new Colaborador();
        colaborador.setNomeColaborador(txtNome.getText().trim());
        colaborador.setCpfColaborador(txtCpf.getText().trim());
        colaborador.setTelefoneColaborador(txtTelefone.getText().trim());
        colaborador.setFuncaoColaborador(txtFuncao.getText().trim());
        colaborador.setEmailColaborador(txtEmailColaborador.getText().trim());
        String senhaCriptografada = SenhaUtil.criptografarSenha(senha);
        colaborador.setSenhaColaborador(senhaCriptografada);
        colaborador.setPerfil("administrador");
        colaborador.setCodigoEmpresa(codigoEmpresa);

        new ColaboradorDAO().cadastrar(colaborador);

        JOptionPane.showMessageDialog(this,
            "Cadastro realizado com sucesso!\n" +
            "Seu Código da Empresa é: " + codigoEmpresa + "\n" +
            "Guarde este código para fazer login!",
            "Sucesso",
            JOptionPane.INFORMATION_MESSAGE);

        new TelaLogin().setVisible(true);
        dispose();
    }
}