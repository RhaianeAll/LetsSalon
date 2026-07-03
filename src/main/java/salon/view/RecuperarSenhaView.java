package salon.view;

import javax.swing.*;
import java.awt.*;
import salon.controller.UsuarioController;
import salon.view.components.RoundedButton;

public class RecuperarSenhaView extends JDialog {

    private JTextField txtEmail;
    private UsuarioController controller = new UsuarioController();

    public RecuperarSenhaView(JFrame parent) {
        super(parent, "Recuperação de Senha", true);
        setSize(450, 300);
        setLocationRelativeTo(parent);
        setResizable(false);
        setLayout(new BorderLayout());

        JPanel painelFundo = new JPanel();
        painelFundo.setLayout(new BoxLayout(painelFundo, BoxLayout.Y_AXIS));
        painelFundo.setBackground(Color.WHITE);
        painelFundo.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JLabel lblTitulo = new JLabel("Esqueceu sua senha?");
        lblTitulo.setFont(new Font("Segoe UI Black", Font.BOLD, 22));
        lblTitulo.setForeground(Color.decode("#D81B60"));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblDescricao = new JLabel("<html><center>Digite o e-mail cadastrado no seu perfil.<br>Enviaremos uma senha provisória para você.</center></html>");
        lblDescricao.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblDescricao.setForeground(Color.GRAY);
        lblDescricao.setAlignmentX(Component.CENTER_ALIGNMENT);

        txtEmail = new JTextField();
        txtEmail.setMaximumSize(new Dimension(350, 40));
        txtEmail.setPreferredSize(new Dimension(350, 40));
        txtEmail.setBorder(BorderFactory.createLineBorder(Color.decode("#FFCDD2"), 2, true));
        txtEmail.setHorizontalAlignment(JTextField.CENTER);

        RoundedButton btnEnviar = new RoundedButton("Enviar Senha Provisória");
        btnEnviar.setPreferredSize(new Dimension(250, 45));
        btnEnviar.setMaximumSize(new Dimension(250, 45));
        btnEnviar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnEnviar.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnEnviar.addActionListener(e -> {
            btnEnviar.setText("Enviando...");
            btnEnviar.setEnabled(false);
            
            // Usamos uma Thread para não travar a tela enquanto a internet envia o email
            new Thread(() -> {
                try {
                    controller.recuperarSenha(txtEmail.getText());
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(this, "E-mail enviado com sucesso! Verifique sua caixa de entrada.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        this.dispose();
                    });
                } catch (IllegalArgumentException ex) {
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(this, ex.getMessage(), "Aviso", JOptionPane.WARNING_MESSAGE);
                        btnEnviar.setText("Enviar Senha Provisória");
                        btnEnviar.setEnabled(true);
                    });
                } catch (Exception ex) {
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                        btnEnviar.setText("Enviar Senha Provisória");
                        btnEnviar.setEnabled(true);
                    });
                }
            }).start();
        });

        painelFundo.add(lblTitulo);
        painelFundo.add(Box.createRigidArea(new Dimension(0, 10)));
        painelFundo.add(lblDescricao);
        painelFundo.add(Box.createRigidArea(new Dimension(0, 20)));
        painelFundo.add(txtEmail);
        painelFundo.add(Box.createRigidArea(new Dimension(0, 20)));
        painelFundo.add(btnEnviar);

        add(painelFundo, BorderLayout.CENTER);
    }
}