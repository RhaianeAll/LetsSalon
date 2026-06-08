package com.letssalon.view;

import com.letssalon.dao.AgendaDAO;
import com.letssalon.dao.EmpresaDAO;
import com.letssalon.model.Agenda;
import com.letssalon.model.Colaborador;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaPrincipal extends JFrame {

    private Colaborador colaboradorLogado;
    private JLabel lblNome, lblFuncao, lblEmpresa;
    private JButton btnAgendamentos, btnCadastroProfissionais;
    private JButton btnCadastroServicos, btnEstoque;
    private JButton btnCadastroInsumos, btnRelatorios;
    private JButton btnSair;
    private JTable tabelaAtendimentos;
    private DefaultTableModel modeloTabela;

    public TelaPrincipal(Colaborador colaborador) {
        this.colaboradorLogado = colaborador;
        initComponents();
    }

    private void initComponents() {
    setTitle("Let's Salon - Dashboard");
    setSize(900, 650);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setLayout(new BorderLayout());
    getContentPane().setBackground(new Color(255, 240, 243));

    // ── TOPO ──
    JPanel painelTopo = new JPanel(new BorderLayout());
    painelTopo.setBackground(new Color(201, 72, 105));
    painelTopo.setPreferredSize(new Dimension(900, 60));

    JLabel lblLogo = new JLabel("  LET'S SALON");
    lblLogo.setFont(new Font("Arial", Font.BOLD, 20));
    lblLogo.setForeground(Color.WHITE);
    painelTopo.add(lblLogo, BorderLayout.WEST);

    lblEmpresa = new JLabel("Empresa  ");
    lblEmpresa.setFont(new Font("Arial", Font.BOLD, 16));
    lblEmpresa.setForeground(Color.WHITE);
    painelTopo.add(lblEmpresa, BorderLayout.EAST);

    add(painelTopo, BorderLayout.NORTH);

    // ── PAINEL PRINCIPAL ──
    JPanel painelPrincipal = new JPanel(new BorderLayout());
    painelPrincipal.setBackground(new Color(255, 240, 243));

    // Perfil + Notificações
    JPanel painelTopo2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
    painelTopo2.setBackground(new Color(255, 240, 243));

    JPanel painelInfo = new JPanel(new GridLayout(2, 1));
    painelInfo.setBackground(new Color(255, 240, 243));

    lblNome = new JLabel("Nome do Colaborador");
    lblNome.setFont(new Font("Arial", Font.BOLD, 16));
    lblNome.setForeground(new Color(201, 72, 105));

    lblFuncao = new JLabel("Função");
    lblFuncao.setFont(new Font("Arial", Font.PLAIN, 14));
    lblFuncao.setForeground(Color.GRAY);

    painelInfo.add(lblNome);
    painelInfo.add(lblFuncao);
    painelTopo2.add(painelInfo);

    JPanel painelNotificacoes = new JPanel(new GridLayout(2, 1, 5, 5));
    painelNotificacoes.setBackground(new Color(255, 240, 243));
    painelNotificacoes.setBorder(BorderFactory.createTitledBorder("Notificações"));

    JLabel notif1 = new JLabel("⚠ Verifique agendamentos pendentes");
    notif1.setForeground(new Color(201, 72, 105));
    JLabel notif2 = new JLabel("⚠ Verifique atendimentos realizados");
    notif2.setForeground(new Color(201, 72, 105));

    painelNotificacoes.add(notif1);
    painelNotificacoes.add(notif2);
    painelTopo2.add(painelNotificacoes);

    painelPrincipal.add(painelTopo2, BorderLayout.NORTH);

    // ── BOTÕES DO MENU ──
    JPanel painelBotoes = new JPanel(new GridLayout(3, 2, 15, 15));
    painelBotoes.setBackground(new Color(255, 240, 243));
    painelBotoes.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

    btnAgendamentos          = criarBotao("Agendamentos");
    btnCadastroProfissionais = criarBotao("Cadastro de Profissionais");
    btnCadastroServicos      = criarBotao("Cadastro de Serviços");
    btnCadastroInsumos       = criarBotao("Cadastro de Insumos");
    btnEstoque               = criarBotao("Estoque");
    btnRelatorios            = criarBotao("Relatórios");

    painelBotoes.add(btnAgendamentos);
    painelBotoes.add(btnCadastroInsumos);
    painelBotoes.add(btnCadastroProfissionais);
    painelBotoes.add(btnEstoque);
    painelBotoes.add(btnCadastroServicos);
    painelBotoes.add(btnRelatorios);

    painelPrincipal.add(painelBotoes, BorderLayout.CENTER);

    // ── TABELA atendimentos > 15 dias ──
    JPanel painelTabela = new JPanel(new BorderLayout());
    painelTabela.setBackground(new Color(255, 240, 243));
    painelTabela.setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createLineBorder(new Color(201, 72, 105)),
        "Atendimentos Realizados a Mais de 15 Dias Atrás",
        0, 0,
        new Font("Arial", Font.BOLD, 13),
        new Color(201, 72, 105)
    ));

    String[] colunas = {"Nome Cliente", "Telefone", "Data/Hora", "Id Funcionário", "Serviço", "Realizado a"};
    modeloTabela = new DefaultTableModel(colunas, 0);
    tabelaAtendimentos = new JTable(modeloTabela);
    tabelaAtendimentos.setRowHeight(28);
    tabelaAtendimentos.getTableHeader().setBackground(new Color(201, 72, 105));
    tabelaAtendimentos.getTableHeader().setForeground(Color.WHITE);

    painelTabela.add(new JScrollPane(tabelaAtendimentos));
    painelPrincipal.add(painelTabela, BorderLayout.SOUTH);

    add(painelPrincipal, BorderLayout.CENTER);

    // ── RODAPÉ ──
    JPanel painelRodape = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    painelRodape.setBackground(new Color(201, 72, 105));

    btnSair = new JButton("Sair");
    btnSair.setBackground(Color.WHITE);
    btnSair.setForeground(new Color(201, 72, 105));
    btnSair.setFont(new Font("Arial", Font.BOLD, 12));
    painelRodape.add(btnSair);
    add(painelRodape, BorderLayout.SOUTH);

    // Preenche dados
    preencherDados();

    // ── AÇÕES DOS BOTÕES ──
    btnAgendamentos.addActionListener(e ->
        new TelaAgendamentos(colaboradorLogado).setVisible(true));
    btnCadastroProfissionais.addActionListener(e ->
        new TelaCadastroColaborador(colaboradorLogado).setVisible(true));
    btnCadastroServicos.addActionListener(e ->
        new TelaCadastroServico(colaboradorLogado).setVisible(true));
    btnCadastroInsumos.addActionListener(e ->
        new TelaCadastroInsumo(colaboradorLogado).setVisible(true));
    btnEstoque.addActionListener(e ->
        new TelaEstoque(colaboradorLogado).setVisible(true));
    btnRelatorios.addActionListener(e ->
        new TelaRelatorios(colaboradorLogado).setVisible(true));
    btnSair.addActionListener(e -> {
        new TelaLogin().setVisible(true);
        dispose();
    });
}

    private JButton criarBotao(String texto) {
        JButton btn = new JButton(texto);
        btn.setBackground(new Color(201, 72, 105));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(200, 45));
        return btn;
    }

    private void preencherDados() {
    lblNome.setText(colaboradorLogado.getNomeColaborador());
    lblFuncao.setText(colaboradorLogado.getFuncaoColaborador());
    
     // Busca o nome da empresa pelo código
    EmpresaDAO empresaDAO = new EmpresaDAO();
    empresaDAO.listar().stream()
        .filter(e -> e.getCodigoEmpresa() == colaboradorLogado.getCodigoEmpresa())
        .findFirst()
        .ifPresent(e -> lblEmpresa.setText(e.getNomeEmpresa() + "  "));
 
    // Preenche tabela de atendimentos realizados há mais de 15 dias
    AgendaDAO agendaDAO = new AgendaDAO();
    modeloTabela.setRowCount(0);
    java.time.LocalDate limite = java.time.LocalDate.now().minusDays(15);
    for (Agenda a : agendaDAO.listar()) {
        if (!a.isConfirmado()) continue;
        try {
            // data no formato dd/MM/yyyy
            java.time.format.DateTimeFormatter fmt = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
            java.time.LocalDate dataAgenda = java.time.LocalDate.parse(a.getData(), fmt);
            if (dataAgenda.isBefore(limite)) {
                long diasAtras = java.time.temporal.ChronoUnit.DAYS.between(dataAgenda, java.time.LocalDate.now());
                modeloTabela.addRow(new Object[]{
                    a.getCpfCliente(), "-",
                    a.getData() + " " + a.getHorario(),
                    a.getIdColaborador(), a.getCodigoServico(),
                    diasAtras + " dias atrás"
                });
            }
        } catch (Exception ignored) {}
    }
}
}