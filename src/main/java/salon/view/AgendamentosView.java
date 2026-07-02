package salon.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

import salon.util.ThemeUtil;
import salon.view.components.BackgroundPanel;
import salon.view.components.RoundedButton;
import salon.view.components.RoundedPanel;

import salon.controller.AgendamentoController;
import salon.controller.FuncionarioController;
import salon.controller.ServicoController;
import salon.model.Agendamento;
import salon.model.Funcionario;
import salon.model.Servico;

public class AgendamentosView extends JFrame {

    private String nomeUsuario;
    private String nomeEmpresa;

    private JFormattedTextField txtData, txtHora;
    private JTextField txtNomeCliente, txtIdAgendamento;
    private JComboBox<Funcionario> cbFuncionario;
    private JComboBox<Servico> cbServico;

    private JTable tabelaPendentes, tabelaGeral;
    private DefaultTableModel modelPendentes, modelGeral;

    private YearMonth mesAtual;
    private JPanel panelDiasCalendario;
    private JLabel lblMesAno;

    private AgendamentoController agendamentoController = new AgendamentoController();
    private FuncionarioController funcionarioController = new FuncionarioController();
    private ServicoController servicoController = new ServicoController();

    // Define o idioma para Português do Brasil
    private Locale localeBR = new Locale("pt", "BR");

