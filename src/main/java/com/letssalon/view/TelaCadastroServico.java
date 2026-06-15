package com.letssalon.view;

import com.letssalon.dao.InsumoDAO;
import com.letssalon.dao.ServicoDAO;
import com.letssalon.model.Colaborador;
import com.letssalon.model.Insumo;
import com.letssalon.model.Servico;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import com.letssalon.util.CoresUtil;

public class TelaCadastroServico extends JFrame {

    private Colaborador colaboradorLogado;
    private JTextField txtCodigo, txtNome, txtValor, txtDuracao, txtQuantidade;
    private JComboBox<String> cmbInsumos;
    private JButton btnCadastrar, btnAtualizar, btnExcluir, btnLimpar, btnVoltar;
    private JTable tabelaServicos;
    private DefaultTableModel modeloTabela;
    private JTextField txtBuscar;

    private ServicoDAO servicoDAO = new ServicoDAO();
    private InsumoDAO insumoDAO = new InsumoDAO();

    public TelaCadastroServico(Colaborador colaborador) {
        this.colaboradorLogado = colaborador;
        initComponents();
        carregarTabela();
        carregarInsumos();
    }

    private void initComponents() {
        setTitle("Let's Salon - Cadastro de Serviços");
        setSize(850, 620);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(CoresUtil.ROSA_FUNDO);

        // ── TOPO ──
        JPanel painelTopo = new JPanel(new BorderLayout());
        painelTopo.setBackground(new Color(201, 72, 105));
        painelTopo.setPreferredSize(new Dimension(850, 50));

        JLabel lblTitulo = new JLabel("  Cadastro de Serviços");
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

        // Nome do Serviço
        gbc.gridx = 0; gbc.gridy = 1;
        painelForm.add(new JLabel("Nome do Serviço:"), gbc);
        txtNome = new JTextField(25);
        gbc.gridx = 1; gbc.gridwidth = 3;
        painelForm.add(txtNome, gbc);

        // Valor
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 2;
        painelForm.add(new JLabel("Valor:"), gbc);
        txtValor = new JTextField(10);
        txtValor.setToolTipText("Ex: 12,50");
        gbc.gridx = 1;
        painelForm.add(txtValor, gbc);

        // Duração
        gbc.gridx = 2;
        painelForm.add(new JLabel("Duração Aproximada:"), gbc);
        txtDuracao = new JTextField(10);
        txtDuracao.setToolTipText("Ex: 1h, 30min");
        gbc.gridx = 3;
        painelForm.add(txtDuracao, gbc);

        // Insumos
        gbc.gridx = 0; gbc.gridy = 3;
        painelForm.add(new JLabel("Insumos Necessários:"), gbc);
        cmbInsumos = new JComboBox<>();
        gbc.gridx = 1;
        painelForm.add(cmbInsumos, gbc);

        // Quantidade
        gbc.gridx = 2;
        painelForm.add(new JLabel("Quantidade:"), gbc);
        txtQuantidade = new JTextField(8);
        gbc.gridx = 3;
        painelForm.add(txtQuantidade, gbc);

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

        String[] colunas = {"Código", "Nome do Serviço", "Valor", "Duração Aproximada", "Insumos Necessários", "Quantidade"};
        modeloTabela = new DefaultTableModel(colunas, 0);
        tabelaServicos = new JTable(modeloTabela);
        tabelaServicos.setRowHeight(28);
        estilizarTabela(tabelaServicos);

        painelTabela.add(new JScrollPane(tabelaServicos));
        add(painelTabela, BorderLayout.CENTER);

        // Ações
        btnCadastrar.addActionListener(e -> cadastrar());
        btnAtualizar.addActionListener(e -> atualizar());
        btnExcluir.addActionListener(e -> excluir());
        btnLimpar.addActionListener(e -> limparCampos());
        btnVoltar.addActionListener(e -> dispose());

        tabelaServicos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                selecionarLinha();
            }
        });
    }

    private void carregarInsumos() {
        cmbInsumos.removeAllItems();
        List<Insumo> insumos = insumoDAO.listar();
        for (Insumo i : insumos) {
            cmbInsumos.addItem(i.getCodigoInsumo() + " - " + i.getNomeInsumo());
        }
    }

    private void carregarTabela() {
        modeloTabela.setRowCount(0);
        List<Servico> lista = servicoDAO.listar();
        for (Servico s : lista) {
            modeloTabela.addRow(new Object[]{
                s.getCodigoServico(),
                s.getNomeServico(),
                s.getValorServico(),
                s.getDuracaoAproximada(),
                s.getCodigoInsumo(),
                s.getQuantidadeInsumo()
            });
        }
    }

    private void cadastrar() {
        if (txtNome.getText().isEmpty() || txtValor.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha os campos obrigatórios!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Servico s = new Servico();
        s.setNomeServico(txtNome.getText().trim());
        s.setValorServico(Double.parseDouble(txtValor.getText().replace(",", ".")));
        s.setDuracaoAproximada(txtDuracao.getText().trim());
        s.setQuantidadeInsumo(txtQuantidade.getText().isEmpty() ? 0 : Integer.parseInt(txtQuantidade.getText()));
        s.setCodigoEmpresa(colaboradorLogado.getCodigoEmpresa());

        if (cmbInsumos.getSelectedItem() != null) {
            String codStr = cmbInsumos.getSelectedItem().toString().split(" - ")[0];
            s.setCodigoInsumo(Integer.parseInt(codStr));
        }

        servicoDAO.cadastrar(s);
        carregarTabela();
        limparCampos();
        JOptionPane.showMessageDialog(this, "Serviço cadastrado com sucesso!");
    }

    private void atualizar() {
        if (txtCodigo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um serviço na tabela!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Servico s = new Servico();
        s.setCodigoServico(Integer.parseInt(txtCodigo.getText()));
        s.setNomeServico(txtNome.getText().trim());
        s.setValorServico(Double.parseDouble(txtValor.getText().replace(",", ".")));
        s.setDuracaoAproximada(txtDuracao.getText().trim());
        s.setQuantidadeInsumo(txtQuantidade.getText().isEmpty() ? 0 : Integer.parseInt(txtQuantidade.getText()));

        servicoDAO.atualizar(s);
        carregarTabela();
        limparCampos();
        JOptionPane.showMessageDialog(this, "Serviço atualizado com sucesso!");
    }

    private void excluir() {
        if (txtCodigo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um serviço na tabela!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirmacao = JOptionPane.showConfirmDialog(this, "Deseja excluir este serviço?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirmacao == JOptionPane.YES_OPTION) {
            servicoDAO.excluir(Integer.parseInt(txtCodigo.getText()));
            carregarTabela();
            limparCampos();
            JOptionPane.showMessageDialog(this, "Serviço excluído com sucesso!");
        }
    }

    private void selecionarLinha() {
        int linha = tabelaServicos.getSelectedRow();
        if (linha != -1) {
            txtCodigo.setText(modeloTabela.getValueAt(linha, 0).toString());
            txtNome.setText(modeloTabela.getValueAt(linha, 1).toString());
            txtValor.setText(modeloTabela.getValueAt(linha, 2).toString());
            txtDuracao.setText(modeloTabela.getValueAt(linha, 3).toString());
        }
    }

    private void limparCampos() {
        txtCodigo.setText("");
        txtNome.setText("");
        txtValor.setText("");
        txtDuracao.setText("");
        txtQuantidade.setText("");
        cmbInsumos.setSelectedIndex(0);
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