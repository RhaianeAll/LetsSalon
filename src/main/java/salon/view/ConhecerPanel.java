package salon.view;

import javax.swing.*;
import java.awt.*;
import salon.view.components.RoundedButton;
import salon.view.components.RoundedPanel;

public class ConhecerPanel extends JPanel {

    public ConhecerPanel(AuthView parentFrame) {
        setOpaque(false); // Transparente para ver a marca d'água do fundo
        setLayout(new GridBagLayout());

        // Card com fundo transparente ou branco com leve opacidade (aqui usaremos branco sólido)
        RoundedPanel cardPanel = new RoundedPanel(30, Color.WHITE);
        cardPanel.setPreferredSize(new Dimension(500, 450));
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        JLabel lblTitle = new JLabel("Quem Somos");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(Color.decode("#D81B60"));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Uso de HTML no JLabel para fazer a quebra de linha automática (Word Wrap) igual ao PDF
        String texto = "<html><div style='text-align: center; color: #555555;'>"
                + "O sistema Let's Salon surgiu com o intuito de resolver a "
                + "problemática que muitos sistemas de controle para "
                + "empresas voltada a área de beleza tem, como a "
                + "exemplo mau funcionamento e falta de controle.<br><br>"
                + "O sistema Let's Salon é completo e compreende vários "
                + "estilos de negócios, possui ferramentas personalizadas "
                + "baseado em negócios reais, além de um ótimo sistema "
                + "de controle financeiro com disponibilidade de planilhas "
                + "de controle.<br><br><br>"
                + "<b style='color: #D81B60;'>Se interessou? Se inscreva e seja nosso colaborador!</b>"
                + "</div></html>";

        JLabel lblTexto = new JLabel(texto);
        lblTexto.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblTexto.setAlignmentX(Component.CENTER_ALIGNMENT);

        RoundedButton btnDesejoSer = new RoundedButton("DESEJO SER UM COLABORADOR");
        btnDesejoSer.setMaximumSize(new Dimension(280, 45));
        btnDesejoSer.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnDesejoSer.addActionListener(e -> parentFrame.trocarTela("CadastroColaborador"));

        cardPanel.add(lblTitle);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        cardPanel.add(lblTexto);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        cardPanel.add(btnDesejoSer);

        // Botão voltar com a Opção 1 (Texto Rosa)
        JButton btnVoltar = new JButton("← Voltar para a Tela Inicial");
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