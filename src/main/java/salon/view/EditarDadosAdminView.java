package salon.view;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;

import salon.controller.UsuarioController;
import salon.model.Usuario;
import salon.util.ThemeUtil;
import salon.view.components.BackgroundPanel;
import salon.view.components.RoundedButton;
import salon.view.components.RoundedPanel;

public class EditarDadosAdminView extends JFrame {

    private String nomeUsuarioAtual;
    private String nomeEmpresa;
    
    private Usuario adminLogado;
    private UsuarioController usuarioController = new UsuarioController();
    
    private JTextField txtNome, txtTelefone, txtEmail, txtCargo, txtUsuario;
    private JPasswordField txtSenha, txtConfirmaSenha;
    private JLabel lblFotoPerfil, lblNomeGrande, lblCargoPequeno;
    
    private byte[] fotoBytes = null;

    public EditarDadosAdminView(String nomeUsuario, String nomeEmpresa) {
        this.nomeUsuarioAtual = nomeUsuario;
        this.nomeEmpresa = nomeEmpresa;

        // Busca o usuário na nossa tabela unificada
        this.adminLogado = usuarioController.buscarUsuarioPorLogin(nomeUsuarioAtual);
        if (this.adminLogado != null) {
            this.fotoBytes = adminLogado.getFoto();
        }

        setTitle("Let's Salon - Editar Perfil");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        BackgroundPanel mainBg = new BackgroundPanel("/images/grafico_apoio.png", new BorderLayout());
        setContentPane(mainBg);

        // --- NAVBAR ---
        JPanel navbar = new JPanel(new BorderLayout()); 
        navbar.setBackground(Color.decode("#F48FB1")); 
        navbar.setPreferredSize(new Dimension(0, 70)); 
        navbar.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        
        JLabel lblNavLogo = new JLabel("LET'S SALON 🌿"); 
        lblNavLogo.setFont(new Font("Segoe UI Black", Font.BOLD, 22)); 
        lblNavLogo.setForeground(Color.WHITE);
        
        RoundedPanel pillUser = new RoundedPanel(30, Color.decode("#FF6090")); 
        pillUser.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 5));
        JLabel lblNavName = new JLabel(nomeUsuarioAtual); 
        lblNavName.setFont(new Font("Segoe UI", Font.BOLD, 16)); 
        lblNavName.setForeground(Color.WHITE); 
        pillUser.add(lblNavName);
        
        JPanel navCenter = new JPanel(new FlowLayout(FlowLayout.CENTER)); 
        navCenter.setOpaque(false); 
        navCenter.add(pillUser);
        
        JPanel navRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0)); 
        navRight.setOpaque(false);
        JLabel lblStudio = new JLabel(nomeEmpresa); 
        lblStudio.setFont(new Font("Segoe UI", Font.ITALIC, 18)); 
        lblStudio.setForeground(Color.WHITE);
        
        JButton btnVoltar = new JButton("↩ Voltar"); 
        btnVoltar.setContentAreaFilled(false); 
        btnVoltar.setBorderPainted(false); 
        btnVoltar.setFont(new Font("Segoe UI", Font.BOLD, 14)); 
        btnVoltar.setForeground(Color.WHITE); 
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVoltar.addActionListener(e -> { 
            this.dispose(); 
            new MainAdministradorView(nomeUsuarioAtual, nomeEmpresa).setVisible(true); 
        });
        
        navRight.add(lblStudio); navRight.add(btnVoltar); 
        navbar.add(lblNavLogo, BorderLayout.WEST); 
        navbar.add(navCenter, BorderLayout.CENTER); 
        navbar.add(navRight, BorderLayout.EAST);
        mainBg.add(navbar, BorderLayout.NORTH);

        // --- CONTEÚDO PRINCIPAL ---
        JPanel contentPanel = new JPanel(); 
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS)); 
        contentPanel.setOpaque(false); 
        contentPanel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        // TOPO (Foto e Informações)
        JPanel topSection = new JPanel(new FlowLayout(FlowLayout.LEFT, 40, 0)); 
        topSection.setOpaque(false); 
        topSection.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        lblFotoPerfil = new JLabel();
        lblFotoPerfil.setPreferredSize(new Dimension(150, 150));
        lblFotoPerfil.setBorder(BorderFactory.createLineBorder(Color.decode("#F48FB1"), 4, true));
        lblFotoPerfil.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel infoTopo = new JPanel(); 
        infoTopo.setLayout(new BoxLayout(infoTopo, BoxLayout.Y_AXIS)); 
        infoTopo.setOpaque(false);
        
        lblNomeGrande = new JLabel("Carregando..."); 
        lblNomeGrande.setFont(new Font("Segoe UI Black", Font.BOLD, 24));
        lblCargoPequeno = new JLabel("Colaborador"); 
        lblCargoPequeno.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        
        RoundedButton btnImportar = new RoundedButton("IMPORTAR FOTO");
        btnImportar.setPreferredSize(new Dimension(180, 40)); 
        btnImportar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnImportar.addActionListener(e -> selecionarFoto());
        
        infoTopo.add(lblNomeGrande); 
        infoTopo.add(lblCargoPequeno); 
        infoTopo.add(Box.createRigidArea(new Dimension(0, 20))); 
        infoTopo.add(btnImportar);
        
        topSection.add(lblFotoPerfil); 
        topSection.add(infoTopo);
        contentPanel.add(topSection);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        JLabel lblTitle = new JLabel("Editar Dados de Usuário"); 
        lblTitle.setFont(new Font("Segoe UI Black", Font.BOLD, 36)); 
        lblTitle.setForeground(Color.decode("#FF6090")); 
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(lblTitle); 
        contentPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // FORMULÁRIO DE EDIÇÃO
        JPanel formPanel = new JPanel(new GridBagLayout()); 
        formPanel.setOpaque(false); 
        formPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        GridBagConstraints gbc = new GridBagConstraints(); 
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.insets = new Insets(10, 10, 10, 10);
        
        Font labelFont = new Font("Segoe UI Black", Font.BOLD, 16); 
        Color labelColor = Color.decode("#D81B60");

        txtNome = new JTextField(); txtTelefone = new JTextField(); 
        txtEmail = new JTextField(); txtCargo = new JTextField(); 
        txtUsuario = new JTextField();
        txtSenha = new JPasswordField(); txtConfirmaSenha = new JPasswordField();
        
        adicionarCampo(formPanel, gbc, 0, "Nome Completo:", txtNome, labelFont, labelColor);
        adicionarCampo(formPanel, gbc, 1, "Telefone:", txtTelefone, labelFont, labelColor);
        adicionarCampo(formPanel, gbc, 2, "E-mail:", txtEmail, labelFont, labelColor);
        adicionarCampo(formPanel, gbc, 3, "Cargo:", txtCargo, labelFont, labelColor);
        adicionarCampo(formPanel, gbc, 4, "Usuário:", txtUsuario, labelFont, labelColor);
        adicionarCampo(formPanel, gbc, 5, "Senha (deixe branco p/ manter):", txtSenha, labelFont, labelColor);
        adicionarCampo(formPanel, gbc, 6, "Confirme a Senha:", txtConfirmaSenha, labelFont, labelColor);

        // BOTÕES
        JPanel botoesForm = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0)); 
        botoesForm.setOpaque(false);
        RoundedButton btnAlterar = new RoundedButton("ALTERAR"); 
        btnAlterar.setPreferredSize(new Dimension(150, 45)); 
        btnAlterar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        RoundedButton btnLimpar = new RoundedButton("LIMPAR"); 
        btnLimpar.setPreferredSize(new Dimension(150, 45)); 
        btnLimpar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        
        btnAlterar.addActionListener(e -> salvarAlteracoes());
        btnLimpar.addActionListener(e -> preencherDadosAtuais()); // Limpar volta ao estado do BD
        
        botoesForm.add(btnAlterar); botoesForm.add(btnLimpar);
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2; gbc.insets = new Insets(30, 0, 0, 0); 
        formPanel.add(botoesForm, gbc);
        
        contentPanel.add(formPanel);

        JScrollPane scroll = new JScrollPane(contentPanel); 
        scroll.setOpaque(false); 
        scroll.getViewport().setOpaque(false); 
        scroll.setBorder(null); 
        scroll.getVerticalScrollBar().setUnitIncrement(25);
        mainBg.add(scroll, BorderLayout.CENTER);
        
        // Puxa os dados para a tela no momento em que abre
        preencherDadosAtuais();
    }

    private void preencherDadosAtuais() {
        if (adminLogado != null) {
            txtNome.setText(adminLogado.getNomeCompleto() != null ? adminLogado.getNomeCompleto() : "");
            txtTelefone.setText(adminLogado.getTelefone() != null ? adminLogado.getTelefone() : "");
            txtEmail.setText(adminLogado.getEmail() != null ? adminLogado.getEmail() : "");
            txtCargo.setText(adminLogado.getCargo() != null ? adminLogado.getCargo() : "");
            txtUsuario.setText(adminLogado.getUsuario() != null ? adminLogado.getUsuario() : "");
            
            lblNomeGrande.setText(adminLogado.getNomeCompleto() != null ? adminLogado.getNomeCompleto() : "Administrador");
            
            // Se o cargo estiver vazio, mostra o tipo do usuário (ex: Colaborador)
            String subtitulo = (adminLogado.getCargo() != null && !adminLogado.getCargo().isEmpty()) 
                                ? adminLogado.getCargo() 
                                : adminLogado.getTipoUsuario().substring(0, 1).toUpperCase() + adminLogado.getTipoUsuario().substring(1);
            lblCargoPequeno.setText(subtitulo);
            
            // As senhas não são preenchidas por segurança
            txtSenha.setText("");
            txtConfirmaSenha.setText("");
            
            atualizarExibicaoFoto();
        } else {
            JOptionPane.showMessageDialog(this, "Não foi possível carregar os dados deste usuário.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void selecionarFoto() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Imagens (JPG, PNG)", "jpg", "jpeg", "png"));
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                File arquivo = chooser.getSelectedFile();
                fotoBytes = Files.readAllBytes(arquivo.toPath());
                atualizarExibicaoFoto();
            } catch (Exception ex) { 
                JOptionPane.showMessageDialog(this, "Erro ao ler a imagem."); 
            }
        }
    }

    private void atualizarExibicaoFoto() {
        if (fotoBytes != null && fotoBytes.length > 0) {
            ImageIcon icon = new ImageIcon(fotoBytes);
            Image img = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            lblFotoPerfil.setIcon(new ImageIcon(img));
            lblFotoPerfil.setText("");
        } else {
            lblFotoPerfil.setIcon(null);
            lblFotoPerfil.setText("Sem Foto");
        }
    }

    private void salvarAlteracoes() {
        if (adminLogado == null) return;
        
        try {
            adminLogado.setNomeCompleto(txtNome.getText());
            adminLogado.setTelefone(txtTelefone.getText());
            adminLogado.setEmail(txtEmail.getText());
            adminLogado.setCargo(txtCargo.getText());
            adminLogado.setUsuario(txtUsuario.getText());
            adminLogado.setFoto(fotoBytes);
            
            String senha = new String(txtSenha.getPassword());
            String confirma = new String(txtConfirmaSenha.getPassword());
            
            usuarioController.atualizarPerfil(adminLogado, senha, confirma);
            
            JOptionPane.showMessageDialog(this, "Perfil atualizado com sucesso!");
            
            // Recarrega o menu principal com o novo nome (caso ele tenha mudado de login)
            this.dispose();
            new MainAdministradorView(adminLogado.getUsuario(), nomeEmpresa).setVisible(true);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro ao Atualizar", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void adicionarCampo(JPanel painel, GridBagConstraints gbc, int linha, String textoLabel, JTextField campo, Font font, Color cor) {
        JLabel lbl = new JLabel(textoLabel); 
        lbl.setFont(font); 
        lbl.setForeground(cor); 
        lbl.setHorizontalAlignment(SwingConstants.RIGHT);
        
        gbc.gridx = 0; gbc.gridy = linha; 
        gbc.weightx = 0.3; gbc.gridwidth = 1; 
        painel.add(lbl, gbc);
        
        campo.setPreferredSize(new Dimension(350, 40)); 
        campo.setBorder(BorderFactory.createLineBorder(Color.decode("#FFCDD2"), 2, true));
        
        gbc.gridx = 1; gbc.weightx = 0.7; 
        painel.add(campo, gbc);
    }
}