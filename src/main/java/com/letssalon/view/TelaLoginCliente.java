package com.letssalon.view;

import com.letssalon.dao.ClienteDAO;
import com.letssalon.model.Cliente;
import com.letssalon.util.SenhaUtil;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import com.letssalon.util.CoresUtil;

public class TelaLoginCliente extends JFrame {

    private JTextField txtEmailCliente;
    private JPasswordField txtSenha;
    private JButton btnEntrar, btnInscrever, btnVoltar;

    public TelaLoginCliente() {
        initComponents();
    }

    private void initComponents() {
        // Tela de login exclusiva para clientes
        setTitle("Let's Salon - Login Cliente");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(CoresUtil.ROSA_FUNDO);

        // ── PAINEL ESQUERDO: Logo ──
        JPanel painelEsquerdo = new JPanel(new GridBagLayout());
        painelEsquerdo.setBackground(CoresUtil.ROSA_FUNDO);
        painelEsquerdo.setPreferredSize(new Dimension(350, 500));

        GridBagConstraints gbcE = new GridBagConstraints();
        gbcE.gridx = 0;
        gbcE.insets = new Insets(5, 20, 5, 20);

        JLabel lblNome = new JLabel("Let's Salon.");
        lblNome.setFont(new Font("Serif", Font.ITALIC, 42));
        lblNome.setForeground(new Color(201, 72, 105));
        gbcE.gridy = 0;
        painelEsquerdo.add(lblNome, gbcE);

        JLabel lblSlogan = new JLabel("Seu sistema de controle para seu studio de beleza");
        lblSlogan.setFont(new Font("Arial", Font.PLAIN, 11));
        lblSlogan.setForeground(new Color(201, 72, 105));
        gbcE.gridy = 1;
        painelEsquerdo.add(lblSlogan, gbcE);

        add(painelEsquerdo, BorderLayout.WEST);

        // ── PAINEL DIREITO: Formulário de login ──
        JPanel painelDireito = new JPanel(new GridBagLayout());
        painelDireito.setBackground(CoresUtil.ROSA_PAINEL_FORM);
        painelDireito.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        // Título LOGIN
        JLabel lblTitulo = new JLabel("LOGIN", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 26));
        lblTitulo.setForeground(new Color(201, 72, 105));
        gbc.gridy = 0;
        painelDireito.add(lblTitulo, gbc);

        // Campo E-mail
        gbc.gridy = 1;
        painelDireito.add(new JLabel("E-mail:"), gbc);
        txtEmailCliente = new JTextField(20);
        gbc.gridy = 2;
        painelDireito.add(txtEmailCliente, gbc);

        // Campo Senha com botão olho
        gbc.gridy = 3;
        painelDireito.add(new JLabel("Senha:"), gbc);
        txtSenha = new JPasswordField(20);
        gbc.gridy = 4;
        // Usa o utilitário para criar o painel com olho
        painelDireito.add(SenhaUtil.criarPainelSenha(txtSenha), gbc);

        // Link esqueci minha senha
        JLabel lblEsqueci = new JLabel("<html><u>Não lembra sua senha? Clique aqui.</u></html>");
        lblEsqueci.setFont(new Font("Arial", Font.PLAIN, 11));
        lblEsqueci.setForeground(CoresUtil.ROSA_ESCURO_LINK);
        lblEsqueci.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridy = 5;
        painelDireito.add(lblEsqueci, gbc);
        lblEsqueci.addMouseListener(new MouseAdapter() {

    @Override
    public void mouseClicked(MouseEvent e) {

        String email = JOptionPane.showInputDialog(
            null,
            "Digite seu e-mail cadastrado:"
        );

        if (email == null || email.trim().isEmpty()) {
            return;
        }

        String novaSenha = JOptionPane.showInputDialog(
            null,
            "Digite sua nova senha:"
        );

        if (novaSenha == null || novaSenha.trim().isEmpty()) {
            return;
        }

        ClienteDAO clienteDAO = new ClienteDAO();

        boolean senhaAlterada = clienteDAO.redefinirSenha(
            email,
            novaSenha
        );

        if (senhaAlterada) {

            JOptionPane.showMessageDialog(
                null,
                "Senha alterada com sucesso!"
            );

        } else {

            JOptionPane.showMessageDialog(
                null,
                "E-mail não encontrado.",
                "Erro",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
});

        // Botão Entrar
        btnEntrar = criarBotao("ENTRAR", new Color(201, 72, 105));
        gbc.gridy = 6;
        painelDireito.add(btnEntrar, gbc);

        // Texto e botão inscreva-se
        JLabel lblConta = new JLabel("Não tem uma conta ainda?", SwingConstants.CENTER);
        lblConta.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridy = 7;
        painelDireito.add(lblConta, gbc);

        btnInscrever = criarBotao("INSCREVA-SE", CoresUtil.ROSA_PAINEL_FORM);
        gbc.gridy = 8;
        painelDireito.add(btnInscrever, gbc);

        btnVoltar = criarBotao("VOLTAR", CoresUtil.CINZA_VOLTAR);
        gbc.gridy = 9;
        painelDireito.add(btnVoltar, gbc);

        add(painelDireito, BorderLayout.CENTER);

        // ── AÇÕES ──
        btnEntrar.addActionListener(e -> realizarLogin());

        btnInscrever.addActionListener(e -> {
            new TelaInscricaoCliente().setVisible(true);
            dispose();
        });

        btnVoltar.addActionListener(e -> {
            new TelaSelecao().setVisible(true);
            dispose();
        });
    }

    private void realizarLogin() {
        String email = txtEmailCliente.getText().trim();
        String senha = new String(txtSenha.getPassword()).trim();

        // Validação básica dos campos
        if (email.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Preencha todos os campos!",
                "Atenção",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Busca o cliente no banco pelo e-mail e senha
        ClienteDAO clienteDAO = new ClienteDAO();
        Cliente cliente = clienteDAO.buscarPorLogin(email, senha);

        if (cliente != null) {
            JOptionPane.showMessageDialog(this,
                "Bem-vindo(a), " + cliente.getNomeCliente() + "!");
            new TelaAgendaCliente(cliente).setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                "E-mail ou senha incorretos!",
                "Erro de Login",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private JButton criarBotao(String texto, Color cor) {
        JButton btn = new JButton(texto);
        btn.setBackground(cor);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        return btn;
    }
}