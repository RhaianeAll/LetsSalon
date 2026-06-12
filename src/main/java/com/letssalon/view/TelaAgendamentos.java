package com.letssalon.view;

import com.letssalon.dao.AgendaDAO;
import com.letssalon.dao.ClienteDAO;
import com.letssalon.dao.ColaboradorDAO;
import com.letssalon.dao.ServicoDAO;
import com.letssalon.model.Agenda;
import com.letssalon.model.Cliente;
import com.letssalon.model.Colaborador;
import com.letssalon.model.Servico;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaAgendamentos extends JFrame {

    private Colaborador colaboradorLogado;
    private JTextField txtData, txtHora;
    private JComboBox<String> cmbCliente;
    private JComboBox<String> cmbColaborador, cmbServico;
    private JButton btnCadastrar, btnAtualizar, btnExcluir, btnLimpar, btnVoltar;
    private JTable tabelaAgendamentos, tabelaPendentes;
    private DefaultTableModel modeloAgendamentos, modeloPendentes;
    private JTextField txtBuscar;

    private AgendaDAO agendaDAO = new AgendaDAO();
    private ClienteDAO clienteDAO = new ClienteDAO();
    private ColaboradorDAO colaboradorDAO = new ColaboradorDAO();
    private ServicoDAO servicoDAO = new ServicoDAO();

    public TelaAgendamentos(Colaborador colaborador) {
        this.colaboradorLogado = colaborador;
        initComponents();
        carregarDados();
    }

    private void initComponents() {
        setTitle("Let's Salon - Agendamentos");
        setSize(950, 750);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(255, 240, 243));

        // ── TOPO ──
        JPanel painelTopo = new JPanel(new BorderLayout());
        painelTopo.setBackground(new Color(201, 72, 105));
        painelTopo.setPreferredSize(new Dimension(950, 50));

        JLabel lblTitulo = new JLabel("  Agendamentos");
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
        painelForm.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Data
        gbc.gridx = 0; gbc.gridy = 0;
        painelForm.add(new JLabel("Data:"), gbc);
        txtData = new JTextField("Ex: 12/06/2026", 12);
        gbc.gridx = 1;
        painelForm.add(txtData, gbc);

        // Hora
        gbc.gridx = 2;
        painelForm.add(new JLabel("Hora:"), gbc);
        txtHora = new JTextField("Ex: 16:30", 8);
        gbc.gridx = 3;
        painelForm.add(txtHora, gbc);

        // Cliente
        gbc.gridx = 0; gbc.gridy = 1;
        painelForm.add(new JLabel("Cliente:"), gbc);

        cmbCliente = new JComboBox<>();

        gbc.gridx = 1;
        gbc.gridwidth = 3;
        painelForm.add(cmbCliente, gbc);

        // Colaborador
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 2;
        painelForm.add(new JLabel("Funcionário(a):"), gbc);
        cmbColaborador = new JComboBox<>();
        gbc.gridx = 1;
        painelForm.add(cmbColaborador, gbc);

        // Serviço
        gbc.gridx = 2;
        painelForm.add(new JLabel("Serviço:"), gbc);
        cmbServico = new JComboBox<>();
        gbc.gridx = 3;
        painelForm.add(cmbServico, gbc);

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

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 4;
        painelForm.add(painelBotoes, gbc);

        add(painelForm, BorderLayout.NORTH);

        // ── CENTRO: Pendentes + Tabela principal ──
        JPanel painelCentro = new JPanel(new GridLayout(2, 1, 0, 10));
        painelCentro.setBackground(new Color(255, 240, 243));
        painelCentro.setBorder(BorderFactory.createEmptyBorder(5, 20, 10, 20));

        // Tabela de confirmações pendentes
        JPanel painelPendentes = new JPanel(new BorderLayout());
        painelPendentes.setBackground(new Color(255, 240, 243));
        painelPendentes.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(201, 72, 105)),
            "Confirmações de Agendamentos Pendentes",
            0, 0,
            new Font("Arial", Font.BOLD, 12),
            new Color(201, 72, 105)
        ));

        String[] colunasPendentes = {"ID", "Nome Cliente", "Telefone", "Data/Hora", "Id Funcionário", "Código Serviço", "Confirmar"};
        modeloPendentes = new DefaultTableModel(colunasPendentes, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };
        tabelaPendentes = new JTable(modeloPendentes);
        tabelaPendentes.setRowHeight(28);
        estilizarTabela(tabelaPendentes);
        // Esconder coluna ID (índice 0)
        tabelaPendentes.getColumnModel().getColumn(0).setMinWidth(0);
        tabelaPendentes.getColumnModel().getColumn(0).setMaxWidth(0);
        tabelaPendentes.getColumnModel().getColumn(0).setWidth(0);

        JButton btnConfirmarPendente = criarBotao("✔ Confirmar Selecionado", new Color(60, 150, 80));
        painelPendentes.add(btnConfirmarPendente, BorderLayout.SOUTH);
        btnConfirmarPendente.addActionListener(e -> confirmarAgendamentoPendente());

        painelPendentes.add(new JScrollPane(tabelaPendentes));

        // Busca + Tabela principal
        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.setBackground(new Color(255, 240, 243));

        JPanel painelBusca = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelBusca.setBackground(new Color(255, 240, 243));
        painelBusca.add(new JLabel("Buscar:"));
        txtBuscar = new JTextField(20);
        painelBusca.add(txtBuscar);
        JButton btnBuscar = new JButton("🔍");
        btnBuscar.addActionListener(e -> carregarDados());
        painelBusca.add(btnBuscar);
        painelPrincipal.add(painelBusca, BorderLayout.NORTH);

        String[] colunasAgendamentos = {"Data", "Hora", "Cliente", "Funcionário", "Serviço", "Qtd Insumos"};
        modeloAgendamentos = new DefaultTableModel(colunasAgendamentos, 0);
        tabelaAgendamentos = new JTable(modeloAgendamentos);
        tabelaAgendamentos.setRowHeight(28);
        estilizarTabela(tabelaAgendamentos);
        painelPrincipal.add(new JScrollPane(tabelaAgendamentos));

        painelCentro.add(painelPendentes);
        painelCentro.add(painelPrincipal);

        add(painelCentro, BorderLayout.CENTER);

        // Ações
        btnCadastrar.addActionListener(e -> cadastrarAgendamento());
        btnExcluir.addActionListener(e -> excluirAgendamento());
        btnLimpar.addActionListener(e -> limparCampos());
        btnVoltar.addActionListener(e -> dispose());

        // Clique na tabela para selecionar
        tabelaAgendamentos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                selecionarLinha();
            }
        });
    }

    private void carregarDados() {
        // Carrega clientes
        cmbCliente.removeAllItems();

        List<Cliente> clientes = clienteDAO.listar();

        for (Cliente c : clientes) {
            cmbCliente.addItem(
            c.getCpfCliente() + " - " + c.getNomeCliente()
    );
}
        // Carrega colaboradores no combo (somente da empresa logada)
        cmbColaborador.removeAllItems();
        List<Colaborador> colaboradores = colaboradorDAO.listarPorEmpresa(colaboradorLogado.getCodigoEmpresa());
        for (Colaborador c : colaboradores) {
            cmbColaborador.addItem(c.getIdColaborador() + " - " + c.getNomeColaborador());
        }

        // Carrega serviços no combo
        cmbServico.removeAllItems();
        List<Servico> servicos = servicoDAO.listar();
        for (Servico s : servicos) {
            cmbServico.addItem(s.getCodigoServico() + " - " + s.getNomeServico());
        }

        // Carrega agendamentos na tabela
        modeloAgendamentos.setRowCount(0);
        List<Agenda> agendamentos = agendaDAO.listar();
        for (Agenda a : agendamentos) {
            modeloAgendamentos.addRow(new Object[]{
                a.getData(), a.getHorario(), a.getCpfCliente(),
                a.getIdColaborador(), a.getCodigoServico(), "-"
            });
        }

        // Carrega pendentes
        modeloPendentes.setRowCount(0);
        for (Agenda a : agendamentos) {
            if (!a.isConfirmado()) {
                modeloPendentes.addRow(new Object[]{
                    a.getIdAgendamento(),
                    a.getCpfCliente(), "-",
                    a.getData() + " " + a.getHorario(),
                    a.getIdColaborador(), a.getCodigoServico(), "Confirmar"
                });
            }
        }
    }

    private void cadastrarAgendamento() {
        if (txtData.getText().isEmpty()
        || txtHora.getText().isEmpty()
        || cmbCliente.getSelectedItem() == null) {

    JOptionPane.showMessageDialog(
        this,
        "Preencha todos os campos!",
        "Atenção",
        JOptionPane.WARNING_MESSAGE
    );

    return;
}

        Agenda agenda = new Agenda();
        if (cmbCliente.getSelectedItem() != null) {

            String clienteSelecionado =
            cmbCliente.getSelectedItem().toString();

            String cpfCliente =
            clienteSelecionado.split(" - ")[0];

        agenda.setCpfCliente(cpfCliente);
}       agenda.setData(txtData.getText().trim());
        agenda.setHorario(txtHora.getText().trim());
        agenda.setConfirmado(false);
        agenda.setCodigoEmpresa(colaboradorLogado.getCodigoEmpresa());

        if (cmbColaborador.getSelectedItem() != null) {
            String idStr = cmbColaborador.getSelectedItem().toString().split(" - ")[0];
            agenda.setIdColaborador(Integer.parseInt(idStr));
        }
        if (cmbServico.getSelectedItem() != null) {
            String codStr = cmbServico.getSelectedItem().toString().split(" - ")[0];
            agenda.setCodigoServico(Integer.parseInt(codStr));
        }

        agendaDAO.cadastrar(agenda);
        carregarDados();
        limparCampos();
        JOptionPane.showMessageDialog(this, "Agendamento cadastrado com sucesso!");
    }

    private void confirmarAgendamentoPendente() {
        int linha = tabelaPendentes.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um agendamento pendente para confirmar!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int idAgendamento = (int) modeloPendentes.getValueAt(linha, 0);
        agendaDAO.confirmar(idAgendamento);
        JOptionPane.showMessageDialog(this, "Agendamento confirmado com sucesso!");
        carregarDados();
    }

    private void excluirAgendamento() {
        int linhaSelecionada = tabelaAgendamentos.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um agendamento!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        // Busca o id do agendamento pelo cpf+data na lista (coluna 0=data, 1=hora, 2=cpf)
        String data = modeloAgendamentos.getValueAt(linhaSelecionada, 0).toString();
        String hora = modeloAgendamentos.getValueAt(linhaSelecionada, 1).toString();
        String cpf  = modeloAgendamentos.getValueAt(linhaSelecionada, 2).toString();
        int idParaExcluir = -1;
        for (Agenda a : agendaDAO.listar()) {
            if (a.getData().equals(data) && a.getHorario().equals(hora) && a.getCpfCliente().equals(cpf)) {
                idParaExcluir = a.getIdAgendamento();
                break;
            }
        }
        if (idParaExcluir == -1) {
            JOptionPane.showMessageDialog(this, "Agendamento não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int confirmacao = JOptionPane.showConfirmDialog(this, "Deseja excluir este agendamento?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirmacao == JOptionPane.YES_OPTION) {
            agendaDAO.excluir(idParaExcluir);
            carregarDados();
            JOptionPane.showMessageDialog(this, "Agendamento excluído com sucesso!");
        }
    }

    private void selecionarLinha() {
        int linha = tabelaAgendamentos.getSelectedRow();
        if (linha != -1) {
            txtData.setText(modeloAgendamentos.getValueAt(linha, 0).toString());
            txtHora.setText(modeloAgendamentos.getValueAt(linha, 1).toString());
            cmbCliente.setSelectedIndex(0);
        }
    }

    private void limparCampos() {
        txtData.setText("");
        txtHora.setText("");
        cmbCliente.setSelectedIndex(0);
        cmbColaborador.setSelectedIndex(0);
        cmbServico.setSelectedIndex(0);
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