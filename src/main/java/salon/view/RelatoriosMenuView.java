package salon.view;

import javax.swing.*;
import java.awt.*;
import salon.util.ThemeUtil;
import salon.view.components.BackgroundPanel;
import salon.view.components.RoundedButton;
import salon.view.components.RoundedPanel;

public class RelatoriosMenuView extends JFrame {

    private String nomeUsuario;
    private String nomeEmpresa;

    public RelatoriosMenuView(String nomeUsuario, String nomeEmpresa) {
        this.nomeUsuario = nomeUsuario;
        this.nomeEmpresa = nomeEmpresa;

        setTitle("Let's Salon - Relatórios");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        BackgroundPanel mainBg = new BackgroundPanel("/images/grafico_apoio.png", new BorderLayout());
        setContentPane(mainBg);

        // --- NAVBAR ---
        JPanel navbar = new JPanel(new BorderLayout());
        navbar.setBackground(Color.decode("#F48FB1"));
        navbar.setPreferredSize(new Dimension(0, 70));
        navbar.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));

        JLabel lblNavLogo = new JLabel("LET'S SALON 🌿");
        lblNavLogo.setFont(new Font("Segoe UI Black", Font.BOLD, 22)); lblNavLogo.setForeground(Color.WHITE);
        
        RoundedPanel pillUser = new RoundedPanel(30, Color.decode("#FF6090"));
        pillUser.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 5));
        JLabel lblNavName = new JLabel(nomeUsuario); lblNavName.setFont(new Font("Segoe UI", Font.BOLD, 16)); lblNavName.setForeground(Color.WHITE);
        pillUser.add(lblNavName);
        
        JPanel navCenter = new JPanel(new FlowLayout(FlowLayout.CENTER)); navCenter.setOpaque(false); navCenter.add(pillUser);
        JPanel navRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0)); navRight.setOpaque(false);
        JLabel lblStudio = new JLabel(nomeEmpresa); lblStudio.setFont(new Font("Segoe UI", Font.ITALIC, 18)); lblStudio.setForeground(Color.WHITE);
        
        JButton btnVoltar = new JButton("↩ Voltar"); btnVoltar.setContentAreaFilled(false); btnVoltar.setBorderPainted(false);
        btnVoltar.setFont(new Font("Segoe UI", Font.BOLD, 14)); btnVoltar.setForeground(Color.WHITE); btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVoltar.addActionListener(e -> { this.dispose(); new MainAdministradorView(nomeUsuario, nomeEmpresa).setVisible(true); });
        
        navRight.add(lblStudio); navRight.add(btnVoltar);
        navbar.add(lblNavLogo, BorderLayout.WEST); navbar.add(navCenter, BorderLayout.CENTER); navbar.add(navRight, BorderLayout.EAST);
        mainBg.add(navbar, BorderLayout.NORTH);

        // --- CONTEÚDO ---
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        JLabel lblTitle = new JLabel("Relatórios");
        lblTitle.setFont(new Font("Segoe UI Black", Font.BOLD, 48)); lblTitle.setForeground(Color.decode("#FF6090"));
        JPanel titleWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0)); titleWrapper.setOpaque(false); titleWrapper.setAlignmentX(Component.LEFT_ALIGNMENT);
        titleWrapper.add(lblTitle); contentPanel.add(titleWrapper); contentPanel.add(Box.createRigidArea(new Dimension(0, 50)));

        // --- PAINEL DE BOTÕES ---
        JPanel botoesPanel = new JPanel(new GridLayout(4, 1, 0, 20));
        botoesPanel.setOpaque(false);
        botoesPanel.setMaximumSize(new Dimension(400, 300));
        botoesPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        String[] relatorios = {"Relatório Geral", "Relatório Parcial", "Registro de Atendimentos", "Relatório Financeiro"};
        
        for (String titulo : relatorios) {
            RoundedButton btn = new RoundedButton(titulo);
            btn.setFont(new Font("Segoe UI Black", Font.BOLD, 16));
            btn.setPreferredSize(new Dimension(300, 50));
            
            btn.addActionListener(e -> {
                if (titulo.equals("Relatório Geral")) {
                    this.dispose(); new RelatorioGeralView(nomeUsuario, nomeEmpresa).setVisible(true);
                } else if (titulo.equals("Relatório Parcial")) {
                    this.dispose(); new RelatorioParcialView(nomeUsuario, nomeEmpresa).setVisible(true);
                } else if (titulo.equals("Registro de Atendimentos")) {
                    this.dispose(); new RelatorioAtendimentosView(nomeUsuario, nomeEmpresa).setVisible(true);
                } else if (titulo.equals("Relatório Financeiro")) {
                    this.dispose(); new RelatorioFinanceiroView(nomeUsuario, nomeEmpresa).setVisible(true);
                }
            });
            
            botoesPanel.add(btn);
        }

        contentPanel.add(botoesPanel);
        mainBg.add(contentPanel, BorderLayout.CENTER);
    }
}