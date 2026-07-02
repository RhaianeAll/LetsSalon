package salon.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.text.MessageFormat;
import java.util.List;

import salon.controller.RelatorioController;
import salon.view.components.BackgroundPanel;
import salon.view.components.RoundedButton;
import salon.view.components.RoundedPanel;

public class RelatorioAtendimentosView extends JFrame {

    private String nomeUsuario;
    private String nomeEmpresa;
    
    private JTable tabelaRelatorio;
    private DefaultTableModel modelRelatorio;
    private TableRowSorter<DefaultTableModel> sorter;
    private JTextField txtBusca;
    
    private RelatorioController relatorioController = new RelatorioController();

    public RelatorioAtendimentosView(String nomeUsuario, String nomeEmpresa) {
        this.nomeUsuario = nomeUsuario;
        this.nomeEmpresa = nomeEmpresa;

        setTitle("Let's Salon - Registro de Atendimentos");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        BackgroundPanel mainBg = new BackgroundPanel("/images/grafico_apoio.png", new BorderLayout());
        setContentPane(mainBg);

        // --- NAVBAR SUPERIOR --- (Mesmo padrão)
        JPanel navbar = new JPanel(new BorderLayout());
        navbar.setBackground(Color.decode("#F48FB1"));
        navbar.setPreferredSize(new Dimension(0, 70));
        navbar.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));

        JLabel lblNavLogo = new JLabel("LET'S SALON 🌿"); lblNavLogo.setFont(new Font("Segoe UI Black", Font.BOLD, 22)); lblNavLogo.setForeground(Color.WHITE);
        RoundedPanel pillUser = new RoundedPanel(30, Color.decode("#FF6090")); pillUser.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 5));
        JLabel lblNavName = new JLabel(nomeUsuario); lblNavName.setFont(new Font("Segoe UI", Font.BOLD, 16)); lblNavName.setForeground(Color.WHITE);
        pillUser.add(lblNavName);
        
        JPanel navCenter = new JPanel(new FlowLayout(FlowLayout.CENTER)); navCenter.setOpaque(false); navCenter.add(pillUser);
        JPanel navRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0)); navRight.setOpaque(false);
        JLabel lblStudio = new JLabel(nomeEmpresa); lblStudio.setFont(new Font("Segoe UI", Font.ITALIC, 18)); lblStudio.setForeground(Color.WHITE);
        
        JButton btnVoltar = new JButton("↩ Voltar"); btnVoltar.setContentAreaFilled(false); btnVoltar.setBorderPainted(false);
        btnVoltar.setFont(new Font("Segoe UI", Font.BOLD, 14)); btnVoltar.setForeground(Color.WHITE); btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVoltar.addActionListener(e -> { this.dispose(); new RelatoriosMenuView(nomeUsuario, nomeEmpresa).setVisible(true); });
        
        navRight.add(lblStudio); navRight.add(btnVoltar);
        navbar.add(lblNavLogo, BorderLayout.WEST); navbar.add(navCenter, BorderLayout.CENTER); navbar.add(navRight, BorderLayout.EAST);
        mainBg.add(navbar, BorderLayout.NORTH);

        // --- CONTEÚDO PRINCIPAL ---
        JPanel contentPanel = new JPanel(new BorderLayout(0, 20));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 40, 50));

        // Cabeçalho e Título
        JPanel headerPanel = new JPanel(new BorderLayout()); headerPanel.setOpaque(false);
        JLabel lblTitle = new JLabel("Registro de Atendimentos");
        lblTitle.setFont(new Font("Segoe UI Black", Font.BOLD, 36)); lblTitle.setForeground(Color.decode("#FF6090"));
        headerPanel.add(lblTitle, BorderLayout.NORTH);

        // Barra de Busca e Botão PDF
        JPanel toolsPanel = new JPanel(new BorderLayout()); toolsPanel.setOpaque(false); toolsPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        
        JPanel pnlBusca = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0)); pnlBusca.setOpaque(false);
        JLabel lblBusca = new JLabel("Buscar:"); lblBusca.setFont(new Font("Segoe UI Black", Font.BOLD, 16)); lblBusca.setForeground(Color.decode("#D81B60"));
        txtBusca = new JTextField(25); txtBusca.setBorder(BorderFactory.createLineBorder(Color.decode("#FFCDD2"), 2, true));
        
        // Filtro Dinâmico
        txtBusca.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filtrar(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filtrar(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filtrar(); }
        });
        
        pnlBusca.add(lblBusca); pnlBusca.add(txtBusca);
        toolsPanel.add(pnlBusca, BorderLayout.WEST);

        // Botão Exportar PDF / Imprimir
        RoundedButton btnPrint = new RoundedButton("🖨️ Salvar PDF / Imprimir");
        btnPrint.setPreferredSize(new Dimension(200, 35));
        btnPrint.addActionListener(e -> imprimirRelatorio());
        toolsPanel.add(btnPrint, BorderLayout.EAST);

        headerPanel.add(toolsPanel, BorderLayout.SOUTH);
        contentPanel.add(headerPanel, BorderLayout.NORTH);

        // --- TABELA ---
        String[] colunas = {"ID", "Cliente", "Funcionário(a)", "Serviço", "Data/Hora", "Status", "Valor do Serviço", "Tempo Estimado"};
        modelRelatorio = new DefaultTableModel(new Object[][]{}, colunas) { 
            @Override public boolean isCellEditable(int r, int c) { return false; } 
        };
        
        tabelaRelatorio = new JTable(modelRelatorio);
        tabelaRelatorio.setRowHeight(35);
        tabelaRelatorio.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabelaRelatorio.getTableHeader().setBackground(Color.decode("#F5F5F5"));
        tabelaRelatorio.setSelectionBackground(Color.decode("#FFCDD2"));
        
        sorter = new TableRowSorter<>(modelRelatorio);
        tabelaRelatorio.setRowSorter(sorter);

        JScrollPane scroll = new JScrollPane(tabelaRelatorio);
        scroll.setBorder(BorderFactory.createLineBorder(Color.decode("#F48FB1"), 4));
        contentPanel.add(scroll, BorderLayout.CENTER);

        mainBg.add(contentPanel, BorderLayout.CENTER);
        
        carregarDados();
    }

    private void filtrar() {
        if (sorter != null) {
            String texto = txtBusca.getText();
            if (texto.trim().length() == 0) { sorter.setRowFilter(null); } 
            else { sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto)); }
        }
    }

    private void carregarDados() {
        modelRelatorio.setRowCount(0);
        try {
            List<Object[]> linhas = relatorioController.getRegistroAtendimentos();
            for (Object[] linha : linhas) {
                modelRelatorio.addRow(linha);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar relatório: " + e.getMessage());
        }
    }

    // --- FUNÇÃO MÁGICA PARA PDF ---
    private void imprimirRelatorio() {
        try {
            MessageFormat header = new MessageFormat("Registro de Atendimentos - Let's Salon");
            MessageFormat footer = new MessageFormat("Página {0}");
            
            // Abre a janela nativa do OS. O usuário pode escolher "Imprimir para PDF" ou usar uma impressora real.
            boolean concluido = tabelaRelatorio.print(JTable.PrintMode.FIT_WIDTH, header, footer);
            
            if (concluido) { JOptionPane.showMessageDialog(this, "Relatório gerado com sucesso!"); }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao gerar PDF/Impressão: " + e.getMessage());
        }
    }
}