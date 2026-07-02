package salon.view;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import salon.controller.UsuarioController;
import salon.view.components.RoundedButton;
import salon.view.components.RoundedPanel;

public class CadastroClientePanel extends JPanel {

    private UsuarioController usuarioController;

    public CadastroClientePanel(AuthView parentFrame) {
        this.usuarioController = new UsuarioController();
        setOpaque(false);
        setLayout(new BorderLayout());

        // ==========================================
        // 1. TOPO: BOTÃO VOLTAR
        // ==========================================
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        topPanel.setOpaque(false);
        JButton btnVoltar = new JButton("← Voltar");
        btnVoltar.setContentAreaFilled(false);
        btnVoltar.setBorderPainted(false);
        btnVoltar.setForeground(Color.decode("#D81B60"));
        btnVoltar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVoltar.addActionListener(e -> parentFrame.trocarTela("LoginCliente"));
        topPanel.add(btnVoltar);
        add(topPanel, BorderLayout.NORTH);

        // ==========================================
        // 2. CENTRO: LAYOUT DIVIDIDO
        // ==========================================
        JPanel centerContainer = new JPanel(new GridBagLayout());
        centerContainer.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH; gbc.weighty = 1.0;

        // LADO ESQUERDO: LOGO (Métrica Padrão de 850px)
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setOpaque(false);
        leftPanel.add(criarLogo(850, -1)); 
        leftPanel.setPreferredSize(new Dimension(0, 0)); // Trava a divisão da tela
        
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.55;
        centerContainer.add(leftPanel, gbc);

        // LADO DIREITO: FORMULÁRIO E TÍTULO
        JPanel rightPanel = new JPanel();
        rightPanel.setOpaque(false);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        JLabel lblTitle = new JLabel("INSCREVA-SE");
        lblTitle.setFont(new Font("Segoe UI Black", Font.BOLD, 36));
        lblTitle.setForeground(Color.decode("#F48FB1"));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // CARD ROSA (Um pouco mais baixo que o do Colaborador, pois tem menos campos)
        RoundedPanel cardPanel = new RoundedPanel(30, Color.decode("#FFCDD2"));
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));
        
        Dimension cardSize = new Dimension(420, 580);
        cardPanel.setPreferredSize(cardSize);
        cardPanel.setMaximumSize(cardSize);
        cardPanel.setMinimumSize(cardSize);

        // TAMANHO PADRÃO DOS CAMPOS (Respiro de 60px de altura)
        Dimension fieldSize = new Dimension(360, 60);

        // --- LINHA 1: NOME E CPF (LADO A LADO) ---
        JPanel row1 = new JPanel(new GridLayout(1, 2, 10, 0)); 
        row1.setOpaque(false);
        row1.setPreferredSize(fieldSize);
        row1.setMaximumSize(fieldSize);
        row1.setMinimumSize(fieldSize); 
        
        JTextField txtNome = new JTextField();
        txtNome.setBorder(BorderFactory.createTitledBorder("Nome Completo:"));
        
        // MÁSCARA DE CPF APLICADA
        JFormattedTextField txtCpf = new JFormattedTextField(criarMascara("###.###.###-##"));
        txtCpf.setBorder(BorderFactory.createTitledBorder("CPF:"));
        
        row1.add(txtNome);
        row1.add(txtCpf);

        // --- DEMAIS CAMPOS EMPILHADOS ---
        // MÁSCARA DE TELEFONE APLICADA
        JFormattedTextField txtTelefone = new JFormattedTextField(criarMascara("(##) #####-####"));
        txtTelefone.setBorder(BorderFactory.createTitledBorder("Número de Telefone (Whatsapp):"));
        
        JTextField txtUsuario = new JTextField();
        txtUsuario.setBorder(BorderFactory.createTitledBorder("Nome de Usuário:"));
        
        JPasswordField txtSenha = new JPasswordField();
        txtSenha.setBorder(BorderFactory.createTitledBorder("Senha:"));
        
        JPasswordField txtConfirmaSenha = new JPasswordField();
        txtConfirmaSenha.setBorder(BorderFactory.createTitledBorder("Confirme a Senha:"));

        // Montando as linhas no Card
        cardPanel.add(row1);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 10))); 

        Component[] campos = {txtTelefone, txtUsuario, txtSenha, txtConfirmaSenha};
        for (Component c : campos) {
            JComponent jc = (JComponent) c;
            jc.setPreferredSize(fieldSize);
            jc.setMaximumSize(fieldSize);
            jc.setMinimumSize(fieldSize); // Trava o tamanho anti-achatamento
            cardPanel.add(jc);
            cardPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        RoundedButton btnInscrever = new RoundedButton("INSCREVER-SE");
        btnInscrever.setPreferredSize(new Dimension(180, 45));
        btnInscrever.setMaximumSize(new Dimension(180, 45));
        btnInscrever.setMinimumSize(new Dimension(180, 45));
        btnInscrever.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Ação de Cadastro no Banco de Dados
        btnInscrever.addActionListener(e -> {
            try {
                // Pegue os textos dos campos da sua tela (substitua pelos nomes reais das suas variáveis)
                String nome = txtNome.getText();
                String cpf = txtCpf.getText();
                String telefone = txtTelefone.getText();
                String usuario = txtUsuario.getText();
                String senha = new String(txtSenha.getPassword());
                String confirmaSenha = new String(txtConfirmaSenha.getPassword());

                // Chama o Controller corrigido
                UsuarioController controller = new UsuarioController();
                controller.cadastrarCliente(nome, cpf, telefone, usuario, senha, confirmaSenha);

                JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso! Bem-vindo!");

                // Aqui você pode limpar os campos ou fechar a tela

            } catch (IllegalArgumentException ex) {
                // Erros de campos vazios ou senhas que não batem
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Atenção", JOptionPane.WARNING_MESSAGE);
            } catch (Exception ex) {
                // ERRO REAL DO BANCO MOSTRADO AQUI (em vez daquela mensagem genérica)
                JOptionPane.showMessageDialog(this, "Erro Crítico: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
}
        });

        cardPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        cardPanel.add(btnInscrever);

        // Montagem final da Coluna Direita
        rightPanel.add(Box.createVerticalGlue());
        rightPanel.add(lblTitle);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        rightPanel.add(cardPanel);
        rightPanel.add(Box.createVerticalGlue());

        JPanel rightContainer = new JPanel(new GridBagLayout());
        rightContainer.setOpaque(false);
        rightContainer.add(rightPanel);
        rightContainer.setPreferredSize(new Dimension(0, 0)); // Trava a divisão

        gbc.gridx = 1; gbc.weightx = 0.45;
        centerContainer.add(rightContainer, gbc);

        add(centerContainer, BorderLayout.CENTER);
    }

    // ==========================================
    // MÉTODOS UTILITÁRIOS
    // ==========================================
    
    private MaskFormatter criarMascara(String formato) {
        try {
            MaskFormatter mascara = new MaskFormatter(formato);
            mascara.setPlaceholderCharacter('_');
            return mascara;
        } catch (Exception e) {
            System.err.println("Erro ao criar máscara: " + e.getMessage());
            return null;
        }
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
}