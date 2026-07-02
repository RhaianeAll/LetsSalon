package salon.view;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
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
import salon.controller.ServicoInsumoController;
import salon.controller.UsuarioController;

import salon.model.Agendamento;
import salon.model.Funcionario;
import salon.model.Servico;
import salon.model.Usuario;

public class AgendamentosView extends JFrame {

    private String nomeUsuario;
    private String nomeEmpresa;

    private JFormattedTextField txtData, txtHora;
    private JTextField txtIdAgendamento;
    private JTextField txtBusca;
    private TableRowSorter<DefaultTableModel> sorterGeral;

    private JComboBox<Usuario> cbCliente; 
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
    private UsuarioController usuarioController = new UsuarioController();
    private ServicoInsumoController servicoInsumoController = new ServicoInsumoController();

    private Locale localeBR = new Locale("pt", "BR");

    public AgendamentosView(String nomeUsuario, String nomeEmpresa) {
        this.nomeUsuario = nomeUsuario;
        this.nomeEmpresa = nomeEmpresa;
        this.mesAtual = YearMonth.now();

        setTitle("Let's Salon - Agendamentos");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        BackgroundPanel mainBg = new BackgroundPanel("/images/grafico_apoio.png", new BorderLayout());
        setContentPane(mainBg);

        // ==========================================
        // NAVBAR SUPERIOR (RECUPERADA E CORRIGIDA)
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
        
        // --- Redirecionamento para o menu do colaborador ---
        btnVoltar.addActionListener(e -> { 
            this.dispose(); 
            new MainAdministradorView(nomeUsuario, nomeEmpresa).setVisible(true); 
        });
        
        navRight.add(lblStudio); 
        navRight.add(btnVoltar);
        navbar.add(lblNavLogo, BorderLayout.WEST); 
        navbar.add(navCenter, BorderLayout.CENTER); 
        navbar.add(navRight, BorderLayout.EAST);
        
        mainBg.add(navbar, BorderLayout.NORTH); // Adiciona a barra no topo da tela

        // ==========================================
        // CONTEÚDO PRINCIPAL DA TELA
        // ==========================================
        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        contentPanel.setLayout(new BorderLayout(20, 20));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        JPanel topPanel = new JPanel(new GridLayout(1, 2, 50, 0));
        topPanel.setOpaque(false);
        topPanel.add(criarFormulario());
        topPanel.add(criarCalendarioVisual());
        
        JPanel painelBusca = criarBarraDeBusca();

        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.add(criarPainelPendentes());
        bottomPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        bottomPanel.add(painelBusca);
        bottomPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        bottomPanel.add(criarPainelGeral());

        contentPanel.add(topPanel, BorderLayout.NORTH);
        contentPanel.add(bottomPanel, BorderLayout.CENTER);

        JScrollPane scroll = new JScrollPane(contentPanel);
        scroll.setOpaque(false); 
        scroll.getViewport().setOpaque(false); 
        scroll.setBorder(null);
        
        // --- A MÁGICA PARA DEIXAR O SCROLL RÁPIDO E FLUIDO ---
        scroll.getVerticalScrollBar().setUnitIncrement(25); // O número 25 representa a velocidade (pode aumentar se quiser mais rápido)
        
        mainBg.add(scroll, BorderLayout.CENTER);
        
        desenharCalendario();
        carregarComboBoxes();
        carregarTabelas();
    }

