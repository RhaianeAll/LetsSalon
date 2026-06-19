package salon.view;

import javax.swing.*;
import java.awt.*;
import salon.controller.FuncionarioController;
import salon.view.components.RoundedButton;
import salon.view.components.RoundedPanel;

public class CadastroColaboradorPanel extends JPanel {

    private FuncionarioController funcionarioController;

    public CadastroColaboradorPanel(AuthView parentFrame) {
        this.funcionarioController = new FuncionarioController();

        setOpaque(false);
        setLayout(new GridBagLayout());

        RoundedPanel cardPanel = new RoundedPanel(30, Color.decode("#FFCDD2"));
        cardPanel.setPreferredSize(new Dimension(400, 680));
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel lblTitle = new JLabel("INSCREVER COLABORADOR");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(Color.decode("#D81B60"));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Criando todos os campos solicitados no PDF
        JTextField txtNome = new JTextField();
        txtNome.setBorder(BorderFactory.createTitledBorder("Nome Completo:"));
        
        JTextField txtCpf = new JTextField();
        txtCpf.setBorder(BorderFactory.createTitledBorder("CPF:"));
        
        JTextField txtTelefone = new JTextField();
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

        Component[] campos = {txtNome, txtCpf, txtTelefone, txtEmpresa, txtCargo, txtUsuario, txtSenha, txtConfirmaSenha};
        
        cardPanel.add(lblTitle);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        for (Component c : campos) {
            ((JComponent) c).setMaximumSize(new Dimension(340, 45)); // Altura um pouco menor para caber tudo
            cardPanel.add(c);
            cardPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        }

        RoundedButton btnInscrever = new RoundedButton("INSCREVER-SE");
        btnInscrever.setMaximumSize(new Dimension(180, 40));
        btnInscrever.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Ação de Cadastrar
        btnInscrever.addActionListener(e -> {
            try {
                // Aqui passamos "1" como ID da empresa provisório, pois a arquitetura exige o ID real do banco
                funcionarioController.cadastrarFuncionario(
                    txtNome.getText(), txtCpf.getText(), txtTelefone.getText(),
                    txtUsuario.getText(), new String(txtSenha.getPassword()), 
                    txtCargo.getText(), 1 
                );
                JOptionPane.showMessageDialog(this, "Colaborador cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                parentFrame.trocarTela("LoginColaborador");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        cardPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        cardPanel.add(btnInscrever);

        // Botão Voltar (Opção 1)
        JButton btnVoltar = new JButton("← Voltar para Login");
        btnVoltar.setContentAreaFilled(false);
        btnVoltar.setBorderPainted(false);
        btnVoltar.setForeground(Color.decode("#D81B60"));
        btnVoltar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVoltar.addActionListener(e -> parentFrame.trocarTela("LoginColaborador"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0;
        add(btnVoltar, gbc);
        
        gbc.gridy = 1;
        add(cardPanel, gbc);
    }
}