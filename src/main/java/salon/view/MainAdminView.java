package salon.view;

import javax.swing.*;
import java.awt.*;

public class MainAdminView extends JFrame {
    
    private JPanel cardsPanel;
    private CardLayout cardLayout;

    public MainAdminView() {
        setTitle("Let's Salon - Dashboard Administrador");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Sidebar
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new GridLayout(8, 1, 0, 10));
        sidebar.setBackground(Color.decode("#F48FB1"));
        sidebar.setPreferredSize(new Dimension(200, 700));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        String[] menus = {"Agendamentos", "Funcionários", "Serviços", "Estoque", "Insumos", "Relatórios", "Perfil"};
        for (String menu : menus) {
            JButton btn = new JButton(menu);
            btn.setForeground(Color.WHITE);
            btn.setBackground(Color.decode("#D81B60"));
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.addActionListener(e -> cardLayout.show(cardsPanel, menu));
            sidebar.add(btn);
        }

        // Cards Panel (Navegação Dinâmica)
        cardLayout = new CardLayout();
        cardsPanel = new JPanel(cardLayout);
        cardsPanel.setBackground(Color.WHITE);

        // Criação de painéis para cada funcionalidade
        cardsPanel.add(createPanel("Gestão de Agendamentos"), "Agendamentos");
        cardsPanel.add(createPanel("Cadastro de Funcionários"), "Funcionários");
        cardsPanel.add(createPanel("Módulo de Relatórios Financeiros"), "Relatórios");

        add(sidebar, BorderLayout.WEST);
        add(cardsPanel, BorderLayout.CENTER);
    }

    private JPanel createPanel(String title) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        JLabel lbl = new JLabel(title, SwingConstants.CENTER);
        lbl.setFont(new Font("Poppins", Font.BOLD, 24));
        panel.add(lbl, BorderLayout.NORTH);
        // Aqui você adicionaria as JTables e formulários respectivos
        return panel;
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