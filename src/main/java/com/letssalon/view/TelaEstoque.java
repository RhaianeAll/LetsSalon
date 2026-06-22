package com.letssalon.view;

import com.letssalon.dao.InsumoDAO;
import com.letssalon.model.Colaborador;
import com.letssalon.model.Insumo;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import com.letssalon.util.CoresUtil;

public class TelaEstoque extends JFrame {

    private Colaborador colaboradorLogado;
    private JTextField txtCodigo, txtNome, txtValor, txtQuantidade, txtCapacidade;
    private JButton btnCadastrar, btnAtualizar, btnExcluir, btnLimpar, btnVoltar;
    private JTable tabelaEstoque;
    private DefaultTableModel modeloTabela;
    private JTextField txtBuscar;

    private InsumoDAO insumoDAO = new InsumoDAO();

    public TelaEstoque(Colaborador colaborador) {
        this.colaboradorLogado = colaborador;
        initComponents();
        carregarTabela();
    }

    private void initComponents() {
        setTitle("Let's Salon - Estoque");
        setSize(850, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(CoresUtil.ROSA_FUNDO);

        // ── TOPO ──
        JPanel painelTopo = new JPanel(new BorderLayout());
        painelTopo.setBackground(new Color(201, 72, 105));
        painelTopo.setPreferredSize(new Dimension(850, 50));

        JLabel lblTitulo = new JLabel("  Estoque");
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

        // Código
        gbc.gridx = 0; gbc.gridy = 0;
        painelForm.add(new JLabel("Código:"), gbc);
        txtCodigo = new JTextField(8);
        txtCodigo.setEditable(false);
        gbc.gridx = 1;
        painelForm.add(txtCodigo, gbc);

        // Nome
        gbc.gridx = 0; gbc.gridy = 1;
        painelForm.add(new JLabel("Nome do Insumo:"), gbc);
        txtNome = new JTextField(25);
        gbc.gridx = 1; gbc.gridwidth = 3;
        painelForm.add(txtNome, gbc);

        // Valor
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 2;
        painelForm.add(new JLabel("Valor:"), gbc);
        txtValor = new JTextField(10);
        gbc.gridx = 1;
        painelForm.add(txtValor, gbc);

        // Quantidade
        gbc.gridx = 2;
        painelForm.add(new JLabel("Quantidade em Estoque:"), gbc);
        txtQuantidade = new JTextField(10);
        gbc.gridx = 3;
        painelForm.add(txtQuantidade, gbc);

        // Capacidade
        gbc.gridx = 0; gbc.gridy = 3;
        painelForm.add(new JLabel("Capacidade:"), gbc);
        txtCapacidade = new JTextField(20);
        txtCapacidade.setToolTipText("Ex: 300ml, 1kg, 600g, 10 unidades");
        gbc.gridx = 1; gbc.gridwidth = 3;
        painelForm.add(txtCapacidade, gbc);

        // Botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        painelBotoes.setBackground(CoresUtil.ROSA_FUNDO);

        btnCadastrar = criarBotao("Cadastrar", new Color(201, 72, 105));
        btnAtualizar = criarBotao("Atualizar", new Color(201, 72, 105));
        btnExcluir   = criarBotao("Excluir", CoresUtil.VERMELHO_EXCLUIR);
        btnLimpar    = criarBotao("Limpar", CoresUtil.ROSA_SECUNDARIO);
        btnVoltar    = criarBotao("Voltar", CoresUtil.CINZA_VOLTAR);

        painelBotoes.add(btnCadastrar);
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnLimpar);
        painelBotoes.add(btnVoltar);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 4;
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
        btnBuscar.addActionListener(e -> carregarTabela());
        painelBusca.add(btnBuscar);
        painelTabela.add(painelBusca, BorderLayout.NORTH);

        String[] colunas = {"Código", "Nome do Insumo", "Valor", "Quantidade", "Capacidade"};
        modeloTabela = new DefaultTableModel(colunas, 0);
        tabelaEstoque = new JTable(modeloTabela);
        tabelaEstoque.setRowHeight(28);
        estilizarTabela(tabelaEstoque);

        painelTabela.add(new JScrollPane(tabelaEstoque));
        add(painelTabela, BorderLayout.CENTER);

        // Ações
        btnCadastrar.addActionListener(e -> cadastrar());
        btnAtualizar.addActionListener(e -> atualizar());
        btnExcluir.addActionListener(e -> excluir());
        btnLimpar.addActionListener(e -> limparCampos());
        btnVoltar.addActionListener(e -> dispose());

        tabelaEstoque.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                selecionarLinha();
            }
        });
    }

    private void carregarTabela() {
        modeloTabela.setRowCount(0);
        List<Insumo> lista = insumoDAO.listar();
        for (Insumo i : lista) {
            modeloTabela.addRow(new Object[]{
                i.getCodigoInsumo(),
                i.getNomeInsumo(),
                i.getValorInsumo(),
                i.getQuantidadeInsumo(),
                i.getCapacidade()
            });
        }
    }

    private void cadastrar() {
        if (txtNome.getText().isEmpty() || txtQuantidade.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha os campos obrigatórios!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Insumo i = new Insumo();
        i.setNomeInsumo(txtNome.getText().trim());
        i.setValorInsumo(txtValor.getText().isEmpty() ? 0 : Double.parseDouble(txtValor.getText().replace(",", ".")));
        i.setQuantidadeInsumo(Integer.parseInt(txtQuantidade.getText().trim()));
        i.setCapacidade(txtCapacidade.getText().trim());
        i.setCodigoEmpresa(colaboradorLogado.getCodigoEmpresa());

        boolean sucesso = insumoDAO.cadastrar(i);

if (sucesso) {

    carregarTabela();
    limparCampos();

    JOptionPane.showMessageDialog(
        this,
        "Insumo cadastrado com sucesso!"
    );

} else {

    JOptionPane.showMessageDialog(
        this,
        "Erro ao cadastrar insumo!",
        "Erro",
        JOptionPane.ERROR_MESSAGE
    );
}
    }

    private void atualizar() {
        if (txtCodigo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um insumo na tabela!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Insumo i = new Insumo();
        i.setCodigoInsumo(Integer.parseInt(txtCodigo.getText()));
        i.setNomeInsumo(txtNome.getText().trim());
        i.setValorInsumo(txtValor.getText().isEmpty() ? 0 : Double.parseDouble(txtValor.getText().replace(",", ".")));
        i.setQuantidadeInsumo(Integer.parseInt(txtQuantidade.getText().trim()));
        i.setCapacidade(txtCapacidade.getText().trim());
        i.setCodigoEmpresa(colaboradorLogado.getCodigoEmpresa());

        insumoDAO.atualizar(i);
        carregarTabela();
        limparCampos();
        JOptionPane.showMessageDialog(this, "Insumo atualizado com sucesso!");
    }

    private void excluir() {
        if (txtCodigo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um insumo na tabela!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirmacao = JOptionPane.showConfirmDialog(this, "Deseja excluir este insumo?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirmacao == JOptionPane.YES_OPTION) {
            insumoDAO.excluir(Integer.parseInt(txtCodigo.getText()));
            carregarTabela();
            limparCampos();
            JOptionPane.showMessageDialog(this, "Insumo excluído com sucesso!");
        }
    }

    private void selecionarLinha() {
        int linha = tabelaEstoque.getSelectedRow();
        if (linha != -1) {
            txtCodigo.setText(modeloTabela.getValueAt(linha, 0).toString());
            txtNome.setText(modeloTabela.getValueAt(linha, 1).toString());
            txtValor.setText(modeloTabela.getValueAt(linha, 2).toString());
            txtQuantidade.setText(modeloTabela.getValueAt(linha, 3).toString());
            txtCapacidade.setText(modeloTabela.getValueAt(linha, 4).toString());
        }
    }

    private void limparCampos() {
        txtCodigo.setText("");
        txtNome.setText("");
        txtValor.setText("");
        txtQuantidade.setText("");
        txtCapacidade.setText("");
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