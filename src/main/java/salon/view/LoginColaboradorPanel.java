package salon.view;

import javax.swing.*;
import java.awt.*;
import salon.view.components.RoundedButton;
import salon.view.components.RoundedPanel;

public class LoginColaboradorPanel extends JPanel {

    public LoginColaboradorPanel(AuthView parentFrame) {
        setOpaque(false);
        setLayout(new GridBagLayout());

        RoundedPanel cardPanel = new RoundedPanel(30, Color.decode("#FFCDD2"));
        cardPanel.setPreferredSize(new Dimension(350, 460));
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBorder(BorderFactory.createEmptyBorder(40, 30, 40, 30));

        JLabel lblTitle = new JLabel("LOGIN COLABORADOR");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(Color.decode("#D81B60"));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField txtCodigo = new JTextField();
        txtCodigo.setBorder(BorderFactory.createTitledBorder("Código da Empresa:"));
        txtCodigo.setMaximumSize(new Dimension(300, 50));

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
        btnInscrever.addActionListener(e -> parentFrame.trocarTela("CadastroColaborador"));

        // Montagem do Card
        cardPanel.add(lblTitle);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        cardPanel.add(txtCodigo);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        cardPanel.add(txtUsuario);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        cardPanel.add(txtSenha);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        cardPanel.add(btnEntrar);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        JLabel lblNovaConta = new JLabel("Não tem uma conta ainda?");
        lblNovaConta.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.add(lblNovaConta);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        cardPanel.add(btnInscrever);

        // Botão voltar com a Opção 1
        JButton btnVoltar = new JButton("← Voltar");
        btnVoltar.setContentAreaFilled(false);
        btnVoltar.setBorderPainted(false);
        btnVoltar.setForeground(Color.decode("#D81B60"));
        btnVoltar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVoltar.addActionListener(e -> parentFrame.trocarTela("Welcome"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0;
        add(btnVoltar, gbc);
        
        gbc.gridy = 1;
        add(cardPanel, gbc);
    }
}