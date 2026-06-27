package com.letssalon.view;

import com.letssalon.dao.CadastroInsumoDAO;
import com.letssalon.dao.InsumoDAO;
import com.letssalon.dao.ServicoDAO;
import com.letssalon.model.CadastroInsumo;
import com.letssalon.model.Colaborador;
import com.letssalon.model.Insumo;
import com.letssalon.model.Servico;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import com.letssalon.util.CoresUtil;

public class TelaCadastroInsumo extends JFrame {

    private Colaborador colaboradorLogado;
    private JTextField txtRendeAplicacoes;
    private JComboBox<String> cmbInsumo, cmbServico;
    private JButton btnCadastrar, btnExcluir, btnLimpar, btnVoltar;
    private JTable tabelaInsumos;
    private DefaultTableModel modeloTabela;
    private JTextField txtBuscar;

    private CadastroInsumoDAO cadastroInsumoDAO = new CadastroInsumoDAO();
    private InsumoDAO insumoDAO = new InsumoDAO();
    private ServicoDAO servicoDAO = new ServicoDAO();

    public TelaCadastroInsumo(Colaborador colaborador) {
        this.colaboradorLogado = colaborador;
        initComponents();
        carregarCombos();
        carregarTabela();
    }

    private void initComponents() {
        setTitle("Let's Salon - Cadastro de Insumos");
        setSize(800, 580);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(CoresUtil.ROSA_FUNDO);

        // ── TOPO ──
        JPanel painelTopo = new JPanel(new BorderLayout());
        painelTopo.setBackground(new Color(201, 72, 105));
        painelTopo.setPreferredSize(new Dimension(800, 50));

        JLabel lblTitulo = new JLabel("  Cadastro de Insumos");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);
        painelTopo.add(lblTitulo, BorderLayout.WEST);

        JLabel lblColaborador = new JLabel(colaboradorLogado.getNomeColaborador() + "  ");
        lblColaborador.setForeground(Color.WHITE);
        painelTopo.add(lblColaborador, BorderLayout.EAST);

        add(painelTopo, BorderLayout.NORTH);

