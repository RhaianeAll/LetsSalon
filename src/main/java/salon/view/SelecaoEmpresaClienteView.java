package salon.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import salon.util.ThemeUtil;
import salon.view.components.BackgroundPanel;
import salon.view.components.RoundedButton;
import salon.view.components.RoundedPanel;

import salon.controller.EmpresaController;
import salon.model.Empresa;
import salon.controller.UsuarioController;
import salon.model.Usuario;

public class SelecaoEmpresaClienteView extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private TableRowSorter<DefaultTableModel> sorter;
    
    private EmpresaController empresaController = new EmpresaController();

    public SelecaoEmpresaClienteView(String nomeUsuarioLogado) {
        setTitle("Let's Salon - Escolha um Studio");
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
        JLabel lblNavName = new JLabel("Área do Cliente");
        lblNavName.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblNavName.setForeground(Color.WHITE);
        pillUser.add(lblNavName);
        
        JPanel navCenter = new JPanel(new FlowLayout(FlowLayout.CENTER));
        navCenter.setOpaque(false);
        navCenter.add(pillUser);

        JButton btnSair = new JButton("🚪 Sair");
        btnSair.setContentAreaFilled(false);
        btnSair.setBorderPainted(false);
        btnSair.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSair.setForeground(Color.WHITE);
        btnSair.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSair.addActionListener(e -> {
            this.dispose();
            new AuthView().setVisible(true);
        });
        
        navbar.add(lblNavLogo, BorderLayout.WEST);
        navbar.add(navCenter, BorderLayout.CENTER);
        navbar.add(btnSair, BorderLayout.EAST);
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

        Usuario userLogado = new UsuarioController().buscarUsuarioPorLogin(nomeUsuarioLogado);
        
        if (userLogado != null && userLogado.getFoto() != null && userLogado.getFoto().length > 0) {
            ImageIcon icon = new ImageIcon(userLogado.getFoto());
            Image img = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH); 
            lblAvatar.setIcon(new ImageIcon(img));
            lblAvatar.setText(""); 
            lblAvatar.setOpaque(false); 
            lblAvatar.setBorder(BorderFactory.createLineBorder(Color.decode("#F48FB1"), 3, true)); 
        }

        JPanel infoProfile = new JPanel(new GridLayout(2, 1));
        infoProfile.setOpaque(false);
        JLabel lblUserName = new JLabel(userLogado != null ? userLogado.getNomeCompleto() : nomeUsuarioLogado);
        lblUserName.setFont(new Font("Segoe UI", Font.BOLD, 18));
        
        // Puxa o cargo dinâmico (Cliente) com a primeira letra maiúscula
        String tipo = userLogado != null ? userLogado.getTipoUsuario() : "Cliente";
        String tipoFormatado = tipo.substring(0, 1).toUpperCase() + tipo.substring(1);
        JLabel lblUserRole = new JLabel(tipoFormatado);
        lblUserRole.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        infoProfile.add(lblUserName);
        infoProfile.add(lblUserRole);

        profilePanel.add(lblAvatar);
        profilePanel.add(infoProfile);
        profileWrapper.add(profilePanel);

        contentPanel.add(profileWrapper);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        // TÍTULO CORRIGIDO
        JPanel titleWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        titleWrapper.setOpaque(false);
        titleWrapper.setMaximumSize(new Dimension(9999, 50));
        JLabel lblTitle = new JLabel("Studios Disponíveis");
        lblTitle.setFont(new Font("Segoe UI Black", Font.BOLD, 36));
        lblTitle.setForeground(Color.decode("#D81B60"));
        titleWrapper.add(lblTitle);
        contentPanel.add(titleWrapper);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // ==========================================
        // 3. TABELA DE BUSCA E SELEÇÃO
        // ==========================================
        Font labelFont = new Font("Segoe UI Black", Font.BOLD, 16);
        Color labelColor = Color.decode("#D81B60");

        JPanel searchBar = new JPanel(new BorderLayout());
        searchBar.setOpaque(false);
        searchBar.setMaximumSize(new Dimension(9999, 40));
        
        JPanel pnlBusca = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        pnlBusca.setOpaque(false);
        JLabel lblBusca = new JLabel("Buscar Studio:");
        lblBusca.setFont(labelFont); lblBusca.setForeground(labelColor);
        
        JTextField txtBusca = new JTextField(25);
        txtBusca.setBorder(BorderFactory.createLineBorder(Color.decode("#FFCDD2"), 2, true));
        txtBusca.setPreferredSize(new Dimension(250, 35));
        
        // Lógica de filtro dinâmico
        txtBusca.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filtrarTabela(txtBusca.getText()); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filtrarTabela(txtBusca.getText()); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filtrarTabela(txtBusca.getText()); }
        });

        pnlBusca.add(lblBusca); pnlBusca.add(txtBusca);
        searchBar.add(pnlBusca, BorderLayout.WEST);
        
        contentPanel.add(searchBar);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        String[] colunas = {"ID", "Nome do Studio", "Telefone", "E-mail"};
        model = new DefaultTableModel(new Object[][]{}, colunas) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        
        table = new JTable(model);
        table.setRowHeight(45);
        table.setSelectionBackground(Color.decode("#FFCDD2"));
        table.setSelectionForeground(Color.decode("#D81B60"));
        table.getTableHeader().setFont(new Font("Segoe UI Black", Font.BOLD, 14));
        table.getTableHeader().setBackground(Color.decode("#F5F5F5"));
        table.getTableHeader().setPreferredSize(new Dimension(100, 45));
        
        // Esconde a coluna ID (Índice 0) para o cliente não ver códigos do banco
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setWidth(0);

        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        JScrollPane scrollTable = new JScrollPane(table);
        scrollTable.setBorder(BorderFactory.createLineBorder(Color.decode("#F48FB1"), 6)); 
        
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setOpaque(false);
        tableContainer.setMaximumSize(new Dimension(9999, 400));
        tableContainer.setPreferredSize(new Dimension(1000, 300));
        tableContainer.add(scrollTable, BorderLayout.CENTER);

        contentPanel.add(tableContainer);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // ==========================================
        // 4. BOTÃO ENTRAR
        // ==========================================
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        actionPanel.setOpaque(false);
        RoundedButton btnEntrar = new RoundedButton("Entrar no Studio");
        btnEntrar.setPreferredSize(new Dimension(250, 60));
        btnEntrar.setFont(new Font("Segoe UI Black", Font.BOLD, 18));
        actionPanel.add(btnEntrar);
        
        contentPanel.add(actionPanel);

        JScrollPane mainScroll = new JScrollPane(contentPanel);
        mainScroll.setBorder(null);
        mainScroll.setOpaque(false);
        mainScroll.getViewport().setOpaque(false);
        mainScroll.getVerticalScrollBar().setUnitIncrement(20); 
        
        mainBg.add(mainScroll, BorderLayout.CENTER);
        setContentPane(mainBg);

        // AÇÃO DE ENTRAR DIRETAMENTE PARA O CLIENTE
        btnEntrar.addActionListener(e -> {
            int linhaView = table.getSelectedRow();
            if (linhaView == -1) {
                JOptionPane.showMessageDialog(this, "Por favor, selecione um studio na tabela antes de entrar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            } else {
                int linhaModel = table.convertRowIndexToModel(linhaView);
                // Coluna 1 é o Nome do Studio
                String nomeDaEmpresa = model.getValueAt(linhaModel, 1).toString();
                
                this.dispose(); 
                new AgendamentosClienteView(nomeUsuarioLogado, nomeDaEmpresa).setVisible(true);
            }
        });

        carregarTabela();
    }

    private void filtrarTabela(String texto) {
        if (texto.trim().length() == 0) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto));
        }
    }

    private void carregarTabela() {
        model.setRowCount(0); 
        try {
            List<Empresa> lista = empresaController.listarEmpresas(); 
            for (Empresa empresa : lista) {
                model.addRow(new Object[]{
                    empresa.getIdEmpresa(),
                    empresa.getNomeEmpresa(),
                    empresa.getTelefoneEmpresa(),
                    empresa.getEmailEmpresa()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar studios: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}