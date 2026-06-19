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
        
        setBackground(Color.WHITE);
        setLayout(new GridBagLayout()); // Centraliza o card rosa na tela branca

        // Card Rosa com cantos arredondados
        RoundedPanel cardPanel = new RoundedPanel(30, Color.decode("#FFCDD2"));
        cardPanel.setPreferredSize(new Dimension(350, 400));
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBorder(BorderFactory.createEmptyBorder(40, 30, 40, 30));

        JLabel lblTitle = new JLabel("LOGIN CLIENTE");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(Color.decode("#D81B60"));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField txtUsuario = new JTextField();
        txtUsuario.setBorder(BorderFactory.createTitledBorder("Usuário:"));
        txtUsuario.setMaximumSize(new Dimension(300, 50));

        JPasswordField txtSenha = new JPasswordField();
        txtSenha.setBorder(BorderFactory.createTitledBorder("Senha:"));
        txtSenha.setMaximumSize(new Dimension(300, 50));

        RoundedButton btnEntrar = new RoundedButton("ENTRAR");
        btnEntrar.setMaximumSize(new Dimension(150, 40));
        btnEntrar.setAlignmentX(Component.CENTER_ALIGNMENT);

        RoundedButton btnInscrever = new RoundedButton("INSCREVA-SE");
        btnInscrever.setMaximumSize(new Dimension(150, 40));
        btnInscrever.setAlignmentX(Component.CENTER_ALIGNMENT);

        // AÇÃO DO BOTÃO ENTRAR: Conecta com o Banco de Dados!
        btnEntrar.addActionListener(e -> {
            try {
                String usuario = txtUsuario.getText();
                String senha = new String(txtSenha.getPassword());

                if (loginController.fazerLogin(usuario, senha)) {
                    JOptionPane.showMessageDialog(this, "Bem-vindo(a) ao Let's Salon!");
                    // parentFrame.dispose();
                    // new MainClienteView().setVisible(true); // Abre o sistema principal
                } else {
                    JOptionPane.showMessageDialog(this, "Usuário ou senha incorretos.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });

        // AÇÃO DO BOTÃO INSCREVER-SE: Troca a tela
        btnInscrever.addActionListener(e -> parentFrame.trocarTela("CadastroCliente"));

        // Montagem do Card
        cardPanel.add(lblTitle);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        cardPanel.add(txtUsuario);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        cardPanel.add(txtSenha);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        cardPanel.add(btnEntrar);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        JLabel lblNovaConta = new JLabel("Não tem uma conta ainda?");
        lblNovaConta.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.add(lblNovaConta);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        cardPanel.add(btnInscrever);

        // Botão voltar solto fora do card
        JButton btnVoltar = new JButton("← Voltar");
        btnVoltar.setContentAreaFilled(false);
        btnVoltar.setBorderPainted(false);
        btnVoltar.setForeground(Color.decode("#D81B60")); // Deixa o texto rosa escuro
        btnVoltar.setFont(new Font("Segoe UI", Font.BOLD, 14)); // Deixa a fonte mais visível
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVoltar.addActionListener(e -> parentFrame.trocarTela("Welcome"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0;
        add(btnVoltar, gbc);
        
        gbc.gridy = 1;
        add(cardPanel, gbc);
    }
}