package com.letssalon.view;

import com.letssalon.dao.AtendimentoDAO;
import com.letssalon.dao.ColaboradorDAO;
import com.letssalon.model.Atendimento;
import com.letssalon.model.Colaborador;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import com.letssalon.util.CoresUtil;
import java.util.HashMap;
import java.util.Map;

public class TelaRelatorios extends JFrame {

    private Colaborador colaboradorLogado;
    private JTable tabelaResumo, tabelaAtendimentos;
    private DefaultTableModel modeloResumo, modeloAtendimentos;
    private JTextField txtBuscarResumo, txtBuscarAtendimentos;

    private AtendimentoDAO atendimentoDAO = new AtendimentoDAO();
    private ColaboradorDAO colaboradorDAO = new ColaboradorDAO();
    
    private JLabel lblTopFunc;
    private JLabel lblTopCliente;
    private JLabel lblHorarioPico;

   public TelaRelatorios(Colaborador colaborador) {

    this.colaboradorLogado = colaborador;

    initComponents(); // UI primeiro

    SwingUtilities.invokeLater(() -> {
        carregarDados(); // dados depois
    });
   }

    private void initComponents() {
        setTitle("Let's Salon - Relatórios");
        setSize(950, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(CoresUtil.ROSA_FUNDO);

        // ── TOPO ──
        JPanel painelTopo = new JPanel(new BorderLayout());
        painelTopo.setBackground(new Color(201, 72, 105));
        painelTopo.setPreferredSize(new Dimension(950, 50));

        JLabel lblTitulo = new JLabel("  Relatórios");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);
        painelTopo.add(lblTitulo, BorderLayout.WEST);

        JLabel lblColaborador = new JLabel(colaboradorLogado.getNomeColaborador() + "  ");
        lblColaborador.setForeground(Color.WHITE);
        painelTopo.add(lblColaborador, BorderLayout.EAST);

        add(painelTopo, BorderLayout.NORTH);

        // ── CENTRO: duas tabelas empilhadas ──
        JPanel painelCentro = new JPanel(new GridLayout(2, 1, 0, 15));
        painelCentro.setBackground(CoresUtil.ROSA_FUNDO);
        painelCentro.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // ── RESUMO GERAL ──
        JPanel painelResumo = new JPanel(new BorderLayout());
        painelResumo.setBackground(CoresUtil.ROSA_FUNDO);
        painelResumo.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(201, 72, 105)),
            "Resumo Geral",
            0, 0,
            new Font("Arial", Font.BOLD, 13),
            new Color(201, 72, 105)
        ));

        JPanel painelBuscaResumo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelBuscaResumo.setBackground(CoresUtil.ROSA_FUNDO);
        painelBuscaResumo.add(new JLabel("Buscar:"));
        txtBuscarResumo = new JTextField(20);
        painelBuscaResumo.add(txtBuscarResumo);
        JButton btnBuscarResumo = new JButton("🔍");
        btnBuscarResumo.addActionListener(e -> carregarResumo());
        painelBuscaResumo.add(btnBuscarResumo);

        JButton btnExportar = new JButton("📄 Exportar");
        btnExportar.setBackground(new Color(201, 72, 105));
        btnExportar.setForeground(Color.WHITE);
        btnExportar.addActionListener(e -> exportarRelatorio());
        painelBuscaResumo.add(btnExportar);

        painelResumo.add(painelBuscaResumo, BorderLayout.NORTH);
        JPanel painelInsights = new JPanel(new FlowLayout(FlowLayout.LEFT));
painelInsights.setBackground(CoresUtil.ROSA_FUNDO);

lblTopFunc = new JLabel();
lblTopFunc.setForeground(new Color(201, 72, 105));
lblTopFunc.setFont(new Font("Arial", Font.BOLD, 12));

lblTopCliente = new JLabel();
lblTopCliente.setForeground(new Color(201, 72, 105));
lblTopCliente.setFont(new Font("Arial", Font.BOLD, 12));

JLabel lblHorarioPico = new JLabel();
lblHorarioPico.setForeground(new Color(201, 72, 105));
lblHorarioPico.setFont(new Font("Arial", Font.BOLD, 12));

painelInsights.add(lblHorarioPico);

painelInsights.add(lblTopFunc);
painelInsights.add(new JLabel("   |   "));
painelInsights.add(lblTopCliente);

