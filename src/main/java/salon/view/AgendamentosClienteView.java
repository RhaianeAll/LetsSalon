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
import salon.controller.UsuarioController;

import salon.model.Agendamento;
import salon.model.Funcionario;
import salon.model.Servico;
import salon.model.Usuario;

public class AgendamentosClienteView extends JFrame {

    private String nomeUsuario;
    private String nomeEmpresa;
    private Usuario clienteLogado;

    private JFormattedTextField txtData, txtHora;
    private JComboBox<Funcionario> cbFuncionario;
    private JComboBox<Servico> cbServico;
    private JTextField txtIdAgendamento;

    private JTable tabelaAgendamentos;
    private DefaultTableModel modelTabela;

    private YearMonth mesAtual;
    private JPanel panelDiasCalendario;
    private JLabel lblMesAno;

    private AgendamentoController agendamentoController = new AgendamentoController();
    private FuncionarioController funcionarioController = new FuncionarioController();
    private ServicoController servicoController = new ServicoController();
    private UsuarioController usuarioController = new UsuarioController();

    private Locale localeBR = new Locale("pt", "BR");

    public AgendamentosClienteView(String nomeUsuario, String nomeEmpresa) {
        this.nomeUsuario = nomeUsuario;
        this.nomeEmpresa = nomeEmpresa;
        this.mesAtual = YearMonth.now();
        
        this.clienteLogado = usuarioController.buscarUsuarioPorLogin(nomeUsuario);

        setTitle("Let's Salon - Meus Agendamentos");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1024, 768));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        BackgroundPanel mainBg = new BackgroundPanel("/images/grafico_apoio.png", new BorderLayout());
        setContentPane(mainBg);

        // ==========================================
        // 1. NAVBAR SUPERIOR E PÍLULA DE PERFIL
        // ==========================================
        JPanel navbar = new JPanel(new BorderLayout());
        navbar.setBackground(Color.decode("#F48FB1"));
        navbar.setPreferredSize(new Dimension(0, 70));
        navbar.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));

        JLabel lblNavLogo = new JLabel("LET'S SALON 🌿");
        lblNavLogo.setFont(new Font("Segoe UI Black", Font.BOLD, 22)); lblNavLogo.setForeground(Color.WHITE);

        RoundedPanel pillUser = new RoundedPanel(30, Color.decode("#FF6090"));
        pillUser.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 5));
        
        // ---> A MÁGICA FOI MOVIDA PARA CÁ <---
        pillUser.setCursor(new Cursor(Cursor.HAND_CURSOR));
        pillUser.setToolTipText("Clique para editar seu perfil e foto");
        pillUser.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { 
                dispose(); 
                new EditarDadosClienteView(nomeUsuario, nomeEmpresa).setVisible(true); 
            }
        });

        JLabel lblNavName = new JLabel(clienteLogado != null ? clienteLogado.getNomeCompleto() : nomeUsuario);
        lblNavName.setFont(new Font("Segoe UI", Font.BOLD, 16)); lblNavName.setForeground(Color.WHITE);
        pillUser.add(lblNavName);
        
        JPanel navCenter = new JPanel(new FlowLayout(FlowLayout.CENTER)); navCenter.setOpaque(false); navCenter.add(pillUser);

        JPanel navRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0)); navRight.setOpaque(false);
        JLabel lblStudio = new JLabel(nomeEmpresa); lblStudio.setFont(new Font("Segoe UI", Font.ITALIC, 18)); lblStudio.setForeground(Color.WHITE);
        JButton btnSair = new JButton("🚪 Sair"); btnSair.setContentAreaFilled(false); btnSair.setBorderPainted(false);
        btnSair.setFont(new Font("Segoe UI", Font.BOLD, 14)); btnSair.setForeground(Color.WHITE); btnSair.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSair.addActionListener(e -> { this.dispose(); new AuthView().setVisible(true); }); 
        
        navRight.add(lblStudio); navRight.add(btnSair);
        navbar.add(lblNavLogo, BorderLayout.WEST); navbar.add(navCenter, BorderLayout.CENTER); navbar.add(navRight, BorderLayout.EAST);
        mainBg.add(navbar, BorderLayout.NORTH);

        // ==========================================
        // 2. CONTEÚDO PRINCIPAL (Scroll)
        // ==========================================
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 60, 40, 60));

        // --- TOPO: PERFIL E NOTIFICAÇÕES ---
        JPanel topSection = new JPanel(new GridLayout(1, 2, 50, 0)); topSection.setOpaque(false); topSection.setMaximumSize(new Dimension(1200, 160));

        JPanel profilePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0)); profilePanel.setOpaque(false);
        JLabel lblFotoPerfil = new JLabel(); lblFotoPerfil.setPreferredSize(new Dimension(130, 130));
        lblFotoPerfil.setBorder(BorderFactory.createLineBorder(Color.decode("#F48FB1"), 4, true)); lblFotoPerfil.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Removido o clique da foto daqui! Agora é apenas visual.

        if (clienteLogado != null && clienteLogado.getFoto() != null && clienteLogado.getFoto().length > 0) {
            ImageIcon icon = new ImageIcon(clienteLogado.getFoto()); Image img = icon.getImage().getScaledInstance(130, 130, Image.SCALE_SMOOTH); lblFotoPerfil.setIcon(new ImageIcon(img));
        } else {
            lblFotoPerfil.setText("Sem Foto"); lblFotoPerfil.setOpaque(true); lblFotoPerfil.setBackground(Color.decode("#E0E0E0"));
        }

        JPanel infoProfilePanel = new JPanel(); infoProfilePanel.setLayout(new BoxLayout(infoProfilePanel, BoxLayout.Y_AXIS)); infoProfilePanel.setOpaque(false);
        JLabel lblBemVindo = new JLabel(clienteLogado != null ? clienteLogado.getNomeCompleto() : nomeUsuario); lblBemVindo.setFont(new Font("Segoe UI Black", Font.BOLD, 22));
        JLabel lblCargo = new JLabel("Cliente"); lblCargo.setFont(new Font("Segoe UI", Font.PLAIN, 16)); lblCargo.setForeground(Color.DARK_GRAY);
        infoProfilePanel.add(Box.createRigidArea(new Dimension(0, 30))); infoProfilePanel.add(lblBemVindo); infoProfilePanel.add(lblCargo);
        profilePanel.add(lblFotoPerfil); profilePanel.add(infoProfilePanel); topSection.add(profilePanel);

        // Notificações Dinâmicas do Cliente
        JPanel notifPanel = new JPanel(); notifPanel.setLayout(new BoxLayout(notifPanel, BoxLayout.Y_AXIS)); notifPanel.setOpaque(false);
        JLabel lblNotifTitle = new JLabel("Notificações:"); lblNotifTitle.setFont(new Font("Segoe UI Black", Font.BOLD, 20)); lblNotifTitle.setForeground(Color.decode("#D81B60"));
        
        RoundedPanel notifBox = new RoundedPanel(20, Color.WHITE); notifBox.setBorder(BorderFactory.createLineBorder(Color.decode("#F48FB1"), 3));
        notifBox.setLayout(new BorderLayout()); notifBox.setPreferredSize(new Dimension(400, 80));

        int pendentes = agendamentoController.contarPendentesDoCliente(clienteLogado.getIdUsuario(), nomeEmpresa);
        String txtNotificacao = pendentes > 0 ? "<html><center>⏳ Você tem <b>" + pendentes + "</b> agendamento(s)<br>aguardando confirmação do salão.</center></html>" 
                                              : "<html><center>Nenhuma notificação no momento.</center></html>";
        JLabel lblNotificacaoTexto = new JLabel(txtNotificacao); lblNotificacaoTexto.setFont(new Font("Segoe UI", Font.BOLD, 14)); lblNotificacaoTexto.setHorizontalAlignment(SwingConstants.CENTER);
        if(pendentes > 0) lblNotificacaoTexto.setForeground(Color.decode("#D81B60"));
        notifBox.add(lblNotificacaoTexto, BorderLayout.CENTER);
        notifPanel.add(lblNotifTitle); notifPanel.add(Box.createRigidArea(new Dimension(0, 10))); notifPanel.add(notifBox);
        topSection.add(notifPanel);
        contentPanel.add(topSection); contentPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        // --- MEIO: FORMULÁRIO E CALENDÁRIO ---
        JPanel middleSection = new JPanel(new GridLayout(1, 2, 50, 0)); middleSection.setOpaque(false);
        
        // Formulário
        JPanel formPanel = new JPanel(new GridBagLayout()); formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints(); gbc.fill = GridBagConstraints.HORIZONTAL; gbc.insets = new Insets(10, 5, 10, 5);
        Font labelFont = new Font("Segoe UI Black", Font.BOLD, 18); Color labelColor = Color.decode("#FF6090");

        JLabel lblAgendaTitle = new JLabel("Agendamentos"); lblAgendaTitle.setFont(new Font("Segoe UI Black", Font.BOLD, 32)); lblAgendaTitle.setForeground(labelColor);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; formPanel.add(lblAgendaTitle, gbc);

        txtIdAgendamento = new JTextField(); txtIdAgendamento.setVisible(false);
        txtData = criarCampoMascara("##/##/####"); txtHora = criarCampoMascara("##:##");
        cbFuncionario = new JComboBox<>(); cbServico = new JComboBox<>();
        
        JPanel dataHoraPanel = new JPanel(new GridLayout(1, 4, 10, 0)); dataHoraPanel.setOpaque(false);
        JLabel lblData = new JLabel("Data:"); lblData.setFont(labelFont); lblData.setForeground(labelColor); lblData.setHorizontalAlignment(SwingConstants.RIGHT);
        JLabel lblHora = new JLabel("Hora:"); lblHora.setFont(labelFont); lblHora.setForeground(labelColor); lblHora.setHorizontalAlignment(SwingConstants.RIGHT);
        dataHoraPanel.add(lblData); dataHoraPanel.add(txtData); dataHoraPanel.add(lblHora); dataHoraPanel.add(txtHora);
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2; formPanel.add(dataHoraPanel, gbc);

        gbc.gridwidth = 1;
        adicionarCombo(formPanel, gbc, 2, "Funcionário(a):", cbFuncionario, labelFont, labelColor);
        adicionarCombo(formPanel, gbc, 3, "Serviço:", cbServico, labelFont, labelColor);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0)); btnPanel.setOpaque(false);
        RoundedButton btnAgendar = new RoundedButton("Agendar"); btnAgendar.setPreferredSize(new Dimension(150, 40)); btnAgendar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        RoundedButton btnCancelar = new RoundedButton("Cancelar Agendamento"); btnCancelar.setPreferredSize(new Dimension(200, 40)); btnCancelar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnPanel.add(btnAgendar); btnPanel.add(btnCancelar);
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; gbc.insets = new Insets(20, 5, 0, 5); formPanel.add(btnPanel, gbc);
        middleSection.add(formPanel);

        // Calendário
        middleSection.add(criarCalendarioVisual());
        contentPanel.add(middleSection); contentPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        // --- INFERIOR: TABELA DE AGENDAMENTOS ---
        JLabel lblTableTitle = new JLabel("Seus Agendamentos:"); lblTableTitle.setFont(new Font("Segoe UI Black", Font.BOLD, 22)); lblTableTitle.setForeground(labelColor);
        JPanel titleTablePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0)); titleTablePanel.setOpaque(false); titleTablePanel.add(lblTableTitle);
        contentPanel.add(titleTablePanel); contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        String[] colunas = {"Data", "Hora", "Funcionário", "Serviço", "Status", "ID"};
        modelTabela = new DefaultTableModel(new Object[][]{}, colunas) { @Override public boolean isCellEditable(int r, int c) { return false; } };
        tabelaAgendamentos = new JTable(modelTabela); tabelaAgendamentos.setRowHeight(35);
        tabelaAgendamentos.getTableHeader().setFont(new Font("Segoe UI Black", Font.BOLD, 14)); tabelaAgendamentos.getTableHeader().setBackground(Color.decode("#F5F5F5"));
        tabelaAgendamentos.getColumnModel().getColumn(5).setMinWidth(0); tabelaAgendamentos.getColumnModel().getColumn(5).setMaxWidth(0); tabelaAgendamentos.getColumnModel().getColumn(5).setWidth(0);

        tabelaAgendamentos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int linha = tabelaAgendamentos.getSelectedRow();
                if (linha != -1) { txtIdAgendamento.setText(tabelaAgendamentos.getValueAt(linha, 5).toString()); }
            }
        });

        JScrollPane scrollTable = new JScrollPane(tabelaAgendamentos); scrollTable.setBorder(BorderFactory.createLineBorder(Color.decode("#F48FB1"), 4));
        scrollTable.setPreferredSize(new Dimension(1000, 250)); contentPanel.add(scrollTable);

        JScrollPane mainScroll = new JScrollPane(contentPanel); mainScroll.setOpaque(false); mainScroll.getViewport().setOpaque(false); mainScroll.setBorder(null); mainScroll.getVerticalScrollBar().setUnitIncrement(25);
        mainBg.add(mainScroll, BorderLayout.CENTER);

        // ==========================================
        // AÇÕES DOS BOTÕES E CARREGAMENTOS
        // ==========================================
        btnAgendar.addActionListener(e -> {
            try {
                LocalDate data = LocalDate.parse(txtData.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                LocalTime hora = LocalTime.parse(txtHora.getText(), DateTimeFormatter.ofPattern("HH:mm"));
                Funcionario fun = (Funcionario) cbFuncionario.getSelectedItem(); Servico ser = (Servico) cbServico.getSelectedItem();
                
                if (fun == null || fun.getIdFuncionario() == 0 || ser == null || ser.getIdServico() == 0) {
                    JOptionPane.showMessageDialog(this, "Selecione o profissional e o serviço.", "Aviso", JOptionPane.WARNING_MESSAGE); return;
                }
                
                agendamentoController.solicitarAgendamento(clienteLogado.getIdUsuario(), fun.getIdFuncionario(), ser.getIdServico(), data, hora, nomeEmpresa);
                JOptionPane.showMessageDialog(this, "Solicitação de agendamento enviada! Aguarde a confirmação do salão.");
                carregarTabelaENotificacoes(); limparFormulario();
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE); }
        });

        btnCancelar.addActionListener(e -> {
            if (txtIdAgendamento.getText().isEmpty()) { JOptionPane.showMessageDialog(this, "Selecione um agendamento na tabela para cancelar."); return; }
            int linha = tabelaAgendamentos.getSelectedRow();
            String status = tabelaAgendamentos.getValueAt(linha, 4).toString();
            
            if (status.equals("Concluído") || status.equals("Cancelado")) {
                JOptionPane.showMessageDialog(this, "Não é possível cancelar um agendamento já concluído ou cancelado."); return;
            }
            
            if (JOptionPane.showConfirmDialog(this, "Tem a certeza que deseja cancelar este agendamento?", "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                agendamentoController.recusarOuCancelarAgendamento(Integer.parseInt(txtIdAgendamento.getText()));
                JOptionPane.showMessageDialog(this, "Agendamento cancelado.");
                carregarTabelaENotificacoes(); limparFormulario();
            }
        });

        desenharCalendario(); carregarComboBoxes(); carregarTabelaENotificacoes();
    }

    private void carregarTabelaENotificacoes() {
        modelTabela.setRowCount(0);
        try {
            List<Agendamento> meusAgendamentos = agendamentoController.listarAgendamentosDoCliente(clienteLogado.getIdUsuario(), nomeEmpresa);
            for (Agendamento a : meusAgendamentos) {
                String dataF = a.getDataAgendamento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                modelTabela.addRow(new Object[]{dataF, a.getHora().toString(), a.getNomeFuncionario(), a.getNomeServico(), a.getStatus(), a.getIdAgendamento()});
            }
        } catch (Exception e) {}
    }

    private void carregarComboBoxes() {
        cbFuncionario.removeAllItems(); cbServico.removeAllItems();
        Funcionario fun = new Funcionario(); fun.setIdFuncionario(0); fun.setNome("Selecione..."); cbFuncionario.addItem(fun);
        Servico ser = new Servico(); ser.setIdServico(0); ser.setNome("Selecione..."); cbServico.addItem(ser);
        try {
            for (Funcionario f : funcionarioController.listarFuncionarios(nomeEmpresa)) cbFuncionario.addItem(f);
            for (Servico s : servicoController.listarServicos(nomeEmpresa)) cbServico.addItem(s);
        } catch (Exception e) {}
    }

    private void limparFormulario() { txtData.setValue(null); txtHora.setValue(null); cbFuncionario.setSelectedIndex(0); cbServico.setSelectedIndex(0); txtIdAgendamento.setText(""); tabelaAgendamentos.clearSelection(); }

    private JFormattedTextField criarCampoMascara(String m) {
        try { MaskFormatter mf = new MaskFormatter(m); mf.setPlaceholderCharacter('_'); return new JFormattedTextField(mf); } catch (Exception e) { return new JFormattedTextField(); }
    }

    private void adicionarCombo(JPanel painel, GridBagConstraints gbc, int linha, String textoLabel, JComboBox combo, Font font, Color cor) {
        JLabel lbl = new JLabel(textoLabel); lbl.setFont(font); lbl.setForeground(cor); lbl.setHorizontalAlignment(SwingConstants.RIGHT);
        gbc.gridx = 0; gbc.gridy = linha; gbc.weightx = 0.3; painel.add(lbl, gbc);
        combo.setPreferredSize(new Dimension(250, 35)); combo.setBorder(BorderFactory.createLineBorder(Color.decode("#FFCDD2"), 2, true)); combo.setBackground(Color.WHITE);
        gbc.gridx = 1; gbc.weightx = 0.7; painel.add(combo, gbc);
    }

    private JPanel criarCalendarioVisual() {
        RoundedPanel p = new RoundedPanel(30, Color.decode("#FFF0F5")); p.setBorder(BorderFactory.createLineBorder(Color.decode("#D81B60"), 3)); p.setLayout(new BorderLayout());
        JPanel header = new JPanel(new BorderLayout()); header.setOpaque(false); header.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        lblMesAno = new JLabel("", SwingConstants.CENTER); lblMesAno.setFont(new Font("Segoe UI Black", Font.BOLD, 22)); lblMesAno.setForeground(Color.decode("#D81B60"));
        JButton btnPrev = new JButton("<"); JButton btnNext = new JButton(">");
        btnPrev.addActionListener(e -> { mesAtual = mesAtual.minusMonths(1); desenharCalendario(); }); btnNext.addActionListener(e -> { mesAtual = mesAtual.plusMonths(1); desenharCalendario(); });
        header.add(btnPrev, BorderLayout.WEST); header.add(lblMesAno, BorderLayout.CENTER); header.add(btnNext, BorderLayout.EAST); p.add(header, BorderLayout.NORTH);
        JPanel body = new JPanel(new BorderLayout()); body.setOpaque(false); JPanel diasSemana = new JPanel(new GridLayout(1, 7)); diasSemana.setOpaque(false);
        String[] dias = {"DOM", "SEG", "TER", "QUA", "QUI", "SEX", "SÁB"};
        for (String d : dias) { JLabel l = new JLabel(d, SwingConstants.CENTER); l.setFont(new Font("Segoe UI", Font.BOLD, 12)); l.setForeground(Color.decode("#FF6090")); diasSemana.add(l); }
        body.add(diasSemana, BorderLayout.NORTH); panelDiasCalendario = new JPanel(new GridLayout(0, 7, 5, 5)); panelDiasCalendario.setOpaque(false); panelDiasCalendario.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        body.add(panelDiasCalendario, BorderLayout.CENTER); p.add(body, BorderLayout.CENTER); return p;
    }

    private void desenharCalendario() {
        panelDiasCalendario.removeAll();
        String nomeMes = mesAtual.getMonth().getDisplayName(TextStyle.FULL, localeBR).toUpperCase(); lblMesAno.setText(nomeMes + " " + mesAtual.getYear());
        LocalDate primeiroDia = mesAtual.atDay(1); int diaDaSemanaInicio = primeiroDia.getDayOfWeek().getValue() % 7; int diasNoMes = mesAtual.lengthOfMonth();
        for (int i = 0; i < diaDaSemanaInicio; i++) panelDiasCalendario.add(new JLabel(""));
        for (int i = 1; i <= diasNoMes; i++) {
            JButton btnDia = new JButton(String.valueOf(i)); btnDia.setFont(new Font("Segoe UI", Font.BOLD, 14)); btnDia.setBackground(Color.WHITE); btnDia.setForeground(Color.decode("#D81B60")); btnDia.setFocusPainted(false); btnDia.setBorder(BorderFactory.createLineBorder(Color.decode("#FFCDD2")));
            final int d = i; btnDia.addActionListener(e -> { String dataF = String.format("%02d/%02d/%d", d, mesAtual.getMonthValue(), mesAtual.getYear()); txtData.setText(dataF); });
            panelDiasCalendario.add(btnDia);
        }
        panelDiasCalendario.revalidate(); panelDiasCalendario.repaint();
    }
}