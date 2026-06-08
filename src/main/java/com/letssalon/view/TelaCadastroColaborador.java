package com.letssalon.view;

import com.letssalon.dao.ColaboradorDAO;
import com.letssalon.model.Colaborador;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaCadastroColaborador extends JFrame {

    private Colaborador colaboradorLogado;
    private JTextField txtId, txtNome, txtCpf, txtTelefone, txtEmailColaborador;
    private JPasswordField txtSenha;
    private JComboBox<String> cmbServicos;
    private JButton btnCadastrar, btnAtualizar, btnExcluir, btnLimpar, btnVoltar;
    private JTable tabelaColaboradores;
    private DefaultTableModel modeloTabela;
    private JTextField txtBuscar;

    private ColaboradorDAO colaboradorDAO = new ColaboradorDAO();

    public TelaCadastroColaborador(Colaborador colaborador) {
        this.colaboradorLogado = colaborador;
        initComponents();
        carregarTabela();
    }

    private void initComponents() {
        setTitle("Let's Salon - Cadastro de Profissionais");
        setSize(850, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(255, 240, 243));

        // ── TOPO ──
        JPanel painelTopo = new JPanel(new BorderLayout());
        painelTopo.setBackground(new Color(201, 72, 105));
        painelTopo.setPreferredSize(new Dimension(850, 50));

        JLabel lblTitulo = new JLabel("  Cadastro de Funcionários(as)");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);
        painelTopo.add(lblTitulo, BorderLayout.WEST);

        JLabel lblColaborador = new JLabel(colaboradorLogado.getNomeColaborador() + "  ");
        lblColaborador.setForeground(Color.WHITE);
        painelTopo.add(lblColaborador, BorderLayout.EAST);

        add(painelTopo, BorderLayout.NORTH);

        // ── FORMULÁRIO ──
        JPanel painelForm = new JPanel(new GridBagLayout());
        painelForm.setBackground(new Color(255, 240, 243));
        painelForm.setBorder(BorderFactory.createEmptyBorder(15, 30, 10, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Id
        gbc.gridx = 0; gbc.gridy = 0;
        painelForm.add(new JLabel("Id:"), gbc);
        txtId = new JTextField(5);
        txtId.setEditable(false);
        gbc.gridx = 1;
        painelForm.add(txtId, gbc);

        // Nome
        gbc.gridx = 0; gbc.gridy = 1;
        painelForm.add(new JLabel("Nome:"), gbc);
        txtNome = new JTextField(25);
        gbc.gridx = 1; gbc.gridwidth = 3;
        painelForm.add(txtNome, gbc);

        // CPF
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 2;
        painelForm.add(new JLabel("CPF:"), gbc);
        txtCpf = new JTextField(15);
        txtCpf.setToolTipText("Ex: 000.000.000-00");
        gbc.gridx = 1;
        painelForm.add(txtCpf, gbc);

        // Telefone
        gbc.gridx = 2;
        painelForm.add(new JLabel("Telefone:"), gbc);
        txtTelefone = new JTextField(15);
        txtTelefone.setToolTipText("Ex: 49 99999999");
        gbc.gridx = 3;
        painelForm.add(txtTelefone, gbc);

        // Usuário
        gbc.gridx = 0; gbc.gridy = 3;
        painelForm.add(new JLabel("Usuário:"), gbc);
        txtEmailColaborador = new JTextField(15);
        gbc.gridx = 1;
        painelForm.add(txtEmailColaborador, gbc);

        // Senha
        gbc.gridx = 2;
        painelForm.add(new JLabel("Senha:"), gbc);
        txtSenha = new JPasswordField(15);
        gbc.gridx = 3;
        painelForm.add(txtSenha, gbc);

        // Serviços
        gbc.gridx = 2;
        painelForm.add(new JLabel("Serviços:"), gbc);
        cmbServicos = new JComboBox<>();
        gbc.gridx = 3;
        painelForm.add(cmbServicos, gbc);

        // Botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        painelBotoes.setBackground(new Color(255, 240, 243));

        btnCadastrar = criarBotao("Cadastrar", new Color(201, 72, 105));
        btnAtualizar = criarBotao("Atualizar", new Color(201, 72, 105));
        btnExcluir   = criarBotao("Excluir", new Color(220, 80, 80));
        btnLimpar    = criarBotao("Limpar", new Color(240, 150, 170));
        btnVoltar    = criarBotao("Voltar", new Color(150, 150, 150));

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
        painelTabela.setBackground(new Color(255, 240, 243));
        painelTabela.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

        JPanel painelBusca = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelBusca.setBackground(new Color(255, 240, 243));
        painelBusca.add(new JLabel("Buscar:"));
        txtBuscar = new JTextField(20);
        painelBusca.add(txtBuscar);
        JButton btnBuscar = new JButton("🔍");
        btnBuscar.addActionListener(e -> carregarTabela());
        painelBusca.add(btnBuscar);
        painelTabela.add(painelBusca, BorderLayout.NORTH);

        String[] colunas = {"Id", "Nome", "CPF", "Telefone", "Serviços"};
        modeloTabela = new DefaultTableModel(colunas, 0);
        tabelaColaboradores = new JTable(modeloTabela);
        tabelaColaboradores.setRowHeight(28);
        estilizarTabela(tabelaColaboradores);

        painelTabela.add(new JScrollPane(tabelaColaboradores));
        add(painelTabela, BorderLayout.CENTER);

        // Ações
        btnCadastrar.addActionListener(e -> cadastrar());
        btnAtualizar.addActionListener(e -> atualizar());
        btnExcluir.addActionListener(e -> excluir());
        btnLimpar.addActionListener(e -> limparCampos());
        btnVoltar.addActionListener(e -> dispose());

        tabelaColaboradores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                selecionarLinha();
            }
        });
    }

    private void carregarTabela() {
        modeloTabela.setRowCount(0);
        List<Colaborador> lista = colaboradorDAO.listarPorEmpresa(colaboradorLogado.getCodigoEmpresa());
        for (Colaborador c : lista) {
            modeloTabela.addRow(new Object[]{
                c.getIdColaborador(),
                c.getNomeColaborador(),
                c.getCpfColaborador(),
                c.getTelefoneColaborador(),
                c.getFuncaoColaborador()
            });
        }
    }

    private void cadastrar() {
        if (txtNome.getText().isEmpty() || txtCpf.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha os campos obrigatórios!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Colaborador c = new Colaborador();
        c.setNomeColaborador(txtNome.getText().trim());
        c.setCpfColaborador(txtCpf.getText().trim());
        c.setTelefoneColaborador(txtTelefone.getText().trim());
        c.setEmailColaborador(txtEmailColaborador.getText().trim());
        String senhaDigitada = new String(txtSenha.getPassword()).trim();
        c.setSenhaColaborador(senhaDigitada.isEmpty() ? "123456" : senhaDigitada);
        c.setPerfil("colaborador");
        c.setCodigoEmpresa(colaboradorLogado.getCodigoEmpresa());

        colaboradorDAO.cadastrar(c);
        carregarTabela();
        limparCampos();
        JOptionPane.showMessageDialog(this, "Profissional cadastrado com sucesso!");
    }

    private void atualizar() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um profissional na tabela!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Colaborador c = new Colaborador();
        c.setIdColaborador(Integer.parseInt(txtId.getText()));
        c.setNomeColaborador(txtNome.getText().trim());
        c.setCpfColaborador(txtCpf.getText().trim());
        c.setTelefoneColaborador(txtTelefone.getText().trim());
        c.setEmailColaborador(txtEmailColaborador.getText().trim());
        c.setCodigoEmpresa(colaboradorLogado.getCodigoEmpresa());

        colaboradorDAO.atualizar(c);
        carregarTabela();
        limparCampos();
        JOptionPane.showMessageDialog(this, "Profissional atualizado com sucesso!");
    }

    private void excluir() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um profissional na tabela!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirmacao = JOptionPane.showConfirmDialog(this, "Deseja excluir este profissional?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirmacao == JOptionPane.YES_OPTION) {
            colaboradorDAO.excluir(Integer.parseInt(txtId.getText()));
            carregarTabela();
            limparCampos();
            JOptionPane.showMessageDialog(this, "Profissional excluído com sucesso!");
        }
    }

    private void selecionarLinha() {
        int linha = tabelaColaboradores.getSelectedRow();
        if (linha != -1) {
            txtId.setText(modeloTabela.getValueAt(linha, 0).toString());
            txtNome.setText(modeloTabela.getValueAt(linha, 1).toString());
            txtCpf.setText(modeloTabela.getValueAt(linha, 2).toString());
            txtTelefone.setText(modeloTabela.getValueAt(linha, 3).toString());
        }
    }

    private void limparCampos() {
        txtId.setText("");
        txtNome.setText("");
        txtCpf.setText("");
        txtTelefone.setText("");
        txtEmailColaborador.setText("");
        txtSenha.setText("");
    }

    private JButton criarBotao(String texto, Color cor) {
        JButton btn = new JButton(texto);
        btn.setBackground(cor);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void estilizarTabela(JTable tabela) {
        tabela.getTableHeader().setBackground(new Color(201, 72, 105));
        tabela.getTableHeader().setForeground(Color.WHITE);
        tabela.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tabela.setBackground(Color.WHITE);
        tabela.setGridColor(new Color(240, 150, 170));
    }
}