package salon.view;

import javax.swing.*;
import java.awt.*;
import salon.controller.UsuarioController;
import salon.model.Usuario;
import salon.view.components.RoundedButton;
import salon.view.components.RoundedPanel;

public class LoginColaboradorPanel extends JPanel {

    public LoginColaboradorPanel(AuthView parentFrame) {
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
        btnVoltar.addActionListener(e -> parentFrame.trocarTela("Welcome"));
        topPanel.add(btnVoltar);
        add(topPanel, BorderLayout.NORTH);

        // ==========================================
        // 2. CENTRO: LAYOUT DIVIDIDO (Logo | Form)
        // ==========================================
        JPanel centerContainer = new JPanel(new GridBagLayout());
        centerContainer.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;

        // LADO ESQUERDO: LOGO (Métrica Padrão: 850px)
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

        JLabel lblTitle = new JLabel("LOGIN");
        lblTitle.setFont(new Font("Segoe UI Black", Font.BOLD, 42)); 
        lblTitle.setForeground(Color.decode("#F48FB1"));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // CARD ROSA
        RoundedPanel cardPanel = new RoundedPanel(30, Color.decode("#FFCDD2"));
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        // Travando o tamanho do Card para ele não esmagar os campos
        Dimension cardSize = new Dimension(380, 480);
        cardPanel.setPreferredSize(cardSize);
        cardPanel.setMaximumSize(cardSize);
        cardPanel.setMinimumSize(cardSize);

        // DIMENSÃO DOS CAMPOS (Anti-achatamento)
        Dimension fieldSize = new Dimension(320, 60);

        JTextField txtCodigo = new JTextField();
        txtCodigo.setBorder(BorderFactory.createTitledBorder("Código da Empresa:"));
        configurarCampo(txtCodigo, fieldSize);

        JTextField txtUsuario = new JTextField();
        txtUsuario.setBorder(BorderFactory.createTitledBorder("Usuário:"));
        configurarCampo(txtUsuario, fieldSize);

        JPasswordField txtSenha = new JPasswordField();
        txtSenha.setBorder(BorderFactory.createTitledBorder("Senha:"));
        configurarCampo(txtSenha, fieldSize);

        RoundedButton btnEntrar = new RoundedButton("ENTRAR");
        btnEntrar.setPreferredSize(new Dimension(150, 40));
        btnEntrar.setMaximumSize(new Dimension(150, 40));
        btnEntrar.setMinimumSize(new Dimension(150, 40));
        btnEntrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        btnEntrar.addActionListener(e -> {
            String usuarioDigitado = txtUsuario.getText().trim();
            String senhaDigitada = new String(txtSenha.getPassword()).trim();
            String codigoDigitado = txtCodigo.getText().trim();

            if (usuarioDigitado.isEmpty() || senhaDigitada.isEmpty() || codigoDigitado.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                // TENTATIVA 1: O Utilizador é o Administrador/Dono do Studio?
                salon.model.Usuario admin = new salon.controller.UsuarioController().fazerLoginColaborador(usuarioDigitado, senhaDigitada, codigoDigitado);
                
                // Sucesso! É o dono. Vai para o Lobby de seleção.
                SwingUtilities.getWindowAncestor(this).dispose();
                new salon.view.SelecaoEmpresaView(admin.getUsuario()).setVisible(true);

            } catch (IllegalArgumentException exAdmin) {
                // TENTATIVA 2: Se falhou, será que é um Funcionário?
                try {
                    salon.model.Funcionario func = new salon.controller.FuncionarioController().fazerLogin(usuarioDigitado, senhaDigitada, codigoDigitado);
                    String nomeEmpresa = new salon.controller.EmpresaController().buscarNomePorId(func.getEmpresaId());
                    
                    // Sucesso! É um funcionário. Vai direto para a sua agenda privada.
                    SwingUtilities.getWindowAncestor(this).dispose();
                    new salon.view.ColaboradorAgendamentosView(func, nomeEmpresa).setVisible(true);

                } catch (IllegalArgumentException exFunc) {
                    // Se falhou em ambos, as credenciais estão erradas.
                    JOptionPane.showMessageDialog(this, "Credenciais inválidas! Verifique usuário, senha e o código do Studio.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        RoundedButton btnInscrever = new RoundedButton("INSCREVA-SE");
        btnInscrever.setPreferredSize(new Dimension(150, 40));
        btnInscrever.setMaximumSize(new Dimension(150, 40));
        btnInscrever.setMinimumSize(new Dimension(150, 40));
        btnInscrever.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnInscrever.addActionListener(e -> parentFrame.trocarTela("CadastroColaborador"));

        // MONTAGEM DO CARD
        cardPanel.add(txtCodigo);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        cardPanel.add(txtUsuario);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        cardPanel.add(txtSenha);
        
        // ... (resto do código para cima)
        cardPanel.add(txtSenha);
        
        // Espaçamento pequeno entre a senha e o texto de esqueci a senha
        cardPanel.add(Box.createRigidArea(new Dimension(0, 5))); 
        
        JLabel lblEsqueci = new JLabel("<html><u>Não lembra sua senha? Clique aqui.</u></html>");
        lblEsqueci.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblEsqueci.setForeground(Color.DARK_GRAY); // Dá um tom mais elegante
        lblEsqueci.setAlignmentX(Component.CENTER_ALIGNMENT); // Centraliza perfeitamente
        
        lblEsqueci.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblEsqueci.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Abre a tela de recuperação por cima de tudo
                new RecuperarSenhaView((JFrame) SwingUtilities.getWindowAncestor(lblEsqueci)).setVisible(true);
            }
        });
        
        // A LINHA MÁGICA QUE FALTAVA PARA APARECER NA TELA:
        cardPanel.add(lblEsqueci);
        
        // Espaçamento antes do botão Entrar
        cardPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        cardPanel.add(btnEntrar);
        // ... (resto do código para baixo)
        
        cardPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        cardPanel.add(btnEntrar);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        JLabel lblNovaConta = new JLabel("Não tem uma conta ainda?");
        lblNovaConta.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.add(lblNovaConta);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        cardPanel.add(btnInscrever);

        // MONTAGEM FINAL DA COLUNA DIREITA
        rightPanel.add(Box.createVerticalGlue());
        rightPanel.add(lblTitle);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        rightPanel.add(cardPanel);
        rightPanel.add(Box.createVerticalGlue());

        JPanel rightContainer = new JPanel(new GridBagLayout());
        rightContainer.setOpaque(false);
        rightContainer.add(rightPanel);
        rightContainer.setPreferredSize(new Dimension(0, 0)); // Trava a divisão da tela

        gbc.gridx = 1; gbc.weightx = 0.45;
        centerContainer.add(rightContainer, gbc);

        add(centerContainer, BorderLayout.CENTER);
    }

    // Método utilitário para travar o tamanho dos TextFields (Anti-Achatamento)
    private void configurarCampo(JComponent campo, Dimension size) {
        campo.setPreferredSize(size);
        campo.setMaximumSize(size);
        campo.setMinimumSize(size);
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