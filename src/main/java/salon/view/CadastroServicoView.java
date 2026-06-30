package salon.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import salon.util.ThemeUtil;
import salon.view.components.BackgroundPanel;
import salon.view.components.RoundedButton;
import salon.view.components.RoundedPanel;

import salon.controller.ServicoController;
import salon.controller.ServicoInsumoController;
import salon.controller.InsumoController;
import salon.model.Servico;
import salon.model.Insumo;
import salon.model.ServicoInsumo;

public class CadastroServicoView extends JFrame {

    private String nomeUsuario;
    private String nomeEmpresa;
    private int idEmpresaSelecionada = 1; // Fixo para testes

    private JTextField txtCodigo, txtNome, txtValor, txtDuracao;
    private JTable tabelaInsumos, tabelaPrincipal;
    private DefaultTableModel modelInsumos, modelPrincipal;

    private ServicoController servicoController = new ServicoController();
    private ServicoInsumoController servicoInsumoController = new ServicoInsumoController();
    private InsumoController insumoController = new InsumoController();
    
    // Guarda a lista de insumos reais do banco para podermos buscar os IDs depois
    private List<Insumo> insumosDisponiveisNoBanco;

    public CadastroServicoView(String nomeUsuario, String nomeEmpresa) {
        this.nomeUsuario = nomeUsuario;
        this.nomeEmpresa = nomeEmpresa;

        setTitle("Let's Salon - Cadastro de Serviços");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1024, 768));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        BackgroundPanel mainBg = new BackgroundPanel("/images/grafico_apoio.png", new BorderLayout());

        // ==========================================
        // NAVBAR SUPERIOR (Omitido código visual básico para brevidade)
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
        // FORMULÁRIO BÁSICO E TABELAS
        // ==========================================
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 80, 50, 80));

        JLabel lblTitle = new JLabel("Cadastro de Serviços");
        lblTitle.setFont(new Font("Segoe UI Black", Font.BOLD, 36));
        lblTitle.setForeground(Color.decode("#FF6090"));
        contentPanel.add(lblTitle); contentPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        JPanel splitPanel = new JPanel(new BorderLayout(50, 0)); splitPanel.setOpaque(false); splitPanel.setMaximumSize(new Dimension(1200, 300));
        JPanel formLeft = new JPanel(new GridBagLayout()); formLeft.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints(); gbc.fill = GridBagConstraints.HORIZONTAL; gbc.insets = new Insets(8, 5, 8, 5);
        Font labelFont = new Font("Segoe UI Black", Font.BOLD, 14); Color labelColor = Color.decode("#D81B60"); Dimension fieldSize = new Dimension(280, 35);

        txtCodigo = new JTextField(); txtCodigo.setEditable(false); txtCodigo.setBackground(Color.decode("#F5F5F5"));
        txtNome = new JTextField(); txtValor = new JTextField(); txtDuracao = new JTextField();

        adicionarCampo(formLeft, gbc, 0, "Código:", txtCodigo, labelFont, labelColor, fieldSize);
        adicionarCampo(formLeft, gbc, 1, "Nome do Serviço:", txtNome, labelFont, labelColor, fieldSize);
        adicionarCampo(formLeft, gbc, 2, "Valor (R$):", txtValor, labelFont, labelColor, fieldSize);
        adicionarCampo(formLeft, gbc, 3, "Duração Aprox.:", txtDuracao, labelFont, labelColor, fieldSize);

        JPanel crudButtons = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0)); crudButtons.setOpaque(false);
        RoundedButton btnCadastrar = new RoundedButton("Cadastrar"); RoundedButton btnAtualizar = new RoundedButton("Atualizar");
        RoundedButton btnExcluir = new RoundedButton("Excluir"); RoundedButton btnLimpar = new RoundedButton("Limpar");
        RoundedButton[] botoes = {btnCadastrar, btnAtualizar, btnExcluir, btnLimpar};
        for (RoundedButton btn : botoes) { btn.setPreferredSize(new Dimension(100, 35)); btn.setFont(new Font("Segoe UI", Font.BOLD, 12)); crudButtons.add(btn); }
        gbc.gridx = 1; gbc.gridy = 4; gbc.insets = new Insets(20, 5, 0, 5); formLeft.add(crudButtons, gbc);
        splitPanel.add(formLeft, BorderLayout.WEST);

        // ==========================================
        // TABELA DE INSUMOS COM COMBOBOX DINÂMICO
        // ==========================================
        JPanel painelInsumos = new JPanel(new BorderLayout(0, 10)); painelInsumos.setOpaque(false);
        JPanel headerInsumos = new JPanel(new BorderLayout()); headerInsumos.setOpaque(false);
        JLabel lblInsumosTitulo = new JLabel("Insumos Necessários (Opcional):"); lblInsumosTitulo.setFont(labelFont); lblInsumosTitulo.setForeground(labelColor);
        JPanel btnAddRemovePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0)); btnAddRemovePanel.setOpaque(false);
        JButton btnAddLinha = new JButton("+"); JButton btnRemoverLinha = new JButton("-");
        estilizarBotaoPequeno(btnAddLinha); estilizarBotaoPequeno(btnRemoverLinha);
        btnAddRemovePanel.add(btnAddLinha); btnAddRemovePanel.add(btnRemoverLinha);
        headerInsumos.add(lblInsumosTitulo, BorderLayout.WEST); headerInsumos.add(btnAddRemovePanel, BorderLayout.EAST);
        painelInsumos.add(headerInsumos, BorderLayout.NORTH);

        String[] colunasInsumos = {"Insumo", "Qtd. Necessária"};
        modelInsumos = new DefaultTableModel(new Object[][]{}, colunasInsumos);
        tabelaInsumos = new JTable(modelInsumos); tabelaInsumos.setRowHeight(30); tabelaInsumos.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE); 
        
        // --- COLOCANDO OS INSUMOS DO BANCO NA CAIXINHA DE SELEÇÃO DA TABELA ---
        JComboBox<String> cbInsumos = new JComboBox<>();
        try {
            insumosDisponiveisNoBanco = insumoController.listarEstoque();
            cbInsumos.addItem("Selecione...");
            for (Insumo i : insumosDisponiveisNoBanco) {
                cbInsumos.addItem(i.getNome());
            }
        } catch (Exception e) {}
        
        TableColumn colInsumo = tabelaInsumos.getColumnModel().getColumn(0);
        colInsumo.setCellEditor(new DefaultCellEditor(cbInsumos)); // Transforma a coluna 0 em ComboBox!
        // -----------------------------------------------------------------------

        JScrollPane scrollInsumos = new JScrollPane(tabelaInsumos); scrollInsumos.setBorder(BorderFactory.createLineBorder(Color.decode("#FFCDD2"), 2));
        painelInsumos.add(scrollInsumos, BorderLayout.CENTER);

        btnAddLinha.addActionListener(e -> modelInsumos.addRow(new Object[]{"Selecione...", "1"}));
        btnRemoverLinha.addActionListener(e -> { int linha = tabelaInsumos.getSelectedRow(); if (linha != -1) modelInsumos.removeRow(linha); });
        splitPanel.add(painelInsumos, BorderLayout.CENTER); contentPanel.add(splitPanel); contentPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        // ==========================================
        // TABELA PRINCIPAL DE SERVIÇOS
        // ==========================================
        String[] colunasPrincipal = {"Código", "Nome do Serviço", "Valor", "Duração Aprox.", "Qtd. Insumos Usados"};
        modelPrincipal = new DefaultTableModel(new Object[][]{}, colunasPrincipal) { @Override public boolean isCellEditable(int row, int col) { return false; } };
        tabelaPrincipal = new JTable(modelPrincipal); tabelaPrincipal.setRowHeight(40); tabelaPrincipal.setSelectionBackground(Color.decode("#FFCDD2"));
        tabelaPrincipal.setSelectionForeground(Color.decode("#D81B60")); tabelaPrincipal.getTableHeader().setFont(new Font("Segoe UI Black", Font.BOLD, 12));
        tabelaPrincipal.getTableHeader().setBackground(Color.decode("#F5F5F5"));
        
        // EVENTO DE CLIQUE NA TABELA PRINCIPAL (Sobe dados para o Form e para a Tabelinha)
        tabelaPrincipal.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int linha = tabelaPrincipal.getSelectedRow();
                if (linha != -1) {
                    txtCodigo.setText(tabelaPrincipal.getValueAt(linha, 0).toString());
                    txtNome.setText(tabelaPrincipal.getValueAt(linha, 1).toString());
                    txtValor.setText(tabelaPrincipal.getValueAt(linha, 2).toString());
                    txtDuracao.setText(tabelaPrincipal.getValueAt(linha, 3).toString());
                    
                    carregarInsumosDoServico(Integer.parseInt(txtCodigo.getText())); // Puxa os insumos dele!
                }
            }
        });

        JScrollPane scrollTable = new JScrollPane(tabelaPrincipal); scrollTable.setBorder(BorderFactory.createLineBorder(Color.decode("#F48FB1"), 6)); 
        JPanel tableContainer = new JPanel(new BorderLayout()); tableContainer.setOpaque(false); tableContainer.setMaximumSize(new Dimension(9999, 400));
        tableContainer.setPreferredSize(new Dimension(1000, 300)); tableContainer.add(scrollTable, BorderLayout.CENTER);
        contentPanel.add(tableContainer); contentPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        JScrollPane mainScroll = new JScrollPane(contentPanel); mainScroll.setBorder(null); mainScroll.setOpaque(false); mainScroll.getViewport().setOpaque(false); mainScroll.getVerticalScrollBar().setUnitIncrement(20); 
        mainBg.add(mainScroll, BorderLayout.CENTER); add(mainBg);

        // ==========================================
        // LÓGICA DO CRUD E RELAÇÃO MESTRE-DETALHE
        // ==========================================
        
        btnCadastrar.addActionListener(e -> {
            try {
                // 1. Salva o serviço e resgata o ID gerado
                int servicoId = servicoController.cadastrarServico(txtNome.getText(), txtValor.getText(), txtDuracao.getText(), idEmpresaSelecionada);
                
                // 2. Salva os insumos vinculados a esse ID
                salvarInsumosDaTabela(servicoId);
                
                JOptionPane.showMessageDialog(this, "Serviço e Insumos cadastrados com sucesso!");
                limparCampos(); carregarTabelaPrincipal();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnAtualizar.addActionListener(e -> {
            if (txtCodigo.getText().isEmpty()) { JOptionPane.showMessageDialog(this, "Selecione um serviço para atualizar."); return; }
            try {
                int servicoId = Integer.parseInt(txtCodigo.getText());
                // 1. Atualiza dados básicos
                servicoController.atualizarServico(servicoId, txtNome.getText(), txtValor.getText(), txtDuracao.getText());
                // 2. Limpa os insumos velhos e salva os novos da tabela
                servicoInsumoController.limparInsumosDoServico(servicoId);
                salvarInsumosDaTabela(servicoId);
                
                JOptionPane.showMessageDialog(this, "Serviço atualizado com sucesso!");
                limparCampos(); carregarTabelaPrincipal();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnExcluir.addActionListener(e -> {
            if (txtCodigo.getText().isEmpty()) return;
            if (JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir?", "Aviso", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                int servicoId = Integer.parseInt(txtCodigo.getText());
                // Apaga os insumos filhos primeiro (para evitar erro de chave estrangeira), depois apaga o serviço
                servicoInsumoController.limparInsumosDoServico(servicoId);
                servicoController.excluirServico(servicoId);
                limparCampos(); carregarTabelaPrincipal();
            }
        });

        btnLimpar.addActionListener(e -> limparCampos());
        carregarTabelaPrincipal(); // Inicia com a tabela cheia
    }

    // ==========================================
    // MÉTODOS DE CONTROLE INTERNO
    // ==========================================
    
    // Método que varre a tabelinha e salva linha por linha no banco
    private void salvarInsumosDaTabela(int servicoId) {
        if (tabelaInsumos.isEditing()) tabelaInsumos.getCellEditor().stopCellEditing(); // Força a gravar se o usuário estiver com o cursor piscando
        
        for (int i = 0; i < modelInsumos.getRowCount(); i++) {
            String nomeInsumo = (String) modelInsumos.getValueAt(i, 0);
            String qtd = (String) modelInsumos.getValueAt(i, 1);
            
            if (nomeInsumo != null && !nomeInsumo.equals("Selecione...") && qtd != null && !qtd.isEmpty()) {
                int insumoId = obterIdInsumoPorNome(nomeInsumo);
                if (insumoId != -1) {
                    servicoInsumoController.vincularInsumo(servicoId, insumoId, qtd);
                }
            }
        }
    }

    private void carregarTabelaPrincipal() {
        modelPrincipal.setRowCount(0);
        try {
            List<Servico> lista = servicoController.listarServicos(idEmpresaSelecionada);
            for (Servico s : lista) {
                // Descobre quantos insumos esse serviço gasta só para exibir na tela
                int qtdInsumos = servicoInsumoController.listarInsumosDoServico(s.getIdServico()).size();
                modelPrincipal.addRow(new Object[]{ s.getIdServico(), s.getNome(), s.getValor(), s.getDuracao(), qtdInsumos + " itens" });
            }
        } catch (Exception e) {}
    }

    private void carregarInsumosDoServico(int servicoId) {
        modelInsumos.setRowCount(0);
        try {
            List<ServicoInsumo> insumosVinculados = servicoInsumoController.listarInsumosDoServico(servicoId);
            for (ServicoInsumo si : insumosVinculados) {
                String nome = obterNomeInsumoPorId(si.getInsumoId());
                modelInsumos.addRow(new Object[]{nome, String.valueOf(si.getQuantidadeNecessaria())});
            }
        } catch (Exception e) {}
    }

    private int obterIdInsumoPorNome(String nome) {
        if (insumosDisponiveisNoBanco == null) return -1;
        for (Insumo i : insumosDisponiveisNoBanco) {
            if (i.getNome().equals(nome)) return i.getIdInsumo();
        }
        return -1;
    }

    private String obterNomeInsumoPorId(int id) {
        if (insumosDisponiveisNoBanco == null) return "Desconhecido";
        for (Insumo i : insumosDisponiveisNoBanco) {
            if (i.getIdInsumo() == id) return i.getNome();
        }
        return "Desconhecido";
    }

    private void limparCampos() {
        txtCodigo.setText(""); txtNome.setText(""); txtValor.setText(""); txtDuracao.setText("");
        modelInsumos.setRowCount(0); tabelaPrincipal.clearSelection();
    }

    private void adicionarCampo(JPanel painel, GridBagConstraints gbc, int linha, String textoLabel, JTextField campo, Font fonteLabel, Color corLabel, Dimension tamanhoCampo) {
        JLabel lbl = new JLabel(textoLabel); lbl.setFont(fonteLabel); lbl.setForeground(corLabel); lbl.setHorizontalAlignment(SwingConstants.RIGHT);
        gbc.gridx = 0; gbc.gridy = linha; gbc.weightx = 0.2; painel.add(lbl, gbc);
        campo.setPreferredSize(tamanhoCampo); campo.setBorder(BorderFactory.createLineBorder(Color.decode("#FFCDD2"), 2, true));
        gbc.gridx = 1; gbc.weightx = 0.8; painel.add(campo, gbc);
    }
    
    private void estilizarBotaoPequeno(JButton btn) {
        btn.setBackground(Color.decode("#F48FB1")); btn.setForeground(Color.WHITE); btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false); btn.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public static void main(String[] args) {
        ThemeUtil.setupTheme(); SwingUtilities.invokeLater(() -> new CadastroServicoView("RhaianeAll", "Madame Studio").setVisible(true));
    }
}