painelResumo.add(painelInsights, BorderLayout.SOUTH);

        String[] colunasResumo = {
            "Id Funcionário", "Funcionário(a)",
            "Qtd Serviços Realizados", "Clientes Atendidos",
            "Total Insumos Utilizados", "Valor Total Bruto", "Valor Total Líquido"
        };
        modeloResumo = new DefaultTableModel(colunasResumo, 0);
        tabelaResumo = new JTable(modeloResumo);
        tabelaResumo.setRowHeight(28);
        estilizarTabela(tabelaResumo);
        painelResumo.add(new JScrollPane(tabelaResumo));

        // ── REGISTRO DE ATENDIMENTOS ──
        JPanel painelAtendimentos = new JPanel(new BorderLayout());
        painelAtendimentos.setBackground(CoresUtil.ROSA_FUNDO);
        painelAtendimentos.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(201, 72, 105)),
            "Registro de Atendimentos",
            0, 0,
            new Font("Arial", Font.BOLD, 13),
            new Color(201, 72, 105)
        ));

        JPanel painelBuscaAtend = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelBuscaAtend.setBackground(CoresUtil.ROSA_FUNDO);
        painelBuscaAtend.add(new JLabel("Buscar:"));
        txtBuscarAtendimentos = new JTextField(20);
        painelBuscaAtend.add(txtBuscarAtendimentos);
        JButton btnBuscarAtend = new JButton("🔍");
        btnBuscarAtend.addActionListener(e -> carregarAtendimentos());        painelBuscaAtend.add(btnBuscarAtend);
        painelAtendimentos.add(painelBuscaAtend, BorderLayout.NORTH);

        String[] colunasAtend = {
            "Id Funcionário", "Funcionário(a)", "Serviço Realizado",
            "Insumos Utilizados", "Qtd Aplicações", "Nome Cliente",
            "Data/Hora", "Valor Arrecadado"
        };
        modeloAtendimentos = new DefaultTableModel(colunasAtend, 0);
        tabelaAtendimentos = new JTable(modeloAtendimentos);
        tabelaAtendimentos.setRowHeight(28);
        estilizarTabela(tabelaAtendimentos);
        painelAtendimentos.add(new JScrollPane(tabelaAtendimentos));

        painelCentro.add(painelResumo);
        painelCentro.add(painelAtendimentos);
        add(painelCentro, BorderLayout.CENTER);

        // ── RODAPÉ ──
        JPanel painelRodape = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelRodape.setBackground(new Color(201, 72, 105));

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setBackground(Color.WHITE);
        btnVoltar.setForeground(new Color(201, 72, 105));
        btnVoltar.setFont(new Font("Arial", Font.BOLD, 12));
        btnVoltar.addActionListener(e -> dispose());
        painelRodape.add(btnVoltar);

        add(painelRodape, BorderLayout.SOUTH);
    }

    private void carregarDados() {
        carregarResumo();
        carregarAtendimentos();
    }

private void carregarResumo() {

    modeloResumo.setRowCount(0);

    String filtro = txtBuscarResumo.getText();

    List<Colaborador> colaboradores = colaboradorDAO.listar();
    List<Atendimento> atendimentos = atendimentoDAO.listar();
    // 🏆 cálculos inteligentes
    String funcionarioTop = calcularFuncionarioMaisProdutivo(colaboradores, atendimentos);
    String clienteTop = calcularClienteMaisFrequente(atendimentos);
    String horarioPico = calcularHorarioPico(atendimentos);
    
    for (Colaborador c : colaboradores) {

        // 🔍 FILTRO AQUI (nome do funcionário)
        if (!passaFiltro(c.getNomeColaborador(), filtro)) continue;

        int qtdServicos = 0;
        double valorBruto = 0;
        double valorLiquido = 0;
        int totalInsumos = 0;

        for (Atendimento a : atendimentos) {

            if (a.getIdColaborador() == c.getIdColaborador()) {

                qtdServicos++;
                valorBruto += a.getValorArrecadado();
                valorLiquido += (a.getValorArrecadado() - a.getCustoInsumo());
                totalInsumos += a.getQtdAplicacoes();
            }
        }

        modeloResumo.addRow(new Object[]{
            c.getIdColaborador(),
            c.getNomeColaborador(),
            qtdServicos,
            qtdServicos,
            totalInsumos,
            String.format("R$ %.2f", valorBruto),
            String.format("R$ %.2f", valorLiquido)
        });
    }
    lblTopFunc.setText("🏆 Funcionário mais produtivo: " + funcionarioTop);
    lblTopCliente.setText("⭐ Cliente mais frequente: " + clienteTop);
    lblHorarioPico.setText("⏰ Pico de atendimento: " + horarioPico);
}

