package salon.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import salon.util.ThemeUtil;
import salon.view.components.BackgroundPanel;
import salon.view.components.RoundedButton;
import salon.view.components.RoundedPanel;

import salon.controller.InsumoController;
import salon.model.Insumo;

public class EstoqueView extends JFrame {

    private String nomeUsuario;
    private String nomeEmpresa;

    private JTextField txtId, txtNome, txtValor, txtQuantidade, txtCapacidade;
    private JTable table;
    private DefaultTableModel model;

    private InsumoController insumoController = new InsumoController();
    
    // Variável oculta para não perdermos o rendimento ao atualizar o estoque
    private int rendimentoOcultoSelecionado = 1; 

    public EstoqueView(String nomeUsuario, String nomeEmpresa) {
        this.nomeUsuario = nomeUsuario;
        this.nomeEmpresa = nomeEmpresa;

        setTitle("Let's Salon - Controle de Estoque");
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
        navbar.setPreferredSize(new Dimension(0, 70));
        navbar.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));

        JLabel lblNavLogo = new JLabel("LET'S SALON 🌿");
        lblNavLogo.setFont(new Font("Segoe UI Black", Font.BOLD, 22));
        lblNavLogo.setForeground(Color.WHITE);

        RoundedPanel pillUser = new RoundedPanel(30, Color.decode("#FF6090"));
        pillUser.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 5));
        JLabel lblNavName = new JLabel(nomeUsuario);
        lblNavName.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblNavName.setForeground(Color.WHITE);
        pillUser.add(lblNavName);
        
        JPanel navCenter = new JPanel(new FlowLayout(FlowLayout.CENTER)); navCenter.setOpaque(false); navCenter.add(pillUser);

        JPanel navRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0)); navRight.setOpaque(false);
        JLabel lblStudio = new JLabel(nomeEmpresa); lblStudio.setFont(new Font("Segoe UI", Font.ITALIC, 18)); lblStudio.setForeground(Color.WHITE);
        JButton btnVoltar = new JButton("↩ Voltar"); btnVoltar.setContentAreaFilled(false); btnVoltar.setBorderPainted(false);
        btnVoltar.setFont(new Font("Segoe UI", Font.BOLD, 14)); btnVoltar.setForeground(Color.WHITE); btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVoltar.addActionListener(e -> { this.dispose(); new MainAdministradorView(nomeUsuario, nomeEmpresa).setVisible(true); });
        navRight.add(lblStudio); navRight.add(btnVoltar);
        navbar.add(lblNavLogo, BorderLayout.WEST); navbar.add(navCenter, BorderLayout.CENTER); navbar.add(navRight, BorderLayout.EAST);
        mainBg.add(navbar, BorderLayout.NORTH);

        // ==========================================
        // 2. CONTEÚDO PRINCIPAL (Formulário)
        // ==========================================
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 80, 50, 80));

        JPanel titleWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0)); titleWrapper.setOpaque(false); titleWrapper.setMaximumSize(new Dimension(9999, 50));
        JLabel lblTitle = new JLabel("Estoque"); lblTitle.setFont(new Font("Segoe UI Black", Font.BOLD, 36)); lblTitle.setForeground(Color.decode("#FF6090"));
        titleWrapper.add(lblTitle); contentPanel.add(titleWrapper); contentPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        JPanel formPanel = new JPanel(new GridBagLayout()); formPanel.setOpaque(false); formPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        GridBagConstraints gbc = new GridBagConstraints(); gbc.fill = GridBagConstraints.HORIZONTAL; gbc.insets = new Insets(8, 5, 8, 5);
        Font labelFont = new Font("Segoe UI Black", Font.BOLD, 14); Color labelColor = Color.decode("#D81B60"); Dimension fieldSize = new Dimension(320, 35);

        txtId = new JTextField(); txtId.setEditable(false); txtId.setBackground(Color.decode("#F5F5F5"));
        txtNome = new JTextField(); txtValor = new JTextField(); txtQuantidade = new JTextField();
        txtCapacidade = new JTextField(); txtCapacidade.setToolTipText("Ex: 300ml, 1kg, 600g, 10 unidades");

        adicionarCampo(formPanel, gbc, 0, "Código:", txtId, labelFont, labelColor, fieldSize);
        adicionarCampo(formPanel, gbc, 1, "Nome do Insumo:", txtNome, labelFont, labelColor, fieldSize);
        adicionarCampo(formPanel, gbc, 2, "Valor:", txtValor, labelFont, labelColor, fieldSize);
        adicionarCampo(formPanel, gbc, 3, "Quantidade em Estoque:", txtQuantidade, labelFont, labelColor, fieldSize);
        adicionarCampo(formPanel, gbc, 4, "Capacidade:", txtCapacidade, labelFont, labelColor, fieldSize);

        JPanel crudButtons = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0)); crudButtons.setOpaque(false);
        RoundedButton btnCadastrar = new RoundedButton("Cadastrar"); RoundedButton btnAtualizar = new RoundedButton("Atualizar");
        RoundedButton btnExcluir = new RoundedButton("Excluir"); RoundedButton btnLimpar = new RoundedButton("Limpar");

        RoundedButton[] botoes = {btnCadastrar, btnAtualizar, btnExcluir, btnLimpar};
        for (RoundedButton btn : botoes) { btn.setPreferredSize(new Dimension(100, 35)); btn.setFont(new Font("Segoe UI", Font.BOLD, 12)); crudButtons.add(btn); }
        gbc.gridx = 1; gbc.gridy = 5; gbc.insets = new Insets(20, 5, 0, 5); formPanel.add(crudButtons, gbc);
        
        JPanel formWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0)); formWrapper.setOpaque(false); formWrapper.setMaximumSize(new Dimension(9999, 300));
        formWrapper.add(formPanel); contentPanel.add(formWrapper); contentPanel.add(Box.createRigidArea(new Dimension(0, 50)));

        // ==========================================
        // 3. TABELA DE ESTOQUE
        // ==========================================
        JPanel searchBar = new JPanel(new BorderLayout()); searchBar.setOpaque(false); searchBar.setMaximumSize(new Dimension(9999, 40));
        JPanel pnlBusca = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0)); pnlBusca.setOpaque(false);
        JLabel lblBusca = new JLabel("Buscar:"); lblBusca.setFont(labelFont); lblBusca.setForeground(labelColor);
        JTextField txtBusca = new JTextField(20); txtBusca.setBorder(BorderFactory.createLineBorder(Color.decode("#FFCDD2"), 2, true)); txtBusca.setPreferredSize(new Dimension(200, 35));
        pnlBusca.add(lblBusca); pnlBusca.add(txtBusca);
        JLabel lblFiltrar = new JLabel("Filtrar ≡"); lblFiltrar.setFont(labelFont); lblFiltrar.setForeground(labelColor);
        searchBar.add(pnlBusca, BorderLayout.WEST); searchBar.add(lblFiltrar, BorderLayout.EAST); contentPanel.add(searchBar); contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        String[] colunas = {"Código", "Nome do Insumo", "Valor", "Quantidade", "Capacidade"};
        model = new DefaultTableModel(new Object[][]{}, colunas) { @Override public boolean isCellEditable(int row, int col) { return false; } };
        table = new JTable(model);
        
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int linha = table.getSelectedRow();
                if (linha != -1) {
                    txtId.setText(table.getValueAt(linha, 0).toString());
                    txtNome.setText(table.getValueAt(linha, 1).toString());
                    txtValor.setText(table.getValueAt(linha, 2).toString());
                    txtQuantidade.setText(table.getValueAt(linha, 3).toString());
                    txtCapacidade.setText(table.getValueAt(linha, 4).toString());
                    
                    // Busca o rendimento real no banco de dados invisivelmente
                    int idSelecionado = Integer.parseInt(txtId.getText());
                    rendimentoOcultoSelecionado = insumoController.buscarRendimento(idSelecionado);
                }
            }
        });
        
        table.setRowHeight(40); table.setSelectionBackground(Color.decode("#FFCDD2")); table.setSelectionForeground(Color.decode("#D81B60"));
        table.getTableHeader().setFont(new Font("Segoe UI Black", Font.BOLD, 12)); table.getTableHeader().setBackground(Color.decode("#F5F5F5")); table.getTableHeader().setPreferredSize(new Dimension(100, 40));
        JScrollPane scrollTable = new JScrollPane(table); scrollTable.setBorder(BorderFactory.createLineBorder(Color.decode("#F48FB1"), 6)); 
        JPanel tableContainer = new JPanel(new BorderLayout()); tableContainer.setOpaque(false); tableContainer.setMaximumSize(new Dimension(9999, 400));
        tableContainer.setPreferredSize(new Dimension(1000, 300)); tableContainer.add(scrollTable, BorderLayout.CENTER);
        contentPanel.add(tableContainer); contentPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        JScrollPane mainScroll = new JScrollPane(contentPanel); mainScroll.setBorder(null); mainScroll.setOpaque(false); mainScroll.getViewport().setOpaque(false); mainScroll.getVerticalScrollBar().setUnitIncrement(20); 
        mainBg.add(mainScroll, BorderLayout.CENTER); add(mainBg);

        // ==========================================
        // 4. LÓGICA REAPROVEITANDO O CONTROLLER!
        // ==========================================
        
        btnCadastrar.addActionListener(e -> {
            try {
                // Passamos "1" como rendimento padrão para não quebrar o banco caso cadastrem por aqui
                insumoController.cadastrarEstoque(txtNome.getText(), txtValor.getText(), txtQuantidade.getText(), txtCapacidade.getText(), "1");
                JOptionPane.showMessageDialog(this, "Produto adicionado ao estoque com sucesso!");
                carregarTabela(); limparCampos();
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE); }
        });

        btnAtualizar.addActionListener(e -> {
            if (txtId.getText().isEmpty()) { JOptionPane.showMessageDialog(this, "Selecione um item para atualizar.", "Aviso", JOptionPane.WARNING_MESSAGE); return; }
            try {
                int id = Integer.parseInt(txtId.getText());
                // Reenviamos o rendimento que resgatamos no clique da tabela
                insumoController.atualizarInsumo(id, txtNome.getText(), txtValor.getText(), txtQuantidade.getText(), txtCapacidade.getText(), String.valueOf(rendimentoOcultoSelecionado));
                JOptionPane.showMessageDialog(this, "Estoque atualizado com sucesso!");
                carregarTabela(); limparCampos();
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE); }
        });

        btnExcluir.addActionListener(e -> {
            if (txtId.getText().isEmpty()) return;
            if (JOptionPane.showConfirmDialog(this, "Excluir este item do estoque?", "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                try {
                    int id = Integer.parseInt(txtId.getText());
                    insumoController.excluirInsumo(id);
                    JOptionPane.showMessageDialog(this, "Item excluído do estoque com sucesso!");
                    carregarTabela(); limparCampos();
                } catch (Exception ex) { JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE); }
            }
        });
        
        btnLimpar.addActionListener(e -> limparCampos());

        carregarTabela();
    }

    private void limparCampos() {
        txtId.setText(""); txtNome.setText(""); txtValor.setText("");
        txtQuantidade.setText(""); txtCapacidade.setText("");
        rendimentoOcultoSelecionado = 1; // Reseta a variável
        table.clearSelection();
    }

    private void carregarTabela() {
        model.setRowCount(0); 
        try {
            List<Insumo> lista = insumoController.listarEstoque();
            for (Insumo i : lista) {
                model.addRow(new Object[]{
                    i.getIdInsumo(), i.getNome(), i.getValor(), i.getQuantidade(), i.getCapacidade()
                });
            }
        } catch (Exception e) {}
    }

    private void adicionarCampo(JPanel painel, GridBagConstraints gbc, int linha, String textoLabel, JTextField campo, Font fonteLabel, Color corLabel, Dimension tamanhoCampo) {
        JLabel lbl = new JLabel(textoLabel); lbl.setFont(fonteLabel); lbl.setForeground(corLabel); lbl.setHorizontalAlignment(SwingConstants.RIGHT);
        gbc.gridx = 0; gbc.gridy = linha; gbc.weightx = 0.2; painel.add(lbl, gbc);
        campo.setPreferredSize(tamanhoCampo); campo.setBorder(BorderFactory.createLineBorder(Color.decode("#FFCDD2"), 2, true));
        gbc.gridx = 1; gbc.weightx = 0.8; painel.add(campo, gbc);
    }

    public static void main(String[] args) {
        ThemeUtil.setupTheme(); SwingUtilities.invokeLater(() -> new EstoqueView("RhaianeAll", "Madame Studio").setVisible(true));
    }
}