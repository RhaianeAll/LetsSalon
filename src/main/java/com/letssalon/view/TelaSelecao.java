package com.letssalon.view;

import javax.swing.*;
import java.awt.*;
import com.letssalon.util.CoresUtil;

public class TelaSelecao extends JFrame {

    public TelaSelecao() {
        initComponents();
    }

    private void initComponents() {
        // Configurações básicas da janela
        setTitle("Let's Salon");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(CoresUtil.ROSA_FUNDO);

        // ── PAINEL ESQUERDO: Logo e slogan ──
        JPanel painelEsquerdo = new JPanel(new GridBagLayout());
        painelEsquerdo.setBackground(CoresUtil.ROSA_FUNDO);
        painelEsquerdo.setPreferredSize(new Dimension(380, 500));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 20, 5, 20);
        gbc.gridx = 0;

        // Nome do sistema
        JLabel lblNome = new JLabel("Let's Salon.");
        lblNome.setFont(new Font("Serif", Font.ITALIC, 48));
        lblNome.setForeground(CoresUtil.ROSA_PRINCIPAL);
        gbc.gridy = 0;
        painelEsquerdo.add(lblNome, gbc);

        // Slogan
        JLabel lblSlogan = new JLabel("Seu sistema de controle para seu studio de beleza");
        lblSlogan.setFont(new Font("Arial", Font.PLAIN, 12));
        lblSlogan.setForeground(CoresUtil.ROSA_PRINCIPAL);
        gbc.gridy = 1;
        painelEsquerdo.add(lblSlogan, gbc);

        add(painelEsquerdo, BorderLayout.WEST);

        // ── PAINEL DIREITO: Botões de seleção ──
        JPanel painelDireito = new JPanel(new GridBagLayout());
        painelDireito.setBackground(CoresUtil.ROSA_FUNDO);

        GridBagConstraints gbcD = new GridBagConstraints();
        gbcD.insets = new Insets(10, 20, 5, 20);
        gbcD.gridx = 0;
        gbcD.fill = GridBagConstraints.HORIZONTAL;

        // Texto explicativo e botão Sou Cliente
        JLabel lblTextoCliente = new JLabel("Clique aqui se deseja conhecer os prestadores de serviço");
        lblTextoCliente.setFont(new Font("Arial", Font.PLAIN, 11));
        lblTextoCliente.setForeground(Color.GRAY);
        gbcD.gridy = 0;
        painelDireito.add(lblTextoCliente, gbcD);

        JButton btnCliente = criarBotao("SOU CLIENTE");
        gbcD.gridy = 1;
        painelDireito.add(btnCliente, gbcD);

        // Texto explicativo e botão Sou Colaborador
        JLabel lblTextoColab = new JLabel("Clique aqui se você tem uma empresa cadastrada.");
        lblTextoColab.setFont(new Font("Arial", Font.PLAIN, 11));
        lblTextoColab.setForeground(Color.GRAY);
        gbcD.gridy = 2;
        gbcD.insets = new Insets(20, 20, 5, 20);
        painelDireito.add(lblTextoColab, gbcD);

        JButton btnColaborador = criarBotao("SOU COLABORADOR");
        gbcD.gridy = 3;
        gbcD.insets = new Insets(5, 20, 5, 20);
        painelDireito.add(btnColaborador, gbcD);

        // Texto explicativo e botão Quero Conhecer
        JLabel lblTextoConhecer = new JLabel("Clique aqui se tem interesse em conhecer nossos serviços");
        lblTextoConhecer.setFont(new Font("Arial", Font.PLAIN, 11));
        lblTextoConhecer.setForeground(Color.GRAY);
        gbcD.gridy = 4;
        gbcD.insets = new Insets(20, 20, 5, 20);
        painelDireito.add(lblTextoConhecer, gbcD);

        JButton btnConhecer = criarBotao("QUERO CONHECER");
        gbcD.gridy = 5;
        gbcD.insets = new Insets(5, 20, 5, 20);
        painelDireito.add(btnConhecer, gbcD);

        add(painelDireito, BorderLayout.CENTER);

        // ── AÇÕES DOS BOTÕES ──
        // Cliente vai para o login de cliente
        btnCliente.addActionListener(e -> {
            new TelaLoginCliente().setVisible(true);
            dispose();
        });

        // Colaborador vai para o login de colaborador
        btnColaborador.addActionListener(e -> {
            new TelaLogin().setVisible(true);
            dispose();
        });

        // Quero Conhecer vai para a tela institucional
        btnConhecer.addActionListener(e -> {
            new TelaQuemSomos().setVisible(true);
            dispose();
        });
    }

    // Método auxiliar para criar botões padronizados
    private JButton criarBotao(String texto) {
        JButton btn = new JButton(texto);
        btn.setBackground(CoresUtil.ROSA_SECUNDARIO);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(280, 45));
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        return btn;
    }
}