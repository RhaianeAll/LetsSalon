package com.letssalon.view;

import com.letssalon.dao.AgendaDAO;
import com.letssalon.dao.ColaboradorDAO;
import com.letssalon.dao.ServicoDAO;
import com.letssalon.model.Agenda;
import com.letssalon.model.Cliente;
import com.letssalon.model.Colaborador;
import com.letssalon.model.Servico;
import com.letssalon.util.BotaoUtil;
import static com.letssalon.util.BotaoUtil.criarBotao;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import com.letssalon.util.CoresUtil;
import javax.swing.table.DefaultTableCellRenderer;

public class TelaAgendaCliente extends JFrame {

    private Cliente clienteLogado;
    private JTextField txtData, txtHora;
    private JComboBox<String> cmbColaborador, cmbServico;
    private JButton btnAgendar, btnCancelar, btnSair;
    private JTable tabelaMeusAgendamentos;
    private DefaultTableModel modeloTabela;

    private AgendaDAO agendaDAO = new AgendaDAO();
    private ColaboradorDAO colaboradorDAO = new ColaboradorDAO();
    private ServicoDAO servicoDAO = new ServicoDAO();

    // Guarda os objetos completos dos profissionais carregados.
    // Assim mostramos apenas o nome no ComboBox,
    // mas continuamos tendo acesso ao ID.
    private List<Colaborador> listaColaboradores;

    // Guarda os objetos completos dos serviços.
    private List<Servico> listaServicos;
    
    public TelaAgendaCliente(Cliente cliente) {
        this.clienteLogado = cliente;
        initComponents();
        carregarDados();
    }

    private void initComponents() {
        setTitle("Let's Salon - Meus Agendamentos");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(CoresUtil.ROSA_FUNDO);

        // ── TOPO ──
        JPanel painelTopo = new JPanel(new BorderLayout());
        painelTopo.setBackground(new Color(201, 72, 105));
        painelTopo.setPreferredSize(new Dimension(750, 50));

        JLabel lblLogo = new JLabel("  LET'S SALON");
        lblLogo.setFont(new Font("Arial", Font.BOLD, 20));
        lblLogo.setForeground(Color.WHITE);
        painelTopo.add(lblLogo, BorderLayout.WEST);

        JLabel lblNomeCliente = new JLabel("Olá, " + clienteLogado.getNomeCliente() + "  ");
        lblNomeCliente.setFont(new Font("Arial", Font.BOLD, 14));
        lblNomeCliente.setForeground(Color.WHITE);
        painelTopo.add(lblNomeCliente, BorderLayout.EAST);

        add(painelTopo, BorderLayout.NORTH);

        // ── FORMULÁRIO ──
        JPanel painelForm = new JPanel(new GridBagLayout());
        painelForm.setBackground(CoresUtil.ROSA_FUNDO);
        painelForm.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(201, 72, 105)),
            "Novo Agendamento",
            0, 0,
            new Font("Arial", Font.BOLD, 13),
            new Color(201, 72, 105)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Data
        gbc.gridx = 0;
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

        // profissional
        gbc.gridx = 0; gbc.gridy = 1;
        painelForm.add(new JLabel("Profissional:"), gbc);
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
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        painelBotoes.setBackground(CoresUtil.ROSA_FUNDO);

        btnAgendar = criarBotao("Agendar", new Color(201, 72, 105));

        painelBotoes.add(btnAgendar);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 4;
        painelForm.add(painelBotoes, gbc);


