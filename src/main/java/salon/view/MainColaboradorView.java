package salon.view;

import javax.swing.*;
import java.awt.*;
import salon.util.ThemeUtil;

public class MainColaboradorView extends JFrame {

    private CardLayout cardLayout;
    private JPanel contentPanel;

    public MainColaboradorView() {
        setTitle("Let's Salon - Painel do Colaborador");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Abre em tela cheia
        setMinimumSize(new Dimension(1024, 768));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.decode("#FAFAFA")); // Fundo cinza super claro para destacar os cards brancos

        // ==========================================
        // 1. SIDEBAR (MENU LATERAL)
        // ==========================================
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BorderLayout());
        sidebar.setBackground(Color.WHITE);
        sidebar.setPreferredSize(new Dimension(280, 0));
        // Linha sutil separando o menu do conteúdo
        sidebar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.decode("#E0E0E0")));

        // 1.1 Perfil do Usuário no Topo do Menu
        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));
        profilePanel.setBackground(Color.WHITE);
        profilePanel.setBorder(BorderFactory.createEmptyBorder(40, 20, 30, 20));

        // Letra inicial no lugar da foto (até implementarmos o upload de imagem)
        JLabel lblAvatar = new JLabel("C", SwingConstants.CENTER);
        lblAvatar.setFont(new Font("Segoe UI", Font.BOLD, 40));
        lblAvatar.setForeground(Color.WHITE);
        lblAvatar.setOpaque(true);
        lblAvatar.setBackground(Color.decode("#F48FB1"));
        lblAvatar.setPreferredSize(new Dimension(80, 80));
        lblAvatar.setMaximumSize(new Dimension(80, 80));
        lblAvatar.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Arredondando o ícone usando borda
        lblAvatar.setBorder(BorderFactory.createEmptyBorder()); 

        JLabel lblNome = new JLabel("Colaborador Padrão");
        lblNome.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblNome.setForeground(Color.decode("#D81B60"));
        lblNome.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblCargo = new JLabel("Colaborador(a)");
        lblCargo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblCargo.setForeground(Color.GRAY);
        lblCargo.setAlignmentX(Component.CENTER_ALIGNMENT);

        profilePanel.add(lblAvatar);
        profilePanel.add(Box.createRigidArea(new Dimension(0, 15)));
        profilePanel.add(lblNome);
        profilePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        profilePanel.add(lblCargo);

        sidebar.add(profilePanel, BorderLayout.NORTH);

        // 1.2 Botões de Navegação
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(Color.WHITE);
        menuPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        // Adicionamos os botões do menu
        menuPanel.add(criarBotaoMenu("📅 Meus Agendamentos", "Agendamentos"));
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        menuPanel.add(criarBotaoMenu("⚙️ Meu Perfil", "Perfil"));

        sidebar.add(menuPanel, BorderLayout.CENTER);

        // 1.3 Botão de Sair no Rodapé
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setBackground(Color.WHITE);
        footerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        
        JButton btnSair = new JButton("🚪 Sair do Sistema");
        btnSair.setContentAreaFilled(false);
        btnSair.setBorderPainted(false);
        btnSair.setForeground(Color.decode("#B71C1C"));
        btnSair.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSair.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Ação de Logout
        btnSair.addActionListener(e -> {
            this.dispose(); // Fecha o Dashboard
            new AuthView().setVisible(true); // Abre o Login novamente
        });
        
        footerPanel.add(btnSair);
        sidebar.add(footerPanel, BorderLayout.SOUTH);

        add(sidebar, BorderLayout.WEST);

        // ==========================================
        // 2. ÁREA DE CONTEÚDO (DIREITA)
        // ==========================================
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(Color.decode("#FAFAFA"));

        // Adicionamos as telas (Criaremos a tela de agendamentos logo abaixo)
        contentPanel.add(new ColaboradorAgendamentosPanel(), "Agendamentos");
        
        // Exemplo de uma tela em branco para o perfil (Faremos depois)
        JPanel perfilPlaceholder = new JPanel();
        perfilPlaceholder.setBackground(Color.decode("#FAFAFA"));
        contentPanel.add(perfilPlaceholder, "Perfil");

        add(contentPanel, BorderLayout.CENTER);
    }

    // Método que cria um botão de menu com efeito Hover
    private JButton criarBotaoMenu(String texto, String nomeCard) {
        JButton btn = new JButton(texto);
        btn.setPreferredSize(new Dimension(240, 50));
        btn.setMaximumSize(new Dimension(240, 50));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setForeground(Color.decode("#555555"));
        btn.setBackground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Efeito mágico de Hover (Muda de cor ao passar o mouse)
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(Color.decode("#FFCDD2"));
                btn.setForeground(Color.decode("#D81B60"));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(Color.WHITE);
                btn.setForeground(Color.decode("#555555"));
            }
        });

        // Quando clica, troca o card da direita
        btn.addActionListener(e -> cardLayout.show(contentPanel, nomeCard));
        return btn;
    }

    public static void main(String[] args) {
        ThemeUtil.setupTheme();
        SwingUtilities.invokeLater(() -> new MainColaboradorView().setVisible(true));
    }
}