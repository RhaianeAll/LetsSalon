package com.letssalon.view;

import javax.swing.*;
import java.awt.*;

public class TelaQuemSomos extends JFrame {

    public TelaQuemSomos() {
        initComponents();
    }

    private void initComponents() {
        // Tela institucional - fechar essa tela fecha o sistema inteiro
        setTitle("Let's Salon - Quem Somos");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // fecha o sistema ao fechar
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(255, 240, 243));

        // ── TOPO: Logo e slogan ──
        JPanel painelTopo = new JPanel();
        painelTopo.setLayout(new BoxLayout(painelTopo, BoxLayout.Y_AXIS));
        painelTopo.setBackground(new Color(255, 240, 243));
        painelTopo.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        JLabel lblNome = new JLabel("Let's Salon.");
        lblNome.setFont(new Font("Serif", Font.ITALIC, 48));
        lblNome.setForeground(new Color(201, 72, 105));
        lblNome.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelTopo.add(lblNome);

        JLabel lblSlogan = new JLabel("Seu sistema de controle para seu studio de beleza");
        lblSlogan.setFont(new Font("Arial", Font.PLAIN, 13));
        lblSlogan.setForeground(new Color(201, 72, 105));
        lblSlogan.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelTopo.add(lblSlogan);

        add(painelTopo, BorderLayout.NORTH);

        // ── CENTRO: Card com título e texto completo ──
        JPanel painelCentro = new JPanel(new GridBagLayout());
        painelCentro.setBackground(new Color(255, 240, 243));

        // Card branco arredondado com sombra simulada
        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
            BorderFactory.createEmptyBorder(25, 30, 25, 30)
        ));

        // Título "Quem Somos" dentro do card
        JLabel lblTitulo = new JLabel("Quem Somos");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(201, 72, 105));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(lblTitulo);
        card.add(Box.createVerticalStrut(20));

        // Parágrafo 1
        card.add(criarTextArea(
            "O sistema Let's Salon surgiu com o intuito de resolver a problemática que " +
            "muitos sistemas de controle para empresas voltada a área de beleza têm, " +
            "como por exemplo mau funcionamento e falta de controle."
        ));
        card.add(Box.createVerticalStrut(15));

        // Parágrafo 2
        card.add(criarTextArea(
            "O sistema Let's Salon é completo e compreende vários estilos de negócios, " +
            "possui ferramentas personalizadas baseadas em negócios reais, além de um ótimo " +
            "sistema de controle financeiro com disponibilidade de planilhas de controle."
        ));
        card.add(Box.createVerticalStrut(15));

        // Parágrafo 3
        card.add(criarTextArea("Se interessou? Se inscreva e seja nosso colaborador!"));

        // Adiciona o card com tamanho preferido ao centro
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 40, 10, 40);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        painelCentro.add(card, gbc);

        add(painelCentro, BorderLayout.CENTER);

        // ── RODAPÉ: Texto e botão ──
        JPanel painelRodape = new JPanel();
        painelRodape.setLayout(new BoxLayout(painelRodape, BoxLayout.Y_AXIS));
        painelRodape.setBackground(new Color(255, 240, 243));
        painelRodape.setBorder(BorderFactory.createEmptyBorder(5, 0, 20, 0));

        JLabel lblTexto = new JLabel("Clique aqui se desejar fazer a inscrição e gerenciar seu negócio.");
        lblTexto.setFont(new Font("Arial", Font.PLAIN, 11));
        lblTexto.setForeground(Color.GRAY);
        lblTexto.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelRodape.add(lblTexto);
        painelRodape.add(Box.createVerticalStrut(8));

        // Botão principal de inscrição
        JButton btnInscrever = new JButton("DESEJO SER UM COLABORADOR");
        btnInscrever.setBackground(new Color(240, 150, 170));
        btnInscrever.setForeground(Color.WHITE);
        btnInscrever.setFont(new Font("Arial", Font.BOLD, 14));
        btnInscrever.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnInscrever.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnInscrever.setMaximumSize(new Dimension(350, 45));
        btnInscrever.setBorderPainted(false);
        btnInscrever.setFocusPainted(false);
        painelRodape.add(btnInscrever);

        add(painelRodape, BorderLayout.SOUTH);

        // ── AÇÕES ──
        // Direciona para a tela de inscrição de colaborador
        btnInscrever.addActionListener(e -> {
            new TelaInscricao().setVisible(true);
            dispose();
        });
    }

    // Cria área de texto não editável, sem borda, com quebra de linha automática
    private JTextArea criarTextArea(String texto) {
        JTextArea txt = new JTextArea(texto);
        txt.setFont(new Font("Arial", Font.PLAIN, 13));
        txt.setForeground(new Color(80, 80, 80));
        txt.setBackground(Color.WHITE);
        txt.setEditable(false);
        txt.setFocusable(false);
        txt.setLineWrap(true);
        txt.setWrapStyleWord(true);
        txt.setAlignmentX(Component.LEFT_ALIGNMENT);
        // Define tamanho máximo para evitar espaços em branco excessivos
        txt.setPreferredSize(null);
        txt.setMinimumSize(new Dimension(100, 20));
        txt.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        return txt;
    }
}