        // ── TABELA: Meus agendamentos ──
        JPanel painelTabela = new JPanel(new BorderLayout());
        painelTabela.setBackground(CoresUtil.ROSA_FUNDO);
        painelTabela.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(201, 72, 105)),
            "Meus Agendamentos",
            0, 0,
            new Font("Arial", Font.BOLD, 13),
            new Color(201, 72, 105)
        ));

        String[] colunas = {"Data", "Hora", "Profissional", "Serviço", "Status"};
        modeloTabela = new DefaultTableModel(colunas, 0);
        tabelaMeusAgendamentos = new JTable(modeloTabela);
        
        // Define uma largura inicial para cada coluna
        tabelaMeusAgendamentos.getColumnModel().getColumn(0).setPreferredWidth(80);   // Data

        tabelaMeusAgendamentos.getColumnModel().getColumn(1).setPreferredWidth(60);   // Hora

        tabelaMeusAgendamentos.getColumnModel().getColumn(2).setPreferredWidth(180);  // Profissional

        tabelaMeusAgendamentos.getColumnModel().getColumn(3).setPreferredWidth(220);  // Serviço

        tabelaMeusAgendamentos.getColumnModel().getColumn(4).setPreferredWidth(110);  // Status
        
        // Renderizador para centralizar o texto
        DefaultTableCellRenderer centralizado =
            new DefaultTableCellRenderer();

        centralizado.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Centraliza Data
        tabelaMeusAgendamentos
        .getColumnModel()
        .getColumn(0)
        .setCellRenderer(centralizado);

        // Centraliza Hora
        tabelaMeusAgendamentos
        .getColumnModel()
        .getColumn(1)
        .setCellRenderer(centralizado);

        // Centraliza Status
        tabelaMeusAgendamentos
        .getColumnModel()
        .getColumn(4)
        .setCellRenderer(centralizado);

        tabelaMeusAgendamentos.getColumnModel()
        .getColumn(0)
        .setMaxWidth(0);

        tabelaMeusAgendamentos.getColumnModel()
        .getColumn(0)
        .setWidth(0);
        tabelaMeusAgendamentos.setRowHeight(28);
        tabelaMeusAgendamentos.getTableHeader().setBackground(new Color(201, 72, 105));
        tabelaMeusAgendamentos.getTableHeader().setForeground(Color.WHITE);
        tabelaMeusAgendamentos.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tabelaMeusAgendamentos.setBackground(Color.WHITE);
        tabelaMeusAgendamentos.setGridColor(CoresUtil.ROSA_SECUNDARIO);

        painelTabela.add(new JScrollPane(tabelaMeusAgendamentos));
        
        // Painel de botões da tabela
        JPanel painelBotoesTabela = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotoesTabela.setBackground(CoresUtil.ROSA_FUNDO);

        btnCancelar = criarBotao(
            "Cancelar Agendamento",
            CoresUtil.VERMELHO_EXCLUIR);

        btnSair = criarBotao(
            "Sair",
            CoresUtil.CINZA_VOLTAR);

        painelBotoesTabela.add(btnCancelar);
        painelBotoesTabela.add(btnSair);

        painelTabela.add(
            painelBotoesTabela,
            BorderLayout.SOUTH);
        
        // Painel principal que organiza formulário e tabela
        JPanel painelConteudo = new JPanel(new BorderLayout());
        painelConteudo.setBackground(CoresUtil.ROSA_FUNDO);

        // Formulário fica no topo
        painelConteudo.add(painelForm, BorderLayout.NORTH);

        // Tabela ocupa todo o restante da tela
        painelConteudo.add(painelTabela, BorderLayout.CENTER);

        // Adiciona o painel na janela
        add(painelConteudo, BorderLayout.CENTER);

        // Ações
        btnAgendar.addActionListener(e -> realizarAgendamento());
        btnCancelar.addActionListener(e -> cancelarAgendamento());
        btnSair.addActionListener(e -> {
            new TelaLogin().setVisible(true);
            dispose();
        });
    }

    private void carregarDados() {
        // Limpa o ComboBox antes de carregar novamente
        cmbColaborador.removeAllItems();

        // Busca todos os profissionais
        listaColaboradores = colaboradorDAO.listar();

        // Adiciona somente o nome de cada profissional
        for (Colaborador c : listaColaboradores) {

        cmbColaborador.addItem(
            c.getNomeColaborador());
}

        // Limpa o ComboBox
        cmbServico.removeAllItems();

        // Busca os serviços
        listaServicos = servicoDAO.listar();

        // Mostra apenas o nome
        for (Servico s : listaServicos) {

        cmbServico.addItem(
            s.getNomeServico());
}

        // Carrega agendamentos do cliente logado
        modeloTabela.setRowCount(0);

List<Object[]> agendamentos =
        agendaDAO.listarPorCliente(
                clienteLogado.getCpfCliente()
        );

for (Object[] linha : agendamentos) {

    modeloTabela.addRow(linha);
}
    }

    private void realizarAgendamento() {
        if (txtData.getText().isEmpty() || txtHora.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Preencha a data e o horário!",
                "Atenção",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        Agenda agenda = new Agenda();
        agenda.setCpfCliente(clienteLogado.getCpfCliente());
        agenda.setData(txtData.getText().trim());
        agenda.setHorario(txtHora.getText().trim());
        agenda.setStatus("PENDENTE");

        if (cmbColaborador.getSelectedItem() != null) {
        // Descobre qual profissional foi escolhido
        Colaborador colaboradorSelecionado =
        listaColaboradores.get(
                cmbColaborador.getSelectedIndex());

        // Salva o ID verdadeiro no agendamento
        agenda.setIdColaborador(
        colaboradorSelecionado.getIdColaborador());
        }
        if (cmbServico.getSelectedItem() != null) {

        Servico servicoSelecionado =
            listaServicos.get(
                cmbServico.getSelectedIndex());

        // Salva o código do serviço
        agenda.setCodigoServico(
            servicoSelecionado.getCodigoServico());

        // Salva automaticamente a empresa do serviço
        agenda.setCodigoEmpresa(
            servicoSelecionado.getCodigoEmpresa());

}
        agendaDAO.cadastrar(agenda);
        JOptionPane.showMessageDialog(this,
            "Solicitação de agendamento enviada!\n\n"
            + "O salão irá analisar a disponibilidade do horário.\n"
            + "Você será atendido assim que o agendamento for confirmado.",
            "Solicitação enviada",
            JOptionPane.INFORMATION_MESSAGE);

        limparCampos();
        carregarDados();
    }

    private void cancelarAgendamento() {
        int linha = tabelaMeusAgendamentos.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this,
                "Selecione um agendamento para cancelar!",
                "Atenção",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacao = JOptionPane.showConfirmDialog(this,
            "Deseja cancelar este agendamento?",
            "Confirmar",
            JOptionPane.YES_NO_OPTION);

if (confirmacao == JOptionPane.YES_OPTION) {

    int idAgendamento =
        Integer.parseInt(
            modeloTabela
                .getValueAt(linha, 0)
                .toString()
        );

    agendaDAO.cancelar(idAgendamento);

    JOptionPane.showMessageDialog(
        this,
        "Agendamento cancelado!"
    );

    carregarDados();
}
    }
 
    private void limparCampos() {
        txtData.setText("");
        txtHora.setText("");
        cmbColaborador.setSelectedIndex(0);
        cmbServico.setSelectedIndex(0);
    }

   }