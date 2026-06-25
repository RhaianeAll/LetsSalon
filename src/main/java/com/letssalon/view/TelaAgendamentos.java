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
import com.letssalon.util.CoresUtil;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.xml.crypto.Data;

public class TelaAgendamentos extends JFrame {

    private Colaborador colaboradorLogado;
    private JTextField txtData, txtHora;
    private JComboBox<String> cmbCliente;
    private JComboBox<String> cmbColaborador, cmbServico;
    private JButton btnCadastrar, btnAtualizar, btnExcluir, btnLimpar, btnVoltar, btnFinalizar;
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
        getContentPane().setBackground(CoresUtil.ROSA_FUNDO);
    
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

        // ── FORMULÁRIO ──
        JPanel painelForm = new JPanel(new GridBagLayout());
        painelForm.setBackground(CoresUtil.ROSA_FUNDO);
        painelForm.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        //---PAINEL PRINCIPAL----
        JPanel painelNorth = new JPanel();
        painelNorth.setLayout(new BorderLayout());
        
        painelNorth.add(painelTopo, BorderLayout.NORTH);
        painelNorth.add(painelForm, BorderLayout.CENTER);
        
        add(painelNorth, BorderLayout.NORTH);

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
        painelBotoes.setBackground(CoresUtil.ROSA_FUNDO);

        btnCadastrar = criarBotao("Cadastrar", new Color(201, 72, 105));
        btnAtualizar = criarBotao("Atualizar", new Color(201, 72, 105));
        btnExcluir   = criarBotao("Excluir", CoresUtil.VERMELHO_EXCLUIR);
        btnLimpar    = criarBotao("Limpar", CoresUtil.ROSA_SECUNDARIO);
        btnVoltar    = criarBotao("Voltar", CoresUtil.CINZA_VOLTAR);
        btnFinalizar = criarBotao("Finalizar", new Color(120, 180, 120)
);

        painelBotoes.add(btnCadastrar);
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnLimpar);
        painelBotoes.add(btnVoltar);
        painelBotoes.add(btnFinalizar);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 4;
        painelForm.add(painelBotoes, gbc);


        // ── CENTRO: Pendentes + Tabela principal ──
        JPanel painelCentro = new JPanel(new GridLayout(2, 1, 0, 10));
        painelCentro.setBackground(CoresUtil.ROSA_FUNDO);
        painelCentro.setBorder(BorderFactory.createEmptyBorder(5, 20, 10, 20));

        // Tabela de confirmações pendentes
        JPanel painelPendentes = new JPanel(new BorderLayout());
        painelPendentes.setBackground(CoresUtil.ROSA_FUNDO);
        painelPendentes.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(201, 72, 105)),
            "Confirmações de Agendamentos Pendentes",
            0, 0,
            new Font("Arial", Font.BOLD, 12),
            new Color(201, 72, 105)
        ));

        String[] colunasPendentes = {"ID", "Cliente", "Telefone", "Data/Hora", "Profissional", "Serviço", "Confirmar"};
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

        JButton btnConfirmarPendente = criarBotao("✔ Confirmar Selecionado", CoresUtil.VERDE_CONFIRMAR);
        painelPendentes.add(btnConfirmarPendente, BorderLayout.SOUTH);
        btnConfirmarPendente.addActionListener(e -> confirmarAgendamentoPendente());

        painelPendentes.add(new JScrollPane(tabelaPendentes));

        // Busca + Tabela principal
        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.setBackground(CoresUtil.ROSA_FUNDO);

        JPanel painelBusca = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelBusca.setBackground(CoresUtil.ROSA_FUNDO);
        painelBusca.add(new JLabel("Buscar:"));
        txtBuscar = new JTextField(20);
        painelBusca.add(txtBuscar);
        JButton btnBuscar = new JButton("🔍");
        btnBuscar.addActionListener(e -> carregarDados());
        painelBusca.add(btnBuscar);
        painelPrincipal.add(painelBusca, BorderLayout.NORTH);

        String[] colunasAgendamentos = {
    "ID",
    "Data",
    "Hora",
    "Cliente",
    "Profissional",
    "Serviço",
    };
        modeloAgendamentos = new DefaultTableModel(colunasAgendamentos, 0);
        tabelaAgendamentos = new JTable(modeloAgendamentos);
        tabelaAgendamentos.getColumnModel()
                  .getColumn(0)
                  .setMinWidth(0);

        tabelaAgendamentos.getColumnModel()
                  .getColumn(0)
                  .setMaxWidth(0);

        tabelaAgendamentos.getColumnModel()
                  .getColumn(0)
                  .setWidth(0);
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
        btnFinalizar.addActionListener(e -> finalizarAgendamento());

        // Clique na tabela para selecionar
        tabelaAgendamentos.addMouseListener(
            new java.awt.event.MouseAdapter() {

            public void mouseClicked(
                    java.awt.event.MouseEvent e
        ) {
            selecionarLinha();
        }
    }
);

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

