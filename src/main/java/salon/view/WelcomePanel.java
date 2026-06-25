package salon.view;

import javax.swing.*;
import java.awt.*;
import salon.view.components.RoundedButton;

public class WelcomePanel extends JPanel {

    public WelcomePanel(AuthView parentFrame) {
        setOpaque(false);
        setLayout(new BorderLayout()); // MUDANÇA: Igualamos o layout com as outras telas

        // ==========================================
        // 1. TOPO INVISÍVEL (Mantém a logo na mesma altura das outras telas)
        // ==========================================
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        topPanel.setOpaque(false);
        
        JButton fakeButton = new JButton("← Voltar");
        fakeButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        fakeButton.setVisible(false); // Fica invisível, mas ocupa o mesmo espaço e altura!
        topPanel.add(fakeButton);
        
        add(topPanel, BorderLayout.NORTH);

        // ==========================================
        // 2. CENTRO DIVIDIDO E CONGELADO
        // ==========================================
        JPanel centerContainer = new JPanel(new GridBagLayout());
        centerContainer.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;

        // LADO ESQUERDO: LOGO
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setOpaque(false);
        leftPanel.add(criarLogo(850, -1)); 
        
        // O SEGREDO: Força o Java a usar estritamente a porcentagem (55%), ignorando o tamanho interno
        leftPanel.setPreferredSize(new Dimension(0, 0)); 

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.55; 
        centerContainer.add(leftPanel, gbc);

        // LADO DIREITO: BOTÕES
        JPanel rightPanel = new JPanel();
        rightPanel.setOpaque(false);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        RoundedButton btnCliente = new RoundedButton("SOU CLIENTE");
        configurarBotao(btnCliente);
        btnCliente.addActionListener(e -> parentFrame.trocarTela("LoginCliente"));

        RoundedButton btnColaborador = new RoundedButton("SOU COLABORADOR");
        configurarBotao(btnColaborador);
        btnColaborador.addActionListener(e -> parentFrame.trocarTela("LoginColaborador"));

        RoundedButton btnConhecer = new RoundedButton("QUERO CONHECER");
        configurarBotao(btnConhecer);
        btnConhecer.addActionListener(e -> parentFrame.trocarTela("Conhecer"));

        rightPanel.add(Box.createVerticalGlue()); 
        rightPanel.add(criarSecao("Clique aqui se deseja conhecer os prestadores serviço", btnCliente));
        rightPanel.add(Box.createRigidArea(new Dimension(0, 35))); 
        rightPanel.add(criarSecao("Clique aqui se você tem uma empresa cadastrada.", btnColaborador));
        rightPanel.add(Box.createRigidArea(new Dimension(0, 35)));
        rightPanel.add(criarSecao("Clique aqui se tem interesse em conhecer nossos serviços", btnConhecer));
        rightPanel.add(Box.createVerticalGlue());

        JPanel rightContainer = new JPanel(new GridBagLayout());
        rightContainer.setOpaque(false);
        rightContainer.add(rightPanel);
        
        // O SEGREDO 2: Força o lado direito a respeitar estritamente os (45%)
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

    private void configurarBotao(RoundedButton btn) {
        btn.setPreferredSize(new Dimension(380, 55)); 
        btn.setMaximumSize(new Dimension(380, 55));
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 20)); 
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private JPanel criarSecao(String texto, RoundedButton btn) {
        JPanel pnl = new JPanel();
        pnl.setOpaque(false);
        pnl.setLayout(new BoxLayout(pnl, BoxLayout.Y_AXIS));
        
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl.setForeground(Color.decode("#B71C1C")); 
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        pnl.add(lbl);
        pnl.add(Box.createRigidArea(new Dimension(0, 8))); 
        pnl.add(btn);
        return pnl;
    }
}