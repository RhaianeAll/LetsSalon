package salon.view;

import javax.swing.*;
import java.awt.*;
import salon.view.components.RoundedButton;

public class WelcomePanel extends JPanel {

    public WelcomePanel(AuthView parentFrame) {
        setOpaque(false); // DEIXA TRANSPARENTE PARA A MARCA D'ÁGUA APARECER NO FUNDO
        setLayout(new GridBagLayout()); // O Segredo para centralizar tudo em tela cheia!

        // Criamos um painel interno para segurar o conteúdo
        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        // Logo
        JLabel lblLogo = new JLabel("Let's Salon");
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 64)); // Aumentei a fonte para tela cheia
        lblLogo.setForeground(Color.decode("#F48FB1"));
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSub = new JLabel("Seu sistema de controle para seu studio de beleza");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 16)); // Aumentei um pouco também
        lblSub.setForeground(Color.GRAY);
        lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Botões
        RoundedButton btnCliente = new RoundedButton("SOU CLIENTE");
        configurarBotao(btnCliente);
        btnCliente.addActionListener(e -> parentFrame.trocarTela("LoginCliente"));

        RoundedButton btnColaborador = new RoundedButton("SOU COLABORADOR");
        configurarBotao(btnColaborador);
        btnColaborador.addActionListener(e -> parentFrame.trocarTela("LoginColaborador")); // AGORA FUNCIONA

        RoundedButton btnConhecer = new RoundedButton("QUERO CONHECER");
        configurarBotao(btnConhecer);
        btnConhecer.addActionListener(e -> parentFrame.trocarTela("Conhecer")); // AGORA FUNCIONA

        // Montagem
        contentPanel.add(lblLogo);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPanel.add(lblSub);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 80))); // Mais respiro
        
        contentPanel.add(criarSecao("Clique aqui se deseja conhecer os prestadores", btnCliente));
        contentPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        contentPanel.add(criarSecao("Clique aqui se você tem uma empresa cadastrada.", btnColaborador));
        contentPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        contentPanel.add(criarSecao("Clique aqui se tem interesse em conhecer nossos serviços", btnConhecer));

        // Adiciona o conteúdo perfeitamente no centro do GridBagLayout
        add(contentPanel, new GridBagConstraints());
    }

    private void configurarBotao(RoundedButton btn) {
        btn.setMaximumSize(new Dimension(280, 50)); // Botões ligeiramente maiores
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private JPanel criarSecao(String texto, RoundedButton btn) {
        JPanel pnl = new JPanel();
        pnl.setOpaque(false); // Transparente também
        pnl.setLayout(new BoxLayout(pnl, BoxLayout.Y_AXIS));
        
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        pnl.add(lbl);
        pnl.add(Box.createRigidArea(new Dimension(0, 8)));
        pnl.add(btn);
        return pnl;
    }
}