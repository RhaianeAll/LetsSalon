package salon.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import salon.util.ThemeUtil;
import salon.view.components.BackgroundPanel;
import salon.view.components.RoundedButton;
import salon.view.components.RoundedPanel;

// Importações do seu Banco e Controller!
import salon.controller.EmpresaController;
import salon.model.Empresa;
import salon.controller.UsuarioController;
import salon.model.Usuario;

public class SelecaoEmpresaView extends JFrame {

    private JTextField txtId, txtNome, txtEmail;
    private JFormattedTextField txtTelefone;
    private JTable table;
    private DefaultTableModel model;
    
    // O seu Controller oficialmente instanciado e pronto!
    private EmpresaController empresaController = new EmpresaController();

    public SelecaoEmpresaView(String nomeUsuarioLogado) {
        setTitle("Let's Salon - Seleção de Empresa");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1024, 768));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        BackgroundPanel mainBg = new BackgroundPanel("/images/grafico_apoio.png", new BorderLayout());

        // ==========================================
        // 1. NAVBAR SUPERIOR
        // ==========================================
        JPanel navbar = new JPanel(new BorderLayout());
        navbar.setBackground(Color.decode("#F48FB1"));
        navbar.setPreferredSize(new Dimension(0, 60));
        navbar.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        JLabel lblNavLogo = new JLabel("LET'S SALON");
        lblNavLogo.setFont(new Font("Segoe UI Black", Font.BOLD, 20));
        lblNavLogo.setForeground(Color.WHITE);

        RoundedPanel pillUser = new RoundedPanel(25, Color.decode("#FF6090"));
        pillUser.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 5));
        JLabel lblNavName = new JLabel("Área de Seleção");
        lblNavName.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblNavName.setForeground(Color.WHITE);
        pillUser.add(lblNavName);
        
        JPanel navCenter = new JPanel(new FlowLayout(FlowLayout.CENTER));
        navCenter.setOpaque(false);
        navCenter.add(pillUser);

        JButton btnPerfil = new JButton("⚙ Meu Perfil");
        btnPerfil.setContentAreaFilled(false);
        btnPerfil.setBorderPainted(false);
        btnPerfil.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnPerfil.setForeground(Color.WHITE);
        btnPerfil.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        navbar.add(lblNavLogo, BorderLayout.WEST);
        navbar.add(navCenter, BorderLayout.CENTER);
        navbar.add(btnPerfil, BorderLayout.EAST);
        mainBg.add(navbar, BorderLayout.NORTH);

        // ==========================================
        // 2. CONTEÚDO PRINCIPAL E PERFIL
        // ==========================================
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 80, 50, 80));

        JPanel profileWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        profileWrapper.setOpaque(false);
        profileWrapper.setMaximumSize(new Dimension(9999, 100));
        
        JPanel profilePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        profilePanel.setOpaque(false);
        
        JLabel lblAvatar = new JLabel("IMG", SwingConstants.CENTER);
        lblAvatar.setPreferredSize(new Dimension(100, 100));
        lblAvatar.setOpaque(true);
        lblAvatar.setBackground(Color.decode("#E0E0E0"));
        lblAvatar.setBorder(BorderFactory.createLineBorder(Color.WHITE, 4));

        // --- AQUI ESTÁ A MÁGICA DA FOTO INSERIDA ---
        Usuario userLogado = new UsuarioController().buscarUsuarioPorLogin(nomeUsuarioLogado);
        
        if (userLogado != null && userLogado.getFoto() != null && userLogado.getFoto().length > 0) {
            ImageIcon icon = new ImageIcon(userLogado.getFoto());
            Image img = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH); 
            lblAvatar.setIcon(new ImageIcon(img));
            lblAvatar.setText(""); // Remove o texto "IMG"
            lblAvatar.setOpaque(false); // Tira o fundo cinza
            lblAvatar.setBorder(BorderFactory.createLineBorder(Color.decode("#F48FB1"), 3, true)); // Borda arredondada rosa
        }
        // ---------------------------------------------

        JPanel infoProfile = new JPanel(new GridLayout(2, 1));
        infoProfile.setOpaque(false);
        JLabel lblUserName = new JLabel(nomeUsuarioLogado);
        lblUserName.setFont(new Font("Segoe UI", Font.BOLD, 18));
        JLabel lblUserRole = new JLabel("Administrador(a)");
        lblUserRole.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        infoProfile.add(lblUserName);
        infoProfile.add(lblUserRole);

        profilePanel.add(lblAvatar);
        profilePanel.add(infoProfile);
        profileWrapper.add(profilePanel);

        contentPanel.add(profileWrapper);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        JPanel titleWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        titleWrapper.setOpaque(false);
        titleWrapper.setMaximumSize(new Dimension(9999, 50));
        JLabel lblTitle = new JLabel("Empresas Cadastradas");
        lblTitle.setFont(new Font("Segoe UI Black", Font.BOLD, 32));
        lblTitle.setForeground(Color.decode("#D81B60"));
        titleWrapper.add(lblTitle);
        contentPanel.add(titleWrapper);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        // ==========================================
        // 3. FORMULÁRIO (CRUD)
        // ==========================================
        JPanel formAndActionPanel = new JPanel(new BorderLayout(50, 0));
        formAndActionPanel.setOpaque(false);
        formAndActionPanel.setMaximumSize(new Dimension(9999, 250));

        JPanel formLeft = new JPanel(new GridBagLayout());
        formLeft.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 5, 8, 5);

        Font labelFont = new Font("Segoe UI Black", Font.BOLD, 14);
        Color labelColor = Color.decode("#D81B60");
        Dimension fieldSize = new Dimension(300, 40);

        txtId = new JTextField();
        txtId.setEditable(false);
        txtId.setBackground(Color.decode("#F5F5F5"));
        
        txtNome = new JTextField();
        txtTelefone = new JFormattedTextField();
        configurarCampoComMascara(txtTelefone, "(##) #####-####");
        txtEmail = new JTextField();

        adicionarCampo(formLeft, gbc, 0, "Id:", txtId, labelFont, labelColor, fieldSize);
        adicionarCampo(formLeft, gbc, 1, "Nome da Empresa:", txtNome, labelFont, labelColor, fieldSize);
        adicionarCampo(formLeft, gbc, 2, "Telefone:", txtTelefone, labelFont, labelColor, fieldSize);
        adicionarCampo(formLeft, gbc, 3, "E-mail:", txtEmail, labelFont, labelColor, fieldSize);

        // ==========================================
        // 3.1 BOTÕES E EVENTOS DO BANCO DE DADOS
        // ==========================================
        JPanel crudButtons = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        crudButtons.setOpaque(false);
        
        RoundedButton btnCadastrar = new RoundedButton("Cadastrar");
        RoundedButton btnAtualizar = new RoundedButton("Atualizar");
        RoundedButton btnExcluir = new RoundedButton("Excluir");
        RoundedButton btnLimpar = new RoundedButton("Limpar");

        RoundedButton[] botoes = {btnCadastrar, btnAtualizar, btnExcluir, btnLimpar};
        for (RoundedButton btn : botoes) {
            btn.setPreferredSize(new Dimension(100, 35));
            btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
            crudButtons.add(btn);
        }
        
        gbc.gridx = 1; gbc.gridy = 4; gbc.insets = new Insets(15, 5, 0, 5);
        formLeft.add(crudButtons, gbc);
        formAndActionPanel.add(formLeft, BorderLayout.WEST);

        // AÇÃO: CADASTRAR
        btnCadastrar.addActionListener(e -> {
            try {
                empresaController.cadastrarEmpresa(txtNome.getText(), txtTelefone.getText(), txtEmail.getText());
                JOptionPane.showMessageDialog(this, "Empresa cadastrada com sucesso!");
                carregarTabela(); // A mágica que atualiza a tela na hora!
                limparCampos();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        // AÇÃO: ATUALIZAR
        btnAtualizar.addActionListener(e -> {
            if (txtId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Selecione uma empresa na tabela para atualizar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                int id = Integer.parseInt(txtId.getText());
                empresaController.atualizarEmpresa(id, txtNome.getText(), txtTelefone.getText(), txtEmail.getText());
                JOptionPane.showMessageDialog(this, "Empresa atualizada com sucesso!");
                carregarTabela();
                limparCampos();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        // AÇÃO: EXCLUIR
        btnExcluir.addActionListener(e -> {
            if (txtId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Selecione uma empresa na tabela para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir esta empresa?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    int id = Integer.parseInt(txtId.getText());
                    empresaController.excluirEmpresa(id);
                    JOptionPane.showMessageDialog(this, "Empresa excluída com sucesso!");
                    carregarTabela();
                    limparCampos();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnLimpar.addActionListener(e -> limparCampos());

        JPanel actionRight = new JPanel(new GridBagLayout());
        actionRight.setOpaque(false);
        RoundedButton btnEntrar = new RoundedButton("Entrar");
        btnEntrar.setPreferredSize(new Dimension(200, 80));
        btnEntrar.setFont(new Font("Segoe UI", Font.BOLD, 20));
        actionRight.add(btnEntrar);
        
        formAndActionPanel.add(actionRight, BorderLayout.CENTER);
        contentPanel.add(formAndActionPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 50)));

        // ==========================================
        // 4. TABELA DE EMPRESAS 
        // ==========================================
        JPanel searchBar = new JPanel(new BorderLayout());
        searchBar.setOpaque(false);
        searchBar.setMaximumSize(new Dimension(9999, 40));
        
        JPanel pnlBusca = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        pnlBusca.setOpaque(false);
        JLabel lblBusca = new JLabel("Buscar:");
        lblBusca.setFont(labelFont); lblBusca.setForeground(labelColor);
        JTextField txtBusca = new JTextField(20);
        txtBusca.setBorder(BorderFactory.createLineBorder(Color.decode("#FFCDD2"), 2, true));
        txtBusca.setPreferredSize(new Dimension(200, 35));
        pnlBusca.add(lblBusca); pnlBusca.add(txtBusca);
        
        JLabel lblFiltrar = new JLabel("Filtrar ≡");
        lblFiltrar.setFont(labelFont); lblFiltrar.setForeground(labelColor);
        searchBar.add(pnlBusca, BorderLayout.WEST);
        searchBar.add(lblFiltrar, BorderLayout.EAST);
        contentPanel.add(searchBar);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        String[] colunas = {"Id", "Nome da Empresa", "Telefone", "E-mail"};
        model = new DefaultTableModel(new Object[][]{}, colunas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        table = new JTable(model);
        
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int linha = table.getSelectedRow();
                if (linha != -1) {
                    txtId.setText(table.getValueAt(linha, 0).toString());
                    txtNome.setText(table.getValueAt(linha, 1).toString());
                    txtTelefone.setText(table.getValueAt(linha, 2).toString());
                    txtEmail.setText(table.getValueAt(linha, 3).toString());
                }
            }
        });
        
        table.setRowHeight(40);
        table.setSelectionBackground(Color.decode("#FFCDD2"));
        table.setSelectionForeground(Color.decode("#D81B60"));
        table.getTableHeader().setFont(new Font("Segoe UI Black", Font.BOLD, 12));
        table.getTableHeader().setBackground(Color.decode("#F5F5F5"));
        table.getTableHeader().setPreferredSize(new Dimension(100, 40));
        
        JScrollPane scrollTable = new JScrollPane(table);
        scrollTable.setBorder(BorderFactory.createLineBorder(Color.decode("#F48FB1"), 6)); 
        
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setOpaque(false);
        tableContainer.setMaximumSize(new Dimension(9999, 400));
        tableContainer.setPreferredSize(new Dimension(1000, 300));
        tableContainer.add(scrollTable, BorderLayout.CENTER);

        contentPanel.add(tableContainer);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        JScrollPane mainScroll = new JScrollPane(contentPanel);
        mainScroll.setBorder(null);
        mainScroll.setOpaque(false);
        mainScroll.getViewport().setOpaque(false);
        mainScroll.getVerticalScrollBar().setUnitIncrement(20); 
        
        mainBg.add(mainScroll, BorderLayout.CENTER);
        add(mainBg);

        btnEntrar.addActionListener(e -> {
            int linhaSelecionada = table.getSelectedRow();
            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(this, "Por favor, selecione uma empresa na tabela antes de entrar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            } else {
                // Pega o nome da empresa clicada na tabela (que está na coluna 1)
                String nomeDaEmpresa = table.getValueAt(linhaSelecionada, 1).toString();
                
                this.dispose(); // Fecha o Lobby
                
                // Abre o NOVO Painel de Administrador passando os dados!
                new MainAdministradorView(nomeUsuarioLogado, nomeDaEmpresa).setVisible(true);
            }
        });

        // Carrega a tabela assim que a tela abre!
        carregarTabela();
    }

    // ==========================================
    // MÉTODOS DE CONTROLE DA TABELA E CAMPOS
    // ==========================================

    private void limparCampos() {
        txtId.setText("");
        txtNome.setText("");
        txtTelefone.setValue(null); 
        txtEmail.setText("");
        table.clearSelection();
    }

    private void carregarTabela() {
        model.setRowCount(0); // Limpa as linhas visuais da tabela
        try {
            // Busca a lista real do Banco de Dados
            List<Empresa> lista = empresaController.listarEmpresas(); 
            
            // Desenha a lista na tela
            for (Empresa empresa : lista) {
                model.addRow(new Object[]{
                    empresa.getIdEmpresa(),
                    empresa.getNomeEmpresa(),
                    empresa.getTelefoneEmpresa(),
                    empresa.getEmailEmpresa()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar empresas na tabela: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void configurarCampoComMascara(JFormattedTextField campo, String mascara) {
        try {
            MaskFormatter mf = new MaskFormatter(mascara);
            mf.setPlaceholderCharacter('_'); 
            mf.install(campo);
        } catch (Exception e) {}
    }

    private void adicionarCampo(JPanel painel, GridBagConstraints gbc, int linha, String textoLabel, JTextField campo, Font fonteLabel, Color corLabel, Dimension tamanhoCampo) {
        JLabel lbl = new JLabel(textoLabel);
        lbl.setFont(fonteLabel);
        lbl.setForeground(corLabel);
        lbl.setHorizontalAlignment(SwingConstants.RIGHT);
        gbc.gridx = 0; gbc.gridy = linha; gbc.weightx = 0.2;
        painel.add(lbl, gbc);

        campo.setPreferredSize(tamanhoCampo);
        campo.setBorder(BorderFactory.createLineBorder(Color.decode("#FFCDD2"), 2, true));
        gbc.gridx = 1; gbc.weightx = 0.8;
        painel.add(campo, gbc);
    }

    public static void main(String[] args) {
        ThemeUtil.setupTheme();
        SwingUtilities.invokeLater(() -> new SelecaoEmpresaView("RhaianeAll").setVisible(true));
    }
}