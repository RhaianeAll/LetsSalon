package com.letssalon.view;

import com.letssalon.dao.ClienteDAO;
import com.letssalon.dao.ColaboradorDAO;
import com.letssalon.model.Cliente;
import com.letssalon.model.Colaborador;
import com.letssalon.util.SenhaUtil;
import javax.swing.*;
import java.awt.*;
import com.letssalon.util.CoresUtil;
import com.letssalon.util.BotaoUtil;

public class TelaLogin extends JFrame {

    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private JTextField txtCodigoEmpresa;
    private JButton btnEntrar, btnInscrever, btnVoltar;

    public TelaLogin() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Let's Salon - Login");
        setSize(480, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(CoresUtil.ROSA_FUNDO);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets  = new Insets(8, 10, 8, 10);
        gbc.fill    = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // ── Título ──────────────────────────────────────────
        JLabel lblTitulo = new JLabel("LOGIN", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(201, 72, 105));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(lblTitulo, gbc);

        // ── Linha 1: Código da Empresa ───────────────────────
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Código da Empresa:"), gbc);
        txtCodigoEmpresa = new JTextField(20);
        gbc.gridx = 1;
        add(txtCodigoEmpresa, gbc);

        // ── Linha 2: E-mail ─────────────────────────────────
       gbc.gridx = 0; gbc.gridy = 2;
       add(new JLabel("E-mail:"), gbc);
       txtEmail = new JTextField(20);
       gbc.gridx = 1;
       add(txtEmail, gbc);

        // ── Linha 3: Label Senha ─────────────────────────────
        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Senha:"), gbc);

        // ── Linha 3: Campo Senha (com olho) ──────────────────
        txtSenha = new JPasswordField(20);
        JPanel painelSenha = SenhaUtil.criarPainelSenha(txtSenha);
        gbc.gridx = 1; gbc.gridy = 3;
        add(painelSenha, gbc);

        // ── Linha 4: Botão ENTRAR ────────────────────────────
        btnEntrar = BotaoUtil.criarBotao("ENTRAR", new Color(201, 72, 105));
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        add(btnEntrar, gbc);

        // ── Linha 5: Botão INSCREVA-SE ───────────────────────
        btnInscrever = BotaoUtil.criarBotao("INSCREVA-SE", CoresUtil.ROSA_SECUNDARIO);
        gbc.gridy = 5;
        add(btnInscrever, gbc);

        // ── Linha 6: Botão VOLTAR ─────────────────────────────
        btnVoltar = BotaoUtil.criarBotao("VOLTAR", CoresUtil.CINZA_VOLTAR);
        gbc.gridy = 6;
        add(btnVoltar, gbc);

        // ── Ações ─────────────────────────────────────────────
        btnEntrar.addActionListener(e -> realizarLogin());
        btnInscrever.addActionListener(e -> {
            new TelaInscricao().setVisible(true);
            dispose();
        });
        btnVoltar.addActionListener(e -> {
            new TelaSelecao().setVisible(true);
            dispose();
        });
    }

    private void realizarLogin() {
        String email = txtEmail.getText().trim();
        String senha     = new String(txtSenha.getPassword()).trim();
        String codigoStr = txtCodigoEmpresa.getText().trim();

       if (email.isEmpty() || senha.isEmpty() || codigoStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int codigoEmpresa;
        try {
            codigoEmpresa = Integer.parseInt(codigoStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Código da empresa inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ColaboradorDAO colaboradorDAO = new ColaboradorDAO();
        Colaborador colaborador = colaboradorDAO.buscarPorLogin(email, senha, codigoEmpresa);
        if (colaborador != null) {
            JOptionPane.showMessageDialog(this, "Bem-vindo(a), " + colaborador.getNomeColaborador() + "!");
            new TelaPrincipal(colaborador).setVisible(true);
            dispose();
            return;
        }

        ClienteDAO clienteDAO = new ClienteDAO();
        Cliente cliente = clienteDAO.buscarPorLogin(email, senha);
        if (cliente != null) {
            JOptionPane.showMessageDialog(this, "Bem-vindo(a), " + cliente.getNomeCliente() + "!");
            new TelaAgendaCliente(cliente).setVisible(true);
            dispose();
            return;
        }

        JOptionPane.showMessageDialog(this, "Usuário ou senha incorretos!", "Erro de Login", JOptionPane.ERROR_MESSAGE);
    }
}