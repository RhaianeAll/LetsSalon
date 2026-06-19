package salon.view;

import javax.swing.*;
import java.awt.*;
import salon.util.ThemeUtil;
import salon.view.components.BackgroundPanel;

public class AuthView extends JFrame {

    private CardLayout cardLayout;
    private BackgroundPanel mainContainer; // Usaremos nosso painel customizado com a imagem

    public AuthView() {
        setTitle("Let's Salon - Bem-vindo");
        
        // 1. Comando MÁGICO para abrir em Tela Cheia (Maximizada)
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        // 2. Tamanho mínimo de segurança (se o usuário restaurar a janela, ela não "espreme" os botões)
        setMinimumSize(new Dimension(1024, 768)); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 

        cardLayout = new CardLayout();
        
        // 3. Inicializa o painel com a imagem de fundo (coloque as folhas/grafismos aqui)
        mainContainer = new BackgroundPanel("/images/folhas_fundo.png", cardLayout);

        // Adicionando as telas ao contêiner no AuthView.java
        mainContainer.add(new WelcomePanel(this), "Welcome");
        mainContainer.add(new LoginClientePanel(this), "LoginCliente");
        mainContainer.add(new CadastroClientePanel(this), "CadastroCliente");
        mainContainer.add(new LoginColaboradorPanel(this), "LoginColaborador"); // NOVA
        mainContainer.add(new CadastroColaboradorPanel(this), "CadastroColaborador"); // NOVA
        mainContainer.add(new ConhecerPanel(this), "Conhecer"); // NOVA

        add(mainContainer);
    }

    public void trocarTela(String nomeDaTela) {
        cardLayout.show(mainContainer, nomeDaTela);
    }

    public static void main(String[] args) {
        ThemeUtil.setupTheme();
        SwingUtilities.invokeLater(() -> new AuthView().setVisible(true));
    }
}