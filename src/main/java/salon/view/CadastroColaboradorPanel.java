package salon.view;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import salon.controller.FuncionarioController;
import salon.view.components.RoundedButton;
import salon.view.components.RoundedPanel;

public class CadastroColaboradorPanel extends JPanel {

    private FuncionarioController funcionarioController;

    public CadastroColaboradorPanel(AuthView parentFrame) {
        this.funcionarioController = new FuncionarioController();
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
        btnVoltar.addActionListener(e -> parentFrame.trocarTela("LoginColaborador"));
        topPanel.add(btnVoltar);
        add(topPanel, BorderLayout.NORTH);

        // ==========================================
        // 2. CENTRO: LAYOUT DIVIDIDO
        // ==========================================
        JPanel centerContainer = new JPanel(new GridBagLayout());
        centerContainer.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH; gbc.weighty = 1.0;

        // LADO ESQUERDO: LOGO 
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

        // CARD ROSA COM PROTEÇÃO ANTI-ACHATAMENTO
        RoundedPanel cardPanel = new RoundedPanel(30, Color.decode("#FFCDD2"));
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));
        
        Dimension cardSize = new Dimension(420, 700);
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
        
        // APLICANDO MÁSCARA NO CPF
        JFormattedTextField txtCpf = new JFormattedTextField(criarMascara("###.###.###-##"));
        txtCpf.setBorder(BorderFactory.createTitledBorder("CPF:"));
        
        row1.add(txtNome);
        row1.add(txtCpf);

        // --- DEMAIS CAMPOS ---
        // APLICANDO MÁSCARA NO TELEFONE
        JFormattedTextField txtTelefone = new JFormattedTextField(criarMascara("(##) #####-####"));
        txtTelefone.setBorder(BorderFactory.createTitledBorder("Número de Telefone (Whatsapp):"));
        
        JTextField txtEmpresa = new JTextField();
        txtEmpresa.setBorder(BorderFactory.createTitledBorder("Nome da Empresa:"));
        
        JTextField txtCargo = new JTextField();
        txtCargo.setBorder(BorderFactory.createTitledBorder("Cargo:"));
        
        JTextField txtUsuario = new JTextField();
        txtUsuario.setBorder(BorderFactory.createTitledBorder("Nome de Usuário:"));
        
        JPasswordField txtSenha = new JPasswordField();
        txtSenha.setBorder(BorderFactory.createTitledBorder("Senha:"));
        
        JPasswordField txtConfirmaSenha = new JPasswordField();
        txtConfirmaSenha.setBorder(BorderFactory.createTitledBorder("Confirme a Senha:"));

        // Montando as linhas no Card
        cardPanel.add(row1);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 10))); 

        Component[] campos = {txtTelefone, txtEmpresa, txtCargo, txtUsuario, txtSenha, txtConfirmaSenha};
        for (Component c : campos) {
            JComponent jc = (JComponent) c;
            jc.setPreferredSize(fieldSize);
            jc.setMaximumSize(fieldSize);
            jc.setMinimumSize(fieldSize); // Trava o tamanho
            cardPanel.add(jc);
            cardPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        RoundedButton btnInscrever = new RoundedButton("INSCREVER-SE");
        btnInscrever.setPreferredSize(new Dimension(180, 45));
        btnInscrever.setMaximumSize(new Dimension(180, 45));
        btnInscrever.setMinimumSize(new Dimension(180, 45));
        btnInscrever.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        btnInscrever.addActionListener(e -> {
            // O getText() do JFormattedTextField já vem com a máscara (ex: 123.456.789-00)
            JOptionPane.showMessageDialog(this, "Cadastro finalizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            parentFrame.trocarTela("LoginColaborador");
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
    
    // Cria a máscara para o JFormattedTextField
    private MaskFormatter criarMascara(String formato) {
        try {
            MaskFormatter mascara = new MaskFormatter(formato);
            mascara.setPlaceholderCharacter('_'); // Exibe o underline onde o usuário deve digitar
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