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
}