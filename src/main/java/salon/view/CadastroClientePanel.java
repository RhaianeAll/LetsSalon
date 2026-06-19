package salon.view;

import javax.swing.*;
import java.awt.*;
import salon.controller.UsuarioController;
import salon.view.components.RoundedButton;
import salon.view.components.RoundedPanel;

public class CadastroClientePanel extends JPanel {

    private UsuarioController usuarioController;

    public CadastroClientePanel(AuthView parentFrame) {
        this.usuarioController = new UsuarioController();

        setBackground(Color.WHITE);
        setLayout(new GridBagLayout());

        RoundedPanel cardPanel = new RoundedPanel(30, Color.decode("#FFCDD2"));
        cardPanel.setPreferredSize(new Dimension(380, 550));
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JTextField txtNome = new JTextField();
        txtNome.setBorder(BorderFactory.createTitledBorder("Nome Completo:"));
        
        JTextField txtCpf = new JTextField();
        txtCpf.setBorder(BorderFactory.createTitledBorder("CPF:"));
        
        JTextField txtTelefone = new JTextField();
        txtTelefone.setBorder(BorderFactory.createTitledBorder("Número de Telefone:"));
        
        JTextField txtUsuario = new JTextField();
        txtUsuario.setBorder(BorderFactory.createTitledBorder("Nome de Usuário:"));
        
        JPasswordField txtSenha = new JPasswordField();
        txtSenha.setBorder(BorderFactory.createTitledBorder("Senha:"));
        
        JPasswordField txtConfirmaSenha = new JPasswordField();
        txtConfirmaSenha.setBorder(BorderFactory.createTitledBorder("Confirme a Senha:"));

        // Ajustando os tamanhos para ficarem padronizados
        Component[] campos = {txtNome, txtCpf, txtTelefone, txtUsuario, txtSenha, txtConfirmaSenha};
        for (Component c : campos) {
            ((JComponent) c).setMaximumSize(new Dimension(320, 50));
            cardPanel.add(c);
            cardPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        RoundedButton btnInscrever = new RoundedButton("INSCREVER-SE");
        btnInscrever.setMaximumSize(new Dimension(180, 40));
        btnInscrever.setAlignmentX(Component.CENTER_ALIGNMENT);

        // AÇÃO DE CADASTRO NO BANCO
        btnInscrever.addActionListener(e -> {
            try {
                usuarioController.cadastrarCliente(
                    txtNome.getText(),
                    txtCpf.getText(),
                    txtTelefone.getText(),
                    txtUsuario.getText(),
                    new String(txtSenha.getPassword()),
                    new String(txtConfirmaSenha.getPassword())
                );
                JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                parentFrame.trocarTela("LoginCliente"); // Volta para o login após cadastrar
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Aviso", JOptionPane.WARNING_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao conectar com o banco de dados.", "Erro Crítico", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        cardPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        cardPanel.add(btnInscrever);

        JButton btnVoltar = new JButton("← Voltar");
        btnVoltar.setContentAreaFilled(false);
        btnVoltar.setBorderPainted(false);
        btnVoltar.setForeground(Color.decode("#D81B60")); // Deixa o texto rosa escuro
        btnVoltar.setFont(new Font("Segoe UI", Font.BOLD, 14)); // Deixa a fonte mais visível
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVoltar.addActionListener(e -> parentFrame.trocarTela("Welcome"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0;
        add(btnVoltar, gbc);
        
        gbc.gridy = 1;
        add(cardPanel, gbc);
    }
}