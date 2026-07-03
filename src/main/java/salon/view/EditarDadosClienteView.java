package salon.view;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;

import salon.controller.UsuarioController;
import salon.model.Usuario;
import salon.view.components.BackgroundPanel;
import salon.view.components.RoundedButton;
import salon.view.components.RoundedPanel;

public class EditarDadosClienteView extends JFrame {

    private String nomeUsuarioAtual;
    private String nomeEmpresa;
    private Usuario clienteLogado;
    private UsuarioController usuarioController = new UsuarioController();
    
    private JTextField txtNome, txtTelefone, txtEmail, txtUsuario;
    private JPasswordField txtSenha, txtConfirmaSenha;
    private JLabel lblFotoPerfil, lblNomeGrande;
    private byte[] fotoBytes = null;

    public EditarDadosClienteView(String nomeUsuario, String nomeEmpresa) {
        this.nomeUsuarioAtual = nomeUsuario;
        this.nomeEmpresa = nomeEmpresa;
        this.clienteLogado = usuarioController.buscarUsuarioPorLogin(nomeUsuarioAtual);
        if (this.clienteLogado != null) this.fotoBytes = clienteLogado.getFoto();

        setTitle("Let's Salon - Editar Perfil");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        BackgroundPanel mainBg = new BackgroundPanel("/images/grafico_apoio.png", new BorderLayout());
        setContentPane(mainBg);

        // --- NAVBAR ---
        JPanel navbar = new JPanel(new BorderLayout()); navbar.setBackground(Color.decode("#F48FB1")); navbar.setPreferredSize(new Dimension(0, 70)); navbar.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        JLabel lblNavLogo = new JLabel("LET'S SALON 🌿"); lblNavLogo.setFont(new Font("Segoe UI Black", Font.BOLD, 22)); lblNavLogo.setForeground(Color.WHITE); navbar.add(lblNavLogo, BorderLayout.WEST);
        
        JPanel navRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0)); navRight.setOpaque(false);
        JButton btnVoltar = new JButton("↩ Voltar"); btnVoltar.setContentAreaFilled(false); btnVoltar.setBorderPainted(false); btnVoltar.setFont(new Font("Segoe UI", Font.BOLD, 14)); btnVoltar.setForeground(Color.WHITE); btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVoltar.addActionListener(e -> { this.dispose(); new AgendamentosClienteView(nomeUsuarioAtual, nomeEmpresa).setVisible(true); });
        navRight.add(btnVoltar); navbar.add(navRight, BorderLayout.EAST);
        mainBg.add(navbar, BorderLayout.NORTH);

        // --- CONTEÚDO PRINCIPAL ---
        JPanel contentPanel = new JPanel(); contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS)); contentPanel.setOpaque(false); contentPanel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        JPanel topSection = new JPanel(new FlowLayout(FlowLayout.LEFT, 40, 0)); topSection.setOpaque(false); topSection.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblFotoPerfil = new JLabel(); lblFotoPerfil.setPreferredSize(new Dimension(150, 150)); lblFotoPerfil.setBorder(BorderFactory.createLineBorder(Color.decode("#F48FB1"), 4, true)); lblFotoPerfil.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel infoTopo = new JPanel(); infoTopo.setLayout(new BoxLayout(infoTopo, BoxLayout.Y_AXIS)); infoTopo.setOpaque(false);
        lblNomeGrande = new JLabel("Carregando..."); lblNomeGrande.setFont(new Font("Segoe UI Black", Font.BOLD, 24));
        RoundedButton btnImportar = new RoundedButton("ALTERAR FOTO"); btnImportar.setPreferredSize(new Dimension(180, 40)); btnImportar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnImportar.addActionListener(e -> selecionarFoto());
        
        infoTopo.add(lblNomeGrande); infoTopo.add(Box.createRigidArea(new Dimension(0, 20))); infoTopo.add(btnImportar);
        topSection.add(lblFotoPerfil); topSection.add(infoTopo); contentPanel.add(topSection); contentPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        // FORMULÁRIO
        JPanel formPanel = new JPanel(new GridBagLayout()); formPanel.setOpaque(false); formPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        GridBagConstraints gbc = new GridBagConstraints(); gbc.fill = GridBagConstraints.HORIZONTAL; gbc.insets = new Insets(10, 10, 10, 10);
        Font labelFont = new Font("Segoe UI Black", Font.BOLD, 16); Color labelColor = Color.decode("#D81B60");

        txtNome = new JTextField(); txtTelefone = new JTextField(); txtEmail = new JTextField(); txtUsuario = new JTextField(); txtSenha = new JPasswordField(); txtConfirmaSenha = new JPasswordField();
        
        adicionarCampo(formPanel, gbc, 0, "Nome Completo:", txtNome, labelFont, labelColor);
        adicionarCampo(formPanel, gbc, 1, "Telefone:", txtTelefone, labelFont, labelColor);
        adicionarCampo(formPanel, gbc, 2, "E-mail:", txtEmail, labelFont, labelColor);
        adicionarCampo(formPanel, gbc, 3, "Usuário de Login:", txtUsuario, labelFont, labelColor);
        adicionarCampo(formPanel, gbc, 4, "Nova Senha (deixe branco p/ manter):", txtSenha, labelFont, labelColor);
        adicionarCampo(formPanel, gbc, 5, "Confirme a Senha:", txtConfirmaSenha, labelFont, labelColor);

        JPanel botoesForm = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0)); botoesForm.setOpaque(false);
        RoundedButton btnAlterar = new RoundedButton("SALVAR ALTERAÇÕES"); btnAlterar.setPreferredSize(new Dimension(200, 45)); btnAlterar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnAlterar.addActionListener(e -> salvarAlteracoes()); botoesForm.add(btnAlterar);
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2; gbc.insets = new Insets(30, 0, 0, 0); formPanel.add(botoesForm, gbc);
        
        contentPanel.add(formPanel);
        JScrollPane scroll = new JScrollPane(contentPanel); scroll.setOpaque(false); scroll.getViewport().setOpaque(false); scroll.setBorder(null); scroll.getVerticalScrollBar().setUnitIncrement(25);
        mainBg.add(scroll, BorderLayout.CENTER);
        
        preencherDadosAtuais();
    }

    private void preencherDadosAtuais() {
        if (clienteLogado != null) {
            txtNome.setText(clienteLogado.getNomeCompleto() != null ? clienteLogado.getNomeCompleto() : "");
            txtTelefone.setText(clienteLogado.getTelefone() != null ? clienteLogado.getTelefone() : "");
            txtEmail.setText(clienteLogado.getEmail() != null ? clienteLogado.getEmail() : "");
            txtUsuario.setText(clienteLogado.getUsuario() != null ? clienteLogado.getUsuario() : "");
            lblNomeGrande.setText(clienteLogado.getNomeCompleto() != null ? clienteLogado.getNomeCompleto() : "Cliente");
            atualizarExibicaoFoto();
        }
    }

    private void selecionarFoto() {
        JFileChooser chooser = new JFileChooser(); chooser.setFileFilter(new FileNameExtensionFilter("Imagens (JPG, PNG)", "jpg", "jpeg", "png"));
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try { fotoBytes = Files.readAllBytes(chooser.getSelectedFile().toPath()); atualizarExibicaoFoto(); } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Erro ao ler a imagem."); }
        }
    }

    private void atualizarExibicaoFoto() {
        if (fotoBytes != null && fotoBytes.length > 0) {
            ImageIcon icon = new ImageIcon(fotoBytes); Image img = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            lblFotoPerfil.setIcon(new ImageIcon(img)); lblFotoPerfil.setText("");
        } else { lblFotoPerfil.setIcon(null); lblFotoPerfil.setText("Sem Foto"); }
    }

    private void salvarAlteracoes() {
        if (clienteLogado == null) return;
        try {
            clienteLogado.setNomeCompleto(txtNome.getText()); clienteLogado.setTelefone(txtTelefone.getText());
            clienteLogado.setEmail(txtEmail.getText()); clienteLogado.setUsuario(txtUsuario.getText()); clienteLogado.setFoto(fotoBytes);
            String senha = new String(txtSenha.getPassword()); String confirma = new String(txtConfirmaSenha.getPassword());
            
            usuarioController.atualizarPerfil(clienteLogado, senha, confirma);
            JOptionPane.showMessageDialog(this, "Perfil atualizado com sucesso!");
            
            this.dispose(); new AgendamentosClienteView(clienteLogado.getUsuario(), nomeEmpresa).setVisible(true);
        } catch (Exception ex) { JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro ao Atualizar", JOptionPane.ERROR_MESSAGE); }
    }

    private void adicionarCampo(JPanel painel, GridBagConstraints gbc, int linha, String textoLabel, JTextField campo, Font font, Color cor) {
        JLabel lbl = new JLabel(textoLabel); lbl.setFont(font); lbl.setForeground(cor); lbl.setHorizontalAlignment(SwingConstants.RIGHT);
        gbc.gridx = 0; gbc.gridy = linha; gbc.weightx = 0.3; gbc.gridwidth = 1; painel.add(lbl, gbc);
        campo.setPreferredSize(new Dimension(350, 40)); campo.setBorder(BorderFactory.createLineBorder(Color.decode("#FFCDD2"), 2, true));
        gbc.gridx = 1; gbc.weightx = 0.7; painel.add(campo, gbc);
    }
}