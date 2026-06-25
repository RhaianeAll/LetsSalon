package salon.view;

import javax.swing.*;
import java.awt.*;
import salon.view.components.RoundedButton;
import salon.util.ThemeUtil;

public class LoginView extends JFrame {

    public LoginView() {
        setTitle("Let's Salon - Login");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        mainPanel.setBackground(Color.decode("#FFCDD2")); // Fundo rosa claro do card

        JLabel lblTitle = new JLabel("Let's Salon");
        lblTitle.setFont(new Font("Poppins", Font.BOLD, 32));
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

        mainPanel.add(lblTitle);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        mainPanel.add(txtUsuario);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(txtSenha);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        mainPanel.add(btnEntrar);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(new JLabel("Não tem uma conta ainda?"));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(btnInscrever);

        add(mainPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        ThemeUtil.setupTheme();
        SwingUtilities.invokeLater(() -> new LoginView().setVisible(true));
    }
    
    private JLabel criarLogo(int largura, int altura) {
    JLabel lblLogo = new JLabel();
    try {
        // Busca a imagem na pasta resources
        java.net.URL imgURL = getClass().getResource("/images/logo.png");
        
        if (imgURL != null) {
            ImageIcon icon = new ImageIcon(imgURL);
            // Redimensiona a imagem para os tamanhos passados por parâmetro
            Image img = icon.getImage().getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(img));
        } else {
            // Fallback de segurança: se a imagem sumir, exibe o texto
            lblLogo.setText("Let's Salon");
            lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 24));
            lblLogo.setForeground(Color.decode("#F48FB1"));
        }
    } catch (Exception e) {
        System.err.println("Erro ao carregar a logo: " + e.getMessage());
    }
    lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
    return lblLogo;
    }
}