List<Object[]> agendamentos =
        agendaDAO.listarComNomes();

for (Object[] linha : agendamentos) {

    modeloAgendamentos.addRow(linha);
}

       // Carrega pendentes
modeloPendentes.setRowCount(0);

List<Object[]> pendentes =
        agendaDAO.listarPendentesComNomes();

for (Object[] linha : pendentes) {

    modeloPendentes.addRow(linha);
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

    try {

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate dataAgendamento =
                LocalDate.parse(
                        txtData.getText().trim(),
                        formatter
            );

        LocalDate hoje = LocalDate.now();

        if (dataAgendamento.isBefore(hoje)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Não é permitido agendar datas passadas!",
                    "Data inválida",
                    JOptionPane.WARNING_MESSAGE
        );

            return;
    }

}   catch (Exception e) {

        JOptionPane.showMessageDialog(
                this,
                "Data inválida! Use o formato dd/MM/yyyy",
                "Erro",
                JOptionPane.ERROR_MESSAGE
    );

        return;
}

        agenda.setStatus("PENDENTE");
        agenda.setCodigoEmpresa(colaboradorLogado.getCodigoEmpresa());

        if (cmbColaborador.getSelectedItem() != null) {
            String idStr = cmbColaborador.getSelectedItem().toString().split(" - ")[0];
            agenda.setIdColaborador(Integer.parseInt(idStr));
        }
        if (cmbServico.getSelectedItem() != null) {
            String codStr = cmbServico.getSelectedItem().toString().split(" - ")[0];
            agenda.setCodigoServico(Integer.parseInt(codStr));
        }
        
    boolean conflito =
        agendaDAO.verificarConflitoHorario(
            agenda.getData(),
            agenda.getHorario(),
            agenda.getIdColaborador()
    );

    if (conflito) {

        JOptionPane.showMessageDialog(
            this,
            "Horário já está ocupado!",
            "Conflito de horário",
            JOptionPane.WARNING_MESSAGE
    );

        return;
}
        
        boolean sucesso = agendaDAO.cadastrar(agenda);

if (sucesso) {

    carregarDados();
    limparCampos();

    JOptionPane.showMessageDialog(
        this,
        "Agendamento cadastrado com sucesso!"
    );

} else {

    JOptionPane.showMessageDialog(
        this,
        "Erro ao cadastrar agendamento!",
        "Erro",
        JOptionPane.ERROR_MESSAGE
    );
}
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
        int idParaExcluir = 
                Integer.parseInt(
                    modeloAgendamentos
                            .getValueAt(linhaSelecionada, 0)
                            .toString()
                );
        
        int confirmacao = 
                JOptionPane.showConfirmDialog(
                        this, "Deseja excluir este agendamento?", 
                        "Confirmar", 
                        JOptionPane.YES_NO_OPTION
                );
        if (confirmacao == JOptionPane.YES_OPTION) {
            agendaDAO.cancelar(idParaExcluir);
            carregarDados();
            JOptionPane.showMessageDialog(
                    this, 
                    "Agendamento cancelado com sucesso!"
            );
        }
    }
    
 
    private void finalizarAgendamento() {

        int linhaSelecionada =
                tabelaAgendamentos.getSelectedRow();

        if (linhaSelecionada == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Selecione um agendamento!",
                    "Atenção",
                    JOptionPane.WARNING_MESSAGE
        );

            return;
    }

       int idAgendamento = 
               Integer.parseInt(
                    modeloAgendamentos
                        .getValueAt(linhaSelecionada, 0)
                        .toString()
               );

        if (idAgendamento == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Agendamento não encontrado!",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
        );

            return;
    }
        boolean sucesso =
        agendaDAO.finalizar(idAgendamento);
        if (sucesso) {
            carregarDados();
            JOptionPane.showMessageDialog(
            this,
                    "Agendamento finalizado!"
            );
            
        } else {
            JOptionPane.showMessageDialog(
            this,
                    ("Erro ao finalizar agendamento!")
            );
        }
}

    private void selecionarLinha() {
        int linha = tabelaAgendamentos.getSelectedRow();
        if (linha != -1) {
            txtData.setText(modeloAgendamentos.getValueAt(linha, 1).toString());
            txtHora.setText(modeloAgendamentos.getValueAt(linha, 2).toString());
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