        // ── FORMULÁRIO ──
        JPanel painelForm = new JPanel(new GridBagLayout());
        painelForm.setBackground(CoresUtil.ROSA_FUNDO);
        painelForm.setBorder(BorderFactory.createEmptyBorder(15, 30, 10, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Insumo
        gbc.gridx = 0; gbc.gridy = 0;
        painelForm.add(new JLabel("Insumo:"), gbc);
        cmbInsumo = new JComboBox<>();
        gbc.gridx = 1; gbc.gridwidth = 2;
        painelForm.add(cmbInsumo, gbc);

        // Rende Aplicações
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1;
        painelForm.add(new JLabel("Rende Aplicações:"), gbc);
        txtRendeAplicacoes = new JTextField(10);
        txtRendeAplicacoes.setToolTipText("Ex: 5, 20, 1");
        gbc.gridx = 1;
        painelForm.add(txtRendeAplicacoes, gbc);

        // Serviço Associado
        gbc.gridx = 0; gbc.gridy = 2;
        painelForm.add(new JLabel("Serviço Associado:"), gbc);
        cmbServico = new JComboBox<>();
        gbc.gridx = 1; gbc.gridwidth = 2;
        painelForm.add(cmbServico, gbc);

        // Botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        painelBotoes.setBackground(CoresUtil.ROSA_FUNDO);

        btnCadastrar = criarBotao("Cadastrar", new Color(201, 72, 105));
        btnExcluir   = criarBotao("Excluir", CoresUtil.VERMELHO_EXCLUIR);
        btnLimpar    = criarBotao("Limpar", CoresUtil.ROSA_SECUNDARIO);
        btnVoltar    = criarBotao("Voltar", CoresUtil.CINZA_VOLTAR);

        painelBotoes.add(btnCadastrar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnLimpar);
        painelBotoes.add(btnVoltar);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 3;
        painelForm.add(painelBotoes, gbc);

        add(painelForm, BorderLayout.NORTH);

        // ── TABELA ──
        JPanel painelTabela = new JPanel(new BorderLayout());
        painelTabela.setBackground(CoresUtil.ROSA_FUNDO);
        painelTabela.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

        JPanel painelBusca = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelBusca.setBackground(CoresUtil.ROSA_FUNDO);
        painelBusca.add(new JLabel("Buscar:"));
        txtBuscar = new JTextField(20);
        painelBusca.add(txtBuscar);
        JButton btnBuscar = new JButton("🔍");
        btnBuscar.addActionListener(e -> carregarTabelaFiltrada());
        painelBusca.add(btnBuscar);
        painelTabela.add(painelBusca, BorderLayout.NORTH);

        String[] colunas = {"Código Insumo", "Nome Insumo", "Rende Aplicações", "Serviço Associado"};
        modeloTabela = new DefaultTableModel(colunas, 0);
        tabelaInsumos = new JTable(modeloTabela);
        tabelaInsumos.setRowHeight(28);
        estilizarTabela(tabelaInsumos);

        painelTabela.add(new JScrollPane(tabelaInsumos));
        add(painelTabela, BorderLayout.CENTER);

        // Ações
        btnCadastrar.addActionListener(e -> cadastrar());
        btnExcluir.addActionListener(e -> excluir());
        btnLimpar.addActionListener(e -> limparCampos());
        btnVoltar.addActionListener(e -> dispose());
    }

    private void carregarCombos() {
        cmbInsumo.removeAllItems();
        for (Insumo i : insumoDAO.listar()) {
            cmbInsumo.addItem(i.getCodigoInsumo() + " - " + i.getNomeInsumo());
        }
        cmbServico.removeAllItems();
        for (Servico s : servicoDAO.listar()) {
            cmbServico.addItem(s.getCodigoServico() + " - " + s.getNomeServico());
        }
    }

private void carregarTabela() {

    modeloTabela.setRowCount(0);

    List<Insumo> lista =
            insumoDAO.listarPorEmpresa(
                    colaboradorLogado.getCodigoEmpresa()
            );

    for (Insumo i : lista) {

        modeloTabela.addRow(new Object[]{
            i.getCodigoInsumo(),
            i.getNomeInsumo(),
            "-",
            "-"
        });
    }
}
    private void carregarTabelaFiltrada() {

    modeloTabela.setRowCount(0);

    String busca =
            txtBuscar.getText().trim();

    List<Insumo> lista =
            insumoDAO.buscarPorNome(
                    busca,
                    colaboradorLogado.getCodigoEmpresa()
            );

    for (Insumo i : lista) {

        modeloTabela.addRow(new Object[]{
            i.getCodigoInsumo(),
            i.getNomeInsumo(),
            "-",
            "-"
        });
    }
}

   private void cadastrar() {

    if (cmbInsumo.getSelectedItem() == null
            || txtRendeAplicacoes.getText().isEmpty()) {

        JOptionPane.showMessageDialog(
                this,
                "Preencha todos os campos!",
                "Atenção",
                JOptionPane.WARNING_MESSAGE
        );

        return;
    }

    CadastroInsumo ci = new CadastroInsumo();

    String codInsumo =
            cmbInsumo.getSelectedItem()
            .toString()
            .split(" - ")[0];

    ci.setCodigoInsumo(
            Integer.parseInt(codInsumo)
    );

    ci.setRendeAplicacoes(
            Integer.parseInt(
                    txtRendeAplicacoes.getText().trim()
            )
    );

    if (cmbServico.getSelectedItem() != null) {

        String codServico =
                cmbServico.getSelectedItem()
                .toString()
                .split(" - ")[0];

        ci.setCodigoServico(
                Integer.parseInt(codServico)
        );
    }

    cadastroInsumoDAO.cadastrar(ci);

    carregarTabela();

    limparCampos();

    JOptionPane.showMessageDialog(
            this,
            "Insumo cadastrado com sucesso!"
    );
}

    private void excluir() {
        int linha = tabelaInsumos.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um insumo!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirmacao = JOptionPane.showConfirmDialog(this, "Deseja excluir?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirmacao == JOptionPane.YES_OPTION) {
            carregarTabela();
            JOptionPane.showMessageDialog(this, "Excluído com sucesso!");
        }
    }

    private void limparCampos() {
        txtRendeAplicacoes.setText("");
        if (cmbInsumo.getItemCount() > 0) cmbInsumo.setSelectedIndex(0);
        if (cmbServico.getItemCount() > 0) cmbServico.setSelectedIndex(0);
    }

    private JButton criarBotao(String texto, Color cor) {
    JButton btn = new JButton(texto);
    btn.setBackground(cor);

    if (cor.equals(CoresUtil.CINZA_VOLTAR) || cor.equals(CoresUtil.VERDE_CONFIRMAR)) {
        btn.setForeground(new Color(60, 60, 60));
    } else {
        btn.setForeground(Color.WHITE);
    }

    btn.setFont(new Font("Arial", Font.BOLD, 12));
    btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    return btn;
}

    private void estilizarTabela(JTable tabela) {
        tabela.getTableHeader().setBackground(new Color(201, 72, 105));
        tabela.getTableHeader().setForeground(Color.WHITE);
        tabela.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tabela.setBackground(Color.WHITE);
        tabela.setGridColor(CoresUtil.ROSA_SECUNDARIO);
    }
}