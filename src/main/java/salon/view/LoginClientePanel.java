package salon.view;

import javax.swing.*;
import java.awt.*;
import salon.controller.LoginController;
import salon.view.components.RoundedButton;
import salon.view.components.RoundedPanel;

public class LoginClientePanel extends JPanel {

    private LoginController loginController;

    public LoginClientePanel(AuthView parentFrame) {
        this.loginController = new LoginController();
        setOpaque(false); 
        setLayout(new BorderLayout()); 

        // ==========================================
        // 1. TOPO: BOTÃO VOLTAR
        // ==========================================
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        topPanel.setOpaque(false);
        JButton btnVoltar = new JButton("← Voltar");
        btnVoltar.setContentAreaFilled(false);
        btnVoltar.setBorderPainted(false);
        btnVoltar.setForeground(Color.decode("#D81B60"));
        btnVoltar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVoltar.addActionListener(e -> parentFrame.trocarTela("Welcome"));
        topPanel.add(btnVoltar);
        add(topPanel, BorderLayout.NORTH);

        // ==========================================
        // 2. CENTRO: LAYOUT DIVIDIDO (Logo | Form)
        // ==========================================
        JPanel centerContainer = new JPanel(new GridBagLayout());
        centerContainer.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;

        // Lado Esquerdo: LOGO (Métrica Padrão: 850px)
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setOpaque(false);
        leftPanel.add(criarLogo(850, -1));
        leftPanel.setPreferredSize(new Dimension(0, 0));// Atualizado de 700 para 850
        
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.55; 
        centerContainer.add(leftPanel, gbc);

        // Lado Direito: FORMULÁRIO E TÍTULO
        JPanel rightPanel = new JPanel();
        rightPanel.setOpaque(false);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        JLabel lblTitle = new JLabel("LOGIN");
        lblTitle.setFont(new Font("Segoe UI Black", Font.BOLD, 42)); 
        lblTitle.setForeground(Color.decode("#F48FB1"));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Card Panel redimensionado
        RoundedPanel cardPanel = new RoundedPanel(30, Color.decode("#FFCDD2"));
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        cardPanel.setPreferredSize(new Dimension(380, 400));
        cardPanel.setMaximumSize(new Dimension(380, 450));

        JTextField txtUsuario = new JTextField();
        txtUsuario.setBorder(BorderFactory.createTitledBorder("Usuário:"));
        txtUsuario.setMaximumSize(new Dimension(320, 60)); // Altura 60px
        txtUsuario.setPreferredSize(new Dimension(320, 60));

        JPasswordField txtSenha = new JPasswordField();
        txtSenha.setBorder(BorderFactory.createTitledBorder("Senha:"));
        txtSenha.setMaximumSize(new Dimension(320, 60)); // Altura 60px
        txtSenha.setPreferredSize(new Dimension(320, 60));

        RoundedButton btnEntrar = new RoundedButton("ENTRAR");
        btnEntrar.setMaximumSize(new Dimension(150, 40));
        btnEntrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        btnEntrar.addActionListener(e -> {
            try {
                if (loginController.fazerLogin(txtUsuario.getText(), new String(txtSenha.getPassword()))) {
                    JOptionPane.showMessageDialog(this, "Bem-vindo(a)!");
                } else {
                    JOptionPane.showMessageDialog(this, "Credenciais incorretas.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });

        RoundedButton btnInscrever = new RoundedButton("INSCREVA-SE");
        btnInscrever.setMaximumSize(new Dimension(150, 40));
        btnInscrever.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnInscrever.addActionListener(e -> parentFrame.trocarTela("CadastroCliente"));

        cardPanel.add(txtUsuario);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        cardPanel.add(txtSenha);
        
        JLabel lblEsqueci = new JLabel("<html><u>Não lembra sua senha? Clique aqui.</u></html>");
        lblEsqueci.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        lblEsqueci.setForeground(Color.decode("#D81B60"));
        lblEsqueci.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cardPanel.add(lblEsqueci);
        
        cardPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        cardPanel.add(btnEntrar);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        JLabel lblNovaConta = new JLabel("Não tem uma conta ainda?");
        lblNovaConta.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.add(lblNovaConta);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        cardPanel.add(btnInscrever);

        // Montagem da coluna da direita
        rightPanel.add(Box.createVerticalGlue());
        rightPanel.add(lblTitle);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        rightPanel.add(cardPanel);
        rightPanel.add(Box.createVerticalGlue());

        JPanel rightContainer = new JPanel(new GridBagLayout());
        rightContainer.setOpaque(false);
        rightContainer.add(rightPanel);
        rightContainer.setPreferredSize(new Dimension(0, 0));

        gbc.gridx = 1; gbc.weightx = 0.45;
        centerContainer.add(rightContainer, gbc);

        add(centerContainer, BorderLayout.CENTER);
    }

    private JLabel criarLogo(int largura, int altura) {
        JLabel lblLogo = new JLabel();
        try {
            java.net.URL imgURL = getClass().getResource("/images/logo.png");
            if (imgURL != null) {
                ImageIcon icon = new ImageIcon(imgURL);
                Image img = icon.getImage().getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
                lblLogo.setIcon(new ImageIcon(img));
            }
        } catch (Exception e) {}
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        return lblLogo;
    }
}