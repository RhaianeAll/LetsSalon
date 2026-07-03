package com.letssalon.view;

import com.letssalon.dao.AgendaDAO;
import com.letssalon.dao.EmpresaDAO;
import com.letssalon.model.Agenda;
import com.letssalon.model.Colaborador;
import com.letssalon.model.Empresa;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.Font;
import java.util.List;
import com.letssalon.util.CoresUtil;
import com.letssalon.util.BotaoUtil;

public class TelaPrincipal extends JFrame {

    private Colaborador colaboradorLogado;
    private JLabel lblNome, lblFuncao, lblEmpresa;
    private JPanel painelTabela;
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
    setExtendedState(JFrame.MAXIMIZED_BOTH);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());
    getContentPane().setBackground(CoresUtil.ROSA_FUNDO);

    // TOPO

    JPanel painelTopo = new JPanel(new BorderLayout());
    painelTopo.setBackground(new Color(201, 72, 105));
    painelTopo.setPreferredSize(new Dimension(0, 60));

    JLabel lblLogo = new JLabel("  LET'S SALON");
    lblLogo.setFont(new Font("Georgia", Font.ITALIC, 52));
    lblLogo.setForeground(Color.WHITE);

    lblEmpresa = new JLabel("Empresa  ");
    lblEmpresa.setFont(new Font("Arial", Font.BOLD, 16));
    lblEmpresa.setForeground(Color.WHITE);

    painelTopo.add(lblLogo, BorderLayout.WEST);
    painelTopo.add(lblEmpresa, BorderLayout.EAST);

    add(painelTopo, BorderLayout.NORTH);

    // MENU LATERAL

    JPanel painelMenu = new JPanel();
    painelMenu.setBackground(new Color(245, 220, 225));
    painelMenu.setPreferredSize(new Dimension(240, 0));
    painelMenu.setLayout(new BoxLayout(painelMenu, BoxLayout.Y_AXIS));
    painelMenu.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));

    lblNome = new JLabel(colaboradorLogado.getNomeColaborador());
    lblNome.setFont(new Font("Arial", Font.BOLD, 18));
    lblNome.setForeground(new Color(201, 72, 105));

    lblFuncao = new JLabel(colaboradorLogado.getFuncaoColaborador());
    lblFuncao.setFont(new Font("Arial", Font.PLAIN, 14));
    lblFuncao.setForeground(Color.GRAY);

    painelMenu.add(lblNome);
    painelMenu.add(Box.createVerticalStrut(5));
    painelMenu.add(lblFuncao);
    painelMenu.add(Box.createVerticalStrut(25));

    // BOTÕES MENU

    btnAgendamentos          = criarBotao("Agendamentos");
    btnCadastroProfissionais = criarBotao("Profissionais");
    btnCadastroServicos      = criarBotao("Serviços");
    btnCadastroInsumos       = criarBotao("Insumos");
    btnEstoque               = criarBotao("Estoque");
    btnRelatorios            = criarBotao("Relatórios");
    btnSair                  = criarBotao("Sair");

    painelMenu.add(btnAgendamentos);
    painelMenu.add(Box.createVerticalStrut(10));

    painelMenu.add(btnCadastroProfissionais);
    painelMenu.add(Box.createVerticalStrut(10));

    painelMenu.add(btnCadastroServicos);
    painelMenu.add(Box.createVerticalStrut(10));

    painelMenu.add(btnCadastroInsumos);
    painelMenu.add(Box.createVerticalStrut(10));

    painelMenu.add(btnEstoque);
    painelMenu.add(Box.createVerticalStrut(10));

    painelMenu.add(btnRelatorios);
    painelMenu.add(Box.createVerticalGlue());

    // BOTÃO SAIR

    btnSair.setBackground(CoresUtil.CINZA_VOLTAR);
    btnSair.setForeground(CoresUtil.CINZA_VOLTAR_TEXTO);

    painelMenu.add(btnSair);

    add(painelMenu, BorderLayout.WEST);

    // PAINEL CENTRAL

    JPanel painelCentro = new JPanel(new BorderLayout());
    painelCentro.setBackground(CoresUtil.ROSA_FUNDO);
    painelCentro.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    // NOTIFICAÇÕES

    JPanel painelNotificacoes = new JPanel(new GridLayout(2, 1, 5, 5));
    painelNotificacoes.setBackground(CoresUtil.ROSA_FUNDO);
    painelNotificacoes.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(201, 72, 105)),
            "Notificações"
    ));

    JLabel notif1 = new JLabel("⚠ Verifique agendamentos pendentes");
    notif1.setForeground(new Color(201, 72, 105));

    JLabel notif2 = new JLabel("⚠ Verifique atendimentos realizados");
    notif2.setForeground(new Color(201, 72, 105));

    painelNotificacoes.add(notif1);
    painelNotificacoes.add(notif2);

    painelCentro.add(painelNotificacoes, BorderLayout.NORTH);

    // TABELA

    painelTabela = new JPanel(new BorderLayout());
    painelTabela.setBackground(CoresUtil.ROSA_FUNDO);

    painelTabela.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(201, 72, 105)),
            "Atendimentos Realizados Há Mais de 15 Dias",
            0,
            0,
            new Font("Arial", Font.BOLD, 13),
            new Color(201, 72, 105)
    ));

    String[] colunas = {
        "Cliente",
        "Telefone",
        "Data/Hora",
        "Funcionário",
        "Serviço",
        "Realizado"
    };

    modeloTabela = new DefaultTableModel(colunas, 0);

    tabelaAtendimentos = new JTable(modeloTabela);
    painelTabela.setVisible(false);

    tabelaAtendimentos.setRowHeight(28);

    tabelaAtendimentos.getTableHeader().setBackground(new Color(201, 72, 105));
    tabelaAtendimentos.getTableHeader().setForeground(Color.WHITE);

    JScrollPane scrollTabela = new JScrollPane(tabelaAtendimentos);

    painelTabela.add(scrollTabela);

    painelCentro.add(painelTabela, BorderLayout.CENTER);

    add(painelCentro, BorderLayout.CENTER);

    // DADOS

    preencherDados();

    // AÇÕES

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

        JButton btn = BotaoUtil.criarBotao(texto, CoresUtil.ROSA_PRINCIPAL);

        btn.setMaximumSize(new Dimension(220, 45));
        btn.setMinimumSize(new Dimension(220, 45));
        btn.setPreferredSize(new Dimension(220, 45));

        return btn;
}

    private void preencherDados() {
    lblNome.setText(colaboradorLogado.getNomeColaborador());
    lblFuncao.setText(colaboradorLogado.getFuncaoColaborador());
    
 // Busca o nome da empresa pelo código
EmpresaDAO empresaDAO = new EmpresaDAO();

Empresa empresa = empresaDAO.buscarPorCodigo(
        colaboradorLogado.getCodigoEmpresa());

if (empresa != null) {

    lblEmpresa.setText(
            empresa.getNomeEmpresa() + "  ");

}
 
   // Preenche tabela de atendimentos realizados há mais de 15 dias
AgendaDAO agendaDAO = new AgendaDAO();

modeloTabela.setRowCount(0);

List<Object[]> atendimentos =
        agendaDAO.listarFinalizadosAntigos(
                colaboradorLogado.getCodigoEmpresa());

for (Object[] linha : atendimentos) {

    modeloTabela.addRow(linha);

}
String funcao = colaboradorLogado.getFuncaoColaborador();

boolean isAdmin =
        funcao != null &&
        (funcao.equalsIgnoreCase("ADMIN") ||
         funcao.equalsIgnoreCase("ADMINISTRADOR") ||
         funcao.equalsIgnoreCase("Dono"));

painelTabela.setVisible(isAdmin);
}
}