    public AgendamentosView(String nomeUsuario, String nomeEmpresa) {
        this.nomeUsuario = nomeUsuario;
        this.nomeEmpresa = nomeEmpresa;
        this.mesAtual = YearMonth.now();

        setTitle("Let's Salon - Agendamentos");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1280, 800));
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
        lblNavLogo.setFont(new Font("Segoe UI Black", Font.BOLD, 22)); lblNavLogo.setForeground(Color.WHITE);
        
        RoundedPanel pillUser = new RoundedPanel(30, Color.decode("#FF6090"));
        pillUser.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 5));
        JLabel lblNavName = new JLabel(nomeUsuario); lblNavName.setFont(new Font("Segoe UI", Font.BOLD, 16)); lblNavName.setForeground(Color.WHITE);
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
        // 2. CONTEÚDO PRINCIPAL 
        // ==========================================
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 50, 50));

        JLabel lblTitle = new JLabel("Agendamentos");
        lblTitle.setFont(new Font("Segoe UI Black", Font.BOLD, 42)); lblTitle.setForeground(Color.decode("#FF6090"));
        JPanel titleWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0)); titleWrapper.setOpaque(false); titleWrapper.setAlignmentX(Component.LEFT_ALIGNMENT);
        titleWrapper.add(lblTitle); contentPanel.add(titleWrapper); contentPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // ==========================================
        // 3. TOPO: FORMULÁRIO E CALENDÁRIO
        // ==========================================
        // Aumentei a altura máxima aqui para os botões não cortarem (de 350 para 450)
        JPanel topSection = new JPanel(new GridLayout(1, 2, 50, 0)); topSection.setOpaque(false); 
        topSection.setMaximumSize(new Dimension(Integer.MAX_VALUE, 500));
        topSection.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel formPanel = new JPanel(new GridBagLayout()); formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints(); gbc.fill = GridBagConstraints.HORIZONTAL; gbc.insets = new Insets(8, 5, 8, 5);
        Font labelFont = new Font("Segoe UI Black", Font.BOLD, 14); Color labelColor = Color.decode("#D81B60");

        txtIdAgendamento = new JTextField(); txtIdAgendamento.setVisible(false);
        txtData = new JFormattedTextField(); configurarMascara(txtData, "##/##/####");
        txtHora = new JFormattedTextField(); configurarMascara(txtHora, "##:##");
        txtNomeCliente = new JTextField();
        cbFuncionario = new JComboBox<>();
        cbServico = new JComboBox<>();
        
        JPanel dataHoraPanel = new JPanel(new GridLayout(1, 4, 10, 0)); dataHoraPanel.setOpaque(false);
        JLabel lblData = new JLabel("Data:"); lblData.setFont(labelFont); lblData.setForeground(labelColor); lblData.setHorizontalAlignment(SwingConstants.RIGHT);
        JLabel lblHora = new JLabel("Hora:"); lblHora.setFont(labelFont); lblHora.setForeground(labelColor); lblHora.setHorizontalAlignment(SwingConstants.RIGHT);
        dataHoraPanel.add(lblData); dataHoraPanel.add(txtData); dataHoraPanel.add(lblHora); dataHoraPanel.add(txtHora);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; formPanel.add(dataHoraPanel, gbc);

        gbc.gridwidth = 1;
        adicionarCampo(formPanel, gbc, 1, "Nome Cliente:", txtNomeCliente, labelFont, labelColor);
        adicionarCombo(formPanel, gbc, 2, "Funcionário(a):", cbFuncionario, labelFont, labelColor);
        adicionarCombo(formPanel, gbc, 3, "Serviço:", cbServico, labelFont, labelColor);

        JPanel crudButtons = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0)); crudButtons.setOpaque(false);
        RoundedButton btnCadastrar = new RoundedButton("Cadastrar"); RoundedButton btnAtualizar = new RoundedButton("Atualizar");
        RoundedButton btnExcluir = new RoundedButton("Excluir"); RoundedButton btnLimpar = new RoundedButton("Limpar");
        for (RoundedButton btn : new RoundedButton[]{btnCadastrar, btnAtualizar, btnExcluir, btnLimpar}) {
            btn.setPreferredSize(new Dimension(100, 35)); btn.setFont(new Font("Segoe UI", Font.BOLD, 12)); crudButtons.add(btn);
        }
        gbc.gridx = 1; gbc.gridy = 4; gbc.insets = new Insets(20, 5, 0, 5); formPanel.add(crudButtons, gbc);
        topSection.add(formPanel);

        // --- CALENDÁRIO CUSTOMIZADO ---
        RoundedPanel calendarContainer = new RoundedPanel(30, Color.decode("#FFF0F5"));
        calendarContainer.setBorder(BorderFactory.createLineBorder(Color.decode("#D81B60"), 3));
        calendarContainer.setLayout(new BorderLayout());
        
        JPanel calendarHeader = new JPanel(new BorderLayout()); calendarHeader.setOpaque(false); calendarHeader.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        lblMesAno = new JLabel("", SwingConstants.CENTER); lblMesAno.setFont(new Font("Segoe UI Black", Font.BOLD, 22)); lblMesAno.setForeground(Color.decode("#D81B60"));
        JButton btnPrev = new JButton("<"); JButton btnNext = new JButton(">");
        btnPrev.addActionListener(e -> { mesAtual = mesAtual.minusMonths(1); desenharCalendario(); });
        btnNext.addActionListener(e -> { mesAtual = mesAtual.plusMonths(1); desenharCalendario(); });
        calendarHeader.add(btnPrev, BorderLayout.WEST); calendarHeader.add(lblMesAno, BorderLayout.CENTER); calendarHeader.add(btnNext, BorderLayout.EAST);
        calendarContainer.add(calendarHeader, BorderLayout.NORTH);

        JPanel calendarBody = new JPanel(new BorderLayout()); calendarBody.setOpaque(false);
        JPanel diasSemana = new JPanel(new GridLayout(1, 7)); diasSemana.setOpaque(false);
        String[] dias = {"DOM", "SEG", "TER", "QUA", "QUI", "SEX", "SÁB"};
        for (String d : dias) {
            JLabel l = new JLabel(d, SwingConstants.CENTER); l.setFont(new Font("Segoe UI", Font.BOLD, 12)); l.setForeground(Color.decode("#FF6090")); diasSemana.add(l);
        }
        calendarBody.add(diasSemana, BorderLayout.NORTH);
        
        panelDiasCalendario = new JPanel(new GridLayout(0, 7, 5, 5)); panelDiasCalendario.setOpaque(false); panelDiasCalendario.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        calendarBody.add(panelDiasCalendario, BorderLayout.CENTER);
        calendarContainer.add(calendarBody, BorderLayout.CENTER);
        topSection.add(calendarContainer);
        
        contentPanel.add(topSection);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        // ==========================================
        // 4. TABELA DE CONFIRMAÇÕES PENDENTES
        // ==========================================
        RoundedPanel pendentesContainer = new RoundedPanel(20, Color.decode("#FFCDD2"));
        pendentesContainer.setLayout(new BoxLayout(pendentesContainer, BoxLayout.Y_AXIS));
        pendentesContainer.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        pendentesContainer.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));
        pendentesContainer.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel lblPendentes = new JLabel("Confirmações de Agendamentos Pendentes:");
        lblPendentes.setFont(new Font("Segoe UI Black", Font.BOLD, 18)); lblPendentes.setForeground(Color.WHITE);
        pendentesContainer.add(lblPendentes); pendentesContainer.add(Box.createRigidArea(new Dimension(0, 10)));

        String[] colPendentes = {"Ação", "Nome do Cliente", "Telefone", "Data", "Hora", "ID (Oculto)"};
        modelPendentes = new DefaultTableModel(new Object[][]{}, colPendentes) { @Override public boolean isCellEditable(int r, int c) { return false; } };
        tabelaPendentes = new JTable(modelPendentes);
        tabelaPendentes.setRowHeight(35); tabelaPendentes.getTableHeader().setBackground(Color.decode("#FF6090")); tabelaPendentes.getTableHeader().setForeground(Color.WHITE);
        
        tabelaPendentes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = tabelaPendentes.columnAtPoint(e.getPoint());
                int row = tabelaPendentes.rowAtPoint(e.getPoint());
                if (col == 0 && row != -1) { 
                    int idAgendamento = Integer.parseInt(tabelaPendentes.getValueAt(row, 5).toString());
                    if (JOptionPane.showConfirmDialog(null, "Confirmar este agendamento?", "Aviso", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        try {
                            agendamentoController.aprovarAgendamento(idAgendamento);
                            JOptionPane.showMessageDialog(null, "Agendamento Confirmado!");
                            carregarTabelas();
                        } catch (Exception ex) { JOptionPane.showMessageDialog(null, "Erro: " + ex.getMessage()); }
                    }
                }
            }
        });
        
        JScrollPane scrollPendentes = new JScrollPane(tabelaPendentes);
        pendentesContainer.add(scrollPendentes);
        contentPanel.add(pendentesContainer);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        // ==========================================
        // 5. TABELA PRINCIPAL (Todos os Agendamentos)
        // ==========================================
        JPanel searchBar = new JPanel(new BorderLayout()); searchBar.setOpaque(false); 
        searchBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        searchBar.setAlignmentX(Component.LEFT_ALIGNMENT);
        JPanel pnlBusca = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0)); pnlBusca.setOpaque(false);
        JLabel lblBusca = new JLabel("Buscar:"); lblBusca.setFont(labelFont); lblBusca.setForeground(labelColor);
        JTextField txtBusca = new JTextField(20); txtBusca.setBorder(BorderFactory.createLineBorder(Color.decode("#FFCDD2"), 2, true));
        pnlBusca.add(lblBusca); pnlBusca.add(txtBusca); 
        
        JLabel lblFiltrar = new JLabel("Filtrar ≡"); lblFiltrar.setFont(labelFont); lblFiltrar.setForeground(labelColor);
        searchBar.add(pnlBusca, BorderLayout.WEST); searchBar.add(lblFiltrar, BorderLayout.EAST);
        contentPanel.add(searchBar); contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        String[] colGeral = {"Data", "Hora", "Cliente", "Funcionário", "Serviço", "Quantidade de Insumos Necessários", "Status"};
        modelGeral = new DefaultTableModel(new Object[][]{}, colGeral) { @Override public boolean isCellEditable(int r, int c) { return false; } };
        tabelaGeral = new JTable(modelGeral);
        tabelaGeral.setRowHeight(40); tabelaGeral.setSelectionBackground(Color.decode("#FFCDD2")); tabelaGeral.setSelectionForeground(Color.decode("#D81B60"));
        tabelaGeral.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tabelaGeral.getTableHeader().setBackground(Color.decode("#F5F5F5"));
        
        JScrollPane scrollGeral = new JScrollPane(tabelaGeral); scrollGeral.setBorder(BorderFactory.createLineBorder(Color.decode("#F48FB1"), 4));
        JPanel tableContainer = new JPanel(new BorderLayout()); tableContainer.setOpaque(false); 
        tableContainer.setMaximumSize(new Dimension(Integer.MAX_VALUE, 350)); 
        tableContainer.setPreferredSize(new Dimension(0, 350));
        tableContainer.setAlignmentX(Component.LEFT_ALIGNMENT);
        tableContainer.add(scrollGeral, BorderLayout.CENTER);
        contentPanel.add(tableContainer);

        JScrollPane mainScroll = new JScrollPane(contentPanel); mainScroll.setBorder(null); mainScroll.setOpaque(false); mainScroll.getViewport().setOpaque(false); mainScroll.getVerticalScrollBar().setUnitIncrement(20);
        mainBg.add(mainScroll, BorderLayout.CENTER); add(mainBg);

        // ==========================================
        // 6. EVENTOS DOS BOTÕES
        // ==========================================
        btnCadastrar.addActionListener(e -> {
            try {
                LocalDate data = LocalDate.parse(txtData.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                LocalTime hora = LocalTime.parse(txtHora.getText(), DateTimeFormatter.ofPattern("HH:mm"));
                
                Funcionario funcSelecionado = (Funcionario) cbFuncionario.getSelectedItem();
                Servico servSelecionado = (Servico) cbServico.getSelectedItem();
                
                if (funcSelecionado == null || funcSelecionado.getIdFuncionario() == 0 || servSelecionado == null || servSelecionado.getIdServico() == 0) {
                    JOptionPane.showMessageDialog(this, "Selecione um Funcionário e um Serviço válidos.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                agendamentoController.solicitarAgendamento(1, funcSelecionado.getIdFuncionario(), servSelecionado.getIdServico(), data, hora);
                JOptionPane.showMessageDialog(this, "Agendamento solicitado com sucesso!");
                carregarTabelas(); limparCampos();
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE); }
        });

        btnLimpar.addActionListener(e -> limparCampos());

        desenharCalendario();
        carregarComboBoxes();
        carregarTabelas();
    }

    // ==========================================
    // MÉTODOS DE PREENCHIMENTO E LÓGICA
    // ==========================================
    
    private void carregarComboBoxes() {
        cbFuncionario.removeAllItems();
        cbServico.removeAllItems();

        Funcionario funcPlaceholder = new Funcionario(); funcPlaceholder.setIdFuncionario(0); funcPlaceholder.setNome("Selecione um(a) profissional...");
        cbFuncionario.addItem(funcPlaceholder);

        Servico servPlaceholder = new Servico(); servPlaceholder.setIdServico(0); servPlaceholder.setNome("Selecione um serviço...");
        cbServico.addItem(servPlaceholder);

        try {
            List<Funcionario> listaFuncionarios = funcionarioController.listarFuncionarios();
            for (Funcionario f : listaFuncionarios) { cbFuncionario.addItem(f); }
            
            List<Servico> listaServicos = servicoController.listarServicos();
            for (Servico s : listaServicos) { cbServico.addItem(s); }
        } catch (Exception e) {}
    }

    private void desenharCalendario() {
        panelDiasCalendario.removeAll();
        
        // Formata o mês em português e converte para maiúsculo
        String nomeMes = mesAtual.getMonth().getDisplayName(TextStyle.FULL, localeBR).toUpperCase();
        lblMesAno.setText(nomeMes + " " + mesAtual.getYear());

        LocalDate primeiroDia = mesAtual.atDay(1);
        int diaDaSemanaInicio = primeiroDia.getDayOfWeek().getValue() % 7; 
        int diasNoMes = mesAtual.lengthOfMonth();

        for (int i = 0; i < diaDaSemanaInicio; i++) {
            panelDiasCalendario.add(new JLabel(""));
        }

        for (int i = 1; i <= diasNoMes; i++) {
            JButton btnDia = new JButton(String.valueOf(i));
            btnDia.setFont(new Font("Segoe UI", Font.BOLD, 14));
            btnDia.setBackground(Color.WHITE); 
            
            // Corrige o problema do número invisível
            btnDia.setForeground(Color.decode("#D81B60")); 
            
            btnDia.setFocusPainted(false);
            btnDia.setBorder(BorderFactory.createLineBorder(Color.decode("#FFCDD2")));
            btnDia.setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            final int diaAtual = i;
            btnDia.addActionListener(e -> {
                String dataFormatada = String.format("%02d/%02d/%d", diaAtual, mesAtual.getMonthValue(), mesAtual.getYear());
                txtData.setText(dataFormatada);
            });
            panelDiasCalendario.add(btnDia);
        }

        panelDiasCalendario.revalidate();
        panelDiasCalendario.repaint();
    }

    private void carregarTabelas() {
        modelPendentes.setRowCount(0);
        modelGeral.setRowCount(0);
        try {
            List<Agendamento> pendentes = agendamentoController.listarConfirmacoesPendentes();
            for (Agendamento a : pendentes) {
                String data = a.getDataAgendamento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                modelPendentes.addRow(new Object[]{"✅ Confirmar", a.getNomeCliente(), a.getTelefoneCliente(), data, a.getHora().toString(), a.getIdAgendamento()});
                
                // Preenche a tabela geral com os dados provisórios para você ver funcionando
                modelGeral.addRow(new Object[]{data, a.getHora().toString(), a.getNomeCliente(), a.getNomeFuncionario(), a.getNomeServico(), "N/A", a.getStatus()});
            }
        } catch (Exception e) {}
    }

    private void limparCampos() {
        txtData.setValue(null); txtHora.setValue(null); txtNomeCliente.setText(""); txtIdAgendamento.setText("");
        cbFuncionario.setSelectedIndex(0); cbServico.setSelectedIndex(0);
    }

    private void configurarMascara(JFormattedTextField campo, String mascara) {
        try { MaskFormatter mf = new MaskFormatter(mascara); mf.setPlaceholderCharacter('_'); mf.install(campo); } catch (Exception e) {}
    }

    private void adicionarCampo(JPanel painel, GridBagConstraints gbc, int linha, String textoLabel, JTextField campo, Font font, Color cor) {
        JLabel lbl = new JLabel(textoLabel); lbl.setFont(font); lbl.setForeground(cor); lbl.setHorizontalAlignment(SwingConstants.RIGHT);
        gbc.gridx = 0; gbc.gridy = linha; gbc.weightx = 0.3; painel.add(lbl, gbc);
        campo.setPreferredSize(new Dimension(300, 35)); campo.setBorder(BorderFactory.createLineBorder(Color.decode("#FFCDD2"), 2, true));
        gbc.gridx = 1; gbc.weightx = 0.7; painel.add(campo, gbc);
    }

    private void adicionarCombo(JPanel painel, GridBagConstraints gbc, int linha, String textoLabel, JComboBox combo, Font font, Color cor) {
        JLabel lbl = new JLabel(textoLabel); lbl.setFont(font); lbl.setForeground(cor); lbl.setHorizontalAlignment(SwingConstants.RIGHT);
        gbc.gridx = 0; gbc.gridy = linha; gbc.weightx = 0.3; painel.add(lbl, gbc);
        combo.setPreferredSize(new Dimension(300, 35)); combo.setBorder(BorderFactory.createLineBorder(Color.decode("#FFCDD2"), 2, true));
        combo.setBackground(Color.WHITE);
        gbc.gridx = 1; gbc.weightx = 0.7; painel.add(combo, gbc);
    }

    public static void main(String[] args) {
        ThemeUtil.setupTheme(); SwingUtilities.invokeLater(() -> new AgendamentosView("RhaianeAll", "Madame Studio").setVisible(true));
    }
}