package salon.view;

import javax.swing.*;
import java.awt.*;
import salon.view.components.RoundedButton;
import salon.view.components.RoundedPanel;

public class ConhecerPanel extends JPanel {

    public ConhecerPanel(AuthView parentFrame) {
        setOpaque(false); // Transparente para ver a marca d'água das folhas no fundo
        setLayout(new BorderLayout()); // Garante que o Topo e o Centro sejam isolados

        // ==========================================
        // 1. PAINEL DO TOPO (Botão Voltar fixo)
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
        add(topPanel, BorderLayout.NORTH); // Prende no topo da tela

        // ==========================================
        // 2. CONTEÚDO CENTRALIZADO (Título, Card e Botão)
        // ==========================================
        // Este painel usa GridBagLayout vazio apenas para "puxar" o conteúdo para o exato meio da tela
        JPanel centerContainer = new JPanel(new GridBagLayout()); 
        centerContainer.setOpaque(false);

        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        // Título "Quem Somos"
        JLabel lblTitle = new JLabel("Quem Somos");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 36)); 
        lblTitle.setForeground(Color.decode("#D81B60")); 
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(lblTitle);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 30))); // Espaçamento maior entre o título e o card

        // Card com fundo Off-White
        RoundedPanel cardPanel = new RoundedPanel(30, Color.decode("#F5F5F5"));
        cardPanel.setLayout(new BorderLayout());
        cardPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        
        // Mantemos o tamanho padrão do card
        cardPanel.setPreferredSize(new Dimension(650, 380));
        cardPanel.setMaximumSize(new Dimension(650, 380)); 

        // O HTML agora está com 'text-align: center' para centralizar o texto dentro do card
        String texto = "<html><div style='color: #B71C1C; font-family: Segoe UI; font-size: 14px; text-align: center;'>"
                + "O sistema Let's Salon surgiu com o intuito de resolver a<br>"
                + "problemática que muitos sistemas de controle para<br>"
                + "empresas voltada a área de beleza tem, como a<br>"
                + "exemplo mau funcionamento e falta de controle.<br><br>"
                + "O sistema Let's Salon é completo e compreende vários<br>"
                + "estilos de negócios, possui ferramentas personalizadas<br>"
                + "baseado em negócios reais, além de um ótimo sistema<br>"
                + "de controle financeiro com disponibilidade de planilhas<br>"
                + "de controle.<br><br>"
                + "<b>Se interessou? Se increva e seja nosso colaborador!</b>"
                + "</div></html>";

        JLabel lblTexto = new JLabel(texto);
        lblTexto.setVerticalAlignment(SwingConstants.CENTER); // Garante a centralização vertical no card
        lblTexto.setHorizontalAlignment(SwingConstants.CENTER); // Garante a centralização horizontal no card
        
        cardPanel.add(lblTexto, BorderLayout.CENTER);
        contentPanel.add(cardPanel);
        
        contentPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Texto explicativo pequeno
        JLabel lblSub = new JLabel("Clique aqui se desejar fazer a inscrição e gerenciar seu negócio.");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSub.setForeground(Color.decode("#B71C1C")); 
        lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(lblSub);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Botão de Ação Rosa
        RoundedButton btnDesejoSer = new RoundedButton("DESEJO SER UM COLABORADOR");
        btnDesejoSer.setMaximumSize(new Dimension(400, 50));
        btnDesejoSer.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btnDesejoSer.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnDesejoSer.addActionListener(e -> parentFrame.trocarTela("CadastroColaborador"));
        contentPanel.add(btnDesejoSer);

        // Adiciona a "coluna" de conteúdo dentro da "âncora" que centraliza no meio da tela
        centerContainer.add(contentPanel); 
        
        add(centerContainer, BorderLayout.CENTER); // Preenche todo o resto da tela
    }
}