private void carregarAtendimentos() {

    modeloAtendimentos.setRowCount(0);

    String filtro = txtBuscarAtendimentos.getText();

    List<Atendimento> atendimentos = atendimentoDAO.listar();
    List<Colaborador> colaboradores = colaboradorDAO.listar();

    Map<Integer, String> nomeColaboradorMap = new HashMap<>();

    for (Colaborador c : colaboradores) {
        nomeColaboradorMap.put(c.getIdColaborador(), c.getNomeColaborador());
    }

    for (Atendimento a : atendimentos) {

        String textoBusca =
                a.getCpfCliente() + " " +
                a.getCodigoServico() + " " +
                a.getCodigoInsumo();

        if (!passaFiltro(textoBusca, filtro)) continue;

        modeloAtendimentos.addRow(new Object[]{
            a.getIdColaborador(),
            nomeColaboradorMap.getOrDefault(a.getIdColaborador(), "-"),
            a.getCodigoServico(),
            a.getCodigoInsumo(),
            a.getQtdAplicacoes(),
            a.getCpfCliente(),
            a.getDataHora(),
            String.format("R$ %.2f", a.getValorArrecadado())
        });
    }
}

    private void exportarRelatorio() {
        JOptionPane.showMessageDialog(this,
            "Funcionalidade de exportação será implementada em breve!",
            "Exportar",
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void estilizarTabela(JTable tabela) {
        tabela.getTableHeader().setBackground(new Color(201, 72, 105));
        tabela.getTableHeader().setForeground(Color.WHITE);
        tabela.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tabela.setBackground(Color.WHITE);
        tabela.setGridColor(CoresUtil.ROSA_SECUNDARIO);
        tabela.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    }
    private boolean passaFiltro(String texto, String filtro) {
    if (filtro == null || filtro.trim().isEmpty()) return true;
    return texto.toLowerCase().contains(filtro.toLowerCase());
}
    private String calcularFuncionarioMaisProdutivo(List<Colaborador> colaboradores, List<Atendimento> atendimentos) {

    int melhorQtd = 0;
    String nomeMelhor = "-";

    for (Colaborador c : colaboradores) {

        int qtd = 0;

        for (Atendimento a : atendimentos) {
            if (a.getIdColaborador() == c.getIdColaborador()) {
                qtd++;
            }
        }

        if (qtd > melhorQtd) {
            melhorQtd = qtd;
            nomeMelhor = c.getNomeColaborador();
        }
    }

    return nomeMelhor + " (" + melhorQtd + " atendimentos)";
}
    private String calcularClienteMaisFrequente(List<Atendimento> atendimentos) {

    Map<String, Integer> contagemClientes = new HashMap<>();

    for (Atendimento a : atendimentos) {

        String cliente = a.getCpfCliente();

        contagemClientes.put(
            cliente,
            contagemClientes.getOrDefault(cliente, 0) + 1
        );
    }

    String melhorCliente = "-";
    int maior = 0;

    for (Map.Entry<String, Integer> entry : contagemClientes.entrySet()) {

        if (entry.getValue() > maior) {
            maior = entry.getValue();
            melhorCliente = entry.getKey();
        }
    }

    return melhorCliente + " (" + maior + " visitas)";
}
    private String calcularHorarioPico(List<Atendimento> atendimentos) {

    Map<Integer, Integer> mapaHoras = new HashMap<>();

    for (Atendimento a : atendimentos) {

        // supondo formato: "2026-07-03 14:30:00"
        String dataHora = a.getDataHora().toString();

        int hora = Integer.parseInt(dataHora.substring(11, 13));

        mapaHoras.put(hora, mapaHoras.getOrDefault(hora, 0) + 1);
    }

    int melhorHora = 0;
    int maior = 0;

    for (Map.Entry<Integer, Integer> entry : mapaHoras.entrySet()) {

        if (entry.getValue() > maior) {
            maior = entry.getValue();
            melhorHora = entry.getKey();
        }
    }

    return melhorHora + "h (" + maior + " atendimentos)";
}
}