    private JPanel criarFormulario() {
        JPanel p = new JPanel(new GridBagLayout()); p.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints(); gbc.fill = GridBagConstraints.HORIZONTAL; gbc.insets = new Insets(8, 5, 8, 5);
        Font labelFont = new Font("Segoe UI Black", Font.BOLD, 14); Color labelColor = Color.decode("#D81B60");

        txtIdAgendamento = new JTextField(); txtIdAgendamento.setVisible(false);
        txtData = criarCampoMascara("##/##/####"); txtHora = criarCampoMascara("##:##");
        cbCliente = new JComboBox<>(); cbFuncionario = new JComboBox<>(); cbServico = new JComboBox<>();
        
        JPanel dataHoraPanel = new JPanel(new GridLayout(1, 4, 10, 0)); dataHoraPanel.setOpaque(false);
        JLabel lblData = new JLabel("Data:"); lblData.setFont(labelFont); lblData.setForeground(labelColor); lblData.setHorizontalAlignment(SwingConstants.RIGHT);
        JLabel lblHora = new JLabel("Hora:"); lblHora.setFont(labelFont); lblHora.setForeground(labelColor); lblHora.setHorizontalAlignment(SwingConstants.RIGHT);
        dataHoraPanel.add(lblData); dataHoraPanel.add(txtData); dataHoraPanel.add(lblHora); dataHoraPanel.add(txtHora);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; p.add(dataHoraPanel, gbc);

        gbc.gridwidth = 1;
        adicionarCombo(p, gbc, 1, "Nome Cliente:", cbCliente, labelFont, labelColor);
        adicionarCombo(p, gbc, 2, "Funcionário(a):", cbFuncionario, labelFont, labelColor);
        adicionarCombo(p, gbc, 3, "Serviço:", cbServico, labelFont, labelColor);

        JPanel crudButtons = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0)); crudButtons.setOpaque(false);
        RoundedButton btnCadastrar = new RoundedButton("Cadastrar"); RoundedButton btnAtualizar = new RoundedButton("Atualizar");
        RoundedButton btnExcluir = new RoundedButton("Excluir"); RoundedButton btnLimpar = new RoundedButton("Limpar");
        
        btnCadastrar.addActionListener(e -> {
            try {
                LocalDate data = LocalDate.parse(txtData.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                LocalTime hora = LocalTime.parse(txtHora.getText(), DateTimeFormatter.ofPattern("HH:mm"));
                Usuario cli = (Usuario) cbCliente.getSelectedItem(); Funcionario fun = (Funcionario) cbFuncionario.getSelectedItem(); Servico ser = (Servico) cbServico.getSelectedItem();
                
                if (cli == null || cli.getIdUsuario() == 0 || fun == null || fun.getIdFuncionario() == 0 || ser == null || ser.getIdServico() == 0) {
                    JOptionPane.showMessageDialog(this, "Selecione opções válidas.", "Aviso", JOptionPane.WARNING_MESSAGE); return;
                }
                
                agendamentoController.solicitarAgendamento(cli.getIdUsuario(), fun.getIdFuncionario(), ser.getIdServico(), data, hora);
                JOptionPane.showMessageDialog(this, "Agendamento solicitado com sucesso!");
                carregarTabelas(); limparCampos();
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE); }
        });

        btnAtualizar.addActionListener(e -> {
            if (txtIdAgendamento.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Selecione um agendamento na tabela primeiro.", "Aviso", JOptionPane.WARNING_MESSAGE); return;
            }
            try {
                int id = Integer.parseInt(txtIdAgendamento.getText());
                LocalDate data = LocalDate.parse(txtData.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                LocalTime hora = LocalTime.parse(txtHora.getText(), DateTimeFormatter.ofPattern("HH:mm"));
                Usuario cli = (Usuario) cbCliente.getSelectedItem(); Funcionario fun = (Funcionario) cbFuncionario.getSelectedItem(); Servico ser = (Servico) cbServico.getSelectedItem();
                
                agendamentoController.atualizarAgendamento(id, cli.getIdUsuario(), fun.getIdFuncionario(), ser.getIdServico(), data, hora);
                JOptionPane.showMessageDialog(this, "Agendamento atualizado com sucesso!");
                carregarTabelas(); limparCampos();
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE); }
        });

        btnExcluir.addActionListener(e -> {
            if (txtIdAgendamento.getText().isEmpty()) return;
            if (JOptionPane.showConfirmDialog(this, "Deseja excluir este agendamento?", "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                agendamentoController.excluirAgendamento(Integer.parseInt(txtIdAgendamento.getText()));
                JOptionPane.showMessageDialog(this, "Agendamento excluído!");
                carregarTabelas(); limparCampos();
            }
        });

        btnLimpar.addActionListener(e -> limparCampos());

        for (RoundedButton btn : new RoundedButton[]{btnCadastrar, btnAtualizar, btnExcluir, btnLimpar}) {
            btn.setPreferredSize(new Dimension(100, 35)); btn.setFont(new Font("Segoe UI", Font.BOLD, 12)); crudButtons.add(btn);
        }
        gbc.gridx = 1; gbc.gridy = 4; gbc.insets = new Insets(20, 5, 0, 5); p.add(crudButtons, gbc);
        return p;
    }

    private JPanel criarCalendarioVisual() {
        RoundedPanel p = new RoundedPanel(30, Color.decode("#FFF0F5"));
        p.setBorder(BorderFactory.createLineBorder(Color.decode("#D81B60"), 3));
        p.setLayout(new BorderLayout());
        
        JPanel header = new JPanel(new BorderLayout()); header.setOpaque(false); header.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        lblMesAno = new JLabel("", SwingConstants.CENTER); lblMesAno.setFont(new Font("Segoe UI Black", Font.BOLD, 22)); lblMesAno.setForeground(Color.decode("#D81B60"));
        JButton btnPrev = new JButton("<"); JButton btnNext = new JButton(">");
        btnPrev.addActionListener(e -> { mesAtual = mesAtual.minusMonths(1); desenharCalendario(); });
        btnNext.addActionListener(e -> { mesAtual = mesAtual.plusMonths(1); desenharCalendario(); });
        header.add(btnPrev, BorderLayout.WEST); header.add(lblMesAno, BorderLayout.CENTER); header.add(btnNext, BorderLayout.EAST);
        p.add(header, BorderLayout.NORTH);

        JPanel body = new JPanel(new BorderLayout()); body.setOpaque(false);
        JPanel diasSemana = new JPanel(new GridLayout(1, 7)); diasSemana.setOpaque(false);
        String[] dias = {"DOM", "SEG", "TER", "QUA", "QUI", "SEX", "SÁB"};
        for (String d : dias) {
            JLabel l = new JLabel(d, SwingConstants.CENTER); l.setFont(new Font("Segoe UI", Font.BOLD, 12)); l.setForeground(Color.decode("#FF6090")); diasSemana.add(l);
        }
        body.add(diasSemana, BorderLayout.NORTH);
        
        panelDiasCalendario = new JPanel(new GridLayout(0, 7, 5, 5)); panelDiasCalendario.setOpaque(false); panelDiasCalendario.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        body.add(panelDiasCalendario, BorderLayout.CENTER);
        p.add(body, BorderLayout.CENTER);
        return p;
    }

    private JPanel criarBarraDeBusca() {
        JPanel searchBar = new JPanel(new BorderLayout()); searchBar.setOpaque(false); 
        searchBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        searchBar.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JPanel pnlBusca = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0)); pnlBusca.setOpaque(false);
        JLabel lblBusca = new JLabel("Buscar (Data, Nome, Serviço):"); 
        lblBusca.setFont(new Font("Segoe UI Black", Font.BOLD, 14)); lblBusca.setForeground(Color.decode("#D81B60"));
        
        txtBusca = new JTextField(25); 
        txtBusca.setBorder(BorderFactory.createLineBorder(Color.decode("#FFCDD2"), 2, true));
        
        txtBusca.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filtrarTabelaGeral(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filtrarTabelaGeral(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filtrarTabelaGeral(); }
        });

        pnlBusca.add(lblBusca); pnlBusca.add(txtBusca); 
        
        JLabel lblFiltrar = new JLabel("Filtrar ≡"); lblFiltrar.setFont(new Font("Segoe UI Black", Font.BOLD, 14)); lblFiltrar.setForeground(Color.decode("#D81B60"));
        searchBar.add(pnlBusca, BorderLayout.WEST); searchBar.add(lblFiltrar, BorderLayout.EAST);
        
        return searchBar;
    }

    private void filtrarTabelaGeral() {
        if (sorterGeral != null) {
            String texto = txtBusca.getText();
            if (texto.trim().length() == 0) {
                sorterGeral.setRowFilter(null);
            } else {
                sorterGeral.setRowFilter(RowFilter.regexFilter("(?i)" + texto));
            }
        }
    }

    private JPanel criarPainelPendentes() {
        JPanel p = new JPanel(new BorderLayout()); p.setOpaque(false);
        JLabel lbl = new JLabel("Confirmações de Agendamentos Pendentes:");
        lbl.setFont(new Font("Segoe UI Black", Font.BOLD, 18)); lbl.setForeground(Color.decode("#D81B60"));
        p.add(lbl, BorderLayout.NORTH);

        String[] col = {"Ação", "Nome do Cliente", "Telefone", "Data", "Hora", "ID"};
        modelPendentes = new DefaultTableModel(new Object[][]{}, col) { @Override public boolean isCellEditable(int r, int c) { return false; } };
        tabelaPendentes = new JTable(modelPendentes);
        tabelaPendentes.setRowHeight(35); tabelaPendentes.getTableHeader().setBackground(Color.decode("#FF6090")); tabelaPendentes.getTableHeader().setForeground(Color.WHITE);
        
        tabelaPendentes.getColumnModel().getColumn(5).setMinWidth(0); tabelaPendentes.getColumnModel().getColumn(5).setMaxWidth(0); tabelaPendentes.getColumnModel().getColumn(5).setWidth(0);

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
                            carregarTabelas(); 
                        } catch (Exception ex) { JOptionPane.showMessageDialog(null, "Erro: " + ex.getMessage()); }
                    }
                }
            }
        });
        
        JScrollPane scroll = new JScrollPane(tabelaPendentes); scroll.setPreferredSize(new Dimension(0, 200));
        p.add(scroll, BorderLayout.CENTER);
        return p;
    }

    private JPanel criarPainelGeral() {
        JPanel p = new JPanel(new BorderLayout()); p.setOpaque(false);
        JLabel lbl = new JLabel("Agenda Geral:");
        lbl.setFont(new Font("Segoe UI Black", Font.BOLD, 18)); lbl.setForeground(Color.decode("#D81B60"));
        p.add(lbl, BorderLayout.NORTH);

        String[] colGeral = {"Data", "Hora", "Cliente", "Funcionário", "Serviço", "Insumos Necessários", "Status", "ID"};
        
        modelGeral = new DefaultTableModel(new Object[][]{}, colGeral) { 
            @Override public boolean isCellEditable(int r, int c) { return c == 6; } 
        };
        
        tabelaGeral = new JTable(modelGeral);
        tabelaGeral.setRowHeight(40); tabelaGeral.setSelectionBackground(Color.decode("#FFCDD2")); tabelaGeral.setSelectionForeground(Color.decode("#D81B60"));
        tabelaGeral.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12)); tabelaGeral.getTableHeader().setBackground(Color.decode("#F5F5F5"));
        
        sorterGeral = new TableRowSorter<>(modelGeral);
        tabelaGeral.setRowSorter(sorterGeral);
        
        tabelaGeral.getColumnModel().getColumn(7).setMinWidth(0); tabelaGeral.getColumnModel().getColumn(7).setMaxWidth(0); tabelaGeral.getColumnModel().getColumn(7).setWidth(0);

        JComboBox<String> cbStatus = new JComboBox<>(new String[]{"Agendado", "Em andamento", "Concluído"});
        tabelaGeral.getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(cbStatus));

        modelGeral.addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE && e.getColumn() == 6) {
                int row = e.getFirstRow();
                int modelRow = tabelaGeral.convertRowIndexToModel(row); 
                String novoStatus = (String) modelGeral.getValueAt(modelRow, 6);
                int idAgendamento = Integer.parseInt(modelGeral.getValueAt(modelRow, 7).toString());
                try {
                    agendamentoController.alterarStatus(idAgendamento, novoStatus);
                } catch (Exception ex) { JOptionPane.showMessageDialog(null, "Erro ao atualizar status: " + ex.getMessage()); }
            }
        });
        
        tabelaGeral.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int linhaView = tabelaGeral.getSelectedRow();
                if (linhaView != -1) {
                    int linha = tabelaGeral.convertRowIndexToModel(linhaView);
                    txtData.setText(modelGeral.getValueAt(linha, 0).toString());
                    txtHora.setText(modelGeral.getValueAt(linha, 1).toString());
                    
                    selecionarItemCombo(cbCliente, modelGeral.getValueAt(linha, 2).toString());
                    selecionarItemCombo(cbFuncionario, modelGeral.getValueAt(linha, 3).toString());
                    selecionarItemCombo(cbServico, modelGeral.getValueAt(linha, 4).toString());
                    
                    txtIdAgendamento.setText(modelGeral.getValueAt(linha, 7).toString());
                }
            }
        });

        JScrollPane scroll = new JScrollPane(tabelaGeral); scroll.setBorder(BorderFactory.createLineBorder(Color.decode("#F48FB1"), 4));
        p.add(scroll, BorderLayout.CENTER);
        return p;
    }

    private void selecionarItemCombo(JComboBox<?> combo, String textoProcurado) {
        for (int i = 0; i < combo.getItemCount(); i++) {
            if (combo.getItemAt(i).toString().equals(textoProcurado)) {
                combo.setSelectedIndex(i);
                return;
            }
        }
    }

    private void carregarComboBoxes() {
        cbCliente.removeAllItems(); cbFuncionario.removeAllItems(); cbServico.removeAllItems();

        Usuario cli = new Usuario(); cli.setIdUsuario(0); cli.setNomeCompleto("Selecione um cliente..."); cbCliente.addItem(cli);
        Funcionario fun = new Funcionario(); fun.setIdFuncionario(0); fun.setNome("Selecione um profissional..."); cbFuncionario.addItem(fun);
        Servico ser = new Servico(); ser.setIdServico(0); ser.setNome("Selecione um serviço..."); cbServico.addItem(ser);

        try {
            for (Usuario u : usuarioController.listarUsuarios()) cbCliente.addItem(u);
            for (Funcionario f : funcionarioController.listarFuncionarios()) cbFuncionario.addItem(f);
            for (Servico s : servicoController.listarServicos()) cbServico.addItem(s);
        } catch (Exception e) {}
    }

    private void desenharCalendario() {
        panelDiasCalendario.removeAll();
        
        String nomeMes = mesAtual.getMonth().getDisplayName(TextStyle.FULL, localeBR).toUpperCase();
        lblMesAno.setText(nomeMes + " " + mesAtual.getYear());

        LocalDate primeiroDia = mesAtual.atDay(1);
        int diaDaSemanaInicio = primeiroDia.getDayOfWeek().getValue() % 7; 
        int diasNoMes = mesAtual.lengthOfMonth();

        for (int i = 0; i < diaDaSemanaInicio; i++) panelDiasCalendario.add(new JLabel(""));

        for (int i = 1; i <= diasNoMes; i++) {
            JButton btnDia = new JButton(String.valueOf(i));
            btnDia.setFont(new Font("Segoe UI", Font.BOLD, 14)); btnDia.setBackground(Color.WHITE); btnDia.setForeground(Color.decode("#D81B60")); 
            btnDia.setFocusPainted(false); btnDia.setBorder(BorderFactory.createLineBorder(Color.decode("#FFCDD2")));
            final int d = i;
            
            btnDia.addActionListener(e -> {
                String dataFormatada = String.format("%02d/%02d/%d", d, mesAtual.getMonthValue(), mesAtual.getYear());
                txtData.setText(dataFormatada);
                txtBusca.setText(dataFormatada); 
            });
            panelDiasCalendario.add(btnDia);
        }
        panelDiasCalendario.revalidate(); panelDiasCalendario.repaint();
    }

    private void carregarTabelas() {
        modelPendentes.setRowCount(0);
        modelGeral.setRowCount(0);
        try {
            for (Agendamento a : agendamentoController.listarConfirmacoesPendentes()) {
                String data = a.getDataAgendamento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                modelPendentes.addRow(new Object[]{"✅ Confirmar", a.getNomeCliente(), a.getTelefoneCliente(), data, a.getHora().toString(), a.getIdAgendamento()});
            }
            
            for (Agendamento a : agendamentoController.listarAgendamentosGerais()) {
                String data = a.getDataAgendamento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                
                int qtdInsumos = 0;
                try { qtdInsumos = servicoInsumoController.listarInsumosDoServico(a.getServicoId()).size(); } catch(Exception ignored) {}
                String textoInsumos = qtdInsumos > 0 ? qtdInsumos + " itens" : "Nenhum";
                
                modelGeral.addRow(new Object[]{data, a.getHora().toString(), a.getNomeCliente(), a.getNomeFuncionario(), a.getNomeServico(), textoInsumos, a.getStatus(), a.getIdAgendamento()});
            }
        } catch (Exception e) {}
    }

    private void limparCampos() {
        txtData.setValue(null); txtHora.setValue(null); txtIdAgendamento.setText("");
        cbCliente.setSelectedIndex(0); cbFuncionario.setSelectedIndex(0); cbServico.setSelectedIndex(0);
        txtBusca.setText(""); 
        tabelaGeral.clearSelection();
    }

    private JFormattedTextField criarCampoMascara(String m) {
        try { MaskFormatter mf = new MaskFormatter(m); mf.setPlaceholderCharacter('_'); return new JFormattedTextField(mf); } 
        catch (Exception e) { return new JFormattedTextField(); }
    }

    private void adicionarCombo(JPanel painel, GridBagConstraints gbc, int linha, String textoLabel, JComboBox combo, Font font, Color cor) {
        JLabel lbl = new JLabel(textoLabel); lbl.setFont(font); lbl.setForeground(cor); lbl.setHorizontalAlignment(SwingConstants.RIGHT);
        gbc.gridx = 0; gbc.gridy = linha; gbc.weightx = 0.3; painel.add(lbl, gbc);
        combo.setPreferredSize(new Dimension(300, 35)); combo.setBorder(BorderFactory.createLineBorder(Color.decode("#FFCDD2"), 2, true));
        combo.setBackground(Color.WHITE);
        gbc.gridx = 1; gbc.weightx = 0.7; painel.add(combo, gbc);
    }
}