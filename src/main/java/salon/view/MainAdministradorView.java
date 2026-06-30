package salon.view;

import javax.swing.*;
import java.awt.*;
import salon.util.ThemeUtil;
import salon.view.components.BackgroundPanel;
import salon.view.components.RoundedButton;
import salon.view.components.RoundedPanel;

public class MainAdministradorView extends JFrame {

    private String nomeUsuario;
    private String nomeEmpresa;

    public MainAdministradorView(String nomeUsuario, String nomeEmpresa) {
        this.nomeUsuario = nomeUsuario;
        this.nomeEmpresa = nomeEmpresa;

        setTitle("Let's Salon - Painel do Administrador");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Abre maximizado
        setMinimumSize(new Dimension(1024, 768));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Fundo com a marca d'água das folhas
        BackgroundPanel mainBg = new BackgroundPanel("/images/grafico_apoio.png", new BorderLayout());

        // ==========================================
        // 1. NAVBAR SUPERIOR (Rosa)
        // ==========================================
        JPanel navbar = new JPanel(new BorderLayout());
        navbar.setBackground(Color.decode("#F48FB1"));
        navbar.setPreferredSize(new Dimension(0, 70));
        navbar.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));

        // Logo Esquerda
        JLabel lblNavLogo = new JLabel("LET'S SALON 🌿");
        lblNavLogo.setFont(new Font("Segoe UI Black", Font.BOLD, 22));
        lblNavLogo.setForeground(Color.WHITE);

        // Pill Central (Nome do Usuário)
        RoundedPanel pillUser = new RoundedPanel(30, Color.decode("#FF6090"));
        pillUser.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 5));
        JLabel lblNavName = new JLabel(nomeUsuario);
        lblNavName.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblNavName.setForeground(Color.WHITE);
        pillUser.add(lblNavName);
        
        JPanel navCenter = new JPanel(new FlowLayout(FlowLayout.CENTER));
        navCenter.setOpaque(false);
        navCenter.add(pillUser);

        // Nome da Empresa + Botão Sair (Direita)
        JPanel navRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        navRight.setOpaque(false);
        
        JLabel lblStudio = new JLabel(nomeEmpresa);
        lblStudio.setFont(new Font("Segoe UI", Font.ITALIC, 18));
        lblStudio.setForeground(Color.WHITE);

        JButton btnSair = new JButton("🚪Sair");
        btnSair.setContentAreaFilled(false);
        btnSair.setBorderPainted(false);
        btnSair.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSair.setForeground(Color.WHITE);
        btnSair.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSair.addActionListener(e -> {
            this.dispose();
            new SelecaoEmpresaView(nomeUsuario).setVisible(true); // Volta para selecionar empresa
        });

        navRight.add(lblStudio);
        navRight.add(btnSair);

        navbar.add(lblNavLogo, BorderLayout.WEST);
        navbar.add(navCenter, BorderLayout.CENTER);
        navbar.add(navRight, BorderLayout.EAST);

        mainBg.add(navbar, BorderLayout.NORTH);

        // ==========================================
        // 2. CONTEÚDO PRINCIPAL (Meio da Tela)
        // ==========================================
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));

        // 2.1 PAINEL SUPERIOR (Perfil Esquerda | Notificações Direita)
        JPanel topSection = new JPanel(new GridLayout(1, 2, 50, 0));
        topSection.setOpaque(false);
        topSection.setMaximumSize(new Dimension(1200, 180));

        // ---> Perfil do Usuário (Esquerda)
        JPanel profilePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        profilePanel.setOpaque(false);
        
        JLabel lblAvatar = new JLabel("IMG", SwingConstants.CENTER);
        lblAvatar.setPreferredSize(new Dimension(140, 140));
        lblAvatar.setOpaque(true);
        lblAvatar.setBackground(Color.decode("#E0E0E0"));
        // Borda grossa simulando a sombra da imagem do seu print
        lblAvatar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.decode("#D3D3D3"), 4, true),
                BorderFactory.createEmptyBorder()
        ));

        JPanel infoProfile = new JPanel(new GridLayout(2, 1));
        infoProfile.setOpaque(false);
        
        JLabel lblUserName = new JLabel(nomeUsuario);
        lblUserName.setFont(new Font("Segoe UI", Font.BOLD, 20));
        
        JLabel lblUserRole = new JLabel("Administrador(a)");
        lblUserRole.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        
        infoProfile.add(lblUserName);
        infoProfile.add(lblUserRole);

        profilePanel.add(lblAvatar);
        profilePanel.add(infoProfile);
        topSection.add(profilePanel);

        // ---> Notificações (Direita) - VAZIO PARA SISTEMA NOVO
        JPanel notificationsPanel = new JPanel();
        notificationsPanel.setLayout(new BoxLayout(notificationsPanel, BoxLayout.Y_AXIS));
        notificationsPanel.setOpaque(false);

        JLabel lblNotificacoes = new JLabel("Notificações:");
        lblNotificacoes.setFont(new Font("Segoe UI Black", Font.BOLD, 22));
        lblNotificacoes.setForeground(Color.decode("#D81B60"));
        lblNotificacoes.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Card de Notificação Vazia (Sistema novo)
        RoundedPanel cardNotif = new RoundedPanel(30, Color.WHITE);
        cardNotif.setLayout(new BorderLayout());
        cardNotif.setBorder(BorderFactory.createLineBorder(Color.decode("#F48FB1"), 3)); // Borda rosa
        cardNotif.setMaximumSize(new Dimension(400, 60));
        
        JLabel lblMensagem = new JLabel("Nenhuma nova notificação no momento.");
        lblMensagem.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblMensagem.setHorizontalAlignment(SwingConstants.CENTER);
        cardNotif.add(lblMensagem, BorderLayout.CENTER);

        notificationsPanel.add(lblNotificacoes);
        notificationsPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        notificationsPanel.add(cardNotif);

        topSection.add(notificationsPanel);
        
        contentPanel.add(topSection);
        
        // Linha Divisória
        contentPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setForeground(Color.decode("#FFCDD2"));
        separator.setMaximumSize(new Dimension(1200, 2));
        contentPanel.add(separator);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        // ==========================================
        // 3. GRID DE BOTÕES (Menu do Sistema)
        // ==========================================
        JPanel gridContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        gridContainer.setOpaque(false);
        
        JPanel buttonGrid = new JPanel(new GridLayout(3, 2, 40, 30)); // 3 linhas, 2 colunas, espaçamentos
        buttonGrid.setOpaque(false);
        buttonGrid.setPreferredSize(new Dimension(800, 300)); // Limita o tamanho para os botões não ficarem gigantes

        // Nomes dos botões conforme seu design
        String[] botoes = {
            "Agendamentos", "Cadastro de Insumos", 
            "Cadastro de Profissionais", "Estoque", 
            "Cadastro de Serviços", "Relatórios"
        };

        for (String texto : botoes) {
            RoundedButton btn = new RoundedButton(texto);
            btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
            
            // Lógica de navegação ATUALIZADA
            btn.addActionListener(e -> {
                if (texto.equals("Cadastro de Profissionais")) {
                    this.dispose(); 
                    new CadastroFuncionarioView(nomeUsuario, nomeEmpresa).setVisible(true);
                } 
                else if (texto.equals("Cadastro de Serviços")) { 
                    this.dispose(); 
                    new CadastroServicoView(nomeUsuario, nomeEmpresa).setVisible(true);
                }
                else if (texto.equals("Cadastro de Insumos")) { // <-- A NOVA CONEXÃO AQUI!
                    this.dispose(); 
                    new CadastroInsumoView(nomeUsuario, nomeEmpresa).setVisible(true);
                }
                else if (texto.equals("Estoque")) { 
                    this.dispose(); 
                    new EstoqueView(nomeUsuario, nomeEmpresa).setVisible(true);
                }
                else {
                    // Mantém o aviso de "Em construção" para os botões que ainda faltam
                    JOptionPane.showMessageDialog(this, "Navegando para: " + texto + "\n(Em construção)");
                }
            });
            
            buttonGrid.add(btn);
        }

        gridContainer.add(buttonGrid);
        contentPanel.add(gridContainer);
        contentPanel.add(Box.createVerticalGlue()); // Empurra tudo para cima de forma elegante

        mainBg.add(contentPanel, BorderLayout.CENTER);
        add(mainBg);
    }
}